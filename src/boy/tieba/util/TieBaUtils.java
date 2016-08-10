package boy.tieba.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import boy.tieba.domain.User;

public class TieBaUtils {
	/**
	 * 获取用户列表
	 * @param sum 获取用户的个数
	 * @return List<User> size=sum
	 */
	public static List<User> getUsersHeader(String name,int sum) {
		try {
			name = URLEncoder.encode(name,"gbk");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//http://tieba.baidu.com/f/like/furank?kw=%B8%A3%C7%E5&pn=2
		List<User> users = new ArrayList<User>();
		if(sum<0)sum=1;
		int len = sum/20;
		int mod = sum%20;
		if(sum<20) mod=sum;
		for(int i=0;i<len;i++){
			List<User> all = getUsersByPage(name,i+1);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			users.addAll(all);
		}
		if(mod>=1)
			users.addAll(getUsersByPageNumber(name,len+1,mod));
		return users;
	}
/**
 * 
 * @param pageNumber 指定的页数
 * &pn="+sum
 * @param mod 某页中的某些用户
 * @return List<User> size = mod
 */
	private static List<User> getUsersByPageNumber(String name,int pageNumber, int mod) {
		Document document = WebUtil.getURLDocument("http://tieba.baidu.com/f/like/furank?kw="+name+"&pn="+pageNumber);
		List<User> users = new ArrayList<User>();
		User user = null;
		Element rootElement = document.select("table.drl_list").first();
		Elements trElements = rootElement.select("tr.drl_list_item");
		for(Element e : trElements){
			Element userElement = e.select("a").first();
			String userName= userElement.text();
			if(TieBaUtils.existsUser(userName)){
				user = new User();
				Element vipElement = e.select("div.drl_item_card").first();
				String css = vipElement.attr("class");
				if(css.contains("drl_item_vip")){
					user.setVip(true);
				}else{
					user.setVip(false);
				}
				user.setName(userElement.text());
				String imageUrl = WebUtil.getUserImage(userElement.attr("href"));
				user.setImageUrl(imageUrl);
				users.add(user);
			}
			if(users.size()==mod) break;
		}
		return users;
	}
	/**
	 * 得到指定页的所有用户
	 * @param sum 指定的页
	 * &pn=sum
	 * @return List<User> size = 20
	 */
	private static List<User> getUsersByPage(String name,int sum) {
		String url = "";
		if(sum==1){
			url="http://tieba.baidu.com/f/like/furank?kw="+name+"&ie=utf-8#p";
		}else{
			url = "http://tieba.baidu.com/f/like/furank?kw="+name+"&pn="+sum;
		}
		Document document = WebUtil.getURLDocument(url);
		List<User> users = new ArrayList<User>();
		User user = null;
		Element rootElement = document.select("table.drl_list").first();
		Elements trElements = rootElement.select("tr.drl_list_item");
		for(Element e : trElements){
			Element userElement = e.select("a").first();
			String userName = userElement.text();
			if(TieBaUtils.existsUser(userName)){
				user = new User();
				Element vipElement = e.select("div.drl_item_card").first();
				String css = vipElement.attr("class");
				if(css.contains("drl_item_vip")){
					user.setVip(true);
				}else{
					user.setVip(false);
				}
				user.setName(userElement.text());
				String imageUrl = WebUtil.getUserImage(userElement.attr("href"));
				user.setImageUrl(imageUrl);
				users.add(user);
			}
		}
		return users;
	}
	/**
	 * 判断是否存在该贴吧
	 * @param name
	 * @return
	 */
	public static boolean existsTieba(String name){
		//如果不存在<title>百度贴吧_javadfgdfgas</title>
		//如果存在<title>java吧_百度贴吧</title>
		Document root = WebUtil.getTieBaDocument(name);
		String titleValue = root.select("title").first().text();
		if(titleValue.startsWith(name)){
			System.out.println("贴吧存在");
			return true;
		}
		System.out.println("贴吧不存在");
		return false;
	}
	/**
	 * 获取URL的图片
	 * @param path
	 * @return
	 */
	public static BufferedImage getBufferedImageByURL(String path){
		URL url = null;
		BufferedImage image = null;
		try {
			url = new URL(path);
			image = ImageIO.read(url);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return image;
	}
	
	/**
	 * 根据用户名判断是否存在页面
	 * @param name
	 * @return
	 */
	public static boolean existsUser(String name) {
//		<title>贴吧404</title>
//		<title>英三嘉哥的贴吧</title>
		Document root = WebUtil.getUserDocumentByName(name);
		String titleValue = root.select("title").first().text();
		//System.out.println(titleValue);
		if(titleValue.startsWith(name)){
			//System.out.println(name+"  用户存在");
			return true;
		}
		//System.out.println(name+" 用户不存在");
		return false;
	}
	public static BufferedImage getSignBufferedImage() {
		InputStream in = TieBaUtils.class.getClassLoader().getResourceAsStream("vip.png");
		BufferedImage image = null;
		try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	public static BufferedImage getBlackSignBufferedImage() {
		InputStream in = TieBaUtils.class.getClassLoader().getResourceAsStream("vip.png");
		BufferedImage image = null;
		try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int y=0;y<image.getHeight();y++){
			for(int x=0;x<image.getWidth();x++){
				int rgb = image.getRGB(x, y);
				int r = (rgb>>16)&255;
				int g = (rgb>>8)&255;
				int b = rgb&255;
				int a = rgb>>24;
				double d = (r+g+b)/(255*3.0);
				int c = (int) (d*255);
				b=c;
				g=c<<8;
				r=c<<16;
				a=a<<24;
				rgb=(r|g|b|a);
				image.setRGB(x, y, rgb);
			}
		}
		return image;
	}
}
