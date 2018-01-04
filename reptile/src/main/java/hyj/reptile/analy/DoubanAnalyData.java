package hyj.reptile.analy;

import hyj.reptile.head.DefaultHeader;
import hyj.reptile.regex.DoubanRegex;
import hyj.reptile.util.HttpGet;
import hyj.tool.dao.jdbc.BaseDao;
import hyj.tool.dao.jdbc.CommDao;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * 分析数据
 * @author huangyujie
 *
 */
public class DoubanAnalyData{
	CommDao commDao = new CommDao();
	
	public void getMsgByUrl(Connection conn, String url) throws Exception{
		HttpGet httpGet = new HttpGet();
		DoubanRegex doubanRegex = new DoubanRegex();
		
		//请求数据
		String result = httpGet.getString(url,DefaultHeader.douBanHeader(url));

		//获取书籍信息
		String msg = doubanRegex.getSjInfo(result);
		String msgArray[] = doubanRegex.getSjInfoArray(msg);
		
		for(String ss : msgArray){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("BOOKNAME", doubanRegex.getBookName(ss));	//书名
			dataMap.put("AUTHOR", 	doubanRegex.getAuthor(ss));		//作者名
			dataMap.put("PJFS", 	doubanRegex.getPjFs(ss));		//评价分数
			dataMap.put("PJRS", 	doubanRegex.getPjRs(ss));		//评价人数
			dataMap.put("CBRQ", 	doubanRegex.getCbrq(ss));		//出版日期
			saveResult(conn, dataMap);
		}
	}
	
	/**
	 * 保存抓取的书籍信息
	 * @param conn
	 * @param dataMap
	 * @throws Exception
	 */
	public void saveResult(Connection conn, Map<String,Object> dataMap) throws Exception{
		//如果这本书还未下载
		String sql   = "SELECT count(1) FROM douban WHERE bookname = ? and download in ('1','2')";
		String order = "BOOKNAME";
		
		if(commDao.selectObject(conn, sql, order, dataMap).toString().equals("0")){
			//先根据书名删除一次
			sql = "DELETE FROM douban WHERE bookname = ? and download = '0'";
			order = "BOOKNAME";
			commDao.update(new BaseDao().getConnect(), sql, order, dataMap);
			
			//再插入到数据
			sql = "INSERT INTO douban(id,bookname,author,pjfs,pjrs,cbrq,bzrq)"
				+ "VALUES(SEQ_DOUBAN.NEXTVAL,?,?,?,?,?,SYSDATE)";
			
			order = "BOOKNAME,AUTHOR,PJFS,PJRS,CBRQ";
			
			commDao.update(conn, sql, order, dataMap);
		}
	}
}

