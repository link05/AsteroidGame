package com.mycompany.a3;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;
public class UndoCmd extends Command {
   UndoCmd(){super("Undo");}
   public void actionPerformed(ActionEvent ae)
   {
	   System.out.println("Undo pressed!");
   }
}
