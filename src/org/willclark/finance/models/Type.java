package org.willclark.finance.models;

public enum Type {

	DEBIT("Debit"), CREDIT("Credit"), TRANSFER("Transfer");
	
	private String display;
	
	private Type(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return display;
	}
	
}
