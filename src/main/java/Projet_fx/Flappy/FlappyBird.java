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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.dieselpoint.norm.Query;
import com.sun.jdi.LongValue;

public class FlappyBird extends Application {
	
	private int APP_HEIGHT = 700;
    private int APP_WIDTH = 400;
    private int SCORE = 0;
    private boolean CLICKED, GAME_START, HIT_TUY, GAME_OVER;
    private Bird bird;
    private ArrayList<Tuyaux> tuyaux;
    private LongValue startNanoTime;
    private Sprite firstFloor, secondFloor, birdSprite;
    private Text scoreLabel;
    private GraphicsContext gc, birdGC;
    private AnimationTimer timer;
    private ImageView gameOver, startGame,scoreBest;
    private Group root;
    private double motionTime, elapsedTime;
    private long spaceClickA;
    private int scoreA = SCORE;
    private Text scoreA1;
    private String pseudo = PrimaryController.pseudo1;
    private Text ScoreB1;
    public int scoreB;
    

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Flappy Bird");
		primaryStage.setResizable(false);
		
		Parent root = getContent();
		Scene main = new Scene(root, APP_WIDTH,APP_HEIGHT);
		keyinput(main);
		primaryStage.setScene(main);
		primaryStage.show();
		
		startgame();
		
	}
	
	//game start
	private void startgame() {
		startNanoTime = new LongValue(System.nanoTime());
		scoreB = App.db.sql("select score from scoreboard where pseudo = ?", pseudo).first(int.class);
		timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				elapsedTime = (now - startNanoTime.value) /1000000000.0;
				startNanoTime.value = now;
				
				
				gc.clearRect(0, 0, APP_WIDTH, APP_HEIGHT);
				birdGC.clearRect(0, 0, APP_WIDTH, APP_HEIGHT);
				deplacerFloor();
				checktimebetween();
				
				if(GAME_START) {
					rendertuy();
					veriftuyscroll();
					majscore();
						if(BirdHitTuy()) {
							root.getChildren().add(gameOver);
							root.getChildren().add(scoreBest);
							stopescroll();
							motionTime +=0.18;
								if(motionTime > 0.5) {
									birdSprite.addVelocity(-200, 400);
									birdSprite.draw(gc);
									birdSprite.maj(elapsedTime);
									motionTime = 0;
								}
						}
						if(BirdHitFloor()) {
							if(!root.getChildren().contains(gameOver)) {
								root.getChildren().add(gameOver);
								root.getChildren().add(scoreBest);
								HitEffect();
							}
							timer.stop();
							GAME_OVER=true;
						}
						if(birdSprite.getposY() < -60) {
							if(!root.getChildren().contains(gameOver)) {
								root.getChildren().add(gameOver);
								root.getChildren().add(scoreBest);
								HitEffect();
							}
							timer.stop();
							GAME_OVER=true;
						}
						if(root.getChildren().contains(gameOver) && !root.getChildren().contains(scoreA1) && !root.getChildren().contains(ScoreB1)) {
							
							root.getChildren().remove(scoreLabel);
							 scoreA1 = new Text(""+SCORE);
						        scoreA1.setFont(Font.font("Courier", FontWeight.EXTRA_BOLD, 50));
						        scoreA1.setStroke(Color.BLACK);
						        scoreA1.setFill(Color.WHITE);
						        scoreA1.setLayoutX(168);
						        scoreA1.setLayoutY(300);
					        root.getChildren().add(scoreA1);
					        
					        ScoreB1 = new Text(""+scoreB);
					        ScoreB1.setFont(Font.font("Courier", FontWeight.EXTRA_BOLD, 50));
					        ScoreB1.setStroke(Color.BLACK);
					        ScoreB1.setFill(Color.WHITE);
					        ScoreB1.setLayoutX(160);
					        ScoreB1.setLayoutY(400);
					        root.getChildren().add(ScoreB1);
					        
					        	if(SCORE > scoreB) {
					        		
					        		App.db.sql("update scoreboard set score ='"+SCORE+"' where pseudo =?",pseudo).execute();
					        	
					        	}
						}
				}
			}
		};
		timer.start();
	}
	
	//input clavier ou souris
	private void keyinput(Scene scene) {
		scene.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.SPACE) {
				setOnUserInput();
			}
		});
		scene.setOnMouseClicked(e -> {
			setOnUserInput();
		});
	}
	
	//les input du joueur
	private void setOnUserInput() {
		if(!HIT_TUY) {
			CLICKED=true;
			if(!GAME_START) {
				root.getChildren().remove(startGame);
				GAME_START=true;
			}
			else{
				spaceClickA = System.currentTimeMillis();
				birdSprite.setVelocity(0, -200);
			}
		}
		if(GAME_OVER) {
			NouvGame();
		}
	}
	
	//getter le contenu
	private Parent getContent() {
		root = new Group();
		Canvas canvas = new Canvas(APP_WIDTH, APP_HEIGHT);
        Canvas birdCanvas = new Canvas(APP_WIDTH, APP_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        birdGC = birdCanvas.getGraphicsContext2D();

        ImageView bg = setBackground();
        setFloor();
        tuyaux = new ArrayList<>();
        settuy();
        setBird();
        setLabel();

        root.getChildren().addAll(bg, canvas, birdCanvas, scoreLabel, startGame);
        return root;
	}
	
	//image du fond
	private ImageView setBackground() {
		String filePath = "images/background.png";
		ImageView imageView = new ImageView(new Image(getClass().getResource(filePath).toString()));
		imageView.setFitWidth(APP_WIDTH);
        imageView.setFitHeight(APP_HEIGHT);
        return imageView;
	}
	
	//parametre du label
	private void setLabel() {
		scoreLabel = new Text("0");
        scoreLabel.setFont(Font.font("Courier", FontWeight.EXTRA_BOLD, 50));
        scoreLabel.setStroke(Color.BLACK);
        scoreLabel.setFill(Color.WHITE);
        scoreLabel.setLayoutX(175);
        scoreLabel.setLayoutY(70);
        
        gameOver = new ImageView(new Image(getClass().getResource("images/game_over.png").toString()));
        gameOver.setFitWidth(178);
        gameOver.setFitHeight(50);
        gameOver.setLayoutX(110);
        gameOver.setLayoutY(100);

        startGame = new ImageView(new Image(getClass().getResource("images/ready.png").toString()));
        startGame.setFitWidth(178);
        startGame.setFitHeight(50);
        startGame.setLayoutX(100);
        startGame.setLayoutY(100);
        
        scoreBest = new ImageView(new Image(getClass().getResource("images/score.png.").toString()));
        scoreBest.setFitWidth(150);
        scoreBest.setFitHeight(230);
        scoreBest.setLayoutX(110);
        scoreBest.setLayoutY(200);
        
	}
	
	//on implement le sol
	private void setFloor() {
		firstFloor = new Sprite();
		firstFloor.resizeImage(getClass().getResource("/images/floor.png").toString(), 400, 140);
		firstFloor.setPositionXY(0, APP_HEIGHT - 100);
		firstFloor.setVelocity(-.4, 0);
		firstFloor.draw(birdGC);
		
		secondFloor = new Sprite();
		secondFloor.resizeImage(getClass().getResource("/images/floor.png").toString(), 400, 140);
		secondFloor.setPositionXY(firstFloor.getwidth(), APP_HEIGHT - 100);
		secondFloor.setVelocity(-.4, 0);
		secondFloor.draw(gc);
	}
	
	//on implente le Bird
	private void setBird() {
		bird = new Bird();
		birdSprite = bird.getBird();
		birdSprite.draw(gc);
	}
	
	//start une nouvelle partie
	private void NouvGame() {
		root.getChildren().remove(GAME_OVER);
		root.getChildren().remove(gameOver);
		root.getChildren().remove(scoreBest);
		root.getChildren().remove(scoreA1);
		root.getChildren().remove(ScoreB1);
		root.getChildren().add(startGame);
		root.getChildren().add(scoreLabel);
		tuyaux.clear();
		setFloor();
		settuy();
		setBird();
		remizeAzero();
		startgame();
	}
	
	//remise a zero
	private void remizeAzero() {
		scorelabel(0);
		SCORE = 0;
		HIT_TUY = false;
		CLICKED = false;
		GAME_START = false;
		GAME_OVER = false;
	}
	
	//time diff entre les actions
	private void checktimebetween() {
		long difference = (System.currentTimeMillis() - spaceClickA) / 300;
		
		if (difference >= .001 && CLICKED) {
			CLICKED = false;
			birdSprite.addVelocity(0, 730);
			birdSprite.draw(birdGC);
			birdSprite.maj(elapsedTime);
		}
		else {
			AnimBird();
		}
	}
	
	//maj du totale score 
	private void majscore() {
		if(!HIT_TUY) {
			for(Tuyaux tuyau : tuyaux) {
				if (tuyau.gettuyaux().getposX() == birdSprite.getposX()) {
					scorelabel(++SCORE);
					break;
				}
			}
		}
	}
	
	//score label
	private void scorelabel(int score) {
		scoreLabel.setText(Integer.toString(score));
	}
	
	//deplacer le sol
	private void deplacerFloor() {
		firstFloor.draw(gc);
		secondFloor.draw(gc);
		firstFloor.maj(5);
		secondFloor.maj(5);
		
		if(firstFloor.getposX() <= -APP_WIDTH) {
			firstFloor.setPositionXY(secondFloor.getposX() + secondFloor.getwidth(), APP_HEIGHT - 100);
		}
		else if (secondFloor.getposX() <= -APP_WIDTH ) {
			secondFloor.setPositionXY(firstFloor.getposX() + firstFloor.getwidth(), APP_HEIGHT - 100);
		}
	}
	
	//animation du Bird
	private void AnimBird() {
		birdSprite.draw(birdGC);
		birdSprite.maj(elapsedTime);
		
		motionTime += 0.18;
		if(motionTime > 0.5 && CLICKED) {
			Sprite temp = birdSprite;
			birdSprite = bird.animate();
			birdSprite.setPositionXY(temp.getposX(), temp.getposY());
			birdSprite.setVelocity(temp.getVelX(), temp.getVelY());
			motionTime=0;
		}
	}
	
	//verifier si le Bird touche les tuyaux
	private boolean BirdHitTuy() {
		for (Tuyaux tuyaux : tuyaux) {
			if(!HIT_TUY && birdSprite.intersectsSprite(tuyaux.gettuyaux())) {
				HIT_TUY = true;
				HitEffect();
				return true;
			}
		}
		return false;
	}
	
	//animation de touche
	private void HitEffect() {
		ParallelTransition parallelTransition = new ParallelTransition();
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.10), root);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        parallelTransition.getChildren().add(fadeTransition);
        parallelTransition.play();
	}
	
	//verifier si le Bird touche le sol
	private boolean BirdHitFloor() {
		return birdSprite.intersectsSprite(firstFloor) ||
                birdSprite.intersectsSprite(secondFloor) ||
                birdSprite.getposX() < 0;
	}
	
	//arreter le defilement
	private void stopescroll() {
		for (Tuyaux tuyau : tuyaux) {
			tuyau.gettuyaux().setVelocity(0, 0);
		}
		firstFloor.setVelocity(0, 0);
		secondFloor.setVelocity(0, 0);
	}
	
	//verifier si ils scroll et les enlever
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
	
	//placer les tuyaux
	private void settuy() {
		int height = randomtuyheight();
		
		Tuyaux tuyau1 = new Tuyaux(true,height);
		Tuyaux tuyau2 = new Tuyaux(false, 425-height);
		
		tuyau1.gettuyaux().setVelocity(-.4, 0);
		tuyau2.gettuyaux().addVelocity(-.4, 0);
		
		tuyau1.gettuyaux().draw(gc);
		tuyau2.gettuyaux().draw(gc);
		
		tuyaux.addAll(Arrays.asList(tuyau1,tuyau2));
	}
	
	//placer à hauteur random
	private int randomtuyheight() {
		return (int) (Math.random() * (410 - 25) + 25);
	}
	
	//apparaitre les tuyaux
	private void rendertuy() {
		for (Tuyaux tuyau : tuyaux) {
			Sprite t = tuyau.gettuyaux();
			t.draw(gc);
			t.maj(5);
		}
	}
	
	//valeur d'observation
	public class LongValue{
		public long value;
		
		public LongValue(long i) {
			this.value=i;
		}
	}

}
