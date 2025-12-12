package ru.anyanov.generics;

import java.util.*;
import java.util.function.*;

public class GenericUtils {

    // ========== ЗАДАНИЕ 2: ПОИСК МАКСИМУМА В КОРОБКАХ ==========

    public static double findMaxInBoxes(List<Box<? extends Number>> boxes) {
        double max = Double.NEGATIVE_INFINITY;

        for (Box<? extends Number> box : boxes) {
            if (!box.isEmpty() && box.peek() != null) {
                double val = box.peek().doubleValue();
                if (val > max) max = val;
            }
        }

        return max == Double.NEGATIVE_INFINITY ? 0.0 : max;
    }

    // ========== ЗАДАНИЕ 3.1: ФУНКЦИЯ (MAP) ==========

    public static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        List<R> result = new ArrayList<>();
        for (T item : list) result.add(function.apply(item));
        return result;
    }

    // ========== ЗАДАНИЕ 3.2: ФИЛЬТР ==========

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) if (predicate.test(item)) result.add(item);
        return result;
    }

    // ========== ЗАДАНИЕ 3.3: СОКРАЩЕНИЕ (REDUCE) ==========

    public static <T> T reduce(List<T> list, T identity, BinaryOperator<T> accumulator) {
        if (list == null || list.isEmpty()) return identity;

        T result = identity;
        for (T item : list) result = accumulator.apply(result, item);
        return result;
    }

    // ========== ЗАДАНИЕ 3.4: КОЛЛЕКЦИОНИРОВАНИЕ ==========

    // Убрал ограничение extends Collection<T>
    public static <T, R> R collect(
            List<T> items,
            Supplier<R> collectionFactory,
            BiConsumer<R, T> accumulator
    ) {
        R result = collectionFactory.get();
        for (T item : items) {
            accumulator.accept(result, item);
        }
        return result;
    }

    // ========== ДОПОЛНИТЕЛЬНЫЕ МЕТОДЫ ==========

    public static <T, K> Map<K, List<T>> collectBy(List<T> list, Function<T, K> classifier) {
        Map<K, List<T>> result = new HashMap<>();
        for (T item : list) {
            K key = classifier.apply(item);
            result.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
        }
        return result;
    }

    public static Map<Boolean, List<Integer>> partitionNumbers(List<Integer> numbers) {
        return collectBy(numbers, n -> n > 0);
    }

    // Метод для подсчета общего количества элементов в списках списков
    public static int countTotalElements(List<List<Integer>> listOfLists) {
        int total = 0;
        for (List<Integer> list : listOfLists) {
            total += list.size();
        }
        return total;
    }

    // Метод для работы с массивами
    public static int findMaxInArray(int[] array) {
        if (array == null || array.length == 0) return 0;
        int max = array[0];
        for (int num : array) {
            if (num > max) max = num;
        }
        return max;
    }

    // Метод для проверки массива на отсутствие положительных элементов
    public static boolean hasNoPositives(int[] array) {
        for (int num : array) {
            if (num > 0) return false;
        }
        return true;
    }
}