package com.mycompany.a3;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;
public class SaveCmd extends Command {
   SaveCmd(){super("Save");}
   public void actionPerformed(ActionEvent ae)
   {
	   System.out.println("Save button was pressed.");
	}
}
