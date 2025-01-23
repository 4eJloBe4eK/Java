import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AirplaneGUI  {  // Базовые классы самолётов
    static abstract class Airplane {
        String flightName;
        int passengers;
        double fuel;
        String type;

        public Airplane(String flightName, int passengers, double fuel, String type) {
            this.flightName = flightName;
            this.passengers = passengers;
            this.fuel = fuel;
            this.type = type;
        }

        @Override
        public String toString() {
            return "Рейс: " + flightName + ", Пассажиры: " + passengers + ", Топливо: " + fuel + ", Тип: " + type;
        }
    }

    static class PassengerPlane extends Airplane {
        public PassengerPlane(String flightName, int passengers, double fuel) {
            super(flightName, passengers, fuel, "Пассажирский");
        }
    }

    static class CargoPlane extends Airplane {
        public CargoPlane(String flightName, int passengers, double fuel) {
            super(flightName, passengers, fuel, "Грузовой");
        }
    }

    static class MilitaryPlane extends Airplane {
        public MilitaryPlane(String flightName, int passengers, double fuel) {
            super(flightName, passengers, fuel, "Военный");
        }
    }

    // Репозиторий самолётов
    static class AirplaneRepository {
        private List<Airplane> airplanes;

        public AirplaneRepository() {
            this.airplanes = new ArrayList<>();
        }

        public void addAirplane(Airplane airplane) {
            airplanes.add(airplane);
        }

        public void removeAirplane(int index) {
            if (index >= 0 && index < airplanes.size()) {
                airplanes.remove(index);
            } else {
                JOptionPane.showMessageDialog(null, "Некорректный индекс.");
            }
        }

        public void updateAirplane(int index, Airplane airplane) {
            if (index >= 0 && index < airplanes.size()) {
                airplanes.set(index, airplane);
            } else {
                JOptionPane.showMessageDialog(null, "Некорректный индекс.");
            }
        }

        public List<Airplane> getAirplanes() {
            return airplanes;
        }
    }

    private AirplaneRepository airplaneRepository = new AirplaneRepository();

    public AirplaneGUI() {
        JFrame frame = new JFrame("Управление Самолётами");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.SOUTH);

        JButton addButton = new JButton("Добавить самолёт");
        JButton viewAllButton = new JButton("Просмотреть все самолёты");
        JButton updateButton = new JButton("Обновить самолёт");
        JButton removeButton = new JButton("Удалить самолёт");

        panel.add(addButton);
        panel.add(viewAllButton);
        panel.add(updateButton);
        panel.add(removeButton);

        addButton.addActionListener(e -> addAirplane());
        viewAllButton.addActionListener(e -> viewAllAirplanes(displayArea));
        updateButton.addActionListener(e -> updateAirplane(displayArea));
        removeButton.addActionListener(e -> removeAirplane(displayArea));

        frame.setVisible(true);
    }

    private void addAirplane() {
        Airplane airplane = createAirplaneFromInput();
        if (airplane != null) {
            airplaneRepository.addAirplane(airplane);
        }
    }

    private void updateAirplane(JTextArea displayArea) {
        try {
            int indexToUpdate = Integer.parseInt(JOptionPane.showInputDialog("Введите индекс для обновления:"));
            Airplane updatedAirplane = createAirplaneFromInput();
            if (updatedAirplane != null) {
                airplaneRepository.updateAirplane(indexToUpdate - 1, updatedAirplane);
                viewAllAirplanes(displayArea);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Пожалуйста, введите корректный индекс.");
        }
    }

    private void removeAirplane(JTextArea displayArea) {
        try {
            int indexToRemove = Integer.parseInt(JOptionPane.showInputDialog("Введите индекс для удаления:"));
            airplaneRepository.removeAirplane(indexToRemove - 1);
            viewAllAirplanes(displayArea);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Пожалуйста, введите корректный индекс.");
        }
    }

    private void viewAllAirplanes(JTextArea displayArea) {
        StringBuilder sb = new StringBuilder();
        List<Airplane> airplanes = airplaneRepository.getAirplanes();
        for (int i = 0; i < airplanes.size(); i++) {
            sb.append("Индекс: ").append(i + 1).append("\n")
                    .append(airplanes.get(i).toString()).append("\n\n");
        }
        displayArea.setText(sb.toString());
    }

    private Airplane createAirplaneFromInput() {
        try {
            String flightName = JOptionPane.showInputDialog("Введите название рейса:");
            int passengers = Integer.parseInt(JOptionPane.showInputDialog("Введите количество пассажиров:"));
            double fuel = Double.parseDouble(JOptionPane.showInputDialog("Введите количество топлива:"));

            String type = JOptionPane.showInputDialog("Введите тип самолёта (пассажирский/грузовой/военный):").toLowerCase();
            return switch (type) {
                case "пассажирский" -> new PassengerPlane(flightName, passengers, fuel);
                case "грузовой" -> new CargoPlane(flightName, passengers, fuel);
                case "военный" -> new MilitaryPlane(flightName, passengers, fuel);
                default -> {
                    JOptionPane.showMessageDialog(null, "Неверный тип самолёта.");
                    yield null;
                }
            };
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ошибка ввода. Пожалуйста, введите корректные данные.");
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AirplaneGUI::new);
    }
}