/*

 Program Name: Ball Puzzle

 Description: The user will control a small ball through level using the arrow keys.
                The user will progressivly encounter harder levels with different
                combinations of 4 special squares. The user will have to get
                the ball to an endpoint to finish the level.

 Author: Samuel James Catania

 Start: Thursday, May 9, 2019

 End: Tuesday, June 4, 2019
 */

import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.Robot;
import java.awt.AWTException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.border.TitledBorder;


public class BallPuzzle extends JFrame implements KeyListener, ActionListener {

    //Tracing variable
    boolean tracing = false;

    //Tracing variable (for alpha timer only)
    boolean alphaTracing = false;

    //Tracing Variables
    int secondsPassed = 0;

    //Declaring JFrames
    JFrame menuFrame = new JFrame("Ball Puzzle - Menu");
    JFrame titleFrame = new JFrame("Ball Puzzle - Title");
    JFrame levelFrame = new JFrame("Ball Puzzle");
    JFrame levelSelectFrame = new JFrame("Ball Puzzle - Level Select");
    JFrame instructionsFrame = new JFrame("Ball Puzzle - Instructions");
    JFrame aboutFrame = new JFrame("Ball Puzzle - About");

    //Declaring additional panels for level select frame
    JPanel levelSelectPanel = new JPanel();
    JPanel levelSelectSecondaryPanel = new JPanel(new GridLayout(5, 2));

    //Declaring additional panels for the menu frame
    JPanel menuPanel = new JPanel(new BorderLayout());
    JPanel menuBoxLayout = new JPanel();

    //Declaring JLabels for the title and credit on the menu frame
    JLabel menuTitle = new JLabel("Ball Puzzle");
    JLabel menuCredit = new JLabel("By: Samuel Catania");

    //JLabel for splash screen image
    JLabel splashScreenImage = new JLabel();

    //Used to control movement of ball
    boolean space = false;
    boolean moving = false;
    boolean stopDown = false;
    boolean stopLeft = false;
    boolean stopRight = false;
    boolean stopUp = false;

    //Used to check color of screen pixels
    Color color;

    //Access of BallAnimations components in this class
    BallAnimation ball = new BallAnimation();

    //Creating fonts
    Font titleFont = new Font("Monospaced", Font.BOLD, 80);
    Font headerFont = new Font("Monospaced", Font.BOLD, 25);
    Font normalTextFont = new Font("Monospaced", Font.PLAIN, 19);

    //Creating global title text to change color
    JTextField levelSelectScreenTitle = new JTextField("Level Select");
    JTextField instructionsScreenTitle = new JTextField("Instructions");
    JTextField aboutScreenTitle = new JTextField("About");

    //JLabel for showing the date and time on menu frame
    JLabel dateTime = new JLabel();

    //Timer for keeping real time on menu frame
    Timer menuTimer = new Timer();
    TimerTask menuTimerTask = new TimerTask() {

        public void run() {

            String currentTime = SimpleDateFormat.getInstance().format(Calendar.getInstance().getTime());

            dateTime.setText(currentTime);

            menuBoxLayout.add(dateTime);

            secondsPassed++;

            if (tracing) {
                System.out.println("Total Seconds Passed: " + secondsPassed);
            }

        }
    };

    public static void main(String args[]) {
        new BallPuzzle();
    }

    //Start of Constructor method
    BallPuzzle() {

        buildMenuFrame();

        buildLevelSelectFrame();

        buildInstructionsFrame();

        buildAboutFrame();

        //Set font, alignment, delay and intervals for date/time timer
        dateTime.setHorizontalAlignment(JLabel.CENTER);
        dateTime.setFont(normalTextFont);
        menuTimer.scheduleAtFixedRate(menuTimerTask, 200, 1000);

        //Create splash screen image
        ImageIcon image = new ImageIcon("cataniaCompanyLogo.png");
        splashScreenImage.setIcon(image);
        splashScreenImage.setOpaque(true);
        splashScreenImage.setForeground(Color.BLACK);

        //Timer to control the visibility time of the spash screen
        Timer splashScreenTimer = new Timer();
        TimerTask splashScreen = new TimerTask() {
            int seconds = 0;

            public void run() {
                seconds++;

                if (tracing) {
                    System.out.println("Second Passed for Splash Screen: " + seconds);
                }

                if (seconds == 3) {
                    splashScreenTimer.cancel();
                    titleFrame.setVisible(false);
                    menuFrame.setVisible(true);
                }
            }
        };

        //Timer to control the alpha value of the various frame titles, credit and date/time
        Timer alphaCycleTimer = new Timer();
        TimerTask alphaCycle = new TimerTask() {
            int alpha = 250;
            int changeInAlpha = 1;

            public void run() {
                alpha += changeInAlpha;

                if (alpha == 255) {
                    changeInAlpha = -changeInAlpha;
                }
                if (alpha == 50) {
                    changeInAlpha = -changeInAlpha;
                }
                if (alphaTracing) {
                    System.out.println("Alpha Value: " + alpha);
                }
                menuTitle.setForeground(new Color(33, 33, 232, alpha));
                dateTime.setForeground(new Color(33, 33, 232, alpha));
                menuCredit.setForeground(new Color(33, 33, 232, alpha));
                instructionsScreenTitle.setForeground(new Color(33, 33, 232, alpha));
                aboutScreenTitle.setForeground(new Color(33, 33, 232, alpha));
                levelSelectScreenTitle.setForeground(new Color(33, 33, 232, alpha));
                menuTitle.repaint();

            }

        };

        //Starting splash screen timer
        splashScreenTimer.scheduleAtFixedRate(splashScreen, 0, 1000);

        //Startin color cycle timer
        alphaCycleTimer.scheduleAtFixedRate(alphaCycle, 0, 6);

        //Creating title frame
        titleFrame.add(splashScreenImage);
        titleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        titleFrame.setSize(750, 750);
        titleFrame.setResizable(false);
        titleFrame.pack();
        titleFrame.setVisible(true);
        titleFrame.setLocationRelativeTo(null);

        //Creating level frame
        levelFrame.setUndecorated(true);
        levelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        levelFrame.setContentPane(ball);
        levelFrame.addKeyListener(this);
        levelFrame.setSize(500, 500);
        levelFrame.setResizable(false);
        ball.validate();
        ball.repaint();

    }

    //**************************************************************************

    public void buildMenuFrame() {
        //Adding Titled Border to Menu screen
        TitledBorder menuBorder = new TitledBorder("Menu");
        menuBorder.setTitleFont(headerFont);
        menuBoxLayout.setBorder(menuBorder);

        //Editing menu Title Screen Text
        menuTitle.setHorizontalAlignment(JTextField.CENTER);
        menuTitle.setFont(titleFont);

        //Editing Credit Text
        menuCredit.setHorizontalAlignment(JTextField.CENTER);
        menuCredit.setFont(headerFont);

        //Menu Spacer Panels
        JPanel menuSpacer1 = new JPanel();
        JPanel menuSpacer2 = new JPanel();
        JPanel menuSpacer3 = new JPanel();
        JPanel menuSpacer4 = new JPanel();

        //Buttons for Menu
        JButton startButton = new JButton("Start");
        startButton.setFont(headerFont);
        startButton.setPreferredSize(new Dimension(300, 60));

        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.setFont(headerFont);
        instructionsButton.setPreferredSize(new Dimension(300, 60));

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(headerFont);
        exitButton.setPreferredSize(new Dimension(300, 60));

        JButton aboutButton = new JButton("About");
        aboutButton.setFont(headerFont);
        aboutButton.setPreferredSize(new Dimension(300, 60));

        JButton levelSelectButton = new JButton("Level Select");
        levelSelectButton.setFont(headerFont);
        levelSelectButton.setPreferredSize(new Dimension(300, 60));

        //Aligning Menu Buttons
        startButton.setAlignmentX(menuBoxLayout.CENTER_ALIGNMENT);
        instructionsButton.setAlignmentX(menuBoxLayout.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(menuBoxLayout.CENTER_ALIGNMENT);
        aboutButton.setAlignmentX(menuBoxLayout.CENTER_ALIGNMENT);

        //Adding Action Listeners to buttons
        startButton.addActionListener(this);
        instructionsButton.addActionListener(this);
        aboutButton.addActionListener(this);
        levelSelectButton.addActionListener(this);
        exitButton.addActionListener(this);

        //Adding and organizing spacing for Menu buttons
        menuBoxLayout.add(Box.createRigidArea(new Dimension(100000000, 25)));
        menuBoxLayout.add(startButton);
        menuBoxLayout.add(Box.createRigidArea(new Dimension(100000000, 25)));
        menuBoxLayout.add(levelSelectButton);
        menuBoxLayout.add(Box.createRigidArea(new Dimension(100000000, 25)));
        menuBoxLayout.add(instructionsButton);
        menuBoxLayout.add(Box.createRigidArea(new Dimension(100000000, 25)));
        menuBoxLayout.add(aboutButton);
        menuBoxLayout.add(Box.createRigidArea(new Dimension(100000000, 25)));
        menuBoxLayout.add(exitButton);
        menuBoxLayout.add(Box.createRigidArea(new Dimension(100000000, 25)));

        //Implementing spacer dimensions to menu screen
        menuSpacer1.add(Box.createRigidArea(new Dimension(100, 30)));
        menuSpacer2.add(Box.createRigidArea(new Dimension(100, 30)));
        menuSpacer3.add(Box.createRigidArea(new Dimension(0, 50)));
        menuSpacer4.add(Box.createRigidArea(new Dimension(0, 20)));

        //Creating the menu panel
        menuPanel.add(menuTitle, BorderLayout.NORTH);
        menuPanel.add(menuBoxLayout, BorderLayout.CENTER);
        menuPanel.add(menuSpacer1, BorderLayout.WEST);
        menuPanel.add(menuSpacer2, BorderLayout.EAST);
        menuPanel.add(menuCredit, BorderLayout.SOUTH);

        //Creating the menu frame
        menuFrame.setContentPane(menuPanel);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(750, 750);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setResizable(false);
    }

    //**************************************************************************

    public void buildLevelSelectFrame() {
        //Editing level select title text
        levelSelectScreenTitle.setHorizontalAlignment(JTextField.CENTER);
        levelSelectScreenTitle.setFont(titleFont);
        levelSelectScreenTitle.setEditable(false);

        //Secondary panel for level select buttons
        JPanel levelSelectButtonsPanel = new JPanel();

        //Buttons for Level Select Screen
        JButton menuButton = new JButton("Menu");
        menuButton.setPreferredSize(new Dimension(200, 60));

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(200, 60));

        JButton levelOneButton = new JButton("Level One");
        levelOneButton.setPreferredSize(new Dimension(200, 70));

        JButton levelTwoButton = new JButton("Level Two");
        levelTwoButton.setPreferredSize(new Dimension(200, 70));

        JButton levelThreeButton = new JButton("Level Three");
        levelThreeButton.setPreferredSize(new Dimension(200, 70));

        JButton levelFourButton = new JButton("Level Four");
        levelFourButton.setPreferredSize(new Dimension(200, 70));

        JButton levelFiveButton = new JButton("Level Five");
        levelFiveButton.setPreferredSize(new Dimension(200, 70));

        JButton levelSixButton = new JButton("Level Six");
        levelSixButton.setPreferredSize(new Dimension(200, 70));

        JButton levelSevenButton = new JButton("Level Seven");
        levelSevenButton.setPreferredSize(new Dimension(200, 70));

        JButton levelEightButton = new JButton("Level Eight");
        levelEightButton.setPreferredSize(new Dimension(200, 70));

        JButton levelNineButton = new JButton("Level Nine");
        levelNineButton.setPreferredSize(new Dimension(200, 70));

        JButton levelTenButton = new JButton("Level Ten");
        levelTenButton.setPreferredSize(new Dimension(200, 70));

        //Adding Action Listeners to Buttons
        exitButton.addActionListener(this);
        menuButton.addActionListener(this);
        levelOneButton.addActionListener(this);
        levelTwoButton.addActionListener(this);
        levelThreeButton.addActionListener(this);
        levelFourButton.addActionListener(this);
        levelFiveButton.addActionListener(this);
        levelSixButton.addActionListener(this);
        levelSevenButton.addActionListener(this);
        levelEightButton.addActionListener(this);
        levelNineButton.addActionListener(this);
        levelTenButton.addActionListener(this);

        //Adding Level Buttons to secondary level select panel
        levelSelectSecondaryPanel.add(levelOneButton);
        levelSelectSecondaryPanel.add(levelTwoButton);
        levelSelectSecondaryPanel.add(levelThreeButton);
        levelSelectSecondaryPanel.add(levelFourButton);
        levelSelectSecondaryPanel.add(levelFiveButton);
        levelSelectSecondaryPanel.add(levelSixButton);
        levelSelectSecondaryPanel.add(levelSevenButton);
        levelSelectSecondaryPanel.add(levelEightButton);
        levelSelectSecondaryPanel.add(levelNineButton);
        levelSelectSecondaryPanel.add(levelTenButton);

        //Adding Level Select Screen level Buttons
        levelSelectButtonsPanel.add(menuButton, BorderLayout.WEST);
        levelSelectButtonsPanel.add(exitButton, BorderLayout.EAST);

        //Creating level select panel
        levelSelectPanel.add(levelSelectSecondaryPanel, BorderLayout.CENTER);

        //Creating level select frame
        levelSelectFrame.add(levelSelectScreenTitle, BorderLayout.NORTH);
        levelSelectFrame.add(levelSelectPanel, BorderLayout.CENTER);
        levelSelectFrame.add(levelSelectButtonsPanel, BorderLayout.SOUTH);
        levelSelectFrame.setSize(750, 750);
        levelSelectFrame.setLocationRelativeTo(null);
        levelSelectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        levelSelectFrame.setResizable(false);
    }

    //**************************************************************************

    public void buildInstructionsFrame() {
        //Editing instructions title text
        instructionsScreenTitle.setHorizontalAlignment(JTextField.CENTER);
        instructionsScreenTitle.setFont(titleFont);
        instructionsScreenTitle.setEditable(false);

        //Panel for instructions screen
        JPanel instructionsPanel = new JPanel(new BorderLayout());

        //Secondary panel for instructions buttons
        JPanel instructionsButtonPanel = new JPanel();

        //Instructions Spacer Panels
        JPanel instructionsSpacer1 = new JPanel();
        JPanel instructionsSpacer2 = new JPanel();

        //Implementing spacer dimensions to instructions screen
        instructionsSpacer1.add(Box.createRigidArea(new Dimension(50, 0)));
        instructionsSpacer2.add(Box.createRigidArea(new Dimension(50, 0)));

        //Buttons for Instructions Screen
        JButton menuButton = new JButton("Menu");
        menuButton.setPreferredSize(new Dimension(200, 60));

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(200, 60));

        //Adding Action Listeners to Buttons
        exitButton.addActionListener(this);
        menuButton.addActionListener(this);

        //Adding Instruction screen Buttons
        instructionsButtonPanel.add(menuButton, BorderLayout.WEST);
        instructionsButtonPanel.add(exitButton, BorderLayout.EAST);

        //Creating Instructions text
        JTextArea instructions = new JTextArea("Welcome to Ball Puzzle! To start"
                + " off the program, just click on the 'Start' button on the "
                + "menu screen. (You may also select what level you would like "
                + "to start on by clicking level select.) Ball Puzzle is an easy"
                + " to learn, yet hard to master game. \n\n"
                + ""
                + "The aim of this game is "
                + "to get the RED BALL to the GREEN FINISH SQUARE in each "
                + "level, 10 levels total. You will control the direction of the ball "
                + "using the arrow keys on your keyboard, with unlimited moves. "
                + "Once in motion, the ball will not stop until it comes into "
                + "contact with certian squares. There are"
                + " 4 special squares in this game: \n"
                + ""
                + "The DARK BLUE SQUARES are unpassable, "
                + "and will stop the ball coming from any direction. \n\n"
                + ""
                + "The GREEN SQUARES are the finish points - you will have to "
                + "reach them to finish the level. \n\n"
                + ""
                + "The ORANGE SQUARES are Portals. They are "
                + "always linked to eachother, and there will never be more than "
                + "one set. When the ball enters one ORANGE SQUARE, it will "
                + "continue in the same direction out of the other ORANGE SQUARE."
                + "\n\n"
                + ""
                + "Finally, the BLACK SQUARES WITH PINK TRIANGLES are One Ways."
                + " There are 4 kinds of this square, each with a PINK TRIANGLE "
                + "facing a different direction. The ball can only pass through "
                + "these squares in the direction the PINK TRIANGLE'S point is "
                + "pointing. (The ball will pass through as long as it is coming "
                + "from the PINK SIDE)\n\n"
                + ""
                + "In any level, you may press "
                + "the ESCAPE key on your keyboard to pause, and bring up the "
                + "level number, the meaning of each square, and an option to "
                + "exit, restart or return to the menu. KEEP IN MIND: You can "
                + "only pause when the ball ISN'T moving. This is due to the way "
                + "the program was designed (It checks the color of the pixels on"
                + " your screen) "
                + "This means that it is very important to have the Puzzle Screen "
                + "always ontop, or at the very front of your computer screen with no "
                + "overlapping screens/GUIs. This prevents any unintentional "
                + "movement of the ball. Thank you for understanding.\n\n"
                + ""
                + "To reset the ball in "
                + "any level, just press the spacebar ONCE. If pressed more than"
                + " once, or by chance, the direction key you wish the ball to "
                + "move in will have to be pressed twice in a row. Please "
                + "reference the user manual for more information, and have a "
                + "great day!");

        //Editing Instructions Text
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        instructions.setFont(headerFont);
        instructions.setEditable(false);

        //Creating a scroll feature for the instructions screen
        JScrollPane instructionsScrollPane = new JScrollPane(instructions);

        //Creating the instructions panel
        instructionsPanel.add(instructionsScreenTitle, BorderLayout.NORTH);
        instructionsPanel.add(instructionsButtonPanel, BorderLayout.SOUTH);
        instructionsPanel.add(instructionsScrollPane, BorderLayout.CENTER);
        instructionsPanel.add(instructionsSpacer1, BorderLayout.WEST);
        instructionsPanel.add(instructionsSpacer2, BorderLayout.EAST);

        //Creating the instructions frame
        instructionsFrame.add(instructionsPanel);
        instructionsFrame.setSize(750, 750);
        instructionsFrame.setVisible(false);
        instructionsFrame.setLocationRelativeTo(null);
        instructionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instructionsFrame.setResizable(false);
    }

    //**************************************************************************

    public void buildAboutFrame() {
        //Editing about title text
        aboutScreenTitle.setHorizontalAlignment(JTextField.CENTER);
        aboutScreenTitle.setFont(titleFont);
        aboutScreenTitle.setEditable(false);

        //Panel for about screen
        JPanel aboutPanel = new JPanel(new BorderLayout());

        //Secondary panel for about buttons
        JPanel aboutButtonsPanel = new JPanel();

        //About Spacer Panels
        JPanel aboutSpacer1 = new JPanel();
        JPanel aboutSpacer2 = new JPanel();

        //Implementing spacer dimensions to about screen
        aboutSpacer1.add(Box.createRigidArea(new Dimension(50, 0)));
        aboutSpacer2.add(Box.createRigidArea(new Dimension(50, 0)));

        //Buttons for About Screen
        JButton menuButton = new JButton("Menu");
        menuButton.setPreferredSize(new Dimension(200, 60));

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(200, 60));

        //Adding Action Listeners to Buttons
        exitButton.addActionListener(this);
        menuButton.addActionListener(this);

        //Adding About Screen Buttons
        aboutButtonsPanel.add(menuButton, BorderLayout.WEST);
        aboutButtonsPanel.add(exitButton, BorderLayout.EAST);

        //Creating About text
        JTextArea about = new JTextArea("This Program was created by Samuel Catania\n\n"
                + "Samuel is a grade 11 student at Ursula Franklin Academy. "
                + "This is his second year taking a Computer Sciences course.\n\n"
                + "If you have any questions/comments/concerns, please, feel "
                + "free to contact Sam at the address below:\n\n"
                + "samuel.catania@student.tdsb.on.ca\n\n"
                + "Have a great day!\n\n"
                + "Last Updated: 6/3/19\n"
                + "Version 1.2\n"
                + "©2019 Sam Cat Inc. ");

        //Editing About Text
        about.setLineWrap(true);
        about.setWrapStyleWord(true);
        about.setFont(headerFont);
        about.setEditable(false);

        //Creating a scroll feature for the about screen
        JScrollPane aboutScrollPane = new JScrollPane(about);

        //Creating the about panel
        aboutPanel.add(aboutScreenTitle, BorderLayout.NORTH);
        aboutPanel.add(aboutButtonsPanel, BorderLayout.SOUTH);
        aboutPanel.add(aboutScrollPane, BorderLayout.CENTER);
        aboutPanel.add(aboutSpacer1, BorderLayout.WEST);
        aboutPanel.add(aboutSpacer2, BorderLayout.EAST);

        //Creating the about frame
        aboutFrame.add(aboutPanel);
        aboutFrame.setSize(750, 750);
        aboutFrame.setVisible(false);
        aboutFrame.setLocationRelativeTo(null);
        aboutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aboutFrame.setResizable(false);
    }

    //**************************************************************************

    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Start")) {

            levelFrame.setVisible(true);

            menuFrame.setVisible(false);

            ball.level = 1;

            ball.repaint();

        } else if (e.getActionCommand().equals("Instructions")) {

            instructionsFrame.setVisible(true);
            menuFrame.setVisible(false);

        } else if (e.getActionCommand().equals("Exit")) {

            int x = JOptionPane.showConfirmDialog(null, "Are you sure you want to Exit?", "Exit", JOptionPane.YES_NO_OPTION);

            while (x == 0) {

                System.exit(0);

            }

        } else if (e.getActionCommand().equals("About")) {

            aboutFrame.setVisible(true);
            menuFrame.setVisible(false);

        } else if (e.getActionCommand().equals("Menu")) {

            menuFrame.setVisible(true);

            instructionsFrame.setVisible(false);

            titleFrame.setVisible(false);

            aboutFrame.setVisible(false);

            levelSelectFrame.setVisible(false);

        } else if (e.getActionCommand().equals("Level Select")) {

            levelSelectFrame.setVisible(true);

            menuFrame.setVisible(false);

        } else if (e.getActionCommand().equals("Level One")) {

            ball.level = 1;

            ball.positionX = 100;
            ball.positionY = 450;

            levelFrame.setVisible(true);

            levelSelectFrame.setVisible(false);

        } else if (e.getActionCommand().equals("Level Two")) {

            ball.level = 2;

            ball.positionX = 350;
            ball.positionY = 450;

            levelFrame.setVisible(true);

            levelSelectFrame.setVisible(false);

        } else if (e.getActionCommand().equals("Level Three")) {

            ball.level = 3;

            ball.positionX = 100;
            ball.positionY = 100;

            levelFrame.setVisible(true);

            levelSelectFrame.setVisible(false);

        } else if (e.getActionCommand().equals("Level Four")) {

            ball.level = 4;

            ball.positionX = 100;
            ball.positionY = 100;

            levelFrame.setVisible(true);

            levelSelectFrame.setVisible(false);

        } else if (e.getActionCommand().equals("Level Five")) {

            ball.level = 5;

            ball.positionX = 100;
            ball.positionY = 100;

            levelFrame.setVisible(true);

            levelSelectFrame.setVisible(false);

        } else if (e.getActionCommand().equals("Level Six")) {

            ball.level = 6;

            ball.positionX = 350;
            ball.positionY = 500;

            levelFrame.setVisible(true);

            levelSelectFrame.setVisible(false);

        } else if (e.getActionCommand().equals("Level Seven")) {

            ball.level = 7;

            ball.positionX = 100;
            ball.positionY = 100;

            levelFrame.setVisible(true);

            levelSelectFrame.setVisible(false);

        } else if (e.getActionCommand().equals("Level Eight")) {

            ball.level = 8;

            ball.positionX = 250;
            ball.positionY = 750;

            levelFrame.setVisible(true);

            levelSelectFrame.setVisible(false);

        } else if (e.getActionCommand().equals("Level Nine")) {

            ball.level = 9;

            ball.positionX = 500;
            ball.positionY = 550;

            levelFrame.setVisible(true);

            levelSelectFrame.setVisible(false);

        } else if (e.getActionCommand().equals("Level Ten")) {

            ball.level = 10;

            ball.positionX = 100;
            ball.positionY = 100;

            levelFrame.setVisible(true);

            levelSelectFrame.setVisible(false);
        }
    }

    @Override

    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_RIGHT:
                if (moving == false) {
                    moving = true;

                    Timer timer = new Timer();
                    TimerTask moveRight = new TimerTask() {

                        //Start of run (timer) method
                        public void run() {
                            try {
                                Robot robot = new Robot();

                                int y = ball.positionY / 2 + 12;

                                int x = ball.positionX / 2 + 26;

                                if (tracing) {
                                    System.out.println("");
                                    System.out.println("X: " + ball.positionX);
                                    System.out.println("Y: " + ball.positionY);
                                }

                                color = robot.getPixelColor(x, y);

                                if (tracing) {
                                    System.out.println("");
                                    System.out.println("Red   = " + color.getRed());
                                    System.out.println("Green = " + color.getGreen());
                                    System.out.println("Blue  = " + color.getBlue());
                                }

                            } catch (AWTException e) {
                                e.printStackTrace();
                            }
                            if ((color.getRed() == 255) && (color.getGreen() == 105) && (color.getBlue() == 180)) {
                                stopRight = false;
                            }
                            if ((color.getRed() == 51) && (color.getGreen() == 204) && (color.getBlue() == 255)) {
                                stopRight = true;
                            }
                            if (space) {
                                timer.cancel();
                                moving = false;
                                space = false;
                            } else if ((color.getRed() == 0) && (color.getGreen() == 0) && (color.getBlue() == 255)) {
                                timer.cancel();
                                moving = false;
                            } else if ((color.getRed() == 124) && (color.getGreen() == 252) && (color.getBlue() == 0)) {
                                timer.cancel();
                                moving = false;
                                switch (ball.level) {
                                    case 1:
                                        ball.positionX = 350;
                                        ball.positionY = 450;
                                        break;
                                    case 2:
                                        ball.positionX = 100;
                                        ball.positionY = 100;
                                        break;
                                    case 3:
                                    case 4:
                                    case 6:
                                        ball.positionX = 100;
                                        ball.positionY = 100;
                                        break;
                                    case 5:
                                        ball.positionX = 350;
                                        ball.positionY = 500;
                                        break;
                                    case 7:
                                        ball.positionX = 250;
                                        ball.positionY = 750;
                                        break;
                                    case 8:
                                        ball.positionX = 500;
                                        ball.positionY = 550;
                                        break;
                                    case 9:
                                        ball.positionX = 100;
                                        ball.positionY = 100;
                                        break;
                                    default:
                                        Object[] options = {"Restart", "Menu", "Exit"};
                                        int a = JOptionPane.showOptionDialog(levelFrame, "What do you want to do?", "Great Job! You won!",
                                                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                                        if (tracing) {
                                            System.out.println("");
                                            System.out.println("Finished Choice: " + a);
                                        }
                                        if (a == 0) {
                                            ball.level = 0;
                                            ball.positionX = 100;
                                            ball.positionY = 450;
                                        }
                                        if (a == 1) {
                                            menuFrame.setVisible(true);
                                            levelFrame.setVisible(false);
                                            ball.positionX = 100;
                                            ball.positionY = 450;
                                        }
                                        if (a == 2) {
                                            System.exit(0);
                                        }
                                        break;
                                }
                                ball.level++;
                                ball.repaint();
                            } else if ((color.getRed() == 255) && (color.getGreen() == 140) && (color.getBlue() == 0)) {
                                if (ball.positionX / 2 + 25 == ball.portalXCoordinates[0] && ball.positionY / 2 == ball.portalYCoordinates[0]) {
                                    ball.positionX = ball.portalXCoordinates[1] * 2;
                                    ball.positionY = ball.portalYCoordinates[1] * 2;
                                } else if (ball.positionX / 2 + 25 == ball.portalXCoordinates[1] && ball.positionY / 2 == ball.portalYCoordinates[1]) {
                                    ball.positionX = ball.portalXCoordinates[0] * 2;
                                    ball.positionY = ball.portalYCoordinates[0] * 2;
                                }
                                ball.repaint();
                            } else {
                                if (stopRight == true && (color.getRed() == 0) && (color.getGreen() == 0) && (color.getBlue() == 0)) {
                                    timer.cancel();
                                    moving = false;
                                } else {
                                    if (tracing) {
                                        System.out.println("");
                                        System.out.println("StopRight: " + stopRight);
                                    }
                                    ball.positionX += 5;
                                    if (ball.positionX > 999) {
                                        ball.positionX = 0;
                                    }
                                }
                            }
                            ball.repaint();
                            //End of run (timer) method

                        }

                    };
                    timer.scheduleAtFixedRate(moveRight, 0, 15);
                }
                break;

            case KeyEvent.VK_UP:
                if (moving == false) {
                    moving = true;
                    Timer timer = new Timer();

                    TimerTask moveUp = new TimerTask() {

                        //Start of run (timer) method
                        public void run() {
                            try {
                                Robot robot = new Robot();
                                int y = ball.positionY / 2 - 1;
                                int x = ball.positionX / 2 + 12;

                                if (tracing) {
                                    System.out.println("");
                                    System.out.println("X: " + ball.positionX);
                                    System.out.println("Y: " + ball.positionY);
                                }

                                color = robot.getPixelColor(x, y);

                                if (tracing) {
                                    System.out.println("");
                                    System.out.println("Red   = " + color.getRed());
                                    System.out.println("Green = " + color.getGreen());
                                    System.out.println("Blue  = " + color.getBlue());
                                }

                            } catch (AWTException e) {
                                e.printStackTrace();
                            }
                            if ((color.getRed() == 255) && (color.getGreen() == 105) && (color.getBlue() == 180)) {
                                stopUp = false;
                            }
                            if ((color.getRed() == 51) && (color.getGreen() == 204) && (color.getBlue() == 255)) {
                                stopUp = true;
                            }
                            if (space) {
                                timer.cancel();
                                moving = false;
                                space = false;
                            } else if ((color.getRed() == 0) && (color.getGreen() == 0) && (color.getBlue() == 255)) {
                                timer.cancel();
                                moving = false;
                            } else if ((color.getRed() == 124) && (color.getGreen() == 252) && (color.getBlue() == 0)) {
                                timer.cancel();
                                moving = false;
                                switch (ball.level) {
                                    case 1:
                                        ball.positionX = 350;
                                        ball.positionY = 450;
                                        break;
                                    case 2:
                                        ball.positionX = 100;
                                        ball.positionY = 100;
                                        break;
                                    case 3:
                                    case 4:
                                    case 6:
                                        ball.positionX = 100;
                                        ball.positionY = 100;
                                        break;
                                    case 5:
                                        ball.positionX = 350;
                                        ball.positionY = 500;
                                        break;
                                    case 7:
                                        ball.positionX = 250;
                                        ball.positionY = 750;
                                        break;
                                    case 8:
                                        ball.positionX = 500;
                                        ball.positionY = 550;
                                        break;
                                    case 9:
                                        ball.positionX = 100;
                                        ball.positionY = 100;
                                        break;
                                    default:
                                        Object[] options = {"Restart", "Menu", "Exit"};
                                        int a = JOptionPane.showOptionDialog(levelFrame, "What do you want to do?", "Great Job! You won!",
                                                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                                        if (tracing) {
                                            System.out.println("");
                                            System.out.println("Finished Choice: " + a);
                                        }
                                        if (a == 0) {
                                            ball.level = 0;
                                            ball.positionX = 100;
                                            ball.positionY = 450;
                                        }
                                        if (a == 1) {
                                            menuFrame.setVisible(true);
                                            levelFrame.setVisible(false);
                                            ball.positionX = 100;
                                            ball.positionY = 450;
                                        }
                                        if (a == 2) {
                                            System.exit(0);
                                        }
                                        break;
                                }
                                ball.level++;
                                ball.repaint();
                            } else if ((color.getRed() == 255) && (color.getGreen() == 140) && (color.getBlue() == 0)) {
                                if (ball.positionX / 2 == ball.portalXCoordinates[0] && ball.positionY / 2 - 25 == ball.portalYCoordinates[0]) {
                                    ball.positionX = ball.portalXCoordinates[1] * 2;
                                    ball.positionY = ball.portalYCoordinates[1] * 2;
                                } else if (ball.positionX / 2 == ball.portalXCoordinates[1] && ball.positionY / 2 - 25 == ball.portalYCoordinates[1]) {
                                    ball.positionX = ball.portalXCoordinates[0] * 2;
                                    ball.positionY = ball.portalYCoordinates[0] * 2;
                                }
                                ball.repaint();
                            } else {
                                if (stopUp == true && (color.getRed() == 0) && (color.getGreen() == 0) && (color.getBlue() == 0)) {
                                    timer.cancel();
                                    moving = false;
                                } else {
                                    if (tracing) {
                                        System.out.println("");
                                        System.out.println("StopUp: " + stopUp);
                                    }
                                    ball.positionY -= 5;
                                    if (ball.positionY < 1) {
                                        ball.positionY = 1000;
                                    }
                                }
                            }
                            ball.repaint();
                        }
                        //End of run (timer) method

                    };
                    timer.scheduleAtFixedRate(moveUp, 0, 15);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (moving == false) {
                    moving = true;
                    Timer timer = new Timer();
                    TimerTask moveLeft = new TimerTask() {

                        //Start of run (timer) method
                        public void run() {
                            try {
                                Robot robot = new Robot();
                                int y = ball.positionY / 2 + 12;
                                int x = ball.positionX / 2 - 1;

                                if (tracing) {
                                    System.out.println("");
                                    System.out.println("X: " + ball.positionX);
                                    System.out.println("Y: " + ball.positionY);
                                }

                                color = robot.getPixelColor(x, y);

                                if (tracing) {
                                    System.out.println("");
                                    System.out.println("Red   = " + color.getRed());
                                    System.out.println("Green = " + color.getGreen());
                                    System.out.println("Blue  = " + color.getBlue());
                                }

                            } catch (AWTException e) {
                                e.printStackTrace();
                            }
                            if ((color.getRed() == 255) && (color.getGreen() == 105) && (color.getBlue() == 180)) {
                                stopLeft = false;
                            }
                            if ((color.getRed() == 51) && (color.getGreen() == 204) && (color.getBlue() == 255)) {
                                stopLeft = true;
                            }

                            if (space) {
                                timer.cancel();
                                moving = false;
                                space = false;
                            } else if ((color.getRed() == 0) && (color.getGreen() == 0) && (color.getBlue() == 255)) {
                                timer.cancel();
                                moving = false;
                            } else if ((color.getRed() == 124) && (color.getGreen() == 252) && (color.getBlue() == 0)) {
                                timer.cancel();
                                moving = false;
                                switch (ball.level) {
                                    case 1:
                                        ball.positionX = 350;
                                        ball.positionY = 450;
                                        break;
                                    case 2:
                                        ball.positionX = 100;
                                        ball.positionY = 100;
                                        break;
                                    case 3:
                                    case 4:
                                    case 6:
                                        ball.positionX = 100;
                                        ball.positionY = 100;
                                        break;
                                    case 5:
                                        ball.positionX = 350;
                                        ball.positionY = 500;
                                        break;
                                    case 7:
                                        ball.positionX = 250;
                                        ball.positionY = 750;
                                        break;
                                    case 8:
                                        ball.positionX = 500;
                                        ball.positionY = 550;
                                        break;
                                    case 9:
                                        ball.positionX = 100;
                                        ball.positionY = 100;
                                        break;
                                    default:
                                        Object[] options = {"Restart", "Menu", "Exit"};
                                        int a = JOptionPane.showOptionDialog(levelFrame, "What do you want to do?", "Great Job! You won!",
                                                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                                        if (tracing) {
                                            System.out.println("");
                                            System.out.println("Finished Choice: " + a);
                                        }
                                        if (a == 0) {
                                            ball.level = 0;
                                            ball.positionX = 100;
                                            ball.positionY = 450;
                                        }
                                        if (a == 1) {
                                            menuFrame.setVisible(true);
                                            levelFrame.setVisible(false);
                                            ball.positionX = 100;
                                            ball.positionY = 450;
                                        }
                                        if (a == 2) {
                                            System.exit(0);
                                        }
                                        break;
                                }
                                ball.level++;
                                ball.repaint();
                            } else if ((color.getRed() == 255) && (color.getGreen() == 140) && (color.getBlue() == 0)) {
                                if (ball.positionX / 2 - 25 == ball.portalXCoordinates[0] && ball.positionY / 2 == ball.portalYCoordinates[0]) {
                                    ball.positionX = ball.portalXCoordinates[1] * 2;
                                    ball.positionY = ball.portalYCoordinates[1] * 2;
                                } else if (ball.positionX / 2 - 25 == ball.portalXCoordinates[1] && ball.positionY / 2 == ball.portalYCoordinates[1]) {
                                    ball.positionX = ball.portalXCoordinates[0] * 2;
                                    ball.positionY = ball.portalYCoordinates[0] * 2;
                                }
                                ball.repaint();
                            } else {
                                if (stopLeft == true && (color.getRed() == 0) && (color.getGreen() == 0) && (color.getBlue() == 0)) {
                                    timer.cancel();
                                    moving = false;
                                } else {
                                    if (tracing) {
                                        System.out.println("");
                                        System.out.println("StopLeft: " + stopLeft);
                                    }
                                    ball.positionX -= 5;
                                    if (ball.positionX < 1) {
                                        ball.positionX = 1000;
                                    }
                                }
                            }
                            ball.repaint();
                        }
                        //End of run (timer) method

                    };
                    timer.scheduleAtFixedRate(moveLeft, 0, 15);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (moving == false) {
                    moving = true;
                    Timer timer = new Timer();
                    TimerTask moveDown = new TimerTask() {

                        //Start of run (timer) method
                        public void run() {
                            try {
                                Robot robot = new Robot();
                                int y = ball.positionY / 2 + 26;
                                int x = ball.positionX / 2 + 12;

                                if (tracing) {
                                    System.out.println("");
                                    System.out.println("X: " + ball.positionX);
                                    System.out.println("Y: " + ball.positionY);
                                }

                                color = robot.getPixelColor(x, y);

                                if (tracing) {
                                    System.out.println("");
                                    System.out.println("Red   = " + color.getRed());
                                    System.out.println("Green = " + color.getGreen());
                                    System.out.println("Blue  = " + color.getBlue());
                                }

                            } catch (AWTException e) {
                                e.printStackTrace();
                            }
                            if ((color.getRed() == 255) && (color.getGreen() == 105) && (color.getBlue() == 180)) {
                                stopDown = false;
                            }
                            if ((color.getRed() == 51) && (color.getGreen() == 204) && (color.getBlue() == 255)) {
                                stopDown = true;
                            }
                            if (space) {
                                timer.cancel();
                                moving = false;
                                space = false;
                            } else if ((color.getRed() == 0) && (color.getGreen() == 0) && (color.getBlue() == 255)) {
                                timer.cancel();
                                moving = false;
                            } else if ((color.getRed() == 124) && (color.getGreen() == 252) && (color.getBlue() == 0)) {
                                timer.cancel();
                                moving = false;
                                switch (ball.level) {
                                    case 1:
                                        ball.positionX = 350;
                                        ball.positionY = 450;
                                        break;
                                    case 2:
                                        ball.positionX = 100;
                                        ball.positionY = 100;
                                        break;
                                    case 3:
                                    case 4:
                                    case 6:
                                        ball.positionX = 100;
                                        ball.positionY = 100;
                                        break;
                                    case 5:
                                        ball.positionX = 350;
                                        ball.positionY = 500;
                                        break;
                                    case 7:
                                        ball.positionX = 250;
                                        ball.positionY = 750;
                                        break;
                                    case 8:
                                        ball.positionX = 500;
                                        ball.positionY = 550;
                                        break;
                                    case 9:
                                        ball.positionX = 100;
                                        ball.positionY = 100;
                                        break;
                                    default:
                                        Object[] options = {"Restart", "Menu", "Exit"};
                                        int a = JOptionPane.showOptionDialog(levelFrame, "What do you want to do?", "Great Job! You won!",
                                                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                                        if (tracing) {
                                            System.out.println("");
                                            System.out.println("Finished Choice: " + a);
                                        }
                                        if (a == 0) {
                                            ball.level = 0;
                                            ball.positionX = 100;
                                            ball.positionY = 450;
                                        }
                                        if (a == 1) {
                                            menuFrame.setVisible(true);
                                            levelFrame.setVisible(false);
                                            ball.positionX = 100;
                                            ball.positionY = 450;
                                        }
                                        if (a == 2) {
                                            System.exit(0);
                                        }
                                        break;
                                }
                                ball.level++;
                                ball.repaint();
                            } else if ((color.getRed() == 255) && (color.getGreen() == 140) && (color.getBlue() == 0)) {
                                if (ball.positionX / 2 == ball.portalXCoordinates[0] && ball.positionY / 2 + 25 == ball.portalYCoordinates[0]) {
                                    ball.positionX = ball.portalXCoordinates[1] * 2;
                                    ball.positionY = ball.portalYCoordinates[1] * 2;
                                } else if (ball.positionX / 2 == ball.portalXCoordinates[1] && ball.positionY / 2 + 25 == ball.portalYCoordinates[1]) {
                                    ball.positionX = ball.portalXCoordinates[0] * 2;
                                    ball.positionY = ball.portalYCoordinates[0] * 2;
                                }
                                ball.repaint();
                            } else {
                                if (stopDown == true && (color.getRed() == 0) && (color.getGreen() == 0) && (color.getBlue() == 0)) {
                                    timer.cancel();
                                    moving = false;
                                } else {
                                    if (tracing) {
                                        System.out.println("");
                                        System.out.println("StopDown: " + stopDown);
                                    }
                                    ball.positionY += 5;
                                    if (ball.positionY > 999) {
                                        ball.positionY = 0;
                                    }
                                }
                            }

                            ball.repaint();
                        }
                        //End of run (timer) method

                    };
                    timer.scheduleAtFixedRate(moveDown, 0, 15);
                }
                break;
            case KeyEvent.VK_SPACE:
                switch (ball.level) {
                    case 2:
                        ball.positionX = 350;
                        ball.positionY = 450;
                        break;
                    case 3:
                        ball.positionX = 100;
                        ball.positionY = 100;
                        break;
                    case 4:
                    case 5:
                    case 7:
                        ball.positionX = 100;
                        ball.positionY = 100;
                        break;
                    case 6:
                        ball.positionX = 350;
                        ball.positionY = 500;
                        break;
                    case 8:
                        ball.positionX = 250;
                        ball.positionY = 750;
                        break;
                    case 9:
                        ball.positionX = 500;
                        ball.positionY = 550;
                        break;
                    case 10:
                        ball.positionX = 100;
                        ball.positionY = 100;
                        break;
                    default:
                        ball.positionX = 100;
                        ball.positionY = 450;
                        break;
                }
                ball.repaint();
                space = true;
                break;
            case KeyEvent.VK_ESCAPE:
                if (moving == false) {
                    Object[] options = {"Restart", "Menu", "Exit"};
                    int a = JOptionPane.showOptionDialog(levelFrame, "Use the arrow keys to move the ball to the GREEN FINISH SQUARE.\n\n"
                                    + "DARK BLUE SQUARES are unpassable.\n\n"
                                    + "GREEN SQUARES are the finish points.\n\n"
                                    + "ORANGE SQUARES are Portals.\n\n"
                                    + "BLACK SQUARES WITH PINK TRIANGLES are One Ways.\n"
                                    + "(The ball will pass through as long as it is coming "
                                    + "from the PINK SIDE)\n\n"
                                    + "Press ESCAPE to resume.", "Paused - Level " + ball.level,
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (tracing) {
                        System.out.println("");
                        System.out.println("Paused Choice: " + a);
                    }
                    if (a == 0) {
                        ball.level = 1;
                        ball.positionX = 100;
                        ball.positionY = 450;
                        ball.repaint();
                    }
                    if (a == 1) {
                        menuFrame.setVisible(true);
                        levelFrame.setVisible(false);
                        ball.positionX = 100;
                        ball.positionY = 450;
                    }
                    if (a == 2) {
                        System.exit(0);
                    }
                }
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
