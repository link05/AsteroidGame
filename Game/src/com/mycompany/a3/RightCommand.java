package com.mycompany.a3;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;

public class RightCommand extends Command{
	private GameWorld gw;
   public RightCommand(GameWorld gw)
   {
	   super("Move Right");
	   this.gw = gw;
   }
   
   public void actionPerformed(ActionEvent av)
   {
	   gw.MoveShipRight();
   }
}
