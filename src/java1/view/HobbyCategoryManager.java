package java1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

import java1.common.ServerResponse;
import java1.service.HobbyCategoryMap;
import java1.service.HobbyMap;
import java1.service.HobbyNode;
import java1.service.Manageer;
import java1.service.StudentMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

import java1.service.*;
import java1.common.*;

public class HobbyCategoryManager extends JFrame {

	public static HobbyCategoryManager _instance;
	private JPanel contentPane;
	public final Table_Model model;
	public final JTable table;
	private boolean isFirst=true;
	public int row_changed=-1;
	public final JButton add_button;
	public final JButton look_button;
	public final JButton delete_button;
	public final JButton find_button;
	public final JToggleButton change_toggleButton;
	private String info="";
	private JTextField textField;
	private Manageer manageer = new Manageer();
	private ServerResponse rep = new ServerResponse();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					HobbyCategoryManager frame = new HobbyCategoryManager();
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
	public HobbyCategoryManager(StudentMap stuMap, HobbyMap hobbyMap, HobbyCategoryMap hobbyCategoryMap) {
		setTitle("\u5174\u8DA3\u7C7B\u522B\u7BA1\u7406");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds((int)(getToolkit().getScreenSize().getWidth()-434)/2, (int)(getToolkit().getScreenSize().getHeight()-400)/2, 434, 400);		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		setContentPane(contentPane);
		
//		添加兴趣类别
		add_button = new JButton("\u6DFB\u52A0\u7C7B\u522B");
		add_button.setFont(new Font("宋体", Font.PLAIN, 14));
		add_button.setForeground(Color.BLUE);
		add_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InputHobbyCategoryInfo ici= new InputHobbyCategoryInfo(hobbyCategoryMap);
				ici.setVisible(true);
				add_button.setEnabled(false);
				ici.addWindowListener(new WindowListener() {
					
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
						add_button.setEnabled(true);
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
		
//		查看详情按钮
		look_button = new JButton("\u67E5\u770B\u8BE6\u60C5");
		look_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				CategoryMenberInfo cmi= new CategoryMenberInfo((String)table.getValueAt(table.getSelectedRow(), 0), hobbyCategoryMap);
				cmi.setVisible(true);
				cmi.addWindowListener(new WindowListener() {
					
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
		look_button.setEnabled(false);
		look_button.setForeground(Color.BLACK);
		look_button.setFont(new Font("宋体", Font.PLAIN, 14));
		
//		删除兴趣类别按钮
		delete_button = new JButton("\u5220\u9664\u7C7B\u522B");
		delete_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteCorporation(stuMap, hobbyMap, hobbyCategoryMap);
			}
		});
		delete_button.setEnabled(false);
		delete_button.setForeground(Color.RED);
		delete_button.setFont(new Font("宋体", Font.PLAIN, 14));
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				change_toggleButton.setSelected(false);
				change_toggleButton.setEnabled(false);
				look_button.setEnabled(false);
				delete_button.setEnabled(false);
				table.clearSelection();
			}
		});
		
//		查找按钮
		find_button = new JButton("\u641C\u7D22");
		find_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(info.equals(textField.getText())){
					return;
				}
				info=textField.getText();
				table.clearSelection();
				change_toggleButton.setSelected(false);
				change_toggleButton.setEnabled(false);
				look_button.setEnabled(false);
				delete_button.setEnabled(false);
				while(model.getRowCount()!=0){
					model.removeRow(0);
					model.fireTableDataChanged();
				}
				Set<String> set = hobbyCategoryMap.getKeyset();
				if(info.equals("")){
					if(!set.isEmpty()){
						for(String ID : set){
							HobbyCategoryNode hobbyCategory = hobbyCategoryMap.get(ID);
							model.addRow(hobbyCategory.getID(), hobbyCategory.getHobbyCategory());
							model.fireTableDataChanged();
						}
					}
			
					return;
				}
				if(Pattern.compile("^[-\\+]?[\\d]*$").matcher(info).matches()){
					//按ID找
					if(!set.isEmpty()){
						for(String ID : set){
							HobbyCategoryNode hobbyCategory = hobbyCategoryMap.get(ID);
							if(hobbyCategory.getID().indexOf(info) != -1){
								model.addRow(hobbyCategory.getID(), hobbyCategory.getHobbyCategory());
								model.fireTableDataChanged();
							}
						}
					}
				}
				else {
					//按兴趣类名称找
					for(String ID : set){
						HobbyCategoryNode hobbyCategory = hobbyCategoryMap.get(ID);
						if(hobbyCategory.getHobbyCategory().indexOf(info) != -1){
							model.addRow(hobbyCategory.getID(), hobbyCategory.getHobbyCategory());
							model.fireTableDataChanged();
						}
					}
				}
			}
		});
		find_button.setForeground(Color.BLACK);
		find_button.setFont(new Font("宋体", Font.PLAIN, 14));
		
//		修改兴趣类别按钮
		change_toggleButton = new JToggleButton("\u4FEE\u6539\u4FE1\u606F");
		change_toggleButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(isFirst&&change_toggleButton.isEnabled()&&!change_toggleButton.isSelected()){
					isFirst=false;
					JOptionPane.showMessageDialog(contentPane, "双击兴趣类别信息进行编辑修改！");
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
		change_toggleButton.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(change_toggleButton.isEnabled()){
					model.isCorporationEditor=change_toggleButton.isSelected();
					if(!change_toggleButton.isSelected()){
						if(row_changed!=-1){
							//关闭的时候询问是否保存兴趣类别信息
							try {
								ChooseChange(stuMap, hobbyMap, hobbyCategoryMap);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							row_changed=-1;
							change_toggleButton.setSelected(false);
						}
						row_changed=-1;
					}
				}
			}
		});
		change_toggleButton.setEnabled(false);
		change_toggleButton.setFont(new Font("宋体", Font.PLAIN, 14));
		
		
		String[] title= { "类别ID","类别名称"};
		model=new Table_Model(20,title);
		model.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if(e.getType()==TableModelEvent.UPDATE&&model.isCorporationEditor){
					row_changed=e.getFirstRow();
					//System.out.println(111);
				}
			}
		});
		
		table=new JTable(model);
		DefaultTableCellRenderer r=new DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class,r);
		table.getTableHeader().setBackground(Color.LIGHT_GRAY);
		table.getTableHeader().setForeground((Color.BLUE));
		
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
		
		
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				//change_toggleButton.setEnabled(true);
				if(table.getSelectedRows().length!=1){
					look_button.setEnabled(false);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				//
				change_toggleButton.setEnabled(true);
				delete_button.setEnabled(true);
				change_toggleButton.setEnabled(true);
				if(table.getSelectedRows().length==1){
					
					look_button.setEnabled(true);
					
				}
				else{
					look_button.setEnabled(false);
				}
				if(table.getSelectedRow()!=row_changed&&row_changed!=-1){
					try {
						ChooseChange(stuMap, hobbyMap, hobbyCategoryMap);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					row_changed=-1;
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
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setSize(419, 302);
		scroll.setLocation(5,68);
		contentPane.add(scroll);
		
		scroll.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				table.clearSelection();
				change_toggleButton.setSelected(false);
				change_toggleButton.setEnabled(false);
				look_button.setEnabled(false);
				delete_button.setEnabled(false);
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
		
		Set<String> set = hobbyCategoryMap.getKeyset();
		if(!set.isEmpty()){
			for(String ID : set){
				HobbyCategoryNode hobbyCategory = hobbyCategoryMap.get(ID);
				model.addRow(hobbyCategory.getID(), hobbyCategory.getHobbyCategory());
			}
		}
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(add_button, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(change_toggleButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(look_button, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
						.addComponent(textField, 312, 312, 312))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(delete_button, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
						.addComponent(find_button, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(add_button)
						.addComponent(delete_button, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(change_toggleButton)
						.addComponent(look_button, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(find_button, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(308, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		_instance=this;
	}
	
	
	public void ChooseChange(StudentMap stuMap, HobbyMap hobbyMap, HobbyCategoryMap hobbyCategoryMap) throws IOException{
		int judge=JOptionPane.showConfirmDialog(contentPane, "确定要更改该兴趣类别的信息吗？");
		if(judge==0){//存信息
			ChangeCorporationInfo(stuMap, hobbyMap, hobbyCategoryMap);
		}
		else{//恢复显示
			RecoveryHobbyInfo(hobbyCategoryMap);
		}
	}
	public void ChangeCorporationInfo(StudentMap stuMap, HobbyMap hobbyMap, HobbyCategoryMap hobbyCategoryMap) throws IOException{
		int row=row_changed;
		String categoryID = (String) table.getValueAt(row, 0);
		String categoryName = (String) table.getValueAt(row, 1);
		HobbyCategoryNode newHobbyCategory = new HobbyCategoryNode(categoryID, categoryName);
		
		rep = manageer.modifyHobbyCategory(categoryID, newHobbyCategory, hobbyCategoryMap);
		if(!rep.isSuccess()){
			JOptionPane.showMessageDialog(contentPane, rep.getMsg());
		}
		
//		写入文件
//		StudentAndCorporationManager.Write();
	}
	
	public void RecoveryHobbyInfo(HobbyCategoryMap hobbyCategoryMap){
		int row=row_changed;
		String ID = (String)table.getValueAt(row, 0);
		HobbyCategoryNode hobbyCategory = hobbyCategoryMap.get(ID);
		table.setValueAt(hobbyCategory.getHobbyCategory(), row, 0);
		table.setValueAt(hobbyCategory.getID(), row, 1);
	}
	
	public void DeleteCorporation(StudentMap stuMap, HobbyMap hobbyMap, HobbyCategoryMap hobbyCategoryMap){
		int judge=JOptionPane.showConfirmDialog(contentPane, "确定要删除兴趣类别吗？");
		if(judge==0){//删除
			int []rows=table.getSelectedRows();
			
			for(int row:rows){
				String hobbyCategoryID = (String)table.getValueAt(row, 0);
				rep = manageer.removeHobbyCategory(stuMap, hobbyMap, hobbyCategoryMap, hobbyCategoryID);
				if(!rep.isSuccess()){
					JOptionPane.showMessageDialog(contentPane, rep.getMsg());
					return ;
				}
				//System.out.print(row+"  "+rows.length);
			}
			
			JOptionPane.showMessageDialog(contentPane, "删除兴趣类别成功！");
			
			for(int row=rows.length-1;row>=0;row--){
				model.removeRow(rows[row]);
				model.fireTableDataChanged();
			}
//			button_4.setEnabled(false);
//			button_3.setEnabled(false);
//			button_2.setEnabled(false);
//			toggleButton.setEnabled(false);
//			toggleButton.setSelected(false);
			change_toggleButton.setSelected(false);
			change_toggleButton.setEnabled(false);
			look_button.setEnabled(false);
			delete_button.setEnabled(false);
			
			row_changed=-1;
			model.isCorporationEditor=false;
			
//			写入文件
//			try {
//				StudentAndCorporationManager.Write();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
		}
	}
	
	public void ReLodeTable(HobbyCategoryMap hobbyCategoryMap){
		while(table.getRowCount()>0){
			model.removeRow(table.getRowCount()-1);
			model.fireTableDataChanged();
		}
		Set<String> set = hobbyCategoryMap.getKeyset();
		
		for(String ID : set){
			HobbyCategoryNode hobbyCategory = hobbyCategoryMap.get(ID);
			model.addRow(hobbyCategory.getID(), hobbyCategory.getHobbyCategory());
			model.fireTableDataChanged();
		}
		change_toggleButton.setSelected(false);
		change_toggleButton.setEnabled(false);
		look_button.setEnabled(false);
		delete_button.setEnabled(false);
	}

}
