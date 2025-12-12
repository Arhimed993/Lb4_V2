package ru.anyanov.functions;

@FunctionalInterface
public interface Predicate<T> {
    boolean test(T value);
}