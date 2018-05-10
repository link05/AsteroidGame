package com.mycompany.a3;
import java.util.*;
import com.codename1.ui.*;

public class GameWorld extends Observable implements IGameWorld
{
	private GameObjectCollection gameobjs;
	boolean sound= true;
	private int score = 0;
	private int gameclock=0;
	private final int DEFAULT_LIFE = 3;
	private int life = DEFAULT_LIFE;
	private int WIDTH_MAX, HEIGHT_MAX,mv_start_x,mv_start_y; //mv_startx and y hold the origin of mapview
	private int move_time;
	private Sound fire_missle,gameover;
	private Sound collision_sound;
	private BGSound bg_music;
	private boolean pause_game =false;
	private boolean game_end =false;
	public GameWorld()
	{
		 gameobjs = new GameObjectCollection();
		 bg_music = new BGSound("background.wav");
		 bg_music.play();
		 fire_missle = new Sound("fire.wav");
		 collision_sound = new Sound("directhit.wav");
		 gameover = new Sound("gameover.wav");
		 SetSound(true);
		 //WIDTH_MAX = width;
		 //HEIGHT_MAX = height;
	}
	
	public boolean IsGameOver(){return game_end;}
	/*This method gets called when RefuelMissle button is pressed in pause mode*/
	public void Refuel()
	{
	 //get iterator and find if missle is selected and ifso refuel it
	 IIterator iter = GetObjects().getIterator();
	 Object temp;
	 while(iter.hasNext())
	 {
	   temp = iter.getNext();
	   if(temp instanceof Missle)
	   {
		 Missle temp_missle = (Missle) temp;
		 if(temp_missle.isSelected())
		 {
		   temp_missle.SetFuel();
		   System.out.println("Refueld missle");
		 }
	   }
	 }
	}
    //The purpose of this method is to set selected variable the selectable obj. as true
	public void SelectObj(ISelectable select_temp)
	{
      if(pause_game)
      {
    	select_temp.setSelected(true);
      }
	}
	public void SetGamePause(boolean status){ pause_game=status;}
	public boolean GetGamePause(){return pause_game;}
	public BGSound GetBgMusic(){return bg_music;}
	private void UpdateBkgroundMusic(BGSound bg)
	{
	  if(!GetSound()) bg.pause();
	  else bg.play();
	}
	
	/* 1).SetTime gets int value passed from Game and this variable helps 
	      determine how far an obj. should move each tick
	   2).GetMoveTime returns that time
	   3). GetObjects returns the gameobjs, which holds all the active game objs
	   4). setGameDimensions is used to save the width and the height of the mapview
	*/
	public void SetTime(int time){ move_time = time;}
	public int GetMoveTime(){return move_time;}
	public GameObjectCollection GetObjects(){return gameobjs;}
	public void SetGameObjCollection(GameObjectCollection new_collection){ gameobjs = new_collection;}
	public void setGameDimensions(int width, int height)
	{
	  WIDTH_MAX = width;
	  HEIGHT_MAX = height;
	}
	//this method saves the origin of the mapview
	public void SetMapOrigin(int x, int y)
	{
      mv_start_x = x;
      mv_start_y = y;
	}
	public void QuitGame(){
		Command cOk = new Command("Ok");
		Command cCancel = new Command("Cancel");
		Label instructions = new Label("");
		Command [] cmds = new Command[] {cOk,cCancel};
		Command c = Dialog.show("Are you sure you want to Quit?",instructions,cmds);
		if(c == cOk)  System.exit(0);
	}
	
	public void About(){
		Command cDone = new Command("Done");
		Label info = new Label("By Amit Dahal, csc 133, Sacramento State");
		Command c = Dialog.show("About:",info,cDone);
	}
	public void UpdateSound(){
		if(GetSound() == false) SetSound(true);
		else SetSound(false);
		UpdateBkgroundMusic(bg_music);
		 this.setChanged();
		 this.notifyObservers(new GameWorldProxy(this));
	}
	public void AddAsteroid(){
		  gameobjs.add(new Asteroid(WIDTH_MAX,HEIGHT_MAX));
		  this.setChanged();
		  this.notifyObservers(new GameWorldProxy(this));
	  }
	
	public void AddFlyingSaucer(){
		  gameobjs.add(new FlyingSaucer(WIDTH_MAX,HEIGHT_MAX));
		  this.setChanged();
		  this.notifyObservers(new GameWorldProxy(this));
	  }
	
	public void AddSpacestation(){
		  gameobjs.add(new SpaceStation(WIDTH_MAX,HEIGHT_MAX));
		  this.setChanged();
		  this.notifyObservers(new GameWorldProxy(this));
	  }
	
	public void MoveShipLeft(){
		  Ship temp= FindShip();
		  if(temp!= null) 
		  {
		   temp.GoLeft();
		   this.setChanged();
		   this.notifyObservers(new GameWorldProxy(this));
		  }
		  else System.out.println("Error no ship can be found therefore cannot move to left");
	  }
	
	public void MoveShipRight(){
		  Ship temp = FindShip();
		  if(temp != null)
		  {
		   temp.GoRight();
		   this.setChanged();
		   this.notifyObservers(new GameWorldProxy(this));
		  }
		  else System.out.println("Error no ship can be found therefore cannot move to right");
	  }
	
	public void Fire_Missile(){
		Ship temp = FindShip(); //find the ship
		if(temp == null) System.out.println("Error cannot find ship therefore cannot fire missle");
		else
		{
		   if(temp.GetMissleCount()>0)
		   {
			 gameobjs.add(new Missle(temp.GetX(),temp.GetY(),temp.GetDirection()));
			 temp.DecreaseMissleCount();
			 if(GetSound()) fire_missle.play();
			 this.setChanged();
			 this.notifyObservers(new GameWorldProxy(this));
		   }
		   else System.out.println("Error. Out of Missles");
			
		}
	  }
	
	public void MoveShipToDefault(){
		  Ship temp = FindShip();
		  if(temp == null) System.out.println("Error cannot find ship therefore cannot move to default");
		  else 
		  {
		    temp.Default_Location();
		    this.setChanged();
			this.notifyObservers(new GameWorldProxy(this));
		  }
	  }
	  public void ReloadMissle(){
		  Ship temp = FindShip();
		  if(temp == null) System.out.println("Error cannot find ship. Therefore cannot reload missles.");
		  else
		  {
		    temp.UpdateMissleCount();
		    this.setChanged();
			this.notifyObservers(new GameWorldProxy(this));
		   }
	  }
	  
	public void AccelerateShip()
	  {
		Ship temp = FindShip();
		if(temp!= null)
		{
		  temp.IncreaseSpeed();
		  this.setChanged();
		  this.notifyObservers(new GameWorldProxy(this));
		}
		else System.out.println("Error no ship can be found therefore cannot modify speed");
	  }
	
	public void DecelerateShip()
	  {
		Ship temp = FindShip();
		if(temp!= null)
		{
		  temp.DecreaseSpeed();
		  this.setChanged();
		  this.notifyObservers(new GameWorldProxy(this));
		}
		else System.out.println("Error no ship can be found therefore cannot modify speed");
	  }
	
	public void AddSpaceship(){
		  Ship temp =FindShip();
		  //only create ship if it DNE as only one ship can exist at a time...
		  if(GetLife()<=0)
		  {
			System.out.println("out of life");
		  }
		  else if(temp == null)
		  {
		   gameobjs.add(new Ship(WIDTH_MAX,HEIGHT_MAX));
		   this.setChanged();
		   this.notifyObservers(new GameWorldProxy(this));
		  }
		  else System.out.println("There is already one ship therefore cannot create another.");
	  }
	
	public Ship FindShip()
	  {
		IIterator collection = gameobjs.getIterator();
		Ship temp_ship=null;
		Object temp_obj = null;
		while(collection.hasNext())
		{
		  temp_obj = collection.getNext();
		  if(temp_obj instanceof Ship)
		  {
		    temp_ship = (Ship) temp_obj;
			break;
		  }
		}
		return temp_ship;
	  }
	/*
	public Asteroid FindAsteroid(Asteroid a)
	  {
		IIterator collection = gameobjs.getIterator();
		Asteroid temp_aster=null;
		Object temp_obj = null;
		while(collection.hasNext())
		{
		  temp_obj = collection.getNext();
		  if(temp_obj instanceof Asteroid && temp_obj == a)
		  {
		    temp_aster = (Asteroid) temp_obj;
			break;
		  }
		}
		return temp_aster;
	   }
	*/
	
	/*
	 * DestroyedAsteroid method: This method takes 2 parameters (asteroid and missle) and removes them from gameobj.
	 *  Called when collision between missle and asteroid occurs
	 */
	public void DestroyedAsteroid(Asteroid a, Missle m){
		 boolean missle_stat= false;
		 boolean aster_stat= false;
		 missle_stat = RemoveMissle(m);  //becomes true if missle was sucessfuly removed
		 Asteroid temp_aster = a;
		 if(temp_aster != null && temp_aster.GetSize() <= 18)
		 {
		   aster_stat = RemoveAsteroid(temp_aster); //becomes true if aster was sucessfully reomoved
		   if(aster_stat) System.out.println("asteroid sucessfuly removed!!");
		   else System.out.println("Error removing asteroid");
		   score = score + 150;
		   this.setChanged();
		   this.notifyObservers(new GameWorldProxy(this));
		}
		 else if (temp_aster.GetSize() >= 19 && temp_aster.GetSize() <= 24)
		 {
		   aster_stat = RemoveAsteroid(temp_aster);
		   if(aster_stat) System.out.println("asteroid sucessfuly removed!!");
		   int tot_size = temp_aster.GetSize();
		   int size1, size2;
		   size1 = tot_size - 10;
		   size2 = tot_size -size1;
		   Asteroid aster1 = new Asteroid(WIDTH_MAX,HEIGHT_MAX);
		   Asteroid aster2 = new Asteroid(WIDTH_MAX,HEIGHT_MAX);
		   aster1.SetSize(size1);
		   aster2.SetSize(size2);
		   gameobjs.add(aster1);
		   gameobjs.add(aster2);
		   score = 125 + score;
		   this.setChanged();
		   this.notifyObservers(new GameWorldProxy(this));
		 }
		 
		 else if (temp_aster.GetSize() >= 25 && temp_aster.GetSize() <= 30)
		 {
		   aster_stat = RemoveAsteroid(temp_aster);
		   if(aster_stat) System.out.println("asteroid sucessfuly removed!!");
		   int tot_size = temp_aster.GetSize();
		   int size1, size2,size3;
		   size1 = tot_size - 20;
		   size2 = tot_size - 10;
		   size3 = tot_size - size2 -size1;
		   Asteroid aster1 = new Asteroid(WIDTH_MAX,HEIGHT_MAX);
		   Asteroid aster2 = new Asteroid(WIDTH_MAX,HEIGHT_MAX);
		   Asteroid aster3 = new Asteroid(WIDTH_MAX,HEIGHT_MAX);
		   aster1.SetSize(size1);
		   aster2.SetSize(size2);
		   aster3.SetSize(size3);
		   gameobjs.add(aster1);
		   gameobjs.add(aster2);
		   gameobjs.add(aster3);
		   score = 115 + score;
		   this.setChanged();
		   this.notifyObservers(new GameWorldProxy(this));
		 }
		 if(missle_stat) System.out.println("Missle was removed");
		 else System.out.println("Missle could not be removed because Missle could not be deteced");
		 
		 if(aster_stat) System.out.println("Asteroid was removed");
		 else System.out.println("Asteroid could not be removed because it could not be fund");
		 if(GetSound()) collision_sound.play();
	  }
	
	 //When 2 asteroids get collided, remove them
	public void AsteroidCollided(Asteroid a1, Asteroid a2)
	{
	 boolean stat1 =false;
	 boolean stat2 = false;
	 stat1 = RemoveAsteroid(a1);
	 stat2 = RemoveAsteroid(a2);
	 if(stat1 && stat2) System.out.println("Removed both Asteroids");
	}
	//when flyingsaucer and missle collide, remove them
	public void DestroyedSaucer(FlyingSaucer s, Missle m)
	{
	  boolean missle_stat= false;
	  boolean saucer_stat = false;
	  missle_stat = RemoveMissle(m);
	  saucer_stat = RemoveSaucer(s);
	  if(GetSound()) collision_sound.play();
	  score=score+150;
	  this.setChanged();
	  this.notifyObservers(new GameWorldProxy(this));
	  
	  if(missle_stat) System.out.println("Missle was removed");
	  else System.out.println("Missle could not be removed because Missle could not be deteced");
	  
	  if(saucer_stat) System.out.println("Saucer was removed");
	  else System.out.println("Saucer could not be removed");
	}
	//when ship gets crashed into asteroid remove  the asteroid and ship
	public void ShipCrashedAsteroid(Asteroid a)
	  {
		 boolean stat1,stat2;
		 stat1=stat2 = false;
		 
		  System.out.println("Ship crashed into Asteroid");
		  stat1 = RemoveShip();
		  stat2 = RemoveAsteroid(a);
		  if(stat1) System.out.println("Ship was removed");
		  if(stat2) System.out.println("Asteroid was removed");
		  UseLife();
		  this.setChanged();
		  this.notifyObservers(new GameWorldProxy(this));
	  }
	//when ship crashes into saucer, then remove them both
	 public void ShipCrashedSaucer(FlyingSaucer s)
	  {
		  System.out.println("Ship crashed into Saucer");
		  boolean stat1 = false;
		  boolean stat2 = false;
		  stat1 = RemoveShip();
		  stat2 = RemoveSaucer(s);
		  if(stat1) System.out.println("Removed ship");
		  if(stat2) System.out.println("Removed Saucer");
		  UseLife();
		  this.setChanged();
		  this.notifyObservers(new GameWorldProxy(this));
	  }
	 //Asteroid to saucer collision
	 public void AsteroidSaucer(FlyingSaucer s, Asteroid a)
	 {
	  boolean stat1 = RemoveAsteroid(a);
	  boolean stat2= RemoveSaucer(s);
	  if(stat1) System.out.println("Remove Asteroid");
	  if(stat2) System.out.println("Remove Saucer");
	  this.setChanged();
	  this.notifyObservers(new GameWorldProxy(this));
	  }
	  
	 /* Tick: method that gets called many times by the run method at game level.
	  *  This method calls UpdateMove() which updates the location of every game obj.,
	  *  It also calls UpdateCollision() which checks if collsion occured when gameobjs were moved due to updatemove()
	  *  It also updates the blinkingstation, fuel level of missles, and lastly, increments gameclock
	  *  Lastly, it calls mapview, which tells the computer to repaint to account for the moved objs and collsion
	  */
	 public void Tick()
	  {
		  //after tick...update location call move methods
	   UpdateMove();
	   UpdateCollision();
		  //if a second has passed
		if((gameclock %100) ==0)
		{
		 UpdateFuelLevel();
		 UpdateBlinking();  
		}
		if(GetLife()>=0)gameclock++;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	  }
  
  /*UpdateCollision: checks whether an collision has occured between an of the gameobjs.
   * If collision does occur then it calls HandleCOllision method to deal with it*/
  private void UpdateCollision()
  {
	IIterator iter = GetObjects().getIterator();
	while(iter.hasNext())
	{
	 ICollider cur_obj = (ICollider)iter.getNext();
	 IIterator iter2 = GetObjects().getIterator();
	 while(iter2.hasNext())
	 {
	  //System.out.println("before get iterator has ");
	  ICollider col_obj = (ICollider) iter2.getNext();
	  //System.out.println("after get iterator has ");
	  //iter2.Display();
	  if(col_obj != cur_obj)
	   {
	    if(cur_obj.CollidesWith(col_obj)) HandleCollision(cur_obj,col_obj);
	   }
      }
	 }//end of outer loop
  }
  
  /*
   * HandleCollision: This method calls proper methods to deal with
   * modifying objects when 2 objs are close to each other.  
   */
  private void HandleCollision(ICollider cur_obj, ICollider col_obj)
  {
	 //if anyone of the 2 obj is ship do this....
	if(cur_obj instanceof Ship || col_obj instanceof Ship)
	{
     HandleShipCollision(cur_obj,col_obj);
	}
	//if any of the 2 objs is missle call the method HandleMissleCollision
	else if(cur_obj instanceof Missle || col_obj instanceof Missle)
	{
	 HandleMissleCollision(cur_obj,col_obj);
	}
	//collsion between asteroid and saucers
	else if(cur_obj instanceof Asteroid && col_obj instanceof FlyingSaucer)
	{
	   AsteroidSaucer((FlyingSaucer)col_obj,(Asteroid)cur_obj);
	}
	else if(cur_obj instanceof FlyingSaucer && col_obj instanceof Asteroid)
	{
	   AsteroidSaucer((FlyingSaucer)cur_obj,(Asteroid) col_obj);
	}
	else if(cur_obj instanceof Asteroid && col_obj instanceof Asteroid)
	{
		AsteroidCollided((Asteroid)cur_obj,(Asteroid)col_obj); 
	}
	IIterator iter = GetObjects().getIterator();
	GameObjectCollection new_collection = new GameObjectCollection();
	GameObject g_obj1 = (GameObject) cur_obj;
	GameObject g_obj2 = (GameObject) col_obj;
	while(iter.hasNext())
	{
	  GameObject temp = (GameObject)iter.getNext();
	  //if the object is not equal to its self or object that it is colliding with then add to new iterator
	  
	  if(temp != g_obj1 && temp != g_obj2)
	  {
	    new_collection.add(temp);
	  }
	}
    
  }
  
  /*
   * This method gets invoked ONLY when a ship is involved in collision.
   * there are 2 variables which can hold ship obj(either cur_obj or col_obj)s
   *  but only one of them holds the ship object. so this method works on 
   *  both cases where cur_obj holds ship and col_obj holds ship.
   *  After finding which variable holds ship, this method finds out what 
   *  type of obj. is the other and calls approprate method to deal with it.
   */
  private void HandleShipCollision(ICollider cur_obj, ICollider col_obj)
  {
	Ship temp;
	boolean cur_obj_ship = false;  
	//have temp point to ship
	
	//find out which variable holds ship obj
	if(cur_obj instanceof Ship)
	{
	  temp = (Ship) cur_obj;
	  cur_obj_ship = true;
	}
	else temp = (Ship) col_obj;
	
	//if true, it means current obj is ship so check what type is the other obj
	if(cur_obj_ship)
	{
	  if(col_obj instanceof FlyingSaucer) ShipCrashedSaucer((FlyingSaucer)col_obj);
	  if(col_obj instanceof Asteroid) ShipCrashedAsteroid((Asteroid)col_obj);
	  if(col_obj instanceof SpaceStation)
	  {
	    SpaceStation temp_station = (SpaceStation) col_obj;
	    if(temp_station.GetBlinkStatus()) ReloadMissle();
	  }
	 }//end of if(cur_obj_ship)
	else
	{
	 if(col_obj instanceof FlyingSaucer) ShipCrashedSaucer((FlyingSaucer)col_obj);
	 if(col_obj instanceof Asteroid) ShipCrashedAsteroid((Asteroid)col_obj);
     if(col_obj instanceof SpaceStation)
	 {
	  SpaceStation temp_station = (SpaceStation) col_obj;
	  if(temp_station.GetBlinkStatus()) ReloadMissle();
     }
	}//end of else part
  }//end of method
  
  /*
   * HandleMissleCollision gets called ONLY when a missle is involved in an 
   * collision. This method figures out which of 2 variables(cur_obj or col_obj)
   *  holds object of type missle and proceeds to find out what the other object is.
   *  Based on the type of other_obj, it calls appropriate method to deal with it.
   *  
   *  The sound that should be played when certain obj gets destoryed will be
   *  called in their "destoryedX..." method.
   */
  private void HandleMissleCollision (ICollider cur_obj, ICollider col_obj)
  {
	boolean missle_cur= false;
	Missle temp_missle;
	//find out which obj holds missle obj
	if(cur_obj instanceof Missle)
	{
	  temp_missle = (Missle) cur_obj;
	  missle_cur = true;
	}
	else temp_missle = (Missle)col_obj;
	  
	  //if cur_obj holds missle then find out what the other obj is
	if(missle_cur)
	{
	  if(col_obj instanceof Asteroid) DestroyedAsteroid((Asteroid)col_obj,temp_missle);
	  if(col_obj instanceof FlyingSaucer) DestroyedSaucer((FlyingSaucer)col_obj,temp_missle);
	  //if(!(col_obj instanceof Missle) && GetSound()) fire_missle.play();
	}//end of if(misle_cur)
	else //meaning col_obj holds missle
	{
	 if(cur_obj instanceof Asteroid) DestroyedAsteroid((Asteroid)cur_obj,temp_missle);
	 if(cur_obj instanceof FlyingSaucer) DestroyedSaucer((FlyingSaucer)cur_obj,temp_missle);
	}
  }
  
  /* UpdateMove: This method is in charge of calling the move() of each and every 
   * moveable gameobject. This method also ensure that if object moves beyond the
   * mapview then it wraps the object. In other words, if obj moves beyond from right side then it 
   * re-appears in the left side.
   */
  private void UpdateMove()
	 {
		 //System.out.println("in UpdateMove()");
		IIterator collection = gameobjs.getIterator();
		while(collection.hasNext())
		{
		  Object temp_obj = collection.getNext();
		  if(temp_obj instanceof Ship)
		  {
			Ship temp= (Ship) temp_obj;
			temp.move(move_time);
			if(temp.GetX() < 0) temp.UpdateX(WIDTH_MAX);
			if(temp.GetX() > WIDTH_MAX) temp.UpdateX(0);
			if(temp.GetY() < 0) temp.UpdateY(HEIGHT_MAX);
			if(temp.GetY() > HEIGHT_MAX) temp.UpdateY(0);
		  }
		  else if(temp_obj instanceof Missle)
		  {
			Missle temp = (Missle) temp_obj;
			temp.move(move_time);
			if(temp.GetX() < 0) temp.UpdateX(WIDTH_MAX);
			if(temp.GetX() > WIDTH_MAX) temp.UpdateX(0);
			if(temp.GetY() < 0) temp.UpdateY(HEIGHT_MAX);
			if(temp.GetY() > HEIGHT_MAX) temp.UpdateY(0);
		  }
		  else if(temp_obj instanceof Asteroid)
		  {
			Asteroid temp = (Asteroid) temp_obj;
			temp.move(move_time);
			if(temp.GetX() < 0) temp.UpdateX(WIDTH_MAX);
			if(temp.GetX() > WIDTH_MAX) temp.UpdateX(0);
			if(temp.GetY() < 0) temp.UpdateY(HEIGHT_MAX);
			if(temp.GetY() > HEIGHT_MAX) temp.UpdateY(0);
		  }
		  else if(temp_obj instanceof FlyingSaucer)
		  {
			FlyingSaucer temp = (FlyingSaucer) temp_obj;
			temp.move(move_time);
			if(temp.GetX() < 0) temp.UpdateX(WIDTH_MAX);
			if(temp.GetX() > WIDTH_MAX) temp.UpdateX(0);
			if(temp.GetY() < 0) temp.UpdateY(HEIGHT_MAX);
			if(temp.GetY() > HEIGHT_MAX) temp.UpdateY(0);
		  }
		  else if(temp_obj instanceof Missle)
		  {
			Missle temp = (Missle) temp_obj;
			temp.move(move_time);
		  }
		  
	    }//end of for-loop
	  }//end of UpdateMove method
  
  /*
   * UpdateFuelLevel: This method removes all missle objs. that are out of fuel.
   * First, add all the missle objs. that are out of fuel to a stack. Then remove 
   * them from the gameobj collection
   */
  private void UpdateFuelLevel()
  {
	 Stack<Missle> expired_missle =new Stack<Missle>(); //hold the missles that needs to be removed
	 IIterator collection = gameobjs.getIterator();
	  while(collection.hasNext())
	  {
		 Object temp_obj = collection.getNext();
		 if(temp_obj instanceof Missle)
		 {
		  Missle temp= (Missle)temp_obj;
	      temp.DecrementFuelLevel(); 
		  System.out.println("fuel level to:"+temp.GetFuelLevel());
		  if(temp.GetFuelLevel()==0) expired_missle.push(temp);
	     }
      }
	 while(!expired_missle.isEmpty())
	  {
		  System.out.println("Removing expired Missle");
		  Missle target = expired_missle.pop();
		  collection.Delete(target);
	  }
  }
  
  /*Method that takes care of either to blink the spacestation or not to*/
  private void UpdateBlinking()
  {
   IIterator collection = gameobjs.getIterator();
   while(collection.hasNext())
	{
	   Object temp_obj = collection.getNext();
	  if(temp_obj instanceof SpaceStation)
	  {
	    SpaceStation temp = (SpaceStation) temp_obj;
	    if(temp.GetBlinkRate() == 0)
	    {
		   temp.SetBlinkStatus(true);
		   System.out.println("Station is always blinking because rate is 0");
	    }
	    else if(gameclock % temp.GetBlinkRate()==0)
	    {
	 	  if(temp.GetBlinkStatus()==true) 
	      {
		    temp.SetBlinkStatus(false);
		    System.out.println("blinker is turned off");
		  }
		  else 
		  {
		    temp.SetBlinkStatus(true);
		    System.out.println("blinker is on");
		  }
	    }//end of else if
	  }//end of if
     }//end of while loop
  }//end of method
    
   /*
    * UseLife: gets called everytime spaceship gets destroyed
    *  and needs to use life.
    */
	private void UseLife()
	{
		if(life==0)
		{
	      System.out.println("You are out of life. Game Over");
	      if(GetSound()) gameover.play();
	      life--;
	      game_end=true;
		}
		else
		{
		  life=life-1;
		  gameobjs.add(new Ship(WIDTH_MAX,HEIGHT_MAX));
		}
	 }
	
	/*RemoveMissle(missle m): this method is incharge of removing missle m.
	 * returns status, if it suessfully removed missle. TRUE if sucessfuly removed
	 * FALSE otherwise.*/
	private boolean RemoveMissle(Missle m)
	{
	  boolean removed= false;
	  IIterator collection = gameobjs.getIterator();
	  Object temp_obj = null;
	  while(collection.hasNext() & !removed)
		{
		  temp_obj = collection.getNext();
		  if(temp_obj instanceof Missle && temp_obj == m)
		  {
		    collection.Delete(temp_obj);
			removed= true;
		  }
		}
	  return removed;
	}
	
	/*
	 * RemoveAsteroid(Asteroid a): This method remove
	 * asteroid a from gameobjcollection and returns status if 
	 * it sucessfuly removed object.(True if sucess, false otherwise).
	 */
	private boolean RemoveAsteroid(Asteroid a)
	{
	  boolean removed= false;
	  IIterator collection = gameobjs.getIterator();
	  Object temp_obj = null;
	  while(collection.hasNext() & !removed)
		{
		  temp_obj = collection.getNext();
		  if(temp_obj instanceof Asteroid && a == temp_obj)
		  {
		    collection.Delete(temp_obj);
			removed= true;
		  }
		}
	  return removed;
	}

	 /*RemoveSaucer(FlyingSaucer s): This method removes obj  s and returns status*/
	private boolean RemoveSaucer(FlyingSaucer s)
	{
	  boolean removed= false;
	  IIterator collection = gameobjs.getIterator();
	  Object temp_obj = null;
	  while(collection.hasNext() & !removed)
		{
		  temp_obj = collection.getNext();
		  if(temp_obj instanceof FlyingSaucer && temp_obj==s )
		  {
		    collection.Delete(temp_obj);
			removed= true;
		  }
		}
	  return removed;
	}
	//removes ship and returns true if it suceeds
	private boolean RemoveShip()
	{
	  boolean removed= false;
	  IIterator collection = gameobjs.getIterator();
	  Object temp_obj = null;
	  while(collection.hasNext() & !removed)
		{
		  temp_obj = collection.getNext();
		  if(temp_obj instanceof Ship)
		  {
		    collection.Delete(temp_obj);
			removed= true;
		  }
		}
	  return removed;
	}
	public void SetSound(boolean s){ sound= s;}
	public boolean GetSound(){return sound;}
	public int GetLife(){ return life;}
	public int GetScore(){ return score;}
	public int GetTime(){return gameclock;}
	
	public int GetMisslesNum(){
		Ship temp = FindShip();
		if(temp != null)
		{
		  return temp.GetMissleCount();
		}
		else return -1;
	}
	
	/*
	 * ObjectLocations(): is in charge of printing every object's location and 
	 * their specfic info.
	 */
	public String ObjectLocations()
	  {
		IIterator collection = gameobjs.getIterator();
		Object temp_obj;
		String output="";
		while(collection.hasNext())
		{
		   temp_obj = collection.getNext();
		  if(temp_obj instanceof Ship)
		  {
			Ship temp_ship= (Ship) temp_obj;
			output= output+ temp_ship.toString();
		  }
		  else if(temp_obj instanceof Asteroid)
		  {
			Asteroid temp_asteroid = (Asteroid) temp_obj;
			output= output+temp_asteroid.toString();
		  }
		  else if(temp_obj instanceof FlyingSaucer)
		  {
			FlyingSaucer temp_saucer = (FlyingSaucer) temp_obj;
			output = output+temp_saucer.toString();
		  }
		  else if(temp_obj instanceof Missle)
		  {
			Missle temp_missle = (Missle) temp_obj;
			output= output+temp_missle.toString();
		  }
		  else if(temp_obj instanceof SpaceStation)
		  {
			SpaceStation temp_station = (SpaceStation) temp_obj;
			output = output + temp_station.toString();
		  }
	    }//end of while-loop
		return output;
	  }
}
