package Errant.GUI;

import Errant.Map.*;


// linear travel for a movementScript. duration is in millis, speed is in tiles per second (but kept internally in tiles per milli)
public class MovementStep implements GUIConstants
{
	private int remainingDuration;
	private double xSpeedMillis;
	private double ySpeedMillis;


	public int getRemainingDuration(){return remainingDuration;}
	public double getXSpeed(){return xSpeedMillis * 1000.0;}
	public double getYSpeed(){return ySpeedMillis * 1000.0;}


	public void setRemainingDuration(int r){remainingDuration = r;}
	public void setXSpeed(double x){xSpeedMillis = x / 1000.0;}
	public void setYSpeed(double y){ySpeedMillis = y / 1000.0;}

   public MovementStep(int durationInMillis, double xSpeed, double ySpeed)
   {
      remainingDuration = durationInMillis;
      setXSpeed(xSpeed);
      setYSpeed(ySpeed);
   }

   public void millisElapsed(int me, MapPosition target)
   {
      target.add(0, 0, xSpeedMillis * me, ySpeedMillis * me);
      remainingDuration -= me;
   }
   
   public boolean isExpired()
   {
      return remainingDuration <= 0;
   }
}