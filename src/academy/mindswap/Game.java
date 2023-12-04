package academy.mindswap;

import academy.mindswap.field.Field;
import academy.mindswap.field.Position;
import academy.mindswap.gameobjects.fruit.Fruit;
import academy.mindswap.gameobjects.snake.Direction;
import academy.mindswap.gameobjects.snake.Snake;
import com.googlecode.lanterna.input.Key;

import java.util.Random;

public class Game {

    private Snake snake;
    private int delay;
    private Fruit fruit;

    public Game(int cols, int rows, int delay) {
        this.delay = delay;
        Field.init(cols, rows);

        int initialSnakeCol = cols / 2;
        int initialSnakeRow = rows / 2;
        snake = new Snake();
        snake.initSnake(initialSnakeCol, initialSnakeRow);

        generateFruit();
    }

    public void start() throws InterruptedException {
        while (snake.isAlive()) {
            Thread.sleep(delay);
            Field.clearTail(snake);
            moveSnake();
            checkCollisions();
            Field.drawSnake(snake);
        }

        System.out.println("Game Over!");


        Thread.sleep(2000);
        System.exit(0);
    }

    private void moveSnake() {
        Key k = Field.readInput();

        if (k != null) {
            switch (k.getKind()) {
                case ArrowUp:
                    snake.setDirection(Direction.UP);
                    break;
                case ArrowDown:
                    snake.setDirection(Direction.DOWN);
                    break;
                case ArrowLeft:
                    snake.setDirection(Direction.LEFT);
                    break;
                case ArrowRight:
                    snake.setDirection(Direction.RIGHT);
                    break;
            }
        }


        snake.move();


        if (snake.getHead().equals(fruit.getPosition())) {
            snake.increaseSize();
            generateFruit();
        }

        Field.clearTail(snake);
        Field.drawSnake(snake);
    }

    private void checkCollisions() {
        Position head = snake.getHead();

        // Check collision with walls
        if (head.getCol() == 0 || head.getCol() == Field.getWidth() - 1 || head.getRow() == 0 || head.getRow() == Field.getHeight() - 1) {
            snake.die();
        }

        // Check collision with itself
        for (Position bodyPart : snake.getFullSnake()) {
            if (head.equals(bodyPart) && !bodyPart.equals(head)) {
                snake.die();
                break;
            }
        }
    }

    private void generateFruit() {
        Random random = new Random();
        int col = random.nextInt(Field.getWidth() - 2) + 1;
        int row = random.nextInt(Field.getHeight() - 2) + 1;
        fruit = new Fruit(new Position(col, row));
        Field.drawFruit(fruit);
    }


}
