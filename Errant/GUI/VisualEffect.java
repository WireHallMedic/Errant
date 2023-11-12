package Errant.GUI;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

// holds a list of images that are cycled through, and is controled by millis rather than a blink
public class VisualEffect extends ErrantImage implements GUIConstants, MilliListener
{
   protected Vector<BufferedImage> imageList;
   protected Vector<BufferedImage> baseImageList;
	private int animationSpeed;
	private int millisPerFrame;
	private int millisThisFrame;
	private int curFrame;


	public int getAnimationSpeed(){return animationSpeed;}
	public int getMillisPerFrame(){return millisPerFrame;}
	public int getMillisThisFrame(){return millisThisFrame;}
	public boolean isExpired(){return curFrame < imageList.size();}
	public int getCurFrame(){return curFrame;}


	public void setAnimationSpeed(int a){animationSpeed = a;}
	public void setMillisPerFrame(int m){millisPerFrame = m;}
	public void setMillisThisFrame(int m){millisThisFrame = m;}
	public void expire(){curFrame = imageList.size();}
	public void setCurFrame(int c){curFrame = c;}




   public VisualEffect(Vector<BufferedImage> imgLst)
   {
      this(imgLst, DEFAULT_SIZE, DEFAULT_SIZE, VERY_FAST_ANIMATION_SPEED, 200);
   }

   public VisualEffect(Vector<BufferedImage> imgLst, int tileSize)
   {
      this(imgLst, tileSize, tileSize, VERY_FAST_ANIMATION_SPEED, 200);
   }

   public VisualEffect(Vector<BufferedImage> imgLst, int w, int h)
   {
      this(imgLst, w, h, VERY_FAST_ANIMATION_SPEED, 200);
   }

   public VisualEffect(Vector<BufferedImage> imgLst, int w, int h, int as, int mpf)
   {
      super(imgLst.elementAt(0), w, h);
      process();
      animationSpeed = as;
      millisPerFrame = mpf;
      millisThisFrame = 0;
      curFrame = 0;
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
      imageList = new Vector<BufferedImage>();
      for(BufferedImage base : baseImageList)
         imageList.add(ImageTools.scale(base, width, height));
   }
   
   @Override
	public BufferedImage getImage()
   {
      if(curFrame < imageList.size())
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
      }
   }
}