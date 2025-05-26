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
        panel.setLayout(new GridLayout(8, 2));  // Увеличено с 1 ред за нов бутон

        JButton btnAddPet = new JButton("Добави животно");
        JButton btnViewPets = new JButton("Покажи всички животни");
        JButton btnSort = new JButton("Сортирай животните по възраст");
        JButton btnAddAdopter = new JButton("Добави осиновител");
        JButton btnViewAdopters = new JButton("Покажи всички осиновители");
        JButton btnSearchPet = new JButton("Търси животно по име");
        JButton btnSearchAdopter = new JButton("Търси осиновител по име");
        JButton btnAdoptPet = new JButton("Осинови животно");  // НОВ бутон

        panel.add(btnAddPet);
        panel.add(btnViewPets);
        panel.add(btnSort);
        panel.add(btnAddAdopter);
        panel.add(btnViewAdopters);
        panel.add(btnSearchPet);
        panel.add(btnSearchAdopter);
        panel.add(btnAdoptPet);

        add(panel, BorderLayout.NORTH);
        output.setEditable(false);
        add(new JScrollPane(output), BorderLayout.CENTER);

        // Обработчици на събития

        btnAddPet.addActionListener(_ -> {
            String type = JOptionPane.showInputDialog("Въведете вид животно:");
            String name = JOptionPane.showInputDialog("Въведете име:");
            int age = Integer.parseInt(JOptionPane.showInputDialog("Въведете възраст:"));
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

        // НОВ обработчик за осиновяване
        btnAdoptPet.addActionListener(_ -> {
            String adopterName = JOptionPane.showInputDialog("Въведете име на осиновител:");
            String petName = JOptionPane.showInputDialog("Въведете име на животно за осиновяване:");
            if (manager.adoptPet(adopterName, petName)) {
                output.setText("Осиновяването е успешно.");
            } else {
                output.setText("Осиновяването не бе успешно.");
            }
        });

        // При затваряне на прозореца, записваме данните в файловете
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
