package com.mycompany.a3;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class StatusCommand extends Command
{
	Game g;
	public StatusCommand(Game g)
	{
	 super("Pause");
	 this.g = g;
	}
	   
	public void actionPerformed(ActionEvent av)
	{
	 if(((Button)av.getComponent()).getText().equals("Pause"))
	 {
	  ((Button)av.getComponent()).setText("Play");
	  g.pause();
	 }
	 else
	 {
	  ((Button)av.getComponent()).setText("Pause");
	  g.play();
	 }
	}
}
