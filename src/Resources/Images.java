package Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class Images {


    public static BufferedImage[] butstart;
    public static BufferedImage title;
    public static BufferedImage Pause;
    public static BufferedImage GameOver;
    public static BufferedImage SnakeGameOver;
    public static BufferedImage PauseWall;
    public static BufferedImage[] Resume;
    public static BufferedImage[] BTitle;  
    public static ImageIcon icon;
    public static BufferedImage black1;
    public static BufferedImage PauseIt;
  

    public Images() {

        butstart = new BufferedImage[3];
        Resume = new BufferedImage[2];
        BTitle = new BufferedImage[2];
    
        try {

            title = ImageIO.read(getClass().getResourceAsStream("/Buttons/SnakeMenu.png"));
            Pause = ImageIO.read(getClass().getResourceAsStream("/Buttons/Pause.png"));
            SnakeGameOver = ImageIO.read(getClass().getResourceAsStream("/Buttons/SnakeGameOver.png"));
            GameOver = ImageIO.read(getClass().getResourceAsStream("/Buttons/GameOver.jpg"));
            PauseIt = ImageIO.read(getClass().getResourceAsStream("/Buttons/PauseIt.jpg"));
            black1 = ImageIO.read(getClass().getResourceAsStream("/Buttons/black1.jpg"));
            PauseWall = ImageIO.read(getClass().getResourceAsStream("/Buttons/PauseWall.jpg"));
            Resume[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Resume.png"));
            Resume[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/ResumeP.png"));
            BTitle[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitle.png"));
            BTitle[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitleP.png"));
            butstart[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/NormBut.png"));//normbut
            butstart[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/HoverBut.png"));//hoverbut
            butstart[2]= ImageIO.read(getClass().getResourceAsStream("/Buttons/ClickedBut.png"));//clickbut

            icon =  new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Sheets/icon.png")));


        }catch (IOException e) {
        e.printStackTrace();
    }


    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Images.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
