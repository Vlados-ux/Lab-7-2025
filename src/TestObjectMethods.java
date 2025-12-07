import functions.*;
import functions.basic.*;
import java.util.Arrays;

public class TestObjectMethods {
    public static void main(String[] args) {
        System.out.println("Тестирование переопределенных методов");

        testFunctionPointMethods();
        testArrayTabulatedFunctionMethods();
        testLinkedListTabulatedFunctionMethods();
        testCrossClassEquality();

        System.out.println("\nКонец тестирования");
    }

    private static void testFunctionPointMethods() {
        System.out.println("\nТестирование FunctionPoint:");

        FunctionPoint p1 = new FunctionPoint(1.0, 2.0);
        FunctionPoint p2 = new FunctionPoint(1.0, 2.0);
        FunctionPoint p3 = new FunctionPoint(1.0, 3.0);
        FunctionPoint p4 = new FunctionPoint(2.0, 2.0);

        System.out.println("toString(): " + p1.toString());

        System.out.println("p1.equals(p2): " + p1.equals(p2) + " (ожидается: true)");
        System.out.println("p1.equals(p3): " + p1.equals(p3) + " (ожидается: false)");
        System.out.println("p1.equals(p4): " + p1.equals(p4) + " (ожидается: false)");

        System.out.println("p1.hashCode(): " + p1.hashCode());
        System.out.println("p2.hashCode(): " + p2.hashCode());
        System.out.println("p1.hashCode() == p2.hashCode(): " +
                (p1.hashCode() == p2.hashCode()) + " (ожидается: true)");

        FunctionPoint p1Clone = (FunctionPoint) p1.clone();
        System.out.println("p1.clone().equals(p1): " + p1Clone.equals(p1) + " (ожидается: true)");
        System.out.println("p1 == p1.clone(): " + (p1 == p1Clone) + " (ожидается: false)");
    }

    private static void testArrayTabulatedFunctionMethods() {
        System.out.println("\nТестирование ArrayTabulatedFunction:");

        FunctionPoint[] points1 = {
                new FunctionPoint(0, 0),
                new FunctionPoint(1, 1),
                new FunctionPoint(2, 4)
        };

        FunctionPoint[] points2 = {
                new FunctionPoint(0, 0),
                new FunctionPoint(1, 1),
                new FunctionPoint(2, 4)
        };

        FunctionPoint[] points3 = {
                new FunctionPoint(0, 0),
                new FunctionPoint(1, 2), // отличается
                new FunctionPoint(2, 4)
        };

        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(points1);
        ArrayTabulatedFunction func2 = new ArrayTabulatedFunction(points2);
        ArrayTabulatedFunction func3 = new ArrayTabulatedFunction(points3);

        System.out.println("func1.toString(): " + func1.toString());

        System.out.println("func1.equals(func2): " + func1.equals(func2) + " (ожидается: true)");
        System.out.println("func1.equals(func3): " + func1.equals(func3) + " (ожидается: false)");

        System.out.println("func1.hashCode(): " + func1.hashCode());
        System.out.println("func2.hashCode(): " + func2.hashCode());
        System.out.println("func1.hashCode() == func2.hashCode(): " +
                (func1.hashCode() == func2.hashCode()) + " (ожидается: true)");

        ArrayTabulatedFunction func1Clone = (ArrayTabulatedFunction) func1.clone();
        System.out.println("func1.equals(func1Clone): " + func1.equals(func1Clone) + " (ожидается: true)");
        System.out.println("func1 == func1Clone: " + (func1 == func1Clone) + " (ожидается: false)");

        func1.setPointY(1, 100);
        System.out.println("После изменения оригинала - func1.equals(func1Clone): " +
                func1.equals(func1Clone) + " (ожидается: false)");
    }

    private static void testLinkedListTabulatedFunctionMethods() {
        System.out.println("\nТестирование LinkedListTabulatedFunction:");

        FunctionPoint[] points = {
                new FunctionPoint(0, 0),
                new FunctionPoint(1, 1),
                new FunctionPoint(2, 4)
        };

        LinkedListTabulatedFunction func1 = new LinkedListTabulatedFunction(points);
        LinkedListTabulatedFunction func2 = new LinkedListTabulatedFunction(points);

        System.out.println("func1.toString(): " + func1.toString());

        System.out.println("func1.equals(func2): " + func1.equals(func2) + " (ожидается: true)");

        System.out.println("func1.hashCode(): " + func1.hashCode());
        System.out.println("func2.hashCode(): " + func2.hashCode());

        LinkedListTabulatedFunction func1Clone = (LinkedListTabulatedFunction) func1.clone();
        System.out.println("func1.equals(func1Clone): " + func1.equals(func1Clone) + " (ожидается: true)");

        func1.setPointY(1, 100);
        System.out.println("После изменения оригинала - func1.equals(func1Clone): " +
                func1.equals(func1Clone) + " (ожидается: false)");
    }

    private static void testCrossClassEquality() {
        System.out.println("\nТестирование межклассового сравнения:");

        FunctionPoint[] points = {
                new FunctionPoint(0, 0),
                new FunctionPoint(1, 1),
                new FunctionPoint(2, 4)
        };

        ArrayTabulatedFunction arrayFunc = new ArrayTabulatedFunction(points);
        LinkedListTabulatedFunction linkedListFunc = new LinkedListTabulatedFunction(points);

        System.out.println("arrayFunc.equals(linkedListFunc): " +
                arrayFunc.equals(linkedListFunc) + " (ожидается: true)");
        System.out.println("linkedListFunc.equals(arrayFunc): " +
                linkedListFunc.equals(arrayFunc) + " (ожидается: true)");

        FunctionPoint[] differentPoints = {
                new FunctionPoint(0, 0),
                new FunctionPoint(1, 2),
                new FunctionPoint(2, 4)
        };

        LinkedListTabulatedFunction differentFunc = new LinkedListTabulatedFunction(differentPoints);
        System.out.println("arrayFunc.equals(differentFunc): " +
                arrayFunc.equals(differentFunc) + " (ожидается: false)");
    }
}
