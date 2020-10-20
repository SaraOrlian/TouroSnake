package touro.snake;

/**
 * The possible directions that the Snake's head can be in.
 */
public enum Direction {
    North, East, South, West;

    /**
     *
     * @param direction
     * @return true if direction is a valid Direction that the Snake can turn to, otherwise false.
     */
    public boolean canTurnTo(Direction direction) {
        return this == direction ||
                ((this == North || this == South) && (direction == East || direction == West)) ||
                ((this == East || this == West) && (direction == North || direction == South));
    }

}
