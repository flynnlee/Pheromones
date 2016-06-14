package com.clouter.pheromones.common;

import java.io.IOException;

public interface InterfaceInput {
	public String readUTF() throws IOException;
	public float readFloat() throws IOException;
	public double readDouble() throws IOException;
	public boolean readBoolean() throws IOException;
	public byte readByte() throws IOException;
	public short readShort() throws IOException;
	public int readInt() throws IOException;
	public long readLong() throws IOException;
	public void readFully(byte[] buffer) throws IOException;
	
	public void close();
}
