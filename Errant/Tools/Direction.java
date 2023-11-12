package Errant.Tools;

import WidlerSuite.Coord;

public enum Direction
{
   ORIGIN      (0, 0),
   NORTH       (0, -1),
   NORTHEAST   (1, -1),
   EAST        (1, 0),
   SOUTHEAST   (1, 1),
   SOUTH       (0, 1),
   SOUTHWEST   (-1, 1),
   WEST        (-1, 0),
   NORTHWEST   (-1, -1);
   
   public int x;
   public int y;
   
   private Direction(int xIn, int yIn)
   {
      x = xIn;
      y = yIn;
   }
   
   public Coord getAsCoord()
   {
      return new Coord(x, y);
   }
   
   public Direction getNextClockwise()
   {
      if(this == ORIGIN)
         return this;
      int index = ordinal() + 1;
      if(index == values().length)
         index = 1;
      return values()[index];
   }
   
   public Direction getNextCounterClockwise()
   {
      if(this == ORIGIN)
         return this;
      int index = ordinal() - 1;
      if(index == 0)
         index = values().length - 1;
      return values()[index];
   }
}