package edu.ntnu.idi.idatt2003.cardgame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DeckOfCardsTest {

    private DeckOfCards deck;

    @BeforeEach
    void setUp() {
        deck = new DeckOfCards();
    }

    @Test
    void deckHas52Cards() {
        assertEquals(52, deck.size());
    }

    @Test
    void dealHandReturnsCorrectSize() {
        HandOfCards hand = deck.dealHand(5);
        assertEquals(5, hand.size());
    }

    @Test
    void dealHandReturnsUniqueCards() {
        HandOfCards hand = deck.dealHand(10);
        Set<String> seen = new HashSet<>();
        for (PlayingCard card : hand.getCards()) {
            assertTrue(seen.add(card.toString()),
                    "Duplicate card in hand: " + card);
        }
    }

    @Test
    void dealHandWithNEqualsOne() {
        HandOfCards hand = deck.dealHand(1);
        assertEquals(1, hand.size());
    }

    @Test
    void dealHandWithNEquals52() {
        HandOfCards hand = deck.dealHand(52);
        assertEquals(52, hand.size());
    }

    @Test
    void dealHandWithZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> deck.dealHand(0));
    }

    @Test
    void dealHandWithNTooLargeThrows() {
        assertThrows(IllegalArgumentException.class, () -> deck.dealHand(53));
    }

    @Test
    void dealHandContainsAllSuits() {
        // Dealing the full deck must contain all four suits
        HandOfCards hand = deck.dealHand(52);
        Set<Character> suits = new HashSet<>();
        for (PlayingCard card : hand.getCards()) {
            suits.add(card.getSuit());
        }
        assertEquals(Set.of('S', 'H', 'D', 'C'), suits);
    }
}
