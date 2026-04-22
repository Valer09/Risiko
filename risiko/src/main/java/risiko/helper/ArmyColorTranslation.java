package risiko.helper;

import risiko.model.ArmyColor;

public class ArmyColorTranslation
{
    public static String getItalianColor(ArmyColor armyColor)
    {
        return switch (armyColor)
        {
            case Red -> "Rosso";
            case Blue -> "Blue";
            case Green -> "Verde";
            case White -> "Bianco";
            case Yellow -> "Giallo";
            case Purple -> "Viola";
        };
    }

    public static String getItalianColorLabel(ArmyColor armyColor)
    {
        return String.format("[%c]", GetFirstChar(armyColor));
    }

    public static char GetFirstChar(ArmyColor color)
    {
        return switch (color)
        {
            case Red -> 'R';
            case Blue -> 'B';
            case Green -> 'V';
            case White -> 'W';
            case Yellow -> 'G';
            case Purple -> 'P';
        };
    }

    public static ArmyColor getColorFromFirstChar(char armyColor)
    {
        for (ArmyColor armyColor1 : ArmyColor.values())
            if(Character.toUpperCase(armyColor) == Character.toUpperCase(GetFirstChar(armyColor1))) return armyColor1;

        throw new IllegalArgumentException("Invalid army color: " + armyColor);
    }
}
