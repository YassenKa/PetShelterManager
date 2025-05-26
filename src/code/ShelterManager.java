package code;

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
}