import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class goboom {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<String> deck = createDeck(); // init. create deck method
        Collections.shuffle(deck); // shuffle deck

        // Assign determinant for first turn trick #1 center card
        String centerCard = deck.remove(0);
        int currentPlayer = getFirstPlayer(centerCard);

        // Deal cards to players
        List<List<String>> players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            players.add(new ArrayList<>());
        }

        // distributes cards from deck to each player
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                players.get(j).add(deck.remove(0));
            }
        }

        List<String> center = new ArrayList<>(); // initialize center pile
        center.add(centerCard); // put the center card in the center pile

        int trickNumber = 1;

        boolean running = true;
        String command;
        do {
            // Game status display
            System.out.println("Trick #" + trickNumber);
            for (int i = 0; i < 4; i++) {
                System.out.println("Player" + (i + 1) + ": " + players.get(i));
            }
            System.out.println("Center : " + center);
            System.out.println("Deck : " + deck);
            System.out.println("Score: Player1 = 0 | Player2 = 0 | Player3 = 0 | Player4 = 0");
            System.out.println("Turn : Player" + currentPlayer);

            // Menu
            System.out.println("\n--- MENU ---");
            System.out.println("s: Start a new game");
            System.out.println("x: Exit the game");
            System.out.println("d: Draw a card from the deck"); 
            System.out.println("card: To play a card");
            System.out.println("-------------");
            System.out.print("> ");
            command = input.nextLine();

            switch (command) {
                case "s":
                    // Reset everything
                    deck = createDeck();
                    Collections.shuffle(deck);
                    centerCard = deck.remove(0);
                    currentPlayer = getFirstPlayer(centerCard);
                    players.clear();
                    for (int i = 0; i < 4; i++) {
                        players.add(new ArrayList<>());
                    }
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 4; j++) {
                            players.get(j).add(deck.remove(0));
                        }
                    }
                    center.clear();
                    center.add(centerCard);
                    trickNumber = 1;
                    break;

                case "x":
                    running = false;
                    System.out.println("Closing Game...");
                    break;

                case "d":
                    if (deck.size() > 0) {
                        String newCard = deck.remove(0);
                        players.get(currentPlayer - 1).add(newCard);
                    }

                    else  { 
                    System.out.println("The deck is empty");
                    
                    }

                    break;

                    case "card":
    System.out.println("Choose a card from your hand to play");
    System.out.print("> ");
    String playedCard = input.nextLine();

    // Check if the player has the card in their hand
    if (players.get(currentPlayer - 1).contains(playedCard)) {
        // Check if it's the first trick
        if (trickNumber == 1) {
            // Check if the played card follows the leading card suit and rank
            if (center.isEmpty() || isCardSameSuitAndRank(playedCard, centerCard)) {
                // Remove the played card from the player's hand and add it to the center
                players.get(currentPlayer - 1).remove(playedCard);
                center.add(playedCard);
                System.out.println("Player" + currentPlayer + " played " + playedCard);

                // Check if the center is complete
                if (center.size() == 5) {
                    center.remove(centerCard);
                    String winningCard = getWinningCard1(center, centerCard);
                    int winningPlayer = (currentPlayer + center.indexOf(winningCard)) % 4 + 1;
                    System.out.println("*** Player" + winningPlayer + " wins Trick #" + trickNumber + " ***");
                    System.out.println("Winning Card: " + winningCard);
                    centerCard = winningCard;
                    center.clear();
                    currentPlayer = winningPlayer;

                    // Increment trick number
                    trickNumber++;
                } else {
                    currentPlayer = (currentPlayer % 4) + 1;
                }
            } else {
                System.out.println("The played card must follow the leading card suit and rank.");
            }
        } else if (trickNumber >= 2) {
           
            //initializing leading card
            if (center.size() == 0){
            players.get(currentPlayer - 1 ).remove(playedCard);
            center.add(playedCard);
            System.out.println("Player" + currentPlayer + " played " + playedCard);
            currentPlayer = (currentPlayer % 4) + 1;
            break;
            }
            
            String leadcard = center.get(0);
            
            if (isCardSameSuitAndRank(playedCard, leadcard) && center.size() >= 1) {
                // Remove the played card from the player's hand and add it to the center
                players.get(currentPlayer - 1).remove(playedCard);
                center.add(playedCard);
                System.out.println("Player" + currentPlayer + " played " + playedCard);

                // Check if the center is complete
                if (center.size() == 4) {
                    String winningCard = getWinningCard2(center, leadcard);
                    int winningPlayer = (currentPlayer + center.indexOf(winningCard)) % 4 + 1;
                    System.out.println("Player" + winningPlayer + " wins Trick #" + trickNumber);
                    System.out.println("Winning Card: " + winningCard);
                    leadcard = winningCard;
                    center.clear();
                    currentPlayer = winningPlayer;

                    trickNumber++;
                } else {
                    currentPlayer = (currentPlayer % 4) + 1;
                }
            } else {
                System.out.println("The played card must follow the first player's card suit and rank.");
            }
        }
    } else {
        System.out.println("You do not have this card.");
    }
    break;
            
                default:
                System.out.println("Input invalid! Please choose the following options given (PRESS ENTER TO CONTINUE)");
                try {
                    input.nextLine(); // using your Scanner object
                } catch (Exception e) {
                    System.out.println("An error occurred while waiting for input: " + e.getMessage());
                }
                break;
                
            }
        } while (running && !deck.isEmpty());
    }

    // create deck
    public static List<String> createDeck() {
        List<String> deck = new ArrayList<>();
        String[] suits = { "c", "d", "h", "s" };
        String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K", "A" };

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(suit + rank);
            }
        }

        return deck;
    }

    // firstplayer method
    public static int getFirstPlayer(String card) {
        int firstPlayer = 0;
        char rank = card.charAt(1);
        switch (rank) {
            case 'A':
            case '5':
            case '9':
            case 'K':
                firstPlayer = 1;
                break;
            case '2':
            case '6':
            case 'X':
                firstPlayer = 2;
                break;
            case '3':
            case '7':
            case 'J':
                firstPlayer = 3;
                break;
            case '4':
            case '8':
            case 'Q':
                firstPlayer = 4;
                break;
        }
        return firstPlayer;
    }

    // get the winning card from the center pile for first trick
    public static String getWinningCard1(List<String> center, String centerCard) {
        String winningCard = null;
        char centerSuit = centerCard.charAt(0);

        // Filter for cards that have the same suit as the centerCard
        for (String card : center) {
            if (card.charAt(0) == centerSuit) {
                // If this is the first card with the correct suit or if it has a higher rank
                // than the current winning card
                if (winningCard == null || compareRanks(card.charAt(1), winningCard.charAt(1)) > 0) {
                    winningCard = card;
                }
            }
        }
        return winningCard;
    }

    public static String getWinningCard2(List<String> center, String leadcard) {
        String winningCard = null;
        char leadSuit = leadcard.charAt(0);
        for (String card : center) {
            if (card.charAt(0) == leadSuit) {
                if (winningCard == null || compareRanks(card.charAt(1), winningCard.charAt(1)) > 0) {
                    winningCard = card;
                }
            }
        }
        return winningCard;
    }

    // compare the ranks of two cards
    public static int compareRanks(char rank1, char rank2) {
        String ranks = "23456789XJQKA";
        return ranks.indexOf(rank1) - ranks.indexOf(rank2);
    }

    // Method to check if the played card follows the first player's card suit and rank
    private static boolean isCardSameSuitAndRank(String playedCard, String firstPlayerCard) {
        String playedSuit = playedCard.substring(0, 1);
        String playedRank = playedCard.substring(1);
        String firstPlayerSuit = firstPlayerCard.substring(0, 1);
        String firstPlayerRank = firstPlayerCard.substring(1);
    
        // Compare the suits and ranks
        return playedSuit.equals(firstPlayerSuit) ||  playedRank.equals(firstPlayerRank);
    }   

}