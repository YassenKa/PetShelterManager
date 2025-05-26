package code;

import javax.swing.*;
import java.awt.*;

public class PetShelterGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ShelterManager manager = new ShelterManager();
    JTextArea output = new JTextArea();

    public PetShelterGUI() {
        setTitle("Приют за животни");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JButton btnAddPet = new JButton("Добави животно");
        JButton btnViewPets = new JButton("Покажи всички");
        JButton btnSort = new JButton("Сортирай по възраст");

        panel.add(btnAddPet);
        panel.add(btnViewPets);
        panel.add(btnSort);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);

        btnAddPet.addActionListener(_ -> {
            String type = JOptionPane.showInputDialog("Вид:");
            String name = JOptionPane.showInputDialog("Име:");
            String ageStr = JOptionPane.showInputDialog("Възраст:");
            String health = JOptionPane.showInputDialog("Здраве:");

            try {
                if (type.isEmpty() || name.isEmpty() || ageStr.isEmpty() || health.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Моля, попълни всички полета.");
                    return;
                }
                int age = Integer.parseInt(ageStr);
                manager.addPet(new Pet(type, name, age, health));
                output.append("Добавено: " + name + "\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Невалидни данни.");
            }
        });

        btnViewPets.addActionListener(_ -> {
            output.setText("");
            for (Pet p : manager.getPets()) {
                output.append(p.toString() + "\n");
            }
        });

        btnSort.addActionListener(_ -> {
            manager.sortPetsByAge();
            output.append("Животните бяха сортирани по възраст.\n");
        });

        setVisible(true);
    }
}

