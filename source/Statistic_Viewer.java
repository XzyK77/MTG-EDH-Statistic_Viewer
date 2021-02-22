import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Statistic_Viewer extends PApplet {

Table table;                                        //Table to hold imported data
PImage sprite;                                      //spriteStorage ralating to Table Information

String cardName, cardType, cardRarity, cardIsFoil;  //Card Data Declaration
int cardPopularity;
float cardPrice;
PImage spriteSheet;
PImage background;

Graph[] graph;
Graph allCards;

Card shadowCard;                                    //Used as a placeholder for sorting arrays

int CARDWIDTH = 372;                                //CONSTANTS for card sizes
int CARDHEIGHT = 520;

int ALL = 0;
int ARTIFACTS = 1;
int LANDS = 2;
int CREATURES = 3;
int ENCHANTMENTS = 4;
int INSTANTS = 5;
int SORCERYS = 6;
int PLANESWALKERS = 7;
int BASIC_LANDS = 8;
int LEGENDARY = 9;

int COMMONS = 10;
int UNCOMMONS = 11;
int RARES = 12;
int MYTHICS = 13;


int cardDisplayX, cardDisplayY;

int tileX, tileY;                                   //Used for locating the correct card image within the spriteSheet.

int page;                                           //Used to keep track of what Graphs are visible


//=======================================================================================================================

public void setup(){
  
  graph = new Graph[14];                                                                                                //allocates Memory for Graphs
  
  graph[ALL] = new Graph("Nicol-Bolas Deck Value", 25, 25, displayWidth - 50, displayHeight - 75, true);                //First graph is main graph will all cars, starts Visible
  
  int smW = (displayWidth - 100) / 3;                                                                                   //Calculates divisions based on display sizes
  int smH = (displayHeight - 125) / 3;
  
  graph[ARTIFACTS] = new Graph("Artifacts",     smW * 0 + 25, smH * 0 + 25, smW, smH , false);                          //new Graph("graphName", x + offset, y + offset, division amounts, start invisible);
  graph[LANDS] = new Graph("Lands",         smW * 1 + 50, smH * 0 + 25, smW, smH , false);
  graph[CREATURES] = new Graph("Creatures",     smW * 2 + 75, smH * 0 + 25, smW, smH , false);
  graph[ENCHANTMENTS] = new Graph("Enchantments",  smW * 0 + 25, smH * 1 + 50, smW, smH , false);
  graph[INSTANTS] = new Graph("Instants",      smW * 1 + 50, smH * 1 + 50, smW, smH , false);
  graph[SORCERYS] = new Graph("Sorcerys",      smW * 2 + 75, smH * 1 + 50, smW, smH , false);
  graph[PLANESWALKERS] = new Graph("Planeswalkers", smW * 0 + 25, smH * 2 + 75, smW, smH , false);
  graph[BASIC_LANDS] = new Graph("Basic Lands",   smW * 1 + 50, smH * 2 + 75, smW, smH , false);
  graph[LEGENDARY] = new Graph("Legendary",     smW * 2 + 75, smH * 2 + 75, smW, smH , false); 
  
  int medW = (displayWidth - 75) / 2;                                                                                    //bigger than last section
  int medH = (displayHeight - 100) / 2;
  
  graph[COMMONS] = new Graph("Commons",   medW * 0 + 25, medH * 0 + 25, medW, medH , false);
  graph[UNCOMMONS] = new Graph("Uncommons", medW * 1 + 50, medH * 0 + 25, medW, medH , false);
  graph[RARES] = new Graph("Rares",     medW * 0 + 25, medH * 1 + 50, medW, medH , false);
  graph[MYTHICS] = new Graph("Mythics",   medW * 1 + 50, medH * 1 + 50, medW, medH , false);
  
  page = 0;                                                                                                              //Page number starts at 0
  
  table = loadTable("Nicol_Bolas_Commander_CardList.csv", "header");                                                     //Loads csv and deck spriteSheet
  spriteSheet = loadImage("CardSpriteSheet.png");
  
  tileX = 0;                                                                                                             //Card faces are found based on tiles relating to sizes * tile for each axis
  tileY = 0;
  sprite = spriteSheet.get(CARDWIDTH * tileX, CARDHEIGHT * tileY, CARDWIDTH, CARDHEIGHT);                                //used when displaying the card face to the screen
  
  shadowCard = new Card(" "," "," "," ", 0, 0.0f,0,0);                                                                    //shadowcard to be used when distributing cards to different arrays
  
                                                                                       //screen size of minitor
  
  for(TableRow row : table.rows()){                                                                                      //For (each table row)
    
    shadowCard.setCard(row.getString(0), row.getString(1), row.getString(2), row.getString(3), row.getInt(4), row.getFloat(5), row.getInt(6), row.getInt(7));  //ShadowCard becomes row
    
    graph[0].addToCardList(shadowCard);                                                                                                                        //Add every card to the ALL graph
    
    switch(shadowCard.cardType){                                                                                                                               //Places cards in there repective graphs -> cardType
    
      case "Artifact":                graph[ARTIFACTS].addToCardList(shadowCard); break;
      case "Land":                    graph[LANDS].addToCardList(shadowCard); break;
      case "Creature":                graph[CREATURES].addToCardList(shadowCard); break;
      case "Enchantment":             graph[ENCHANTMENTS].addToCardList(shadowCard); break;
      case "Instant":                 graph[INSTANTS].addToCardList(shadowCard); break;
      case "Sorcery":                 graph[SORCERYS].addToCardList(shadowCard); break;
      case "Planeswalker":            graph[PLANESWALKERS].addToCardList(shadowCard); break;
      case "Artifact Creature":       graph[ARTIFACTS].addToCardList(shadowCard); graph[CREATURES].addToCardList(shadowCard); break;
      case "Basic Land":              graph[BASIC_LANDS].addToCardList(shadowCard); graph[LANDS].addToCardList(shadowCard); break;
      case "Artifact Land":           graph[ARTIFACTS].addToCardList(shadowCard); graph[LANDS].addToCardList(shadowCard); break;
      case "Legendary Creature":      graph[LEGENDARY].addToCardList(shadowCard); graph[CREATURES].addToCardList(shadowCard); break;
      case "Legendary Planeswalker":  graph[LEGENDARY].addToCardList(shadowCard); graph[PLANESWALKERS].addToCardList(shadowCard); break;
      default: println("DEFAULT :: cardType :: shadowCard ");
        shadowCard.consolOutCard(); break;
    }
    
    switch(shadowCard.cardRarity){                                                                                                                              //Places cards in respective graphs -> cardRarity
    
      case "Common":     graph[COMMONS].addToCardList(shadowCard); break;
      case "Uncommon":   graph[UNCOMMONS].addToCardList(shadowCard); break;
      case "Rare":       graph[RARES].addToCardList(shadowCard); break;
      case "Mythic":     graph[MYTHICS].addToCardList(shadowCard); break;
      default: println("DEFAULT :: cardRarity :: shadowCard ");
        shadowCard.consolOutCard(); break;
    }
  }
 
  for(int i = 0; i < graph.length; i++){ graph[i].calculateGraphMaxs(); }                                            //each graph needs to calculate their max price and popularity in order to scale the graph properly.
  
}

//===========================================================================================================================================

public void draw(){

  background(255);                                                                                                //Start With White Background
  
  strokeWeight(2);                                                                                                //Instructions for user...not fancy, I know :(
  fill(0,0,0);
  textSize(40);
  if(page == 0){
    text("TAB -> Next Page", displayWidth - 500 , 50);
    text("Mouse Wheel -> Zoom", displayWidth - 500 , 100);
  }
  
  for(int i = 0; i < graph.length; i++){                                                                          //For (all the graphs)
    if(graph[i].getVisibility() == true){                                                                         //If (its visible)
    
      graph[i].displayGraph();                                                                                    //Draw the Graph -> see displayGraph() for more!
      
      for(int j = 0; j < graph[i].refList.length; j++){                                                           //for (each graph's ellipse Locations)
      
        float distanceX = graph[i].refList[j].getRefX() - mouseX;                                                 //X and Y distance from Mouse
        float distanceY = graph[i].refList[j].getRefY() - mouseY;
        float distance = sqrt((distanceX * distanceX + distanceY * distanceY));                                   //a squared + b squared = c squared!
        
        if (distance <= 15){                                                                                      //If that distance is 15 or less ->
        
          tileX = graph[i].refList[j].getRefTileX();                                                              //Sets the tile X and Y to the ellipse reference point
          tileY = graph[i].refList[j].getRefTileY();
          
          sprite = spriteSheet.get(CARDWIDTH * tileX, CARDHEIGHT * tileY, CARDWIDTH, CARDHEIGHT);                 //Set the sprite to the location on the spriteSheet
          
                                                                                                                  //Draw the card face at the mouse location and relative to the quadrant that the mouse is located
          if((mouseX < graph[i].x + graph[i].w / 2) && (mouseY > graph[i].y + graph[i].h /2)){                    //Lower LH Quadrant  
            image(sprite, mouseX, mouseY - CARDHEIGHT /2, CARDWIDTH / 2, CARDHEIGHT / 2);
          }   
          else if((mouseX > graph[i].x + graph[i].w / 2) && (mouseY > graph[i].y + graph[i].h / 2))               //Lower RH Quadrant
          {
            image(sprite, mouseX - CARDWIDTH / 2, mouseY - CARDHEIGHT / 2, CARDWIDTH / 2, CARDHEIGHT / 2);
          }
          else if((mouseX > graph[i].x + graph[i].w / 2) && (mouseY < graph[i].y + graph[i].h / 2))               //Upper RH Quadrant
          {
            image(sprite, mouseX - CARDWIDTH / 2, mouseY, CARDWIDTH / 2, CARDHEIGHT / 2);
          }
          else{                                                                                                   //Upper LH Quadrant (easiest)
            image(sprite, mouseX, mouseY, CARDWIDTH / 2, CARDHEIGHT / 2); //Draws image... where the mouse is.
          }
        }
      }
    }
  }
}

//===========================================================================================================================================

public void mouseWheel(MouseEvent event){
  float e = event.getCount();                                          //float will be either 1 or -1.  Positive -> Mouse Wheel Down; Negative -> Mouse Wheel Up.  Reversed for Mac.
  for (int i = 0; i < graph.length; i++){                              //for(each graph)
    if(mouseIsInsideGraph(graph[i])){ graph[i].applyZoom(e); }         //If(mouse is inside the graph){ apply the mouseWheel event to that graph's zoom value}
  }
}

//===========================================================================================================================================

public boolean mouseIsInsideGraph(Graph g){                                   //Used to lessen some text on the screen, but checks if the mouse is inside the bounds of a graph; Returns true or false
  if(g.getVisibility() == true){
    if((mouseX > g.getX()) && (mouseX < (g.getWidth() + g.getX()) && (mouseY > g.getY()) && mouseY < (g.getHeight() + g.getY()))){ return true; }
  }
  return false;
}

public void keyPressed(){                                                    
  if(key == '\t'){ page++; if(page == 3){ page = 0; }}                //Listenes for the TAB key, and if page reaches its max, reset it

  switch(page){                                                       //Sets different graph's visibility based on the value of page
  case 0:
    for(int i = 10; i < 14; i++){ graph[i].setVisibility(false); }
    graph[0].setVisibility(true); break;
    
  case 1: 
    graph[0].setVisibility(false); 
    for(int i = 1; i < 10; i++){ graph[i].setVisibility(true); }
    break;
    
  case 2:
    for(int i = 1; i < 10; i++){ graph[i].setVisibility(false); }
    for(int i = 10; i < 14; i++){ graph[i].setVisibility(true); }
    break;
  default: println("ERROR::DEFAULT::TAB KEY PAGE"); break;            //Prints to console if anthing else happens
  }
}
class Card{                                                        //Represents a Card with all relevant info contained herein
  
  String cardName;
  String cardIsFoil;
  String cardType;
  String cardRarity;
  float cardPopularity;
  float cardPrice; 
  int cardTileX, cardTileY;
 
  Card(String name, String isFoil, String type, String rarity, float popularity, float price, int tileX, int tileY){ setCard(name, isFoil, type, rarity, popularity, price, tileX, tileY); }

  public void setCard(String name, String isFoil, String type, String rarity, float popularity, float price, int tileX, int tileY){
    cardName = name;
    cardIsFoil = isFoil;
    cardType = type;
    cardRarity = rarity;
    cardPopularity = popularity;
    cardPrice = price;
    cardTileX = tileX;
    cardTileY = tileY;
  }
  
  public String getName(){ return cardName; }                            //Gets
  public float getPopularity(){ return cardPopularity; }
  public float getPrice(){ return cardPrice; }
  public int getTileRefX(){ return cardTileX; }
  public int getTileRefY(){ return cardTileY; }
  
  public void consolOutCard(){                                           //Prints what is contained in the Card
    if(!(cardName == "")){ println("Name = " + cardName + "\nFoil = " + cardIsFoil + "\nType = " + cardType + "\nRarity = " + cardRarity + "\nPopularity = " + cardPopularity + "\nPrice = " + cardPrice + "\n"); }
  }
  
  public void consolOutCardShort(){                                      //Short version of consoleOut
    if(!(cardName == "")){ println("Name = " + cardName); }
  }
}
class CardList{                                                                                                    //List of Card objects 
  
  int arraySize;
  int index;
  Card[] cards;
  
  CardList(){ setDefaultList(); }

  public void setDefaultList(){
    arraySize = 100;
    index = 0;
    cards = new Card[100];                                                                                        //Creates and fills an array of 100 Cards
    for(int i = 0; i < cards.length; i++){  cards[i] = new Card(" ", " ", " ", " ", 0.0f, 0.0f, 0,0); }
  }
  
  public void addCard(Card c){ cards[index].setCard(c.cardName, c.cardIsFoil, c.cardType, c.cardRarity, c.cardPopularity, c.cardPrice, c.cardTileX, c.cardTileY); index++; }  //Used to add a card to the Array
  
  public void consoleOut(){                                                                                              //Prints to the console all card names within the array
    for(int i = 0; i < cards.length; i++){ 
      if(!(cards[i].getName() == "")){ 
        println("Card Name = " + cards[i].getName());
      }
    }
    println();
  }
  
  public Card getCard(int i){ return cards[i]; }                                                                          //returns a specified card
  
}
class Graph{                                                                                    //Stores and draws everything related to the Graph
  
  String title;
  int x, y, w, h;
  float maxPopularity, maxPrice, zoom;
  boolean isVisible;
  CardList data;
  Reference[] refList;
  int refIndex;
  
  Graph(){ this("",0,0,0,0,false); }

  Graph(String title, int x, int y, int w, int h, boolean v){ 
    this.title = title;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    maxPopularity = 0.0f;
    maxPrice = 0.0f;
    isVisible = v;
    zoom = 1;
    data = new CardList();
    refList = new Reference[100];
    for(int i = 0; i < refList.length; i++){ refList[i] = new Reference(); }
    refIndex = 0;
  }

  public void addToCardList(Card card){ data.addCard(card); }                                          //Adds cards to the CardList
   
  public void consoleOut(){                                                                            //Prints basic data about the graph and all the card names
    println("Graph title = " + title + "\n Location = ( " + x + ", " + y + " )\n width = " + w + " \nheight = " + h + "\n" );
    for(int i = 0; i < data.arraySize; i++){ if(!(data.cards[i].getName() == " ")){data.cards[i].consolOutCardShort();} }
  }
  
  public void displayGraph(){                                                                          //Draws the Graph separatly from the draw() method because referenceing everything inside would be horendous
    for(int i = 0; i < refList.length; i++){ refList[i].clearRef(); refIndex = 0;}
    
    textSize(35);                                                                               //Title in Bottom left hand corner near (0, 0)
    fill(0,0,0);
    text(title, x + (w / 16) + 50, y + (h / 16 * 14));
    
    textSize(20);                                                                               //Name of Axis put at ends of graphs
    fill(255,0,0);
    text("Price", x + 60, y + 15);
    text("Popularity", x + w - 100, y + h - 30);
    
    strokeWeight(2);                                                                            //Thick lines for visibility
    line(x, y, x, y + h);                                                                       //Virtical Line      Lines
    line(x, y + h, x + w, y + h);                                                               //Horizontal Line
    
    textSize(12);
    int[] size = {10,3,5,3,10,3,5,3,10};                                                        //Line lengths for each line along an axis
    for(int i = 0; i < 9; i++){                                                                 //Draws Horizontal lines along virtical scale
      line(x - size[i], y + ((h / 8) * i), x + size[i], y + ((h / 8) * i)); 
      text("$" + nf(maxPrice / zoom / 9 * i,0,2), x + 5, y + ((h / 8) * (8 - i)) + 12);
    }            
    for(int i = 0; i < 9; i++){ 
      line(x + ((w / 8) * i), y + h - size[i], x + ((w / 8) * i), y + h + size[i]);             //Draws Virtical Lines along Horizontal Scale
      text(str(PApplet.parseInt(maxPopularity / zoom / 9 * i)), x + (((w - 50)/8) * i) - 25, y + h - 12);
    }
    
    fill(0,0,255);                                                                              //Turns the Ellipes BLUE!!!
    
    for(int i = 0; i < data.arraySize; i++){                                                    //for (each card in the graph)
      float ellipseX = (data.cards[i].getPopularity() * w / maxPopularity * zoom) + x;          //calculate x location of ellipse based on graphlocation, maxPopularity and zoom level
      float ellipseY = (y + h) - (PApplet.parseInt(data.cards[i].getPrice() * h / maxPrice) * zoom);         //Calculate y location of ellipse based on "  " and invert it so 0,0 is in the bottom LH corner
      
      if((ellipseX >= x) && (ellipseX <= (x + w)) && (ellipseY >= y) && (ellipseY <= (y+h))){   //if (ellipse is inside the graph)
        ellipse(ellipseX, ellipseY, 15, 15);                                                    //Draw Ellipse
        addRef(data.cards[i].getName(), ellipseX, ellipseY, data.cards[i].getTileRefX(), data.cards[i].getTileRefY());  //Store cardName, ellipse x and y, and corresponding tile X and Y for use with mouse rollover  
      }
    }
  }
  
  public void calculateGraphMaxs(){                                                                    //Calculates the maxPopularity and Max Price of a given graph
    for(int i = 0; i < data.arraySize; i++){                                                    //for( each card )
      float pop = data.getCard(i).cardPopularity;                                               //store its Popularity
      float price = data.getCard(i).cardPrice;                                                  //store its price
      
      if(pop > maxPopularity){ maxPopularity = pop; }                                           //if( its bigger than the max){make it the max}
      if(price > maxPrice){ maxPrice = price; }                                                 // ""
    }
  }
  
  public void addRef(String name, float x, float y, int tX, int tY){ refList[refIndex].createRef(name, x, y, tX, tY); refIndex++; }    //adds and sets
  public void setMaxPopularity(int pop){ maxPopularity = pop; }
  public void setMaxPrice(int money){ maxPrice = money; }
  public void setVisibility(boolean v){ isVisible = v; }
  public void applyZoom(float z){ zoom += (z * .2f); if(zoom < 1){ zoom = 1; }}
  
  public boolean getVisibility(){ return isVisible; }                                                   //gets
  public int getX(){ return x; }  
  public int getY(){ return y; }
  public int getWidth(){ return w; }
  public int getHeight(){ return h; }
}
class Reference{                                                        //Stores temporary reference points generatred when an ellipse is created on a graph

  String refName;
  float refX;
  float refY;
  int refTileX;
  int refTileY;
  
  Reference(){ this("",0,0,0,0); }
  
  Reference(String name, float x, float y, int tX, int tY){ createRef(name, x, y, tX, tY); }
  
  public void clearRef(){
  
    refName = "";
    refX = 0;
    refY = 0;
  
  }
  
  public void createRef(String name, float x, float y, int tX, int tY){
  
    refName = name;
    refX = x;
    refY = y;
    refTileX = tX;
    refTileY = tY;
    
  }
  
  public String getRefName(){ return refName; }
  public float getRefX(){ return refX; }
  public float getRefY(){ return refY; }
  public int getRefTileX(){ return refTileX; }
  public int getRefTileY(){ return refTileY; }
  
  public void consoleOut(){println("Ref: " + refName + " refLoc: ( " + refX + " , " + refY + " ).");}

}
  public void settings() {  size(displayWidth, displayHeight); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Statistic_Viewer" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
