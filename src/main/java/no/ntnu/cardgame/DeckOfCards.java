package no.ntnu.cardgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeckOfCards {
    private final char[] suits = {'S', 'H', 'D', 'C'};
    private final List<PlayingCard> deck;
    private final Random random;

    public DeckOfCards() {
        deck = new ArrayList<>(52);
        random = new Random();

        for (char suit : suits) {
            for (int face = 1; face <= 13; face++) {
                deck.add(new PlayingCard(suit, face));
            }
        }
    }


    public HandOfCards dealHand(int n) {
        if (n < 1 || n > deck.size()) {
            throw new IllegalArgumentException(
                    "n must be between 1 and " + deck.size() + ", got: " + n);
        }

        List<PlayingCard> shuffled = new ArrayList<>(deck);
        List<PlayingCard> hand = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            int index = random.nextInt(shuffled.size());
            hand.add(shuffled.remove(index));
        }
        return new HandOfCards(hand);
    }

    public int size() {
        return deck.size();
    }
}
