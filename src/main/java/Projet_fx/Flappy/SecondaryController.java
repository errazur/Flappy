package Projet_fx.Flappy;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.*;
import Projet_fx.Flappy.*;

public class SecondaryController {
	
	@FXML
	public Scene scene;
	@FXML
	public Pane root = new Pane();
	public static boolean GAME_SET;
	
		
	    private Parent createcontent() {
	    	
	    	Rectangle bg = new Rectangle(700,400);
			bg.setFill(Color.rgb(78, 192, 202));
	    	
	    Image i = new Image(getClass().getResource("images/title.png").toString());
	    ImageView title = new ImageView();
	    title.setImage(i);
		title.setFitWidth(178);
		title.setFitHeight(50);
		title.setLayoutX(200);
		title.setLayoutY(50);

		ImageView flappybird = new ImageView(getClass().getResource("images/bird2.png").toString());
		flappybird.setFitWidth(50);
		flappybird.setFitHeight(45);
		flappybird.setLayoutX(260);
		flappybird.setLayoutY(120);
		
		Text textstart = new Text ("appuie sur la barre espace ou\nutilise le clic gauche");
		textstart.setFont(Font.font("Courier", FontWeight.EXTRA_BOLD, 25));
        textstart.setStroke(Color.BLACK);
        textstart.setFill(Color.WHITE);
        textstart.setLayoutX(140);
        textstart.setLayoutY(230);
        
        root.getChildren().addAll(bg,title,flappybird,textstart);
        return root;
	    }
	    
	    public void initialize() {
	    	createcontent();
	    	//scene = new Scene(createcontent(), 700, 400);
	    	
	    	root.setOnKeyPressed(e ->{
	    		startgame();
	    	});
	    	root.setOnMouseClicked(e ->{
	    		startgame();
	    	});
	    	
			/*primaryStage.setTitle("FlappyBird");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();*/
	    }

	@FXML
	public void startgame() {
		if(!GAME_SET) {
			GAME_SET=true;
			FlappyBird game = new FlappyBird();
			Stage st = new Stage();
			try {
				game.start(st);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
	

}