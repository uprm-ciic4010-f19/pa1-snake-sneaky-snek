package Game.GameStates;

import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class OverState extends State {


	private int count = 0;
    private UIManager uiManager;

    public OverState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);



        uiManager.addObjects(new UIImageButton(56, (223+(64+16))+(64+16)+200, 250, 128, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));





    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
        }
        if(handler.getKeyManager().pbutt && count>=30){
            count=0;

            State.setState(handler.getGame().gameState);
        }


    }

    
    
    @Override
    public void render(Graphics g) {
        g.drawImage(Images.SnakeGameOver,0,0,800,800,null);

            g.setColor(Color.RED);
            g.setFont(new Font("Times New Roman", 1, 50));
            g.drawString("Score: " + handler.getWorld().player.currScore, 450, 600);

        uiManager.Render(g);


        
    }
}
