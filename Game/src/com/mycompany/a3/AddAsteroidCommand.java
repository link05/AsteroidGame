package com.mycompany.a3;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;

public class AddAsteroidCommand extends Command{
	 private GameWorld gw;
   public AddAsteroidCommand(GameWorld gw)
   {
	   super("Add Asteroid");
	   this.gw=gw;
   }
   
   public void actionPerformed(ActionEvent av)
   {
	   gw.AddAsteroid();
   }
}
