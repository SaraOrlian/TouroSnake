package touro.snake.strategy;

import touro.snake.Garden;
import touro.snake.Snake;

/**
 * Interface for the Strategy Design Pattern used to turn the Snake.
 * https://en.wikipedia.org/wiki/Strategy_pattern
 */
public interface SnakeStrategy {

    /**
     * Turn the Snake based on parameters in the Garden.
     * @param snake
     * @param garden
     */
    void turnSnake(Snake snake, Garden garden);

}
