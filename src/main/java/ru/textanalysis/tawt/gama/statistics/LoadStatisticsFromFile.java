package ru.textanalysis.tawt.gama.statistics;

import lombok.extern.slf4j.Slf4j;
import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static ru.textanalysis.tawt.ms.model.MorphologicalStructuresProperty.*;
import static template.wrapper.classes.Lzma2FileHelper.ARCHIVE_EXPANSION;
import static template.wrapper.classes.Lzma2FileHelper.deCompressionFile;

/**
 * Загрузка информации о статистике последовательности тегов из бинарного файла
 */
//todo перенести в конвектор в awf т.к. фильтровать можно не только в гамме
@Slf4j
public class LoadStatisticsFromFile {

    private final File file;

    public LoadStatisticsFromFile() {
        String path = System.getProperty("java.io.tmpdir");
        this.file = Paths.get(path, FOLDER, MS_VERSION, OMO_FORM_STATISTICS).toFile();
        deCompress();
    }

    /**
     * Загрузка статистики
     *
     * @param jMorfSdk объект JMorfSdk для установки хеш-кода для слов
     * @return Map ключ - последовательность, значение - вероятности
     */
    public Map<String, String> load(JMorfSdk jMorfSdk) {
        try (InputStream stream = new FileInputStream(file)) {
            return loadStatInfo(jMorfSdk, stream);
        } catch (IOException ex) {
            String messages = "Cannot load OmoFormStat. " + ex.getMessage();
            log.warn(messages, ex);
            return Map.of();
        }
    }

    private void deCompress() {
        if (!file.exists()) {
            System.out.println("Decompress File. Please wait a few minutes");
            File dir = file.getParentFile();
            dir.mkdirs();
            String nameExp = file.getName() + ARCHIVE_EXPANSION;
            String path = FOLDER + nameExp;
            URL pathZip = getClass().getClassLoader().getResource(path);
            if (pathZip != null) {
                deCompressionFile(path, file);
            } else {
                String message = "Not create file '" + path + "' for decompress";
                log.info(message);
            }
        }
    }

    private Map<String, String> loadStatInfo(JMorfSdk jMorfSdk, InputStream inputStreamStatInfo) {
        Map<String, String> tagSequence = new HashMap<>();
        try (BufferedInputStream inputStream = new BufferedInputStream(inputStreamStatInfo)) {
            while (inputStream.available() > 0) {
                loadSequenceInfo(tagSequence, jMorfSdk, inputStream);
            }
        } catch (IOException e) {
            String messages = "Cannot load OmoFormStat. " + e.getMessage();
            log.warn(messages, e);
        }
        return tagSequence;
    }

    private void loadSequenceInfo(Map<String, String> tagSequence, JMorfSdk jMorfSdk, InputStream inputStreamStatInfo) {
        int byteCount = getShortFromBytes(inputStreamStatInfo);
        String sequence = getStatInfoFromBytes(inputStreamStatInfo, byteCount);
        String[] info = sequence.split(":");
        if (info[0].matches("^[а-яё]+$")) {
            tagSequence.put(String.valueOf(jMorfSdk.getOmoForms(info[0]).get(0).hashCode()), info[1]);
        } else {
            tagSequence.put(info[0], info[1]);
        }
    }

    private short getShortFromBytes(InputStream inputStream) {
        return (short) getValueCodeFromBytes(inputStream, 2);
    }

    private long getValueCodeFromBytes(InputStream inputStream, int countByte) {
        long returnValue = 0;
        try {
            for (int i = 0; i < countByte; i++) {
                long f = 0xFF & inputStream.read();
                long g1 = f << (8 * (countByte - 1 - i));
                returnValue |= g1;
            }
        } catch (IOException ex) {
            String message = String.format("Неожиданное окончание файла, проверти целостность файлов!%s.\n", ex.getMessage());
            log.warn(message, ex);
        }
        return returnValue;
    }

    private String getStatInfoFromBytes(InputStream inputStream, int countByte) {
        byte[] statInfo = new byte[countByte];
        try {
            for (int i = 0; i < countByte; i++) {
                statInfo[i] = (byte) (0xFF & inputStream.read());
            }
        } catch (IOException e) {
            String message = String.format("Неожиданное окончание файла, проверти целостность файлов!%s.\n", e.getMessage());
            log.warn(message, e);
        }
        return new String(statInfo, StandardCharsets.UTF_8);
    }
}