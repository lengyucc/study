package com.antbea.commons.validate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class NumberValidator extends Validator {

	public static void main(String[] args) {
		new NumberValidator().validate("18.01", "年龄", "rangeIn(18,160)");
	}

	public static void validate(Object t, String name, String... args) {
		for (String arg : args) {
			arg = arg.trim();
			if (arg.startsWith(NOTNULL)) {
				validateNotNull(t, name);
			}
			if (arg.startsWith(INT)) {
				validateInt(t, name);
			}
			if (arg.startsWith(MAX)) {
				Object[] params = paraseParams(arg.substring(MAX.length(), arg.length()));
				if (0 == params.length || !(params[0] instanceof BigDecimal)) {
					throw new IllegalArgumentException("格式错误: " + arg);
				}
				validateMax(t, name, (BigDecimal) params[0]);
			}
			if (arg.startsWith(MIN)) {
				Object[] params = paraseParams(arg.substring(MIN.length(), arg.length()));
				if (0 == params.length || !(params[0] instanceof BigDecimal)) {
					throw new IllegalArgumentException("格式错误: " + arg);
				}
				validateMin(t, name, (BigDecimal) params[0]);
			}
			if (arg.startsWith(BETWEEN)) {
				Object[] params = paraseParams(arg.substring(BETWEEN.length(), arg.length()));
				if (0 == params.length || !(params[0] instanceof BigDecimal) || !(params[1] instanceof BigDecimal)) {
					throw new IllegalArgumentException("格式错误: " + arg);
				}
				validateBetween(t, name, (BigDecimal) params[0], (BigDecimal) params[1]);
			}
			if (arg.startsWith(RANGE_IN)) {
				Object[] params = paraseParams(arg.substring(RANGE_IN.length(), arg.length()));
				validateRangeIn(t, name, params);
			}
		}
	}

	private static void validateNotNull(Object t, String name) {
		if (null == t) {
			throw new ValidateException(name + "不允许为空");
		}
	}

	private static void validateInt(Object t, String name) {
		validateNotNull(t, name);
		BigDecimal n = convert2BigDecimal(t);
		if (0 != n.scale()) {
			throw new ValidateException(name + "不是一个整数");
		}
	}

	private static void validateMax(Object t, String name, BigDecimal max) {
		validateNotNull(t, name);

		BigDecimal n = convert2BigDecimal(t);
		if (1 == n.compareTo(max)) {
			throw new ValidateException(name + "不能大于" + max);
		}

	}

	private static void validateMin(Object t, String name, BigDecimal min) {
		validateNotNull(t, name);
		BigDecimal n = convert2BigDecimal(t);
		if (-1 == n.compareTo(min)) {
			throw new ValidateException(name + "不能小于" + min);
		}
	}

	private static void validateBetween(Object t, String name, BigDecimal min, BigDecimal max) {
		validateNotNull(t, name);
		BigDecimal n = convert2BigDecimal(t);
		if (-1 == n.compareTo(min) || 1 == n.compareTo(max)) {
			throw new ValidateException(name + "必须大于" + min + "且小于" + max);
		}
	}

	private static BigDecimal convert2BigDecimal(Object o) {
		BigDecimal n = null;

		if (o instanceof Number) {
			Number t = (Number) o;
			if (t instanceof Byte || t instanceof Short || t instanceof Integer || t instanceof Long) {
				n = new BigDecimal(t.longValue());
			}

			if (t instanceof Double || t instanceof Float) {
				n = new BigDecimal(t.doubleValue());
			}

			if (t instanceof BigInteger) {
				n = new BigDecimal((BigInteger) t);
			}

			if (t instanceof AtomicInteger) {
				n = new BigDecimal(((AtomicInteger) t).get());
			}

			if (t instanceof AtomicLong) {
				n = new BigDecimal(((AtomicLong) t).get());
			}

			if (t instanceof BigDecimal) {
				n = (BigDecimal) t;
			}
		} else if (o instanceof String) {
			try {
				n = new BigDecimal((String) o);
			} catch (Exception e) {
				throw new IllegalArgumentException("格式错误: " + o);
			}
		} else {
			throw new IllegalArgumentException("不支持的类型: " + o.getClass().getName());
		}

		return n;
	}

	private static void validateRangeIn(Object t, String name, Object[] params) {
		validateNotNull(t, name);
		if (0 == params.length) {
			throw new IllegalArgumentException("格式错误: " + t);
		}
		BigDecimal n = convert2BigDecimal(t);
		boolean success = false;
		StringBuffer text = new StringBuffer();
		for (Object param : params) {
			if (!(param instanceof BigDecimal)) {
				throw new IllegalArgumentException("格式错误: " + t);
			}
			BigDecimal p = (BigDecimal) param;
			if (0 == n.compareTo(p)) {
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
