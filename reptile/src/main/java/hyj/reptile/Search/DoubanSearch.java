package hyj.reptile.Search;

import hyj.reptile.analy.DoubanAnalyData;
import hyj.reptile.util.Contants;

import java.sql.Connection;

/**
 * 百度搜索
 * @author yyy
 *
 */
public class DoubanSearch {
	DoubanAnalyData doubanAnalyData = new DoubanAnalyData();
	
	//根据关键字检索图片
	public void searchByWord(Connection conn,
							 String searchType,
            				 String word, 
            				 String orderType) throws Exception{
		
		this.searchByWord(conn, searchType, word, orderType, 1);
	}

	/**
	 * 根据关键字检索数据
	 * @param word       关键字
	 * @param searchType 查询类型 movie.图书 book.书
	 * @param orderType  排序方式 T综合 R日期 S 评价
	 * @param fileDir    写入文件
	 * @param pageSize   查询的页码
	 * @throws Exception
	 */
	public void searchByWord(Connection conn,
			                 String searchType,
			                 String word, 
			                 String orderType,
			                 int pageSize) throws Exception{
		// 定义即将访问的链接
		String url = "https://" + searchType + ".douban.com/tag/";

		//如果没有录入类型，则默认为T
		if(orderType == null){
			orderType = "T";
		}
		
		//循环构造url
		for(int i = 0 ;i < pageSize; i++){
			String murl = url + word + "?start=" + i * 20 +"&type=" + orderType;
			
			doubanAnalyData.getMsgByUrl(conn, murl);
				
			//暂停0.5秒
			Thread.sleep(Contants.SleepTime);
			System.out.println(murl);
		}
			
	}
}

