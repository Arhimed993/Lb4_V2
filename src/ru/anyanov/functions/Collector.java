package ru.anyanov.functions;

import java.util.Collection;

public interface Collector<T, R extends Collection<T>> {
    R collect(Iterable<T> items);
}