package touro.snake;

import org.junit.Test;
import org.mockito.Mockito;
import touro.snake.strategy.SnakeStrategy;

import javax.sound.sampled.Clip;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GardenTest {

    @Test
    public void moveSnake() {
        /*
        Tests that snake moves and that when snake's move does not result
        in death.
         */
        //given
        SnakeStrategy strategy = mock(SnakeStrategy.class);
        Snake snake = mock(Snake.class);
        FoodFactory foodFactory = mock(FoodFactory.class);
        Clip clip = mock(Clip.class);
        Garden garden = new Garden(snake, foodFactory, clip);

        doReturn(strategy).when(snake).getStrategy();
        doReturn(true).when(snake).inBounds();
        doReturn(false).when(snake).eatsSelf();
        Square square = mock(Square.class);
        doReturn(square).when(snake).getHead();

        //when and then
        assertTrue(garden.moveSnake());
        verify(snake).move();
    }

    @Test
    public void createFoodIfNecessary() {

        //given
        Snake snake = mock(Snake.class);
        FoodFactory foodFactory = mock(FoodFactory.class);
        Clip clip = mock(Clip.class);
        Garden garden = new Garden(snake, foodFactory, clip);
        when(foodFactory.newInstance()).thenReturn(mock(Food.class));

        //when
        garden.createFoodIfNecessary();

        //then
        verify(foodFactory).newInstance();
        assertNotNull(garden.getFood());
    }

    @Test
    public void playSound() {
        //given

        SnakeStrategy strategy = mock(SnakeStrategy.class);
        Snake snake = mock(Snake.class);
        FoodFactory foodFactory = mock(FoodFactory.class);
        Food food = new Food(50, 20);
        when(foodFactory.newInstance()).thenReturn(food);
        Clip clip = mock(Clip.class);
        Garden garden = new Garden(snake, foodFactory, clip);
        List<Square> squares = List.of(new Square(50, 20));

        doReturn(strategy).when(snake).getStrategy();
        when(snake.inBounds()).thenReturn(true);
        when(snake.eatsSelf()).thenReturn(false);
        when(snake.getHead()).thenReturn(squares.get(0));

        //when
        garden.createFoodIfNecessary();
        garden.moveSnake();

        //then
        verify(clip).start();


    }
}