import javax.management.JMException;
import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class GameMenuBar {

    public JMenuBar menuBar;

    public GameMenuBar() {
        menuBar = new JMenuBar();
    }

    public GameMenuBar(JMenu... menus) {

        this();

        for (JMenu menu : menus) {
            menuBar.add(menu);
        }

    }

    public GameMenuBar(JMenuItem... items) {

        this(new JMenu("Menu"));

        for (JMenuItem item : items) {
            menuBar.getMenu(0).add(item);
        }

    }

    public class GameMenuItem {

        public JFrame frame;

        public GameMenuItem() {
            frame = new JFrame();
        }

        public GameMenuItem(JFrame f) {
            frame = f;
        }

        public JFrame getMenuItem() {
            return frame;
        }

        public JFrame defaultInstructionsMenuItem() {

            JLabel start = new JLabel("<html><center>To start a game, choose one of the game modes by pressing either<br/>the \"Human vs. Human\" or \"Human vs. Computer\" button.</center></html>", JLabel.CENTER);
            JLabel setup = new JLabel("<html><center>Each player will be prompted to enter a name and choose a piece color.<br/>After that, a prompt will appear to choose the board color.</center></html>", JLabel.CENTER);
            JLabel begin = new JLabel("<html><center>To begin, place a game piece by pressing one of the black arrow buttons above each column.</center></html>", JLabel.CENTER);
            JLabel during = new JLabel("<html><center>Turns will alternate between players after a new piece has been added by the current player.</center></html>", JLabel.CENTER);
            JLabel end = new JLabel("<html><center>The game will end once a player has successfully lined four of their game pieces in a row.<br/>The game also ends when all the columns become full.<br/>A prompt will appear notifying the player(s) that the game is over.</center></html>", JLabel.CENTER);

            JPanel content = new JPanel(new GridLayout(5, 0, 5, 5));
            content.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
            content.add(start);
            content.add(setup);
            content.add(begin);
            content.add(during);
            content.add(end);

            frame.add(content);
            frame.setTitle("Instructions");

            return frame;

        }

        public JFrame defaultAboutMenuItem() {

            JLabel program = new JLabel("<html><center>Four-in-a-Row</center></html>", JLabel.CENTER);
            JLabel version = new JLabel("<html><center>V1.0</center></html>", JLabel.CENTER);
            JLabel names = new JLabel("<html><center>Created by Ricky Mangerie and Zach Jung</center></html>", JLabel.CENTER);
            JLabel university = new JLabel("<html><center>Drexel University</center></html>", JLabel.CENTER);

            JPanel content = new JPanel(new GridLayout(4, 0, 5, 5));
            content.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
            content.add(program);
            content.add(version);
            content.add(names);
            content.add(university);

            frame.add(content);
            frame.setTitle("About");

            return frame;

        }

    }

    public JMenuBar defaultMenu() {

        // Create menus File and Help
        JMenu menuFile = new JMenu("File");
        JMenuItem menuHelp = new JMenu("Help");

        // Create menu items Exit, Instructions, Rules, and About
        JMenuItem itemExit = new JMenuItem("Exit");
        JMenuItem itemInstructions = new JMenuItem("Instructions");
        JMenuItem itemRules = new JMenuItem("Rules");
        JMenuItem itemAbout = new JMenuItem("About");

        addItemListener(itemExit, 0);
        addItemListener(itemInstructions, new GameMenuItem().defaultInstructionsMenuItem());
        addItemListener(itemRules,"http://www.ludoteka.com/connect-4.html");
        addItemListener(itemAbout, new GameMenuItem().defaultAboutMenuItem());


        // Add the exit menu item to the menu
        menuFile.add(itemExit);
        menuHelp.add(itemInstructions);
        menuHelp.add(itemRules);
        menuHelp.add(itemAbout);

        // Add the menu to the menu bar
        menuBar.add(menuFile);
        menuBar.add(menuHelp);

        // Return the menu bar object
        return menuBar;

    }

    public void addItemListener(JMenuItem item, JFrame frame) {

        item.addActionListener(evt -> {

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);

        });

    }

    public void addItemListener(JMenuItem item, String url) {

        item.addActionListener(evt -> {

            try {
                openWebpage(new URL(url));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        });

    }

    public void addItemListener(JMenuItem item, int i) {

        item.addActionListener(evt -> {
            // Close the program
            System.exit(i);
        });

//        return item;

    }

    public static boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean openWebpage(URL url) {
        try {
            return openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

}
