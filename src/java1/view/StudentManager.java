package java1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

import java1.common.ServerResponse;
import java1.service.*;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.JRadioButton;

import java.awt.Font;


public class StudentManager extends JFrame {

	private final JPanel contentPane;
	private final JTextField textField;
	public final Table_Model model;
	public final JTable table;
	public final JButton button;
	public final JButton button_2;
	public final JButton button_3;
	public final JButton button_4;
	JComboBox<String> sexBox = new JComboBox();
	JComboBox<String> gradeBox = new JComboBox();
	JComboBox<String> hobbyBox = new JComboBox();
	public final JToggleButton toggleButton;
	private boolean isFirst=true;
	private String info="";
	public static StudentManager _instance;
	private int row_changed=-1;
	private Manageer manageer = new Manageer();
	private ServerResponse rep = new ServerResponse();
	private JRadioButton radioname;
	private JRadioButton radioid;
	private JRadioButton radiomajor;
	public List<StudentNode> stuList;
	ButtonGroup group = new ButtonGroup();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					StudentManager frame = new StudentManager();
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
	public StudentManager(StudentMap stuMap, HobbyMap hobbyMap, HobbyCategoryMap hobbyCategoryMap) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("\u5B66\u751F\u4FE1\u606F\u7BA1\u7406");
		setBounds((int)(getToolkit().getScreenSize().getWidth()-510)/2, (int)(getToolkit().getScreenSize().getHeight()-500)/2,  510, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		stuList = new ArrayList<StudentNode>();
		Set<String> stuSet = stuMap.getKeyset();
		for(String id : stuSet){
			stuList.add(stuMap.get(id));
		}
		
		sexBox.addItem("ȫ��");
		sexBox.addItem("��");
		sexBox.addItem("Ů");
		
		gradeBox.addItem("ȫ��");
		gradeBox.addItem("��һ");
		gradeBox.addItem("���");
		gradeBox.addItem("����");
		gradeBox.addItem("����");
		
		hobbyBox.addItem("ȫ��");
		Set<String> set = hobbyMap.getKeyset();
		for(String id : set){
			HobbyNode hobby = hobbyMap.get(id);
			hobbyBox.addItem(hobby.getHobby());
		}
		

		
		
//		�޸���Ϣ��ť
		toggleButton = new JToggleButton("\u4FEE\u6539\u4FE1\u606F");
		toggleButton.setEnabled(false);
		toggleButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(isFirst&&toggleButton.isEnabled()&&!toggleButton.isSelected()){
					isFirst=false;
					JOptionPane.showMessageDialog(contentPane, "˫��ѧ����Ϣ���б༭�޸ģ�");
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
		toggleButton.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub

				if(toggleButton.isEnabled()){
					model.isStudentEditor=toggleButton.isSelected();
					if(!toggleButton.isSelected()){
						if(row_changed!=-1){
							//�رյ�ʱ��ѯ���Ƿ񱣴�ѧ����Ϣ
							try {
								ChooseChange(stuMap, hobbyMap);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							row_changed=-1;
							toggleButton.setSelected(false);
						}
//						int row=table.getSelectedRow();
//						int column=table.getSelectedColumn();
//						if(row!=-1&&(column==2||column==3||column==5)){
//							table.setValueAt(table.getValueAt(row, column), row, column);
//						}
//						table.clearSelection();
						row_changed=-1;
					}
//					else if(toggleButton.isSelected()){
//						JOptionPane.showMessageDialog(contentPane, "˫��ѧ����Ϣ���б༭�޸�");
//						toggleButton.setSelected(true);
//					}
				}
				//JOptionPane.showMessageDialog(contentPane, "ȷ��Ҫ���ĸ�ѧ������Ϣ��");
			}
		});
		
//		������
		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(table.getSelectedRow()!=-1){

					table.clearSelection();
					SelectedNull();
				}
				
			}
		});
		textField.setColumns(10);
		
		
//		���ѧ����ť
		button = new JButton("\u6DFB\u52A0\u5B66\u751F");
		button.setForeground(Color.BLUE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button.setEnabled(false);
				InputStudentInfo isi= new InputStudentInfo(stuMap, hobbyMap);
				if(textField.getText() != ""){
					textField.setText("");
					ReLodeTable(stuMap, hobbyMap);
				}
				isi.setVisible(true);
				isi.addWindowListener(new WindowListener() {
					
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
						button.setEnabled(true);
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
		
//		�����Ȥ��ť
		button_2 = new JButton("\u6DFB\u52A0\u5174\u8DA3");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleButton.setSelected(false);
				model.isStudentEditor=false;
				setVisible(false);
				String stuID = (String)table.getValueAt(table.getSelectedRow(),0);
				AddHobby ah= new AddHobby(stuID, stuMap, hobbyMap);
				
//				����ǰ�е���Ϣ����AddHobbyҳ��
				ah.sID=(String)table.getValueAt(table.getSelectedRow(), 0);
				ah.row=table.getSelectedRow();
				ah.setVisible(true);
				ah.addWindowListener(new WindowListener() {
					
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
		button_2.setEnabled(false);
		
		
//		ɾ����Ȥ��ť
		button_3 = new JButton("\u5220\u9664\u5174\u8DA3");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RemoveHobby(stuMap, hobbyMap);
			}
		});
		button_3.setEnabled(false);
		
		
//		ɾ��ѧ����ť
		button_4 = new JButton("\u5220\u9664\u5B66\u751F");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteStudent(stuMap, hobbyMap, hobbyCategoryMap);
			}
		});
		button_4.setEnabled(false);
		button_4.setForeground(Color.RED);
		
		
//		������ť
		JButton button_5 = new JButton("\u641C\u7D22");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(info.equals(textField.getText()))
					return;

				info=textField.getText();
				//System.out.println(info+",,,");
				SelectedNull();
				while(model.getRowCount()!=0){
					model.removeRow(0);
					model.fireTableDataChanged();
				}
				
				sexBox.setSelectedIndex(0);
				gradeBox.setSelectedIndex(0);
				hobbyBox.setSelectedIndex(0);
				
//				ѡ��ȫ���������޸�stuList
//				if(radioall.isSelected()){
//					stuList.removeAll(stuList);
//					Set<String> set = stuMap.getKeyset();
//					for(String id : set){
//						StudentNode stu = stuMap.get(id);
//						stuList.add(stu);
//					}
//				}
				
				if(info.equals("")){
					
					group.clearSelection();
//					radioall.setSelected(false);
					
					Set<String> set = stuMap.getKeyset();
					for(String id : set){
						StudentNode stu = stuMap.get(id);
						String stuID = stu.getID();
						String stuName = stu.getName();
						String sex = stu.getSex();
						String grade = stu.getGrade();
						String major = stu.getMajor();
						HobbyList list = stu.getHobbyList();
						
						if(list != null && list.getHead().getNext() != null){
							HobbyNode temp = list.getHead();
							while(temp.getNext() != null){
								model.addRow(stuID, stuName, sex, grade, major, temp.getNext());
								model.fireTableDataChanged();
								temp = temp.getNext();
							}
						} else{
							model.addRow(stuID, stuName, sex, grade, major, null);
							model.fireTableDataChanged();
						}
					}
					
//					ReLodeTable(stuMap, hobbyMap);
				}
				
				if(radioname.isSelected()){
					//������
					for(StudentNode stu : stuList){
						if(stu.getName().indexOf(info) != -1){
							String stuID = stu.getID();
							String stuName = stu.getName();
							String sex = stu.getSex();
							String grade = stu.getGrade();
							String major = stu.getMajor();
							HobbyList list = stu.getHobbyList();
							
							if(list != null && list.getHead().getNext() != null){
								HobbyNode temp = list.getHead();
								while(temp.getNext() != null){
									model.addRow(stuID, stuName, sex, grade, major, temp.getNext());
									model.fireTableDataChanged();
									temp = temp.getNext();
								}
							} else{
								model.addRow(stuID, stuName, sex, grade, major, null);
								model.fireTableDataChanged();
							}
						}
					}
				}
				
				if(radioid.isSelected()){
					//��ID
					for(StudentNode stu : stuList){
						if(stu.getID().indexOf(info) != -1){
							String stuID = stu.getID();
							String stuName = stu.getName();
							String sex = stu.getSex();
							String grade = stu.getGrade();
							String major = stu.getMajor();
							HobbyList list = stu.getHobbyList();
							
							if(list != null && list.getHead().getNext() != null){
								HobbyNode temp = list.getHead();
								while(temp.getNext() != null){
									model.addRow(stuID, stuName, sex, grade, major, temp.getNext());
									model.fireTableDataChanged();
									temp = temp.getNext();
								}
							} else {
								model.addRow(stuID, stuName, sex, grade, major, null);
								model.fireTableDataChanged();
							}
						}
					}
				}
				
				if(radiomajor.isSelected()){
//					��רҵ
					for(StudentNode stu : stuList){
						if(stu.getMajor().indexOf(info) != -1){
							String stuID = stu.getID();
							String stuName = stu.getName();
							String sex = stu.getSex();
							String grade = stu.getGrade();
							String major = stu.getMajor();
							HobbyList list = stu.getHobbyList();
							
							if(list != null && list.getHead().getNext() != null){
								HobbyNode temp = list.getHead();
								while(temp.getNext() != null){
									model.addRow(stuID, stuName, sex, grade, major, temp.getNext());
									model.fireTableDataChanged();
									temp = temp.getNext();
								}
							} else {
								model.addRow(stuID, stuName, sex, grade, major, null);
								model.fireTableDataChanged();
							}
						}
					}
				}
			}
		});
		
		
//		���ѧ����Ϣ���
		String[] title= { "ѧ��","����", "�Ա�", "�꼶","רҵ","��ȤID","��Ȥ����","��Ȥ���"};
		model=new Table_Model(20,title);
		model.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if(e.getType()==TableModelEvent.UPDATE && model.isStudentEditor){
					row_changed=e.getFirstRow();
				}
			}
		});
		
		table=new JTable(model){
			public TableCellEditor getCellEditor(int row,int colunm){
				if(colunm==5){
					table.setRowSelectionInterval(row,row);
					String sID=(String)table.getValueAt(table.getSelectedRow(), 0);
					String cID=(String)table.getValueAt(table.getSelectedRow(), 5);
					StudentNode p= stuMap.get(sID);
					
					final ComboBoxCellEditor temp= new ComboBoxCellEditor(p.getHobbyList(),row);
					
					temp.addFocusListener(new FocusListener()
					{
						public void focusLost(FocusEvent arg0){
						
						}
						public void focusGained(FocusEvent arg0){
							if(temp.getSelectedIndex()==0&&temp.box_scil!=null){
								String ID=(String)temp.getSelectedItem();
								HobbyNode p= temp.box_scil.getHead();
								while(!p.getID().equals(ID)){
									p=p.getNext();
								}
								model.setValueAt(p.getHobby(), temp.row, 6);
								model.setValueAt(p.getHobbyCategoryID(), temp.row, 7);
							}
							
						}
					});
					temp.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							if(e.getStateChange() == ItemEvent.SELECTED){
								String ID=(String)temp.getSelectedItem();
								HobbyNode p= temp.box_scil.getHead();
								if(p != null){
									if(p.getID() != null){
										while(!p.getID().equals(ID)){
											p=p.getNext();
										}
										model.setValueAt(p.getHobby(), temp.row, 6);
										model.setValueAt(p.getHobbyCategoryID(), temp.row, 7);
									}
								}
							} 
						}
					});
					temp.setSelectedItem(cID);
					return temp;
				}
				
				return super.getCellEditor(row,colunm);
			}
			
		};
		
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
				IsTableSelected(stuMap, hobbyMap);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				//
				IsTableSelected(stuMap, hobbyMap);
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
		
		
		
        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(2).setCellEditor(new DefaultCellEditor(new JComboBox(new String[]{"��","Ů"}))); // ����ĳ�в���jcomboBox��� 
        tcm.getColumn(3).setCellEditor(new DefaultCellEditor(new JComboBox(new String[]{"��һ","���","����","����"})));
//        tcm.getColumn(7).setCellEditor(new DefaultCellEditor(new JComboBox(new String[]{"�᳤","����","����","��Ա"})));
        table.getColumnModel().getColumn(0).setPreferredWidth(90);  //���õ�1�еĿ��  
        table.getColumnModel().getColumn(1).setPreferredWidth(50);  //���õ�2�еĿ��
        table.getColumnModel().getColumn(2).setPreferredWidth(30);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
        table.getColumnModel().getColumn(4).setPreferredWidth(60);
        table.getColumnModel().getColumn(5).setPreferredWidth(50);
        table.getColumnModel().getColumn(6).setPreferredWidth(70);
        table.getColumnModel().getColumn(7).setPreferredWidth(50);
		table.getTableHeader().setBackground(Color.LIGHT_GRAY);
		table.getTableHeader().setForeground(Color.BLUE);
		DefaultTableCellRenderer r=new DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class,r);
        
		JScrollPane scroll = new JScrollPane(table);
		//scroll.setSize(470, 370);
		//scroll.setLocation(17, 85);
		scroll.setBounds(17, 115, 470, 340);
		scroll.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				table.clearSelection();
				SelectedNull();
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
		ReLodeTable(stuMap, hobbyMap);
		
//		ɸѡ����Ա�
		sexBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String sex = (String)sexBox.getSelectedItem();
				String grade = (String)gradeBox.getSelectedItem();
				String hobby = (String)hobbyBox.getSelectedItem();
				comboSelect(sex, grade, hobby, stuMap, hobbyMap);
			}
		});
		
		
		gradeBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String sex = (String)sexBox.getSelectedItem();
				String grade = (String)gradeBox.getSelectedItem();
				String hobby = (String)hobbyBox.getSelectedItem();
				comboSelect(sex, grade, hobby, stuMap, hobbyMap);
			}
		});
		
		
		hobbyBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String sex = (String)sexBox.getSelectedItem();
				String grade = (String)gradeBox.getSelectedItem();
				String hobby = (String)hobbyBox.getSelectedItem();
				comboSelect(sex, grade, hobby, stuMap, hobbyMap);
			}
		});
		

		
		radiomajor = new JRadioButton("\u641C\u7D22\u4E13\u4E1A");
		radiomajor.setFont(new Font("����", Font.PLAIN, 15));
		
		radioname = new JRadioButton("\u641C\u7D22\u59D3\u540D");
		radioname.setFont(new Font("����", Font.PLAIN, 15));
		
		radioid = new JRadioButton("\u641C\u7D22\u5B66\u53F7");
		radioid.setFont(new Font("����", Font.PLAIN, 15));
		
		// ����ѡ��ť�ŵ�ͬһ����
		
		group.add(radiomajor);
		group.add(radioname);
		group.add(radioid);
		
		
//		table.setRowSelectionInterval(0,0 );
		//table.setEditingRow(1);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(sexBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(button, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(toggleButton)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(radioname)
										.addComponent(gradeBox, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(hobbyBox, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
										.addComponent(radioid))
									.addGap(2))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(2)
							.addComponent(radiomajor, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED, 8, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_5, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(button)
								.addComponent(toggleButton)
								.addComponent(button_2))
							.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(sexBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(hobbyBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(gradeBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(2)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(radiomajor)
								.addComponent(radioname)
								.addComponent(radioid))
							.addContainerGap(364, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
								.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(button_5, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())))
		);
		contentPane.setLayout(gl_contentPane);
		_instance=this;
	}
	
	public List<StudentNode> selectByHobby(List<StudentNode> stuList, String hobbyID){
		
		List<StudentNode> stus = new ArrayList<StudentNode>();
		
		for(StudentNode stu : stuList){
			if(stu.isHobbyExist(hobbyID)){
				stus.add(stu);
			}
		}
		
		return stus;
	}
	
	public List<StudentNode> selectBySex(List<StudentNode> stuList, String sex){
		List<StudentNode> stus = new ArrayList<StudentNode>();
		
		for(StudentNode stu : stuList){
			if(stu.getSex().equals(sex)){
				stus.add(stu);
			}
		}
		
		return stus;
	}
	
	public List<StudentNode> selectByGrade(List<StudentNode> stuList, String grade){
		List<StudentNode> stus = new ArrayList<StudentNode>();
		for(StudentNode stu : stuList){
			if(stu.getGrade().equals(grade)){
				stus.add(stu);
			}
		}
		
		return stus;
	}
	
	public void comboSelect(String sex, String grade, String hobby, StudentMap stuMap, HobbyMap hobbyMap){
		
		Set<String> set = stuMap.getKeyset();
		String hobbyID = "";
		
		stuList.removeAll(stuList);
		for(String id : set){
			stuList.add(stuMap.get(id));
		}
		
		while(table.getRowCount()>0){
			model.removeRow(table.getRowCount()-1);
			model.fireTableDataChanged();
		}
		
		if(!sex.equals("ȫ��")){
			stuList = selectBySex(stuList, sex);
		}
		if(!grade.equals("ȫ��")){
			stuList = selectByGrade(stuList, grade);
		}
		if(!hobby.equals("ȫ��")){
			Set<String> hobbySet = hobbyMap.getKeyset();
			for(String id : hobbySet){
				HobbyNode hobbyNode = hobbyMap.get(id);
				if(hobbyNode.getHobby().equals(hobby)){
					hobbyID = hobbyNode.getID();
				}
			}
			stuList = selectByHobby(stuList, hobbyID);
		}
		
		for(StudentNode stu : stuList){
			String stuID = stu.getID();
			String stuName = stu.getName();
			String stuSex = stu.getSex();
			String stuGrade = stu.getGrade();
			String major = stu.getMajor();
			HobbyList list = stu.getHobbyList();
			
			if(hobby.equals("ȫ��")){
				if(list == null || list.getHead().getNext() == null){
					model.addRow(stuID, stuName, stuSex, stuGrade, major, null);
				} else {
					HobbyNode temp = list.getHead();
					while(temp.getNext() != null){
						model.addRow(stuID, stuName, stuSex, stuGrade, major, temp.getNext());
						model.fireTableDataChanged();
						temp = temp.getNext();
					}
				}
			} else {
				model.addRow(stuID, stuName, stuSex, stuGrade, major, hobbyMap.get(hobbyID));
			}
		}
	}
	
//	���������б�
	public void ReLodeTable(StudentMap stuMap, HobbyMap hobbyMap){
		while(table.getRowCount()>0){
			model.removeRow(table.getRowCount()-1);
			model.fireTableDataChanged();
		}
		
		String sex = (String)sexBox.getSelectedItem();
		String grade = (String)gradeBox.getSelectedItem();
		String hobby = (String)hobbyBox.getSelectedItem();
		comboSelect(sex, grade, hobby, stuMap, hobbyMap);
		
		SelectedNull();
	}
	
//	ѧ��ɾ����Ȥ
	public void RemoveHobby(StudentMap stuMap, HobbyMap hobbyMap){
		int row=table.getSelectedRow();
		String stuID=(String)table.getValueAt(row, 0);
		String hobbyID=(String)table.getValueAt(row, 5);
		int judge=JOptionPane.showConfirmDialog(contentPane, "ȷ��Ҫɾ�����"+hobbyID+"����Ȥ��");
		if(judge==0){
			
			rep = manageer.studentRemoveHobby(stuMap, hobbyMap, stuID, hobbyID);
			
			if(!rep.isSuccess()){
				JOptionPane.showMessageDialog(contentPane, rep.getMsg());
				return ;
			}
			
//			StudentNode stu = stuMap.get(stuID);
//			HobbyNode hobby = hobbyMap.get(hobbyID);
//			model.addRow(stu.getID(), stu.getName(), stu.getSex(), stu.getGrade(), stu.getMajor(), hobby, row);
			
			ReLodeTable(stuMap, hobbyMap);
			SelectedNull();
//			if(p.corporations.tail==null){
//				button_3.setEnabled(false);
//			}
			model.fireTableDataChanged();
			JOptionPane.showMessageDialog(contentPane, rep.getMsg());

		}
	}
	
//	ɾ��ѧ����֧������ɾ��
	public void DeleteStudent(StudentMap stuMap, HobbyMap hobbyMap, HobbyCategoryMap hobbyCategoryMap){
		int judge=JOptionPane.showConfirmDialog(contentPane, "ȷ��Ҫɾ��ѧ����Ϣ��");
		if(judge==0){
			//ɾ��
			int []rows=table.getSelectedRows();
			
			for(int row:rows){
				String stuID = (String)table.getValueAt(row, 0);
				rep = manageer.removeStudent(stuMap, hobbyMap, hobbyCategoryMap, stuID);
				if(!rep.isSuccess()){
					JOptionPane.showMessageDialog(contentPane, rep.getMsg());
				}
				//System.out.print(row+"  "+rows.length);
			}
			ReLodeTable(stuMap, hobbyMap);
//			for(int row=rows.length-1;row>=0;row--){
//				model.removeRow(rows[row]);
//				model.fireTableDataChanged();
//			}
			
			SelectedNull();
			row_changed=-1;
			model.isStudentEditor=false;
			
			JOptionPane.showMessageDialog(contentPane, "ɾ���ɹ���");
			
//			ͬʱд���ļ�
//			try {
//				StudentAndCorporationManager.Write();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
		}
	}
	
	public void ChooseChange(StudentMap stuMap, HobbyMap hobbyMap) throws IOException{
		//yes-0 no-1 cancel-2
		int judge=JOptionPane.showConfirmDialog(contentPane, "ȷ��Ҫ���ĸ�ѧ������Ϣ��");
		if(judge==0){//����Ϣ
			ChangeStudentInfo(stuMap, hobbyMap);
		}
		else{//�ָ���ʾ
			RecoveryStudentInfo(stuMap, hobbyMap);
		}
	}
	
	public void ChangeStudentInfo(StudentMap stuMap, HobbyMap hobbyMap) throws IOException{
		int row=row_changed;
		rep = manageer.modifyStudentBasicInfo((String) table.getValueAt(row, 0), (String) table.getValueAt(row, 1), (String) table.getValueAt(row, 2), (String) table.getValueAt(row, 3), (String) table.getValueAt(row, 4), stuMap, hobbyMap);
		if(!rep.isSuccess()){
			JOptionPane.showMessageDialog(contentPane, rep.getMsg());
		}
	}
	
	public void RecoveryStudentInfo(StudentMap stuMap, HobbyMap hobbyMap){
		int row=row_changed;
		String stuID = (String)table.getValueAt(row, 0);
		StudentNode stu = stuMap.get(stuID);
		
		table.setValueAt(stu.getName(), row, 1);
		table.setValueAt(stu.getSex(), row, 2);
		table.setValueAt(stu.getGrade(), row, 3);
		table.setValueAt(stu.getMajor(), row, 4);
		
		String s="";
		HobbyList list = stu.getHobbyList();
		
		if(list != null && list.getHead().getNext() != null){
			String hobbyID=(String)table.getValueAt(row, 5);
			HobbyNode hobby = hobbyMap.get(hobbyID);
			s = hobby.getHobbyCategoryID();
		}
		
		table.setValueAt(s, row, 7);
	}
	
//	���һ��ʱ�����ð�ť�Ƿ�ɰ�
	public void IsTableSelected(StudentMap stuMap, HobbyMap hobbyMap){
		toggleButton.setEnabled(true);
		if(table.getSelectedRows().length==1){
			button_2.setEnabled(true);
			if(!((String)table.getValueAt(table.getSelectedRow(), 5)).equals("��")){
				button_3.setEnabled(true);
			}
			else{
				button_3.setEnabled(false);
			}
		}
		else{
			button_2.setEnabled(false);
			button_3.setEnabled(false);
		}
		button_4.setEnabled(true);
		if(table.getSelectedRow()!=row_changed&&row_changed!=-1){
			try {
				ChooseChange(stuMap, hobbyMap);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			row_changed=-1;
		}
	}
	
	public void SelectedNull(){
		toggleButton.setSelected(false);
		toggleButton.setEnabled(false);
		button_2.setEnabled(false);
		button_3.setEnabled(false);
		button_4.setEnabled(false);
	}
}

class Table_Model extends AbstractTableModel {
    private static final long serialVersionUID = -3094977414157589758L;

    private Vector content = null;

//  	��ͷ�б�
    private String[] title_name; 

    public boolean isStudentEditor=false;//Student
    public boolean isCorporationEditor=false;//Student

    public Table_Model(int count,String []title) {
        content = new Vector(count);
        title_name=title;
    }

    /** 
     * ����һ���� 
     * @param row �к�  0��ʼ�����ǲ�����0
     */

    /** 
     * ����һ������ 
     */
    public void addRow(String ID,String name, String sex, String grade,String major,HobbyNode hobby) 
    {
        Vector<String> v = new Vector<String>(8);
        v.add(0, ID);
        v.add(1, name); 
        v.add(2, sex);
        v.add(3, grade);
        v.add(4, major);
        if(hobby != null){
        	v.add(5, hobby.getID());
            v.add(6, hobby.getHobby());
            v.add(7, hobby.getHobbyCategoryID());
        }
        else{
        	v.add(5, "��");
        	v.add(6, "");
        	v.add(7, "");
        }
        content.add(v);
    }
    
    public void addRow(String ID,String name, String sex, String grade,String major,HobbyNode hobby,int row) 
    {
        Vector<String> v = new Vector<String>(8);
        v.add(0, ID);
        v.add(1, name); 
        v.add(2, sex);
        v.add(3, grade);
        v.add(4, major);
        if(hobby != null){
        	v.add(5, hobby.getID());
            v.add(6, hobby.getHobby());
            v.add(7, hobby.getHobbyCategoryID());
        }
        else{
        	//System.out.println(33333);
        	v.add(5, "��");
        	v.add(6, "");
        	v.add(7, "");
        }
        content.add(row,v);
    }

    public void addRow(String ID,String name, String Category){
    	Vector<String> v = new Vector<String>(3);
        v.add(0, ID);
        v.add(1, name); 
        v.add(2, Category);
        content.add(v);
    }
    
    public void addRow(String ID,String hobbyName){
    	Vector<String> v = new Vector<String>(2);
        v.add(0, ID);
        v.add(1, hobbyName); 
        content.add(v);
    }
    
    public void addRow(String ID,String name, String sex,String grade,String major, HobbyMap hobbyMap, StudentMap stuMap){
    	Vector<String> v = new Vector<String>(6);
    	v.add(0, ID);
        v.add(1, name); 
        v.add(2, sex);
        v.add(3, grade);
        v.add(4, major); 
        v.add(5, major);
        
        content.add(v);
    }
    
    
    //0�����һ��
    public void removeRow(int row) {
        content.remove(row);
    }

    //�����Ԫ���ж����Ƿ�ɱ༭�� �����Ӧ��Ԫ��ʱ�Զ�����
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	if(isStudentEditor){
    		if(columnIndex == 0||columnIndex == 6) {//ѧ�ź���Ȥ���Ʋ������༭
                return false;
            }
            if(columnIndex==7&&((String)getValueAt(rowIndex,5)).equals("��")){
            	return false;
            }
            if(columnIndex == 5&&((String)getValueAt(rowIndex,5)).equals("��"))
    			return false;
            return true;
    	}
    	else if(isCorporationEditor){
    		if(columnIndex==0)
    			return false;
    		return true;
    	}
    	else{
    		//����Ȥ,��ȤID�ɱ༭
    		if(columnIndex == 5&&!((String)getValueAt(rowIndex,5)).equals("��")&&title_name.length==8)
    			return true;
    		return false;
    	}
    }

    public void setValueAt(Object value, int row, int col) {
        ((Vector) content.get(row)).remove(col);
        ((Vector) content.get(row)).add(col, value);
        this.fireTableCellUpdated(row, col);
    }

    public String getColumnName(int col) {
        return title_name[col];
    }

    public int getColumnCount() {
        return title_name.length;
    }

    public int getRowCount() {
        return content.size();
    }

    public Object getValueAt(int row, int col) {
        return ((Vector) content.get(row)).get(col);
    }

    public Class getColumnClass(int col) {
        return getValueAt(0, col).getClass();
    }
}

class ComboBoxCellEditor extends JComboBox implements TableCellEditor{

    protected EventListenerList listenerList = new EventListenerList();
    protected ChangeEvent changeEvent = new ChangeEvent(this);
    
    public HobbyList box_scil;
    public int row;
    
    public ComboBoxCellEditor(int row) {
        super();
        this.row=row;
        box_scil=null;
        addItem("");
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fireEditingStopped();
            }
        });
    }
    
    public ComboBoxCellEditor(HobbyList scil,int row) {
        super();
        this.row=row;
        HobbyNode p=scil.getHead();
        if(scil.getHead().getNext() == null || scil == null){
        	addItem("��");
        	box_scil=null;
        }
        else{
        	box_scil=scil;
        	while(p!=null){
            	addItem(p.getID());
            	p=p.getNext();
            }
        }
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fireEditingStopped();
            }
        });
    }

    public void addCellEditorListener(CellEditorListener listener) {
        listenerList.add(CellEditorListener.class, listener);
    }

    public void removeCellEditorListener(CellEditorListener listener) {
        listenerList.remove(CellEditorListener.class, listener);
    }

    protected void fireEditingStopped() {
        CellEditorListener listener;
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == CellEditorListener.class) {
                listener = (CellEditorListener) listeners[i + 1];
                listener.editingStopped(changeEvent);
            }
        }
    }

    protected void fireEditingCanceled() {
        CellEditorListener listener;
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == CellEditorListener.class) {
                listener = (CellEditorListener) listeners[i + 1];
                listener.editingCanceled(changeEvent);
            }
        }
    }

    public void cancelCellEditing() {
        fireEditingCanceled();
    }

    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }

    public boolean isCellEditable(EventObject event) {
        return true;
    }

    public boolean shouldSelectCell(EventObject event) {
        return true;
    }

    public Object getCellEditorValue() {
        return getSelectedItem();
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        return this;
    }

}
