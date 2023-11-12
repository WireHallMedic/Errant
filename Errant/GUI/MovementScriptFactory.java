package Errant.GUI;

import Errant.Tools.*;
import Errant.Map.*;

public class MovementScriptFactory
{
   public static MovementScript getStepScript(MapPosition mp, Direction dir)
   {
      int newXTile = mp.getXLoc() + dir.x;
      int newYTile = mp.getYLoc() + dir.y;
      
      MovementScript script = new MovementScript(mp);
      script.addStep(250, 4.0 * dir.x, 4.0 * dir.y);
      mp.set(newXTile, newYTile, -1.0 * dir.x, -1.0 * dir.y);
      return script;
   }
   
   public static MovementScript getPreAttackScript(MapPosition mp, Direction dir)
   {
      MovementScript script = new MovementScript(mp);
      script.addStep(75, -3.0 * dir.x, -3.0 * dir.y);
      script.addStep(150, 6.0 * dir.x, 6.0 * dir.y);
      script.setCenterOnEnd(false);
      return script;
   }
   
   public static MovementScript getPostAttackScript(MapPosition mp, Direction dir)
   {
      MovementScript script = new MovementScript(mp);
      script.addStep(225, -3.0 * dir.x, -3.0 * dir.y);
      return script;
   }
}