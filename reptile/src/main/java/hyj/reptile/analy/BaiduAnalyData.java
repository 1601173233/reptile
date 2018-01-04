package hyj.reptile.analy;

import hyj.reptile.head.CookiesManage;
import hyj.reptile.head.DefaultHeader;
import hyj.reptile.head.ImageHeader;
import hyj.reptile.regex.BaiduRegex;
import hyj.reptile.util.HttpGet;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 分析数据
 * @author huangyujie
 *
 */
public class BaiduAnalyData{
	/**
	 * 记录指定页面的所有链接
	 * @throws IOException 
	 */
	public static void getUrlWeb(Connection conn, String url) throws IOException{
		//定义数据连接对象
		HttpGet httpGet = new HttpGet();
		
		//获取指定页面的图片
		String result = httpGet.getString(url);
		
		//获取链接信息
		BaiduRegex baiduRegex = new BaiduRegex();
		String msg = baiduRegex.getWebInfo(result);
		String msgArray[] = baiduRegex.getWebInfoArray(msg);
		
		for(String ss : msgArray){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("ADDRESS", 	baiduRegex.getWebInfoUrlName(ss));		//链接名
			dataMap.put("NAME", 	baiduRegex.getWebInfoUrlAddress(ss));	//链接地址
			dataMap.put("URL", 		baiduRegex.getWebInfoUrl(ss));			//链接
//			saveWebInfo(conn, dataMap);
		}
	}
	
	/**
	 * 保存网页查询结果
	 */
	public static void saveWebInfo(){
		
	}

	/**
	 * 获取指定页面的所有图片
	 * @throws IOException 
	 */
	public static void getUrlPic(String url, String fileDir, int pageSize) throws IOException{
		String picType = "jpg|png|gif";
		
		//获取指定页面的图片
		getUrlPic(url, fileDir, picType, pageSize);
	}

	/**
	 * 获取指定页面的所有图片
	 * @throws IOException 
	 */
	public static void getUrlPicByWord(String word, String fileDir, int pageSize) throws IOException{
		String url = "https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&sf=2&fmq=1480332039000_R_D&pv=&ic=0&nc=1&z=&se=&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word="
			+ word;
		
		//获取指定页面的图片
		getUrlPic(url, fileDir, pageSize);
	}
	
	/**
	 * 获取指定页面的所有图片
	 * @throws IOException 
	 */
	public static void getUrlPic(String url,String fileDir,String picType) throws IOException{
		//定义数据连接对象
		HttpGet httpGet = new HttpGet();

		// 访问链接并获取页面内容
		Map<String,String> dataMap = httpGet.getStringAndCookies(url);
		String data    = dataMap.get("DATA");		//页面数据
		String cookies = dataMap.get("COOKIES");	//请求的Cookies
		
		//百度的请求头
		ImageHeader header = new DefaultHeader().baiduImageHeader(url, picType);
		
		//保存Cookies
		try {
			CookiesManage.storeCookies(new URI(url), cookies);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ImageAnalyData.getHtmlPic(data, fileDir, picType, header);
		
	}
	
	/**
	 * 获取指定页面的所有图片
	 * @throws IOException 
	 */
	public static void getUrlPic(String url,String fileDir,String picType, int page) throws IOException{
		//定义数据连接对象
		HttpGet httpGet = new HttpGet();
		
		//获取首页的数据
		getUrlPic(url, fileDir + 0 + "p", picType);
		
		//获取查询的数据
		String word = url.substring(url.lastIndexOf("word=") + 5,url.length());
		
		//分页获取数据
		for(int i = 1 ;i < page; i++){
			String murl = "https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&ct=201326592&is=&fp=result" +
					"&queryWord=" + word + "&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&z=&ic=0" +
					"&word=" + word + "&s=&se=&tab=&width=&height=&face=0&istype=2&qc=&nc=1&fr=&pn=" + i *30 +
					"&rn=30&gsm=1000000001e&"+ (new Date()).getTime() +"=";
			
			String data = httpGet.getString(murl);

			//获取该页的数据
			ImageAnalyData.getHtmlPic(data, fileDir + i + "p", picType);
		}
	}
	
	
}

