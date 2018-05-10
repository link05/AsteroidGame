package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Missle extends MoveableGameObject implements IDrawable, ICollider, ISelectable{
   private int fuel_level=10;
   private int WIDTH = 10;
   private int HEIGHT = 15;
   private int MAX_FUEL = 10;
   private boolean selected= false;
   private int SPEED = 10;
   Missle(int x,int y,int direction)
   {
	 UpdateColor(ColorUtil.rgb(50,255,150));
	 UpdateX(x);
	 UpdateY(y);
	 UpdateSpeed(SPEED);
	 UpdateDirection(direction);
	 setSize(8);
	 SetEdge(8);
	 System.out.println("Fired missle");
   }
   public void SetFuel() {fuel_level = MAX_FUEL;}
   public int GetFuel(){return fuel_level;}
   public void draw(Graphics g, Point p)
   {
	int locX = this.GetX()+ p.getX();
	int locY = this.GetY()+ p.getY();
	if(selected) g.setColor(ColorUtil.rgb(0,0, 0));
	else g.setColor(GetColor());
	g.drawRect(locX-GetEdge(),locY-GetEdge(),2*GetEdge(),2*GetEdge());
	g.fillRect(locX-GetEdge(),locY-GetEdge(),2*GetEdge(),2*GetEdge());
   }
   public int GetFuelLevel(){return fuel_level;}
   public void DecrementFuelLevel(){fuel_level--;}
   public String toString()
   {
	 String partA = "Missle: loc="+ GetX()+","+GetY()+" color=["+ColorUtil.red(GetColor())+","+ColorUtil.green(GetColor())+","+ColorUtil.blue(GetColor());
     String partB= "] speed="+GetSpeed()+" dir="+GetDirection()+" fuel:"+GetFuelLevel()+"\n";
     return partA+partB;
   }
 //calculate the change in x in 2 objs and change in y in 2 objs and square them
   //difference = change in x,y
   //if(radiusOb1 + radiusOb2)^2 <= differencce then obj is colling
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
   public void setSelected(boolean YesNo){selected= YesNo;}
   public boolean isSelected(){return selected;}
   public boolean contains(Point pPtrRelPrnt)
   {
 	int px = pPtrRelPrnt.getX() - GetX();  //grab mouse x location 
 	int py = pPtrRelPrnt.getY() - GetY();  //grab mouse y location
 	int dist = (px * px) + (py * py);
 	if(dist <= (GetEdge()*GetEdge()))
 	{
 	 return true;
 	}
 	return false;
   }
}
