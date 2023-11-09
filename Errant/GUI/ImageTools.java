package Errant.GUI;

import java.awt.*;
import java.awt.image.*;

public class ImageTools
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
   
   public static BufferedImage getFromSheet(BufferedImage sheet, int x, int y, int tileWidth, int tileHeight)
   {
      return null;
   }
}