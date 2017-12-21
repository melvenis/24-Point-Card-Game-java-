package project3;

import java.awt.Toolkit;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Class   DeckOfCards
 * File:     DeckOfCards.java
 * Description:   Deck of standard 52 playing card deck, allows user to get cards with
 *                      numerical values and with images for each unique card
 * @author        Mel Leggett
 * Environment:   PC, Windows 10, jdk 1.8, NetBeans 8.2
 * Date:         03/04/2017
 * @version         1.0
 * History Log:  03/04/2017
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class DeckOfCards {
    static final int DECK_SIZE = 52;
    private Icon[] cardImages = new Icon[53];
    //int array of the value of the cards 1-13, 4 times, once for each suit
    private static final int[] CARD_VALUE = {1,2,3,4,5,6,7,8,9,10,11,12,13,1,2,3,4,5,6,7,8,9,10,11,12,13,
        1,2,3,4,5,6,7,8,9,10,11,12,13,1,2,3,4,5,6,7,8,9,10,11,12,13};
    Random rand; //random number generator
    static int card1;
    static int card2;
    static int card3;
    static int card4;
    static int cardValue1;
    static int cardValue2;
    static int cardValue3;
    static int cardValue4;
    static Icon cardImage1;
    static Icon cardImage2;
    static Icon cardImage3;
    static Icon cardImage4;
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * DeckOfCards()
     * Description DeckOfCards default constructor, builds new deck of cards
     *                     with no predetermined card value
     * date 03/04/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public DeckOfCards() {
        getImages();
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * DeckOfCards()
     * Description Overloaded constructor with known values for cards.
     * @param a card1 integer value
     * @param b card2 integer value
     * @param c card3 integer value
     * @param d card4 integer value
     * date 03/08/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public DeckOfCards(int a, int b, int c, int d) {
        cardValue1 = getValue(a);
        cardValue2  = getValue(b);
        cardValue3 = getValue(c);
        cardValue4 = getValue(d);
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * shuffle()
     * Description Generates 4 random numbers corresponding to one of the 52 cards, 
     * if a duplicate number is generated, the number is discarded and a number is generated again
     * Then assigns the values corresponding with the card
     * date 03/04/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    static void shuffle() {
        Random rand = new Random();
        card1 = rand.nextInt(DECK_SIZE);
        card2 = rand.nextInt(DECK_SIZE);
        card3 = rand.nextInt(DECK_SIZE);
        card4 = rand.nextInt(DECK_SIZE);
        while(card1 == card2) {
            card2 = rand.nextInt(DECK_SIZE);
        }
        while(card3 == card2 || card3 == card1) {
            card3 = rand.nextInt(DECK_SIZE);
        }
        while(card4 == card3 || card4 == card2 || card4 == card1) {
           card4 = rand.nextInt(DECK_SIZE);
        }
        cardValue1 = getValue(card1);
        cardValue2 = getValue(card2);
        cardValue3 = getValue(card3);
        cardValue4 = getValue(card4);
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * getImages()
     * Description matches up the image of the card with the number selected
     * date 03/04/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void getImages() {
        Icon image = null;
        for(int i = 0; i < cardImages.length; i++) {
            image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/" + (i + 1) + ".png"));
            cardImages[i] = image;
        }
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * getValue()
     * Description returns the value of a specific card index
     * @param index the location of the cards value in the array
     * @return the card's value
     * date 03/04/2017
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public static int getValue(int index) {
        return CARD_VALUE[index];
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * getImage()
     * Description returns the image of a specific card
     * @param index the location of the card image in the array
     * @return the card's image
     * date 03/04/2017
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public Icon getImage(int index) {
        return cardImages[index];
    }
}
