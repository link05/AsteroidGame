package com.mycompany.a3;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;

public class FireMissleCommand extends Command{
	private GameWorld gw;
   public FireMissleCommand(GameWorld gw)
   {
	   super("Fire Missle");
	   this.gw = gw;
   }
   
   public void actionPerformed(ActionEvent av)
   {
	   gw.Fire_Missile();
   }
}
