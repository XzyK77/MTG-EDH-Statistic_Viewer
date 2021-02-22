class Reference{                                                        //Stores temporary reference points generatred when an ellipse is created on a graph

  String refName;
  float refX;
  float refY;
  int refTileX;
  int refTileY;
  
  Reference(){ this("",0,0,0,0); }
  
  Reference(String name, float x, float y, int tX, int tY){ createRef(name, x, y, tX, tY); }
  
  void clearRef(){
  
    refName = "";
    refX = 0;
    refY = 0;
  
  }
  
  void createRef(String name, float x, float y, int tX, int tY){
  
    refName = name;
    refX = x;
    refY = y;
    refTileX = tX;
    refTileY = tY;
    
  }
  
  String getRefName(){ return refName; }
  float getRefX(){ return refX; }
  float getRefY(){ return refY; }
  int getRefTileX(){ return refTileX; }
  int getRefTileY(){ return refTileY; }
  
  void consoleOut(){println("Ref: " + refName + " refLoc: ( " + refX + " , " + refY + " ).");}

}
