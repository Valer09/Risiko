package risiko.helper;

public class InputTypeHelper
{
    public static boolean isInt(String s)
    {
        if(s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) if (!Character.isDigit(s.charAt(i))) return false;
        return true;
    }
}
