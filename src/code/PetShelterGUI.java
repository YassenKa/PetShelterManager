package code;

import javax.swing.*;
import java.awt.*;

public class PetShelterGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    
    ShelterManager manager = new ShelterManager();
    JTextArea output = new JTextArea();

    public PetShelterGUI() {
        setTitle("Приют за животни");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 5, 5)); // Повече редове за новите бутони

        JButton btnAddPet = new JButton("Добави животно");
        JButton btnAddAdopter = new JButton("Добави осиновител");
        JButton btnViewPets = new JButton("Покажи всички животни");
        JButton btnViewAdopters = new JButton("Покажи всички осиновители");
        JButton btnSort = new JButton("Сортирай животните по възраст");

        JTextField searchField = new JTextField();
        JButton btnSearch = new JButton("Търси по име");

        JButton btnAdoptPet = new JButton("Осинови животно");

        panel.add(btnAddPet);
        panel.add(btnAddAdopter);
        panel.add(btnViewPets);
        panel.add(btnViewAdopters);
        panel.add(btnSort);

        panel.add(new JLabel("Търсене по име (животно или осиновител):"));
        panel.add(searchField);
        panel.add(btnSearch);

        panel.add(btnAdoptPet);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);

        // Добавяне на животно
        btnAddPet.addActionListener(_ -> {
            String type = JOptionPane.showInputDialog("Вид:");
            String name = JOptionPane.showInputDialog("Име:");
            String ageStr = JOptionPane.showInputDialog("Възраст:");
            String health = JOptionPane.showInputDialog("Здраве:");

            try {
                if (type == null || name == null || ageStr == null || health == null ||
                    type.isEmpty() || name.isEmpty() || ageStr.isEmpty() || health.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Моля, попълни всички полета.");
                    return;
                }
                int age = Integer.parseInt(ageStr);
                manager.addPet(new Pet(type, name, age, health));
                output.append("Добавено животно: " + name + "\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Възрастта трябва да е число.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Невалидни данни.");
            }
        });

        // Добавяне на осиновител
        btnAddAdopter.addActionListener(_ -> {
            String name = JOptionPane.showInputDialog("Име на осиновителя:");
            String phone = JOptionPane.showInputDialog("Телефон:");

            if (name == null || phone == null || name.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Моля, попълни всички полета.");
                return;
            }

            manager.addAdopter(new Adopter(name, phone));
            output.append("Добавен осиновител: " + name + "\n");
        });

        // Показване на животните
        btnViewPets.addActionListener(_ -> {
            output.setText("");
            if(manager.getPets().isEmpty()) {
                output.append("Няма добавени животни.\n");
            } else {
                for (Pet p : manager.getPets()) {
                    output.append(p.toString() + "\n");
                }
            }
        });

        // Показване на осиновителите
        btnViewAdopters.addActionListener(_ -> {
            output.setText("");
            if(manager.getAdopters().isEmpty()) {
                output.append("Няма добавени осиновители.\n");
            } else {
                for (Adopter a : manager.getAdopters()) {
                    output.append(a.toString() + "\n");
                    if (a.getAdoptedPets().isEmpty()) {
                        output.append("  - Не е осиновил животни\n");
                    } else {
                        for (Pet p : a.getAdoptedPets()) {
                            output.append("  - " + p.toString() + "\n");
                        }
                    }
                }
            }
        });

        // Сортиране на животните по възраст
        btnSort.addActionListener(_ -> {
            manager.sortPetsByAge();
            output.append("Животните бяха сортирани по възраст.\n");
        });

        // Търсене по име на животно или осиновител
        btnSearch.addActionListener(_ -> {
            String searchName = searchField.getText().trim();
            if (searchName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Моля, въведи име за търсене.");
                return;
            }

            output.setText("");
            Pet pet = manager.findPetByName(searchName);
            Adopter adopter = manager.findAdopterByName(searchName);

            if (pet == null && adopter == null) {
                output.append("Няма намерени животни или осиновители с името: " + searchName + "\n");
                return;
            }

            if (pet != null) {
                output.append("Намеренo животно:\n" + pet.toString() + "\n");
            }
            if (adopter != null) {
                output.append("Намерен осиновител:\n" + adopter.toString() + "\n");
                if (adopter.getAdoptedPets().isEmpty()) {
                    output.append("  - Не е осиновил животни\n");
                } else {
                    for (Pet p : adopter.getAdoptedPets()) {
                        output.append("  - " + p.toString() + "\n");
                    }
                }
            }
        });

        // Осиновяване: свързване на животно с осиновител
        btnAdoptPet.addActionListener(_ -> {
            String adopterName = JOptionPane.showInputDialog("Име на осиновителя:");
            if (adopterName == null || adopterName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Моля, въведи име на осиновителя.");
                return;
            }
            Adopter adopter = manager.findAdopterByName(adopterName);
            if (adopter == null) {
                JOptionPane.showMessageDialog(this, "Не е намерен осиновител с това име.");
                return;
            }

            String petName = JOptionPane.showInputDialog("Име на животното, което ще се осинови:");
            if (petName == null || petName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Моля, въведи име на животното.");
                return;
            }
            Pet pet = manager.findPetByName(petName);
            if (pet == null) {
                JOptionPane.showMessageDialog(this, "Не е намерено животно с това име.");
                return;
            }

            adopter.adoptPet(pet);
            output.append("Животното " + petName + " е осиновено от " + adopterName + ".\n");
        });

        setVisible(true);
    }
}
