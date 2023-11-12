package Errant.GUI;

import Errant.Tools.*;
import Errant.Map.*;

public class MovementScriptFactory
{
   public static MovementScript getStepScript(MapPosition mp, Direction dir)
   {
      int newXTile = mp.getXLoc() + dir.x;
      int newYTile = mp.getXLoc() + dir.y;
      
      MovementScript script = new MovementScript(mp);
      script.addStep(500, 2.0 * dir.x, 2.0 * dir.y);
      mp.set(newXTile, newYTile, -1.0 * dir.x, -1.0 * dir.y);
      return script;
   }
}