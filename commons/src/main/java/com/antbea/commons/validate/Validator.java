package com.antbea.commons.validate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class Validator {
	public static final String NOTNULL = "notnull";
	public static final String NOTBLANK = "notblank";
	public static final String INT = "int";
	public static final String DECIMAL = "decimal";
	public static final String MAX_LEN = "maxLen";
	public static final String MIN_LEN = "minLen";
	public static final String MAX = "max";
	public static final String MIN = "min";
	public static final String RANGE_LEN = "rangeLen";
	public static final String RANGE_IN = "rangeIn";
	public static final String BETWEEN = "between";
	public static final String PATTERN = "pattern";

	protected static Object[] paraseParams(String str) {
		String startSymbol = "(";
		String endSymbol = ")";
		String separator = ",";

		str = str.trim();
		if (!str.startsWith(startSymbol) && !str.endsWith(endSymbol)) {
			throw new IllegalArgumentException("格式错误: " + str);
		}
		str = str.substring(1, str.length() - 1);
		List<Object> result = new ArrayList<Object>();
		for (String item : str.split(separator)) {
			item = item.trim();
			String stringSymbol = "'";
			if ((item.startsWith(stringSymbol) && !item.endsWith(stringSymbol)) //
					|| (!item.startsWith(stringSymbol) && item.endsWith(stringSymbol))) {
				throw new IllegalArgumentException("格式错误: " + item);
			}
			if (item.startsWith(stringSymbol) && item.endsWith(stringSymbol)) {
				// 是字符串
				result.add(item.substring(1, item.length() - 1));
				continue;
			}
			if (item.equals("true") || item.equals("false")) {
				// 是布尔值
				result.add(new Boolean(item));
			}
			try {
				result.add(new BigDecimal(item));
			} catch (Exception e) {
				throw new IllegalArgumentException("格式错误: " + item);
			}
		}
		return result.toArray();
	}
}
