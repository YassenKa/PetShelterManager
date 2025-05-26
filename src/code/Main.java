package code;

public class Main {
    public static void main(String[] args) {
        // Стартиране на GUI в Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            PetShelterGUI gui = new PetShelterGUI();
            gui.setVisible(true);
        });
    }
}
