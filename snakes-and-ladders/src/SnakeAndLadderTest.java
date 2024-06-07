import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class SnakeAndLadderTest {
    private SnakeAndLadder snakeAndLadder;

    @Before
    public void setup() {
        snakeAndLadder = new SnakeAndLadder();
    }

    @Test
    public void testDice () {
        int dice = snakeAndLadder.rollDice();
        assertTrue(dice >= 1 && dice <= 6);
    }

    @Test
    public void testPlayerPositionWithSnakes(){
        int dice = 4;
        int playerPosition = 14;
        int expectedPlayerPosition = 7;

        Map<Integer, Integer> snakeLadder = new HashMap<>();
        snakeLadder.put(18, 7);
        snakeAndLadder.snakeLadder = snakeLadder;
        int actualPlayerPosition = snakeAndLadder.playerPosition(playerPosition, dice);
        assertEquals(expectedPlayerPosition, actualPlayerPosition);
    }

    @Test
    public void testPlayerPositionWithLadders(){
        int dice = 6;
        int playerPosition = 6;
        int expectedPlayerPosition = 34;

        Map<Integer, Integer> snakeLadder = new HashMap<>();
        snakeLadder.put(12, 34);
        snakeAndLadder.snakeLadder = snakeLadder;
        int actualPlayerPosition = snakeAndLadder.playerPosition(playerPosition, dice);
        assertEquals(expectedPlayerPosition, actualPlayerPosition);
    }

    @Test
    public void testParseInputWithSnakes(){
        String input = "14, 7 | 22, 16 | 22,18| 64,29| 47,78";
        Map<Integer, Integer> expectedSnakeLadder = new HashMap<>();
        expectedSnakeLadder.put(14, 7);
        expectedSnakeLadder.put(22, 16);
        expectedSnakeLadder.put(64, 29);

        snakeAndLadder.parseInput(input, snakeAndLadder.snakeLadder, true);
        assertEquals(expectedSnakeLadder, snakeAndLadder.snakeLadder);
    }

    @Test
    public void testParseInputWithLadders(){
        String input = "10,25|34,56|56,89|78,63";
        Map<Integer, Integer> expectedSnakeLadder = new HashMap<>();
        expectedSnakeLadder.put(10, 25);
        expectedSnakeLadder.put(34, 56);

        snakeAndLadder.parseInput(input, snakeAndLadder.snakeLadder, false);
        assertEquals(expectedSnakeLadder, snakeAndLadder.snakeLadder);
    }

    @Test
    public void testParseInputWithSnakesAndLadders(){
        String input1 = "15,5|  45,9| 9,3 | 18, 30| 97,62";
        String input2 = "9,18| 20,38  |50 ,67|70, 93";
        Map<Integer, Integer> expectedSnakeLadder = new HashMap<>();
        expectedSnakeLadder.put(15, 5);
        expectedSnakeLadder.put(45, 9);
        expectedSnakeLadder.put(97, 62);
        expectedSnakeLadder.put(20, 38);
        expectedSnakeLadder.put(50, 67);
        expectedSnakeLadder.put(70, 93);

        snakeAndLadder.parseInput(input1, snakeAndLadder.snakeLadder, true);
        snakeAndLadder.parseInput(input2, snakeAndLadder.snakeLadder, false);
        assertEquals(expectedSnakeLadder, snakeAndLadder.snakeLadder);
    }

}
