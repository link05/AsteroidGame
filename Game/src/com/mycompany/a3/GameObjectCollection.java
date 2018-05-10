package com.mycompany.a3;


import java.util.*;

public class GameObjectCollection implements ICollection 
   {
    private Vector gameobjs;
	public GameObjectCollection(){
		gameobjs = new Vector();
	}
	
	public void add(Object newObject)
	{
	  gameobjs.addElement(newObject);
	}
	public IIterator getIterator()
	{
		return new GameobjsIterator();
	}
	public void Delete(Object obj)
	{
      gameobjs.remove(obj);
	}
	private class GameobjsIterator implements IIterator 
	{
	  private int curElementIndex;
	  public GameobjsIterator() 
	  {
	   curElementIndex=-1;
	  }
	  public void Delete(Object obj)
	  {
	   gameobjs.remove(obj);
	   System.out.println("removed obj"+obj+curElementIndex);
	   curElementIndex--;
	   System.out.println("count updated"+curElementIndex);
	  }
	  public boolean hasNext()
	  {
		if(gameobjs.size() <= 0) return false;
		else if(curElementIndex == gameobjs.size()-1) return false;
		else if(curElementIndex >= gameobjs.size()) return false;
		else return true;
	  }
	  public Object getNext()
	  {
		  curElementIndex++;
		 return gameobjs.elementAt(curElementIndex);
	  }
	 }//end of GameobjsIterator
   } //end of GameObjectCollection
