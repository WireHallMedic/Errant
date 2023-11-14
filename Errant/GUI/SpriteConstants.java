package Errant.GUI;

public interface SpriteConstants
{
   public static final String PAPER_DOLL_BODY_FILE = "Actors/paper_doll_bodies.png";
   public static final String PAPER_DOLL_HEAD_FILE = "Actors/paper_doll_heads.png";
   public static final String PAPER_DOLL_GEAR_FILE = "Actors/paper_doll_gear.png";
   public static final String SMALL_EFFECT_FILE = "Effects/oryx_effects_24.png";
   public static final String LARGE_EFFECT_FILE = "Effects/oryx_effects_32.png";
   public static final String UNDEAD_FILE = "Actors/undead.png";
   public static final String CULTIST_FILE = "Actors/cultists.png";
   public static final String ANIMAL_FILE = "Actors/animals.png";
   public static final String DEMON_FILE = "Actors/demons.png";
   public static final String TERRAIN_FILE = "Terrain/oryx_terrain.png";
   public static final String ITEM_FILE = "Items/oryx_items.png";
   public static final String GOLD_FILE = "Items/coins.png";
   
   public enum GoldSprite
   {
      SINGLE_COIN       (0, 0),
      SMALL_PILE        (1, 0),
      MEDIUM_PILE       (2, 0),
      LARGE_PILE        (3, 0),
      VERY_LARGE_PILE   (4, 0);
      
      public int[] sheetLocation;
      public String fileName;
      
      private GoldSprite(int x, int y)
      {
         sheetLocation = new int[2];
         sheetLocation[0] = x;
         sheetLocation[1] = y;
         fileName = GOLD_FILE;
      }
   }
   
   public enum PaperDollBodySprite
   {
      CLOTHES  (0, 0),
      ROBES    (1, 0),
      ARMOR    (2, 0);
      
      public int[][] sheetLocation;
      public String fileName;
      
      private PaperDollBodySprite(int x, int y)
      {
         sheetLocation = new int[2][2];
         sheetLocation[0][0] = x;
         sheetLocation[0][1] = y;
         sheetLocation[1][0] = x;
         sheetLocation[1][1] = y + 1;
         fileName = PAPER_DOLL_BODY_FILE;
      }
   }
   
   public enum PaperDollHeadSprite
   {
      SHORT_HAIR     (0, 0),
      LONG_HAIR      (1, 0),
      LIGHT_HELMET   (2, 0),
      HEAVY_HELMET   (3, 0);
      
      public int[][] sheetLocation;
      public String fileName;
      
      private PaperDollHeadSprite(int x, int y)
      {
         sheetLocation = new int[2][2];
         sheetLocation[0][0] = x;
         sheetLocation[0][1] = y;
         sheetLocation[1][0] = x;
         sheetLocation[1][1] = y + 1;
         fileName = PAPER_DOLL_HEAD_FILE;
      }
   }
   
   public enum PaperDollGearSprite
   {
      DAGGER_MAIN    (0, 0),
      SWORD_MAIN     (1, 0),
      HEAVY_SWORD    (2, 0),
      MACE_MAIN      (3, 0),
      HAMMER_MAIN    (4, 0),
      HEAVY_HAMMER   (5, 0),
      AXE_MAIN       (6, 0),
      HEAVY_AXE      (7, 0),
      BOW            (8, 0),
      SPEAR          (9, 0),
      WAND           (10, 0),
      STAFF          (11, 0),
      LIGHT_SHIELD   (0, 2),
      HEAVY_SHIELD   (1, 2),
      DAGGER_OFF     (2, 2),
      SWORD_OFF      (3, 2),
      MACE_OFF       (4, 2),
      HAMMER_OFF     (5, 2),
      AXE_OFF        (6, 2),
      ORB            (7, 2);
      
      public int[][] sheetLocation;
      public String fileName;
      
      private PaperDollGearSprite(int x, int y)
      {
         sheetLocation = new int[2][2];
         sheetLocation[0][0] = x;
         sheetLocation[0][1] = y;
         sheetLocation[1][0] = x;
         sheetLocation[1][1] = y + 1;
         fileName = PAPER_DOLL_GEAR_FILE;
      }
   }
   
   public enum CreatureSprite
   {
      // undead
      ZOMBIE               (0, 0, UNDEAD_FILE),
      SKELETON_GRUNT       (1, 0, UNDEAD_FILE),
      SKELETON_ARCHER      (2, 0, UNDEAD_FILE),
      SKELETON_WARRIOR     (3, 0, UNDEAD_FILE),
      SKELETON_MAGE        (4, 0, UNDEAD_FILE),
      SHADOW               (5, 0, UNDEAD_FILE),
      GHOST                (6, 0, UNDEAD_FILE),
      MUMMY                (7, 0, UNDEAD_FILE),
      LICH                 (8, 0, UNDEAD_FILE),
      DEATH_KNIGHT         (9, 0, UNDEAD_FILE),
      
      // human
      CULTIST_GRUNT        (0, 0, CULTIST_FILE),
      CULT_WIZARD          (1, 0, CULTIST_FILE),
      CULT_ASSASSIN        (2, 0, CULTIST_FILE),
      CULT_BRUTE           (3, 0, CULTIST_FILE),
      
      // demon
      LESSER_DEMON_GRUNT   (0, 0, DEMON_FILE),
      LESSER_DEMON_WARRIOR (1, 0, DEMON_FILE),
      LESSER_DEMON_WARLOCK (2, 0, DEMON_FILE),
      LESSER_DEMON_BRUTE   (3, 0, DEMON_FILE),
      GREATER_DEMON        (4, 0, DEMON_FILE),
      DEMON_BRUTE          (5, 0, DEMON_FILE),
      DEMON_WATCHER        (6, 0, DEMON_FILE),
      DEMON_WORM           (7, 0, DEMON_FILE),
      AMORPHOUS_DEMON      (8, 0, DEMON_FILE),
      DRAGON               (9, 0, DEMON_FILE),

      // animal
      RAT                  (0, 0, ANIMAL_FILE),
      SPIDER               (1, 0, ANIMAL_FILE),
      BAT                  (2, 0, ANIMAL_FILE),
      WOLF                 (3, 0, ANIMAL_FILE),
      SCORPION             (4, 0, ANIMAL_FILE);
      
      public int[][] sheetLocation;
      public String fileName;
      
      private CreatureSprite(int x, int y, String fn)
      {
         sheetLocation = new int[2][2];
         sheetLocation[0][0] = x;
         sheetLocation[0][1] = y;
         sheetLocation[1][0] = x;
         sheetLocation[1][1] = y + 1;
         fileName = fn;
      }
   }
}