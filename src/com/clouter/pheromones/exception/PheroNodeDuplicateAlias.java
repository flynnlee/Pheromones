package com.clouter.pheromones.exception;

public class PheroNodeDuplicateAlias extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PheroNodeDuplicateAlias(String pheroNodeAlias){
		super("duplicate pheroNode alias " + pheroNodeAlias);
	}
}
