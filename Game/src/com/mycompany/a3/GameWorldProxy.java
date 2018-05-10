package com.mycompany.a3;

import java.util.*;

public class GameWorldProxy extends Observable implements IGameWorld {
	private GameWorld temp_gw; //temporary gameworld
	
	public GameWorldProxy(GameWorld gw)
	{
	  temp_gw = gw;
	}
	
	public int GetLife() { return temp_gw.GetLife();}
	public int GetScore() { return temp_gw.GetScore();}
	public int GetTime()  { return temp_gw.GetTime();}
	public int GetMisslesNum() { return temp_gw.GetMisslesNum();}
	public String ObjectLocations(){ return temp_gw.ObjectLocations();}
	public boolean GetSound(){return temp_gw.GetSound();}
}
