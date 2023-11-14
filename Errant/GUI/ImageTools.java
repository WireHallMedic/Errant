package Errant.GUI;

import java.awt.*;
import java.awt.image.*;
import Errant.Tools.*;

public class ImageTools implements ToolConstants
{
   
   // return a copy scaled to passed size
   public static BufferedImage scale(BufferedImage original, int newWidth, int newHeight)
   {
      Image scaledImage = original.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
      BufferedImage newBuffered = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = newBuffered.createGraphics();
      g2d.drawImage(scaledImage, 0, 0 , null);
      g2d.dispose();
      return newBuffered;
   }
   
   // return a horizontally mirrored copy (ie,swap left and right)
   public static BufferedImage mirrorHorizontal(BufferedImage original)
   {
      BufferedImage mirrored = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = mirrored.createGraphics();
      g2d.scale(-1, 1);
      g2d.translate(-original.getWidth(), 0);
      g2d.drawImage(original, 0, 0, null);
      g2d.dispose();
      return mirrored;
   }
   
   // return a vertically mirrored copy (ie, flip upside down)
   public static BufferedImage mirrorVertical(BufferedImage original)
   {
      BufferedImage mirrored = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = mirrored.createGraphics();
      g2d.scale(1, -1);
      g2d.translate(0, -original.getHeight());
      g2d.drawImage(original, 0, 0, null);
      g2d.dispose();
      return mirrored;
   }
   
   // returns a copy replacing one color with another
   public static BufferedImage replaceColor(BufferedImage img, int oldColor, int newColor)
   {
      int width = img.getWidth();
      int height = img.getHeight();
      BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      int curColorRGB;
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         curColorRGB = img.getRGB(x, y);
         if(curColorRGB == oldColor)
            newImg.setRGB(x, y, newColor);
         else
            newImg.setRGB(x, y, curColorRGB);
      }
      return newImg;
   }
   
   public static BufferedImage replaceColor(BufferedImage img, Color[] oldGroup, Color[] newGroup)
   {
      BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = newImage.createGraphics();
      g2d.drawImage(img, 0, 0, null);
      g2d.dispose();
      for(int i = 0; i < oldGroup.length; i++)
         newImage = replaceColor(newImage, oldGroup[i].getRGB(), newGroup[i].getRGB());
      return newImage;
   }
   
   // returns a copy with upper layered over lower
   public static BufferedImage overlay(BufferedImage lower, BufferedImage upper)
   {
      BufferedImage layered = new BufferedImage(lower.getWidth(), lower.getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = layered.createGraphics();
      g2d.drawImage(lower, 0, 0, null);
      g2d.drawImage(upper, 0, 0, null);
      g2d.dispose();
      return layered;
   }
   
   // return a tile from a spritesheet
   public static BufferedImage getFromSheet(BufferedImage sheet, int x, int y, int tileWidth, int tileHeight)
   {
      try
      {
         return sheet.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
      }
      catch(Exception e)
      {
         e.printStackTrace(); 
      }
      throw new Error("Unable get subimage.");
   }
   
   // returns a rotated copy of an image. May result in clipping. Angle is in radians.
   public static BufferedImage rotate(BufferedImage img, double angle)
   {
      int w = img.getWidth();    
      int h = img.getHeight();
      BufferedImage rotated = new BufferedImage(w, h, img.getType());  
      Graphics2D graphic = rotated.createGraphics();
      graphic.rotate(angle, w / 2, h / 2);
      graphic.drawImage(img, null, 0, 0);
      graphic.dispose();
      return rotated;
   }
}