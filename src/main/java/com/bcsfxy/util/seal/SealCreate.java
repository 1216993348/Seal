package com.bcsfxy.util.seal;

import java.awt.Color;

import com.bcsfxy.util.seal.config.SealCircle;
import com.bcsfxy.util.seal.config.SealConfiguration;
import com.bcsfxy.util.seal.config.SealFont;

public class SealCreate {
	public static void doCreateOfficialSeal (boolean isCircle
			,boolean isInnerEdge
			,boolean isInnerRing
			,String sMainFont
			,String sViceFont
			,String sCenterFont
			,String sTitleFont
			,String path)throws Exception {
		//初始化参数
		
		if(sMainFont == null) {
			sMainFont = "";
		}
		if(sViceFont == null) {
			sViceFont = "";
		}
		if(sCenterFont == null) {
			sCenterFont = "";
		}
		if(sTitleFont == null) {
			sTitleFont = "";
		}
		 /**
         * 印章配置文件
         */
        SealConfiguration configuration = new SealConfiguration();
        
        /**
         * 主文字
         */
        SealFont mainFont = new SealFont();
        mainFont.setBold(true);
        mainFont.setFontFamily("楷体");
        mainFont.setMarginSize(10);


        mainFont.setFontText(sMainFont);

        /**
         * 副文字
         */
        SealFont viceFont = new SealFont();
        viceFont.setBold(true);
        viceFont.setFontFamily("宋体");
        viceFont.setMarginSize(5);
        viceFont.setFontText(sViceFont);


        /**
         * 中心文字
         */
        SealFont centerFont = new SealFont();
        centerFont.setBold(true);
        centerFont.setFontFamily("宋体");
        centerFont.setFontText(sCenterFont);
        /**
         * 抬头文字
         */
        SealFont titleFont = new SealFont();
        titleFont.setBold(true);
        titleFont.setFontFamily("宋体");
        titleFont.setFontSize(22);
        titleFont.setFontText(sTitleFont);

        /**
         * 添加主文字
         */
        configuration.setMainFont(mainFont);
        /**
         * 添加副文字
         */
        configuration.setViceFont(viceFont);
        /**
         * 添加中心文字
         */
        configuration.setCenterFont(centerFont);
        /**
         * 添加抬头文字
         */
        configuration.setTitleFont(titleFont);

        /**
         * 图片大小
         */
        configuration.setImageSize(300);
        /**
         * 背景颜色
         */
        configuration.setBackgroudColor(Color.RED);
        int imageSize = configuration.getImageSize();
        /**
         * 边线粗细、半径
         */
        if(isCircle) {//如果是圆
        	//设置圆形
        	configuration.setBorderCircle(new SealCircle(6*imageSize/300, 140*imageSize/300, 140*imageSize/300));
        	//主标题大小
        	mainFont.setFontSize(35*imageSize/300);
            mainFont.setFontSpace(35.0*imageSize/300);
            //副标题大小
            viceFont.setFontSize(13*imageSize/300);
            viceFont.setFontSpace(12.0*imageSize/300);
            //设置抬头文字大小
            titleFont.setMarginSize(68*imageSize/300);
            titleFont.setFontSpace(10.0*imageSize/300);
            
            
        	if(isInnerEdge && isInnerRing) {//如果有圆内边且有圆内环
        		//添加内边、内环
        		configuration.setBorderInnerCircle(new SealCircle(1*imageSize/300, 132*imageSize/300, 135*imageSize/300));
        		configuration.setInnerCircle(new SealCircle(2*imageSize/300, 105*imageSize/300, 105*imageSize/300));
        		//更新主标题字体大小、边距
                mainFont.setFontSize(20*imageSize/300);
                mainFont.setFontSpace(15.0*imageSize/300);
                //设置中心文字大小
                centerFont.setFontSize(20*imageSize/300);
        	}else {//如果没有内环和内边则判断中心文字
        		if(sCenterFont == null || "".equals(sCenterFont)) {
        			centerFont.setFontText("★");
        		}else {
        			centerFont.setFontText(sCenterFont);
        		}
        		//设置中心文字大小
                centerFont.setFontSize(100*imageSize/300);
        	}
        	
        }else {//否则是椭圆
        	//设置椭圆形
        	configuration.setBorderCircle(new SealCircle(5*imageSize/300, 140*imageSize/300, 100*imageSize/300));
        	//主标题大小
            mainFont.setFontSize(25*imageSize/300);
            mainFont.setFontSpace(12.0*imageSize/300);
            //副标题大小
            viceFont.setFontSize(22*imageSize/300);
            viceFont.setFontSpace(12.0*imageSize/300);
            //设置中心文字大小
            centerFont.setFontSize(20*imageSize/300);
            //设置抬头文字大小
            titleFont.setMarginSize(27*imageSize/300);
        	if(isInnerEdge) {//如果椭圆有内边
        		configuration.setBorderInnerCircle(new SealCircle(1*imageSize/300, 133*imageSize/300, 95*imageSize/300));
        	}
        	if(isInnerRing) {//如果椭圆有内环则一定有内边
        		configuration.setInnerCircle(new SealCircle(2*imageSize/300, 85*imageSize/300, 45*imageSize/300));
        		configuration.setBorderInnerCircle(new SealCircle(1*imageSize/300, 133*imageSize/300, 95*imageSize/300));
        		//再次更新中心文字大小
        		 centerFont.setFontSize(25*imageSize/300);
        	}
        }
       
        
    	//1.生成公章
        SealUtil.buildAndStoreSeal(configuration, path);
        
	}
	public static void seal_zero(String mainTitle,String path)throws Exception {
		doCreateOfficialSeal(true, false, false, mainTitle, null, null, null, path);//公章0
	}
	public static void seal_one(String mainTitle,String viceTitle,String path)throws Exception {
		doCreateOfficialSeal(true, false, false, mainTitle, viceTitle, null, null, path);//公章1
	}
	public static void seal_two(String mainTitle,String topTitle,String path)throws Exception {
		doCreateOfficialSeal(true, false, false, mainTitle, null,null, topTitle, path);//公章2
	}
	public static void seal_three(String mainTitle,String centerTitle,String path)throws Exception {
		if(!centerTitle.contains("\n")) {
			centerTitle = SplitTitleString.lineFeedString(centerTitle);
		}
		doCreateOfficialSeal(true, true, true, mainTitle, null, centerTitle, null, path);//公章3
	}
	public static void seal_four(String mainTitle,String centerTitle,String topTitle,String path)throws Exception {
		doCreateOfficialSeal(false, false, false, mainTitle, null, centerTitle,topTitle, path);//公章4
	}
	public static void seal_five(String mainTitle,String viceTitle,String centerTitle,String path)throws Exception {
		doCreateOfficialSeal(false, true, false, mainTitle, viceTitle, centerTitle, null, path);//公章5
	}
	public static void seal_sex(String mainTitle,String viceTitle,String centerTitle,String path)throws Exception {
		doCreateOfficialSeal(false, true, true, mainTitle, viceTitle, centerTitle, null, path);//公章6
	}
	public static void main(String[] args) throws Exception{
		//String path = "C:\\Users\\Administrator\\Desktop\\SealUtil\\img\\seal-0.png";
		//1.生成公章
		/*doCreateOfficialSeal(true, false, false, "北京XXX科技有限公司", null, null, null, path);//公章0
		path = "C:\\Users\\Administrator\\Desktop\\SealUtil\\img\\seal-1.png";
		doCreateOfficialSeal(true, false, false, "北京XXX科技有限公司", "1231616556", null, null, path);//公章1
		path = "C:\\Users\\Administrator\\Desktop\\SealUtil\\img\\seal-2.png";
		doCreateOfficialSeal(true, false, false, "北京XXX科技有限公司", "1231616556", null, "发货专用", path);//公章2
		path = "C:\\Users\\Administrator\\Desktop\\SealUtil\\img\\seal-3.png";
		String centerTitile =SplitString.lineFeedString("北京XXX科技有限公司生产专用章");
		doCreateOfficialSeal(true, true, true, "XXXXXXXXX XX.XXXXXXXXX XX  XXXXXXXXXX", null, centerTitile, null, path);//公章3
		path = "C:\\Users\\Administrator\\Desktop\\SealUtil\\img\\seal-4.png";
		doCreateOfficialSeal(false, false, false, "北京XXX科技有限公司", null, "1231616556", "正版认证", path);//公章4
		path = "C:\\Users\\Administrator\\Desktop\\SealUtil\\img\\seal-5.png";
		doCreateOfficialSeal(false, true, false, "北京XXX科技有限公司", "正版认证", "1231616556", null, path);//公章5
		path = "C:\\Users\\Administrator\\Desktop\\SealUtil\\img\\seal-6.png";
		doCreateOfficialSeal(false, true, true, "北京XXX科技有限公司", "正版认证", "发票专用", null, path);//公章6
		*/
		//2.生成私章
       // SealFont font = new SealFont();
		// font.setFontSize(120).setBold(true).setFontText("诸和");
		//SealUtil.buildAndStorePersonSeal(300, 16, font, "印", "C:\\Users\\Administrator\\Desktop\\SealUtil\\img\\私章.png");
		
	}
}
