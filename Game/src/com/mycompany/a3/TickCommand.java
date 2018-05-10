package com.mycompany.a3;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;

public class TickCommand extends Command{
	private GameWorld gw;
   public TickCommand(GameWorld gw)
   {
	   super("Tick ");
	   this.gw = gw;
   }
   
   public void actionPerformed(ActionEvent av)
   {
	   gw.Tick();
   }
}
