package touro.snake.strategy;

import touro.snake.Garden;
import touro.snake.Snake;

/**
 * An implementation of SnakeStrategy that has no effect.
 */
public class BlankStrategy implements SnakeStrategy {
    @Override
    public void turnSnake(Snake snake, Garden garden) {

    }
}
