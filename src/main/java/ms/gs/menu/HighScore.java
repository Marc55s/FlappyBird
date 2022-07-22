package ms.gs.menu;

import ms.gs.gamelogic.GameObject;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.*;

public class HighScore extends GameObject implements Serializable {

    private Font f = null;
    private double highscore = 0;
    private int highscoreAllTime = 0;
    File dir;


    public HighScore(String name, float speed, int x, int y, int width, int height) {
        super(name, speed, x, y, width, height);
        try {
            f = Font.createFont(Font.TRUETYPE_FONT,new File("src/main/resources/Font/04B_19__.TTF")).deriveFont(80f);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //setAllTimeHcFromFile();
    }

    File serilizable;

    private void setAllTimeHcFromFile(){
        // TODO: 15.07.2022 save in temp
        dir = new File("src\\main\\resources\\save");
        serilizable = new File("src\\main\\resources\\save\\highscore.ser");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(serilizable);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(dir.mkdir()){
            try {
                if(serilizable.createNewFile()){
                    System.out.println("file created");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getAllTimeHcFromFile(){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(serilizable);
            ObjectInputStream ois = new ObjectInputStream(fis);
            HighScore hs = (HighScore) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(long elapsedTime) {

    }

    @Override
    public void render(Graphics g) {
        String s = String.valueOf((int)highscore);
        g.setFont(f);
        g.drawString(s,getX(),getY());
    }

    public int getHighscoreAllTime() {
        return highscoreAllTime;
    }

    public void setHighscoreAllTime(int highscoreAllTime) {
        this.highscoreAllTime = highscoreAllTime;
    }

    public void setHighscore(double highscore) {
        this.highscore = highscore;
    }

}
