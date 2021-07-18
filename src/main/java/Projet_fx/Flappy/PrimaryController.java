package Projet_fx.Flappy;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.*;
import Projet_fx.Flappy.*;

import com.dieselpoint.norm.Database;

public class PrimaryController {
	
	@FXML
	public ListView<ScoreBoard> listview;
	@FXML
	public TextField text;
	@FXML
	public BorderPane root;
	public static String pseudo1;
	public List<ScoreBoard> pseudo2;
	
	public void initialize() {
		
		List<ScoreBoard> myList = App.db.orderBy("score desc").results(ScoreBoard.class);
        listview.setItems(FXCollections.observableArrayList(myList));
        listview.refresh();
        pseudo2 = App.db.sql("select pseudo from scoreboard").results(ScoreBoard.class);
        
	}
	
	
	@FXML
    private void switchToSecondary() throws IOException {
    	ScoreBoard scoreboard = new ScoreBoard();
    	pseudo1 = text.getText();
    	
        
    		if(text.getText().length() > 3 && pseudo2.toString().contains(pseudo1)) {
    			
    			Alert alert = new Alert(AlertType.CONFIRMATION);
    			alert.setTitle("Confirmation Dialog");
    			alert.setHeaderText("Ce pseudo est déjà utiliser, est tu : "+pseudo1);
    			alert.setContentText("est-tu sûr de ton choix ?");

    			Optional<ButtonType> result = alert.showAndWait();
    			if (result.get() == ButtonType.OK){
    				App.setRoot("secondary");
    			} else {
    			    text.setText("");
    			}

    		}	else if(text.getText().length() > 3 && !pseudo2.toString().contains(pseudo1)) {
    		scoreboard.pseudo = text.getText();
    		App.db.insert(scoreboard);
    		listview.getItems().add(scoreboard);
    		App.setRoot("secondary");
    		}
    		
    	else {
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("error");
    			alert.setHeaderText("Results:");
    			alert.setContentText("Il faut au moins 3 lettres pour ton pseudo !");

    			alert.showAndWait();
    	}
    	
	} 
    
}
