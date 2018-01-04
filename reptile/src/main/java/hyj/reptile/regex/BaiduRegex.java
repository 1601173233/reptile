package hyj.reptile.regex;


public class BaiduRegex {
	//获取查询结果的代码
	public String getWebInfo(String msg){
		return msg.substring(msg.indexOf("<div class=\"result c-container \""),msg.indexOf("<div style=\"clear:both;height:0;\""));
	}
	
	//对数据进行分离
	public String[] getWebInfoArray(String msg){
		//去掉第一个<div class=\"result c-container \"
		msg = msg.replaceFirst("<div class=\"result c-container \"", "");
		msg += "<div class=\"result c-container \"";
		return msg.split("<div class=\"result c-container \"");
	}
	
	//获取网页信息部分的代码
	public String getWebInfoEvery(String msg){
		return msg.substring(msg.indexOf("<div class=\"f13\""),msg.indexOf("<a class=\"c-tip-icon\""));
	}
	
	//获取链接
	public String getWebInfoUrl(String msg){
		return Regex.regexString(msg, "href=\"[^\"]+").replace("href=\"","").replace(" ", "");
	}
	
	//获取链接地址
	public String getWebInfoUrlAddress(String msg){
		return Regex.regexString(msg, "style=\"text-decoration:none;\">[^<]+")
				    .replace("style=\"text-decoration:none;\">", "")
				    .replace("...&nbsp;", "")
				    .replace(" ", "");
	}
	
	//获取链接名字
	public String getWebInfoUrlName(String msg){
		String mMsg = "data-tools=\"{\"title\":\"";
		int indexStart = msg.indexOf(mMsg) + mMsg.length();
		int indexEnd   = msg.indexOf("\"", indexStart);
		
		return msg.substring(indexStart,indexEnd);
	}
	
}

