package ru.textanalysis.tawt.gama.stat;

import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Работа с последовательностью тегов
 */
public class TagSequenceStat {

    private static final Logger log = Logger.getLogger(TagSequenceStat.class.getName());
    private final Map<String, Integer> tagSequence;
    private final List<Sequence> tagSequenceList;
    private final List<String> tagVer;

    /**
     * Instantiates a new Tag sequence stat.
     */
    public TagSequenceStat() {
        tagSequence = new HashMap<>();
        tagVer = new ArrayList<>();
        tagSequenceList = new ArrayList<>();
    }

    /**
     * Добавляет новую последовательность тегов
     *
     * @param sequence последовательность, теги разделяются симовлом '|'
     */
    public void addNewTagSequence(String sequence) {
        if (this.tagSequence.containsKey(sequence)) {
            int curCount = this.tagSequence.get(sequence);
            this.tagSequence.put(sequence, curCount + 1);
        } else {
            this.tagSequence.put(sequence, 1);
        }
    }

    /**
     * Расчёт вероятности встречи всех последовательностей
     */
    public void getTagVers() {
        var keys = this.tagSequence.keySet().toArray(new String[0]);
        var values = this.tagSequence.values().toArray(new Integer[0]);

        for (int i = 0; i < keys.length; i++) {
            tagSequenceList.add(new Sequence(keys[i], values[i]));
        }

        for (int i = tagSequenceList.size() - 1; i >= 0; i--) {
            if (tagSequenceList.get(i).toString().contains("null")) {
                tagSequenceList.remove(i);
            }
        }

        for (int i = 0; i < tagSequenceList.size(); i++) {
            String[] tagSequenceValue = tagSequenceList.get(i).getSequence().split("\\|");
            for (int j = 0; j < tagSequenceValue.length; j++) {
                if (Objects.equals(tagSequenceValue[j], "*")) {
                    tagSequenceValue[j] = "\\*";
                }
            }
            String caseName = "";
            int caseIndex = -1;
            for (int tagSeq = 0; tagSeq < tagSequenceValue.length; tagSeq++) {
                if (tagSequenceValue[tagSeq].matches("[a-zA-Z]+2?")) {
                    caseName = tagSequenceValue[tagSeq];
                    caseIndex = tagSeq;
                }
            }
            if (caseIndex != -1) {
                List<String> cases = new ArrayList<>();

                for (int k = 0; k < tagSequenceValue.length; k++) {
                    StringBuilder caseSequence = new StringBuilder("^");
                    for (int h = 0; h < tagSequenceValue.length; h++) {
                        if (h == k) {
                            caseSequence.append(caseName);
                        } else {
                            caseSequence.append("[0-9*]+");
                        }
                        if (h != tagSequenceValue.length - 1) {
                            caseSequence.append("\\|");
                        }
                    }
                    caseSequence.append("=");
                    cases.add(caseSequence.toString());
                }

                calculationVers(i, caseIndex, tagSequenceList, cases);
            } else {
                List<String> tags = new ArrayList<>();

                for (int k = 0; k < tagSequenceValue.length; k++) {
                    StringBuilder tagsSequence = new StringBuilder("^");
                    for (int h = 0; h < tagSequenceValue.length; h++) {
                        if (k == h) {
                            if (tagSequenceValue.length == 1) {
                                tagsSequence.append("[0-9]+");
                            } else {
                                if (Objects.equals(tagSequenceValue[k], "\\*")) {
                                    tagsSequence.append("\\*");
                                } else {
                                    tagsSequence.append("[0-9]+");
                                }
                            }
                        } else {
                            tagsSequence.append(tagSequenceValue[h]);
                        }
                        if (h != tagSequenceValue.length - 1) {
                            tagsSequence.append("\\|");
                        }
                    }
                    tagsSequence.append("=");
                    tags.add(tagsSequence.toString());
                }

                calculationVers(i, caseIndex, tagSequenceList, tags);
            }
        }
    }

    public List<String> getTagVer() {
        return tagVer;
    }

    public Map<String, Integer> getTagSequence() {
        return tagSequence;
    }

    public List<Sequence> getTagSequenceList() {
        return tagSequenceList;
    }

    private void calculationVers(int index, int caseIndex, List<Sequence> tagSequence, List<String> constitutes) {
        if (caseIndex != -1) {
            int sum = 0;
            for (Sequence s : tagSequence) {
                Pattern pattern = Pattern.compile(constitutes.get(caseIndex));
                Matcher matcher = pattern.matcher(s.toString());
                if (matcher.find()) {
                    sum += s.getOccurrence();
                }
            }
            DecimalFormat df = new DecimalFormat("0.#");
            df.setMaximumFractionDigits(7);
            this.tagVer.add(String.valueOf(df.format((double) tagSequence.get(index).getOccurrence() / (double) sum)));
        } else {
            for (int i = 0; i < constitutes.size(); i++) {
                int sum = 0;
                for (Sequence s : tagSequence) {
                    Pattern pattern = Pattern.compile(constitutes.get(i));
                    Matcher matcher = pattern.matcher(s.toString());
                    if (matcher.find()) {
                        sum += s.getOccurrence();
                    }
                }
                DecimalFormat df = new DecimalFormat("0.#");
                df.setMaximumFractionDigits(7);
                if ((Objects.equals(constitutes.get(i).split("\\|")[i], "\\*\\")) ||
                        (Objects.equals(constitutes.get(i).split("\\|")[i], "\\*=")) ||
                        Objects.equals(constitutes.get(i).split("\\|")[i], "^\\*\\")) {
                    if (i == 0) {
                        this.tagVer.add(String.valueOf(0));
                    } else {
                        this.tagVer.set(this.tagVer.size() - 1, this.tagVer.get(this.tagVer.size() - 1) + 0);
                    }
                } else {
                    if (i == 0) {
                        this.tagVer.add(String.valueOf(df.format((double) tagSequence.get(index).getOccurrence() / (double) sum)));
                    } else {
                        this.tagVer.set(this.tagVer.size() - 1, this.tagVer.get(this.tagVer.size() - 1) + df.format((double) tagSequence.get(index).getOccurrence() / (double) sum));
                    }
                }
                if (i < constitutes.size() - 1) {
                    this.tagVer.set(this.tagVer.size() - 1, this.tagVer.get(this.tagVer.size() - 1) + "|");
                }
            }
        }
    }
}
