package Projet_fx.Flappy;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
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
import Projet_fx.Flappy.ScoreBoard;

import com.dieselpoint.norm.Database;

public class PrimaryController {
	
	@FXML
	public ListView listview;
	@FXML
	public TextField text;
	@FXML
	public BorderPane root;
	
	public void initialize() {
		
		List<ScoreBoard> myList = App.db.orderBy("score desc").results(ScoreBoard.class);
        listview.setItems(FXCollections.observableArrayList(myList));
        
        
	}
	
	
	@FXML
    private void switchToSecondary() throws IOException {
    	ScoreBoard scoreboard = new ScoreBoard();
    	if(text.getText() != "") {
    		scoreboard.pseudo = text.getText();
    		App.db.insert(scoreboard);
    		listview.getItems().add(scoreboard);
    		App.setRoot("secondary");
    	}
        
    }
}
