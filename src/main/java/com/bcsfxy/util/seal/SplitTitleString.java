package com.bcsfxy.util.seal;

public class SplitTitleString {
	public static String []splitString(String str) {
		String[] result = new String[3];
		int rd = 0;
		int length = str.length();
		int x = length/3 <0 ?3: length/3;
		int index = 0;
		while((length) > 0) {
			x++;
			if((length - x) <=0) {
				result[rd++] = str.substring(index, index+length);
				index += length;
				break;
			}else {
				result[rd++] = str.substring(index, index+x);
				index += x;
			}
			length-=x;
		}
		return result;
	}
	public static String lineFeedString(String str) {
		StringBuffer result  =new StringBuffer();
		String [] data = splitString(str);
		for (int i = 0; i < data.length; i++) {
			if(data[i] != null) {
				if(i == data.length -1) {
					
					result.append(data[i]);
				}else {
					result.append(data[i]).append("\n");
				}
			}
		}
		return result.toString();
	}
}
