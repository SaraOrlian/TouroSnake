package touro.snake;

import touro.snake.strategy.astar.orlian.AStarStrategy;

import javax.swing.*;
import java.awt.*;

public class GardenView extends JComponent {

    private final Garden garden;
    public static final int CELL_SIZE = 10;
    AStarStrategy aStarStrategy;

    public GardenView(Garden garden, AStarStrategy aStarStrategy) {
        this.garden = garden;
        this.aStarStrategy = aStarStrategy;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGrass(g);
        paintSearchSpace(g);
        paintChosenPath(g);
        paintFood(g);
        paintSnake(g);
    }

    void paintGrass(Graphics g) {
        // Berger
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    void paintSnake(Graphics g) {
        g.setColor(Color.RED);
        for (Square s : garden.getSnake().getSquares()) {
            g.fillRect(s.getX() * CELL_SIZE, s.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    void paintFood(Graphics g) {
        // Berger
        if (garden.getFood() != null) {
            Food food = garden.getFood();
            g.setColor(Color.LIGHT_GRAY);

            int x = food.getX() * CELL_SIZE;
            int y = food.getY() * CELL_SIZE;
            g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        }
    }

    void paintSearchSpace(Graphics g) {
        g.setColor(Color.cyan);
        for (int ix = 0; ix < aStarStrategy.getSearchSpace().size(); ix++) {
            int x = aStarStrategy.getSearchSpace().get(ix).getX() * CELL_SIZE;
            int y = aStarStrategy.getSearchSpace().get(ix).getY() * CELL_SIZE;
            g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        }
    }

    void paintChosenPath(Graphics g) {
        g.setColor(Color.PINK);
        for (Square square : aStarStrategy.getPath()) {
            int x = square.getX() * CELL_SIZE;
            int y = square.getY() * CELL_SIZE;
            g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        }
    }
}

