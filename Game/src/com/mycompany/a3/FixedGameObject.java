package com.mycompany.a3;

abstract class FixedGameObject extends GameObject{
  private static int count=0;
  private int id;
  
  FixedGameObject()
  {
	count++; //increment count
	SetId(GetCount());  //set id for space
  }
  private void SetId(int id){this.id=id;}
  public int GetId(){return id;}
  public int GetCount(){return count;}
}