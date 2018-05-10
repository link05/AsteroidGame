package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Point;

public class MapView extends Container implements Observer{
	Label map;
	GameWorld gw;
	MapView(GameWorld g)
	{
	  gw = g;
	}
	public void pointerPressed(int x, int y)
	{
	 x = x - getParent().getAbsoluteX() - getX();
	 y = y - getParent().getAbsoluteY() - getY();
	 Point pPtrRelPrnt = new Point(x,y);
	 Object temp_obj;
	 UnselectObj();
	 //check if pointer is near or on obj
	 IIterator iterator1 = gw.GetObjects().getIterator();
	 while(iterator1.hasNext())
	 {
	   temp_obj = iterator1.getNext();
	   if(temp_obj instanceof ISelectable)
	   {
		 ISelectable select_temp = (ISelectable) temp_obj;
		 if(select_temp.contains(pPtrRelPrnt))
		 {
			select_temp.setSelected(true);
		   repaint();
		   return;
		 }
	   }
	 }
	}
	public void UnselectObj()
	{	 
	 IIterator iterator = gw.GetObjects().getIterator();
	 Object temp_obj;
	 //unselect previous objs...
	 while(iterator.hasNext())
	 {
	  temp_obj = iterator.getNext();
	  if(temp_obj instanceof ISelectable)
	   {
		 ISelectable select_temp = (ISelectable) temp_obj;
		 if(select_temp.isSelected()) 
		  {
		  select_temp.setSelected(false);
		  repaint();
		  }
		}
	  }//end of while	
	}
	public void update(Observable o,Object obj)
    {
	  System.out.println(gw.ObjectLocations());
      repaint();
    }
	public void paint(Graphics g)
	{
	 super.paint(g);
	 Point temp_point;
	 temp_point = new Point(this.getX(), this.getY());
	 IIterator iter = gw.GetObjects().getIterator();
	 while(iter.hasNext())
	 {
	  Object temp_obj = iter.getNext();
	  if(temp_obj instanceof Ship)
	  {
		Ship temp_ship = (Ship) temp_obj;
		temp_ship.draw(g,temp_point);
	  }
	  if(temp_obj instanceof Asteroid)
	  {
		Asteroid temp_aster = (Asteroid) temp_obj;
		temp_aster.draw(g,temp_point);
	  }
	  if(temp_obj instanceof FlyingSaucer)
	  {
		FlyingSaucer temp_fly = (FlyingSaucer) temp_obj;
		temp_fly.draw(g, temp_point);
	  }
	  if(temp_obj instanceof Missle)
	  {
	    Missle temp_missle = (Missle) temp_obj;
	    temp_missle.draw(g,temp_point);
	  }
	  if(temp_obj instanceof SpaceStation)
	  {
		SpaceStation temp_station = (SpaceStation) temp_obj;
		temp_station.draw(g,temp_point);
	  }
	 }//end of whileloop
    }//end of paint method
}//end of mapview
