import java.awt.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;

public class BallAnimation extends JPanel {

    //Tracing
    public boolean tracing = false;

    //Shared between classes, store elements of each level
    public int level = 1;
    public int positionX = 100;
    public int positionY = 450;
    public int[] blockXCoordinates;
    public int[] blockYCoordinates;
    public int[] portalXCoordinates;
    public int[] portalYCoordinates;
    public int[] oneWayXCoordinates;
    public int[] oneWayYCoordinates;
    public int[] oneWayTypes;

    //Creating new colors
    Color LIGHT_BLUE = new Color(51, 204, 255);
    Color LIGHT_GREEN = new Color(124, 252, 0);
    Color LIGHT_GREY = new Color(220, 220, 220);
    Color LIGHT_ORANGE = new Color(255, 140, 0);
    Color PINK = new Color(255, 105, 180);

    Scanner file;

    public void paint(Graphics g) {
        String basicFile = "BallPuzzleLevel";
        String fileName = basicFile + "" + level;

        try {
            file = new Scanner(new File(fileName + ".txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found 404");

        }

        g.setColor(LIGHT_BLUE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(LIGHT_GREY);
        for (int x = 0; x < 500; x += 25) {
            for (int y = 0; y < 500; y += 25) {
                g.drawRect(x, y, 25, 25);
            }
        }

        int numberOfBlocks = Integer.parseInt(file.nextLine());
        int numberOfOneWays = Integer.parseInt(file.nextLine());
        int numberOfPortals = Integer.parseInt(file.nextLine());

        file.nextLine();

        if (tracing) {
            System.out.println("Level: " + level);
            System.out.println("Number of Blue Blocks: " + numberOfBlocks);
            System.out.println("Number of One Way Blocks: " + numberOfOneWays);
            System.out.println("Number of Portal Blocks: " + numberOfPortals);
        }

        blockXCoordinates = new int[numberOfBlocks];
        blockYCoordinates = new int[numberOfBlocks];
        portalXCoordinates = new int[numberOfPortals];
        portalYCoordinates = new int[numberOfPortals];
        oneWayXCoordinates = new int[numberOfOneWays];
        oneWayYCoordinates = new int[numberOfOneWays];
        oneWayTypes = new int[numberOfOneWays];

        for (int a = 0; a < (numberOfBlocks); a++) {
            blockXCoordinates[a] = Integer.parseInt(file.nextLine())/2;
        }

        file.nextLine();

        for (int a = 0; a < (numberOfBlocks); a++) {
            blockYCoordinates[a] = Integer.parseInt(file.nextLine())/2;
        }

        file.nextLine();

        for (int a = 0; a < numberOfBlocks; a++) {
            g.setColor(Color.blue);
            g.fillRect(blockXCoordinates[a], blockYCoordinates[a], 25, 25);
            g.setColor(LIGHT_GREY);
            g.drawRect(blockXCoordinates[a], blockYCoordinates[a], 25, 25);
        }

        if (numberOfOneWays >= 1) {

            for (int a = 0; a < (numberOfOneWays); a++) {
                oneWayXCoordinates[a] = Integer.parseInt(file.nextLine())/2;
            }

            file.nextLine();

            for (int a = 0; a < (numberOfOneWays); a++) {
                oneWayYCoordinates[a] = Integer.parseInt(file.nextLine())/2;
            }

            file.nextLine();

            for (int a = 0; a < numberOfOneWays; a++) {
                g.setColor(PINK);
                g.fillRect(oneWayXCoordinates[a], oneWayYCoordinates[a], 25, 25);
                g.setColor(LIGHT_GREY);
                g.drawRect(oneWayXCoordinates[a], oneWayYCoordinates[a], 25, 25);
            }

            for (int a = 0; a < (numberOfOneWays); a++) {
                oneWayTypes[a] = Integer.parseInt(file.nextLine());
            }

            file.nextLine();

            for (int a = 0; a < numberOfOneWays; a++) {
                switch (oneWayTypes[a]) {
                    case 1:
                        drawNorthEastTriangle(g, a);
                        drawSouthEastTriangle(g, a);
                        g.setColor(LIGHT_GREY);
                        g.drawRect(oneWayXCoordinates[a], oneWayYCoordinates[a], 25, 25);
                        break;
                    case 2:
                        drawSouthEastTriangle(g, a);
                        drawSouthWestTriangle(g, a);
                        g.setColor(LIGHT_GREY);
                        g.drawRect(oneWayXCoordinates[a], oneWayYCoordinates[a], 25, 25);
                        break;
                    case 3:
                        drawSouthWestTriangle(g, a);
                        drawNorthWestTriangle(g, a);
                        g.setColor(LIGHT_GREY);
                        g.drawRect(oneWayXCoordinates[a], oneWayYCoordinates[a], 25, 25);
                        break;
                    case 4:
                        drawNorthWestTriangle(g, a);
                        drawNorthEastTriangle(g, a);
                        g.setColor(LIGHT_GREY);
                        g.drawRect(oneWayXCoordinates[a], oneWayYCoordinates[a], 25, 25);
                        break;
                }

            }

        }

        if (numberOfPortals >= 1) {

            for (int a = 0; a < (numberOfPortals); a++) {
                portalXCoordinates[a] = Integer.parseInt(file.nextLine())/2;
            }

            file.nextLine();

            for (int a = 0; a < (numberOfPortals); a++) {
                portalYCoordinates[a] = Integer.parseInt(file.nextLine())/2;
            }

            file.nextLine();

            for (int a = 0; a < numberOfPortals; a++) {
                g.setColor(LIGHT_ORANGE);
                g.fillRect(portalXCoordinates[a], portalYCoordinates[a], 25, 25);
                g.setColor(LIGHT_GREY);
                g.drawRect(portalXCoordinates[a], portalYCoordinates[a], 25, 25);
            }
        }

        int finishXCoordinate = Integer.parseInt(file.nextLine())/2;
        int finishYCoordinate = Integer.parseInt(file.nextLine())/2;

        g.setColor(LIGHT_GREEN);
        g.fillRect(finishXCoordinate, finishYCoordinate, 25, 25);
        g.setColor(LIGHT_GREY);
        g.drawRect(finishXCoordinate, finishYCoordinate, 25, 25);

        g.setColor(Color.red);

        g.fillOval(positionX/2, positionY/2, 25, 25);

    }

    void drawNorthWestTriangle(Graphics g, int a) {
        g.setColor(Color.black);
        int northWestTriangleX[] = {oneWayXCoordinates[a], oneWayXCoordinates[a], oneWayXCoordinates[a] + 25};
        int northWestTriangleY[] = {oneWayYCoordinates[a] + 25, oneWayYCoordinates[a], oneWayYCoordinates[a]};
        g.fillPolygon(northWestTriangleX, northWestTriangleY, 3);
    }

    void drawNorthEastTriangle(Graphics g, int a) {
        g.setColor(Color.black);
        int northEastTriangleX[] = {oneWayXCoordinates[a], oneWayXCoordinates[a] + 25, oneWayXCoordinates[a] + 25};
        int northEastTriangleY[] = {oneWayYCoordinates[a], oneWayYCoordinates[a], oneWayYCoordinates[a] + 25};
        g.fillPolygon(northEastTriangleX, northEastTriangleY, 3);
    }

    void drawSouthWestTriangle(Graphics g, int a) {
        g.setColor(Color.black);
        int southWestTriangleX[] = {oneWayXCoordinates[a], oneWayXCoordinates[a], oneWayXCoordinates[a] + 25};
        int southWestTriangleY[] = {oneWayYCoordinates[a], oneWayYCoordinates[a] + 25, oneWayYCoordinates[a] + 25};
        g.fillPolygon(southWestTriangleX, southWestTriangleY, 3);
    }

    void drawSouthEastTriangle(Graphics g, int a) {
        g.setColor(Color.black);
        int southEastTriangleX[] = {oneWayXCoordinates[a], oneWayXCoordinates[a] + 25, oneWayXCoordinates[a] + 25};
        int southEastTriangleY[] = {oneWayYCoordinates[a] + 25, oneWayYCoordinates[a] + 25, oneWayYCoordinates[a]};
        g.fillPolygon(southEastTriangleX, southEastTriangleY, 3);
    }

}
