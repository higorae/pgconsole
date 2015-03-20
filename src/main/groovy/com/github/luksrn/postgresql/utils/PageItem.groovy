package com.github.luksrn.postgresql.utils

class PageItem {
	
	int number;
	boolean current;
	
	PageItem(int number, boolean current){
		this.number = number;
		this.current = current;
	}
	
	int getNumber(){
		return this.number;
	}
	
	boolean isCurrent(){
		return this.current;
	}
}