package Errant.Tools;

public class SystemTools
{
   public static final boolean RUNNING_FROM_JAR = isRunningFromJar();

   // check if this is running from a .jar or not
   private static boolean isRunningFromJar()
   {
      String str = ImageTools.class.getResource(ImageTools.class.getSimpleName().toString() + ".class").toString();
      Matcher matcher = jarPattern.matcher(str);
      return matcher.find();
   }
}