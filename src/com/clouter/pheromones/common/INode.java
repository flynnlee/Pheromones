package com.clouter.pheromones.common;

public interface INode {
	public void write(InterfaceOutput output) throws Exception;
	public void read(InterfaceInput input) throws Exception;
}
