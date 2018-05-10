package com.mycompany.a3;


import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;
public class AboutCmd extends Command {
    private GameWorld gw;
	AboutCmd(GameWorld gw)
    {
      super("About");
      this.gw = gw;
    }
    public void actionPerformed(ActionEvent ae)
    {
    	System.out.println("About is pressed!");
    	gw.About();
    }
}
