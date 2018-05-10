package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class FlyingSaucer extends MoveableGameObject implements IDrawable, ICollider {
  FlyingSaucer(int w, int h){
	  UpdateX(GetRandomCoordinate(w));
	  UpdateY(GetRandomCoordinate(h)); //set random # as height between 0 to h
	  UpdateSpeed(GetRandom(MAX_SPEED));
	  //UpdateSpeed(0);
	  UpdateDirection(GetRandom(MAX_DIRECTION));
	  UpdateColor(ColorUtil.rgb(255,0,0));
	  size=GetRandomSize();
	  SetEdge(30);
	  System.out.println("added new FlyingSaucer");
  }
  public void draw(Graphics g, Point p)
  {
	int locX = this.GetX()+ p.getX();
	int locY = this.GetY()+ p.getY();
	g.setColor(GetColor());
	g.drawRect(locX-GetEdge(),locY-GetEdge(),GetEdge()*2,GetEdge()*2);
	g.setColor(ColorUtil.rgb(0, 0, 0));
	g.drawRect(locX,locY,2,2);
  }
  //generate random numbers for (x,y) coordinates
  private int GetRandomCoordinate(int max)
  {
	  Random r_num = new Random();
	  int x =  r_num.nextInt(max);
	  return x;
  }
  //randomly returns number for speed and direction values
  private int GetRandom(int max)
  {
   Random r_num = new Random();
   int x = r_num.nextInt(max+1); //+1 b/c its exlcusive
   return x;
  }
  
  private int GetRandomSize(){
	  int x = GetRandom(10); //get random number between 0-9(5 even #, 5 odd #)
	  if (x%2 ==0) return 10; 
	  else return 20;
  }
  
  public String toString()
  {
	String partA= "FlyingSaucer: loc="+ GetX()+","+GetY()+" color=["+ColorUtil.red(GetColor())+","+ColorUtil.green(GetColor())+","+ColorUtil.blue(GetColor());
	String partB= "] speed="+GetSpeed()+" dir="+GetDirection()+" size:"+GetSize()+"\n";
	return partA+partB;
  }
  public boolean CollidesWith(ICollider otherObj)
  {
	boolean collision = false;
    GameObject temp_obj = (GameObject) otherObj;
    int dX = temp_obj.GetX()- GetX();
    int dY = temp_obj.GetY()- GetY();
    int total_difference = (dX * dX)+(dY*dY);
    int total_radius = temp_obj.GetEdge()+GetEdge();
    total_radius= total_radius * total_radius;
    if(total_difference<= total_radius)
    {
      collision = true;
    }
    return collision;
   }
}
