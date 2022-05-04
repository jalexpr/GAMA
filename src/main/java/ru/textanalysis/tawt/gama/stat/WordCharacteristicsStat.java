package ru.textanalysis.tawt.gama.stat;

import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Работа со словами-омонимами
 */
public class WordCharacteristicsStat {

    private static final Logger log = Logger.getLogger(WordCharacteristicsStat.class.getName());
    private final Map<String, Integer> wordSequence;
    private List<String> words;

    /**
     * Instantiates a new Word characteristics stat.
     */
    public WordCharacteristicsStat() {
        wordSequence = new HashMap<>();
        words = new ArrayList<>();
    }

    /**
     * Добавляет новую последовательность слова с тегом
     *
     * @param sequence последовательность, слово и тег разделяются симовлом '|'
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
        Object[] tempWordSequence = this.wordSequence.entrySet().toArray();
        Arrays.sort(tempWordSequence, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getKey()
                        .compareTo(((Map.Entry<String, Integer>) o1).getKey());
            }
        });

        List<Object> wordSequence = new ArrayList<>(Arrays.asList(tempWordSequence));

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

        HashMap<String, Integer> wordTagSequence = new HashMap<>();

        String[] wordProbabilities = new String[wordSequence.size()];

        for (Object o : wordSequence) {
            String[] sequence = o.toString().split("=");
            String[] wordSequenceValue = sequence[0].split("\\|");
            if (wordTagSequence.containsKey(wordSequenceValue[1])) {
                int curCount = wordTagSequence.get(wordSequenceValue[1]);
                wordTagSequence.put(wordSequenceValue[1], curCount + Integer.parseInt(sequence[1]));
            } else {
                wordTagSequence.put(wordSequenceValue[1], Integer.parseInt(sequence[1]));
            }
        }

        for (int i = 0; i < wordSequence.size(); i++) {
            String[] sequence = wordSequence.get(i).toString().split("=");
            String[] wordSequenceValue = sequence[0].split("\\|");
            DecimalFormat df = new DecimalFormat("0.#");
            df.setMaximumFractionDigits(7);
            wordProbabilities[i] = String.valueOf(df.format((double) Integer.parseInt(sequence[1]) / (double) wordTagSequence.get(wordSequenceValue[1])));
        }

        List<String> wordsProbabilities = new ArrayList<>();

        for (int i = 0; i < wordProbabilities.length; i++) {
            String[] sequence = wordSequence.get(i).toString().split("=");
            String[] wordSequenceValue = sequence[0].split("\\|");
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
                StringBuilder text = new StringBuilder(wordSequenceValue[0] + ":" + wordSequenceValue[1] + ";" + wordProbabilities[i]);
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
