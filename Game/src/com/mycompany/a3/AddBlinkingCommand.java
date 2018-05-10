package com.mycompany.a3;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;

public class AddBlinkingCommand extends Command{
	private GameWorld gw;
   public AddBlinkingCommand(GameWorld gw)
   {
	   super("Add Blinking Station");
	   this.gw= gw;
   }
   
   public void actionPerformed(ActionEvent av)
   {
	   gw.AddSpacestation();
   }
}
