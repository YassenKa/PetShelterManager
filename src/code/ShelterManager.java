package code;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class ShelterManager {
    private ArrayList<Pet> pets = new ArrayList<>();
    private ArrayList<Adopter> adopters = new ArrayList<>();

    // Добавяне
    public void addPet(Pet pet) {
        pets.add(pet);
    }

    public void addAdopter(Adopter adopter) {
        adopters.add(adopter);
    }

    // Връщане на списъци
    public ArrayList<Pet> getPets() {
        return pets;
    }

    public ArrayList<Adopter> getAdopters() {
        return adopters;
    }

    // Търсене по име
    public Pet findPetByName(String name) {
        for (Pet p : pets) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    public Adopter findAdopterByName(String name) {
        for (Adopter a : adopters) {
            if (a.getName().equalsIgnoreCase(name)) return a;
        }
        return null;
    }

    // Изтриване по име
    public boolean removePetByName(String name) {
        Pet pet = findPetByName(name);
        if (pet != null) {
            pets.remove(pet);
            return true;
        }
        return false;
    }

    public boolean removeAdopterByName(String name) {
        Adopter adopter = findAdopterByName(name);
        if (adopter != null) {
            adopters.remove(adopter);
            return true;
        }
        return false;
    }

    // Редактиране на животно
    public boolean editPet(String oldName, String newType, String newName, int newAge, String newHealthStatus) {
        Pet pet = findPetByName(oldName);
        if (pet != null) {
            pet.setType(newType);
            pet.setName(newName);
            pet.setAge(newAge);
            pet.setHealthStatus(newHealthStatus);
            return true;
        }
        return false;
    }

    // Редактиране на осиновител
    public boolean editAdopter(String oldName, String newName, String newPhone) {
        Adopter adopter = findAdopterByName(oldName);
        if (adopter != null) {
            adopter.setName(newName);
            adopter.setPhone(newPhone);
            return true;
        }
        return false;
    }

    // Сортиране на животните по възраст
    public void sortPetsByAge() {
        pets.sort(Comparator.comparingInt(Pet::getAge));
    }

    // Осиновяване на животно
    public boolean adoptPet(String adopterName, String petName) {
        Adopter adopter = findAdopterByName(adopterName);
        Pet pet = findPetByName(petName);
        if (adopter != null && pet != null) {
            adopter.adoptPet(pet);
            pets.remove(pet);
            return true;
        }
        return false;
    }

    // Зареждане животни от файл
    public void loadPetsFromFile(String filename) {
        pets.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Очаква се формат: тип;име;възраст;здравословен статус
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    String type = parts[0];
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String health = parts[3];
                    pets.add(new Pet(type, name, age, health));
                }
            }
        } catch (IOException e) {
            System.out.println("Грешка при зареждане на животните: " + e.getMessage());
        }
    }

    // Запис животни във файл
    public void savePetsToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Pet p : pets) {
                bw.write(p.getType() + ";" + p.getName() + ";" + p.getAge() + ";" + p.getHealthStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Грешка при запис на животните: " + e.getMessage());
        }
    }

    // Зареждане осиновители от файл
    public void loadAdoptersFromFile(String filename) {
        adopters.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Формат: име;телефон
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    Adopter a = new Adopter(parts[0], parts[1]);
                    adopters.add(a);
                }
            }
        } catch (IOException e) {
            System.out.println("Грешка при зареждане на осиновителите: " + e.getMessage());
        }
    }

    // Запис осиновители във файл
    public void saveAdoptersToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Adopter a : adopters) {
                bw.write(a.getName() + ";" + a.getPhone());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Грешка при запис на осиновителите: " + e.getMessage());
        }
    }
}
