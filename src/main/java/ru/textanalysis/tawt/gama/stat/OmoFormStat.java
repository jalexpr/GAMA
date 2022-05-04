package ru.textanalysis.tawt.gama.stat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.textanalysis.tawt.ms.constant.Const.LAVAL_COMPRESS;
import static ru.textanalysis.tawt.ms.model.Property.*;
import static template.wrapper.classes.Lzma2FileHelper.compressionFile;

/**
 * Расчёт статистики, создание бирарного файла и его запаковка
 */
public class OmoFormStat {

    private static final Logger log = Logger.getLogger(OmoFormStat.class.getName());
    private final TagSequenceStat tagSequenceStat;
    private final WordCharacteristicsStat wordCharacteristicsStat;
    private final File file;
    private Map<String, Integer> StopWords;

    /**
     * Instantiates a new Omo form stat.
     */
    public OmoFormStat() {
        String path = System.getProperty("java.io.tmpdir");
        this.file = Paths.get(path, FOLDER, VERSION, OMO_FORM_STAT).toFile();
        this.StopWords = new HashMap<>();
        wordCharacteristicsStat = new WordCharacteristicsStat();
        tagSequenceStat = new TagSequenceStat();
    }

    /**
     * Instantiates a new Omo form stat.
     *
     * @param stopWords Map ключ - слово, значение - часть речи в стандарте JMorfSdk
     */
    public OmoFormStat(Map<String, Integer> stopWords) {
        String path = System.getProperty("java.io.tmpdir");
        this.file = Paths.get(path, FOLDER, VERSION, OMO_FORM_STAT).toFile();
        this.StopWords = stopWords;
        wordCharacteristicsStat = new WordCharacteristicsStat();
        tagSequenceStat = new TagSequenceStat();
    }

    /**
     * Устанавливает свой список стоп слов (служебных частей речи)
     * для которых выставлено строго значение части речи
     *
     * @param stopWords Map ключ - слово, значение - часть речи в стандарте JMorfSdk
     */
    public void setStopWords(Map<String, Integer> stopWords) {
        this.StopWords = stopWords;
    }

    /**
     * Добавляет новую последовательность тегов
     *
     * @param sequence последовательность, теги разделяются симовлом '|'
     */
    public void addNewTagSequence(String sequence) {
        tagSequenceStat.addNewTagSequence(sequence);
    }

    /**
     * Добавляет новую последовательность слова с тегом
     *
     * @param sequence последовательность, слово и тег разделяются симовлом '|'
     */
    public void addNewWordSequence(String sequence) {
        wordCharacteristicsStat.addNewWordSequence(sequence);
    }

    /**
     * Создание бинарного файла по собранной статистике
     */
    public void recreate() {
        tagSequenceStat.getTagVers();
        wordCharacteristicsStat.getWordVers(StopWords);
        file.getParentFile().mkdirs();
        try (FileOutputStream stream = new FileOutputStream(file)) {

            if (tagSequenceStat.getTagVer().size() > 0) {
                Object[] tempTagSequence = tagSequenceStat.getTagSequence().entrySet().toArray();
                Arrays.sort(tempTagSequence, new Comparator() {
                    public int compare(Object o1, Object o2) {
                        return ((Map.Entry<String, Integer>) o2).getValue()
                                .compareTo(((Map.Entry<String, Integer>) o1).getValue());
                    }
                });

                List<Object> tagSequence = new ArrayList<>(Arrays.asList(tempTagSequence));

                for (int i = tagSequence.size() - 1; i >= 0; i--) {
                    if (tagSequence.get(i).toString().contains("null")) {
                        tagSequence.remove(i);
                    }
                }

                for (int i = 0; i < tagSequenceStat.getTagVer().size(); i++) {
                    String[] tagSequenceValues = tagSequence.get(i).toString().split("=");
                    String tagSequenceProbability = tagSequenceValues[0] + ":" + tagSequenceStat.getTagVer().get(i).replaceAll(",", ".");
                    byte[] bytesCount = tagSequenceProbability.getBytes();
                    stream.write(ByteBuffer.allocate(2).putShort((short) bytesCount.length).array());
                    stream.write(bytesCount);
                }
            }

            if (wordCharacteristicsStat.getWords().size() > 0) {
                for (int i = 0; i < wordCharacteristicsStat.getWords().size(); i++) {
                    String tagSequenceProbability = wordCharacteristicsStat.getWords().get(i).replaceAll(",", ".");
                    byte[] bytesCount = tagSequenceProbability.getBytes();
                    stream.write(ByteBuffer.allocate(2).putShort((short) bytesCount.length).array());
                    stream.write(bytesCount);
                }

                for (Map.Entry entry : StopWords.entrySet()) {
                    String tagSequenceProbability = entry.getKey() + ":" + entry.getValue();
                    byte[] bytesCount = tagSequenceProbability.getBytes();
                    stream.write(ByteBuffer.allocate(2).putShort((short) bytesCount.length).array());
                    stream.write(bytesCount);
                }
            }
        } catch (FileNotFoundException e) {
            String message = String.format("Файл не найден!%s.\n", e.getMessage());
            log.log(Level.WARNING, message, e);
        } catch (IOException e) {
            String message = String.format("Ошибка записи в файл!%s.\n", e.getMessage());
            log.log(Level.WARNING, message, e);
        }
    }

    /**
     * Запаковывает бинарный файл
     */
    public void compression() {
        compressionFile(file.getAbsolutePath(), LAVAL_COMPRESS);
    }
}