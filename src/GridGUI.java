import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.ArrayList;

/**
 * Controls grid and the minesweeper game
 */
public class GridGUI extends JPanel {

    private int rows, columns, width, height, bombs, covered;
    private boolean gameLost, gameWon, flagBomb;
    private ArrayList<Square> squares;

    private int [][] grid;


    /**
     * Constructor
     * @param dimension
     * @param width
     * @param height
     * @param bombs
     */
    public GridGUI(int dimension, int width, int height, int bombs){
        rows = dimension;
        columns = dimension;
        covered = rows * columns;
        this.width = width - 10;
        this.height = height - 10;
        this.bombs = bombs;


        gameLost = false;
        gameWon = false;
        flagBomb = false;

        MouseListener click = new ClickListener();
        addMouseListener(click);

        createGrid();
        fillGrid();
        createSquares();
    }


    /**
     * Resets Grid
     * @param dimension
     * @param bombs
     */
    public void resetGrid(int dimension, int bombs){
        rows = dimension;
        columns = dimension;
        this.bombs = bombs;

        gameLost = false;
        gameWon = false;
        covered = rows * columns;

        createGrid();
        fillGrid();
        createSquares();

        repaint();
    }


    /**
     * Checks if game is won
     */
    public void checkIfWon(){
        if(covered == bombs)
            gameWon = true;
    }


    /**
     * Sets flagBomb var
     */
    public void setFlagBombs(){
        flagBomb = !flagBomb;
    }


    /**
     * Flags bomb
     * @param x
     * @param y
     */
    public void flagBomb(int x, int y){
        for(Square s : squares){
            if(x > s.getX() && x < s.getX() + s.getWidth()){
                if(y > s.getY() && y < s.getY() + s.getHeight()) {
                    s.flag();
                }
            }
        }
        repaint();
    }


    /**
     * Paints graphical components
     * @param g
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);


        for(Square s : squares){
            Color textColor = setColor(s.getValue());
            g.setColor(textColor);
            g.setFont(new Font("verdanna", Font.BOLD,s.getWidth()/2));
            if(s.getValue().equals("9"))
                g.drawString("B",s.getX() + 2 * s.getWidth()/5,s.getY() + 3 * s.getHeight()/5);
            else
                g.drawString(s.getValue(),s.getX() + 2 * s.getWidth()/5,s.getY() + 3 * s.getHeight()/5);

            //covers square if needed
            g.setColor(Color.lightGray);
            if(s.getCovered()){
                if(s.getFlagged())
                    g.setColor(Color.red);
                g.fillRect(s.getX(),s.getY(),s.getWidth(),s.getHeight());
            }


            //draws border
            g.setColor(Color.black);
            g.drawRect(s.getX(),s.getY(),s.getWidth(),s.getHeight());

        }

        if(gameLost){
            g.setFont(new Font("verdanna",Font.BOLD,30));
            g.drawString("Better Luck Next time", width/2 - 145, height/2 - 60);
            g.drawString("Click to Reset", width/2 - 90, height/2);
        }
        if(gameWon){
            g.setFont(new Font("verdanna",Font.BOLD,30));
            g.drawString("Congratulations! You win", width/2 - 165, height/2 - 60);
            g.drawString("Click to Reset", width/2 - 90, height/2);
        }
    }


    /**
     * Sets the color of the text based on how many
     * bombs are in proximity
     * @param value
     * @return
     */
    public Color setColor(String value){
        switch (value){
            case ("1"):return Color.BLUE;
            case ("2"):return Color.GREEN;
            case ("3"):return Color.RED;
            case ("4"):return Color.PINK;
            case ("5"):return Color.CYAN;
            case ("6"):return Color.YELLOW;
            default:return Color.BLACK;
        }
    }


    /**
     * Creates array of squares that each represent
     * one block on the grid
     */
    public void createSquares(){
        squares = new ArrayList<>();

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                Square s = new Square(i * this.width/columns + 5, j * this.height/rows + 5, this.width/columns, this.height/rows,
                        j,i,grid[i][j] + "");
                squares.add(s);
            }
        }
    }

    /**
     * Creates new empty grid
     */
    public void createGrid(){
        grid = new int[rows][columns];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                grid[i][j] = 0;
            }
        }
    }


    /**
     * Fills grid with bombs
     */
    public void fillGrid(){

        for(int i = 0; i < bombs; i++){
            boolean validPosition = false;

            while(!validPosition){
                int potentialBombRow = (int)(Math.random() * rows);
                int potentialBombColumn = (int)(Math.random() * columns);
                if(grid[potentialBombRow][potentialBombColumn] != 9){
                    grid[potentialBombRow][potentialBombColumn] = 9;
                    validPosition = true;
                    incrementSquares(potentialBombRow, potentialBombColumn);
                }

            }
        }
    }


    /**
     * increments squares adjacent to bombs
     */
    public void incrementSquares(int bombRow, int bombColumn){
        if(validPosition(bombRow, bombColumn + 1) && grid[bombRow][bombColumn + 1] != 9)
            grid[bombRow][bombColumn + 1]++;
        if(validPosition(bombRow, bombColumn - 1) && grid[bombRow][bombColumn - 1] != 9)
            grid[bombRow][bombColumn - 1]++;

        if(validPosition(bombRow - 1, bombColumn - 1) && grid[bombRow - 1][bombColumn - 1] != 9)
            grid[bombRow - 1][bombColumn - 1]++;
        if(validPosition(bombRow - 1, bombColumn) && grid[bombRow - 1][bombColumn] != 9)
            grid[bombRow - 1][bombColumn]++;
        if(validPosition(bombRow - 1, bombColumn + 1) && grid[bombRow - 1][bombColumn + 1] != 9)
            grid[bombRow - 1][bombColumn + 1]++;

        if(validPosition(bombRow + 1, bombColumn - 1) && grid[bombRow + 1][bombColumn - 1] != 9)
            grid[bombRow + 1][bombColumn - 1]++;
        if(validPosition(bombRow + 1, bombColumn) && grid[bombRow + 1][bombColumn] != 9)
            grid[bombRow + 1][bombColumn]++;
        if(validPosition(bombRow + 1, bombColumn + 1) && grid[bombRow + 1][bombColumn + 1] != 9)
            grid[bombRow + 1][bombColumn + 1]++;
    }


    /**
     * Checks to see if position is within grid
     * @param row
     * @param column
     * @return
     */
    public boolean validPosition(int row, int column){
        if(row >= 0 && row < rows){
            if(column >= 0 && column < columns)
                return true;
        }
        return false;
    }


    /**
     * Removes block that has been clicked
     * @param x
     * @param y
     */
    public void removeBlock(int x, int y){
        for(Square s : squares){
            if(x > s.getX() && x < s.getX() + s.getWidth()){
                if(y > s.getY() && y < s.getY() + s.getHeight()) {
                    s.uncover();
                    if(s.getValue().equals(""))
                        uncoverSurroundings(s);
                    else
                        covered--;

                    if(s.getValue().equals("9"))
                        gameLost = true;
                }
            }
        }
        checkIfWon();
        repaint();
    }


    /**
     * Uncovers squares surrounding s
     * @param s
     */
    public void uncoverSurroundings(Square s){
        s.uncover();
        covered--;
        if(s.getValue().equals("")){
            if(validPosition(s.getRow(),s.getColumn() + 1)){
                Square toUncover = getSquare(s.getRow(),s.getColumn() + 1);
                if(toUncover.getCovered())
                    uncoverSurroundings(toUncover);
            }
            if(validPosition(s.getRow(),s.getColumn() - 1)){
                Square toUncover = getSquare(s.getRow(),s.getColumn() - 1);
                if(toUncover.getCovered())
                    uncoverSurroundings(toUncover);
            }

            if(validPosition(s.getRow() - 1,s.getColumn() - 1)){
                Square toUncover = getSquare(s.getRow() - 1,s.getColumn() - 1);
                if(toUncover.getCovered())
                    uncoverSurroundings(toUncover);
            }
            if(validPosition(s.getRow() - 1,s.getColumn())){
                Square toUncover = getSquare(s.getRow() - 1,s.getColumn());
                if(toUncover.getCovered())
                    uncoverSurroundings(toUncover);
            }
            if(validPosition(s.getRow() - 1,s.getColumn() + 1)){
                Square toUncover = getSquare(s.getRow() - 1,s.getColumn() + 1);
                if(toUncover.getCovered())
                    uncoverSurroundings(toUncover);
            }

            if(validPosition(s.getRow() + 1,s.getColumn() - 1)){
                Square toUncover = getSquare(s.getRow() + 1,s.getColumn() - 1);
                if(toUncover.getCovered())
                    uncoverSurroundings(toUncover);
            }
            if(validPosition(s.getRow() + 1,s.getColumn())){
                Square toUncover = getSquare(s.getRow() + 1,s.getColumn());
                if(toUncover.getCovered())
                    uncoverSurroundings(toUncover);
            }
            if(validPosition(s.getRow() + 1,s.getColumn() + 1)){
                Square toUncover = getSquare(s.getRow() + 1,s.getColumn() + 1);
                if(toUncover.getCovered())
                    uncoverSurroundings(toUncover);
            }
        }
    }


    /**
     * Returns square that matches
     * specified row and column
     * @param row
     * @param column
     * @return
     */
    public Square getSquare(int row, int column){
        for(Square s : squares){
            if(s.getRow() == row){
                if(s.getColumn() == column)
                    return s;
            }
        }
        return null;
    }


    /**
     * Listens for mouse clicks
     */
    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent event) {
            if(gameLost || gameWon)
                resetGrid(rows,bombs);
            else{
                int x = event.getX();
                int y = event.getY();
                if(flagBomb)
                    flagBomb(x,y);
                else
                    removeBlock(x,y);
            }
        }
    }
}
