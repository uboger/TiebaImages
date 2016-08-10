package boy.tieba.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


import boy.tieba.domain.User;
import boy.tieba.domain.UserImage;
import boy.tieba.util.TieBaUtils;

@SuppressWarnings("serial")
public class BigImageContainer extends Canvas{
	private List<User> users;
	private List<UserImage> userImages = new ArrayList<UserImage>();
	private BufferedImage image;
	private int width;
	public BigImageContainer(List<User> users, int width) {
		System.out.println("BigImageContainer has create");
		this.users = users;
		this.width = width;
		getUserImages();
		setSize(width*125,width*125);
		image = new BufferedImage(width*125, width*125, BufferedImage.TYPE_INT_BGR);
		System.out.println("userImages size = "+userImages.size());
		setBackground(Color.BLACK);
	}
	@Override
	public void paint(Graphics g1) {
		Graphics g = image.getGraphics();
		int index=-1;
		for(int x=0;x<width;x++){
			for(int y=0;y<width;y++){
				if(index<userImages.size()-1){
					BufferedImage img = userImages.get(++index).getImage();
					g.drawImage(img, y*110+15, x*110+15, null);
				}
			}
		}
		g1.drawImage(image, 0, 0, null);
	}
	
	public List<UserImage> getUserImages() {
		UserImage userImage=null;
		for(User user : users){
			userImage = new UserImage();
			userImage.setName(user.getName());
			BufferedImage img = TieBaUtils.getBufferedImageByURL(user.getImageUrl());
			drawImage(img,user.getName(),user.isVip());
			userImage.setImage(img);
			userImage.setVip(user.isVip());
			userImages.add(userImage);
		}
		return userImages;
	}
	private void drawImage(BufferedImage img,String name,boolean isVip) {
		
		Graphics g = img.createGraphics();
		Color c = g.getColor();
		name = "@"+name;
		if(isVip){
			BufferedImage vipSign = TieBaUtils.getSignBufferedImage();
			g.drawImage(vipSign, 0, 0, null);
			g.setColor(new Color(0xffff19));
			g.drawString(name, 25, 20);
		}else{
			BufferedImage sign = TieBaUtils.getBlackSignBufferedImage();
			g.drawImage(sign, 0, 0, null);
			g.setColor(new Color(0x00cc00));
			g.drawString(name, 25, 20);
		}
		g.setColor(c);
	}
	public BufferedImage getImage() {
		return image;
	}
	
}
