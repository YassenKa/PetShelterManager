package code;

import java.util.ArrayList;

public class Adopter {
    private String name;
    private String phone;
    private ArrayList<Pet> adoptedPets = new ArrayList<>();

    public Adopter(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void adoptPet(Pet pet) {
        adoptedPets.add(pet);
    }

    @Override
    public String toString() {
        return "Осиновител: " + name + ", Телефон: " + phone + ", Осиновени животни: " + adoptedPets.size();
    }
}
