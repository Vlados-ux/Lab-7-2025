import functions.*;
import functions.basic.Cos;
import functions.basic.Sin;

public class TestPatterns {
    public static void main(String[] args) {
        System.out.println("Тестирование:\n");
        System.out.println("1.Тестирование итератора");
        testIterator();
        System.out.println();

        System.out.println("2.Тестирование фабричного метода");
        testFactoryMethod();
        System.out.println();

        System.out.println("3.Тестирование рефлексии");
        testReflection();
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

        System.out.println("\nИтераторы работают(можно использовать for-each)");
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

        System.out.println("\nФабричный метод работает(можно динамически менять тип создаваемых объектов)");
    }

    private static void testReflection() {
        System.out.println("\nДемонстрация работы рефлексии:");
        System.out.println();
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
    }
}