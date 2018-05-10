package com.mycompany.a3;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;

public class QuitCommand extends Command{
	private GameWorld gw;
   public QuitCommand(GameWorld gw)
   {
	   super("Quit");
	   this.gw= gw;
   }
   
   public void actionPerformed(ActionEvent av)
   {
	  gw.QuitGame();
   }
}
