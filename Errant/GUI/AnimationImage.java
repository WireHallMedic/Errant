package Errant.GUI;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

// holds multiple buffered images, returns the current one based on a blink
public class AnimationImage extends ErrantImage implements GUIConstants
{
   protected BufferedImage imageB;
   protected BufferedImage baseImageB;
   protected int animationSpeed;

   public int getAnimationSpeed(){return animationSpeed;}
   
   public void setAnimationSpeed(int as){animationSpeed = as;}


   public AnimationImage(BufferedImage imgA, BufferedImage imgB)
   {
      this(imgA, imgB, DEFAULT_SIZE, DEFAULT_SIZE);
   }

   public AnimationImage(BufferedImage imgA, BufferedImage imgB, int tileSize)
   {
      this(imgA, imgB, tileSize, tileSize);
   }

   public AnimationImage(BufferedImage imgA, BufferedImage imgB, int w, int h)
   {
      super(imgA, w, h);
      baseImageB = imgB;
      process();
      animationSpeed = MEDIUM_ANIMATION_SPEED;
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
      boolean animationState = false;
      switch(animationSpeed)
      {
         case SLOW_ANIMATION_SPEED :   animationState = AnimationManager.slowBlink; break;
         case MEDIUM_ANIMATION_SPEED : animationState = AnimationManager.mediumBlink; break;
         case FAST_ANIMATION_SPEED :   animationState = AnimationManager.fastBlink; break;
      }
      if(animationState)
         return image;
      return imageB;
   }
}