package Projet_fx.Flappy;

public class tuyaux {
	
	private Sprite tuyaux;
	private double localisationX;
	private double localisationY;
	private double width;
    private double height;
    
    public tuyaux(boolean isFaceup, double height) {
    	this.tuyaux = new Sprite();
    	this.tuyaux.resizeImage(isFaceup ? "images/up_pipe.png" : "images/down_pipe.png",70, height);
    	this.height = height;
    	this.width = 70;
    	this.localisationX = 400;
        this.localisationY = isFaceup? 600 - height : 0;
        this.tuyaux.setPositionXY(localisationX, localisationY);
    }
    
    //getter
    public Sprite gettuyaux() {
        return tuyaux;
    }

}
