import java.util.*;

class SnakeAndLadder {

    final static int VICTORY = 100;

    Map<Integer, Integer> snakeLadder = new HashMap<Integer, Integer>();

    /**
     * This method works as a 6 faced dice
     * It uses Random to generate an integer between 1 and 6
     * @return an integer between 1 and 6
     */
    public int rollDice(){ 
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    /**
     * This function calculates the position of a player in the game
     * The player's position remains unchanged if the sum of position and dice is greater than 100
     * If player's position matches a key in the hashmap storing the snakes and ladders,
     * player's position is modified accordingly
     * @param position (stores position of the player)
     * @param dice (stores value of the dice rolled)
     * @return position of an individual player, must be an integer between 1 and 100
     */
    public int playerPosition (int position, int dice){ 
        position = position + dice;
        if(position > VICTORY){
            position = position - dice;
            return position;
        }
        if(null != snakeLadder.get(position)){
            if(position > snakeLadder.get(position)){
                System.out.println("There's a snake at position " + position);
            }
            else{
                System.out.println("There's a ladder at position " + position);
            }
            position = snakeLadder.get(position);
        }
        return position;
    }

    /**
     * @param position
     * @return true if player's position is equal to VICTORY
     */
    public boolean isWinner(int position){
        return VICTORY == position;
    }

    /**
     * @param num
     * @return true if the parameter is between 1 and 100
     */
    public boolean isValidNumber(int num){
        return num >= 1 && num <= 100;
    }

    /**
     * This function sets up the game according to the user's preference
     * The user determines the number of players and the positions of the snakes and ladders
     * The number of players is taken as an integer input
     * The snakes and ladders are taken as string inputs
     * These strings are parsed to the hashmap snakeLadder via parseInput()
     * After the snakes and ladders are parsed, they are printed as feedback to the user
     * @param scan used to take input from the user 
     * @return number of players in the game 
     */
    public int gameSetup(Scanner scan){
        System.out.println("Enter number of players: ");
        int numPlayers = scan.nextInt();

        scan.nextLine();
        System.out.println("Enter snake start and end positions separated by comma and | e.g. <start>, <end> | <start>, <end>: ");
        String snakes = scan.nextLine();
        parseInput(snakes, snakeLadder, true);

        System.out.println("Enter ladder start and end positions separated by comma and | e.g. <start>, <end> | <start>, <end>: ");
        String ladders = scan.nextLine();
        parseInput(ladders, snakeLadder, false);

        printSnakes(snakeLadder);
        printLadders(snakeLadder);

        return numPlayers;
    }

    /**
     * This function parses the strings containing snake/ladder positions to the hashmap snakeLadder
     * Disallows addition of position if the snakeLadder already contains key or contains value 
     * equal to key
     * Disallows addition of snake if key<value and vice versa for ladder
     * Adds snake only if key>value and both key and value are between 1-100
     * Adds ladder only if key<value and both key and value are between 1-100
     * @param input string containing either snake or ladder positions
     * @param snakeLadder hashmap in which positions are to be added 
     * @param isSnake used to determine whether conditions for adding snake or ladder 
     * positions are to be used
     */
    public void parseInput(String input, Map<Integer, Integer> snakeLadder, boolean isSnake){
        String[] numberPairs = input.split("\\|");
        for(String pair : numberPairs){
            String[] numbers = pair.trim().split(",");
            if (numbers.length == 2) {
                int key = Integer.parseInt(numbers[0].trim());
                int value = Integer.parseInt(numbers[1].trim());
                if(snakeLadder.containsKey(key) || snakeLadder.containsValue(key)){
                    System.out.println("Cannot add at " + key + "," + value + " because snake/ladder already exists at either positions");
                    continue;
                } 
                if(isSnake){
                    if (isValidNumber(key) && isValidNumber(value) && key>value) {
                        snakeLadder.put(key, value);
                        System.out.println("Snake " + key + "," + value + " added");
                    }
                    else{
                        System.out.println(key + "," + value + " is an invalid snake");
                    }
                }
                else{
                    if (isValidNumber(key) && isValidNumber(value) && key<value) {
                        snakeLadder.put(key, value);
                        System.out.println("Ladder " + key + "," + value + " added");
                    }
                    else{
                        System.out.println(key + "," + value + " is an invalid ladder");
                    }
                }
            }
        }
    }

    /**
     * This function prints key,value pairs of snake where key>value
     * It keeps count of the number of such pairs
     * @param snake we print contents of this map
     */
    public void printSnakes(Map<Integer, Integer> snake){
        System.out.println("The snakes are :");
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : snake.entrySet()) {
            if (entry.getKey() > entry.getValue()) {
                System.out.println(entry.getKey() + "," + entry.getValue());
                count++;
            }
        }
        System.out.println("Therefore, there are " + count + " snakes.");
    }

    /**
     * This function prints key,value pairs of ladder where key<value
     * It keeps count of the number of such pairs
     * @param ladder we print contents of this map
     */
    public void printLadders(Map<Integer, Integer> ladder){
        System.out.println("The ladders are :");
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : ladder.entrySet()) {
            if (entry.getKey() < entry.getValue()) {
                System.out.println(entry.getKey() + "," + entry.getValue());
                count++;
            }
        }
        System.out.println("Therefore, there are " + count + " ladders.");
    }

    /**
     * This function maintains the position of all players using an array
     * The game continues as long as the user clicks Enter
     * This function returns when isWinner() returns true
     * @param numPlayers number of players in the game
     * @param scan used to take input from the user
     */
    public void startGame(int numPlayers, Scanner scan){
        int currentPlayer = 0;
        int allPlayers[] = new int[numPlayers]; 
        String input;
        int dice;

        do{
            System.out.println("Player " + (currentPlayer + 1) + ", press Enter to roll dice");
            input = scan.nextLine();
            dice = rollDice();
            System.out.println("Rolling dice \ngot " +dice);
            int oldValue = allPlayers[currentPlayer];
            allPlayers[currentPlayer] = playerPosition(allPlayers[currentPlayer], dice);
            System.out.println("Player " + (currentPlayer+1) + " was at " + oldValue + ", is at " + allPlayers[currentPlayer] + " now");
            if(isWinner(allPlayers[currentPlayer])){
                System.out.println("Player " + (currentPlayer+1) + " wins");
                return;
            }
            currentPlayer = (currentPlayer + 1) % numPlayers;
        } while(input.isEmpty());
    }
}

 class SnakeAndLadderMain {

    /**
     * The program's execution starts at this function
     * It calls gameSetUp() and startGame()
     * It allows the user the play another round if the user enters 1, starting the game all
     * over again
     * @param args
     */
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int newGame;
        do{
          SnakeAndLadder object = new SnakeAndLadder();
          int numPlayers = object.gameSetup(scan);
          object.startGame(numPlayers, scan);
          System.out.println("Do you want to set up a fresh game? Enter 1 if yes.");
          newGame = scan.nextInt();
        } while (newGame == 1);
        scan.close();
    }
 }