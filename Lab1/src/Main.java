import java.util.*;

public class Main {
    // Базовый класс для самолёта
    static abstract class Airplane {
        String flightName; // Название рейса
        int passengers;    // Количество пассажиров
        double fuel;       // Количество топлива
        String type;       // Тип самолёта

        public Airplane(String flightName, int passengers, double fuel, String type) {
            this.flightName = flightName;
            this.passengers = passengers;
            this.fuel = fuel;
            this.type = type;
        }

        // Метод для расчёта расхода топлива
        public double calculateFuelConsumption(double distance) {
            return (fuel / passengers) * distance; // Примерная логика расчёта
        }

        // Вывод информации о самолёте
        public void displayInfo() {
            System.out.println("Название рейса: " + flightName);
            System.out.println("Количество пассажиров: " + passengers);
            System.out.println("Количество топлива: " + fuel);
            System.out.println("Тип самолёта: " + type);
            System.out.println();
        }
    }

    // Классы для конкретных типов самолётов
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

    // Класс репозитория
    static class AirplaneRepository {
        private final List<Airplane> airplanes = new ArrayList<>();

        public void addAirplane(Airplane airplane) {
            airplanes.add(airplane);
            System.out.println("Самолёт успешно добавлен.");
        }

        public boolean removeAirplane(String flightName) {
            return airplanes.removeIf(a -> a.flightName.equalsIgnoreCase(flightName));
        }

        public boolean updateAirplane(String flightName, int passengers, double fuel) {
            for (Airplane airplane : airplanes) {
                if (airplane.flightName.equalsIgnoreCase(flightName)) {
                    airplane.passengers = passengers;
                    airplane.fuel = fuel;
                    System.out.println("Данные самолёта обновлены.");
                    return true;
                }
            }
            return false;
        }

        public List<Airplane> getAllAirplanes() {
            return new ArrayList<>(airplanes);
        }

        public List<Airplane> findAirplanesByPassengerCount(int maxPassengers) {
            return airplanes.stream()
                    .filter(a -> a.passengers < maxPassengers)
                    .toList();
        }

        public List<Airplane> findAirplanesByFirstLetter(char letter) {
            return airplanes.stream()
                    .filter(a -> a.flightName.toUpperCase().charAt(0) == Character.toUpperCase(letter))
                    .toList();
        }
    }

    // Вспомогательные методы для взаимодействия с пользователем
    private static Airplane createAirplaneFromInput(Scanner scanner) {
        System.out.print("Введите название рейса: ");
        String flightName = scanner.nextLine();
        System.out.print("Введите количество пассажиров: ");
        int passengers = scanner.nextInt();
        System.out.print("Введите количество топлива: ");
        double fuel = scanner.nextDouble();
        scanner.nextLine(); // Очистка буфера

        System.out.print("Введите тип самолёта (пассажирский/грузовой/военный): ");
        String type = scanner.nextLine().toLowerCase();

        return switch (type) {
            case "пассажирский" -> new PassengerPlane(flightName, passengers, fuel);
            case "грузовой" -> new CargoPlane(flightName, passengers, fuel);
            case "военный" -> new MilitaryPlane(flightName, passengers, fuel);
            default -> {
                System.out.println("Неверный тип самолёта.");
                yield null;
            }
        };
    }

    private static void displayAirplanes(List<Airplane> airplanes) {
        if (airplanes.isEmpty()) {
            System.out.println("Список самолётов пуст.");
        } else {
            airplanes.forEach(Airplane::displayInfo);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AirplaneRepository repository = new AirplaneRepository();

        while (true) {
            System.out.println();
            System.out.println("------------Меню------------");
            System.out.println("1. Добавить самолёт");
            System.out.println("2. Просмотреть все самолёты");
            System.out.println("3. Просмотреть самолёты с количеством пассажиров меньше заданного");
            System.out.println("4. Просмотреть самолёты, название которых начинается с заданной буквы");
            System.out.println("5. Удалить самолёт по названию");
            System.out.println("6. Изменить данные о самолёте");
            System.out.println("7. Выход");
            System.out.print("Выберите действие от 1 до 7: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера

                switch (choice) {
                    case 1 -> {
                        Airplane airplane = createAirplaneFromInput(scanner);
                        if (airplane != null) {
                            repository.addAirplane(airplane);
                        }
                    }
                    case 2 -> displayAirplanes(repository.getAllAirplanes());
                    case 3 -> {
                        System.out.print("Введите пороговое количество пассажиров: ");
                        int maxPassengers = scanner.nextInt();
                        displayAirplanes(repository.findAirplanesByPassengerCount(maxPassengers));
                    }
                    case 4 -> {
                        System.out.print("Введите букву для фильтрации: ");
                        char letter = scanner.nextLine().charAt(0);
                        displayAirplanes(repository.findAirplanesByFirstLetter(letter));
                    }
                    case 5 -> {
                        System.out.print("Введите название рейса для удаления: ");
                        String deleteName = scanner.nextLine();
                        if (repository.removeAirplane(deleteName)) {
                            System.out.println("Самолёт удалён.");
                        } else {
                            System.out.println("Самолёт с таким названием не найден.");
                        }
                    }
                    case 6 -> {
                        System.out.print("Введите название рейса для изменения: ");
                        String updateName = scanner.nextLine();
                        System.out.print("Введите новое количество пассажиров: ");
                        int newPassengers = scanner.nextInt();
                        System.out.print("Введите новое количество топлива: ");
                        double newFuel = scanner.nextDouble();
                        if (!repository.updateAirplane(updateName, newPassengers, newFuel)) {
                            System.out.println("Самолёт с таким названием не найден.");
                        }
                    }
                    case 7 -> {
                        System.out.println("Выход...");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Неверный выбор. Пожалуйста, выберите от 1 до 7.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Пожалуйста, введите число.");
                scanner.nextLine(); // Очистка буфера
            }
        }
    }
}