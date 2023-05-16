import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu extends JFrame {

    // Main frame
    public static JFrame frame;
    // Object that invokes the game panel
    public static GamePanel gamePanel;

    // Values for button choices
    public enum CHOICES {
        HUMAN,
        AI
    }

    /**
     * Handle the event of each button press
     */
    public static void handleEvent (ActionEvent e) {

        // A button was pressed
        String btnPressed = e.getActionCommand(); // Retrieves the action command property set for the current button
        // Close the main menu
        frame.dispose();
        // Open the game panel and send which button was pressed
        gamePanel = new GamePanel(btnPressed);

    }

    /**
     * The main method
     * @param args Unused
     * @return Nothing
     */
    public static void main(String[] args) {

        // Create the main frame
        frame = new JFrame("Four-in-a-Row");

        // Add a listener to the frame to check if the user closes the window
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                // Close the program
                System.exit(0);
            }
        });

        // Create the main window
        JPanel window = new JPanel();

        // Create a new border layout and set the gap between components
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(10);
        borderLayout.setVgap(10);

        // Set the layout of the window to the border layout created above
        window.setLayout(borderLayout);

        // Set the menu bar object of the frame to the default game menu
        frame.setJMenuBar(new GameMenuBar().defaultMenu());

        // Create a title and subtitle for the main menu
        JLabel subtitle = new JLabel("Not five, not three, but...");
        Font subtitleFont = new Font(subtitle.getFont().getName(), Font.ITALIC,subtitle.getFont().getSize());
        subtitle.setFont(subtitleFont);
        subtitle.setHorizontalAlignment(JLabel.CENTER);
        JLabel title = new JLabel("Four-in-a-Row");
        // Set the font and size of the player label
        title.setFont(new Font("Serif", Font.PLAIN, 30));
        title.setHorizontalAlignment(JLabel.CENTER);

        // Place the title and subtitle in a panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(40,20,20,20));
        titlePanel.add(subtitle, BorderLayout.NORTH);
        titlePanel.add(title, BorderLayout.SOUTH);

        // Create buttons that let the user choose the game mode
        JButton humanBtn = new JButton("Human vs. Human");
        humanBtn.setPreferredSize(new Dimension(175, 80));
        humanBtn.setActionCommand(CHOICES.HUMAN.toString());
        humanBtn.addActionListener(e -> handleEvent(e)); // Handle button press
        JButton aiBtn = new JButton("Human vs. Computer");
        aiBtn.setPreferredSize(new Dimension(175, 80));
        aiBtn.setActionCommand(CHOICES.AI.toString());
        aiBtn.addActionListener(e -> handleEvent(e)); // Handle button press

        // Place the buttons in a panel
        JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        buttonPanel.add(humanBtn);
        buttonPanel.add(aiBtn);

        // Place the panels in the window
        window.add(titlePanel, BorderLayout.NORTH);
        window.add(buttonPanel, BorderLayout.SOUTH);

        // Add the window to the frame and set up the frame
        frame.getContentPane().add(window);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }

}
