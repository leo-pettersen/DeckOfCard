package no.ntnu.cardgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a full standard deck of 52 playing cards.
 *
 * <p>The deck contains one card for each combination of suit ('S', 'H', 'D', 'C')
 * and face value (1–13). Cards are created once in the constructor.
 */
public class DeckOfCards {

    private final char[] suits = {'S', 'H', 'D', 'C'};
    private final List<PlayingCard> deck;
    private final Random random;

    /**
     * Constructs a full deck of 52 playing cards.
     */
    public DeckOfCards() {
        deck = new ArrayList<>(52);
        random = new Random();

        for (char suit : suits) {
            for (int face = 1; face <= 13; face++) {
                deck.add(new PlayingCard(suit, face));
            }
        }
    }

    /**
     * Deals a hand of {@code n} randomly chosen cards from the deck.
     *
     * <p>Cards are picked at random without replacement (each card can appear
     * at most once in the returned hand).
     *
     * @param n number of cards to deal (1–52)
     * @return a {@link HandOfCards} containing {@code n} unique random cards
     * @throws IllegalArgumentException if n is outside the range [1, 52]
     */
    public HandOfCards dealHand(int n) {
        if (n < 1 || n > deck.size()) {
            throw new IllegalArgumentException(
                    "n must be between 1 and " + deck.size() + ", got: " + n);
        }

        // Shuffle a copy of indices so we never pick the same card twice
        List<PlayingCard> shuffled = new ArrayList<>(deck);
        List<PlayingCard> hand = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            int index = random.nextInt(shuffled.size());
            hand.add(shuffled.remove(index));
        }

        return new HandOfCards(hand);
    }

    /**
     * Returns the total number of cards in the deck (always 52).
     *
     * @return 52
     */
    public int size() {
        return deck.size();
    }
}
