package Errant.GUI;

import Errant.Tools.*;
import java.awt.*;
import java.awt.image.*;

public class PlayerSpriteFactory implements GUIConstants
{
	private static final Color[] BASE_PRIMARY_COLOR = ORANGE_GROUP;
	private static final Color[] BASE_SECONDARY_COLOR = LIGHT_BLUE_GROUP;
	private static final Color[] BASE_HAIR_COLOR = BROWN_GROUP;
	private static final Color[] BASE_MAIN_HAND_COLOR = GREY_GROUP;
	private static final Color[] BASE_OFF_HAND_COLOR = GREY_GROUP;
   
	private static PaperDollHead headType = PaperDollHead.SHORT_HAIR;
	private static PaperDollBody bodyType = PaperDollBody.CLOTHES;
	private static PaperDollMainHand mainHandType = PaperDollMainHand.NOTHING;
	private static PaperDollOffHand offHandType = PaperDollOffHand.NOTHING;
	private static Color[] primaryColor = ORANGE_GROUP;
	private static Color[] secondaryColor = LIGHT_BLUE_GROUP;
	private static Color[] hairColor = BROWN_GROUP;
	private static Color[] mainHandColor = GREY_GROUP;
	private static Color[] offHandColor = GREY_GROUP;
   private static BufferedImage[] strip0 = null;
   private static BufferedImage[] strip1 = null;


	public static PaperDollHead getHeadType(){return headType;}
	public static PaperDollBody getBodyType(){return bodyType;}
	public static PaperDollMainHand getMainHandType(){return mainHandType;}
	public static PaperDollOffHand getOffHandType(){return offHandType;}
	public static Color[] getPrimaryColor(){return primaryColor;}
	public static Color[] getSecondaryColor(){return secondaryColor;}
	public static Color[] getHairColor(){return hairColor;}
	public static Color[] getMainHandColor(){return mainHandColor;}
	public static Color[] getOffHandColor(){return offHandColor;}


	public static void setHeadType(PaperDollHead h){headType = h;}
	public static void setBodyType(PaperDollBody b){bodyType = b;}
	public static void setMainHandType(PaperDollMainHand m){mainHandType = m;}
	public static void setOffHandType(PaperDollOffHand o){offHandType = o;}
	public static void setPrimaryColor(Color[] p){primaryColor = p;}
	public static void setSecondaryColor(Color[] s){secondaryColor = s;}
	public static void setHairColor(Color[] h){hairColor = h;}
	public static void setMainHandColor(Color[] m){mainHandColor = m;}
	public static void setOffHandColor(Color[] o){offHandColor = o;}


}