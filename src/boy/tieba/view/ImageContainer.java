package boy.tieba.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class ImageContainer extends Canvas{
	private BufferedImage image;
	private String name;
	
	public ImageContainer(BufferedImage image) {
		this.image = image;
		setSize(image.getWidth(),image.getHeight());
		setBackground(Color.BLACK);
	}

	public ImageContainer(BufferedImage image, String name) {
		this(image);
		this.name = name;
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 13));
		g.drawString(name, 70, 100);
		g.setColor(c);
	}
	
}
