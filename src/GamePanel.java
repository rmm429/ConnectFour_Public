import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * The class where the game GUI is set up and displayed
 * @see JFrame
 */

public class GamePanel extends JFrame {

    // How many rows and columns there are in the game board
    public final int ROWS = 6;
    public final int COLS = 7;

    public String gameMode;

    // Objects for Player 1, Player 2, and the current player
    public Player p1;
    public Player p2;
    public Player currentPlayer;

    public JFrame frame;

    // Game panel and game logic controller objects
    public static GamePanel gamePanel;
    public Game game;

    // Menu bar and menu objects
    public static JMenuBar menuBar;
    public static JMenu menu;

    // Player display, arrow buttons, and game board panels
    public static JPanel playerPanel;
    public static JPanel arrowsPanel;
    public static JPanel boardPanel;

    // Current player label
    public JLabel curPlayer;

    // Board layout and constraints objects
    public GridBagLayout boardLayout;
    public GridBagConstraints boardConstraints;

    // Game board color
    public char boardColor;

    // Image objects for a blank piece/slot and the arrow buttons
    public BufferedImage emptyImg = null;
    public BufferedImage arrowImg = null;

    // Arrays that hold the arrow button and game piece objects
    public JButton[] arrowBtns = new JButton[COLS];
    public JLabel[][] piecesImgs = new JLabel[COLS][ROWS];

    /**
     * Handle the event of each arrow button press
     */
    void handleEvent (ActionEvent e) {

//        //DELETE LATER ---------
//        System.out.println("Current player: " + Character.toString(currentPlayer) + "\n");
//        // ----------------------

        // The button that was pressed
        String btnPressed = e.getActionCommand(); // Retrieves the action command property set for the current button

        // Handle each button press
        switch (btnPressed) {

            case "0":
                // Add a game piece to column 1
                addPiece(currentPlayer.getColor(), 0);
                break;
            case "1":
                // Add a game piece to column 2
                addPiece(currentPlayer.getColor(), 1);
                break;
            case "2":
                // Add a game piece to column 3
                addPiece(currentPlayer.getColor(), 2);
                break;
            case "3":
                // Add a game piece to column 4
                addPiece(currentPlayer.getColor(), 3);
                break;
            case "4":
                // Add a game piece to column 5
                addPiece(currentPlayer.getColor(), 4);
                break;
            case "5":
                // Add a game piece to column 6
                addPiece(currentPlayer.getColor(), 5);
                break;
            case "6":
                // Add a game piece to column 7
                addPiece(currentPlayer.getColor(), 6);
                break;
            default:
                // Handling an unknown situation where the button pressed is out-of-range
                System.err.println("ERROR: Cannot determine button action (CODE BEHAVIOR ISSUE)");
                System.exit(1);

        }

    }

    /**
     * Initialize the arrow buttons and add them to the arrow buttons panel
     */
    public void addBtns() {

        // For each column of the game board...
        for (int i = 0; i < COLS; i++) {

            // Create a new button
            JButton btn = new JButton();

            // Set the icon of the button to the arrow image
            btn.setIcon(new ImageIcon(arrowImg));
            // Set empty borders for the button
            btn.setBorder(BorderFactory.createEmptyBorder());
            // Do not fill the button with anything but the image previously set
            btn.setContentAreaFilled(false);

            // Set an action command for the current button to signify the column it is assigned to
            btn.setActionCommand("" + i);

            // Add an action listener to the current button for when it is pressed
            btn.addActionListener(e -> handleEvent(e));

            // Add the current button to the arrow buttons array
            arrowBtns[i] = btn;

            // Add the current button to the arrow buttons panel
            arrowsPanel.add(arrowBtns[i]);

        }

    }

    /**
     * Initialize the game board and add it to the game board panel
     */
    public void addGameBoard() {

        // For each row of the game board...
        for(int row = 0; row < ROWS; row++) {

            // Fill each piece in the constraint completely horizontally
            boardConstraints.fill = GridBagConstraints.HORIZONTAL;

            // For each column of the game board...
            for (int col = 0; col < COLS; col++ ) {

                // Create a new game piece label
                JLabel piece = new JLabel();

                //int rowInv = Math.abs(row-ROWS)-1; // Use if end of 2D array should instead correlate with top of board

                // Set the image of the game piece to an empty board piece
                piece.setIcon(new ImageIcon(emptyImg));
                // Place the current piece in the pieces images array
                piecesImgs[col][row] = piece;
                //piecesImgs[col][rowInv] = piece; // Use if end of 2D array should instead correlate with top of board

                // Set the row and column to place the current empty board piece
                boardConstraints.gridx = col;
                boardConstraints.gridy = row;
                // Add the current empty game piece to the game board panel
                boardPanel.add(piecesImgs[col][row], boardConstraints);
                //boardPanel.add(piecesImgs[col][rowInv], boardConstraints); // Use if end of 2D array should instead correlate with top of board

            }

        }

    }

    /**
     * Add a piece to the game board
     * @param color The color of the piece to add
     * @param col The column to add the piece to
     * @return Nothing
     */
    public void addPiece(char color, int col) {

        // Set the row to initially be the last row of the game board
        int row = 5;

        // If the current column is not full...
        if (game.isColumnFull(col) == false) {

            // Advance up the rows of the game board until an empty spot is reached to place the current piece
            while (game.getLocationStatus(col, row) != '0') {
                row--;
            }

            // Set the image of the current game piece to a new image associated with the color of the current player
            piecesImgs[col][row].setIcon(new ImageIcon(setPieceImage( new BufferedImage(emptyImg.getWidth(), emptyImg.getHeight(), BufferedImage.TYPE_3BYTE_BGR), color )) );

            // Add the game piece to the game logic controller
            game.addGamePiece(col, row, color);

            // Disable the arrow button of the current column if the column is full after placing the current game piece
            if (game.isColumnFull(col) == true) {

                arrowBtns[col].setEnabled(false);

                if (game.isBoardFull(COLS)) {
                    drawGame();
                }

            }

            // Check whether or not a player has won the game after placing the current game piece
            if (game.GameOver() != 0)
            {
                // Declare the winner of the current game
                declareWinner(game.GameOver());
            } else {
                // Switch the current player
                switchPlayer();
            }

        }

    }

    /**
     * End the game as a draw
     */
    public void drawGame() {

        // Create a panel that will contain the game over message
        JLabel dialogLabel = new JLabel("DRAW", JLabel.CENTER);
        JLabel messageLabel = new JLabel("No more moves can be made!", JLabel.CENTER);
        JPanel dialogMsgPanel = new JPanel(new BorderLayout());
        dialogMsgPanel.add(dialogLabel, BorderLayout.NORTH);
        dialogMsgPanel.add(messageLabel, BorderLayout.SOUTH);

        // Creating a Quit and Main Menu button to display within the option pane
        Object[] options = {"Quit", "Main Menu"};

        // Create an option pane dialog box
        int result = JOptionPane.showOptionDialog(null, dialogMsgPanel, "Draw", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);


        // Handles what each option pane button will do
        if (result == 0) {
            // Exit the program
            System.exit(result);
        } else if (result == 1) {
            frame.dispose();
            MainMenu.main(null);
        }

    }

    /**
     * Switch the current player
     */
    public void switchPlayer() {

        if(gameMode.equals("AI"))
        {
         if(currentPlayer == p1) {

             currentPlayer = p2;
             int[] move = p2.getScore(game.gameBoard, p1.Color);
             int col = move[1];
             arrowBtns[col].doClick();

         }
         else
         {
             currentPlayer = p1;
         }
        }
        else
        {
            // Change the value of the object storing the current player
            currentPlayer = (currentPlayer == p1) ? p2 : p1; // If the current player is p1, switch to p2; if the current player is not p1 (p2), switch to p1
        }

        curPlayer.setText(currentPlayer.getPlayerName() + "\'s Turn");

    }


    /**
     * Declare the winner of the current game
     * @param winner The winner of the game
     * @return Nothing
     */
    public void declareWinner(char winner) {

        // Get the name of the winning player and turn it into a message
        String winningPlayer = (winner == p1.getColor()) ? p1.getPlayerName() : p2.getPlayerName();
        String winningPlayerMsg = winningPlayer + " is the winner!";

        // Create a panel that will contain the game over message
        JLabel dialogLabel = new JLabel("GAME OVER", JLabel.CENTER);
        JLabel winningPlayerLabel = new JLabel(winningPlayerMsg, JLabel.CENTER);
        JPanel dialogMsgPanel = new JPanel(new BorderLayout());
        dialogMsgPanel.add(dialogLabel, BorderLayout.NORTH);
        dialogMsgPanel.add(winningPlayerLabel, BorderLayout.SOUTH);

        // Creating a Quit and Main Menu button to display within the option pane
        Object[] options = {"Quit", "Main Menu"};

        // Create an option pane dialog box
        int result = JOptionPane.showOptionDialog(null, dialogMsgPanel, "Game Over", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);


        // Handles what each option pane button will do
        if (result == 0) {
            // Exit the program
            System.exit(result);
        } else if (result == 1) {
            frame.dispose();
            MainMenu.main(null);
        }

    }

    /**
     * Combo Item object containing the label and value of a color
     */
    public class ColorsComboItem {
        private char value;
        private String label;

        /**
         * Constructor
         */
        public ColorsComboItem(char value, String label) {
            this.value = value;
            this.label = label;
        }

        /**
         * Return the color value of an object
         */
        public char getValue() {
            return this.value;
        }

        /**
         * Return the color label of an object when converting the object to a String
         */
        @Override
        public String toString() {
            return this.label;
        }
    }

    /**
     * Prompt the user(s) to set the color of the game board
     */
    public void setBoardColor() {

        // Create an Array List of color combo items
        ArrayList<ColorsComboItem> colors = new ArrayList<>();
        // Add a red and blue color combo item
        colors.add(new ColorsComboItem('Y', "Yellow"));
        colors.add(new ColorsComboItem('B', "Blue"));

        // Create an input panel
        JPanel input = new JPanel(new BorderLayout());

        // Create a color panel
        JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // Create a label prompting the player to choose a color
        JLabel chooseColor = new JLabel("Choose a board color: ");
        // Create a combo box containing all possible color choices
        JComboBox color = new JComboBox(colors.toArray());
        // Set the default color choice to the first color in the combo box
        color.setSelectedIndex(0);
        // Add the label and combo box to the color panel
        colorPanel.add(chooseColor);
        colorPanel.add(color);

        // Add the color panel to the input panel
        input.add(colorPanel, BorderLayout.CENTER);

        // Creating one OK button to display within the option pane
        Object[] options = {"OK"};
        // Create the option pane containing the current input panel and one OK button
        int result = JOptionPane.showOptionDialog(null, input, "Board Color", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        // Determines what happens if the user either clicks the OK button or closes out of the option pane
        if (result == 0) {

            // Set the color of the game board panel
            boardPanel.setBackground(getBoardColor( ((ColorsComboItem)color.getSelectedItem()).getValue() ));
            boardColor = ((ColorsComboItem)color.getSelectedItem()).getValue();

        } else if ( result == JOptionPane.CLOSED_OPTION) {
            // Exit the program
            System.exit(0);
        } else {
            // Handling an unknown situation where an unknown action was made within the option pane
            System.err.println("ERROR: Cannot determine option pane action (CODE BEHAVIOR ISSUE)");
            System.exit(1);
        }

    }

    /**
     * Prompt the user(s) to enter their name and choose a color
     */
    public void setPlayerInfo() {

        // Create an Array List of color combo items
        ArrayList<ColorsComboItem> colors = new ArrayList<>();
        // Add a combo item for each available piece color
        colors.add(new ColorsComboItem('R', "Red"));
        colors.add(new ColorsComboItem('O', "Orange"));
        colors.add(new ColorsComboItem('Y', "Yellow"));
        colors.add(new ColorsComboItem('G', "Green"));
        colors.add(new ColorsComboItem('B', "Blue"));
        colors.add(new ColorsComboItem('P', "Purple"));
        colors.add(new ColorsComboItem('X', "Black"));

        // Prompt for player info twice since there are two players
        for(int i = 0; i < 2; i++) {

            // Create an input panel
            JPanel input = new JPanel(new BorderLayout());

            // Create a name panel
            JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            // Create a label prompting the player to enter their name
            JLabel enterName = new JLabel("Enter your name: ");
            // Create a text field where the player will enter their name
            JTextField name = new JTextField(20);
            // Add the label and text field to the name panel
            namePanel.add(enterName);
            namePanel.add(name);

            // Create a color panel
            JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            // Create a label prompting the player to choose a color
            JLabel chooseColor = new JLabel("Choose a color: ");
            // Create a combo box containing all possible color choices
            JComboBox color = new JComboBox(colors.toArray());
            // Set the default color choice to the first color in the combo box
            color.setSelectedIndex(0);
            // Add the label and combo box to the color panel
            colorPanel.add(chooseColor);
            colorPanel.add(color);

            // Add the name and color panels to the input panel
            input.add(namePanel, BorderLayout.NORTH);
            input.add(colorPanel, BorderLayout.SOUTH);

            // Variable storing the current option pane title
            String title = "";
            // Object storing info for the current player
            currentPlayer = new Human("", '0');
            BufferedImage curImage = null;

            // Option pane options specific to Player 1 and Player 2 (or AI player)
            if (gameMode.equals("AI")) {
                title = "Player";
                p1 = currentPlayer;
            } else if (i == 0) {
                title = "Player 1";
                p1 = currentPlayer;
            } else if (i == 1) {
                title = "Player 2";
                p2 = currentPlayer;
            }

            // Set the default text of the name text field to the current player
            name.setText(title);
            // When the name text field is selected/gains focus, select all of the text in the text field
            name.addFocusListener(new FocusListener() {
                @Override public void focusLost(final FocusEvent pE) {}
                @Override public void focusGained(final FocusEvent pE) {
                    name.selectAll();
                }
            });

            // Creating one OK button to display within the option pane
            Object[] options = {"OK"};
            // Create the option pane containing the current input panel and one OK button
            int result = JOptionPane.showOptionDialog(null, input, title + " Info", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            // Determines what happens if the user either clicks the OK button or closes out of the option pane
            if (result == 0) {

                // Set the name and color of the current player
                currentPlayer.setPlayerName(name.getText());
                currentPlayer.setColor( ((ColorsComboItem)color.getSelectedItem()).getValue() );

                // Remove the color option chosen by the current player from the combo box
                colors.remove(color.getSelectedItem());

            } else if ( result == JOptionPane.CLOSED_OPTION) {
                // Exit the program
                System.exit(0);
            } else {
                // Handling an unknown situation where an unknown action was made within the option pane
                System.err.println("ERROR: Cannot determine option pane action (CODE BEHAVIOR ISSUE)");
                System.exit(1);
            }

            // Set Player 2 as Computer if the game mode is AI
            if (gameMode.equals("AI")) {

                // Set Player 2 as an AI player and assign a random color from the available color choices
                Random random = new Random();
                int randIndex = random.nextInt(colors.size());
                char randColor = colors.get(randIndex).getValue();
                p2 = new AI("Computer", randColor);
                i++; // Exits the loop and ensures that there is no second prompt for Player 2

            }

        }

    }

    /**
     * Get the RGB color value that the game board will be set to
     * @param c The color of the game board
     * @return Color The object that contains the RGB color value
     */
    public Color getBoardColor(char c) {

        // Return the RGB color object associated with the color parameter provided
        switch (c) {

            case 'Y':
                return new Color(253,228,78); // Actual RGB values for a yellow Connect Four board
            case 'B':
                return new Color(0,107,167); // Actual RGB values for a blue Connect Four board
            default:
                // Handling an unknown situation where the color could not be determined
                System.err.println("ERROR: Color could not be determined (CODE BEHAVIOR ISSUE)");
                System.exit(1);

        }

        return null;

    }

    /**
     * Get the RGB color value that the game piece will be set to
     * @param c The color of the game piece
     * @return Color The object that contains the RGB color value
     */
    public Color getPieceColor(char c) {

        // Return the RGB color object associated with the color parameter provided
        switch (c) {

            case 'R':
                return new Color(161, 27, 35); // Actual RGB values for a red Connect Four game piece
            case 'O':
                return new Color(249, 88, 8);
            case 'Y':
                //return new Color(239, 222, 104); // Actual RGB values for a yellow Connect Four game piece
                return new Color(230, 195, 0);
            case 'G':
                return new Color(96, 133, 100);
            case 'B':
                return new Color(22, 35, 132);
                //color = new Color(0, 30, 159);
            case 'P':
                return new Color(90, 50, 147); // Actual RGB values for a black Connect Four game piece
            case 'X':
                return new Color(14, 7, 8); // Actual RGB values for a black Connect Four game piece
            default:
                // Handling an unknown situation where the color could not be determined
                System.err.println("ERROR: Color could not be determined (CODE BEHAVIOR ISSUE)");
                System.exit(1);

        }

        return null;

    }

    /**
     * Set the image for the current game piece
     * @param c The color of the game piece
     * @return Color The object that contains the RGB color value
     */
    public BufferedImage setPieceImage(BufferedImage bi, char c) {

        // Create a 2D graphics object based on the image parameter
        Graphics2D g2d = bi.createGraphics();

        // Draw a circle for the game piece
        g2d.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Set the background color of the game piece to the color of the board
        g2d.setColor(getBoardColor(boardColor));
        g2d.fillRect(0, 0, emptyImg.getWidth(), emptyImg.getHeight());
        // Set the color of the game piece to the color of the current player
        g2d.setPaint(getPieceColor(c));
        g2d.fillOval(0, 0, emptyImg.getWidth(), emptyImg.getHeight());
        g2d.dispose();

        // Return the new game piece
        return bi;

    }

    /**
     * Preliminary setup for the game
     */
    public void setUpGame() {

        // Create a new game object
        game = new Game();

        // Create a panel for the player display, arrow buttons, and game board
        playerPanel = new JPanel();
        arrowsPanel = new JPanel();
        boardPanel = new JPanel();

        try {
            // Set the image for a board piece/slot
            emptyImg = ImageIO.read(getClass().getResource("/images/empty.png"));
            // Set the image for the arrow buttons
            arrowImg  = ImageIO.read(getClass().getResource("/images/arrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the layout of the player display panel to a flow layout and center each component
        playerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        // Set the layout of the arrow buttons panel to a grid layout with 7 columns and gaps between each button
        arrowsPanel.setLayout(new GridLayout(0, COLS, 4, 4));

        // Prompt each player to enter their info
        setPlayerInfo();
        // Set the current player to Player 1
        currentPlayer = p1;

        // Create a label that signifies the current player and set the initial value to Red
//        curPlayer = new JLabel("Red's Turn");
        curPlayer = new JLabel(currentPlayer.getPlayerName() + "\'s Turn");
        // Set the font and size of the player label
        curPlayer.setFont(new Font("Serif", Font.PLAIN, 30));
        // Add the player label to the player display panel
        playerPanel.add(curPlayer, BorderLayout.CENTER);

        // Create a new GridBagLayout object
        boardLayout = new GridBagLayout();
        // Create a board constraints object for the grid bag layout of the game board and set gaps between each component
        boardConstraints = new GridBagConstraints();
        boardConstraints.ipadx = 4;
        boardConstraints.ipady = 4;

        // Set the layout of the game board panel to the grid bag layout object created above
        boardPanel.setLayout(boardLayout);
        // Prompt the user(s) to enter a board color
        setBoardColor();

    }

    /**
     * Class constructor
     */
    public GamePanel(String gameMode) {

        // Set the game mode
        this.gameMode = gameMode;

        // Setting the appropriate title for the frame based on the game mode
        String frameTitle = "Current Game";
        if (gameMode.equals("HUMAN")) {
            frameTitle = "Human vs. Human";
        } else if (gameMode.equals("AI")) {
            frameTitle = "Human vs. Computer";
        } else {
            // Handling an unknown situation where the game mode could not be determined
            System.err.println("ERROR: Game mode could not be determined (CODE BEHAVIOR ISSUE)");
            System.exit(1);
        }

        // Create the main frame
        frame = new JFrame(frameTitle);

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

        // Set the menu bar object of the frame to a menu bar created in another method
        frame.setJMenuBar(new GameMenuBar().defaultMenu());

        // Set up the game
        setUpGame();
        // Add the buttons to the game panel
        addBtns();
        // Add the game board to the game panel
        addGameBoard();

        // Add the player display panel to the north section of the window
        window.add(playerPanel, BorderLayout.NORTH);
        // Add the arrow buttons panel to the center section of the window
        window.add(arrowsPanel, BorderLayout.CENTER);
        // Add the game board panel to the south section of the window
        window.add(boardPanel, BorderLayout.SOUTH);

        // Add the window to the frame and set up the frame
        frame.getContentPane().add(window);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }


}
