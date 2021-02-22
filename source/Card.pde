class Card{                                                        //Represents a Card with all relevant info contained herein
  
  String cardName;
  String cardIsFoil;
  String cardType;
  String cardRarity;
  float cardPopularity;
  float cardPrice; 
  int cardTileX, cardTileY;
 
  Card(String name, String isFoil, String type, String rarity, float popularity, float price, int tileX, int tileY){ setCard(name, isFoil, type, rarity, popularity, price, tileX, tileY); }

  void setCard(String name, String isFoil, String type, String rarity, float popularity, float price, int tileX, int tileY){
    cardName = name;
    cardIsFoil = isFoil;
    cardType = type;
    cardRarity = rarity;
    cardPopularity = popularity;
    cardPrice = price;
    cardTileX = tileX;
    cardTileY = tileY;
  }
  
  String getName(){ return cardName; }                            //Gets
  float getPopularity(){ return cardPopularity; }
  float getPrice(){ return cardPrice; }
  int getTileRefX(){ return cardTileX; }
  int getTileRefY(){ return cardTileY; }
  
  void consolOutCard(){                                           //Prints what is contained in the Card
    if(!(cardName == "")){ println("Name = " + cardName + "\nFoil = " + cardIsFoil + "\nType = " + cardType + "\nRarity = " + cardRarity + "\nPopularity = " + cardPopularity + "\nPrice = " + cardPrice + "\n"); }
  }
  
  void consolOutCardShort(){                                      //Short version of consoleOut
    if(!(cardName == "")){ println("Name = " + cardName); }
  }
}
