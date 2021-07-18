package Projet_fx.Flappy;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="scoreboard")
public class ScoreBoard {

	public String pseudo;
	public int score;
	
	public String toString() {
		return String.format("%s - %d", pseudo,score);
	}
	
}
