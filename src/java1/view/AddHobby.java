package java1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Set;

import java1.common.ServerResponse;
import java1.service.*;

public class AddHobby extends JFrame {

	private JPanel contentPane;
	private Manageer manageer = new Manageer();
	private ServerResponse rep = new ServerResponse();
	public String sID;
	public int row;
	public final Table_Model model;
	public final JTable table;
	

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AddHobby frame = new AddHobby();
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
	public AddHobby(String stuID, StudentMap stuMap, HobbyMap hobbyMap) {
		setResizable(false);
		setTitle("\u6DFB\u52A0\u5174\u8DA3");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds((int)(getToolkit().getScreenSize().getWidth()-440)/2, (int)(getToolkit().getScreenSize().getHeight()-400)/2, 440, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
//		确定添加兴趣按钮
		final JButton button = new JButton("\u786E\u5B9A\u6DFB\u52A0\u5174\u8DA3");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] row=table.getSelectedRows();
				for(int contentRow : row){
					String hobbyID=(String)table.getValueAt(contentRow, 0);
						rep = manageer.studentAddHobby(stuMap, hobbyMap, sID, hobbyID);
						if(!rep.isSuccess()){
							JOptionPane.showMessageDialog(contentPane, rep.getMsg());
							return ;
						}
						
						StudentNode stu = stuMap.get(sID);
						HobbyNode hobby = hobbyMap.get(hobbyID);
						
						StudentManager._instance.ReLodeTable(stuMap, hobbyMap);
						StudentManager._instance.model.fireTableDataChanged();
						StudentManager._instance.SelectedNull();
				}
				JOptionPane.showMessageDialog(contentPane, "添加兴趣成功！");
				ReLoad(sID, hobbyMap, stuMap);
			}
		});
		button.setEnabled(false);
		button.setForeground(Color.GREEN);
		button.setFont(new Font("宋体", Font.PLAIN, 14));
		
		
		
		String title[]={"兴趣ID","兴趣名称","兴趣所属类别"};
		model=new Table_Model(20,title);
		table=new JTable(model);
//		支持多选
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.getTableHeader().setBackground(Color.LIGHT_GRAY);
		table.getTableHeader().setForeground(Color.BLUE);
		DefaultTableCellRenderer r=new DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class,r);
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(table.getSelectedRow()!=-1){
					button.setEnabled(true);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		table.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_TAB||e.getKeyCode()==KeyEvent.VK_UP||e.getKeyCode()==KeyEvent.VK_DOWN||e.getKeyCode()==KeyEvent.VK_LEFT||e.getKeyCode()==KeyEvent.VK_RIGHT){
					e.consume();
				}
			}
		});
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setSize(425, 334);
		scroll.setLocation(5,5);
		scroll.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				table.clearSelection();
				button.setEnabled(false);
				//table.getSelectionModel().setSelectionInterval(-1,-1);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		getContentPane().add(scroll);
		
		Set<String> set = hobbyMap.getKeyset();
		StudentNode stu = stuMap.get(stuID);
		for(String key : set){
			HobbyNode hobby = hobbyMap.get(key);
			if(!stu.isHobbyExist(hobby.getID())){
				model.addRow(hobby.getID(), hobby.getHobby(), hobby.getHobbyCategoryID());
			}
		}
		
//		CorporationInfoNode p=StudentAndCorporationManager.cil.head;
//		if(p!=null&&StudentAndCorporationManager.cil.tail!=null){
//			while(p!=null){
//				model.addRow(p.ID,p.name,p.date);
//				p=p.next;
//			}
//		}
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(150)
					.addComponent(button)
					.addContainerGap(157, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(229, Short.MAX_VALUE)
					.addComponent(button))
		);
		contentPane.setLayout(gl_contentPane);
		contentPane.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				table.clearSelection();
				button.setEnabled(false);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	private void ReLoad(String stuID, HobbyMap hobbyMap, StudentMap stuMap){
		
		while(table.getRowCount()>0){
			model.removeRow(table.getRowCount()-1);
			model.fireTableDataChanged();
		}
		
		Set<String> set = hobbyMap.getKeyset();
		StudentNode stu = stuMap.get(stuID);
		for(String key : set){
			HobbyNode hobby = hobbyMap.get(key);
			if(!stu.isHobbyExist(hobby.getID())){
				model.addRow(hobby.getID(), hobby.getHobby(), hobby.getHobbyCategoryID());
			}
		}
	}
}

