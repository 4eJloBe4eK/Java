import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BubbleSortGUI {

    private final List<Integer> array = new ArrayList<>();
    private final JTextArea displayArea;

    public BubbleSortGUI() {
        JFrame frame = new JFrame("Управление массивом");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Создание текстовой области для отображения массива
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Панель для кнопок
        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.SOUTH);

        // Кнопки для действий
        JButton addButton = new JButton("Добавить элемент");
        JButton removeButton = new JButton("Удалить элемент");
        JButton updateButton = new JButton("Обновить элемент");
        JButton sortButton = new JButton("Отсортировать массив");
        JButton clearButton = new JButton("Очистить массив");

        panel.add(addButton);
        panel.add(removeButton);
        panel.add(updateButton);
        panel.add(sortButton); // Кнопка для сортировки массива
        panel.add(clearButton);

        // Обработчики событий для кнопок
        addButton.addActionListener(e -> addElement());
        removeButton.addActionListener(e -> removeElement());
        updateButton.addActionListener(e -> updateElement());
        sortButton.addActionListener(e -> sortArray()); // Обработчик сортировки
        clearButton.addActionListener(e -> clearArray());

        frame.setVisible(true);
    }

    // Метод для добавления элемента
    private void addElement() {
        try {
            String input = JOptionPane.showInputDialog("Введите число для добавления:");
            if (input != null) {
                int element = Integer.parseInt(input);
                array.add(element);
                displayArray();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Введите корректное число.");
        }
    }

    // Метод для удаления элемента
    private void removeElement() {
        try {
            String input = JOptionPane.showInputDialog("Введите индекс элемента для удаления (от 1 до " + array.size() + "):");
            if (input != null) {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < array.size()) {
                    array.remove(index);
                    displayArray();
                } else {
                    JOptionPane.showMessageDialog(null, "Некорректный индекс.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Введите корректный индекс.");
        }
    }

    // Метод для обновления элемента
    private void updateElement() {
        try {
            String indexInput = JOptionPane.showInputDialog("Введите индекс элемента для обновления (от 1 до " + array.size() + "):");
            if (indexInput != null) {
                int index = Integer.parseInt(indexInput) - 1;
                if (index >= 0 && index < array.size()) {
                    String valueInput = JOptionPane.showInputDialog("Введите новое значение:");
                    if (valueInput != null) {
                        int newValue = Integer.parseInt(valueInput);
                        array.set(index, newValue);
                        displayArray();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Некорректный индекс.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Введите корректные данные.");
        }
    }

    // Метод для сортировки массива
    private void sortArray() {
        bubbleSort(array);
        displayArray();
    }

    // Метод для очистки массива
    private void clearArray() {
        array.clear();
        displayArray();
    }

    // Метод для отображения массива
    private void displayArray() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            sb.append("[").append(i + 1).append("]: ").append(array.get(i)).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    // Реализация пузырьковой сортировки
    private void bubbleSort(List<Integer> array) {
        boolean sorted;
        for (int i = 0; i < array.size() - 1; i++) {
            sorted = true;
            for (int j = 0; j < array.size() - 1 - i; j++) {
                if (array.get(j) > array.get(j + 1)) {
                    int temp = array.get(j);
                    array.set(j, array.get(j + 1));
                    array.set(j + 1, temp);
                    sorted = false;
                }
            }
            if (sorted) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BubbleSortGUI::new);
    }
}