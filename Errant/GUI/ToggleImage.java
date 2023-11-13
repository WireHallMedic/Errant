package Errant.GUI;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

// holds multiple buffered images, returns the current one based on a blink
public class ToggleImage extends ErrantImage implements GUIConstants
{
   protected BufferedImage imageB;
   protected BufferedImage baseImageB;
	private boolean state;


	public boolean gettate(){return state;}


	public void setState(boolean s){state = s;}
   public void toggle(){state = !state;}


   public ToggleImage(BufferedImage imgA, BufferedImage imgB)
   {
      this(imgA, imgB, DEFAULT_SIZE, DEFAULT_SIZE);
   }

   public ToggleImage(BufferedImage imgA, BufferedImage imgB, int tileSize)
   {
      this(imgA, imgB, tileSize, tileSize);
   }

   public ToggleImage(BufferedImage imgA, BufferedImage imgB, int w, int h)
   {
      super(imgA, w, h);
      baseImageB = imgB;
      process();
      state = true;
   }

	public void setImages(BufferedImage imgA, BufferedImage imgB){setImages(imgA, imgB, true);}
	public void setImages(BufferedImage imgA, BufferedImage imgB, boolean process)
   {
      baseImage = imgA;
      baseImageB = imgB;
      if(process)
         generateImage();
   }
   
   @Override
   public void generateImage()
   {
      image = ImageTools.scale(baseImage, width, height);
      if(baseImageB != null)
         imageB = ImageTools.scale(baseImageB, width, height);
   }
   
   @Override
	public BufferedImage getImage()
   {
      if(state)
         return image;
      return imageB;
   }
}