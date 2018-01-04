package hyj.reptile.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	
	/**
	 * 获取首个匹配的第i个匹配的字段
	 * @param targetStr
	 * @param patternStr
	 * @param index
	 * @return
	 */
	public static String regexString(String targetStr, String patternStr,int index) {
		// 定义一个样式模板，此中使用正则表达式，括号中是要抓的内容
		// 相当于埋好了陷阱匹配的地方就会掉下去
		Pattern pattern = Pattern.compile(patternStr);
		// 定义一个matcher用来做匹配
		Matcher matcher = pattern.matcher(targetStr);
		// 如果找到了
		if (matcher.find()) {
			// 打印出结果
			return matcher.group(index);
		}
		return "Nothing";
	}

	/**
	 * 获取首个匹配的第1个匹配的字段
	 * @param targetStr
	 * @param patternStr
	 * @param index
	 * @return
	 */
	public static String regexString(String targetStr, String patternStr) {
		return regexString(targetStr, patternStr, 0);
	}

	/**
	 * 获取所有个匹配的第i个匹配的字段
	 * @param targetStr
	 * @param patternStr
	 * @param index
	 * @return
	 */
	public static List<String> regexList(String targetStr, String patternStr, int i) {
		// 定义一个样式模板，此中使用正则表达式，括号中是要抓的内容
		// 相当于埋好了陷阱匹配的地方就会掉下去
		Pattern pattern = Pattern.compile(patternStr);
		
		// 定义一个matcher用来做匹配
		Matcher matcher = pattern.matcher(targetStr);
		
		List<String> list = new ArrayList<String>();
		
		// 如果找到了就返回一个list
		while (matcher.find()) {
			list.add(matcher.group(i));
		}
		
		// 打印出结果
		return list;
	}

	/**
	 * 获取所有个匹配的第1个匹配的字段
	 * @param targetStr
	 * @param patternStr
	 * @param index
	 * @return
	 */
	public static List<String> regexList(String targetStr, String patternStr) {
		return regexList(targetStr, patternStr, 0);
	}
}

