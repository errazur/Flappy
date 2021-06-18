package Projet_fx.Flappy;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {
	
	private Image image;
	private double positionX;
	private double positionY;
	private double velocityX;
	private double velocityY;
	private double width;
    private double height;
    
    public Sprite() {
    	this.positionX=0;
    	this.positionY=0;
    	this.velocityX=0;
    	this.velocityY=0;
    }
    
    //changer la taille
    public void resizeImage(String filepath, int width, double height) {
        Image toReturn = new Image(filepath, width, height, false, false);
        setImage(toReturn);
    }
    
    //ajout 
    public void addVelocity(double velX, double velY) {
    	this.velocityX += velX;
    	this.velocityY += velY;
    }
    
    //setter
    public void setImage(Image image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }
    
    public void setPositionXY(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
    
    public void setVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
    
    //getter
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }
    
    public double getposX() {
    	return positionX;
    }
    
    public double getposY() {
    	return positionY;
    }
    
    public double getVelX() {
    	return velocityX;
    }
    
    public double getVelY() {
    	return velocityY;
    }
    
    public double getwidth() {
    	return width;
    }
    
    public double getheight() {
    	return height;
    }
    
	//paint
    public void draw(GraphicsContext gc) {
    	gc.drawImage(image, positionX, positionY);
    }
    
    //forcer la maj
    public void maj(double time) {
    	positionX += velocityX * time;
    	positionY += velocityY * time;
    }
    
}
