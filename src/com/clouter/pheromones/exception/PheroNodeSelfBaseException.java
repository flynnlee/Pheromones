package com.clouter.pheromones.exception;

public class PheroNodeSelfBaseException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PheroNodeSelfBaseException(String pheroNodeAlias){
		super("PheroNode alias equals with " + pheroNodeAlias);
	}
}
