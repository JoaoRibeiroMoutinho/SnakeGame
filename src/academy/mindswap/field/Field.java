
package academy.mindswap.field;

import academy.mindswap.gameobjects.fruit.Fruit;
import academy.mindswap.gameobjects.snake.Snake;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.LinkedList;
import java.util.Random;

public final class Field {

    private static final String BORDER_STRING = "▒";
    private static final String SNAKE_BODY_STRING = "#";
    private static final String SNAKE_HEAD_STRING = "0";
    private static final String FRUIT_STRING = "@";

    private static int width;
    private static int height;
    private static Screen screen;
    private static ScreenWriter screenWriter;

    private Field() {
    }

    public static void init(int width, int height) {
        screen = TerminalFacade.createScreen();

        Field.width = width;
        Field.height = height;
        screen.getTerminal().getTerminalSize().setColumns(width);
        screen.getTerminal().getTerminalSize().setRows(height);

        screenWriter = new ScreenWriter(screen);
        screen.setCursorPosition(null);
        screen.startScreen();

        drawWalls();


        int initialSnakeCol = width / 2;
        int initialSnakeRow = height / 2;
        Snake snake = new Snake();
        snake.initSnake(initialSnakeCol, initialSnakeRow);

        screen.refresh();
    }

    public static void drawSnake(Snake snake) {
        Terminal.Color snakeColor = Terminal.Color.GREEN;

        LinkedList<Position> snakeBody = snake.getFullSnake();

        for (Position p : snakeBody) {
            screen.putString(p.getCol(), p.getRow(), SNAKE_BODY_STRING, snakeColor, null);
        }

        Position head = snake.getHead();
        screen.putString(head.getCol(), head.getRow(), SNAKE_HEAD_STRING, snakeColor, null);

        screen.refresh();
    }

    public static void clearTail(Snake snake) {
        LinkedList<Position> snakeBody = snake.getFullSnake();
        Position tail = snakeBody.getLast();
        screen.putString(tail.getCol(), tail.getRow(), " ", null, null);
    }

    private static void drawWalls() {
        for (int i = 0; i < width; i++) {
            screenWriter.drawString(i, 0, BORDER_STRING);
            screenWriter.drawString(i, height - 1, BORDER_STRING);
        }

        for (int j = 0; j < height; j++) {
            screenWriter.drawString(0, j, BORDER_STRING);
            screenWriter.drawString(width - 1, j, BORDER_STRING);
        }
    }

    public static Key readInput() {
        return screen.readInput();
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
    private static Random random = new Random();

    public static void drawFruit(Fruit fruit) {
        Position position = fruit.getPosition();
        screen.putString(position.getCol(), position.getRow(), FRUIT_STRING, Terminal.Color.MAGENTA, null);
        screen.refresh();
    }

    private static Position generateRandomPosition() {
        int col = random.nextInt(width - 2) + 1;
        int row = random.nextInt(height - 2) + 1;
        return new Position(col, row);
    }

    public static void initFruit() {
        Fruit fruit = new Fruit(generateRandomPosition());
        drawFruit(fruit);
    }
}
