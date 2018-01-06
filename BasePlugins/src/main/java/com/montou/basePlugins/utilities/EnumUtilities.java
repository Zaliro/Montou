package com.montou.basePlugins.utilities;

import java.util.Random;

public class EnumUtilities {
	
	private static Random random = new Random();

	public static <T extends Enum<?>> T randomValue(Class<T> clazz) {
		int x = random.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}
}
