import functions.*;
import functions.basic.Cos;
import functions.basic.Sin;

import java.io.*;

public class TestPatterns {
    public static void main(String[] args) {
        System.out.println("Тестирование:\n");
        System.out.println("1. Тестирование итератора");
        testIterator();
        System.out.println();

        System.out.println("2. Тестирование фабричного метода");
        testFactoryMethod();
        System.out.println();

        System.out.println("3. Тестирование рефлексии");
        testReflection();
        System.out.println();

        System.out.println("4. Тестирование чтения с рефлексией");
        testReflectionIO();
        System.out.println();

        System.out.println("Все тесты завершены успешно");
    }

    private static void testIterator() {
        System.out.println("\nТестирование итератора для ArrayTabulatedFunction:");
        TabulatedFunction arrayFunc = new ArrayTabulatedFunction(
                0, 10, new double[]{0, 1, 4, 9, 16, 25});

        System.out.println("Создана функция: f(x) = x² на [0, 10] с 6 точками");
        System.out.println("Точки функции:");
        int pointNumber = 1;
        for (FunctionPoint point : arrayFunc) {
            System.out.printf("   Точка %d: %s%n", pointNumber++, point);
        }

        System.out.println("\nТестирование итератора для LinkedListTabulatedFunction:");
        TabulatedFunction listFunc = new LinkedListTabulatedFunction(
                0, 5, new double[]{0, 1, 2, 3, 4, 5});

        System.out.println("Создана функция: f(x) = x на [0, 5] с 6 точками");
        System.out.println("Точки функции:");

        pointNumber = 1;
        for (FunctionPoint point : listFunc) {
            System.out.printf("   Точка %d: %s%n", pointNumber++, point);
        }

        System.out.println("\nИтераторы работают (можно использовать for-each)");
    }

    private static void testFactoryMethod() {
        System.out.println("\nДемонстрация работы фабричного метода:");
        Function cosFunction = new Cos();
        TabulatedFunction tabulatedFunc;

        System.out.println("\nФабрика по умолчанию (должна создавать ArrayTabulatedFunction):");
        tabulatedFunc = TabulatedFunctions.tabulate(cosFunction, 0, Math.PI, 11);
        System.out.println("Создана tabulate(f, 0, π, 11)");
        System.out.println("Тип созданного объекта: " + tabulatedFunc.getClass().getSimpleName());
        System.out.println("Количество точек: " + tabulatedFunc.getPointsCount());

        System.out.println("\nМеняем фабрику на LinkedListTabulatedFunctionFactory:");
        TabulatedFunctions.setTabulatedFunctionFactory(
                new LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
        tabulatedFunc = TabulatedFunctions.tabulate(cosFunction, 0, Math.PI, 11);
        System.out.println("Создана tabulate(f, 0, π, 11) с новой фабрикой");
        System.out.println("Тип созданного объекта: " + tabulatedFunc.getClass().getSimpleName());
        System.out.println("Количество точек: " + tabulatedFunc.getPointsCount());

        System.out.println("\nВозвращаем фабрику обратно на ArrayTabulatedFunctionFactory:");
        TabulatedFunctions.setTabulatedFunctionFactory(
                new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
        tabulatedFunc = TabulatedFunctions.tabulate(cosFunction, 0, Math.PI, 11);
        System.out.println("Создана tabulate(f, 0, π, 11) с восстановленной фабрикой");
        System.out.println("Тип созданного объекта: " + tabulatedFunc.getClass().getSimpleName());

        System.out.println("\nФабричный метод работает (можно динамически менять тип создаваемых объектов)");
    }

    private static void testReflection() {
        System.out.println("\nДемонстрация работы рефлексии:");
        TabulatedFunction reflectedFunc;

        System.out.println("\nСоздание ArrayTabulatedFunction через рефлексию:");
        reflectedFunc = TabulatedFunctions.createTabulatedFunction(
                ArrayTabulatedFunction.class,
                0,
                10,
                3
        );
        System.out.println("Вызов: createTabulatedFunction(ArrayTabulatedFunction.class, 0, 10, 3)");
        System.out.println("Результат: " + reflectedFunc);
        System.out.println("Тип: " + reflectedFunc.getClass().getSimpleName());

        System.out.println("\nСоздание ArrayTabulatedFunction с массивом значений:");
        reflectedFunc = TabulatedFunctions.createTabulatedFunction(
                ArrayTabulatedFunction.class,
                0,
                10,
                new double[]{0, 10}
        );
        System.out.println("Вызов: createTabulatedFunction(ArrayTabulatedFunction.class, 0, 10, new double[]{0, 10})");
        System.out.println("Результат: " + reflectedFunc);
        System.out.println("Тип: " + reflectedFunc.getClass().getSimpleName());

        System.out.println("\nСоздание LinkedListTabulatedFunction с массивом точек:");
        reflectedFunc = TabulatedFunctions.createTabulatedFunction(
                LinkedListTabulatedFunction.class,
                new FunctionPoint[]{
                        new FunctionPoint(0, 0),
                        new FunctionPoint(10, 10)
                }
        );
        System.out.println("Вызов: createTabulatedFunction(LinkedListTabulatedFunction.class, points[])");
        System.out.println("Результат: " + reflectedFunc);
        System.out.println("Тип: " + reflectedFunc.getClass().getSimpleName());

        System.out.println("\nИспользование tabulate() с рефлексией:");
        Function sinFunction = new Sin();

        reflectedFunc = TabulatedFunctions.tabulate(
                LinkedListTabulatedFunction.class,
                sinFunction,
                0,
                Math.PI,
                11
        );
        System.out.println("Вызов: tabulate(LinkedListTabulatedFunction.class, sin, 0, π, 11)");
        System.out.println("Тип: " + reflectedFunc.getClass().getSimpleName());
        System.out.println("Количество точек: " + reflectedFunc.getPointsCount());

        System.out.println("\nДемонстрация обработки ошибок рефлексии:");
        try {
            Class wrongClass = String.class;

            reflectedFunc = TabulatedFunctions.createTabulatedFunction(
                    wrongClass,
                    0,
                    10,
                    3
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("Причина: " + e.getCause().getClass().getSimpleName());
            }
        }

        System.out.println("\nРефлексия работает (можно создавать объекты по имени класса)");
    }

    private static void testReflectionIO() {
        System.out.println("\nТестирование методов чтения с рефлексией:");

        TabulatedFunction originalFunc = new ArrayTabulatedFunction(
                0, 10, new double[]{0, 1, 4, 9, 16, 25});

        System.out.println("\nИсходная функция: " + originalFunc);

        try {
            System.out.println("\nЧтение из бинарного потока:");

            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            TabulatedFunctions.outputTabulatedFunction(originalFunc, byteOut);
            byte[] binaryData = byteOut.toByteArray();

            ByteArrayInputStream byteIn1 = new ByteArrayInputStream(binaryData);
            TabulatedFunction readArrayFunc = TabulatedFunctions.inputTabulatedFunction(
                    ArrayTabulatedFunction.class, byteIn1);
            System.out.println("   Прочитан как ArrayTabulatedFunction: " +
                    readArrayFunc.getClass().getSimpleName());
            System.out.println("   Результат: " + readArrayFunc);

            ByteArrayInputStream byteIn2 = new ByteArrayInputStream(binaryData);
            TabulatedFunction readListFunc = TabulatedFunctions.inputTabulatedFunction(
                    LinkedListTabulatedFunction.class, byteIn2);
            System.out.println("   Прочитан как LinkedListTabulatedFunction: " +
                    readListFunc.getClass().getSimpleName());
            System.out.println("   Результат: " + readListFunc);

            System.out.println("\nЧтение из текстового потока:");

            StringWriter writer = new StringWriter();
            TabulatedFunctions.writeTabulatedFunction(originalFunc, writer);
            String textData = writer.toString();

            System.out.println("    Текстовые данные: \"" + textData + "\"");

            StringReader reader = new StringReader(textData);
            TabulatedFunction textReadFunc = TabulatedFunctions.readTabulatedFunction(
                    LinkedListTabulatedFunction.class, reader);
            System.out.println("   Прочитан из текста как LinkedListTabulatedFunction: " +
                    textReadFunc.getClass().getSimpleName());
            System.out.println("   Результат: " + textReadFunc);

            System.out.println("\nСравнение функций:");
            System.out.println("   Исходная и прочитанная (Array) идентичны: " +
                    originalFunc.equals(readArrayFunc));
            System.out.println("   Исходная и прочитанная (List) идентичны: " +
                    originalFunc.equals(readListFunc));
            System.out.println("   Array и List представления идентичны: " +
                    readArrayFunc.equals(readListFunc));

            System.out.println("\nДемонстрация обработки ошибок:");
            try {
                ByteArrayInputStream byteIn3 = new ByteArrayInputStream(new byte[]{1, 2, 3});
                TabulatedFunctions.inputTabulatedFunction(
                        ArrayTabulatedFunction.class, byteIn3);
            } catch (RuntimeException e) {
                System.out.println("Поймано исключение (ожидаемо): " + e.getClass().getSimpleName());
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\nМетоды чтения с рефлексией работают");
        System.out.println("Можно указать класс объекта при чтении из потока");
    }
}