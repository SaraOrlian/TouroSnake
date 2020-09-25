package touro.snake;

public class SnakeMain {

    public static void main(String[] args) {

        // Set up all class dependencies here.

        SnakeHeadStateMachine snakeHeadStateMachine = new SnakeHeadStateMachine(Direction.West);
        Snake snake = new Snake(snakeHeadStateMachine);
        FoodFactory foodFactory = new FoodFactory();
        Garden garden = new Garden(snake, foodFactory);
        GardenView gardenView = new GardenView(garden);
        SnakeKeyListener snakeKeyListener = new SnakeKeyListener(snake);

        GardenThread thread = new GardenThread(garden, gardenView);
        thread.start();
        BackgroundSound backgroundSound = new BackgroundSound();

        new SnakeFrame(gardenView, snakeKeyListener, backgroundSound).setVisible(true);
    }

}
