import javax.swing.*;
import java.awt.*;

/**
 * Driver for minesweeper game
 */
public class MinesweeperDriver {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ctrain Studios Presents : Minesweeper");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 900));
        frame.add(new MinesweeperGUI(800, 900, frame));

        frame.setVisible(true);
    }
}
