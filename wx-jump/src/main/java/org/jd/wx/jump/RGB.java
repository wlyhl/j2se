package org.jd.wx.jump;

import java.awt.*;

public class RGB{
	private int rMax,rMin,gMax,gMin,bMax,bMin;
	public int r,g,b;
	public RGB(int rgb){
		this(new Color(rgb));
	}
	private RGB(Color c){
		this(c.getRed(),c.getGreen(),c.getBlue());
	}
	public RGB(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	public RGB setDevition(int rDevition,int gDevition,int bDevition){
		rMax=r+rDevition;
		rMin=r-rDevition;
		gMax=g+gDevition;
		gMin=g-gDevition;
		bMax=b+bDevition;
		bMin=b-bDevition;
		return this;
	}
	public RGB setDevition(int d){
		return setDevition(d, d, d);
	}
	public boolean like(int rgb){
		RGB c=new RGB(rgb);
		return c.r>=rMin && c.r<=rMax
				&& c.g>=gMin && c.g<=gMax
				&& c.b>=bMin && c.b<=bMax;
	}
	@Override
	public String toString() {
		return "[r=" + r + ", g=" + g + ", b=" + b + "]";
	}
	
}
