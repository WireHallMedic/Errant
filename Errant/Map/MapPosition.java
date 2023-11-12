package Errant.Map;

// for stuf that moves around
public class MapPosition
{
	private int xLoc;
	private int yLoc;
	private double xOffset;
	private double yOffset;


	public synchronized int getXLoc(){return xLoc;}
	public synchronized int getYLoc(){return yLoc;}
	public synchronized double getXOffset(){return xOffset;}
	public synchronized double getYOffset(){return yOffset;}


	public synchronized void setXLoc(int x){xLoc = x;}
	public synchronized void setYLoc(int y){yLoc = y;}
	public synchronized void setXOffset(double x){xOffset = x;}
	public synchronized void setYOffset(double y){yOffset = y;}

   public MapPosition()
   {
     this(0, 0, 0.0, 0.0);
   }
   
   public MapPosition(int x, int y)
   {
      this(x, y, 0.0, 0.0);
   }
   
   public MapPosition(int x, int y, double subX, double subY)
   {
      xLoc = x;
      yLoc = y;
      xOffset = subX;
      yOffset = subY;
   }
   
   public synchronized void set(int x, int y)
   {
      xLoc = x;
      yLoc = y;
   }
   
   public synchronized void set(int x, int y, double subX, double subY)
   {
      xLoc = x;
      yLoc = y;
      xOffset = subX;
      yOffset = subY;
   }
   
   public synchronized void setOffset(double subX, double subY)
   {
      xOffset = subX;
      yOffset = subY;
   }
   
   public synchronized void center()
   {
      xOffset = 0.0;
      yOffset = 0.0;
   }
   
   public synchronized void add(int x, int y){add(x, y, 0.0, 0.0);}
   public synchronized void add(int x, int y, double subX, double subY)
   {
      xLoc += x;
      yLoc += y;
      xOffset += subX;
      yOffset += subY;
   }
}