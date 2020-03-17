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
    private boolean covered;
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
    }


    public void uncover(){
        this.covered = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean getCovered(){
        return covered;
    }

    public String getValue(){
        return value;
    }


}
