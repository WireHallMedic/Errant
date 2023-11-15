package Errant.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import Errant.GUI.*;
import java.io.*;
import javax.imageio.*;

public class ColorUnifier implements GUIConstants
{
   public static final int precision = 5;
   public static final Color[][] colorList = {RED_GROUP, GREEN_GROUP, LIGHT_GREEN_GROUP, BLUE_GROUP, LIGHT_BLUE_GROUP, YELLOW_GROUP, ORANGE_GROUP,
                                              PURPLE_GROUP, BLACK_GROUP, WHITE_GROUP, GREY_GROUP, BROWN_GROUP, LIGHT_GREY_GROUP,
                                              DARK_FLESH_GROUP, MEDIUM_FLESH_GROUP, LIGHT_FLESH_GROUP, ROTTEN_FLESH_GROUP, DEMON_FLESH_GROUP};

   public boolean close(int a, int b)
   {
      return (b < a + precision) && (b > a - precision);
   }
   
   public boolean closeButNotEqual(Color standard, Color prospect)
   {
      if(standard.getRGB() == prospect.getRGB())
         return false;
      
      return close(standard.getRed(), prospect.getRed()) &&
             close(standard.getBlue(), prospect.getBlue()) &&
             close(standard.getGreen(), prospect.getGreen()) &&
             close(standard.getAlpha(), prospect.getAlpha());
   }
   
   public Color getTemplateColor(Color c)
   {
      for(int i = 0; i < colorList.length; i++)
      for(int j = 0; j < colorList[i].length; j++)
      {
         Color standardColor = colorList[i][j];
         if(closeButNotEqual(standardColor, c))
            return standardColor;
      }
      return c;
   }
   
   public BufferedImage getCorrected(BufferedImage original)
   {      
      int width = original.getWidth();
      int height = original.getHeight();
      BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Color curColor;
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         curColor = new Color(original.getRGB(x, y), true);
         curColor = getTemplateColor(curColor);
         newImg.setRGB(x, y, curColor.getRGB());
      }
      return newImg;
   }
   
   public static void main(String[] args)
   {
      String fileName = "Actors/paper_doll_heads.png";
      ColorUnifier cu = new ColorUnifier();
      BufferedImage bi = SystemTools.loadImageFromFile(fileName);
      //    "Actors/animals.png"
    //      "Actors/cultists.png");
//          "Actors/demons.png");
//          "Actors/undead.png");
      try
      {
          bi = cu.getCorrected(bi);
          File outputfile = new File("correction_output.png");
          ImageIO.write(bi, "png", outputfile);
      } 
      catch (IOException e) 
      {
          System.out.println(e.getStackTrace());
      }
   }
}

