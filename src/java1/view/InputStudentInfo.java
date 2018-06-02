package java1.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java1.common.ServerResponse;
import java1.service.*;

public class InputStudentInfo extends JFrame {

	private JPanel contentPane;
	private JTextField ID_text;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JTextField name_text;
	private JTextField major_text;
	private JButton clear_button;
	private JButton ok_button;
	private String ID;
	private String name;
	private String sex;
	private String grade;
	private String major;
	private JComboBox comboBox;
	private Manageer manageer = new Manageer();
	private ServerResponse rep = new ServerResponse();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					InputStudentInfo frame = new InputStudentInfo();
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
	public InputStudentInfo(StudentMap stuMap, HobbyMap hobbyMap) {
		setResizable(false);
		setTitle("\u8F93\u5165\u5B66\u751F\u4FE1\u606F");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds((int)(getToolkit().getScreenSize().getWidth()-250)/2, (int)(getToolkit().getScreenSize().getHeight()-250)/2, 250, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel label = new JLabel("\u5B66 \u53F7\uFF1A");
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
//                	非数字输入则不会显示
                    e.consume();  
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
		
		label_1 = new JLabel("\u59D3 \u540D\uFF1A");
		label_1.setFont(new Font("宋体", Font.PLAIN, 16));
		
		label_2 = new JLabel("\u6027 \u522B\uFF1A");
		label_2.setFont(new Font("宋体", Font.PLAIN, 16));
		
		label_3 = new JLabel("\u5E74 \u7EA7\uFF1A");
		label_3.setFont(new Font("宋体", Font.PLAIN, 16));
		
		label_4 = new JLabel("\u4E13 \u4E1A\uFF1A");
		label_4.setFont(new Font("宋体", Font.PLAIN, 16));
		
		name_text = new JTextField();
		name_text.setColumns(10);
		
		major_text = new JTextField();
		major_text.setColumns(10);
		
		final JRadioButton male_radioButton = new JRadioButton("\u7537");
		final JRadioButton female_radioButton = new JRadioButton("\u5973");
		
		final ButtonGroup buttonGroup=new ButtonGroup();
		buttonGroup.add(male_radioButton);
		buttonGroup.add(female_radioButton);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u9009\u62E9\u5E74\u7EA7", "\u5927\u4E00", "\u5927\u4E8C", "\u5927\u4E09", "\u5927\u56DB"}));
		
//		清空按钮
		clear_button = new JButton("\u6E05\u7A7A");
		clear_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ID_text.setText("");
				name_text.setText("");
				buttonGroup.clearSelection();
				comboBox.setSelectedIndex(0);
				major_text.setText("");
			}
		});
		clear_button.setForeground(Color.RED);
		clear_button.setFont(new Font("宋体", Font.PLAIN, 14));
		
//		确认按钮
		ok_button = new JButton("\u786E\u5B9A");
		ok_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ID = ID_text.getText();
				if(ID.length() != 12){
					JOptionPane.showMessageDialog(contentPane, "请输入长度为11位的学号！");
				} else {
					name = name_text.getText();
					sex = "";
					if(male_radioButton.isSelected()){
						sex = male_radioButton.getText();
					}
					if(female_radioButton.isSelected()){
						sex = female_radioButton.getText();
					}
					grade = (String)comboBox.getSelectedItem();
					major = major_text.getText();
					if(!ID.equals("")&&!name.equals("")&&!sex.equals("")&&!grade.equals("选择年级")&&!major.equals("")){
						StudentNode stu = new StudentNode(name, ID, grade, major, sex);
						
						rep = manageer.addStudent(stuMap, stu);
						
						if(!rep.isSuccess()){
							JOptionPane.showMessageDialog(contentPane, rep.getMsg());
						} else {
//							添加学生成功， 刷新学生信息页面
							StudentManager._instance.ReLodeTable(stuMap, hobbyMap);
							JOptionPane.showMessageDialog(contentPane, rep.getMsg());
						}
					}
					else{
						JOptionPane.showMessageDialog(contentPane, "请输入完整的信息！");
					}
				}
			}
		});
		ok_button.setForeground(Color.GREEN);
		ok_button.setFont(new Font("宋体", Font.PLAIN, 14));
		
		
		
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(ok_button, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
							.addComponent(clear_button, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(major_text, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(male_radioButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
							.addComponent(female_radioButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(name_text, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ID_text, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)))
					.addGap(69))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(ID_text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(name_text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(male_radioButton)
						.addComponent(female_radioButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(major_text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(clear_button, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(ok_button, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}

