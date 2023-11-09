package Errant.Tools;

import java.util.regex.*;

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
}