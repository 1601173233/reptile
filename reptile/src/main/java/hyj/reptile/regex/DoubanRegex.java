package hyj.reptile.regex;

import hyj.reptile.util.StringToUnicode;

public class DoubanRegex {
	//获取书籍信息部分的代码
	public String getSjInfo(String msg){
		return msg.substring(msg.indexOf("<div class=\"info\""),msg.indexOf("<div class=\"paginator\""));
	}
	
	//对数据进行分离
	public String[] getSjInfoArray(String msg){
		//去掉第一个<div class=\"info\">
		msg = msg.replaceFirst("<div class=\"info\">", "");
		msg += "<div class=\"info\">";
		return msg.split("<div class=\"info\">");
	}
	
	//获取书名
	public String getBookName(String msg){
		return Regex.regexString(msg, "title=\"[^\"]+\"").replace("title=", "").replace("\"", "")
		     + Regex.regexString(msg, "<span style=\"font-size:12px;\">[^<]*").replace("<span style=\"font-size:12px;\">", "").replaceAll("Nothing", "");
	}
	
	//获取作者
	public String getAuthor(String msg){
		return Regex.regexString(msg, "<div class=\"pub\">[^<]+").replace("<div class=\"pub\">", "").replace(" ", "");
	}
	
	//获取出版日期
	public String getCbrq(String msg){
		String msgArray[] = msg.split("/");
		msg = msgArray[msgArray.length - 2];
		return Regex.regexString(msg, "[0-9\\-]*");
	}
	
	//获取评价分数
	public String getPjFs(String msg){
		return Regex.regexString(msg, "<span class=\"rating_nums\">[^<]+").replace("<span class=\"rating_nums\">", "").replace(" ", "");
	}
	
	//获取评价人数
	public String getPjRs(String msg){
		return Regex.regexString(msg, "\\([^\\)<"+StringToUnicode.stringToUnicode("人")+"]+"+StringToUnicode.stringToUnicode("人评价")+"\\)");
	}
}

