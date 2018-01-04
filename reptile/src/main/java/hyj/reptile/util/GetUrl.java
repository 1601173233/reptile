package hyj.reptile.util;

import hyj.reptile.regex.Regex;

import java.util.ArrayList;
import java.util.List;

/*
 * 获取指定后缀的链接
 */
public class GetUrl {
	//获取指定后缀的链接
	public static List<String> getUrlByType(String data,String Type){
		//解析所有图片的url
		List<String> list = Regex.regexList(data, "http[^\\s]?://[^\\s\\)\\'\\\"]*.(" + Type + ")",0);
		return list;
	}
	
	//百度图片
	public static List<String> getBaiduImageUrl(String data){
		//解析所有图片的url
		List<String> list = Regex.regexList(data, "http(s)?://image.baidu.com/search/index[^\\s\\)\\'\\\"]*\\\"",0);
		List<String> mlist = new ArrayList<String>();
		
		for(String ms:list){
			mlist.add(getBaiduImageUrlByWord(getWord(ms)));
		}
		
		list.clear();
		
		return mlist;
	}
	
	//构造网页查询地址
	public static String getBaiduWebUrlByWord(String word,String searchWeb){
		//用一条可以直接访问的地址直接构造
		StringBuilder stringBuffer = new StringBuilder(250);
		stringBuffer.append("https://www.baidu.com/s?q1=")
		            .append(word)
					.append("&q2=&q3=&q4=&gpc=stf&ft=&q5=&q6=")
		            .append(searchWeb)
					.append("download.csdn.net&tn=baiduadv");
		
		return stringBuffer.toString();
	}
	
	//构造图片查询地址
	public static String getBaiduImageUrlByWord(String word){
		//用一条可以直接访问的地址直接构造
		StringBuilder stringBuffer = new StringBuilder(250);
		stringBuffer.append("https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&sf=2&fmq=1480332039000_R_D&pv=&ic=0&nc=1&z=&se=&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=")
		            .append(word);
		
		return stringBuffer.toString();
	}
	
	//获取word
	public static String getWord(String url){
		int index = url.lastIndexOf("word=") + 5;
		String word = Regex.regexString(url.substring(index,url.length()), "[^\\s\\)\\&\\\"]*");
		
		return word;
	}
}

