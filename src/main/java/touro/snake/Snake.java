package touro.snake;

import java.util.ArrayList;
import java.util.List;

import static touro.snake.Direction.*;

/**
 * Model object that represents the Snake and allows it to grow, turn and move.
 */
public class Snake {

    private static final int START_LENGTH = 6;

    private final List<Square> squares = new ArrayList<>();

    private final SnakeHeadStateMachine snakeHeadStateMachine;

    private boolean grow = false;

    public Snake(SnakeHeadStateMachine snakeHeadStateMachine) {
        this.snakeHeadStateMachine = snakeHeadStateMachine;
        createSnake();
    }

    /**
     * Creates the Snake of length START_LENGTH, starting at the middle of the Garden.
     */
    private void createSnake() {
        int x = Garden.WIDTH / 2;
        int y = Garden.HEIGHT / 2;
        for (int i = 0; i < START_LENGTH; i++) {
            squares.add(new Square(x + i, y));
        }
    }

    public List<Square> getSquares() {
        return squares;
    }

    /**
     * Grows the Snake one square.
     */
    public void grow() {
        setGrow(true);
    }

    public void turnTo(Direction newDirection) {
        snakeHeadStateMachine.turnTo(newDirection);
    }

    public Square getHead() {
        return squares.get(0);
    }

    /**
     * Moves the Snake forward in whatever direction the head is facing.
     */
    public void move() {

        //get direction
        Direction direction = snakeHeadStateMachine.getDirection();

        //save head position in variable previous square
        Square previousHead = getHead();
        int x = previousHead.getX();
        int y = previousHead.getY();

        //move head one square in proper direction (assumes origin is on the top left corner)
        Square newSquare;
        switch (direction) {
            case North:
                newSquare = new Square(x, y - 1);
                break;
            case East:
                newSquare = new Square(x + 1, y);
                break;
            case South:
                newSquare = new Square(x, y + 1);
                break;
            case West:
                newSquare = new Square(x - 1, y);
                break;
            default:
                System.out.println("ERROR: Direction is not valid or is null");
                return;
        }
        squares.add(0, newSquare);
        if (!getGrow()) {
            squares.remove(squares.size() - 1);
        } else {
            setGrow(false);
        }


    }

    /**
     * @param food
     * @return true if the Food intersects with the Snake, otherwise false.
     */
    public boolean contains(Food food) {
        return squares.contains(food);
    }

    /**
     * @return true if the Snake is within the bounds of the Garden, otherwise false.
     */
    public boolean inBounds() {
        Square head = getHead();
        int x = head.getX();
        int y = head.getY();

        return x >= 0 && x < Garden.WIDTH && y >= 0 && y < Garden.HEIGHT;
    }

    /**
     * @return true if the head of the Snake has intersected with itself, otherwise false.
     */
    public boolean eatsSelf() {
        Square head = getHead();

        //snake eats itself if it hits its body at 4th square or beyond
        //loop starts at 4th square and checks if head coordinates matches body coordinates
        for (int i = 3; i < squares.size(); i++) {
            Square bodySquare = squares.get(i);
            if (bodySquare.equals(head)) {
                return true;
            }

        }
        return false;
    }


    public boolean getGrow() {
        return grow;
    }

    public void setGrow(boolean grow) {
        this.grow = grow;
    }
}
