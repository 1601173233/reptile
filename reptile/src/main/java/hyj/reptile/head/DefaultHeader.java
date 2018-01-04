package hyj.reptile.head;
/**
 * 默认数据请求头
 * @author yyy
 *
 */
public class DefaultHeader {

	/**
	 * 创建图像请求头
	 * @param repType
	 * @return
	 */
	public static Header defaultHeader(){
		Header header = new Header();
		return header;
	}
	
	/**
	 * 创建默认图像请求头
	 * @param repType
	 * @return
	 */
	public static ImageHeader defaultImageHeader(String repType){
		ImageHeader header = new ImageHeader();
		
		// 连接超时
		header.setDoInput(true);
		header.setDoOutput(true);
		header.setConnectTimeout(3000);

		// 读取超时 --服务器响应比较慢,增大时间
		header.setReadTimeout(25000);
		header.setRequestMethod("GET");
		header.setAcceptLanguage("zh-cn");
		header.setContentType(repType);
		header.setUserAgent("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");
		
		return header;
	}
	
	/**
	 * 创建百度图像请求头
	 * @param repType
	 * @return
	 */
	public static ImageHeader baiduImageHeader(String url,String repType){
		ImageHeader header = new ImageHeader();
		
		// 连接超时
		header.setDoInput(true);
		header.setDoOutput(true);
		header.setConnectTimeout(25000);

		// 读取超时 --服务器响应比较慢,增大时间
		header.setReadTimeout(25000);
		header.setRequestMethod("GET");
		header.setAccept("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		header.setAcceptEncoding("gzip, deflate, sdch");
		header.setAcceptLanguage("zh-CN,zh;q=0.8");
		header.setContentType(repType);
		header.setCacheControl("max-age=0");
		header.setConnection("keep-alive");
		header.setReferer(url);
		header.setUserAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
		
		return header;
	}
	
	/**
	 * 创建豆瓣请求头
	 * @param repType
	 * @return
	 */
	public static Header douBanHeader(String url){
		Header header = defaultHeader();
		
		header.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		
		return header;
	}
	
	
}

