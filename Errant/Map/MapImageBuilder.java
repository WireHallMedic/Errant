package Errant.Map;

import Errant.GUI.*;
import Errant.Tools.*;
import java.awt.*;
import java.awt.image.*;

public class MapImageBuilder implements GUIConstants, MapConstants
{
   private static final int NORTH = 1;
   private static final int EAST = 2;
   private static final int SOUTH = 4;
   private static final int WEST = 8;
   private static final int Nesw = NORTH;
   private static final int NEsw = NORTH + EAST;
   private static final int NeSw = NORTH + SOUTH;
   private static final int NesW = NORTH + WEST;
   private static final int NESw = NORTH + EAST + SOUTH;
   private static final int NEsW = NORTH + EAST + WEST;
   private static final int NeSW = NORTH + SOUTH + WEST;
   private static final int NESW = NORTH + EAST + SOUTH + WEST;
   private static final int nesw = 0;
   private static final int nEsw = EAST;
   private static final int neSw = SOUTH;
   private static final int nesW = WEST;
   private static final int nESw = EAST + SOUTH;
   private static final int nEsW = EAST + WEST;
   private static final int neSW = SOUTH + WEST;
   private static final int nESW = EAST + SOUTH + WEST;
   
   
   public static MapImage generateMapImage(ZoneMap map, int tileSize){return generateMapImage(map, TerrainStyleIndex.STANDARD, tileSize);}
   public static MapImage generateMapImage(ZoneMap map, TerrainStyleIndex style, int tileSize)
   {
      int widthTiles = map.getWidth();
      int heightTiles = map.getHeight();
      BufferedImage mapBI = new BufferedImage(widthTiles * DEFAULT_TERRAIN_SIZE, heightTiles * DEFAULT_TERRAIN_SIZE, BufferedImage.TYPE_INT_ARGB);
      BufferedImage wallAndFloorTiles = SystemTools.loadImageFromFile("Terrain/walls_and_floors.png");
      BufferedImage doorTiles = SystemTools.loadImageFromFile("Terrain/doors.png");
      Graphics2D g2d = mapBI.createGraphics();
      for(int x = 0; x < widthTiles; x++)
      for(int y = 0; y < heightTiles; y++)
      {
         g2d.drawImage(getBGTile(map, x, y, wallAndFloorTiles, style), x * DEFAULT_TERRAIN_SIZE, y * DEFAULT_TERRAIN_SIZE, null);
      }
      g2d.dispose();
      mapBI = ImageTools.scale(mapBI, widthTiles * tileSize, heightTiles * tileSize);
      MapImage mapImage = new MapImage(mapBI, widthTiles, heightTiles, tileSize);
      for(int x = 0; x < widthTiles; x++)
      for(int y = 0; y < heightTiles; y++)
      {
         if(map.getTileIndex(x, y) == DOOR)
         {
            ToggleImage door = getDoor(doorTiles);
            door.setSize(tileSize);
            mapImage.setFGImage(x, y, door);
         }
      }
      return mapImage;
   }
   
   public static BufferedImage getBGTile(ZoneMap map, int x, int y, BufferedImage spriteSheet, TerrainStyleIndex style)
   {
      char tileIndex = map.getTileIndex(x, y);
      int xIndex = 0;
      int yIndex = style.yLoc;
      if(tileIndex == WALL)
      {
         boolean[] adjWalls = map.getAdjacentWallArray(x, y);
         int adjIndex = 0;
         if(adjWalls[0])
            adjIndex += NORTH;
         if(adjWalls[1])
            adjIndex += EAST;
         if(adjWalls[2])
            adjIndex += SOUTH;
         if(adjWalls[3])
            adjIndex += WEST;
         TerrainStripIndex tsi = null;
         switch(adjIndex)
         {
            case Nesw : tsi = TerrainStripIndex.WALL_N; break;
            case NEsw : tsi = TerrainStripIndex.WALL_NE; break;
            case NeSw : tsi = TerrainStripIndex.WALL_NS; break;
            case NesW : tsi = TerrainStripIndex.WALL_NW; break;
            case NESw : tsi = TerrainStripIndex.WALL_NES; break;
            case NEsW : tsi = TerrainStripIndex.WALL_NEW; break;
            case NeSW : tsi = TerrainStripIndex.WALL_NSW; break;
            case NESW : tsi = TerrainStripIndex.WALL_NESW; break;
            case nesw : tsi = TerrainStripIndex.BLOCK; break;
            case nEsw : tsi = TerrainStripIndex.WALL_E; break;
            case neSw : tsi = TerrainStripIndex.WALL_S; break;
            case nesW : tsi = TerrainStripIndex.WALL_W; break;
            case nESw : tsi = TerrainStripIndex.WALL_ES; break;
            case nEsW : tsi = TerrainStripIndex.WALL_EW; break;
            case neSW : tsi = TerrainStripIndex.WALL_SW; break;
            case nESW : tsi = TerrainStripIndex.WALL_ESW; break;
         }
         if(tsi != null)
             return ImageTools.getFromSheet(spriteSheet, tsi.xLoc, yIndex, DEFAULT_TERRAIN_SIZE, DEFAULT_TERRAIN_SIZE);
      }
      switch(tileIndex)
      {
         case WALL :    xIndex = TerrainStripIndex.BLOCK.xLoc; break;
         case DOOR :
         case FLOOR :   xIndex = TerrainStripIndex.FLOOR.xLoc; break;
      }
      return ImageTools.getFromSheet(spriteSheet, xIndex, yIndex, DEFAULT_TERRAIN_SIZE, DEFAULT_TERRAIN_SIZE);
   }
   
   public static ToggleImage getDoor(BufferedImage spriteSheet)
   {
      BufferedImage closedDoor = ImageTools.getFromSheet(spriteSheet, 0, 0, DEFAULT_TERRAIN_SIZE, DEFAULT_TERRAIN_SIZE);
      BufferedImage openDoor = ImageTools.getFromSheet(spriteSheet, 1, 0, DEFAULT_TERRAIN_SIZE, DEFAULT_TERRAIN_SIZE);
      return new ToggleImage(closedDoor, openDoor, DEFAULT_TERRAIN_SIZE);
   }
}