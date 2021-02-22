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
    maxPopularity = 0.0;
    maxPrice = 0.0;
    isVisible = v;
    zoom = 1;
    data = new CardList();
    refList = new Reference[100];
    for(int i = 0; i < refList.length; i++){ refList[i] = new Reference(); }
    refIndex = 0;
  }

  void addToCardList(Card card){ data.addCard(card); }                                          //Adds cards to the CardList
   
  void consoleOut(){                                                                            //Prints basic data about the graph and all the card names
    println("Graph title = " + title + "\n Location = ( " + x + ", " + y + " )\n width = " + w + " \nheight = " + h + "\n" );
    for(int i = 0; i < data.arraySize; i++){ if(!(data.cards[i].getName() == " ")){data.cards[i].consolOutCardShort();} }
  }
  
  void displayGraph(){                                                                          //Draws the Graph separatly from the draw() method because referenceing everything inside would be horendous
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
      text(str(int(maxPopularity / zoom / 9 * i)), x + (((w - 50)/8) * i) - 25, y + h - 12);
    }
    
    fill(0,0,255);                                                                              //Turns the Ellipes BLUE!!!
    
    for(int i = 0; i < data.arraySize; i++){                                                    //for (each card in the graph)
      float ellipseX = (data.cards[i].getPopularity() * w / maxPopularity * zoom) + x;          //calculate x location of ellipse based on graphlocation, maxPopularity and zoom level
      float ellipseY = (y + h) - (int(data.cards[i].getPrice() * h / maxPrice) * zoom);         //Calculate y location of ellipse based on "  " and invert it so 0,0 is in the bottom LH corner
      
      if((ellipseX >= x) && (ellipseX <= (x + w)) && (ellipseY >= y) && (ellipseY <= (y+h))){   //if (ellipse is inside the graph)
        ellipse(ellipseX, ellipseY, 15, 15);                                                    //Draw Ellipse
        addRef(data.cards[i].getName(), ellipseX, ellipseY, data.cards[i].getTileRefX(), data.cards[i].getTileRefY());  //Store cardName, ellipse x and y, and corresponding tile X and Y for use with mouse rollover  
      }
    }
  }
  
  void calculateGraphMaxs(){                                                                    //Calculates the maxPopularity and Max Price of a given graph
    for(int i = 0; i < data.arraySize; i++){                                                    //for( each card )
      float pop = data.getCard(i).cardPopularity;                                               //store its Popularity
      float price = data.getCard(i).cardPrice;                                                  //store its price
      
      if(pop > maxPopularity){ maxPopularity = pop; }                                           //if( its bigger than the max){make it the max}
      if(price > maxPrice){ maxPrice = price; }                                                 // ""
    }
  }
  
  void addRef(String name, float x, float y, int tX, int tY){ refList[refIndex].createRef(name, x, y, tX, tY); refIndex++; }    //adds and sets
  void setMaxPopularity(int pop){ maxPopularity = pop; }
  void setMaxPrice(int money){ maxPrice = money; }
  void setVisibility(boolean v){ isVisible = v; }
  void applyZoom(float z){ zoom += (z * .2); if(zoom < 1){ zoom = 1; }}
  
  boolean getVisibility(){ return isVisible; }                                                   //gets
  int getX(){ return x; }  
  int getY(){ return y; }
  int getWidth(){ return w; }
  int getHeight(){ return h; }
}
