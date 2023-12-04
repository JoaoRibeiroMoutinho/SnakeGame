package academy.mindswap.gameobjects.snake;

import academy.mindswap.field.Position;

import java.util.LinkedList;

public class Snake {

    private final LinkedList<Position> body = new LinkedList<>();
    private Direction direction;
    private boolean alive;

    public void initSnake(int initialCol, int initialRow) {
        body.clear();
        direction = Direction.RIGHT;
        alive = true;

        for (int i = 2; i >= 0; i--) {
            body.add(new Position(initialCol - i, initialRow));
        }
    }

    public void increaseSize() {
        Position head = getHead();
        Position newHead = new Position(head.getCol(), head.getRow());
        body.addFirst(newHead);
    }

    public void move() {
        Position head = getHead();
        Position newHead = new Position(head.getCol(), head.getRow());

        switch (direction) {
            case UP:
                newHead.decrementRow();
                break;
            case DOWN:
                newHead.incrementRow();
                break;
            case LEFT:
                newHead.decrementCol();
                break;
            case RIGHT:
                newHead.incrementCol();
                break;
        }

        body.addFirst(newHead);
        body.removeLast();
    }

    public void setDirection(Direction newDirection) {
        direction = newDirection;
    }

    public void die() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public Position getHead() {
        return body.getFirst();
    }

    public LinkedList<Position> getFullSnake() {
        return body;
    }
}