package ru.textanalysis.tawt.gama.disambiguation;

import lombok.extern.slf4j.Slf4j;
import ru.textanalysis.tawt.gama.statistics.LoadStatisticsFromFile;
import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tawt.jmorfsdk.JMorfSdkFactory;
import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;
import ru.textanalysis.tawt.ms.model.gama.Word;
import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;

import java.util.*;
import java.util.stream.Collectors;

import static ru.textanalysis.tawt.ms.grammeme.MorfologyParametersHelper.getParametersName;

/**
 * Частичное разрешение омонимии на основе статистики
 */
@Slf4j
public class DisambiguationResolver {

    private final Map<String, String> tagSequence;

    public DisambiguationResolver(boolean init) {
        if (init) {
            JMorfSdk jMorfSdk = JMorfSdkFactory.loadFullLibrary();
            LoadStatisticsFromFile loadStatisticsFromFile = new LoadStatisticsFromFile();
            tagSequence = loadStatisticsFromFile.load(jMorfSdk);
            log.debug("DisambiguationResolver is initialized!");
        } else {
            tagSequence = new HashMap<>();
        }
    }

    /**
     * Устанавливает часть речи для однозначных слов
     * служебных частей речи (союзы, предлоги, частицы)
     *
     * @param bearingPhrase разбитое на токены предложение
     * @param wordTags      соответствие токена и его возможных частей речи
     */
    public void setPoSStopWords(List<Word> bearingPhrase, List<String> wordTags) {
        for (int i = 0; i < wordTags.size(); i++) {
            if (wordTags.get(i).contains("|")) {
                if (bearingPhrase.get(i).getOmoForms().size() > 0 && tagSequenceContains(bearingPhrase.get(i).getOmoForms().get(0).hashCode())) {
                    String word = getTagOccurrence(bearingPhrase.get(i).getOmoForms().get(0).hashCode());
                    if (!word.contains("|")) {
                        if (wordTags.get(i).contains(word)) {
                            wordTags.set(i, word);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param bearingPhrase разбитое на токены предложение
     * @param wordTags      соответствие токена и его возможных частей речи
     */
    public List<Word> setFinalCharacteristics(List<Word> bearingPhrase, List<String> wordTags) {
        List<Word> result = new ArrayList<>();
        for (int i = 0; i < bearingPhrase.size(); i++) {
            String tag = wordTags.get(i);
            List<Form> forms = new ArrayList<>();
            if (!tag.contains("|")) {
                String[] word = tag.split(":");
                forms = bearingPhrase.get(i).getOmoForms().stream()
                    .filter(c -> c.getTypeOfSpeech() == Byte.parseByte(word[0])
                        && (!tag.contains(":case-") || Objects.equals(getParametersName(c.getMorfCharacteristicsByIdentifier(MorfologyParameters.Case.IDENTIFIER)), word[1].split("-")[1])))
                    .collect(Collectors.toList());
            }
            if (forms.size() == 0) {
                forms = bearingPhrase.get(i).getOmoForms();
                if (forms.size() == 0) {
                    forms = null;
                }
            }
            result.add(new Word(forms));
        }
        return result;
    }

    /**
     * @param bearingPhrase разбитое на токены предложение
     * @param wordTags      соответствие токена и его возможных частей речи
     * @param i             индекс токена в предложении
     */
    public void caseProbabilityCalculation(List<Word> bearingPhrase, List<String> wordTags, int i) {
        List<Double> caseCounts = new ArrayList<>();
        List<Form> forms;
        forms = bearingPhrase.get(i).getOmoForms().stream()
            .filter(c -> c.getTypeOfSpeech() == Byte.parseByte(wordTags.get(i)))
            .collect(Collectors.toList());
        if (forms.size() > 1) {
            for (Form form : forms) {
                String caseName = getParametersName(form.getMorfCharacteristicsByIdentifier(MorfologyParameters.Case.IDENTIFIER));
                if (caseName != null && !caseName.equals("UNCLEARGENDER")) {
                    String[] t31 = {"0"};
                    String[] t32 = {"0"};
                    String[] t33 = {"0"};
                    String[] t21 = {"0"};
                    String[] t22 = {"0"};

                    if ((i - 1) >= 0) {
                        t21 = getTagOccurrence(wordTags.get(i - 1) + "|" + "case-" + caseName).split("\\|");
                        if ((i + 1) < wordTags.size()) {
                            t31 = getTagOccurrence(wordTags.get(i - 1) + "|" + "case-" + caseName + "|" + wordTags.get(i + 1)).split("\\|");
                        }
                        if ((i - 2) >= 0) {
                            t32 = getTagOccurrence(wordTags.get(i - 2) + "|" + wordTags.get(i - 1) + "|" + "case-" + caseName).split("\\|");
                        }
                    }
                    if ((i + 1) < wordTags.size()) {
                        t22 = getTagOccurrence("case-" + caseName + "|" + wordTags.get(i + 1)).split("\\|");
                        if ((i + 2) < wordTags.size()) {
                            t33 = getTagOccurrence("case-" + caseName + "|" + wordTags.get(i + 1) + "|" + wordTags.get(i + 2)).split("\\|");
                        }
                    }

                    caseCounts.add(0.2 * Double.parseDouble(t21[0]) + 0.8 * Double.parseDouble(t32[0]) *
                        (0.6 * (Double.parseDouble(t22[0]) + Double.parseDouble(t21[0])) / 2 + 0.4 * Double.parseDouble(t31[0])) *
                        (0.25 * Double.parseDouble(t22[0]) + 0.75 * Double.parseDouble(t33[0])));
                } else {
                    caseCounts.add((double) 0);
                }
            }
            if (Collections.max(caseCounts) != 0) {
                wordTags.set(i, wordTags.get(i) + ":" + "case-" + getParametersName(forms.get(caseCounts.indexOf(Collections.max(caseCounts))).getMorfCharacteristicsByIdentifier(MorfologyParameters.Case.IDENTIFIER)));
            } else {
                wordTags.set(i, wordTags.get(i));
            }
        }
    }

    /**
     * Вычисление вероятностей для последовательности тегов
     *
     * @param bearingPhrase  разбитое на токены предложение
     * @param wordTags       соответствие токена и его возможных частей речи
     * @param i              индекс токена в предложении
     * @param isInDictionary присутствие ли данный токен в словаре статистики
     */
    public void tagProbabilityCalculation(List<Word> bearingPhrase, List<String> wordTags, int i, boolean isInDictionary) {
        List<Double> counts = new ArrayList<>();
        String[] tags = wordTags.get(i).split("\\|");
        for (String tag : tags) {
            String[] s31 = {"0"};
            String[] s32 = {"0"};
            String[] s33 = {"0"};
            String[] s21 = {"0"};
            String[] s22 = {"0"};
            if ((i - 1) >= 0) {
                s21 = getTagOccurrence(wordTags.get(i - 1) + "|" + tag).split("\\|");
                if ((i + 1) < wordTags.size()) {
                    s31 = getTagOccurrence(wordTags.get(i - 1) + "|" + tag + "|" + wordTags.get(i + 1)).split("\\|");
                }
                if ((i - 2) >= 0) {
                    s32 = getTagOccurrence(wordTags.get(i - 2) + "|" + wordTags.get(i - 1) + "|" + tag).split("\\|");
                }
            }
            if ((i + 1) < wordTags.size()) {
                s22 = getTagOccurrence(tag + "|" + wordTags.get(i + 1)).split("\\|");
                if ((i + 2) < wordTags.size()) {
                    s33 = getTagOccurrence(tag + "|" + wordTags.get(i + 1) + "|" + wordTags.get(i + 2)).split("\\|");
                }
            }

            if (i == 0) {
                s21 = getTagOccurrence("*" + "|" + tag).split("\\|");
                s32 = s21;
            }
            if (i == 1) {
                s32 = getTagOccurrence("*" + "|" + wordTags.get(0) + "|" + tag).split("\\|");
            }
            if (i == wordTags.size() - 1) {
                s22 = getTagOccurrence(tag + "|" + "*").split("\\|");
                s33 = s22;
            }
            if (i == wordTags.size() - 2) {
                s33 = getTagOccurrence(tag + "|" + wordTags.get(i + 1) + "|" + "*").split("\\|");
            }

            String[] s1 = getTagOccurrence(tag).split("\\|");

            if (isInDictionary) {
                String[] word = getTagOccurrence(bearingPhrase.get(i).getOmoForms().get(0).hashCode()).split("\\|");
                int wordIndex = -1;
                for (int wo = 0; wo < word.length; wo++) {
                    if (word[wo].matches("^" + tag + ";.*$")) {
                        wordIndex = wo;
                        break;
                    }
                }

                if (wordIndex != -1) {
                    counts.add(Double.parseDouble(word[wordIndex].split(";")[1]) *
                        (0.01 * Double.parseDouble(s1[0]) + 0.55 * Double.parseDouble(checkNull(s21, 1)) + 0.45 * Double.parseDouble(checkNull(s32, 2))) *
                        (0.01 * Double.parseDouble(s1[0]) + 0.55 * (Double.parseDouble(checkNull(s22, 0)) + Double.parseDouble(checkNull(s21, 1))) / 2 + 0.45 * Double.parseDouble(checkNull(s31, 1))) *
                        (0.01 * Double.parseDouble(s1[0]) + 0.55 * Double.parseDouble(checkNull(s22, 0)) + 0.45 * Double.parseDouble(checkNull(s33, 0))));
                } else {
                    counts.add(Double.parseDouble("0"));
                }
            } else {
                counts.add((0.01 * Double.parseDouble(s1[0]) + 0.55 * Double.parseDouble(checkNull(s21, 1)) + 0.45 * Double.parseDouble(checkNull(s32, 2))) *
                    (0.01 * Double.parseDouble(s1[0]) + 0.55 * (Double.parseDouble(checkNull(s22, 0)) + Double.parseDouble(checkNull(s21, 1))) / 2 + 0.45 * Double.parseDouble(checkNull(s31, 1))) *
                    (0.01 * Double.parseDouble(s1[0]) + 0.55 * Double.parseDouble(checkNull(s22, 0)) + 0.45 * Double.parseDouble(checkNull(s33, 0))));
            }
        }
        wordTags.set(i, tags[counts.indexOf(Collections.max(counts))]);
    }

    public boolean tagSequenceContains(int tag) {
        return tagSequenceContains(String.valueOf(tag));
    }

    public boolean tagSequenceContains(String tag) {
        return tagSequence.containsKey(tag);
    }

    private String getTagOccurrence(Integer tag) {
        return getTagOccurrence(String.valueOf(tag));
    }

    private String getTagOccurrence(String tag) {
        try {
            return tagSequence.getOrDefault(tag, "0");
        } catch (Exception ex) {
            return "0";
        }
    }

    private String checkNull(String[] array, int index) {
        if (array.length > index) {
            return array[index];
        }
        return "0";
    }
}