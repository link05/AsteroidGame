package com.mycompany.a3;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

class Ship extends MoveableGameObject implements ISteerable, IDrawable, ICollider {
   private int missle_count;
   private final int MAX_MISSLE=12;
   public Ship(int w, int h)
   {
	 UpdateColor(ColorUtil.rgb(0,255,0));
	 UpdateX(w/2);
	 UpdateY(h/2);
	 UpdateSpeed(0);
	 UpdateDirection(90);
	 setSize(6);
	 SetEdge(30);
	 missle_count=12;
	 System.out.println("added new spaceship");
   }
   public void draw(Graphics g, Point p)
   {
	int locX = this.GetX()+ p.getX();
	int locY = this.GetY()+ p.getY();
	int [] x_val = {locX,locX-GetEdge(), locX+GetEdge()};
	int [] y_val = {locY+GetEdge(),locY-GetEdge(),locY-GetEdge()};
	g.setColor(GetColor());
	g.fillPolygon(x_val, y_val,3);
   }
   private int GetRandomCoordinate(int max)
   {
 	  Random r_num = new Random();
 	  int x =  r_num.nextInt(max)+1; //+1 b/c its exlcusive
 	  return x;
   }
   public int GetMissleCount(){return missle_count;}
   public void UpdateMissleCount()
   {
	  missle_count=MAX_MISSLE;
	  System.out.println("reloaded missle");
   }
   public void DecreaseMissleCount(){missle_count--;}
   public void Default_Location(){
	   UpdateX(512);
	   UpdateY(384);
   }
   public void IncreaseSpeed()
   {
	 System.out.println("increasing speed by 1");
	 if(GetSpeed() <5 ) UpdateSpeed(GetSpeed()+1);   //only increase if its less than max speed which is 5
	}
   
    public void DecreaseSpeed()
    {
      System.out.println("Decreasing speed");
      if(GetSpeed()> 0) UpdateSpeed((GetSpeed()-1));
    } 
   public void GoLeft()
   {
	UpdateDirection(GetDirection()+10); //range is 0-359
	System.out.println("Moved ship to left by 10 degrees");
   }
   
   public void GoRight()
   {
	UpdateDirection(GetDirection()-10); 
	System.out.println("Moved ship to right by 10 degrees"+GetDirection());
   }
   
   public String toString()
   {
	 String partA= "Ship: loc="+  GetX()+","+ GetY()+" color=["+ ColorUtil.red(GetColor())+","+ColorUtil.green(GetColor())+","+ColorUtil.blue(GetColor());
	 String partB="] speed="+ GetSpeed()+" dir="+ GetDirection()+" missiles="+ GetMissleCount()+"\n";
	 return partA+partB;	
   }
   public boolean CollidesWith(ICollider otherObj)
   {
 	boolean collision = false;
     GameObject temp_obj = (GameObject) otherObj;
     int dX = temp_obj.GetX()- GetX();
     int dY = temp_obj.GetY()- GetY();
     int total_difference = (dX * dX)+(dY*dY);
     int total_radius = (temp_obj.GetEdge()+GetEdge())*(temp_obj.GetEdge()+GetEdge());
     if(total_difference <= total_radius)
     {
       collision = true;
     }
     return collision;
    }
   
}
