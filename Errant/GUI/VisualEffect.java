package Errant.GUI;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

// holds a list of images that are cycled through, and is controled by millis rather than a blink
public class VisualEffect extends ErrantImage implements GUIConstants, MilliListener
{
   protected Vector<BufferedImage> imageList;
   protected Vector<BufferedImage> baseImageList;
	private int millisPerFrame;
	private int millisThisFrame;
	private int curFrame;
   private int actionOnEnd;
   private boolean expired;


	public int getMillisPerFrame(){return millisPerFrame;}
	public int getMillisThisFrame(){return millisThisFrame;}
	public boolean isExpired(){return expired;}
	public int getCurFrame(){return curFrame;}
   public int getActionOnEnd(){return actionOnEnd;}


	public void setMillisPerFrame(int m){millisPerFrame = m;}
	public void setMillisThisFrame(int m){millisThisFrame = m;}
	public void expire(){expired = true;}
	public void setCurFrame(int c){curFrame = c;}
   public void setActionOnEnd(int aoe){actionOnEnd = aoe;}




   public VisualEffect(Vector<BufferedImage> imgLst)
   {
      this(imgLst, DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_VISUAL_EFFECT_FRAME_DURATION);
   }

   public VisualEffect(Vector<BufferedImage> imgLst, int tileSize)
   {
      this(imgLst, tileSize, tileSize, DEFAULT_VISUAL_EFFECT_FRAME_DURATION);
   }

   public VisualEffect(Vector<BufferedImage> imgLst, int w, int h)
   {
      this(imgLst, w, h, DEFAULT_VISUAL_EFFECT_FRAME_DURATION);
   }

   public VisualEffect(Vector<BufferedImage> imgLst, int w, int h, int mpf)
   {
      super(imgLst.elementAt(0), w, h);
      setImages(imgLst);
      process();
      millisPerFrame = mpf;
      millisThisFrame = 0;
      curFrame = 0;
      actionOnEnd = EXPIRE_ON_END;
      expired = false;
      AnimationManager.addListener(this);
   }

	public void setImages(Vector<BufferedImage> imgLst){setImages(imgLst, true);}
	public void setImages(Vector<BufferedImage> imgLst, boolean process)
   {
      baseImageList = new Vector<BufferedImage>();
      for(BufferedImage img : imgLst)
         baseImageList.add(img);
      if(process)
         generateImage();
   }
   
   @Override
   public void generateImage()
   {
      if(baseImageList == null)
         return;
      imageList = new Vector<BufferedImage>();
      for(BufferedImage base : baseImageList)
         imageList.add(ImageTools.scale(base, width, height));
   }
   
   @Override
	public BufferedImage getImage()
   {
      if(!expired)
         return imageList.elementAt(curFrame);
      return null;
   }
   
   public void millisElapsed(int me)
   {
      millisThisFrame += me;
      if(millisThisFrame >= millisPerFrame)
      {
         curFrame++;
         millisThisFrame -= millisPerFrame;
         if(curFrame >= imageList.size())
         {
            if(actionOnEnd == LOOP_ON_END)
               curFrame = 0;
            else
               expired = true;
         }
      }
   }
}