package com.mycompany.a3;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;

public class AddSpaceshipCommand extends Command{
	private GameWorld gw;
   public AddSpaceshipCommand(GameWorld gw)
   {
	   super("Add SpaceShip");
	   this.gw = gw;
   }
   
   public void actionPerformed(ActionEvent av)
   {
	   gw.AddSpaceship();
	   
   }
}
