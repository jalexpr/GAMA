package ru.textanalysis.tawt.gama.statistics;

class Sequence {

    private final String sequence;
    private final int occurrence;

    public Sequence(String sequence, int occurrence) {
        this.sequence = sequence;
        this.occurrence = occurrence;
    }

    public String getSequence() {
        return sequence;
    }

    public int getOccurrence() {
        return occurrence;
    }

    @Override
    public String toString() {
        return sequence + "=" + occurrence;
    }
}
