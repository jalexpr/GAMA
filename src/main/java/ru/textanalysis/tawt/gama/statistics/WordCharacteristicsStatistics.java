package ru.textanalysis.tawt.gama.statistics;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Работа со словами - омонимами
 */
public class WordCharacteristicsStatistics {

    private final Map<String, Integer> wordSequence;
    private List<String> words;

    public WordCharacteristicsStatistics() {
        wordSequence = new HashMap<>();
        words = new ArrayList<>();
    }

    /**
     * Добавляет новую последовательность слова с тегом
     *
     * @param sequence последовательность, слово и тег разделяются символом '|'
     */
    public void addNewWordSequence(String sequence) {
        if (wordSequence.containsKey(sequence)) {
            int curCount = wordSequence.get(sequence);
            wordSequence.put(sequence, curCount + 1);
        } else {
            wordSequence.put(sequence, 1);
        }
    }

    /**
     * Расчёт вероятности встречи слова со всеми возможными для него частями речи
     *
     * @param StopWords Map ключ - слово, значение - часть речи в стандарте JMorfSdk
     */
    public void getWordVers(Map<String, Integer> StopWords) {
        Map.Entry<String, Integer>[] tempWordSequence = this.wordSequence.entrySet().toArray(new Map.Entry[0]);
        Arrays.sort(tempWordSequence, (o1, o2) -> o2.getKey().compareTo(o1.getKey()));

        List<Sequence> wordSequence = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : tempWordSequence) {
            wordSequence.add(new Sequence(entry.getKey(), entry.getValue()));
        }

        for (int i = wordSequence.size() - 1; i >= 0; i--) {
            if (StopWords.containsKey(wordSequence.get(i).toString().split("\\|")[0])) {
                wordSequence.remove(i);
            } else if (i == 0) {
                if (!Objects.equals(wordSequence.get(i).toString().split("\\|")[0], wordSequence.get(i + 1).toString().split("\\|")[0])) {
                    wordSequence.remove(i);
                }
            } else if (i == wordSequence.size() - 1) {
                if (!Objects.equals(wordSequence.get(i).toString().split("\\|")[0], wordSequence.get(i - 1).toString().split("\\|")[0])) {
                    wordSequence.remove(i);
                }
            } else {
                if (!Objects.equals(wordSequence.get(i).toString().split("\\|")[0], wordSequence.get(i - 1).toString().split("\\|")[0]) && !Objects.equals(wordSequence.get(i).toString().split("\\|")[0], wordSequence.get(i + 1).toString().split("\\|")[0])) {
                    wordSequence.remove(i);
                }
            }
        }

        Map<String, Integer> wordTagSequence = new HashMap<>();

        String[] wordProbabilities = new String[wordSequence.size()];

        for (Sequence s : wordSequence) {
            String[] wordSequenceValue = s.getSequence().split("\\|");
            if (wordTagSequence.containsKey(wordSequenceValue[1])) {
                int curCount = wordTagSequence.get(wordSequenceValue[1]);
                wordTagSequence.put(wordSequenceValue[1], curCount + s.getOccurrence());
            } else {
                wordTagSequence.put(wordSequenceValue[1], s.getOccurrence());
            }
        }

        for (int i = 0; i < wordSequence.size(); i++) {
            String[] wordSequenceValue = wordSequence.get(i).getSequence().split("\\|");
            DecimalFormat df = new DecimalFormat("0.#");
            df.setMaximumFractionDigits(7);
            wordProbabilities[i] = String.valueOf(df.format((double) wordSequence.get(i).getOccurrence() / (double) wordTagSequence.get(wordSequenceValue[1])));
        }

        List<String> wordsProbabilities = new ArrayList<>();

        for (int i = 0; i < wordProbabilities.length; i++) {
            String[] wordSequenceValue = wordSequence.get(i).getSequence().split("\\|");
            boolean found = false;
            for (String wordsProbability : wordsProbabilities) {
                Pattern pattern = Pattern.compile("^" + wordSequenceValue[0] + ":");
                Matcher matcher = pattern.matcher(wordsProbability);
                if (matcher.find()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                StringBuilder text = new StringBuilder(wordSequenceValue[0]).append(":").append(wordSequenceValue[1]).append(";").append(wordProbabilities[i]);
                for (int j = 0; j < wordProbabilities.length; j++) {
                    Pattern pattern = Pattern.compile("^" + wordSequenceValue[0] + "\\|");
                    Matcher matcher = pattern.matcher(wordSequence.get(j).toString());
                    if (j != i && matcher.find()) {
                        String[] otherSequence = wordSequence.get(j).toString().split("=");
                        String[] otherWordSequenceValue = otherSequence[0].split("\\|");
                        text.append("|").append(otherWordSequenceValue[1]).append(";").append(wordProbabilities[j]);
                    }
                }
                wordsProbabilities.add(text.toString());
            }
        }

        this.words = wordsProbabilities;
    }

    public List<String> getWords() {
        return words;
    }
}
