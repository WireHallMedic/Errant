package Errant.GUI;

import java.awt.*;
import java.awt.image.*;
import Errant.Tools.*;

// holds two buffered images, and another two that are mirrors of the same
public class ActorImage extends AnimationImage implements GUIConstants
{
   protected BufferedImage mirroredA;
   protected BufferedImage mirroredB;
   protected boolean facing;
   
   public boolean getFacing(){return facing;}
   
   public void setFacing(boolean f){facing = f;}


   public ActorImage(BufferedImage imgA, BufferedImage imgB)
   {
      this(imgA, imgB, DEFAULT_SIZE, DEFAULT_SIZE);
   }

   public ActorImage(BufferedImage imgA, BufferedImage imgB, int tileSize)
   {
      this(imgA, imgB, tileSize, tileSize);
   }

   public ActorImage(BufferedImage imgA, BufferedImage imgB, int w, int h)
   {
      super(imgA, imgB, w, h);
      facing = FACING_LEFT;
   }
   
   public void setFacing(Direction dir)
   {
      switch(dir)
      {
         case EAST :
         case NORTHEAST :
         case SOUTHEAST : facing = FACING_RIGHT; break;
         case WEST :
         case NORTHWEST :
         case SOUTHWEST : facing = FACING_LEFT; break;
      }
   }
   
   @Override
   public void generateImage()
   {
      image = ImageTools.scale(baseImage, width, height);
      mirroredA = ImageTools.mirrorHorizontal(image);
      if(baseImageB != null)
      {
         imageB = ImageTools.scale(baseImageB, width, height);
         mirroredB = ImageTools.mirrorHorizontal(imageB);
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