package com.hotel.reservation.utils;

public enum UserType {

	SUPER_ADMIN("super admin"), ADMIN("admin"), VISITOR("visitor");

	String type;

	UserType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
