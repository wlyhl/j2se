package org.jd.image.find;

import java.awt.*;

/**
 * 用于模糊比较
 */
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
	public RGB setDeviation(int rDeviation,int gDeviation,int bDeviation){
		rMax=r+rDeviation;
		rMin=r-rDeviation;
		gMax=g+gDeviation;
		gMin=g-gDeviation;
		bMax=b+bDeviation;
		bMin=b-bDeviation;
		return this;
	}
	public RGB setDeviation(int d){
		return setDeviation(d, d, d);
	}
	public boolean like(int rgb){
		RGB c=new RGB(rgb);
		return c.r>=rMin && c.r<=rMax
				&& c.g>=gMin && c.g<=gMax
				&& c.b>=bMin && c.b<=bMax;
	}

	/**
	 * RGB三色差之和
	 * @param rgb
	 */
	public int sumDeviation(int rgb){
		RGB c=new RGB(rgb);
		return Math.abs(r-c.r)+Math.abs(g-c.g)+Math.abs(b-c.b);
	}
	@Override
	public String toString() {
		return "[r=" + r + ", g=" + g + ", b=" + b + "]";
	}
	
}
