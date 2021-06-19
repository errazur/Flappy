package Projet_fx.Flappy;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.sun.jdi.LongValue;

public class FlappyBird extends Application {
	
	private int APP_HEIGHT = 700;
    private int APP_WIDTH = 400;
    private int SCORE = 0;
    private boolean CLICKED, GAME_START, HIT_PIPE, GAME_OVER;
    private Bird bird;
    private ArrayList<tuyaux> tuyaux;
    private LongValue startNanoTime;
    private Sprite firstFloor, secondFloor, birdSprite;
    private Text scoreLabel;
    private GraphicsContext gc, birdGC;
    private AnimationTimer timer;
    private ImageView gameOver, startGame;
    private Group root;
    private double motionTime, elapsedTime;
    
    

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Flappy Bird");
		primaryStage.setResizable(false);
		
		
		
		
	}
	
	private void BirdHitFloor() {
		
	}
	
	private void stopescroll() {
		for (tuyaux tuyau : tuyaux) {
			tuyau.gettuyaux().setVelocity(0, 0);
		}
		firstFloor.setVelocity(0, 0);
		secondFloor.setVelocity(0, 0);
	}
	
	private void veriftuyscroll() {
		if(tuyaux.size() > 0) {
			Sprite t = tuyaux.get(tuyaux.size() - 1).gettuyaux();
			if(t.getposX() == APP_WIDTH / 2 -80) {
				settuy();
			}
			else if (t.getposX() <= -t.getwidth()) {
				tuyaux.remove(0);
				tuyaux.remove(0);
			}
		}
		
	}
	
	private void settuy() {
		int height = randomtuyheight();
		
		tuyaux tuyau = new tuyaux(true,height);
		tuyaux tuyau2 = new tuyaux(false, 425-height);
		
		tuyau.gettuyaux().setVelocity(-.4, 0);
		tuyau2.gettuyaux().addVelocity(-.4, 0);
		
		tuyau.gettuyaux().draw(gc);
		tuyau2.gettuyaux().draw(gc);
		
		tuyaux.addAll(Arrays.asList(tuyau,tuyau2));
	}
	
	private int randomtuyheight() {
		return (int) (Math.random() * (410 - 25) + 25);
	}
	
	private void rendertuy() {
		for (tuyaux tuyau : tuyaux) {
			Sprite t = tuyau.gettuyaux();
			t.draw(gc);
			t.maj(5);
		}
	}
	
	public class LongValue{
		public long value;
		
		public LongValue(long i) {
			this.value=i;
		}
	}

}
