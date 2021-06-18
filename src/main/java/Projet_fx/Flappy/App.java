package Projet_fx.Flappy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Projet_fx.Flappy.*;

import java.io.IOException;

import com.dieselpoint.norm.Database;



/**
 * JavaFX App
 */
public class App extends Application {
	
	public static Database db = new Database();
    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
    	db.setJdbcUrl("jdbc:mysql://localhost:3306/scoreboard?useSSL=false");
    	db.setUser("root");
    	db.setPassword("");
    	
        scene = new Scene(loadFXML("primary"), 700, 400);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}