package no.ntnu.cardgame;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HandOfCardsTest {
    @Test
    void sumOfFacesIsCorrect() {
        HandOfCards hand = handOf('H', 1, 'S', 2, 'D', 3, 'C', 4, 'H', 5);
        assertEquals(15, hand.sumOfFaces());
    }

    @Test
    void sumOfFacesSingleCard() {
        HandOfCards hand = new HandOfCards(List.of(new PlayingCard('S', 13)));
        assertEquals(13, hand.sumOfFaces());
    }

    @Test
    void heartsStringFormattedCorrectly() {
        HandOfCards hand = new HandOfCards(List.of(
                new PlayingCard('H', 12),
                new PlayingCard('S', 5),
                new PlayingCard('H', 9),
                new PlayingCard('H', 1)
        ));
        assertEquals("H12 H9 H1", hand.heartsAsString());
    }

    @Test
    void noHeartsReturnsNoHeartsString() {
        HandOfCards hand = new HandOfCards(List.of(
                new PlayingCard('S', 5),
                new PlayingCard('D', 3)
        ));
        assertEquals("No Hearts", hand.heartsAsString());
    }

    @Test
    void allHeartsReturned() {
        HandOfCards hand = new HandOfCards(List.of(
                new PlayingCard('H', 1),
                new PlayingCard('H', 2),
                new PlayingCard('H', 3)
        ));
        assertEquals("H1 H2 H3", hand.heartsAsString());
    }

    @Test
    void queenOfSpadesDetected() {
        HandOfCards hand = handOf('S', 12, 'H', 7, 'D', 3, 'C', 5, 'S', 1);
        assertTrue(hand.hasQueenOfSpades());
    }

    @Test
    void queenOfHeartsIsNotQueenOfSpades() {
        HandOfCards hand = new HandOfCards(List.of(new PlayingCard('H', 12)));
        assertFalse(hand.hasQueenOfSpades());
    }

    @Test
    void queenOfSpadesAbsent() {
        HandOfCards hand = handOf('H', 1, 'D', 2, 'C', 3, 'S', 4, 'H', 5);
        assertFalse(hand.hasQueenOfSpades());
    }

    @Test
    void fiveHeartsIsFlush() {
        HandOfCards hand = new HandOfCards(List.of(
                new PlayingCard('H', 1),
                new PlayingCard('H', 5),
                new PlayingCard('H', 7),
                new PlayingCard('H', 9),
                new PlayingCard('H', 11)
        ));
        assertTrue(hand.isFlush());
    }

    @Test
    void fiveSpadesIsFlush() {
        HandOfCards hand = new HandOfCards(List.of(
                new PlayingCard('S', 2),
                new PlayingCard('S', 4),
                new PlayingCard('S', 6),
                new PlayingCard('S', 8),
                new PlayingCard('S', 10)
        ));
        assertTrue(hand.isFlush());
    }

    @Test
    void mixedSuitsNotFlush() {
        HandOfCards hand = handOf('H', 1, 'S', 2, 'D', 3, 'C', 4, 'H', 5);
        assertFalse(hand.isFlush());
    }

    @Test
    void fourCardsNotFlushEvenIfSameSuit() {
        HandOfCards hand = new HandOfCards(List.of(
                new PlayingCard('D', 1),
                new PlayingCard('D', 2),
                new PlayingCard('D', 3),
                new PlayingCard('D', 4)
        ));
        assertFalse(hand.isFlush());
    }

    @Test
    void sixCardsNotFlushEvenIfSameSuit() {
        HandOfCards hand = new HandOfCards(List.of(
                new PlayingCard('C', 1),
                new PlayingCard('C', 2),
                new PlayingCard('C', 3),
                new PlayingCard('C', 4),
                new PlayingCard('C', 5),
                new PlayingCard('C', 6)
        ));
        assertFalse(hand.isFlush());
    }

    @Test
    void toStringSpaceSeparated() {
        HandOfCards hand = new HandOfCards(List.of(
                new PlayingCard('H', 4),
                new PlayingCard('S', 1)
        ));
        assertEquals("H4 S1", hand.toString());
    }

    private HandOfCards handOf(Object... args) {
        List<PlayingCard> cards = new java.util.ArrayList<>();
        for (int i = 0; i < args.length; i += 2) {
            cards.add(new PlayingCard((char) args[i], (int) args[i + 1]));
        }
        return new HandOfCards(cards);
    }
}
