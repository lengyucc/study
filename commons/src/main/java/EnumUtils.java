

import java.lang.reflect.Field;

public final class EnumUtils {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String toString(String className, Object code, String fieldName) throws ClassNotFoundException,
			NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class clazz = Class.forName(className);
		if (!clazz.isEnum()) {
			throw new IllegalArgumentException(clazz + " is not a enum. ");
		}
		return toString(clazz, code, fieldName);
	}

	public static String toString(Class<? extends Enum<?>> clazz, Object code, String fieldName)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		Object[] objs = clazz.getEnumConstants();
		if (objs != null && objs.length > 0) {
			for (Object obj : objs) {
				Object _code = field.get(obj);
				if (_code.equals(code)) {
					return obj.toString();
				}
			}
		}
		return null;
	}

	public static boolean contains(Class<? extends Enum<?>> clazz, Object code, String fieldName)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		Enum<?>[] enums = clazz.getEnumConstants();
		if (enums != null && enums.length > 0) {
			for (Object obj : enums) {
				Object _code = field.get(obj);
				if (_code.equals(code)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, ClassNotFoundException,
			IllegalArgumentException, IllegalAccessException {
		System.out.println(contains(AccountTypeEnum.class, 0, "code"));
	}
}
