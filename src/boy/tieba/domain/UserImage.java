package boy.tieba.domain;

import java.awt.image.BufferedImage;

public class UserImage {
	private BufferedImage image;
	private String name;
	private boolean vip;
	
	public boolean isVip() {
		return vip;
	}
	public void setVip(boolean vip) {
		this.vip = vip;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
