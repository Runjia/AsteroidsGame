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

public class AsteroidsGame extends PApplet {

SpaceShip one;
Star [] number;
ArrayList <Asteroids> num;
int numberOfStars=(int)(Math.random()*100+200);
ArrayList <Bullets> shoot;
Explosion [] explode;

boolean crash=false;
boolean bigBang=false;

public void setup() 
{
  size(800,800);
  one = new SpaceShip();
  number=new Star[numberOfStars];
  for (int i=0; i<number.length; i++){
    number[i]=new Star();
  }

  num=new ArrayList <Asteroids>();
  for (int i=0; i<25; i++){
    num.add(new Asteroids());
  }

  shoot=new ArrayList <Bullets>();

  explode=new Explosion[100];
  for (int i=0; i<explode.length; i++){
    explode[i]=new Explosion();
  }
}

public void draw() 
{
  background(0);
  for (int i=0; i<number.length; i++){
    number[i].show();
  }

  if (crash==false){
    one.move();
    one.show();
    for (int p=0; p<explode.length; p++){
      explode[p].x=one.getX();
      explode[p].y=one.getY();
    }
  }
  
  for (int i=0; i<num.size(); i++){
    num.get(i).move();
    num.get(i).show();
    double distance1=dist(one.getX(), one.getY(), num.get(i).getX(), num.get(i).getY());
    if (distance1<20){
      bigBang=true;
      crash=true;
    }
    
    for (int n=0; n<shoot.size(); n++){
      double distance2=dist(shoot.get(n).getX(), shoot.get(n).getY(), num.get(i).getX(), num.get(i).getY());
      if (distance2<15){
        num.remove(i);
        shoot.remove(n);
      }
    }
  }

  for (int i=0; i<shoot.size(); i++){
    shoot.get(i).move();
    shoot.get(i).show();
  }

  if (bigBang==true){
    for (int p=0; p<explode.length; p++){
      explode[p].move();
      explode[p].show();
    }
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
    one.accelerate(0.3f);
  }
  if (keyPressed && key == 's'){
    one.accelerate(-0.3f);
  }
  if (keyPressed && key == 'h'){
    one.accelerate(0);
    one.setX((int)(Math.random()*800));
    one.setY((int)(Math.random()*800));
    one.setDirectionX(0);
    one.setDirectionY(0);
  } 

  if (keyPressed && key == ' '){
    shoot.add(new Bullets());
  }
}

class Explosion{
  public double x,y,angle,speed;
  Explosion(){
    x=one.getX();
    y=one.getY();
    angle=(Math.random()*2)*(Math.PI);
    speed=Math.random()*2+2;
  }

  public void move(){
    x=x+(Math.cos(angle)*speed);
    y=y+(Math.sin(angle)*speed);
  }

  public void show(){
    noStroke();
    fill(255,0,0);
    ellipse((float)x,(float)y,5,5);
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
    xCorners[0] = (int)myCenterX-10; 
    yCorners[0] = (int)myCenterY-10;
    xCorners[1] = (int)myCenterX-10;
    yCorners[1] = (int)myCenterY+10;
    xCorners[2] = (int)myCenterX+20;
    yCorners[2] = (int)myCenterX;
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
    if (Math.random()<0.5f){
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
    
  public void move ()   //move the floater in the current direction of travel
  {      
  //change the x and y coordinates by myDirectionX and myDirectionY  
    myPointDirection+=rotateSpeed;     
    myCenterX += myDirectionX;    
    myCenterY += myDirectionY; 
    super.move();
  }   
}

class Bullets extends Floater{
  Bullets(){
    myCenterX=one.getX();
    myCenterY=one.getY();
    myPointDirection=one.getPointDirection();
    double dRadians=myPointDirection*(Math.PI/180);
    myDirectionX=5*Math.cos(dRadians)+one.getDirectionX();
    myDirectionY=5*Math.sin(dRadians)+one.getDirectionY();
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

  public void move(){
    myCenterX += myDirectionX;    
    myCenterY += myDirectionY;
  }

  public void show(){
    fill(255);
    noStroke();
    for (int i=0; i<shoot.size(); i++){
      ellipse(shoot.get(i).getX(),shoot.get(i).getY(),2,2);
    }
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

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "AsteroidsGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
