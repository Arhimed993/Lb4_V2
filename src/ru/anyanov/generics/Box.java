package ru.anyanov.generics;

public class Box<T> {
    private T item;

    public Box() {}
    public Box(T item) { this.item = item; }

    public void put(T item) {
        if (this.item != null) throw new IllegalStateException("Коробка занята");
        this.item = item;
    }

    public T take() {
        T result = item;
        item = null;
        return result;
    }

    public T peek() { return item; }
    public boolean isEmpty() { return item == null; }

    @Override
    public String toString() {
        return "Box{" + (item != null ? item : "пусто") + "}";
    }
}