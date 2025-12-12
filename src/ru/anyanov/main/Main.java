package ru.anyanov.main;

import ru.anyanov.generics.*;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== ЛАБОРАТОРНАЯ РАБОТА №4: ОБОБЩЕННЫЕ ТИПЫ ===");
        System.out.println("Анянов Кирилл ИТ-4");
        System.out.println("=".repeat(50));
        showMainMenu();
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
            System.out.println("1 - Операции с коробкой");
            System.out.println("2 - Поиск максимума в коробках");
            System.out.println("3 - Функция (map)");
            System.out.println("4 - Фильтр");
            System.out.println("5 - Сокращение (reduce)");
            System.out.println("6 - Коллекционирование");
            System.out.println("7 - Работа с массивами");
            System.out.println("8 - Демонстрация MyComparable");
            System.out.println("9 - Все демонстрации");
            System.out.println("0 - Выход");
            System.out.print("Выберите: ");

            int choice = readInt();
            switch (choice) {
                case 1: demonstrateBoxOperations(); break;
                case 2: demonstrateMaxInBoxes(); break;
                case 3: demonstrateMap(); break;
                case 4: demonstrateFilter(); break;
                case 5: demonstrateReduce(); break;
                case 6: demonstrateCollect(); break;
                case 7: demonstrateArrays(); break;
                case 8: demonstrateComparable(); break;
                case 9: demonstrateAll(); break;
                case 0: System.out.println("Выход"); scanner.close(); return;
                default: System.out.println("Неверный выбор!");
            }
        }
    }

    // ========== РУЧНОЙ ВВОД ВО ВСЕХ ЗАДАНИЯХ ==========

    private static void demonstrateBoxOperations() {
        System.out.println("\n=== ОПЕРАЦИИ С ОБОБЩЕННОЙ КОРОБКОЙ ===");

        System.out.print("1. Введите число для коробки Integer: ");
        int num = readInt();
        Box<Integer> intBox = new Box<>();
        intBox.put(num);
        System.out.println("   Коробка Integer: " + intBox);
        System.out.println("   Содержимое: " + intBox.peek());

        System.out.print("2. Введите строку для коробки String: ");
        String text = scanner.nextLine();
        Box<String> strBox = new Box<>(text);
        System.out.println("   Коробка String: " + strBox);

        System.out.print("3. Введите дробное число для коробки Double: ");
        double dbl = readDouble();
        Box<Double> dblBox = new Box<>(dbl);
        System.out.println("   Коробка Double: " + dblBox);

        System.out.print("\n4. Проверка ошибок. Попробуем положить в заполненную коробку: ");
        try {
            intBox.put(999);
            System.out.println("   Успешно (это ошибка!)");
        } catch (IllegalStateException e) {
            System.out.println("   Ошибка: " + e.getMessage());
        }

        System.out.print("\n5. Забрать из коробки? (1-да, 2-нет): ");
        if (readInt() == 1) {
            System.out.println("   Забрали из Integer коробки: " + intBox.take());
            System.out.println("   Теперь коробка: " + intBox);
        }

        waitForEnter();
    }

    private static void demonstrateMaxInBoxes() {
        System.out.println("\n=== ПОИСК МАКСИМУМА В КОРОБКАХ ===");

        System.out.print("Сколько коробок создать? (минимум 2): ");
        int count = Math.max(2, readInt());

        List<Box<? extends Number>> boxes = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            System.out.print("Введите число для коробки " + (i+1) + " (можно дробное): ");
            double value = readDouble();

            System.out.print("Тип числа (1-целое, 2-дробное): ");
            int type = readInt();

            if (type == 1) {
                boxes.add(new Box<>((int)value));
            } else {
                boxes.add(new Box<>(value));
            }
        }

        System.out.println("\nСозданные коробки:");
        for (int i = 0; i < boxes.size(); i++) {
            System.out.println("  " + (i+1) + ") " + boxes.get(i));
        }

        double max = GenericUtils.findMaxInBoxes(boxes);
        System.out.println("\nМаксимальное значение в коробках: " + max);

        waitForEnter();
    }

    private static void demonstrateMap() {
        System.out.println("\n=== ФУНКЦИЯ (MAP) ===");

        System.out.println("1. Строки -> Длины строк (3.1.1)");
        System.out.print("   Введите строки через запятую: ");
        List<String> strings = readStringList();
        List<Integer> lengths = GenericUtils.map(strings, String::length);
        System.out.println("   Длины строк: " + lengths);

        System.out.println("\n2. Числа -> Абсолютные значения (3.1.2)");
        System.out.print("   Введите числа через пробел: ");
        List<Integer> numbers = readIntList();
        List<Integer> absolutes = GenericUtils.map(numbers, Math::abs);
        System.out.println("   Абсолютные значения: " + absolutes);

        System.out.println("\n3. Массивы -> Максимумы массивов (3.1.3)");
        System.out.print("   Сколько массивов создать? ");
        int arrayCount = Math.max(1, readInt());

        List<int[]> arrays = new ArrayList<>();
        for (int i = 0; i < arrayCount; i++) {
            System.out.print("   Введите числа для массива " + (i+1) + " через пробел: ");
            int[] array = readIntArray();
            arrays.add(array);
        }

        System.out.println("   Ваши массивы:");
        for (int i = 0; i < arrays.size(); i++) {
            System.out.println("     Массив " + (i+1) + ": " + Arrays.toString(arrays.get(i)));
        }

        List<Integer> maxValues = GenericUtils.map(arrays, arr -> {
            int max = arr[0];
            for (int num : arr) {
                if (num > max) max = num;
            }
            return max;
        });

        System.out.println("   Максимумы массивов: " + maxValues);

        waitForEnter();
    }

    private static void demonstrateFilter() {
        System.out.println("\n=== ФИЛЬТР ===");

        System.out.println("1. Фильтрация строк по длине (3.2.1)");
        System.out.print("   Введите строки через запятую: ");
        List<String> strings = readStringList();

        System.out.print("   Минимальная длина строки для фильтрации: ");
        int minLength = readInt();

        List<String> filtered = GenericUtils.filter(strings, s -> s.length() >= minLength);
        System.out.println("   Строки длиной >= " + minLength + ": " + filtered);

        System.out.println("\n2. Фильтрация положительных чисел (3.2.2)");
        System.out.print("   Введите числа через пробел: ");
        List<Integer> numbers = readIntList();

        List<Integer> positives = GenericUtils.filter(numbers, n -> n > 0);
        System.out.println("   Положительные числа: " + positives);

        System.out.println("\n3. Фильтрация массивов без положительных (3.2.3)");
        System.out.print("   Сколько массивов проверить? ");
        int arrayCount = Math.max(1, readInt());

        List<int[]> arrays = new ArrayList<>();
        for (int i = 0; i < arrayCount; i++) {
            System.out.print("   Введите числа для массива " + (i+1) + " через пробел: ");
            arrays.add(readIntArray());
        }

        List<int[]> noPositiveArrays = GenericUtils.filter(arrays, arr -> {
            for (int num : arr) {
                if (num > 0) return false;
            }
            return true;
        });

        System.out.println("   Массивы без положительных элементов: " + noPositiveArrays.size() + " шт");
        for (int[] arr : noPositiveArrays) {
            System.out.println("     " + Arrays.toString(arr));
        }

        waitForEnter();
    }

    private static void demonstrateReduce() {
        System.out.println("\n=== СОКРАЩЕНИЕ (REDUCE) ===");

        System.out.println("1. Конкатенация строк (3.3.1)");
        System.out.print("   Введите строки через запятую: ");
        List<String> strings = readStringList();

        String concat = GenericUtils.reduce(strings, "", (a, b) -> a + b);
        System.out.println("   Объединенная строка: " + concat);
        System.out.println("   Длина: " + concat.length());

        System.out.println("\n2. Сумма чисел (3.3.2)");
        System.out.print("   Введите числа через пробел: ");
        List<Integer> numbers = readIntList();

        int sum = GenericUtils.reduce(numbers, 0, Integer::sum);
        System.out.println("   Сумма: " + sum);

        System.out.println("\n3. Общее количество элементов в списках (3.3.3)");
        System.out.print("   Сколько списков чисел создать? ");
        int listCount = Math.max(1, readInt());

        List<List<Integer>> listOfLists = new ArrayList<>();
        for (int i = 0; i < listCount; i++) {
            System.out.print("   Введите числа для списка " + (i+1) + " через пробел: ");
            listOfLists.add(readIntList());
        }

        int totalCount = 0;
        for (List<Integer> list : listOfLists) {
            totalCount += list.size();
        }

        System.out.println("   Списки: " + listOfLists);
        System.out.println("   Общее количество элементов: " + totalCount);

        waitForEnter();
    }

    private static void demonstrateCollect() {
        System.out.println("\n=== КОЛЛЕКЦИОНИРОВАНИЕ ===");

        System.out.println("1. Разделение чисел на положительные/отрицательные (3.4.1)");
        System.out.print("   Введите числа через пробел: ");
        List<Integer> numbers = readIntList();

        Map<Boolean, List<Integer>> partitioned = new HashMap<>();
        partitioned.put(true, new ArrayList<>());
        partitioned.put(false, new ArrayList<>());

        for (Integer num : numbers) {
            if (num > 0) {
                partitioned.get(true).add(num);
            } else {
                partitioned.get(false).add(num);
            }
        }

        System.out.println("   Положительные: " + partitioned.get(true));
        System.out.println("   Отрицательные и нули: " + partitioned.get(false));

        System.out.println("\n2. Группировка строк по длине (3.4.2)");
        System.out.print("   Введите строки через запятую: ");
        List<String> strings = readStringList();

        Map<Integer, List<String>> groupedByLength = new HashMap<>();
        for (String str : strings) {
            int length = str.length();
            groupedByLength.computeIfAbsent(length, k -> new ArrayList<>()).add(str);
        }

        System.out.println("   Группировка по длине:");
        for (Map.Entry<Integer, List<String>> entry : groupedByLength.entrySet()) {
            System.out.println("     Длина " + entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\n3. Удаление дубликатов (3.4.3)");
        System.out.print("   Введите строки через запятую (можно с дубликатами): ");
        List<String> withDuplicates = readStringList();

        Set<String> unique = new LinkedHashSet<>(withDuplicates);
        System.out.println("   Без дубликатов: " + unique);

        waitForEnter();
    }

    private static void demonstrateArrays() {
        System.out.println("\n=== РАБОТА С МАССИВАМИ ===");

        System.out.print("1. Введите массив чисел через пробел: ");
        int[] array = readIntArray();

        System.out.println("   Ваш массив: " + Arrays.toString(array));

        int max = array[0];
        for (int num : array) {
            if (num > max) max = num;
        }
        System.out.println("   Максимум: " + max);

        boolean hasPositive = false;
        for (int num : array) {
            if (num > 0) {
                hasPositive = true;
                break;
            }
        }
        System.out.println("   Содержит положительные: " + hasPositive);

        waitForEnter();
    }

    private static void demonstrateComparable() {
        System.out.println("\n=== ДЕМОНСТРАЦИЯ MyComparable ===");

        System.out.print("Введите первое целое число: ");
        int num1 = readInt();
        System.out.print("Введите второе целое число: ");
        int num2 = readInt();

        ComparableInteger a = new ComparableInteger(num1);
        ComparableInteger b = new ComparableInteger(num2);

        System.out.println("\nОбъекты для сравнения:");
        System.out.println("  A = " + a + ", B = " + b);

        int resultAB = a.compare(b);
        int resultBA = b.compare(a);

        System.out.println("\nРезультаты сравнения:");
        System.out.println("  A.compareTo(B) = " + resultAB);
        System.out.println("  B.compareTo(A) = " + resultBA);

        System.out.println("\nИнтерпретация:");
        if (resultAB > 0) {
            System.out.println("  A > B (" + a + " больше " + b + ")");
        } else if (resultAB < 0) {
            System.out.println("  A < B (" + a + " меньше " + b + ")");
        } else {
            System.out.println("  A = B (числа равны)");
        }

        if (resultBA > 0) {
            System.out.println("  B > A (" + b + " больше " + a + ")");
        } else if (resultBA < 0) {
            System.out.println("  B < A (" + b + " меньше " + a + ")");
        }

        System.out.print("\nСравнить с третьим числом? (1-да, 2-нет): ");
        if (readInt() == 1) {
            System.out.print("Введите третье целое число: ");
            int num3 = readInt();
            ComparableInteger c = new ComparableInteger(num3);

            System.out.println("\n" + "=".repeat(40));
            System.out.println("Сравнение всех трех чисел:");
            System.out.println("  A = " + a + ", B = " + b + ", C = " + c);

            System.out.println("\nПопарное сравнение:");
            System.out.println("  A.compareTo(B) = " + a.compare(b) + " → " +
                    (a.compare(b) > 0 ? "A > B" : a.compare(b) < 0 ? "A < B" : "A = B"));
            System.out.println("  A.compareTo(C) = " + a.compare(c) + " → " +
                    (a.compare(c) > 0 ? "A > C" : a.compare(c) < 0 ? "A < C" : "A = C"));
            System.out.println("  B.compareTo(C) = " + b.compare(c) + " → " +
                    (b.compare(c) > 0 ? "B > C" : b.compare(c) < 0 ? "B < C" : "B = C"));

            // Дополнительно: сортировка
            System.out.println("\nОтсортированный порядок:");
            List<ComparableInteger> list = Arrays.asList(a, b, c);
            Collections.sort(list, (x, y) -> x.compare(y));
            System.out.println("  " + list.get(0) + " ≤ " + list.get(1) + " ≤ " + list.get(2));
        }

        waitForEnter();
    }

    private static void demonstrateAll() {
        System.out.println("\n=== ВСЕ ДЕМОНСТРАЦИИ ===");
        demonstrateBoxOperations();
        demonstrateMaxInBoxes();
        demonstrateMap();
        demonstrateFilter();
        demonstrateReduce();
        demonstrateCollect();
        demonstrateArrays();
        demonstrateComparable();
        waitForEnter();
    }

    // ========== ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ДЛЯ ВВОДА ==========

    private static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Ошибка! Введите целое число: ");
            }
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Ошибка! Введите число: ");
            }
        }
    }

    private static List<Integer> readIntList() {
        while (true) {
            try {
                System.out.print("   Ввод: ");
                String input = scanner.nextLine();
                if (input.trim().isEmpty()) {
                    System.out.println("   Используем пример: [1, 2, 3]");
                    return Arrays.asList(1, 2, 3);
                }

                List<Integer> list = new ArrayList<>();
                for (String part : input.split("\\s+")) {
                    list.add(Integer.parseInt(part));
                }
                return list;
            } catch (Exception e) {
                System.out.println("   Ошибка! Введите числа через пробел.");
            }
        }
    }

    private static List<String> readStringList() {
        while (true) {
            try {
                System.out.print("   Ввод: ");
                String input = scanner.nextLine();
                if (input.trim().isEmpty()) {
                    System.out.println("   Используем пример: [яблоко, груша, банан]");
                    return Arrays.asList("яблоко", "груша", "банан");
                }

                return Arrays.asList(input.split(","));
            } catch (Exception e) {
                System.out.println("   Ошибка! Введите строки через запятую.");
            }
        }
    }

    private static int[] readIntArray() {
        while (true) {
            try {
                System.out.print("   Ввод: ");
                String input = scanner.nextLine();
                if (input.trim().isEmpty()) {
                    System.out.println("   Используем пример: [1 2 3 4 5]");
                    return new int[]{1, 2, 3, 4, 5};
                }

                String[] parts = input.split("\\s+");
                int[] array = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    array[i] = Integer.parseInt(parts[i]);
                }
                return array;
            } catch (Exception e) {
                System.out.println("   Ошибка! Введите числа через пробел.");
            }
        }
    }

    private static void waitForEnter() {
        System.out.print("\nНажмите Enter для продолжения...");
        scanner.nextLine();
    }
}