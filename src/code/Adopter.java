package code;

import java.util.ArrayList;

public class Adopter {
    private String name;
    private String phone;
    private ArrayList<Pet> adoptedPets;

    public Adopter(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.adoptedPets = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }

    public void adoptPet(Pet pet) {
        adoptedPets.add(pet);
    }

    public ArrayList<Pet> getAdoptedPets() {
        return adoptedPets;
    }

    @Override
    public String toString() {
        return name + " (" + phone + ")";
    }
}