package com.mycompany.a3;
import com.codename1.charts.util.ColorUtil;
/*
 * modified function in ship...increase/decrease implement it in UML as well
 * same thing in gameworld as well..
 * 
 * 
 * 
 * 
 * 
 */
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;

/*
 * 
 * 
 * create new project and ensure jar file is threre so is readme
 */
public class Game extends Form implements Runnable {
	 private GameWorld gw;
	 private MapView mv;
	 private PointView pv;
	 private UITimer timer;
	 private Button RefuelBtn,AsteroidBtn,FlyingBtn,BlinkStnBtn,SpaceshipBtn,IncreamentBtn,DecrementBtn,LeftBtn,RightBtn;
	 private Button FireMissleBtn,JumpBtn,ExitBtn,gameStatus;
 	 private RefuelCommand refuelCmd;
 	 private StatusCommand statusCmd;
 	 private AddAsteroidCommand asteroidCmd;
 	 private AddFlyingCommand flyingSaucerCmd;
 	 private AddBlinkingCommand blinkingstationCmd;
 	 private AddSpaceshipCommand spaceshipCmd;
 	 private IncreaseSpeedCommand increaseSpeedCmd;
 	 private DecreaseSpeedCommand reduceSpeedCmd;
 	 private LeftCommand moveLeftCmd;
 	 private RightCommand moveRightCmd;
 	 private FireMissleCommand firemissleCmd;
 	 private JumpCommand jumpCmd;
 	 private ReloadCommand reloadCmd;
 	 private TickCommand tickCmd;
 	 private QuitCommand QuitCmd;
 	 private CheckBox sound_check;
 	 private Command game_summary;
	 private boolean gamepaused=false;
	 Game()
     {
		//set layout for main form
		setLayout(new BorderLayout());
    	//create game world obj
    	gw= new GameWorld();
    	mv = new MapView(gw);
    	pv = new PointView(gw);
        getAllStyles().setBgColor(ColorUtil.rgb(128, 128, 128));
        getAllStyles().setFgColor(ColorUtil.rgb(128, 128, 128));
        getAllStyles().setBgTransparency(255);
    	gw.addObserver(mv);
    	gw.addObserver(pv);
    	add(BorderLayout.NORTH,pv);
    	add(BorderLayout.CENTER,mv);
    	
    	//create buttons that go on the left-hand side
    	Container left = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    	left.getAllStyles().setPadding(Component.TOP,50);
    	AsteroidBtn= new Button("add asteroid command");
    	FlyingBtn= new Button("add FlyingSaucer command");
    	BlinkStnBtn= new Button("add BlinkStation command");
    	SpaceshipBtn= new Button("add spaceship command");
    	IncreamentBtn = new Button("Increase Ship speed command");
    	DecrementBtn = new Button("Decrease ship speed command");
    	LeftBtn = new Button("Go left command");
    	RightBtn = new Button("Go right command");
    	FireMissleBtn = new Button("Fire Ship Missle Command");
    	JumpBtn = new Button("Jump command");
    	ExitBtn = new Button("Exit");
    	Label status = new Label("Pause");
    	gameStatus = new Button(status.toString());
    	RefuelBtn = new Button("Refuel");
    	AddPadding(RefuelBtn);
    	RefuelBtn.setEnabled(false);

    	//create commands to trigger if buttons get pushed    	
    	refuelCmd = new RefuelCommand(gw);
    	refuelCmd.setEnabled(false);
    	statusCmd = new StatusCommand(this);
    	asteroidCmd = new AddAsteroidCommand(gw);
    	flyingSaucerCmd= new AddFlyingCommand(gw);
    	blinkingstationCmd = new AddBlinkingCommand(gw);
    	spaceshipCmd = new AddSpaceshipCommand(gw);
    	increaseSpeedCmd= new IncreaseSpeedCommand(gw);
    	reduceSpeedCmd = new DecreaseSpeedCommand(gw);
    	moveLeftCmd = new LeftCommand(gw);
    	moveRightCmd = new RightCommand(gw);
    	firemissleCmd = new FireMissleCommand(gw);
    	jumpCmd = new JumpCommand(gw);
    	reloadCmd = new  ReloadCommand(gw);  
    	tickCmd= new TickCommand(gw);
    	QuitCmd= new QuitCommand(gw);
    	
    	//create menu bar
    	Toolbar myToolbar = new Toolbar();
    	setToolbar(myToolbar);
    	myToolbar.setTitle("Asteroid Game");
    	myToolbar.setTitleCentered(true);
    	
    	//SideMenu
    	myToolbar.addCommandToOverflowMenu(asteroidCmd);
    	myToolbar.addCommandToOverflowMenu(blinkingstationCmd);
    	myToolbar.addCommandToOverflowMenu(spaceshipCmd);
    	myToolbar.addCommandToOverflowMenu(reloadCmd);
    	myToolbar.addCommandToOverflowMenu(tickCmd);
    	myToolbar.addCommandToOverflowMenu(QuitCmd);
    	
    	//overflow commands
    	NewCmd new_game = new NewCmd();
    	SaveCmd save = new SaveCmd();
    	UndoCmd undo = new UndoCmd();
    	SoundCmd sound = new SoundCmd(gw);
    	AboutCmd about = new AboutCmd(gw);
    	sound_check = new CheckBox("Sound ON");
    	sound_check.setSelected(true);
    	
		sound_check.getUnselectedStyle().setBgTransparency(255);
		sound_check.getUnselectedStyle().setBgColor(ColorUtil.rgb(255,255,255));
		sound_check.getUnselectedStyle().setFgColor(ColorUtil.rgb(0,0,0));
    	sound_check.setCommand(sound);
    	
    	//overflow menu
    	myToolbar.addComponentToSideMenu(sound_check);
    	myToolbar.addCommandToSideMenu(new_game);
    	myToolbar.addCommandToSideMenu(save);
    	myToolbar.addCommandToSideMenu(undo);
    	myToolbar.addCommandToSideMenu(about);
    	myToolbar.addCommandToSideMenu(QuitCmd);
    	
    	//connect buttons to command
    	gameStatus.setCommand(statusCmd);
    	RefuelBtn.setCommand(refuelCmd);
    	AsteroidBtn.setCommand(asteroidCmd);
    	SpaceshipBtn.setCommand(spaceshipCmd);
    	BlinkStnBtn.setCommand(blinkingstationCmd);
    	DecrementBtn.setCommand(reduceSpeedCmd);
    	FireMissleBtn.setCommand(firemissleCmd);
    	IncreamentBtn.setCommand(increaseSpeedCmd);
    	FlyingBtn.setCommand(flyingSaucerCmd);
    	LeftBtn.setCommand(moveLeftCmd);
    	RightBtn.setCommand(moveRightCmd);
    	JumpBtn.setCommand(jumpCmd);
    	ExitBtn.setCommand(QuitCmd);
    	
    	//add buttons to left container
    	left.add(gameStatus);
    	left.add(RefuelBtn);
    	left.add(AsteroidBtn);
    	left.add(SpaceshipBtn);
    	left.add(BlinkStnBtn);
    	left.add(DecrementBtn);
    	left.add(FireMissleBtn);
    	left.add(IncreamentBtn);
    	left.add(FlyingBtn);
    	left.add(LeftBtn);
    	left.add(RightBtn);
    	left.add(JumpBtn);
    	left.add(ExitBtn);
    	
    	//add padding between the buttons
    	AddPadding(gameStatus);
    	AddPadding(AsteroidBtn);
    	AddPadding(SpaceshipBtn);
    	AddPadding(BlinkStnBtn);
    	AddPadding(DecrementBtn);
    	AddPadding(FireMissleBtn);
    	AddPadding(IncreamentBtn);
    	AddPadding(FlyingBtn);
    	AddPadding(LeftBtn);
    	AddPadding(RightBtn);
    	AddPadding(JumpBtn);
    	AddPadding(ExitBtn);
    	
    	//key listener
    	addKeyListener(-91,increaseSpeedCmd);
    	addKeyListener(-92,reduceSpeedCmd);
    	addKeyListener(-93,moveLeftCmd);
    	addKeyListener(-94,moveRightCmd);
    	addKeyListener(-90,firemissleCmd);
    	addKeyListener('j',jumpCmd);
    	
    	left.getAllStyles().setBorder(Border.createLineBorder(4,ColorUtil.BLUE));
    	add(BorderLayout.WEST,left);
    	this.show();  
    	gw.setGameDimensions(mv.getWidth(),mv.getHeight());
    	gw.SetMapOrigin(mv.getX(),mv.getY());
    	timer = new UITimer(this);
    	timer.schedule(20,true,this);
    	gw.SetTime(20);
    }
    public void run()
    {
      gw.Tick();
      if(gw.IsGameOver())
      {
    	GameEnd(); 
      }
    }
    public boolean getPause(){return gamepaused;}
    public void setPause(boolean p){ gamepaused = p;}
    public UITimer GetTimer(){return timer;}
    public void GameEnd()
    {
      GetTimer().cancel();
      gw.GetBgMusic().pause();
      gw.SetGamePause(true);
      setPause(true);
      Command cok = new Command("Done");
      String msg= "  your score was: \n"+ gw.GetScore()+"  ";
      Label placeholder = new Label("");
      game_summary= Dialog.show(msg,placeholder, cok);
    }
    public void pause()
    {
      GetTimer().cancel();
      gw.GetBgMusic().pause();
      gw.SetGamePause(true);
      setPause(true);
      DisableButtons();
    }
    public void play()
    {
      mv.UnselectObj();
      GetTimer().schedule(20,true,this);
      if(sound_check.isSelected()) gw.GetBgMusic().play();
      gw.SetGamePause(false);
      setPause(false);
      EnableButtons();
    }
    
    /*DisableButtons(): This method disables buttons when pause is pressed*/
    public void DisableButtons()
    {
     //disable buttons in pause except refuel
     RefuelBtn.setEnabled(true);
   	 AsteroidBtn.setEnabled(false);
   	 FlyingBtn.setEnabled(false);
   	 SpaceshipBtn.setEnabled(false);
   	 IncreamentBtn.setEnabled(false);
   	 DecrementBtn.setEnabled(false);
   	 LeftBtn.setEnabled(false);
   	 RightBtn.setEnabled(false);
   	 FireMissleBtn.setEnabled(false);
   	 JumpBtn.setEnabled(false);
   	 BlinkStnBtn.setEnabled(false);
   	 
   	 //for commands
 	asteroidCmd.setEnabled(false);
 	flyingSaucerCmd.setEnabled(false);
 	blinkingstationCmd.setEnabled(false);
 	spaceshipCmd.setEnabled(false);
 	increaseSpeedCmd.setEnabled(false);
 	reduceSpeedCmd.setEnabled(false);
 	moveLeftCmd.setEnabled(false);
 	moveRightCmd.setEnabled(false);
 	firemissleCmd.setEnabled(false);
 	jumpCmd.setEnabled(false);
 	reloadCmd.setEnabled(false);
    }
    //This method enables buttons and commands and is called by play()
    public void EnableButtons()
    {
     //disable buttons in pause except refuel
     RefuelBtn.setEnabled(false);
   	 AsteroidBtn.setEnabled(true);
   	 FlyingBtn.setEnabled(true);
   	 SpaceshipBtn.setEnabled(true);
   	 IncreamentBtn.setEnabled(true);
   	 DecrementBtn.setEnabled(true);
   	 LeftBtn.setEnabled(true);
   	 RightBtn.setEnabled(true);
   	 FireMissleBtn.setEnabled(true);
   	 JumpBtn.setEnabled(true);
   	 ExitBtn.setEnabled(true);
   	 BlinkStnBtn.setEnabled(true);
   	 
   	 //for commands
   	refuelCmd.setEnabled(false);
 	asteroidCmd.setEnabled(true);
 	flyingSaucerCmd.setEnabled(true);
 	blinkingstationCmd.setEnabled(true);
 	spaceshipCmd.setEnabled(true);
 	increaseSpeedCmd.setEnabled(true);
 	reduceSpeedCmd.setEnabled(true);
 	moveLeftCmd.setEnabled(true);
 	moveRightCmd.setEnabled(true);
 	firemissleCmd.setEnabled(true);
 	jumpCmd.setEnabled(true);
 	reloadCmd.setEnabled(true);
 	tickCmd.setEnabled(true);
 	QuitCmd.setEnabled(true);
    }
    
    private void AddPadding(Button b)
    {
    	b.getUnselectedStyle().setBgTransparency(255);
		b.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,0,255));
		b.getUnselectedStyle().setFgColor(ColorUtil.rgb(100,150,250));
		b.getAllStyles().setPadding(Component.TOP, 3);
		b.getAllStyles().setPadding(Component.BOTTOM, 3);
		DisabledModePadding(b);
    }
    private void DisabledModePadding(Button b)
    {
    	b.getDisabledStyle().setBgTransparency(255);
    	b.getDisabledStyle().setBgColor(ColorUtil.rgb(200,100,100));
    	b.getDisabledStyle().setFgColor(ColorUtil.rgb(0,0,0));
    	/*
    	b.getUnselectedStyle().setBgTransparency(255);
    	b.getUnselectedStyle().setBgColor(ColorUtil.rgb(250,50,50));
    	b.getUnselectedStyle().setFgColor(ColorUtil.rgb(0,0,0));*/
	    b.getAllStyles().setPadding(Component.TOP, 3);
        b.getAllStyles().setPadding(Component.BOTTOM, 3);
    }
}
