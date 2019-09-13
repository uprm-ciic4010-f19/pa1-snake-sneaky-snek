
package Worlds;

import Game.Entities.Dynamic.Player;
import Game.Entities.Dynamic.Tail;
import Game.Entities.Static.Apple;
import Game.GameStates.State;
import Main.Handler;

import java.awt.*;
import java.util.LinkedList;


/**
 * Created by AlexVR on 7/2/2018.
 */
public abstract class WorldBase {

    //How many pixels are from left to right
    //How many pixels are from top to bottom
    //Must be equal
    public int GridWidthHeightPixelCount;

    //automatically calculated, depends on previous input.
    //The size of each box, the size of each box will be GridPixelsize x GridPixelsize.
    public int GridPixelsize;

    public Player player;
    public int steps = 0;

    protected Handler handler;


    public Boolean appleOnBoard;
    private Apple apple;
    public boolean isRotten = false;
    
    public Boolean[][] appleLocation;


    public Boolean[][] playerLocation;

    public LinkedList<Tail> body = new LinkedList<>();


    public WorldBase(Handler handler){
        this.handler = handler;

        appleOnBoard = false;


    }
    public void isGood() {
    	if(isRotten == false && handler.getWorld().steps%1000 == 0 ) {
    		isRotten = true;
    		
    	}
    }
    public void tick(){
    	steps++;
    	System.out.println(steps);
    	isGood();
    	
    	
    	

    }

    public void render(Graphics g){
    	
    	Color backround = new Color(129, 107, 207);

        for (int i = 0; i <= 800; i = i + GridPixelsize) {

            g.setColor(backround);
            g.drawLine(0, i, handler.getWidth() , i);
            g.drawLine(i,0,i,handler.getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Times New Roman", 1, 50));
            g.drawString("Score: " + player.currScore, 10, 40);
            
            
        }
    }
	public Apple getApple() {
		return apple;
	}
	public void setApple(Apple apple) {
		this.apple = apple;
	}
   

}

