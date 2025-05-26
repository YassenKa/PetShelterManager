package code;

import java.io.*;
import java.util.ArrayList;

public class ShelterManager {
    private ArrayList<Pet> pets = new ArrayList<>();
    private ArrayList<Adopter> adopters = new ArrayList<>();

    // Добавяне на животно (без дубликати по име)
    public void addPet(Pet pet) {
        for (Pet p : pets) {
            if (p.getName().equalsIgnoreCase(pet.getName())) {
                System.out.println("Животно с това име вече съществува.");
                return;
            }
        }
        pets.add(pet);
    }

    // Добавяне на осиновител (без дубликати по име)
    public void addAdopter(Adopter adopter) {
        for (Adopter a : adopters) {
            if (a.getName().equalsIgnoreCase(adopter.getName())) {
                System.out.println("Осиновител с това име вече съществува.");
                return;
            }
        }
        adopters.add(adopter);
    }

    public ArrayList<Pet> getPets() {
        return pets;
    }

    public ArrayList<Adopter> getAdopters() {
        return adopters;
    }

    // Търсене на животно по име
    public Pet findPetByName(String name) {
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name)) return pet;
        }
        return null;
    }

    // Търсене на осиновител по име
    public Adopter findAdopterByName(String name) {
        for (Adopter a : adopters) {
            if (a.getName().equalsIgnoreCase(name)) return a;
        }
        return null;
    }

    // Сортиране на животните по възраст (Insertion Sort)
    public void sortPetsByAge() {
        for (int i = 1; i < pets.size(); i++) {
            Pet key = pets.get(i);
            int j = i - 1;
            while (j >= 0 && pets.get(j).getAge() > key.getAge()) {
                pets.set(j + 1, pets.get(j));
                j--;
            }
            pets.set(j + 1, key);
        }
    }

    // Записване на животните в pets.txt
    public void savePetsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("pets.txt"))) {
            for (Pet pet : pets) {
                // Записваме като: type,name,age,healthStatus
                writer.println(pet.getType() + "," + pet.getName() + "," + pet.getAge() + "," + pet.getHealthStatus());
            }
        } catch (IOException e) {
            System.out.println("Грешка при записване на pets.txt: " + e.getMessage());
        }
    }

    // Зареждане на животните от pets.txt
    public void loadPetsFromFile() {
        pets.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("pets.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String type = parts[0];
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String health = parts[3];
                    pets.add(new Pet(type, name, age, health));
                }
            }
        } catch (IOException e) {
            System.out.println("Грешка при зареждане на pets.txt: " + e.getMessage());
        }
    }

    // Записване на осиновителите в adopters.txt с имената на осиновените животни
    public void saveAdoptersToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("adopters.txt"))) {
            for (Adopter adopter : adopters) {
                StringBuilder petsNames = new StringBuilder();
                for (Pet pet : adopter.getAdoptedPets()) {
                    petsNames.append(pet.getName()).append(";");
                }
                if (petsNames.length() > 0) {
                    petsNames.setLength(petsNames.length() - 1); // махаме последната ";"
                }
                writer.println(adopter.getName() + "," + adopter.getPhone() + "," + petsNames);
            }
        } catch (IOException e) {
            System.out.println("Грешка при записване на adopters.txt: " + e.getMessage());
        }
    }

    // Зареждане на осиновителите от adopters.txt и свързване с животните
    public void loadAdoptersFromFile() {
        adopters.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("adopters.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length >= 2) {
                    String name = parts[0];
                    String phone = parts[1];
                    Adopter adopter = new Adopter(name, phone);

                    if (parts.length == 3 && !parts[2].isEmpty()) {
                        String[] petNames = parts[2].split(";");
                        for (String petName : petNames) {
                            Pet pet = findPetByName(petName);
                            if (pet != null) {
                                adopter.adoptPet(pet);
                            }
                        }
                    }
                    adopters.add(adopter);
                }
            }
        } catch (IOException e) {
            System.out.println("Грешка при зареждане на adopters.txt: " + e.getMessage());
        }
    }
}
