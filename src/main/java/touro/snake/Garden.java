package touro.snake;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;


/**
 * A model that contains the Snake and Food and is responsible for logic of moving the Snake,
 * seeing that food has been eaten and generating new food.
 */
public class Garden {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 40;

    private final Snake snake;
    private final FoodFactory foodFactory;
    private Food food;
    private Clip clip;

    public Garden(Snake snake, FoodFactory foodFactory, Clip clip) {
        this.snake = snake;
        this.foodFactory = foodFactory;
        this.clip = clip;
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    /**
     * Moves the snake, checks to see if food has been eaten and creates food if necessary
     *
     * @return true if the snake is still alive, otherwise false.
     */
    public boolean advance() {
        if (moveSnake()) {
            createFoodIfNecessary();
            return true;
        }
        return false;
    }

    /**
     * Moves the Snake, eats the Food or collides with the wall (edges of the Garden), or eats self.
     *
     * @return true if the Snake is still alive, otherwise false.
     */
    boolean moveSnake() {
        snake.getStrategy().turnSnake(snake, this);

        snake.move();

        //if collides with wall or self
        if (!snake.inBounds() || snake.eatsSelf()) {
            return false;
        }

        //if snake eats the food
        if (snake.getHead().equals(food)) {
            //add square to snake
            snake.grow();
            //make noise
            playSound();
            //remove food
            food = null;
        }
        return true;
    }

    /**
     * Creates a Food if there isn't one, making sure it's not already on a Square occupied by the Snake.
     */
    void createFoodIfNecessary() {
        //if snake ate food, create new one
        if (food == null) {
            food = foodFactory.newInstance();

            //if new food on snake, put it somewhere else
            while (snake.contains(food)) {
                food = foodFactory.newInstance();
            }
        }
    }

    /**
     * Plays sound from .wav file found in resources folder
     */
    private void playSound() {
        try {
            clip.setMicrosecondPosition(0); //restart clip
            clip.start();

        } catch (Exception e) {
            System.out.println("Error found when trying to play EatNoise");
            e.printStackTrace();
        }
    }





}
