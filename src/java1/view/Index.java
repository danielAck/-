package java1.view;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java1.service.*;

import java.awt.Font;

public class Index extends JFrame {

//	private static ImageIcon background;
	private Image image;
    private static final  String hobbyCategoryPath = "D:\\FC\\HobbyCategoryInfo.txt";
    private static final  String hobbyInfoPath = "D:\\FC\\HobbyInfo.txt";
    private static final  String studentInfoPath = "D:\\FC\\StudentInfo.txt";
    private static final  String studentHobbyPath = "D:\\FC\\StudentHobby.txt";
	
	private JPanel contentPane;
	private static StudentMap stuMap = new StudentMap();
	private static HobbyMap hobbyMap = new HobbyMap();
	private static HobbyCategoryMap hobbyCategoryMap = new HobbyCategoryMap();
	public static  IOService ioService = new IOService();
	private static Manageer manageer = new Manageer();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		background =new ImageIcon(Index.class.getResource("/picture.jpg"));
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Index frame = new Index();
					
					//加载图片  
			        ImageIcon icon=new ImageIcon("D:\\FC\\picture.png");  
			        //Image im=new Image(icon);  
			        //将图片放入label中  
			        JLabel label=new JLabel(icon);  
			          
			        //设置label的大小  
			        label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());  
			          
			          
			        //获取窗口的第二层，将label放入
			        frame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));  
			              
			        //获取frame的顶层容器,并设置为透明  
			        JPanel j=(JPanel)frame.getContentPane();  
			        j.setOpaque(false); 
			  
			        JPanel panel=new JPanel();  
			  
			        //必须设置为透明的。否则看不到图片  
			        panel.setOpaque(false);  
			  
			        frame.getContentPane().add(panel);  
			        frame.getContentPane().add(panel);  
			        frame.setSize(icon.getIconWidth(),icon.getIconHeight());  
			        frame.setVisible(true);
					
					
					frame.setVisible(true);
					
					frame.addWindowListener(new WindowListener() {
						
						@Override
						public void windowOpened(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowIconified(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowDeiconified(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowDeactivated(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowClosing(WindowEvent e) {
							
							int judge=JOptionPane.showConfirmDialog(frame.getContentPane(), "确定要退出系统吗？");
							if(judge==0){
								
								int judge2 = JOptionPane.showConfirmDialog(frame.getContentPane(), "确定保存修改至文件吗？");
								if(judge2 == 0){
									// 确认修改保存至文件，将所有信息写入文件
									ioService.writeHobbyCategory(hobbyCategoryPath, hobbyCategoryMap);
									ioService.writeHobby(hobbyInfoPath, hobbyMap);
									ioService.writeStudentInfo(studentInfoPath, stuMap);
									ioService.writeStudentHobby(studentHobbyPath, stuMap);
									
									JOptionPane.showMessageDialog(frame.getContentPane(), "保存信息至文件成功！");
								}
								System.exit(0);
							}
							
						}
						
						@Override
						public void windowClosed(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowActivated(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
	}

	
	/**
	 * Create the frame.
	 */
	public Index() {
		
		setTitle("\u5B66\u751F\u5174\u8DA3\u7BA1\u7CFB\u7EDF");
		
		ioService.readHobbyCategory(hobbyCategoryPath, manageer, hobbyCategoryMap);
		ioService.readHobby(hobbyInfoPath, manageer, hobbyCategoryMap, hobbyMap);
		ioService.readStudentInfo(studentInfoPath, stuMap);
		ioService.readStudentHobby(studentHobbyPath, manageer, hobbyMap, stuMap);
		
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds((int)(getToolkit().getScreenSize().getWidth()-400)/2, (int)(getToolkit().getScreenSize().getHeight()-233)/2, 418, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton button = new JButton("\u5B66\u751F\u4FE1\u606F\u7BA1\u7406");
		button.setFont(new Font("宋体", Font.BOLD, 14));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentManager studentManager = new StudentManager(stuMap, hobbyMap, hobbyCategoryMap);
				studentManager.setVisible(true);
				studentManager.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						setVisible(true);
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
		JButton button2 = new JButton("\u5174\u8DA3\u4FE1\u606F\u7BA1\u7406");
		button2.setFont(new Font("宋体", Font.BOLD, 14));
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HobbyManager hobbyManager = new HobbyManager(stuMap, hobbyMap, hobbyCategoryMap);
				hobbyManager.setVisible(true);
				hobbyManager.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						setVisible(true);
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
//		兴趣类别管理按钮
		JButton hobbyCategory_button = new JButton("\u5174\u8DA3\u7C7B\u522B\u7BA1\u7406");
		hobbyCategory_button.setFont(new Font("宋体", Font.BOLD, 14));
		hobbyCategory_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HobbyCategoryManager hobbyCategoryManager = new HobbyCategoryManager(stuMap, hobbyMap, hobbyCategoryMap);
				hobbyCategoryManager.setVisible(true);
				hobbyCategoryManager.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						setVisible(true);
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(122)
					.addComponent(button, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
					.addGap(115))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(button2, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
					.addGap(89)
					.addComponent(hobbyCategory_button, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(31)
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(38)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(button2, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addComponent(hobbyCategory_button, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(41, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
