package com.antbea.commons.validate;

import java.math.BigDecimal;
import java.math.BigInteger;

public class StringValidator extends Validator {

	public static void main(String[] args) {
		validate("1222", "姓名", "decimal");
		
	}

	public static void validate(String t, String name, String... args) {
		for (String arg : args) {
			arg = arg.trim();
			if (arg.startsWith(NOTNULL)) {
				validateNotNull(t, name);
			}
			if (arg.startsWith(NOTBLANK)) {
				validateNotBlank(t, name);
			}
			if (arg.startsWith(INT)) {
				validateInt(t, name);
			}
			if (arg.startsWith(DECIMAL)) {
				validateDecimal(t, name);
			}
			if (arg.startsWith(MAX_LEN)) {
				Object[] params = paraseParams(arg.substring(MAX_LEN.length(), arg.length()));
				if (0 == params.length || !(params[0] instanceof BigDecimal)) {
					throw new IllegalArgumentException("格式错误: " + arg);
				}
				int maxLen = ((BigDecimal) params[0]).intValue();
				validateMaxLen(t, name, maxLen);
			}
			if (arg.startsWith(MIN_LEN)) {
				Object[] params = paraseParams(arg.substring(MIN_LEN.length(), arg.length()));
				if (0 == params.length || !(params[0] instanceof BigDecimal)) {
					throw new IllegalArgumentException("格式错误: " + arg);
				}
				int minLen = ((BigDecimal) params[0]).intValue();
				validateMinLen(t, name, minLen);
			}
			if (arg.startsWith(RANGE_LEN)) {
				Object[] params = paraseParams(arg.substring(RANGE_LEN.length(), arg.length()));
				if (0 == params.length || !(params[0] instanceof BigDecimal) || !(params[1] instanceof BigDecimal)) {
					throw new IllegalArgumentException("格式错误: " + arg);
				}
				int minLen = ((BigDecimal) params[0]).intValue();
				int maxLen = ((BigDecimal) params[1]).intValue();
				validateRangeLen(t, name, minLen, maxLen);
			}
			if (arg.startsWith(RANGE_IN)) {
				Object[] params = paraseParams(arg.substring(RANGE_IN.length(), arg.length()));
				validateRangeIn(t, name, params);
			}
			if (arg.startsWith(PATTERN)) {
				Object[] params = paraseParams(arg.substring(PATTERN.length(), arg.length()));
				if (0 == params.length || !(params[0] instanceof String)) {
					throw new IllegalArgumentException("格式错误: " + arg);
				}
				validatePattern(t, name, (String) params[0]);
			}
		}
	}

	private static void validatePattern(String t, String name, String regex) {
		validateNotNull(t, name);
		if (!t.matches(regex)) {
			throw new ValidateException(name + "格式不正确");
		}
	}

	private static void validateNotNull(String t, String name) {
		if (null == t) {
			throw new ValidateException(name + "不允许为空");
		}
	}

	private static void validateNotBlank(String t, String name) {
		validateNotNull(t, name);
		if (t.trim().isEmpty()) {
			throw new ValidateException(name + "不允许为空");
		}
	}

	private static void validateInt(String t, String name) {
		validateNotBlank(t, name);
		try {
			new BigInteger(t);
		} catch (Exception e) {
			throw new ValidateException(name + "不是一个整数");
		}
	}

	private static void validateDecimal(String t, String name) {
		validateNotBlank(t, name);
		try {
			new BigDecimal(t);
		} catch (Exception e) {
			throw new ValidateException(name + "不是一个数值");
		}
	}

	private static void validateMaxLen(String t, String name, int maxLen) {
		validateNotNull(t, name);
		if (t.length() > maxLen) {
			throw new ValidateException(name + "不能超过" + maxLen + "个长度");
		}
	}

	private static void validateMinLen(String t, String name, int minLen) {
		validateNotNull(t, name);
		if (t.length() < minLen) {
			throw new ValidateException(name + "不能小于" + minLen + "个长度");
		}
	}

	private static void validateRangeLen(String t, String name, int minLen, int maxLen) {
		validateNotNull(t, name);
		if (t.length() < minLen || t.length() > maxLen) {
			throw new ValidateException(name + "必须在" + minLen + "~" + maxLen + "个字符之间");
		}
	}

	private static void validateRangeIn(String t, String name, Object[] params) {
		validateNotNull(t, name);
		if (0 == params.length) {
			throw new IllegalArgumentException("格式错误: " + t);
		}
		boolean success = false;
		StringBuffer text = new StringBuffer();
		for (Object param : params) {
			if (!(param instanceof String)) {
				throw new IllegalArgumentException("格式错误: " + t);
			}
			String p = (String) param;
			if (t.equals(p)) {
				success = true;
			}
			text.append(",");
			text.append(p);
		}
		if (!success) {
			throw new ValidateException(name + "取值必须在(" + text.substring(1).toString() + ")之间");
		}
	}
}
