package net.ci010.minecraftUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntexHelper
{
	public static boolean isNumeric(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches())
		{
			return false;
		}
		return true;
	}
}
