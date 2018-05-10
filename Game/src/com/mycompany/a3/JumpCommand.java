package com.mycompany.a3;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;

public class JumpCommand extends Command{
	private GameWorld gw;
   public JumpCommand(GameWorld gw)
   {
	   super("Jump Command");
	   this.gw=gw;
   }
   
   public void actionPerformed(ActionEvent av)
   {
	   gw.MoveShipToDefault();
   }
}
