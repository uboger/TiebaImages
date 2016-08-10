package web1;


import org.jsoup.nodes.Document;
import org.junit.Test;

import boy.tieba.util.WebUtil;

public class MainTest {
	@Test
	public void test(){
		Document document = WebUtil.getURLDocument("http://tieba.baidu.com/f/like/furank?kw=java&ie=utf-8#p");
		System.out.println(document.toString());
	}
}
