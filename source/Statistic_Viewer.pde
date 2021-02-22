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

void setup(){
  
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
  
  shadowCard = new Card(" "," "," "," ", 0, 0.0,0,0);                                                                    //shadowcard to be used when distributing cards to different arrays
  
  size(displayWidth, displayHeight);                                                                                     //screen size of minitor
  
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

void draw(){

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

void mouseWheel(MouseEvent event){
  float e = event.getCount();                                          //float will be either 1 or -1.  Positive -> Mouse Wheel Down; Negative -> Mouse Wheel Up.  Reversed for Mac.
  for (int i = 0; i < graph.length; i++){                              //for(each graph)
    if(mouseIsInsideGraph(graph[i])){ graph[i].applyZoom(e); }         //If(mouse is inside the graph){ apply the mouseWheel event to that graph's zoom value}
  }
}

//===========================================================================================================================================

boolean mouseIsInsideGraph(Graph g){                                   //Used to lessen some text on the screen, but checks if the mouse is inside the bounds of a graph; Returns true or false
  if(g.getVisibility() == true){
    if((mouseX > g.getX()) && (mouseX < (g.getWidth() + g.getX()) && (mouseY > g.getY()) && mouseY < (g.getHeight() + g.getY()))){ return true; }
  }
  return false;
}

void keyPressed(){                                                    
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
