package solitaire;
import DeckOfCards.Carta;

import java.util.ArrayList;
import  java.util.List;


public class Hand {
    private int total;
    private int soft;
    private List<String> hand = new ArrayList<>();

    public void addCard(Carta carta){
        total+= carta.getValor();
        if(carta.getValor() == valor.reina){
            soft += 1;
        }
        if(soft > 0){
            if(total > 21){
                total -= 10;
                soft -= 1;
            }
        }
        hand.add(carta);
    }

    public Carta getCard(int index){
        return (Carta) hand.get(index);
    }

    public void discardedHand(){
        hand.clear();
        total = 0;
        soft = 0;
    }

    public int getNumberOfCards(){
        return hand.size();
    }

    public void sort(){

    }

    public boolean isEmpty(){
        return hand.isEmpty();
    }

    public int findCard(Carta carta){
        return hand.indexOf(carta);
    }

    public int getSoft (){
        return soft;
    }

    public int evaluateHand(){
        return total;
    }

    @Override
    public String toString(){
        return hand.toString();
    }

}
