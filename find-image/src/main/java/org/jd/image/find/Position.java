package org.jd.image.find;

public class Position {
	public int x,y;

	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}
	
}
