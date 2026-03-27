package no.ntnu.cardgame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayingCardTest {

    @Test
    void constructorSetsFields() {
        PlayingCard card = new PlayingCard('H', 12);
        assertEquals('H', card.getSuit());
        assertEquals(12, card.getFace());
    }

    @Test
    void toStringFormat() {
        assertEquals("S1",  new PlayingCard('S', 1).toString());
        assertEquals("H13", new PlayingCard('H', 13).toString());
        assertEquals("D7",  new PlayingCard('D', 7).toString());
    }

    @Test
    void invalidSuitThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new PlayingCard('X', 5));
    }

    @Test
    void invalidFaceLowThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new PlayingCard('S', 0));
    }

    @Test
    void invalidFaceHighThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new PlayingCard('C', 14));
    }

    @Test
    void allValidSuitsAccepted() {
        assertDoesNotThrow(() -> new PlayingCard('S', 5));
        assertDoesNotThrow(() -> new PlayingCard('H', 5));
        assertDoesNotThrow(() -> new PlayingCard('D', 5));
        assertDoesNotThrow(() -> new PlayingCard('C', 5));
    }
}
