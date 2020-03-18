/**
 * Represents a single square in the grid
 */
public class Square {

    private int x;
    private int y;
    private int width;
    private int height;
    private int row;
    private int column;
    private boolean covered, flagged;
    private String value;

    /**
     * Constructor
     */
    public Square(int x, int y, int width, int height, int row, int column, String value){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.row = row;
        this.column = column;

        if(value.equals("0"))
            this.value = "";
        else
            this.value = value;

        covered = true;
        flagged = false;
    }


    void uncover(){ covered = false; }

    void flag(){ flagged = true;}

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    int getColumn() {
        return column;
    }

    int getRow() {
        return row;
    }

    boolean getCovered(){
        return covered;
    }

    boolean getFlagged(){return flagged;}

    String getValue(){
        return value;
    }
}
