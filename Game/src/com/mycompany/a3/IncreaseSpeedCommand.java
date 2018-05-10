package com.mycompany.a3;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;

public class IncreaseSpeedCommand extends Command{
	private GameWorld gw;
   public IncreaseSpeedCommand(GameWorld gw)
   {
	   super("Increase Speed");
	   this.gw=gw;
   }
   
   public void actionPerformed(ActionEvent av)
   {
	   gw.AccelerateShip();
   }
}
