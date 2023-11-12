package Errant.GUI;

import Errant.Tools.*;
import Errant.Map.*;

public class MovementScriptFactory implements GUIConstants
{
   public static MovementScript getStepScript(ErrantImage image, MapPosition mp, Direction dir)
   {
      int newXTile = mp.getXLoc() + dir.x;
      int newYTile = mp.getYLoc() + dir.y;
      
      MovementScript script = new MovementScript(image, mp);
      script.addStep(250, 4.0 * dir.x, 4.0 * dir.y);
      mp.set(newXTile, newYTile, -1.0 * dir.x, -1.0 * dir.y);
      return script;
   }
   
   public static MovementScript getPreAttackScript(ErrantImage image, MapPosition mp, Direction dir)
   {
      MovementScript script = new MovementScript(image, mp);
      script.addStep(75, -3.0 * dir.x, -3.0 * dir.y);
      script.addStep(150, 6.0 * dir.x, 6.0 * dir.y);
      script.setCenterOnEnd(false);
      return script;
   }
   
   public static MovementScript getPostAttackScript(ErrantImage image, MapPosition mp, Direction dir)
   {
      MovementScript script = new MovementScript(image, mp);
      script.addStep(225, -3.0 * dir.x, -3.0 * dir.y);
      return script;
   }
   
   public static MovementScript getProjectileScript(ErrantImage image, MapPosition mp, int xTarget, int yTarget, double speed)
   {
      MovementScript script = new MovementScript(image, mp);
      double fullX = xTarget - mp.getXLoc();
      double fullY = yTarget - mp.getYLoc();;
      double fullDist = Math.sqrt((fullX * fullX) + (fullY * fullY));
      int duration = (int)((fullDist / speed) * 1000);
      double milliDuration = duration / 1000.0;
      script.addStep(duration, fullX / milliDuration, fullY / milliDuration);
      script.setActionOnEnd(EXPIRE_IMAGE_ON_END);
      return script;
   }
}