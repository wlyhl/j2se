package org.jd.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class StringUtil {
	public static final String[] split(String s,String separator){
		String [] res=new String[1];
		return res;
	}
	/**
	 * 高效得到split后第index个子串,如果没找到就返回最后一个
	 * String s="a,,c,d"
	 * splitAndGet(s, "," ,0)得到 "a"
	 * splitAndGet(s, "," ,2)得到 "c"
	 * splitAndGet(s, "," ,100)得到 "d"
	 * @param s 原字符串
	 * @param separator 分割器
	 * @param index 必须大于-1
	 */
	public static String splitAndGet(String s,String separator,int index){
		int i=0;//永远指向某个分割器的下一个子串
		final int spLength=separator.length();
		while(index-->0){
			int next=s.indexOf(separator,i);//分割器第一个字符的索引
			if(next<0)//找不到了，返回最后一个
				return s.substring(i);
			i=next+spLength;//分割器最后一个字符的索引+1
		}
		int end=s.indexOf(separator,i);
		return subString(s,i,end);
	}
	/**
	 * String s="a,,c,d"
	 * splitAndGet(s, "," ,0,1,50)得到 ["a","","d"]
	 * @param s 原字符串
	 * @param separator 分割器
	 * @param indices 必须大于-1，必须从小到大排好序
	 * @return
	 */
	public static String[] splitAndGet(String s,String separator,int... indices){
		int i=0;//永远指向某个分割器的下一个子串
		String[] res=new String[indices.length];//结果集
		final int spLength=separator.length();
		int num=0;//目前找到的是第几个子串
		for(int p=0;num<res.length;p++){//循环一次找到一个子串
			int next=s.indexOf(separator,i);
			if(p == indices[num])
				res[num++]=subString(s,i,next);
			if(next<0) break;
			i=next+spLength;
		}
		return res;
	}
	private static String subString(String s,int begin,int end){
		return end<0?s.substring(begin):s.substring(begin, end);
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		String s="734235abab";
//		for(int i=0;i<6;i++){
//			System.out.println(splitAndGet(s, "ab", i));
//		}
		System.out.println(Arrays.toString(splitAndGet(s, "ab",0,1,2,3,4 )));
//		System.out.println("abc".substring(1, 2));
//		System.out.println(Arrays.toString("  ".getBytes("unicode")));
	}
}
