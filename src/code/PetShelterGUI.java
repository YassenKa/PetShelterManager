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
        setSize(600, 450);  // малко по-голям, заради новите бутони
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Зареждаме животните и осиновителите при стартиране
        manager.loadPetsFromFile("pets.txt");
        manager.loadAdoptersFromFile("adopters.txt");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2));  // Добавени още редове

        JButton btnAddPet = new JButton("Добави животно");
        JButton btnViewPets = new JButton("Покажи всички животни");
        JButton btnSort = new JButton("Сортирай животните по възраст");
        JButton btnAddAdopter = new JButton("Добави осиновител");
        JButton btnViewAdopters = new JButton("Покажи всички осиновители");
        JButton btnSearchPet = new JButton("Търси животно по име");
        JButton btnSearchAdopter = new JButton("Търси осиновител по име");
        JButton btnAdoptPet = new JButton("Осинови животно");

        // Нови бутони
        JButton btnEditPet = new JButton("Редактирай животно");
        JButton btnDeletePet = new JButton("Изтрий животно");
        JButton btnEditAdopter = new JButton("Редактирай осиновител");
        JButton btnDeleteAdopter = new JButton("Изтрий осиновител");

        panel.add(btnAddPet);
        panel.add(btnViewPets);
        panel.add(btnSort);
        panel.add(btnAddAdopter);
        panel.add(btnViewAdopters);
        panel.add(btnSearchPet);
        panel.add(btnSearchAdopter);
        panel.add(btnAdoptPet);

        panel.add(btnEditPet);
        panel.add(btnDeletePet);
        panel.add(btnEditAdopter);
        panel.add(btnDeleteAdopter);

        add(panel, BorderLayout.NORTH);
        output.setEditable(false);
        add(new JScrollPane(output), BorderLayout.CENTER);

        // Обработчици на събития

        btnAddPet.addActionListener(_ -> {
            String type = JOptionPane.showInputDialog("Въведете вид животно:");
            String name = JOptionPane.showInputDialog("Въведете име:");
            int age;
            try {
                age = Integer.parseInt(JOptionPane.showInputDialog("Въведете възраст:"));
            } catch (Exception e) {
                output.setText("Невалидна възраст.");
                return;
            }
            String health = JOptionPane.showInputDialog("Въведете здравословен статус:");
            manager.addPet(new Pet(type, name, age, health));
            output.setText("Животното е добавено.");
        });

        btnViewPets.addActionListener(_ -> {
            StringBuilder sb = new StringBuilder();
            for (Pet p : manager.getPets()) {
                sb.append(p.toString()).append("\n");
            }
            output.setText(sb.toString());
        });

        btnSort.addActionListener(_ -> {
            manager.sortPetsByAge();
            output.setText("Животните са сортирани по възраст.");
        });

        btnAddAdopter.addActionListener(_ -> {
            String name = JOptionPane.showInputDialog("Въведете име на осиновителя:");
            String phone = JOptionPane.showInputDialog("Въведете телефон на осиновителя:");
            manager.addAdopter(new Adopter(name, phone));
            output.setText("Осиновителят е добавен.");
        });

        btnViewAdopters.addActionListener(_ -> {
            StringBuilder sb = new StringBuilder();
            for (Adopter a : manager.getAdopters()) {
                sb.append(a.toString()).append("\n");
            }
            output.setText(sb.toString());
        });

        btnSearchPet.addActionListener(_ -> {
            String name = JOptionPane.showInputDialog("Въведете име на животно за търсене:");
            Pet p = manager.findPetByName(name);
            if (p != null) output.setText(p.toString());
            else output.setText("Животното не е намерено.");
        });

        btnSearchAdopter.addActionListener(_ -> {
            String name = JOptionPane.showInputDialog("Въведете име на осиновител за търсене:");
            Adopter a = manager.findAdopterByName(name);
            if (a != null) output.setText(a.toString());
            else output.setText("Осиновителят не е намерен.");
        });

        btnAdoptPet.addActionListener(_ -> {
            String adopterName = JOptionPane.showInputDialog("Въведете име на осиновител:");
            String petName = JOptionPane.showInputDialog("Въведете име на животно за осиновяване:");
            if (manager.adoptPet(adopterName, petName)) {
                output.setText("Осиновяването е успешно.");
            } else {
                output.setText("Осиновяването не бе успешно.");
            }
        });

        // Нови обработчици:

        btnEditPet.addActionListener(_ -> {
            String oldName = JOptionPane.showInputDialog("Въведете името на животното за редакция:");
            if (oldName == null) return;
            Pet pet = manager.findPetByName(oldName);
            if (pet == null) {
                output.setText("Животното не е намерено.");
                return;
            }
            String newType = JOptionPane.showInputDialog("Нов вид:", pet.getType());
            String newName = JOptionPane.showInputDialog("Ново име:", pet.getName());
            int newAge;
            try {
                newAge = Integer.parseInt(JOptionPane.showInputDialog("Нова възраст:", pet.getAge()));
            } catch (Exception e) {
                output.setText("Невалидна възраст.");
                return;
            }
            String newHealth = JOptionPane.showInputDialog("Нов здравословен статус:", pet.getHealthStatus());

            if (manager.editPet(oldName, newType, newName, newAge, newHealth)) {
                output.setText("Животното е редактирано успешно.");
            } else {
                output.setText("Грешка при редакция.");
            }
        });

        btnDeletePet.addActionListener(_ -> {
            String name = JOptionPane.showInputDialog("Въведете името на животното за изтриване:");
            if (name == null) return;
            if (manager.removePetByName(name)) {
                output.setText("Животното е изтрито.");
            } else {
                output.setText("Животното не е намерено.");
            }
        });

        btnEditAdopter.addActionListener(_ -> {
            String oldName = JOptionPane.showInputDialog("Въведете името на осиновителя за редакция:");
            if (oldName == null) return;
            Adopter adopter = manager.findAdopterByName(oldName);
            if (adopter == null) {
                output.setText("Осиновителят не е намерен.");
                return;
            }
            String newName = JOptionPane.showInputDialog("Ново име:", adopter.getName());
            String newPhone = JOptionPane.showInputDialog("Нов телефон:", adopter.getPhone());

            if (manager.editAdopter(oldName, newName, newPhone)) {
                output.setText("Осиновителят е редактиран успешно.");
            } else {
                output.setText("Грешка при редакция.");
            }
        });

        btnDeleteAdopter.addActionListener(_ -> {
            String name = JOptionPane.showInputDialog("Въведете името на осиновителя за изтриване:");
            if (name == null) return;
            if (manager.removeAdopterByName(name)) {
                output.setText("Осиновителят е изтрит.");
            } else {
                output.setText("Осиновителят не е намерен.");
            }
        });

        // Запис при затваряне
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                manager.savePetsToFile("pets.txt");
                manager.saveAdoptersToFile("adopters.txt");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PetShelterGUI().setVisible(true);
        });
    }
}
