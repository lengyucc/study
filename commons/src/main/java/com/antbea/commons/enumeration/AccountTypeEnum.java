package com.antbea.commons.enumeration;


public enum AccountTypeEnum {
	SUPER_ACCOUNT(1, "超级账户"), MANAGE_ACCOUNT(2, "普通账户"), GENERAL_ACCOUNT(3, "普通账户");
	private final Object code;
	private final Object desc;

	private AccountTypeEnum(Object code, Object desc) {
		this.code = code;
		this.desc = desc;
	}

	public Object getCode() {
		return code;
	}

	public Object getDesc() {
		return desc;
	}

	// 重写toString
	@Override
	public String toString() {
		return String.valueOf(desc);
	}

	/**
	 * 比较当前枚举的code是否与传入的code相等
	 */
	public boolean equalTo(Object code) {
		return this.code.equals(code);
	}

	/**
	 * 判断是否存在code对应的枚举
	 */
	public static boolean contains(Object code) {
		for (AccountTypeEnum item : AccountTypeEnum.values()) {
			if (item.equalTo(code))
				return true;
		}
		return false;
	}

	/**
	 * 返回code对应的枚举
	 */
	public static AccountTypeEnum valueOf(Object code) {
		for (AccountTypeEnum item : AccountTypeEnum.values()) {
			if (item.equalTo(code))
				return item;
		}
		return null;
	}
}
