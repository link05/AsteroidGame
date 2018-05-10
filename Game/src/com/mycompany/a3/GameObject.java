package com.mycompany.a3;

public abstract class GameObject {
   private int locX,locY;
   private int color; //default value
   protected int size;
   protected int edge_center; //distance from left top most edge to the center, important for drawing only...its like offset variable
   //private int color2; //default value
   //private int color3; //default value
   
   public void UpdateX(int x_value){
	   locX= x_value;
   }
   public void UpdateY(int y_value){
	   locY= y_value;
   }
   public void UpdateColor(int c1)
   {
	color = c1;
	//color2 = c2;
	//color3 = c3;
   }
   public int GetX(){ return locX;}
   public int GetY(){ return locY;}
   public int GetColor(){return color;}
   public void setSize(int sz){size = sz;}
   public int GetSize(){return size;}
   public void SetEdge(int x){edge_center = x;}
   public int GetEdge(){return edge_center;}
}
