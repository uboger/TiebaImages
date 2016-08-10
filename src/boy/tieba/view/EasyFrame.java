package boy.tieba.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import boy.tieba.domain.User;
import boy.tieba.domain.UserImage;
import boy.tieba.util.TieBaUtils;

@SuppressWarnings("serial")
public class EasyFrame extends Frame {
	private String name="";
	private Label nameLabel;
	private Label countLabel;
	private Checkbox bigImage,smallImage;
	private TextField nameText;
	private TextField countText;
	private Button okBtn;
	private Button saveBtn;
	private Panel top;
	private Panel middle;
	private Panel bottom;
	private JFileChooser chooserDir;
	private BigImageContainer image =null;
	private List<User> users = new ArrayList<User>();
	public EasyFrame() {
		setTitle("����ͷ�� --���л���jre8");// ��ʾ����
		nameLabel = new Label(new String("�������ƣ�"));
		countLabel = new Label(new String("��ʾ������"));
		nameText = new TextField(10);
		countText = new TextField(5);
		okBtn = new Button(new String("\u67E5\u770B\u56FE\u50CF"));// ��Ҳ������ʾ
		saveBtn = new Button(new String("����ͼ��"));
		bigImage = new Checkbox("�����ͼ");
		bigImage.setState(true);
		smallImage = new Checkbox("������Сͼ");
		chooserDir = new JFileChooser();
		chooserDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		top = new Panel();
		top.setSize(this.getWidth(), 30);
		middle = new Panel();
		bottom = new Panel();
		bottom.setSize(this.getWidth(), 30);
		top.setLayout(new GridLayout(1, 5));
		top.add(nameLabel);
		top.add(nameText);
		top.add(countLabel);
		top.add(countText);
		top.add(okBtn);
		bottom.setLayout(new GridLayout(1, 3));
		
		bottom.add(bigImage);
		bottom.add(smallImage);
		bottom.add(saveBtn);
		setLayout(new BorderLayout());
		add(top,BorderLayout.NORTH);
		add(middle,BorderLayout.CENTER);
		add(bottom,BorderLayout.SOUTH);
		setLocation(200, 60);
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(bigImage.getState()&&!smallImage.getState()){
					saveBigImage();
				}
				else if(smallImage.getState()&&!bigImage.getState()){
					saveSmallImage();
				}else if(smallImage.getState()&&bigImage.getState()){
					saveAll();
				}
			}

			private void saveAll() {
				List<UserImage> smallImages = image.getUserImages();
				String savePath = openDirectory();
				for(UserImage uimage : smallImages){
					try {
						ImageIO.write(uimage.getImage(), "png", new File(savePath+"\\"+uimage.getName()+".png"));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				BufferedImage pngImage = image.getImage();
				DateFormat df =new SimpleDateFormat("yyyyMMddHHmmss");
				try {
					ImageIO.write(pngImage, "png", new File(savePath+"\\"+df.format(new Date())+".png"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			private void saveSmallImage() {
				List<UserImage> smallImages = image.getUserImages();
				String savePath = openDirectory();
				for(UserImage uimage : smallImages){
					try {
						ImageIO.write(uimage.getImage(), "png", new File(savePath+"\\"+uimage.getName()+".png"));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

			private void saveBigImage() {
				
				BufferedImage pngImage = image.getImage();
				DateFormat df =new SimpleDateFormat("yyyyMMddHHmmss");
				String savePath = openDirectory();
				try {
					ImageIO.write(pngImage, "png", new File(savePath+"\\"+df.format(new Date())+".png"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				middle.removeAll();
				String nameValue = nameText.getText();
				nameValue = nameValue.trim();
				String count = countText.getText();
				count = count.trim();
				if ("".equals(nameValue)) {
					javax.swing.JOptionPane.showMessageDialog(null, "�������,�������������ƣ�");
					return;
				}
				if ("".equals(count)) {
					javax.swing.JOptionPane.showMessageDialog(null, "�������,��������ʾ������");
					return;
				}
				if (!TieBaUtils.existsTieba(nameValue)) {
					javax.swing.JOptionPane.showMessageDialog(null, "�������,������������ɣ�");
					return;
				}
				
				int sum = Integer.parseInt(count);
				if(users.isEmpty()||users.size()!=sum||!name.equals(nameValue)){
					name = nameValue;
					int width = (int)(Math.sqrt(sum))+1;
					users = TieBaUtils.getUsersHeader(name, sum);
					image  = new BigImageContainer(users, width);
					System.out.println("���У�"+users.size()+"��ͼƬ");
				}
				middle.add(image);
				pack();
			
			}
		});
		countText.addTextListener(new TextListener() {
			@Override
			public void textValueChanged(TextEvent e) {
				String value = countText.getText();
				if ("".equals(value))
					return;
				if (!value.matches("\\d{1,3}")) {
					countText.setText("");
					javax.swing.JOptionPane.showMessageDialog(null, "�������,��������3λ�����ڵ�����");
				}
			}
		});
		pack();
		setVisible(true);
	}
	/**
	 * 
	 * @return ѡ���ļ��е�·��
	 */
	private String openDirectory(){
		chooserDir.showDialog(new Label(), "��ѡ��");
		File file = chooserDir.getSelectedFile();
		if(file.isDirectory()){
			return chooserDir.getSelectedFile().getAbsolutePath();
		}else{
			return null;
		}
	}
	/**
	 * ���Ⱥ�ţ�107854094 
	 * @param args �������Ϸ
	 * 
	 */
	
	public static void main(String[] args) {  
		new EasyFrame();
	}
}
