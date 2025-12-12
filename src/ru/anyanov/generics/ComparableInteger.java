package ru.anyanov.generics;

public class ComparableInteger implements MyComparable<ComparableInteger> {
    private final int value;

    public ComparableInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compare(ComparableInteger other) {
        return Integer.compare(this.value, other.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}