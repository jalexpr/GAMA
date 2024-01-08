package ru.textanalysis.tawt.gama.statistics;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.textanalysis.tawt.ms.constant.Const.LAVAL_COMPRESS;
import static ru.textanalysis.tawt.ms.model.MorphologicalStructuresProperty.*;
import static template.wrapper.classes.Lzma2FileHelper.compressionFile;

/**
 * Расчёт статистики, создание бинарного файла и его запаковка
 */
//todo перенести в конвектор в MS
@Slf4j
public class OmoFormStatistics {

    private final TagSequenceStatistics tagSequenceStatistics;
    private final WordCharacteristicsStatistics wordCharacteristicsStatistics;
    private final File file;
    private Map<String, Integer> stopWords;

    public OmoFormStatistics() {
        this(new HashMap<>());
    }

    /**
     * @param stopWords Map ключ - слово, значение - часть речи в стандарте JMorfSdk
     */
    public OmoFormStatistics(Map<String, Integer> stopWords) {
        String path = System.getProperty("java.io.tmpdir");
        this.file = Paths.get(path, FOLDER, MS_VERSION, OMO_FORM_STATISTICS).toFile();
        this.stopWords = stopWords;
        wordCharacteristicsStatistics = new WordCharacteristicsStatistics();
        tagSequenceStatistics = new TagSequenceStatistics();
    }

    /**
     * Устанавливает свой список стоп слов (служебных частей речи)
     * для которых выставлено строго значение части речи
     *
     * @param stopWords Map ключ - слово, значение - часть речи в стандарте JMorfSdk
     */
    public void setStopWords(Map<String, Integer> stopWords) {
        this.stopWords = stopWords;
    }

    /**
     * Добавляет новую последовательность тегов
     *
     * @param sequence последовательность, теги разделяются символом '|'
     */
    public void addNewTagSequence(String sequence) {
        tagSequenceStatistics.addNewTagSequence(sequence);
    }

    /**
     * Добавляет новую последовательность слова с тегом
     *
     * @param sequence последовательность, слово и тег разделяются символом '|'
     */
    public void addNewWordSequence(String sequence) {
        wordCharacteristicsStatistics.addNewWordSequence(sequence);
    }

    /**
     * Создание бинарного файла по собранной статистике
     */
    public void recreate() {
        tagSequenceStatistics.getTagVers();
        wordCharacteristicsStatistics.getWordVers(stopWords);
        file.getParentFile().mkdirs();
        try (FileOutputStream stream = new FileOutputStream(file)) {
            if (tagSequenceStatistics.getTagVer().size() > 0) {
                List<Sequence> tagSequence = new ArrayList<>(tagSequenceStatistics.getTagSequenceList());

                for (int i = tagSequence.size() - 1; i >= 0; i--) {
                    if (tagSequence.get(i).toString().contains("null")) {
                        tagSequence.remove(i);
                    }
                }

                for (int i = 0; i < tagSequenceStatistics.getTagVer().size(); i++) {
                    String tagSequenceProbability = tagSequence.get(i).getSequence() + ":" + tagSequenceStatistics.getTagVer().get(i).replaceAll(",", ".");
                    byte[] bytesCount = tagSequenceProbability.getBytes();
                    stream.write(ByteBuffer.allocate(2).putShort((short) bytesCount.length).array());
                    stream.write(bytesCount);
                }
            }

            if (wordCharacteristicsStatistics.getWords().size() > 0) {
                for (int i = 0; i < wordCharacteristicsStatistics.getWords().size(); i++) {
                    String tagSequenceProbability = wordCharacteristicsStatistics.getWords().get(i).replaceAll(",", ".");
                    byte[] bytesCount = tagSequenceProbability.getBytes();
                    stream.write(ByteBuffer.allocate(2).putShort((short) bytesCount.length).array());
                    stream.write(bytesCount);
                }

                for (Map.Entry<String, Integer> entry : stopWords.entrySet()) {
                    String tagSequenceProbability = entry.getKey() + ":" + entry.getValue();
                    byte[] bytesCount = tagSequenceProbability.getBytes();
                    stream.write(ByteBuffer.allocate(2).putShort((short) bytesCount.length).array());
                    stream.write(bytesCount);
                }
            }
        } catch (FileNotFoundException ex) {
            String message = String.format("Файл не найден!%s.\n", ex.getMessage());
            log.warn(message, ex);
        } catch (IOException ex) {
            String message = String.format("Ошибка записи в файл!%s.\n", ex.getMessage());
            log.warn(message, ex);
        }
    }

    /**
     * Запаковывает бинарный файл
     */
    public void compression() {
        compressionFile(file.getAbsolutePath(), LAVAL_COMPRESS);
    }
}