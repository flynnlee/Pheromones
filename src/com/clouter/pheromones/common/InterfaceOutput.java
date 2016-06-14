package com.clouter.pheromones.common;

public interface InterfaceOutput {
	public void writeUTF(String value) throws Exception;
	public void writeFloat(float value) throws Exception;
	public void writeDouble(double value) throws Exception;
	public void writeByte(byte value) throws Exception;
	public void writeByte(int value) throws Exception;
	public void writeShort(short value) throws Exception;
	public void writeShort(int value) throws Exception;
	public void writeInt(int value) throws Exception;
	public void writeLong(long value) throws Exception;
	public void writeBytes(byte[] bytes) throws Exception;
	
	public byte[] getBytes();
	
	public void close();
}
