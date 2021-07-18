package Projet_fx.Flappy;

import java.util.ArrayList;
import java.util.Arrays;

public class Bird {

	private Sprite bird;
    private int currentBird = 0;
    private double locationX = 70;
    private double locationY = 200;
    private int BIRD_WIDTH = 50;
    private int BIRD_HEIGHT = 45;
    private ArrayList<Sprite> vol = new ArrayList<>();
    
    public Bird() {
    	bird = new Sprite();
    	bird.resizeImage(getClass().getResource("images/bird1.png").toString(), BIRD_WIDTH, BIRD_HEIGHT);
    	bird.setPositionXY(locationX, locationY);
    	volanimation();
    }
    
    
    //animation de vol en changeant les images
    public void volanimation() {
    	Sprite bird2 = new Sprite();
    	bird2.resizeImage(getClass().getResource("images/bird3.png").toString(), BIRD_WIDTH, BIRD_HEIGHT);
    	bird2.setPositionXY(locationX, locationY);
    	
    	Sprite bird3 = new Sprite();
    	bird3.resizeImage(getClass().getResource("images/bird1.png").toString(), BIRD_WIDTH, BIRD_HEIGHT);
    	bird3.setPositionXY(locationX, locationY);
    	
    	Sprite bird4 = new Sprite();
    	bird4.resizeImage(getClass().getResource("images/bird2.png").toString(), BIRD_WIDTH, BIRD_HEIGHT);
    	bird4.setPositionXY(locationX, locationY);
    	
    	vol.addAll(Arrays.asList(bird,bird2,bird3,bird4));
    }
    
    //getter
    public Sprite getBird() {
        return bird;
    }
    
    //animation de vol pour avoir que 1 bird
    public Sprite animate() {
        if (currentBird == vol.size() - 1) {
            currentBird = 0;
        }

        return vol.get(currentBird++);
    }
}
