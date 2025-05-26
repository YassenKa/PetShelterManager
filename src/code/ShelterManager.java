package code;

import java.io.*;
import java.util.ArrayList;

public class ShelterManager {
    private ArrayList<Pet> pets = new ArrayList<>();
    private ArrayList<Adopter> adopters = new ArrayList<>();

    public void addPet(Pet pet) {
        for (Pet p : pets) {
            if (p.getName().equalsIgnoreCase(pet.getName())) {
                System.out.println("Животно с това име вече съществува.");
                return;
            }
        }
        pets.add(pet);
    }

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

    public Pet findPetByName(String name) {
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name)) return pet;
        }
        return null;
    }

    public Adopter findAdopterByName(String name) {
        for (Adopter a : adopters) {
            if (a.getName().equalsIgnoreCase(name)) return a;
        }
        return null;
    }

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

    // Нова добавена функция: осиновяване на животно от осиновител
    public boolean adoptPet(String adopterName, String petName) {
        Adopter adopter = findAdopterByName(adopterName);
        Pet pet = findPetByName(petName);
        if (adopter == null) {
            System.out.println("Няма осиновител с име " + adopterName);
            return false;
        }
        if (pet == null) {
            System.out.println("Няма животно с име " + petName);
            return false;
        }
        adopter.adoptPet(pet);
        pets.remove(pet); // премахваме животното от приюта, защото вече е осиновено
        System.out.println("Животното " + petName + " е осиновено от " + adopterName);
        return true;
    }

    // Запис на животните в текстов файл
    public void savePetsToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Pet p : pets) {
                // Формат: type;name;age;healthStatus
                pw.println(p.getType() + ";" + p.getName() + ";" + p.getAge() + ";" + p.getHealthStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Зареждане на животните от файл
    public void loadPetsFromFile(String filename) {
        pets.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    String type = parts[0];
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String healthStatus = parts[3];
                    pets.add(new Pet(type, name, age, healthStatus));
                }
            }
        } catch (IOException e) {
            // Ако файлът не съществува, няма проблем, просто започваме с празен списък
        }
    }

    // Запис на осиновителите в текстов файл
    public void saveAdoptersToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Adopter a : adopters) {
                // Формат: name;phone;adoptedPetNames (като списък, разделен със запетая)
                StringBuilder petsNames = new StringBuilder();
                for (Pet p : a.getAdoptedPets()) {
                    if (petsNames.length() > 0) petsNames.append(",");
                    petsNames.append(p.getName());
                }
                pw.println(a.getName() + ";" + a.getPhone() + ";" + petsNames.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Зареждане на осиновителите от файл
    public void loadAdoptersFromFile(String filename) {
        adopters.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", 3);
                if (parts.length >= 2) {
                    String name = parts[0];
                    String phone = parts[1];
                    Adopter adopter = new Adopter(name, phone);
                    if (parts.length == 3 && !parts[2].isEmpty()) {
                        String[] petNames = parts[2].split(",");
                        for (String petName : petNames) {
                            Pet pet = findPetByName(petName.trim());
                            if (pet != null) {
                                adopter.adoptPet(pet);
                            }
                        }
                    }
                    adopters.add(adopter);
                }
            }
        } catch (IOException e) {
            // Ако файлът не съществува, няма проблем
        }
    }
}
