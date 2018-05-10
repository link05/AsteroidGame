package com.mycompany.a3;

interface IMoveable{
	 void move(int time);
}
abstract class MoveableGameObject extends GameObject implements IMoveable {
  private int speed;
  private int direction;
  
  final int MAX_SPEED=10;
  final int MAX_DIRECTION=359;
  public int GetSpeed(){
	  return speed;
  }
  public int GetDirection(){
	  return direction;
  }
  public void UpdateSpeed(int s)
  {
	  speed=s;
  }
  public void UpdateDirection(int d)
  {
	  direction=d;
  }
  public void move(int time)
  {
	double deltaX,deltaY,theta;
	int newX,newY;
	double distance = GetSpeed() * time/25;
	theta=90- GetDirection();
	theta = Math.toRadians(theta);
	deltaX = Math.cos(theta)*distance;
	deltaY = Math.sin(theta)*distance;
	newX=(int) Math.round(deltaX+GetX());
	newY= (int)Math.round(deltaY+GetY());
	UpdateX(newX);
	UpdateY(newY);
  }
}
