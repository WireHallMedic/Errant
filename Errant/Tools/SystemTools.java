package Errant.Tools;

import java.awt.*;
import java.net.*;
import java.util.regex.*;
import java.io.*;

public class SystemTools
{
   public static final boolean RUNNING_FROM_JAR = isRunningFromJar();

   // check if this is running from a .jar or not
   private static boolean isRunningFromJar()
   {
      String str = SystemTools.class.getResource(SystemTools.class.getSimpleName().toString() + ".class").toString();
      Pattern jarPattern = Pattern.compile("^jar:");
      Matcher matcher = jarPattern.matcher(str);
      return matcher.find();
   }
   
   public static URL loadResource(String fileName)
   {
      try
      {
         if(RUNNING_FROM_JAR)
            fileName = "." + fileName;
         return SystemTools.class.getResource(fileName);
      } 
      catch (Exception e) 
      { 
         e.printStackTrace(); 
      }
      throw new Error("Unable to load file " + fileName);
   }
}