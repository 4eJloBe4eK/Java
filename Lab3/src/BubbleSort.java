import java.util.Arrays;
import java.util.Scanner;

public class BubbleSort {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Получение размера массива
        System.out.print("Введите размер массива: ");
        int size = scanner.nextInt();

        // Инициализация массива
        int[] array = new int[size];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < size; i++) {
            System.out.print("Элемент " + (i + 1) + ": ");
            array[i] = scanner.nextInt();
        }

        // Сортировка массива методом пузырька
        bubbleSort(array);

        // Вывод отсортированного массива
        System.out.println("Отсортированный массив: " + Arrays.toString(array));

        scanner.close();
    }

    /**
     * Сортировка массива методом пузырька
     * @param array массив для сортировки
     */
    public static void bubbleSort(int[] array) {
        boolean sorted;

        // Внешний цикл уменьшает область проверки с каждым шагом
        for (int i = 0; i < array.length - 1; i++) {
            sorted = true; // Предполагаем, что массив отсортирован

            // Внутренний цикл выполняет обмен соседних элементов
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1); // Меняем местами элементы
                    sorted = false; // Если был обмен, массив не отсортирован
                }
            }

            // Если ни одного обмена не произошло, сортировка завершена
            if (sorted) {
                break;
            }
        }
    }

    /**
     * Метод для обмена элементов массива
     * @param array массив, в котором производится обмен
     * @param i индекс первого элемента
     * @param j индекс второго элемента
     */
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
