class CardList{                                                                                                    //List of Card objects 
  
  int arraySize;
  int index;
  Card[] cards;
  
  CardList(){ setDefaultList(); }

  void setDefaultList(){
    arraySize = 100;
    index = 0;
    cards = new Card[100];                                                                                        //Creates and fills an array of 100 Cards
    for(int i = 0; i < cards.length; i++){  cards[i] = new Card(" ", " ", " ", " ", 0.0, 0.0, 0,0); }
  }
  
  void addCard(Card c){ cards[index].setCard(c.cardName, c.cardIsFoil, c.cardType, c.cardRarity, c.cardPopularity, c.cardPrice, c.cardTileX, c.cardTileY); index++; }  //Used to add a card to the Array
  
  void consoleOut(){                                                                                              //Prints to the console all card names within the array
    for(int i = 0; i < cards.length; i++){ 
      if(!(cards[i].getName() == "")){ 
        println("Card Name = " + cards[i].getName());
      }
    }
    println();
  }
  
  Card getCard(int i){ return cards[i]; }                                                                          //returns a specified card
  
}
