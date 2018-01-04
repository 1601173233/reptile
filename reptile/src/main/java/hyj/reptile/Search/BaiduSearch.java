package hyj.reptile.Search;

import hyj.reptile.analy.BaiduAnalyData;
import hyj.reptile.util.GetUrl;
import hyj.reptile.util.HttpGet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 百度搜索
 * @author yyy
 *
 */
public class BaiduSearch {
	/**
	 * 搜索指定网页是否有指定关键字的网页
	 * @param conn
	 * @param word
	 * @param webHome 搜索的网页
	 * @throws UnsupportedEncodingException 
	 */
	public void searchWebByWord(Connection conn, String word, String webHome) throws Exception{
		String mword = URLEncoder.encode(word, "utf-8");
		BaiduAnalyData.getUrlWeb(conn, GetUrl.getBaiduWebUrlByWord(mword, webHome));
	}
	
	/**
	 * 根据关键字检索图片
	 * @param word
	 * @param fileDir
	 * @throws Exception
	 */
	public void searchImageByWord(String word,String fileDir) throws Exception{
		this.searchImageByWord(word, fileDir, 0);
	}

	/**
	 * 根据关键字检索图片
	 * @param word
	 * @param fileDir
	 * @param pageSize
	 * @throws Exception
	 */
	public void searchImageByWord(String word, String fileDir, int pageSize) throws Exception{
		String mword = URLEncoder.encode(word, "utf-8");
		BaiduAnalyData.getUrlPic(GetUrl.getBaiduImageUrlByWord(mword), fileDir + word + "/", pageSize);
	}
	
	/**
	 * 检索百度图片首页
	 * @param word
	 * @param fileDir
	 * @throws Exception
	 */
	public void searchBaiduImageSy(String fileDir, int pageSize) throws UnsupportedEncodingException, IOException{
		//定义数据连接对象
		HttpGet httpGet = new HttpGet();
		
		// 定义即将访问的链接
		String url = "https://image.baidu.com/";
		
		// 访问链接并获取页面内容
		String result = httpGet.getString(url);
		
		List list = GetUrl.getBaiduImageUrl(result);
		
		Set<String> set = new HashSet<String>();
		set.addAll(list);
		
		list.clear();
		list.addAll(set);
		System.out.println(list.size());
		
		for(int i = 0; i < list.size();i++){
			BaiduAnalyData.getUrlPic(list.get(i).toString(), 
					fileDir + URLDecoder.decode(GetUrl.getWord(list.get(i).toString()) ,"utf-8"), pageSize);
		}
	}
}

