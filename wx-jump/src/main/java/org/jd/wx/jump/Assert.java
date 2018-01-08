package org.jd.wx.jump;

public class Assert {
	public static void isTrue(boolean b,Object... s){
		if(!b) thro(s);
	}
	public static void notNull(Object o,Object... s){
		if(o==null) thro(s);
	}
	public static void thro(Object... s){
		StringBuilder sb=new StringBuilder();
		for(Object ss:s)
			sb.append(ss);
		throw new RuntimeException(sb.toString());
	}
}
