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

    // Нова добавена функция за премахване на осиновено животно
    public boolean removePet(Pet pet) {
        return adoptedPets.remove(pet);
    }

    public ArrayList<Pet> getAdoptedPets() {
        return adoptedPets;
    }

    @Override
    public String toString() {
        if (adoptedPets.isEmpty()) {
            return name + " (" + phone + ") - няма осиновени животни";
        }
        StringBuilder petsList = new StringBuilder();
        for (Pet p : adoptedPets) {
            if (petsList.length() > 0) petsList.append(", ");
            petsList.append(p.getName());
        }
        return name + " (" + phone + ") - осиновени животни: [" + petsList.toString() + "]";
    }
}
