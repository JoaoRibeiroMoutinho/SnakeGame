package academy.mindswap.field;

public class Position {

    private int col;
    private int row;

    public Position(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void incrementCol() {
        col++;
    }

    public void decrementCol() {
        col--;
    }

    public void incrementRow() {
        row++;
    }

    public void decrementRow() {
        row--;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Position position = (Position) obj;

        if (col != position.col) return false;
        return row == position.row;
    }

    @Override
    public int hashCode() {
        int result = col;
        result = 31 * result + row;
        return result;
    }
}