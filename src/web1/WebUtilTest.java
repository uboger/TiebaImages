package web1;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import boy.tieba.domain.User;
import boy.tieba.util.TieBaUtils;
import boy.tieba.util.WebUtil;

public class WebUtilTest {
	@Test
	public void test13() throws IOException {
	File file = new File("C:\\Users\\Administrator\\Pictures\\Tomcat环境配置\\0617-5.PNG");
		BufferedImage image = ImageIO.read(file);
		for(int y=0;y<image.getHeight();y++){
			for(int x=0;x<image.getWidth();x++){
				int rgb = image.getRGB(x, y);
				System.out.println(Integer.toHexString(rgb));
				int r = (rgb>>16)&255;
				System.out.println(Integer.toHexString(r));
				int g = (rgb>>8)&255;
				System.out.println(Integer.toHexString(g));
				int b = rgb&255;
				System.out.println(Integer.toHexString(b));
			
				
				int a = rgb>>24;
				double d = (r+g+b)/(255*3.0);
				int c = (int) (d*255);
				b=c;
				g=c<<8;
				r=c<<16;
				a=255<<24;
				rgb=(r|g|b|a);
				System.out.println(Integer.toHexString(rgb));
				image.setRGB(x, y, rgb);
			}
		}
		ImageIO.write(image, "png", new File("C:\\Users\\Administrator\\Desktop\\aa.png"));
	}
	@Test
	public void test12() {
		List<User> users = TieBaUtils.getUsersHeader("java", 30);
		for(User u : users){
			System.out.print(u.getName()+"\t");
			System.out.println(u.isVip());
		}
	}
	@Test
	public void test11() {
		Document doc = WebUtil.getUserDocumentByName("lol");
		System.out.println(doc);
	}
	@Test
	public void test10() {
		Document doc = WebUtil.getUserDocument("/home/main/?un=lol&fr=furank");
		Document doc1 = WebUtil.getUserDocument("/home/main/?un=%D3%A2%C8%FD%BC%CE%B8%E7&fr=furank");
		//http://tieba.baidu.com/home/main/?un=%D3%A2%C8%FD%BC%CE%B8%E7&fr=furank
		//http://tieba.baidu.com/home/main/?un=lol&fr=furank
		Element e1 = doc.select("title").first();
		Element e2 = doc1.select("title").first();
		System.out.println(e1);
		System.out.println(e2);
	}
	@Test
	public void test9() {
		System.out.println((int)(Math.sqrt(30)));
	}
	@Test
	public void test8() {
		Document document = WebUtil.getTieBaDocument("java");
		//如果不存在<title>百度贴吧_javadfgdfgas</title>
		//如果存在<title>java吧_百度贴吧</title>
		System.out.println(document.select("title"));
	}
	@Test
	public void test7() {
		//vip
		Document document = WebUtil.getURLDocument("http://tieba.baidu.com/f/like/furank?kw=java&ie=utf-8#p");
		Element element = document.select("table.drl_list").first();
		Elements trElements = element.select("tr.drl_list_item");
		Element vipElement = trElements.get(9).select("div.drl_item_card").first();
		String css = vipElement.attr("class");
		System.out.println(vipElement.toString());
		System.out.println(css);
	}
	@Test
	public void test0() {
		Document document = WebUtil.getURLDocument("http://tieba.baidu.com/f/like/furank?kw=java&ie=utf-8#p");
		Element element = document.select("table.drl_list").first();
	
		System.out.println(element.toString());
	}
	@Test
	public void test1() {
		Document document = WebUtil.getURLDocument("http://tieba.baidu.com/f/like/furank?kw=java&ie=utf-8#p");
		Element element = document.select("table.drl_list").first();
		Elements trElements = element.select("tr.drl_list_item");
		Element firstElement = trElements.first().select("a").first();
		Element endElement = trElements.last().select("a").first();
		System.out.println(firstElement.toString());
		System.out.println(endElement.toString());
	}
	@Test
	public void test2() {
		Document document = WebUtil.getURLDocument("http://tieba.baidu.com/f/like/furank?kw=java&pn=2");
		Element rootElement = document.select("table.drl_list").first();
		Elements trElements = rootElement.select("tr.drl_list_item");
		Element firstElement = trElements.first().select("a").first();
		String homeUrl = firstElement.attr("href");
		String userName = firstElement.text();
		System.out.println(homeUrl);
		System.out.println(userName);
	}
	@Test
	public void test3() {
		Document document = WebUtil.getURLDocument("http://tieba.baidu.com/f/like/furank?kw=java&pn=2");
		List<User> users = new ArrayList<User>();
		User user = null;
		Element rootElement = document.select("table.drl_list").first();
		Elements trElements = rootElement.select("tr.drl_list_item");
		for(Element e : trElements){
			user = new User();
			Element userElement = e.select("a").first();
			user.setName(userElement.text());
			String imageUrl = WebUtil.getUserImage(userElement.attr("href"));
			user.setImageUrl(imageUrl);
			users.add(user);
			System.out.println(user.getName());
			System.out.println(imageUrl);
		}
	}	
	@Test
	public void test4() {
		Document document = WebUtil.getUserDocument("/home/main/?un=danycive&fr=furank");
		/*
		 * <a href="javascript:;" style="" class="userinfo_head">
				<img src="http://tb.himg.baidu.com/sys/portrait/item/fc947a00?t=1395389698">
			</a>
		*/
		System.out.println(document);
	}
	@Test
	public void test5() {
		//获取头像图测试
		Document document = WebUtil.getUserDocument("/home/main/?un=danycive&fr=furank");
		/*
		 * <a href="javascript:;" style="" class="userinfo_head">
				<img src="http://tb.himg.baidu.com/sys/portrait/item/fc947a00?t=1395389698">
			</a>
		*/
		String imageUrl  = document.select("a.userinfo_head").first().select("img").first().attr("src");
		System.out.println(imageUrl);
	}
	@Test
	public void test6() {
		String imageUrl = WebUtil.getUserImageByName("猪侯美人戏");
		System.out.println(imageUrl);
	}
}
