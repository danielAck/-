package java1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java1.common.ServerResponse;
import java1.service.HobbyNode;
import java1.service.Manageer;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import java1.service.*;
import java1.common.*;

public class InputHobbyCategoryInfo extends JFrame {

	private JPanel contentPane;
	private JTextField ID_text;
	private JTextField name_text;
	private Manageer manageer = new Manageer();
	private ServerResponse rep = new ServerResponse();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					inputHobbyCategoryInfo frame = new inputHobbyCategoryInfo();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public InputHobbyCategoryInfo(HobbyCategoryMap hobbyCategoryMap) {
		setResizable(false);
		setTitle("\u8F93\u5165\u5174\u8DA3\u7C7B\u522B\u4FE1\u606F");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds((int)(getToolkit().getScreenSize().getWidth()-254)/2, (int)(getToolkit().getScreenSize().getHeight()-185)/2, 254, 185);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("\u7C7B\u522BID   \uFF1A");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		
		JLabel label = new JLabel("\u7C7B\u522B\u540D\u79F0 \uFF1A");
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		
		ID_text = new JTextField();
		ID_text.setColumns(10);
		ID_text.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				int keyChar = e.getKeyChar();                 
                if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
                      
                }else{  
                    e.consume(); //关键，屏蔽掉非法输入  
                }  
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		name_text = new JTextField();
		name_text.setColumns(10);
		
		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ID=ID_text.getText();
				String name=name_text.getText();
//				添加兴趣类别
				if(!ID.equals("") && !name.equals("")){
					HobbyCategoryNode hobbyCategory = new HobbyCategoryNode(ID, name);
					rep = manageer.addHobbyCategory(hobbyCategoryMap, hobbyCategory);
					if(!rep.isSuccess()){
						JOptionPane.showMessageDialog(contentPane, rep.getMsg());
					} else {
						JOptionPane.showMessageDialog(contentPane, "添加成功！");
						HobbyCategoryManager._instance.ReLodeTable(hobbyCategoryMap);
					}
				}
				else{
					JOptionPane.showMessageDialog(contentPane, "请输入完整的信息！");
				}
			}
		});
		button.setForeground(Color.GREEN);
		button.setFont(new Font("宋体", Font.PLAIN, 14));
		
//		清空按钮
		JButton button_1 = new JButton("\u6E05\u7A7A");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ID_text.setText("");
				name_text.setText("");
			}
		});
		button_1.setForeground(Color.RED);
		button_1.setFont(new Font("宋体", Font.PLAIN, 14));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addComponent(label))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(name_text, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
								.addComponent(ID_text, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(ID_text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(name_text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(button)
						.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
