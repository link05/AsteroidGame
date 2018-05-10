package com.mycompany.a3;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Command;

public class NewCmd extends Command{
    NewCmd(){super("New");}
    public void actionPerformed(ActionEvent ae)
    {
    	System.out.println("New was pressed!");
    }
}
