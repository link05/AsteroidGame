package com.mycompany.a3;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;

public class ReloadCommand extends Command{
	private GameWorld gw;
   public ReloadCommand(GameWorld gw)
   {
	   super("Reload");
	   this.gw = gw;
   }
   
   public void actionPerformed(ActionEvent av)
   {
	   gw.ReloadMissle();
   }
}
