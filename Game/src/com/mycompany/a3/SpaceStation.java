package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class SpaceStation extends FixedGameObject implements IDrawable, ICollider {
	final int BLINKMAX=5;  //max valye for blinkrate
	private boolean is_blinking;
	private int blinkrate;
	
	SpaceStation(int w, int h) 
	{
	 super(); 
	 blinkrate = GetRandom(BLINKMAX);
	 System.out.println("added spacestation");
	 System.out.println("blinkrate "+blinkrate);
	 is_blinking=false;
	 setSize(50);
	 UpdateX(Math.round(GetRandomCoordinate(w)));
	 UpdateY(Math.round(GetRandomCoordinate(h)));
	 UpdateColor(ColorUtil.GRAY);
	}
	public void draw(Graphics g, Point p)
	{
	 int locX = this.GetX()+ p.getX();
	 int locY = this.GetY()+ p.getY();
	 g.setColor(ColorUtil.rgb(0, 200,200));
	 g.drawArc(locX-GetSize(),locY-GetSize(),GetSize()*2,GetSize()*2,0,360);
	 g.fillArc(locX-GetSize(),locY-GetSize(),GetSize()*2,GetSize()*2,0,360);
	 //have a red-ring if it is not blinking
	 if(!is_blinking)
	 {
	   g.setColor(ColorUtil.rgb(255,0,0));
	   g.drawArc(locX-GetSize(),locY-GetSize(),GetSize()*2,GetSize()*2, 0,360);
	 }
	 if(is_blinking)
	 {
	   g.setColor(ColorUtil.rgb(0,255,0));
	   g.drawArc(locX-GetSize()-3,locY-GetSize()-3,GetSize()*2,GetSize()*2, 0,360);
	 }
	}
	public int GetBlinkRate() {return blinkrate;}
	public void SetBlinkStatus(boolean b){ is_blinking=b;}
	public boolean GetBlinkStatus(){return is_blinking;}
	
	 //generate random #s for blinkrate
	private int GetRandom(int max) 
	{
	  Random r_num = new Random();
	  int x = r_num.nextInt(max+1); //+1 b/c its exlcusive
	  return x;
	}
		//generate random numbers for (x,y) coordinates
	private int GetRandomCoordinate(int max)
	  {
		  Random r_num = new Random();
		  int x =  r_num.nextInt(max)+1; //+1 b/c its exlcusive
		  return x;
	  }
	
	public String toString()
	{
	  String partA= "SpaceStation: loc="+ GetX()+","+GetY()+" color=["+ ColorUtil.red(GetColor())+","+ColorUtil.green(GetColor())+","+ColorUtil.blue(GetColor());
	  String partB= "] rate="+GetBlinkRate()+"\n";
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
	      System.out.println("Collision occured in saucer");
	      collision = true;
	    }
	    return collision;
	   }
}
