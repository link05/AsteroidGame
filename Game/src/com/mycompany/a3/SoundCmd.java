package com.mycompany.a3;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;

public class SoundCmd extends Command {
	private GameWorld gw;
      SoundCmd(GameWorld gw){
    	  super("Sound");
    	  this.gw = gw;
      }
    public void actionPerformed(ActionEvent ae)
    {
    	System.out.println("Sound Button pressed");
    	gw.UpdateSound();
    }
}
