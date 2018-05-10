package com.mycompany.a3;

import java.util.*;
import com.codename1.ui.Container;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Border;


public class PointView extends Container implements Observer{
    private Label pointsValueLabel,LivesCountLabel,MisslesCountLabel,TimeCountLabel,SoundValueLabel;
    private GameWorld gw;
	public PointView(GameWorld gw)
    {
		this.gw = gw;
    	Label pointTextLabel = new Label("Points:");
    	Label LivesLabel = new Label("Lives:");
    	Label MisslesLabel = new Label("Missles:");
    	Label SoundLabel = new Label("Sound:");
    	Label TimeLabel = new Label("Time:");
    	
    	pointsValueLabel = new Label("0000");
    	LivesCountLabel  = new Label("0");
    	MisslesCountLabel = new Label("00");
    	TimeCountLabel = new Label("00");
    	SoundValueLabel = new Label("ON ");
    	
    	paddings(pointTextLabel);
    	paddings(LivesLabel);
    	paddings(MisslesLabel);
    	paddings(SoundLabel);
    	paddings(TimeLabel);
    	paddings(pointsValueLabel);
    	paddings(LivesCountLabel);
    	paddings(MisslesCountLabel);
    	paddings(TimeCountLabel);
    	paddings(SoundValueLabel);
    
    	Container myContainer = new Container();
    	myContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
    	
    	myContainer.add(pointTextLabel);
    	myContainer.add(pointsValueLabel);
    	myContainer.add(LivesLabel);
    	myContainer.add(LivesCountLabel);
    	myContainer.add(MisslesLabel);
    	myContainer.add(MisslesCountLabel);
    	myContainer.add(SoundLabel);
    	myContainer.add(SoundValueLabel);
    	myContainer.add(TimeLabel);
    	myContainer.add(TimeCountLabel);
    
    	this.add(myContainer);
    }
	public void update(Observable o, Object obj)
    {
	  IGameWorld gw = (IGameWorld) obj;
	  int score = gw.GetScore();
	  int lives= gw.GetLife();
	  int missles;
	  if(gw.GetMisslesNum() ==-1) missles=0;
	  else missles = gw.GetMisslesNum();
	  int time = gw.GetTime();
	  pointsValueLabel.setText(""+(score > 99 ? "":"0")+(score > 99 ? "":"0")+(score > 9 ? "" : "0")+ score);
	  LivesCountLabel.setText(""+ lives);
	  MisslesCountLabel.setText(""+(missles > 9 ? "" : "0")+ missles);
	  if(gw.GetSound()) SoundValueLabel.setText(""+"ON ");
	  else SoundValueLabel.setText(""+"OFF");
	  TimeCountLabel.setText(""+(time > 9 ? "" : "0")+ time);
	  this.repaint();
    }
	
	private void paddings(Label l)
	{
		l.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
    	l.getAllStyles().setBgTransparency(0);
    	l.getAllStyles().setPadding(RIGHT,5);
    	l.getAllStyles().setPadding(LEFT, 5);
    	l.getAllStyles().setBorder(Border.createCompoundBorder(
    			Border.createLineBorder(1,ColorUtil.GREEN),
    			Border.createLineBorder(1,ColorUtil.GREEN),
    			null,
    			null));
	}
}
