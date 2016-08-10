package boy.tieba.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebUtil {
	/**
	 * �õ���ǰ�����µ�Document
	 * @param url ��ַ����
	 * @return ��ǰ�����µ�Document
	 */
	public static Document getURLDocument(String url){
		try {
			URL link = new URL(url);
			return Jsoup.parse(link, 1500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��ȡ�û���ҳ��document
	 * @param url �û���ҳ�����·��
	 * @return �û���ҳ��document
	 */
	public static Document getUserDocument(String url) {
		url = "http://tieba.baidu.com"+url;
		//System.out.println(url);
		try {
			URL link = new URL(url);
			return Jsoup.parse(link, 1500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *��ȡ�û���ͷ��
	 * @param url �û���ҳ�����·��
	 * @return String ͷ��ͼƬ��url
	 */
	public static String getUserImage(String url) {
		//System.out.println(url);
		return getUserDocument(url).select("a.userinfo_head").first().select("img").first().attr("src");
	}
	/**
		 *��ȡ�û���ͷ��
	 * @param name �û����û���
	 * @return String ͷ��ͼƬ��url
	 */
	public static String getUserImageByName(String name) {
		try {
			name = URLEncoder.encode(name, "gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//home/main/?un=%D0%C4DE%BE%F5%CE%F2&fr=furank
		String url = "/home/main/?un="+name+"&fr=furank";
		return getUserDocument(url).select("a.userinfo_head").first().select("img").first().attr("src");
	}
	public static Document getTieBaDocument(String name) {
		//http://tieba.baidu.com/f?ie=utf-8&kw=c&fr=search
		try {
			name = URLEncoder.encode(name, "gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = "http://tieba.baidu.com/f?ie=utf-8&kw="+name+"&fr=search";
		return getURLDocument(url);
	}
	public static Document getUserDocumentByName(String name) {
		try {
			name = URLEncoder.encode(name, "gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = "http://tieba.baidu.com/home/main/?un="+name+"&fr=furank";
		return getURLDocument(url);
	}
}
