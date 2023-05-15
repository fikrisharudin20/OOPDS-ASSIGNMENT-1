import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class goboom {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<String> deck = createDeck();       //init. create deck method
        Collections.shuffle(deck);              //shuffle deck
        

        
        // Deal cards to players
        List<List<String>> players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            players.add(new ArrayList<>());
        }

         //distributes cards from deck to each player
         for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                players.get(j).add(deck.remove(0));
            }
        }

        int command;
        do {
        System.out.println("Trick #1");
        for (int i = 0; i < 4; i++) {
        System.out.println("Player" + (i+1) + ": " + players.get(i));
        }
        System.out.println("Deck : " + deck);
        System.out.println("Score: Player1 = 0 | Player2 = 0 | Player3 = 0 | Player4 = 0");
        System.out.print("> ");
            command = input.nextInt();

        } while (command != 0);
        //area sini
    }

    //create deck
    public static List<String> createDeck() {
        List<String> deck = new ArrayList<>();
        String[] suits = {"c", "d", "h", "s"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(suit + rank);
            }
        }

        return deck;
    }

    //firstplayer method
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
    
}