import java.util.*;

class CardDeck {
    LinkedList<Integer> deck;

    // constructor, creates a deck with n cards, placed in increasing order
    CardDeck(int n) {
	deck = new LinkedList<Integer> ();
	for (int i=1;i<=n;i++) deck.addLast(new Integer(i));
    }

    // executes the card trick
    public void runTrick() {

	while (!deck.isEmpty()) {
	    // remove the first card and remove it
	    Integer topCard = deck.removeFirst();
	    System.out.println("Showing card "+topCard);

	    // if there's nothing left, we are done
	    if (deck.isEmpty()) break;
	    
	    // otherwise, remove the top card and place it at the back.
	    Integer secondCard = deck.removeFirst();
	    deck.addLast(secondCard);

	    System.out.println("Remaining deck: "+deck);

	}
    }



    public void setupDeck(int n) {
	
    	LinkedList<Integer> sorted =new LinkedList<Integer>();  //initialize size*** ? 
  
    	for(int i=n;i>=1;i--){         //sorting algorithm for the trick is within the for loop
    		
    		LinkedList<Integer> temp =new LinkedList<Integer>();
    		temp = (LinkedList) sorted.clone();
    		
    		sorted.clear();
    		
    		sorted.addFirst(i);
    		
    		if(temp.size()!=0){
    		sorted.addLast(temp.getLast());
    		}
    		
    		for(int j=0;j<=temp.size()-2;j++){
    			 if(i!=(n-1)){
    			sorted.addLast(temp.get(j));
    			 }
    		}
    			
    	} 	
    	   	
    	
    	for(int i=0 ; i<n; i++){
    	    	    		this.deck.addLast(sorted.get(i));
    	}
    	}



    public static void main(String args[]) {
	// this is just creating a deck with cards in increasing order, and running the trick. 
	CardDeck d = new CardDeck(10);
	d.runTrick();

	// this is calling the method you are supposed to write, and executing the trick.
	CardDeck e = new CardDeck(0);
	e.setupDeck(10);
	e.runTrick();
    }
}