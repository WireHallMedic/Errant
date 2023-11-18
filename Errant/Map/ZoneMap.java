package Errant.Map;

public class ZoneMap implements MapConstants
{
	private char[][] tileIndex;
	private int width;
	private int height;


	public char[][] getTileIndex(){return tileIndex;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}


	public void setTileIndex(char[][] t){tileIndex = t;}
	public void setWidth(int w){width = w;}
	public void setHeight(int h){height = h;}


   public ZoneMap(int w, int h)
   {
      tileIndex = new char[w][h];
      width = w;
      height = h;
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
         tileIndex[x][y] = EMPTY;
   }
   
   public boolean isInBounds(int x, int y)
   {
      return x > 0 && x < width &&
             y > 0 && y < height;
   }
   
   public char getTileIndex(int x, int y)
   {
      if(isInBounds(x, y))
         return tileIndex[x][y];
      return EMPTY;
   }
   
   public void setTileIndex(int x, int y, char c)
   {
      if(isInBounds(x, y))
         tileIndex[x][y] = c;
   }

}