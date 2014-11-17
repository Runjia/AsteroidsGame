SpaceShip one;
Star [] number;
Asteroids [] num;
int numberOfStars=(int)(Math.random()*100+200);

public void setup() 
{
  size(800,800);
  one = new SpaceShip();
  number=new Star[numberOfStars];
  for (int i=0; i<number.length; i++){
    number[i]=new Star();
  }
  num=new Asteroids[50];
  for (int i=0; i<num.length; i++){
    num[i]=new Asteroids();
  }
}

public void draw() 
{
  background(0);
  for (int i=0; i<number.length; i++){
    number[i].show();
  }
  one.show();
  one.move();

  for (int i=0; i<num.length; i++){
    num[i].move();
    num[i].show();
  }
}

public void keyPressed()
{
  if (keyPressed && key == 'a'){
    one.rotate(-10);
  }
  if (keyPressed && key == 'd'){
    one.rotate(10);
  }
  if (keyPressed && key == 'w'){
    one.accelerate(0.3);
  }
  if (keyPressed && key == 's'){
    one.accelerate(-0.3);
  }
  if (keyPressed && key == 'h'){
    one.accelerate(0);
    one.setX((int)(Math.random()*800));
    one.setY((int)(Math.random()*800));
    one.setDirectionX(0);
    one.setDirectionY(0);
  } 
}

class Star{
  int StarX, StarY,StarSize,color1,color2,color3;
  Star(){
    StarX=(int)(Math.random()*800);
    StarY=(int)(Math.random()*800);
    StarSize=(int)(Math.random()*5+5);
    color1=(int)(Math.random()*255);
    color2=(int)(Math.random()*255);
    color3=(int)(Math.random()*255);
  }

  public void show(){
    noStroke();
    fill(color1,color2,color3);
    ellipse(StarX,StarY,StarSize,StarSize);
  }
}

class SpaceShip extends Floater  
{
  SpaceShip()
  {
    corners=3;
    xCorners=new int [corners];
    yCorners=new int [corners]; 
    xCorners[0] = -10; 
    yCorners[0] = -10;
    xCorners[1] = -10;
    yCorners[1] = 10;
    xCorners[2] = 20;
    yCorners[2] = 0;
    myColor=255;   
    myCenterX=400; //holds center coordinates
    myCenterY=400; //holds center coordinates   
    myDirectionX=0; //holds x and y coordinates of the vector for direction of travel
    myDirectionY=0; //holds x and y coordinates of the vector for direction of travel   
    myPointDirection=0;
  }   
  
  public void setX(int x){myCenterX=x;}  
  public int getX(){return (int)myCenterX;}   
  public void setY(int y){myCenterY=y;}   
  public int getY(){return (int)myCenterY;}  
  public void setDirectionX(double x){myDirectionX=x;}  
  public double getDirectionX(){return myDirectionX;}   
  public void setDirectionY(double y){myDirectionY=y;}   
  public double getDirectionY(){return myDirectionY;}   
  public void setPointDirection(int degrees){myPointDirection=degrees;}   
  public double getPointDirection(){return myPointDirection;}
}

class Asteroids extends Floater
{
  private int rotateSpeed;
  Asteroids(){
    corners=6;
    xCorners=new int [corners];
    yCorners=new int [corners]; 
    xCorners[0] = -10; 
    yCorners[0] = 0;
    xCorners[1] = -5;
    yCorners[1] = -10;
    xCorners[2] = 5;
    yCorners[2] = -10;
    xCorners[3] = 10;
    yCorners[3] = 0;
    xCorners[4] = 5;
    yCorners[4] = 10;
    xCorners[5] = -5;
    yCorners[5] = 10;
    myColor=150;
    myCenterX=(int)(Math.random()*800);
    myCenterY=(int)(Math.random()*800);
    myDirectionX=(Math.random()*3-1);
    myDirectionY=(Math.random()*3-1);
    myPointDirection=0;
    rotateSpeed=(int)(Math.random()*5+1);
    if (Math.random()<0.5){
      rotateSpeed=-1*rotateSpeed;
    }
  }

  public void setX(int x){myCenterX=x;}  
  public int getX(){return (int)myCenterX;}   
  public void setY(int y){myCenterY=y;}   
  public int getY(){return (int)myCenterY;}  
  public void setDirectionX(double x){myDirectionX=x;}  
  public double getDirectionX(){return myDirectionX;}   
  public void setDirectionY(double y){myDirectionY=y;}   
  public double getDirectionY(){return myDirectionY;}   
  public void setPointDirection(int degrees){myPointDirection=degrees;}   
  public double getPointDirection(){return myPointDirection;}

  /*public double getDirectionX(){
    return myDirectionX;
  }
    
  public double getDirectionY(){
    return myDirectionY;
  }

    
  public int getRotateSpeed(){
    return rotateSpeed;
  }*/
    

  public void move ()   //move the floater in the current direction of travel
  {      
  //change the x and y coordinates by myDirectionX and myDirectionY  
    myPointDirection+=rotateSpeed;     
    myCenterX += myDirectionX;    
    myCenterY += myDirectionY; 
    super.move();
  }   
}

abstract class Floater //Do NOT modify the Floater class! Make changes in the SpaceShip class 
{   
  protected int corners;  //the number of corners, a triangular floater has 3   
  protected int[] xCorners;   
  protected int[] yCorners;   
  protected int myColor;   
  protected double myCenterX, myCenterY; //holds center coordinates   
  protected double myDirectionX, myDirectionY; //holds x and y coordinates of the vector for direction of travel   
  protected double myPointDirection; //holds current direction the ship is pointing in degrees    
  abstract public void setX(int x);  
  abstract public int getX();   
  abstract public void setY(int y);   
  abstract public int getY();   
  abstract public void setDirectionX(double x);   
  abstract public double getDirectionX();   
  abstract public void setDirectionY(double y);   
  abstract public double getDirectionY();   
  abstract public void setPointDirection(int degrees);   
  abstract public double getPointDirection(); 

  //Accelerates the floater in the direction it is pointing (myPointDirection)   
  public void accelerate (double dAmount)   
  {          
    //convert the current direction the floater is pointing to radians    
    double dRadians =myPointDirection*(Math.PI/180);     
    //change coordinates of direction of travel    
    myDirectionX += ((dAmount) * Math.cos(dRadians));    
    myDirectionY += ((dAmount) * Math.sin(dRadians));       
  }   
  public void rotate (int nDegreesOfRotation)   
  {     
    //rotates the floater by a given number of degrees    
    myPointDirection+=nDegreesOfRotation;   
  }   
  public void move ()   //move the floater in the current direction of travel
  {      
    //change the x and y coordinates by myDirectionX and myDirectionY       
    myCenterX += myDirectionX;    
    myCenterY += myDirectionY;     

    //wrap around screen    
    if(myCenterX >width)
    {     
      myCenterX = 0;    
    }    
    else if (myCenterX<0)
    {     
      myCenterX = width;    
    }    
    if(myCenterY >height)
    {    
      myCenterY = 0;    
    }   
    else if (myCenterY < 0)
    {     
      myCenterY = height;    
    }   
  }   
  public void show ()  //Draws the floater at the current position  
  {             
    fill(myColor);   
    stroke(myColor);    
    //convert degrees to radians for sin and cos         
    double dRadians = myPointDirection*(Math.PI/180);                 
    int xRotatedTranslated, yRotatedTranslated;    
    beginShape();         
    for(int nI = 0; nI < corners; nI++)    
    {     
      //rotate and translate the coordinates of the floater using current direction 
      xRotatedTranslated = (int)((xCorners[nI]* Math.cos(dRadians)) - (yCorners[nI] * Math.sin(dRadians))+myCenterX);     
      yRotatedTranslated = (int)((xCorners[nI]* Math.sin(dRadians)) + (yCorners[nI] * Math.cos(dRadians))+myCenterY);      
      vertex(xRotatedTranslated,yRotatedTranslated);    
    }   
    endShape(CLOSE);  
  }   
} 

