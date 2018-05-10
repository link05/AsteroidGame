package com.mycompany.a3;


import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Asteroid extends MoveableGameObject implements IDrawable, ICollider, ISelectable{
  private final int SIZE_MIN=6;
  private final int SIZE_MAX=30;
  private boolean selected=false;
  Asteroid(int w, int h)
  {
	UpdateX(GetRandomCoordinate(w));
	UpdateY(GetRandomCoordinate(h));
	UpdateSpeed(GetRandom(1,MAX_SPEED));
	//UpdateSpeed(0);
	UpdateDirection(GetRandom(0,MAX_DIRECTION));
	UpdateColor(ColorUtil.rgb(255,0,0));
	size= GetRandom(SIZE_MIN,SIZE_MAX);
	SetSize(size);
	
	//drawing sizes
	if(GetSize()<=18) SetEdge(20);
	else if(GetSize() >18 && GetSize() <=24)  SetEdge(30);
	else if(GetSize()>=25 && GetSize() <= 30) SetEdge(40);
	System.out.println("added new asteroid");
   }
  public void draw(Graphics g, Point p)
  {
	int locX = this.GetX()+ p.getX();
	int locY = this.GetY()+ p.getY();
	if(selected)
	{
	 g.setColor(ColorUtil.rgb(0, 0, 0));
	 g.drawArc(locX-GetEdge(),locY-GetEdge(),GetEdge()*2,GetEdge()*2,0,360);
	 g.fillArc(locX-GetEdge(),locY-GetEdge(),GetEdge()*2,GetEdge()*2,0,360);
	}
	else
	{
 	 g.setColor(GetColor());
	 g.drawArc(locX-GetEdge(),locY-GetEdge(),GetEdge()*2,GetEdge()*2,0,360);
	 g.fillArc(locX-GetEdge(),locY-GetEdge(),GetEdge()*2,GetEdge()*2,0,360);	
	}
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
  public void SetSize(int s){size= s;}
  
  //returns value for asteroid size
  private int GetRandom(int min,int max)
  {
   Random r_num = new Random();
   int x = r_num.nextInt(max - min + 1)+min;        //+1 b/c its exlcusive
   return x;
  }
  
//generate random numbers for (x,y) coordinates
  private int GetRandomCoordinate(int max)
  {
	  Random r_num = new Random();
	  int x =  r_num.nextInt(max)+1; //+1 b/c its exlcusive
	  return x;
  }
	
  public int GetSize()
  {
	  return size;
  }
  
  public String toString()
  {
	  String partA="Asteroid: loc="+ GetX()+","+GetY()+" color=["+ ColorUtil.red(GetColor())+","+ColorUtil.green(GetColor())+","+ColorUtil.blue(GetColor());
	  String partB="] speed="+GetSpeed()+" dir="+GetDirection()+" size:"+GetSize()+"\n";
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
