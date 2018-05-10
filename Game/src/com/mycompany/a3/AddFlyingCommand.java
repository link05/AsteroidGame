package com.mycompany.a3;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;

public class AddFlyingCommand extends Command{
	private GameWorld gw;
   public AddFlyingCommand(GameWorld g)
   {
	   super("Add Flying Saucer");
	   gw= g;
   }
   
   public void actionPerformed(ActionEvent av)
   {
	   gw.AddFlyingSaucer();
   }
}
