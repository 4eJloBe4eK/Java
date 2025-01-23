package lab1;

import java.util.*;

public class Main {
    // Базовый класс для самолёта
    static class Airplane {
        String flightName;   // Название рейса
        int passengers;      // Количество пассажиров
        double fuel;         // Количество топлива
        String type;         // Тип самолёта

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

    // Класс для пассажирского самолёта
    static class PassengerPlane extends Airplane {
        public PassengerPlane(String flightName, int passengers, double fuel) {
            super(flightName, passengers, fuel, "Пассажирский");
        }
    }

    // Класс для грузового самолёта
    static class CargoPlane extends Airplane {
        public CargoPlane(String flightName, int passengers, double fuel) {
            super(flightName, passengers, fuel, "Грузовой");
        }
    }

    // Класс для военного самолёта
    static class MilitaryPlane extends Airplane {
        public MilitaryPlane(String flightName, int passengers, double fuel) {
            super(flightName, passengers, fuel, "Военный");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Airplane> airplaneList = new ArrayList<>();

        int choice = 0;
        do {
            System.out.println();
            System.out.println("------------Меню------------");
            System.out.println("1. Добавить самолёт");
            System.out.println("2. Просмотреть все самолёты");
            System.out.println("3. Просмотреть самолёты с количеством пассажиров меньше заданного");
            System.out.println("4. Просмотреть самолёты, название которых начинается с заданной буквы");
            System.out.println("5. Выход");
            System.out.print("Выберите действие от 1 до 5: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера после nextInt()

                switch (choice) {
                    case 1:
                        System.out.print("Введите название рейса: ");
                        String flightName = scanner.nextLine();
                        System.out.print("Введите количество пассажиров: ");
                        int passengers = scanner.nextInt();
                        System.out.print("Введите количество топлива: ");
                        double fuel = scanner.nextDouble();
                        scanner.nextLine(); // Очистка буфера

                        // Определение типа самолёта
                        System.out.print("Введите тип самолёта (пассажирский/грузовой/военный): ");
                        String type = scanner.nextLine().toLowerCase();
                        Airplane airplane;
                        switch (type) {
                            case "пассажирский":
                                airplane = new PassengerPlane(flightName, passengers, fuel);
                                break;
                            case "грузовой":
                                airplane = new CargoPlane(flightName, passengers, fuel);
                                break;
                            case "военный":
                                airplane = new MilitaryPlane(flightName, passengers, fuel);
                                break;
                            default:
                                System.out.println("Неверный тип самолёта.");
                                continue;
                        }
                        airplaneList.add(airplane);
                        break;

                    case 2:
                        if (airplaneList.isEmpty()) {
                            System.out.println("Список самолётов пуст.");
                        } else {
                            for (Airplane a : airplaneList) {
                                a.displayInfo();
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Введите пороговое количество пассажиров: ");
                        int threshold = scanner.nextInt();
                        if (airplaneList.isEmpty()) {
                            System.out.println("Список самолётов пуст.");
                        } else {
                            System.out.println("Самолёты с количеством пассажиров меньше " + threshold + ":");
                            boolean found = false;
                            for (Airplane a : airplaneList) {
                                if (a.passengers < threshold) {
                                    a.displayInfo();
                                    found = true;
                                }
                            }
                            if (!found) {
                                System.out.println("Нет самолётов с количеством пассажиров меньше " + threshold + ".");
                            }
                        }
                        break;

                    case 4:
                        System.out.print("Введите букву для фильтрации: ");
                        char letter = scanner.nextLine().toUpperCase().charAt(0);
                        if (airplaneList.isEmpty()) {
                            System.out.println("Список самолётов пуст.");
                        } else {
                            System.out.println("Самолёты, название которых начинается на букву '" + letter + "':");
                            boolean found = false;
                            for (Airplane a : airplaneList) {
                                if (a.flightName.toUpperCase().charAt(0) == letter) {
                                    a.displayInfo();
                                    found = true;
                                }
                            }
                            if (!found) {
                                System.out.println("Нет самолётов, название которых начинается с буквы '" + letter + "'.");
                            }
                        }
                        break;

                    case 5:
                        System.out.println("Выход...");
                        break;

                    default:
                        System.out.println("Неверный выбор. Пожалуйста, выберите от 1 до 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Пожалуйста, введите число.");
                scanner.nextLine(); // Очистка буфера
            }
        } while (choice != 5);

        scanner.close();
    }
}