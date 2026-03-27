package no.ntnu.cardgame;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HandOfCards {
    private final List<PlayingCard> cards;

    public HandOfCards(List<PlayingCard> cards) {
        if (cards == null) {
            throw new IllegalArgumentException("Card list must not be null");
        }
        this.cards = Collections.unmodifiableList(cards);
    }

    public List<PlayingCard> getCards() {
        return cards;
    }

    public int size() {
        return cards.size();
    }

    public int sumOfFaces() {
        return cards.stream()
                .mapToInt(PlayingCard::getFace)
                .sum();
    }

    public String heartsAsString() {
        String result = cards.stream()
                .filter(c -> c.getSuit() == 'H')
                .map(PlayingCard::toString)
                .collect(Collectors.joining(" "));
        return result.isEmpty() ? "No Hearts" : result;
    }


    public boolean hasQueenOfSpades() {
        return cards.stream()
                .anyMatch(c -> c.getSuit() == 'S' && c.getFace() == 12);
    }


    public boolean isFlush() {
        if (cards.size() != 5) {
            return false;
        }
        char firstSuit = cards.getFirst().getSuit();
        return cards.stream()
                .allMatch(c -> c.getSuit() == firstSuit);
    }

    @Override
    public String toString() {
        return cards.stream()
                .map(PlayingCard::toString)
                .collect(Collectors.joining(" "));
    }
}
