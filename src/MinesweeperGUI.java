import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main panel that organizes the game and its various
 * components
 *
 */
public class MinesweeperGUI extends JPanel {

    private int gridDimension;  //amount of squares per side of grid (ex 10 by 10)
    private int bombs;          //number of bombs in grid
    private int gridSize;       //Size of actual grid (ex 700 pix square)

    private static final int TEXTFIELD_SIZE = 1;

    private JLabel bombLabel, sizeLabel, titleLabel;
    private JTextField bombField, sizeField;
    private JButton resetButton;
    private JRadioButton flagBombButton;

    private JPanel controlPanel,titlePanel;
    private GridGUI grid;


    /**
     * Constructor
     */
    public MinesweeperGUI(int width, int height, JFrame frame){

        bombs = 30;
        gridDimension = 10;

        gridSize = 4 * width/5;

        titlePanel = new JPanel();
        createTitlePanel();

        grid = new GridGUI(gridDimension, gridSize, gridSize, bombs);

        controlPanel = new JPanel();
        createControlPanel();
        controlPanel.setPreferredSize(new Dimension(250, 100));


        grid.setPreferredSize(new Dimension(gridSize,gridSize));
        grid.setBackground(Color.gray);

        add(titlePanel, BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

    }


    /**
     * Creates TitlePanel
     */
    public void createTitlePanel(){
        titleLabel = new JLabel("Minesweeper");
        titleLabel.setFont(new Font("verdanna",Font.BOLD,50));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(Color.gray);
    }


    /**
     * Resets game
     */
    public void resetGame(){
        bombs = Integer.parseInt(bombField.getText());
        gridDimension = Integer.parseInt(sizeField.getText());

        grid.resetGrid(gridDimension,bombs);
    }


    /**
     * Allows user to flag bombs
     */
    public void flagBombs(){
        grid.setFlagBombs();
    }


    /**
     * Creates control panel with slider controls
     */
    public void createControlPanel(){
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1,4));

        JPanel buttonPanel = new JPanel();

        sizeField = new JTextField("10",TEXTFIELD_SIZE);
        bombField = new JTextField("30",TEXTFIELD_SIZE);

        bombLabel = new JLabel("Bombs:");
        sizeLabel = new JLabel(" Size:");

        labelPanel.add(bombLabel);
        labelPanel.add(bombField);
        labelPanel.add(sizeLabel);
        labelPanel.add(sizeField);

        resetButton = new JButton("Reset");
        ActionListener listener = new GameListener();
        resetButton.addActionListener(listener);

        flagBombButton = new JRadioButton("Flag Bombs");
        flagBombButton.addActionListener(listener);

        buttonPanel.add(resetButton, BorderLayout.WEST);
        buttonPanel.add(flagBombButton, BorderLayout.EAST);

        controlPanel.add(buttonPanel, BorderLayout.CENTER);
        controlPanel.add(labelPanel, BorderLayout.SOUTH);
        controlPanel.setBackground(Color.gray);
    }


    /**
     * Paints graphics
     * @param g
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(0,0,getWidth(),getHeight());
        g.fillRect(0,0,getWidth(),getHeight());
    }


    /**
     * Listens to Reset Button
     */
    private class GameListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == resetButton)
                resetGame();
            else {
                flagBombs();
            }
        }
    }
}
