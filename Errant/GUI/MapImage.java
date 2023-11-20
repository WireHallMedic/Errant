package Errant.GUI;

import Errant.Map.*;
import Errant.Tools.*;
import java.awt.*;
import java.awt.image.*;

public class MapImage
{
	private BufferedImage baseBGImage;
	private ErrantImage[][] bgImageArray;
	private ErrantImage[][] fgImageArray;
	private int tileSize;
	private int columns;
	private int rows;


	public BufferedImage getBaseBGImage(){return baseBGImage;}
	public ErrantImage[][] getBGImageArray(){return bgImageArray;}
	public ErrantImage[][] getFGImageArray(){return fgImageArray;}
	public int getTileSize(){return tileSize;}
	public int getColumns(){return columns;}
	public int getRows(){return rows;}


	public void setBaseBGImage(BufferedImage b){baseBGImage = b;}
	public void setBGImageArray(ErrantImage[][] b){bgImageArray = b;}
	public void setFGImageArray(ErrantImage[][] f){fgImageArray = f;}
	public void setTileSize(int t){tileSize = t;}
	public void setColumns(int c){columns = c;}
	public void setRows(int r){rows = r;}


   public MapImage(int c, int r, int ts)
   {
      this(null, c, r, ts);
   }
   
   public MapImage(BufferedImage bgImage, int c, int r, int ts)
   {
      baseBGImage = bgImage;
      columns = c;
      rows = r;
      tileSize = ts;
      bgImageArray = new ErrantImage[columns][rows];
      fgImageArray = new ErrantImage[columns][rows];
      
      for(int x = 0; x < columns; x++)
      for(int y = 0; y < rows; y++)
      {
         bgImageArray[x][y] = null;
         fgImageArray[x][y] = null;
      }
   }
   
   public boolean isInBounds(int x, int y)
   {
      return x >= 0 && x < columns &&
             y >= 0 && y < rows;
   }
   
   public void setBGImage(int x, int y, ErrantImage ei)
   {
      if(isInBounds(x, y))
         bgImageArray[x][y] = ei;
   }
   public void setFGImage(int x, int y, ErrantImage ei)
   {
      if(isInBounds(x, y))
         fgImageArray[x][y] = ei;
   }
   
   public ErrantImage getBGImage(int x, int y)
   {
      if(isInBounds(x, y))
         return bgImageArray[x][y];
      return null;
   }
   
   public ErrantImage getFGImage(int x, int y)
   {
      if(isInBounds(x, y))
         return fgImageArray[x][y];
      return null;
   }
}