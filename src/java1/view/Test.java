package java1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java1.common.ServerResponse;
import java1.service.HobbyCategoryMap;
import java1.service.HobbyCategoryNode;
import java1.service.HobbyMap;
import java1.service.HobbyNode;
import java1.service.IOService;
import java1.service.Manageer;
import java1.service.StudentMap;
import java1.service.StudentNode;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class Test extends JFrame {

//	public String cID;
	public HobbyNode hobby;
	private Table_Model model;
	private JPanel contentPane;
	private static Manageer manageer = new Manageer();
	private ServerResponse rep = new ServerResponse();
	
	private static IOService ioService = new IOService();
	private static StudentMap stuMap = new StudentMap();
	private static HobbyMap hobbyMap = new HobbyMap();
	private static HobbyCategoryMap hobbyCategoryMap = new HobbyCategoryMap();
    private static final  String hobbyCategoryPath = "D:\\FC\\HobbyCategoryInfo.txt";
    private static final  String hobbyInfoPath = "D:\\FC\\HobbyInfo.txt";
    private static final  String studentInfoPath = "D:\\FC\\StudentInfo.txt";
    private static final  String studentHobbyPath = "D:\\FC\\StudentHobby.txt";
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				ioService.readHobbyCategory(hobbyCategoryPath, manageer, hobbyCategoryMap);
				ioService.readHobby(hobbyInfoPath, manageer, hobbyCategoryMap, hobbyMap);
				ioService.readStudentInfo(studentInfoPath, stuMap);
				ioService.readStudentHobby(studentHobbyPath, manageer, hobbyMap, stuMap);
				Test frame = new Test(hobbyMap, "1");
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}

	/**
	 * Create the frame.
	 */
	public Test(HobbyMap hobbyMap, String hobbyID) {
		hobby = hobbyMap.get(hobbyID);
		setResizable(false);
		setTitle("\u5174\u8DA3\u4FE1\u606F");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds((int)(getToolkit().getScreenSize().getWidth()-450)/2, (int)(getToolkit().getScreenSize().getHeight()-350)/2, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel label = new JLabel("\u7B5B \u9009\uFF1A");
		label.setForeground(Color.BLUE);
		label.setFont(new Font("宋体", Font.PLAIN, 14));
		
		final JComboBox comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String post=(String)comboBox.getSelectedItem();
				Relode();
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u6240\u6709\u6210\u5458", "\u4F1A\u957F", "\u79D8\u4E66", "\u90E8\u957F", "\u4F1A\u5458"}));
		
		String title[]={"ID", "name", "sex", "grade", "major"};
		model=new Table_Model(20, title);
		JTable table=new JTable(model);
		DefaultTableCellRenderer r=new DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class,r);
		table.getTableHeader().setBackground(Color.LIGHT_GRAY);
		table.getTableHeader().setForeground((Color.BLUE));
		table.getColumnModel().getColumn(0).setPreferredWidth(120);  //设置第1列的宽度 
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setSize(435, 280);
		scroll.setLocation(5,40);
		contentPane.add(scroll);
		
		if(hobby.getStudentList() != null && hobby.getStudentList().getHead().getNext() != null){
			StudentNode temp = hobby.getStudentList().getHead();
			while(temp.getNext() != null){
				model.addRow(temp.getNext().getID(), temp.getNext().getName());
				temp = temp.getNext();
			}
		}
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(268, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(249, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void Relode(){
		while(model.getRowCount()!=0){
			model.removeRow(0);
			model.fireTableDataChanged();
		}
		StudentNode q = hobby.getStudentList().getHead();
		while(q.getNext() !=null){
			model.addRow(q.getNext().getID(), q.getNext().getName(), q.getSex(), q.getGrade(), q.getMajor(), hobbyMap, stuMap);
			q=q.getNext();
		}
	}

}
