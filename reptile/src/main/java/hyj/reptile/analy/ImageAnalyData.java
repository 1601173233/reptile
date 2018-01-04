package hyj.reptile.analy;

import hyj.reptile.IO.ImageFileIo;
import hyj.reptile.head.CookiesManage;
import hyj.reptile.head.DefaultHeader;
import hyj.reptile.head.Header;
import hyj.reptile.head.ImageHeader;
import hyj.reptile.util.GetUrl;
import hyj.reptile.util.HttpGet;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * 分析数据
 * @author huangyujie
 *
 */
public class ImageAnalyData {

	/**
	 * 获取指定页面的所有图片
	 * @throws IOException 
	 */
	public static void getUrlPic(String url,String fileDir) throws IOException{
		String picType = "jpg|png|gif";
		
		//获取指定页面的图片
		getUrlPic(url, fileDir, picType);
	}
	
	/**
	 * 获取指定页面的所有图片
	 * @throws IOException 
	 */
	public static void getUrlPic(String url,String fileDir,Header header) throws IOException{
		//获取指定页面的图片
		getUrlPic(url, fileDir, "jpg|png|gif", header);
	}
	
	/**
	 * 获取指定页面的所有图片
	 * @throws IOException 
	 */
	public static void getUrlPic(String url,String fileDir,String picType) throws IOException{
		//定义数据连接对象
		HttpGet httpGet = new HttpGet();
		
		Map<String,String> dataMap = httpGet.getStringAndCookies(url);

		// 访问链接并获取页面内容
		String data 	= dataMap.get("DATA");
		String cookies 	= dataMap.get("COOKIES");
		
		//保存Cookies
		try {
			CookiesManage.storeCookies(new URI(url), cookies);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		getHtmlPic(data, fileDir, picType);
		
	}
	
	/**
	 * 获取指定页面的所有图片
	 * @throws IOException 
	 */
	public static void getUrlPic(String url,String fileDir,String picType,Header header) throws IOException{
		//定义数据连接对象
		HttpGet httpGet = new HttpGet();

		// 访问链接并获取页面内容
		Map<String,String> dataMap = httpGet.getStringAndCookies(url);

		// 访问链接并获取页面内容
		String data 	= dataMap.get("DATA");
		String cookies 	= dataMap.get("COOKIES");
		
		//保存Cookies
		try {
			CookiesManage.storeCookies(new URI(url), cookies);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		getHtmlPic(data, fileDir, picType);
		
	}
	
	/**
	 * 获取页面的所有图片
	 */
	public static void getHtmlPic(String data,String fileDir,String picType){
		getHtmlPic(data, fileDir, picType,DefaultHeader.defaultImageHeader(picType));
	}
	
	/**
	 * 获取页面的所有图片
	 */
	public static void getHtmlPic(String data, String fileDir, String picType, ImageHeader header){
		//解析所有图片的url
		List<String> list = GetUrl.getUrlByType(data,picType);
		
		if(list.size() == 0){
			return ;
		}
		
		//循环请求url
		for(int i = 0 ;i < list.size();i++){
			String mUrl    = list.get(i);
			int lastIndex  = list.get(i).lastIndexOf(".");
			String mpicType = mUrl.substring(lastIndex + 1, mUrl.length());
			System.out.println(mUrl);

			//最多重试5次
			for(int j = 0;j < 5;j++){
				try{
					//记录到指定的文件夹
					ImageFileIo.downloadImg(mUrl, mpicType, fileDir + i + "." + mpicType, header);
				}catch (Exception e) {
					// TODO: handle exception
					if(j > 4)
						break;
					continue;
				}
				break;
			}
		}
		
	}
}

