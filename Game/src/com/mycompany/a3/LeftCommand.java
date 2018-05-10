package com.mycompany.a3;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;

public class LeftCommand extends Command{
	private GameWorld gw;
   public LeftCommand(GameWorld gw)
   {
	   super("left Command");
	   this.gw = gw;
   }
   
   public void actionPerformed(ActionEvent av)
   {
	   gw.MoveShipLeft();
   }
}
