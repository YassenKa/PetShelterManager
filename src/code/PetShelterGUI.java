package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PetShelterGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    ShelterManager manager = new ShelterManager();
    JTextArea output = new JTextArea();

    public PetShelterGUI() {
        setTitle("Приют за животни");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Зареждаме животните и осиновителите при стартиране
        manager.loadPetsFromFile("pets.txt");
        manager.loadAdoptersFromFile("adopters.txt");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        JButton btnAddPet = new JButton("Добави животно");
        JButton btnViewPets = new JButton("Покажи всички животни");
        JButton btnSort = new JButton("Сортирай животните по възраст");
        JButton btnAddAdopter = new JButton("Добави осиновител");
        JButton btnViewAdopters = new JButton("Покажи всички осиновители");
        JButton btnSearchPet = new JButton("Търси животно по име");
        JButton btnSearchAdopter = new JButton("Търси осиновител по име");

        panel.add(btnAddPet);
        panel.add(btnViewPets);
        panel.add(btnSort);
        panel.add(btnAddAdopter);
        panel.add(btnViewAdopters);
        panel.add(btnSearchPet);
        panel.add(btnSearchAdopter);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);

        // Добавяне на животно
        btnAddPet.addActionListener(_ -> {
            String type = JOptionPane.showInputDialog("Вид:");
            String name = JOptionPane.showInputDialog("Име:");
            String ageStr = JOptionPane.showInputDialog("Възраст:");
            String health = JOptionPane.showInputDialog("Здраве:");

            try {
                if (type == null || name == null || ageStr == null || health == null
                        || type.isEmpty() || name.isEmpty() || ageStr.isEmpty() || health.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Моля, попълни всички полета.");
                    return;
                }
                int age = Integer.parseInt(ageStr);
                manager.addPet(new Pet(type, name, age, health));
                output.append("Добавено животно: " + name + "\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Невалидни данни.");
            }
        });

        // Покажи всички животни
        btnViewPets.addActionListener(_ -> {
            output.setText("");
            for (Pet p : manager.getPets()) {
                output.append(p.toString() + "\n");
            }
        });

        // Сортирай животните по възраст
        btnSort.addActionListener(_ -> {
            manager.sortPetsByAge();
            output.append("Животните бяха сортирани по възраст.\n");
        });

        // Добави осиновител
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

        // Покажи всички осиновители
        btnViewAdopters.addActionListener(_ -> {
            output.setText("");
            for (Adopter a : manager.getAdopters()) {
                output.append(a.toString() + "\n");
            }
        });

        // Търси животно по име
        btnSearchPet.addActionListener(_ -> {
            String name = JOptionPane.showInputDialog("Име на животно за търсене:");
            if (name == null || name.isEmpty()) {
                return;
            }
            Pet pet = manager.findPetByName(name);
            output.setText("");
            if (pet != null) {
                output.append("Намеренo животно: " + pet.toString() + "\n");
            } else {
                output.append("Няма животно с име " + name + "\n");
            }
        });

        // Търси осиновител по име
        btnSearchAdopter.addActionListener(_ -> {
            String name = JOptionPane.showInputDialog("Име на осиновител за търсене:");
            if (name == null || name.isEmpty()) {
                return;
            }
            Adopter adopter = manager.findAdopterByName(name);
            output.setText("");
            if (adopter != null) {
                output.append("Намерен осиновител: " + adopter.toString() + "\n");
            } else {
                output.append("Няма осиновител с име " + name + "\n");
            }
        });

        // Запис при затваряне на прозореца
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                manager.savePetsToFile("pets.txt");
                manager.saveAdoptersToFile("adopters.txt");
            }
        });

        setVisible(true);
    }
}
