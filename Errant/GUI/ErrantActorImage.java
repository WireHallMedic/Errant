package Errant.GUI;

import java.awt.*;
import java.awt.image.*;

// holds two buffered images, and another two that are mirrors of the same
public class ErrantActorImage extends ErrantAnimationImage implements GUIConstants
{
   protected BufferedImage mirroredA;
   protected BufferedImage mirroredB;
   protected boolean facing;
   
   public boolean getFacing(){return facing;}
   
   public void setFacing(boolean f){facing = f;}


   public ErrantActorImage(BufferedImage imgA, BufferedImage imgB)
   {
      this(imgA, imgB, DEFAULT_SIZE, DEFAULT_SIZE);
   }

   public ErrantActorImage(BufferedImage imgA, BufferedImage imgB, int tileSize)
   {
      this(imgA, imgB, tileSize, tileSize);
   }

   public ErrantActorImage(BufferedImage imgA, BufferedImage imgB, int w, int h)
   {
      super(imgA, imgB, w, h);
      facing = FACING_LEFT;
   }
   
   @Override
   public void generateImage()
   {
      image = ImageTools.scale(baseImage, width, height);
      mirroredA = ImageTools.mirrorHorizontal(image);
      if(baseImageB != null)
      {
         imageB = ImageTools.scale(baseImageB, width, height);
         mirroredA = ImageTools.mirrorHorizontal(imageB);
      }
   }
   
   @Override
	public BufferedImage getImage()
   {
      if(facing == FACING_LEFT)
         return super.getImage();
      boolean animationState = false;
      switch(animationSpeed)
      {
         case SLOW_ANIMATION_SPEED :   animationState = AnimationManager.slowBlink; break;
         case MEDIUM_ANIMATION_SPEED : animationState = AnimationManager.mediumBlink; break;
         case FAST_ANIMATION_SPEED :   animationState = AnimationManager.fastBlink; break;
      }
      if(animationState)
         return mirroredA;
      return mirroredB;
   }
}