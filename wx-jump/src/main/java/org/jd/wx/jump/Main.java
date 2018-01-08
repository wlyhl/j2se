package org.jd.wx.jump;
import org.jd.wx.jump.shell.Shell;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Date;

public class Main {
	static String work="D:\\jump\\work\\";
//	static int bgColor=new Color(255,246,163).getRGB();
	public static void main(String[] args) throws Exception {

//		farFrom(new File("D:\\jump\\0.png"));

		start();

	}
	static void start()throws Exception{
		LocalDateTime time=LocalDateTime.now();
		String work=Main.work+time.getHour()+"."+time.getMinute()+"\\";
		File f=new File(work);
		f.mkdir();

		Shell shell=new Shell("cmd");
		for(int i=0;i<1000;i++){
			long l=new Date().getTime();
			InputStream in = shell.exe("adb shell screencap -p");
			BufferedImage img = ImageUtil.readFromADB(in);
			ImageUtil.writeTo(img,f.getAbsolutePath()+"\\"+i+".png");
			System.out.println("截屏耗时"+(new Date().getTime()-l));
			l=new Date().getTime();

			double farFrom = farFrom(img);
			double delay=715f/521f*farFrom;
			String s="距离"+toInt(farFrom)+"延时"+toInt(delay);
			System.out.println(s);
//			img.renameTo(new File(img.getAbsolutePath().replace(".png","")+s+".png"));
			shell.exe("adb shell input swipe 100 100 100 100 "+Double.valueOf(delay).intValue()+"");
			System.out.println("本次耗时"+(new Date().getTime()-l));
			Thread.sleep(3000);
		}
	}
	static double farFrom(BufferedImage i)throws IOException{
		long l=new Date().getTime();
		RGBCache rgb = new RGBCache(i);
		removeShandow(rgb);//背景色
//		ImageIO.write(img, "png", new File("D:\\jump\\img\\deal\\0.27.18.03.png"));
		TargetImg me=new TargetImg(ImageIO.read(Main.class.getClassLoader().getResourceAsStream("me.png")), 2);
		Position mePos = me.foundIn(rgb);
//		ImageIO.write(img, "png", new File("D:\\jump\\img\\deal\\0.27.18.03.png"));
		System.out.println("mePos"+mePos);
		Position platPos = findPlatform(mePos,rgb,me);
		System.out.println("platPos"+platPos);
		System.out.println("计算耗时"+(new Date().getTime()-l));
		return Math.sqrt(Math.pow(mePos.x-platPos.x, 2)+Math.pow(mePos.y-platPos.y, 2));
	}
	static Position findPlatform(Position mePos,RGBCache img,TargetImg me){
		Assert.isTrue(mePos.y>500, "mePos坐标y",mePos);
		Position top=findPlatformTop(img);
		System.out.println("PlatformTop"+top);
		boolean leftJump=mePos.x>top.x;
		for(int y=top.y,borderX=top.x,xMax=img.getWidth();y<1200;y++){
			int x=top.x;
			while(img.notBG((x+=leftJump?-1:1), y));
			if(leftJump?x>=borderX:x<=borderX)
				return new Position(top.x, y);
			borderX=x;
		}
		throw new RuntimeException("未找到平台中心坐标");
	}
	static Position findPlatformTop(RGBCache img){
		for(int y=500,xMax=img.getWidth();y<1200;y++)
			for(int x=0;x<xMax;x++){
				if(img.notBG(x, y)){
					int x2=x;
					while(img.notBG(++x2, y));
					return new Position((x+x2)/2, y);
				}
			}
		throw new RuntimeException("未找到平台顶点");
	}
	static void removeShandow(RGBCache img){
		long l=new Date().getTime();
		int right=img.getWidth()-1;
		for(int y=0,end=img.getHeight();y<end;y++){
			RGB rRgb=new RGB(img.getRGB(right, y)).setDevition(2);
			for(int x=0;x<=right;x++){
				if(rRgb.like(img.getRGB(x, y)))
					img.setBackGround(x,y);
			}
		}
		System.out.println("去阴影耗时"+(new Date().getTime()-l));
	}
	static int toInt(Number n){
		return n.intValue();
	}

}
