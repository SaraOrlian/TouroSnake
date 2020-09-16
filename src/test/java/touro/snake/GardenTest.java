package touro.snake;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GardenTest {

    @Test
    public void moveSnake() {
        /*
        Tests that snake moves and that when snake's move does not result
        in death the snake grows.
         */
        //given
        Snake snake = mock(Snake.class);
        FoodFactory foodFactory = mock(FoodFactory.class);
        Square square = mock(Square.class);
        Garden garden = new Garden(snake, foodFactory);

        doReturn(true).when(snake).inBounds();
        doReturn(false).when(snake).eatsSelf();
        doReturn(square).when(snake).getHead();
        //Food food = mock(Food.class);
        //need to be able to access food object inside of
        //Garden in order for test to pass

        //when
        garden.moveSnake();

        //then
        verify(snake).move();
        verify(snake).grow();
    }

    @Test
    public void createFoodIfNecessary() {
        throw new UnsupportedOperationException("Not Implemented Yet.");
    }
}