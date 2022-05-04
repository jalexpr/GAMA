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
    private final List<String> tagVer;

    /**
     * Instantiates a new Tag sequence stat.
     */
    public TagSequenceStat() {
        tagSequence = new HashMap<>();
        tagVer = new ArrayList<>();
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
        Object[] tempTagSequence = this.tagSequence.entrySet().toArray();
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

        for (int i = 0; i < tagSequence.size(); i++) {
            String[] sequence = tagSequence.get(i).toString().split("=");
            String[] tagSequenceValue = sequence[0].split("\\|");
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

                calculationVers(i, caseIndex, tagSequence, cases);
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

                calculationVers(i, caseIndex, tagSequence, tags);
            }
        }
    }

    public List<String> getTagVer() {
        return tagVer;
    }

    public Map<String, Integer> getTagSequence() {
        return tagSequence;
    }

    private void calculationVers(int index, int caseIndex, List<Object> tagSequence, List<String> constitutes) {
        if (caseIndex != -1) {
            int sum = 0;
            for (Object o : tagSequence) {
                Pattern pattern = Pattern.compile(constitutes.get(caseIndex));
                Matcher matcher = pattern.matcher(o.toString());
                if (matcher.find()) {
                    String[] tagSequenceValue = o.toString().split("=");
                    sum += Integer.parseInt(tagSequenceValue[1]);
                }
            }
            DecimalFormat df = new DecimalFormat("0.#");
            df.setMaximumFractionDigits(7);
            this.tagVer.add(String.valueOf(df.format((double) Integer.parseInt(tagSequence.get(index).toString().split("=")[1]) / (double) sum)));
        } else {
            for (int i = 0; i < constitutes.size(); i++) {
                int sum = 0;
                for (Object o : tagSequence) {
                    Pattern pattern = Pattern.compile(constitutes.get(i));
                    Matcher matcher = pattern.matcher(o.toString());
                    if (matcher.find()) {
                        String[] tagSequenceValue = o.toString().split("=");
                        sum += Integer.parseInt(tagSequenceValue[1]);
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
                        this.tagVer.add(String.valueOf(df.format((double) Integer.parseInt(tagSequence.get(index).toString().split("=")[1]) / (double) sum)));
                    } else {
                        this.tagVer.set(this.tagVer.size() - 1, this.tagVer.get(this.tagVer.size() - 1) + df.format((double) Integer.parseInt(tagSequence.get(index).toString().split("=")[1]) / (double) sum));
                    }
                }
                if (i < constitutes.size() - 1) {
                    this.tagVer.set(this.tagVer.size() - 1, this.tagVer.get(this.tagVer.size() - 1) + "|");
                }
            }
        }
    }
}
