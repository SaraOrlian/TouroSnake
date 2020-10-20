package touro.snake;

import touro.snake.strategy.SnakeStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Model object that represents the Snake and allows it to grow, turn and move.
 */
public class Snake {

    private static final int START_LENGTH = 6;

    private final List<Square> squares = new ArrayList<>();

    private final SnakeHeadStateMachine snakeHeadStateMachine;

    private final SnakeStrategy snakeStrategy;

    private boolean grow = false;

    public Snake(
            SnakeHeadStateMachine snakeHeadStateMachine,
            SnakeStrategy snakeStrategy
    ) {
        this.snakeHeadStateMachine = snakeHeadStateMachine;
        this.snakeStrategy = snakeStrategy;
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

    public boolean canTurnTo(Direction direction) {
        return snakeHeadStateMachine.getDirection().canTurnTo(direction);
    }

    public SnakeStrategy getStrategy() {
        return snakeStrategy;
    }

    public Square getHead() {
        return squares.get(0);
    }

    public List<Square> getBody() {
        return squares.subList(1, squares.size()-1);
    }

    /**
     * @return The location of the new head after it has been moved.
     */
    public Square newHead() {
        //get direction
        Direction direction = snakeHeadStateMachine.getDirection();

        //save head position in variable previous square
        return getHead().moveTo(direction);
    }

    /**
     * Moves the Snake forward in whatever direction the head is facing.
     */
    public void move() {
        squares.add(0, newHead());
        if (!getGrow()) {
            squares.remove(squares.size() - 1);
        } else {
            setGrow(false);
        }
    }

    /**
     * @param square
     * @return true if the Food intersects with the Snake, otherwise false.
     */
    public boolean contains(Square square) {
        return squares.contains(square);
    }

    /**
     * @return true if the Snake is within the bounds of the Garden, otherwise false.
     */
    public boolean inBounds() {
        return getHead().inBounds();
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
