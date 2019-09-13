package Game.Entities.Dynamic;

import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

import Game.GameStates.PauseState;
import Game.GameStates.State;
import Game.GameStates.OverState;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {
	public State pauseState;
	public State overState;
	   
    public int lenght;
    public boolean justAte;
    private Handler handler;
    public double currScore;
    public int xCoord;
    public int yCoord;

    public double moveCounter;
    public int speed;

    public String direction;//is your first name one?

    public Player(Handler handler){
        this.handler = handler;
        xCoord = 0;
        yCoord = 0;
        moveCounter = 0;
        speed = 5;
        direction= "Right";
        justAte = false;
        lenght= 10;

    }

    public void tick(){
        pauseState = new PauseState(handler);
        overState = new OverState(handler);
     
        moveCounter++;
        if(moveCounter>=speed) {
            checkCollisionAndMove();
            moveCounter=0;  
            
        } 
        
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)) {
        	if(speed!=0) {
        		speed++;
        	}
        }
        
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)) { 
        		speed--;
    	}
        
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) { 
        	  lenght++;
              Tail tail= null;
              if( handler.getWorld().body.isEmpty()){
                  if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                      tail = new Tail(this.xCoord+1,this.yCoord,handler);
                  }else{
                      if(this.yCoord!=0){
                          tail = new Tail(this.xCoord,this.yCoord-1,handler);
                      }else{
                          tail =new Tail(this.xCoord,this.yCoord+1,handler);
                      }
                  }
              }else{
                  if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                      tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                  }else{
                      if(handler.getWorld().body.getLast().y!=0){
                          tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                      }else{
                          tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                      }
                  }
              }
              handler.getWorld().body.addLast(tail);
              handler.getWorld().playerLocation[tail.x][tail.y] = true;
              
    	}
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){ 
        	State.setState(pauseState);
        }
     
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP) && direction != "Down"){ 
            direction="Up";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN) && direction != "Up"){
            direction="Down";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT) && direction != "Right"){
            direction="Left";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)&& direction != "Left"){
            direction="Right";
        }
        
    }

    public void checkCollisionAndMove(){
        handler.getWorld().playerLocation[xCoord][yCoord]=false;
        int x = xCoord;
        int y = yCoord;
        switch (direction){
            case "Left":
                if(xCoord==0){
                	handler.getWorld().player.xCoord += 59;
                }else{
                    xCoord--;
                }
                break;
            case "Right":
                if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    handler.getWorld().player.xCoord -= 59;
                }else{
                    xCoord++;
                }
                break;
            case "Up":
                if(yCoord==0){
                   handler.getWorld().player.yCoord += 59;
                }else{
                    yCoord--;
                }
                break;
            case "Down":
                if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    handler.getWorld().player.yCoord -= 59;
                }else{
                    yCoord++;
                }
                break;
        }
        handler.getWorld().playerLocation[xCoord][yCoord]=true;
        
        if(handler.getWorld().appleLocation[xCoord][yCoord]){
			Eat();
		}

		if(!handler.getWorld().body.isEmpty()) {
			handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
			handler.getWorld().body.removeLast();
			handler.getWorld().body.addFirst(new Tail(x, y,handler));
		}

        for (int i = 0; i< handler.getWorld().body.size();i++) {
        	if(xCoord == handler.getWorld().body.get(i).x &&
        			yCoord == handler.getWorld().body.get(i).y) {
        		State.setState(overState);
        	}
        }
    }

	

	

	

	
	public void render(Graphics g,Boolean[][] playeLocation){
		Random r = new Random();
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
				g.setColor(Color.GREEN);

				if(playeLocation[i][j]){
					g.fillRect((i*handler.getWorld().GridPixelsize),
							(j*handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize,
							handler.getWorld().GridPixelsize);
				}
				if(handler.getWorld().appleLocation[i][j]) {
					if(handler.getWorld().isRotten == true) {
						g.setColor(Color.BLACK);
					}
					else {
						g.setColor(Color.RED);
					}
					
					g.fillRect((i*handler.getWorld().GridPixelsize),
							(j*handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize,
							handler.getWorld().GridPixelsize);
				}

			}
		}
	}

	public void Eat(){
		lenght++;
		Tail tail= null;
		handler.getWorld().appleLocation[xCoord][yCoord]=false;
		handler.getWorld().appleOnBoard=false;

		switch (direction){
		case "Left":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail = new Tail(this.xCoord+1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail = new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail =new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
					}else{
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

					}
				}

			}
			break;
		case "Right":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=0){
					tail=new Tail(this.xCoord-1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail=new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail=new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
					}
				}

			}
			break;
		case "Up":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(this.xCoord,this.yCoord+1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					}
				}
			}else{
				if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		case "Down":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=0){
					tail=(new Tail(this.xCoord,this.yCoord-1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					} System.out.println("Tu biscochito");
				}
			}else{
				if(handler.getWorld().body.getLast().y!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		case "addTail":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=0){
					tail=(new Tail(this.xCoord,this.yCoord-1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					} System.out.println("Tu biscochito");
				}
			}else{
				if(handler.getWorld().body.getLast().y!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}

		}
		handler.getWorld().body.addLast(tail);
		handler.getWorld().playerLocation[tail.x][tail.y] = true;

		if (handler.getWorld().appleLocation[xCoord][yCoord]==false && handler.getWorld().isRotten == false) {
			currScore += Math.sqrt((currScore*2)+1);
			currScore = Math.round(currScore*1000.0)/1000.0;
			speed -= 1; 
			handler.getWorld().body.addLast(tail);
			handler.getWorld().playerLocation[tail.x][tail.y] = true;
		} 
		
		else if (handler.getWorld().isRotten == true) {
			currScore -= Math.sqrt((currScore*2)+1);
			currScore = Math.round(currScore*1000.0)/1000.0;
			speed += 1;
			if(handler.getWorld().body.size() >0) {
				handler.getWorld().body.removeLast();
				kill();
			}
			else {
				State.setState(overState);
			}
			
		} 
	}
	public void kill(){
		lenght = 0;
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				handler.getWorld().playerLocation[i][j]=false;

			}
		}
	}

	public boolean isJustAte() {
		return justAte;
	}

	public void setJustAte(boolean justAte) {
		this.justAte = justAte;
	}
}
