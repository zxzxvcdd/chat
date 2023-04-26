package client.client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import server.serverDTO.ChatInfo;
import server.serverDTO.ChatListDTO;
import server.serverDTO.EmpDTO;

public class MainViewFrame extends JFrame implements Runnable, ActionListener, ListSelectionListener {

	public static boolean call = false;
	public static HashMap<Object, Object> resMap = null;


	private JPanel contentPane;
	private JTextField empSearchText;
	private JTextField chatSearchText;
	ObjectOutputStream oos;
	DefaultTableModel model1, model2;
	int empId;
	List<ChatInfo> myChatList;
	private JList sawonList;
	private JTextArea textArea;
	private JTable empTable;
	private JTable chatTable;
	
	private int tEmpId;
	private int tChatId;
	
	private String[] empTabNames = {"부서", "직급", "이름", "번호","id"};
	private DefaultTableModel empModel = new DefaultTableModel(empTabNames,0);
	
	private String[] chatTabNames = {"채팅방 이름", "채팅방 인원","id"};
	private DefaultTableModel chatModel = new DefaultTableModel(chatTabNames,0);
	

	/**
	 * Create the frame.
	 */
	public MainViewFrame(ObjectOutputStream oos, int empId) {

		this.oos = oos;
		this.empId= empId;

		System.out.println(empId);

		HashMap<Object, Object> reqMap = new HashMap<Object, Object>();
		String command = "main";

		
		reqMap.put("command", command);
		reqMap.put("type", "employee_id");
		reqMap.put("empId", empId);

		try {
			oos.writeObject(reqMap);
			oos.flush();
		} catch (Exception e) {

		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 681, 599);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);


		JLabel onlineSawon = new JLabel("   \uC811\uC18D\uC911\uC778 \uC0AC\uC6D0");
		onlineSawon.setFont(new Font("援대┝", Font.PLAIN, 20));
		onlineSawon.setBounds(466, 77, 171, 40);
		contentPane.add(onlineSawon);


		sawonList = new JList();
		sawonList.setBounds(466, 120, 171, 150);
		contentPane.add(sawonList);


		JLabel deparLabel = new JLabel("\uC870\uC9C1\uB3C4");
		deparLabel.setFont(new Font("援대┝", Font.PLAIN, 22));
		deparLabel.setBounds(38, 27, 73, 40);
		contentPane.add(deparLabel);


		
		JButton createButten = new JButton("\uBC29\uB9CC\uB4E4\uAE30");
		createButten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		createButten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		createButten.setFont(new Font("援대┝", Font.PLAIN, 18));
		createButten.setBounds(466, 364, 171, 44);
		contentPane.add(createButten);


		JButton joinButten = new JButton("\uC785\uC7A5\uD558\uAE30");
		joinButten.setFont(new Font("援대┝", Font.PLAIN, 18));
		joinButten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		joinButten.setBounds(466, 424, 171, 44);
		contentPane.add(joinButten);


		JButton logoutButten = new JButton("\uB85C\uADF8\uC544\uC6C3");
		logoutButten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		logoutButten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		logoutButten.setFont(new Font("援대┝", Font.PLAIN, 18));
		logoutButten.setBounds(466, 483, 171, 44);
		contentPane.add(logoutButten);


		JButton chatSearchButton = new JButton("");
		chatSearchButton.setIcon(new ImageIcon(MainViewFrame.class.getResource("/client/icon/search.png")));
		chatSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		chatSearchButton.setBounds(597, 298, 40, 40);
		contentPane.add(chatSearchButton);


		chatSearchText = new JTextField();
		chatSearchText.setText("\uBC29\uAC80\uC0C9");
		chatSearchText.setColumns(10);
		chatSearchText.setBounds(466, 298, 130, 40);
		contentPane.add(chatSearchText);


		JButton empSearchButton = new JButton("");
		empSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
					// Add your search logic here
					String name = empSearchText.getText();
					try {


						
						reqMap.put("command", "selectByName");
						reqMap.put("name", empSearchText.getText());
						oos.writeObject(reqMap);

						oos.flush();
			}
			 catch (Exception ex) {
				ex.printStackTrace();
			 }
			}
		});
		empSearchButton.setIcon(new ImageIcon(MainViewFrame.class.getResource("/client/icon/search.png")));
		empSearchButton.setBounds(597, 27, 40, 40);
		contentPane.add(empSearchButton);

		
		empSearchText = new JTextField();
		empSearchText.setText("\uC0AC\uC6D0\uAC80\uC0C9");
		empSearchText.setBounds(467, 27, 130, 40);
		contentPane.add(empSearchText);
		empSearchText.setColumns(10);



		
	
		getContentPane().setLayout(null);
		
		empTable = new JTable(empModel);
		empTable.setBounds(54, 93, 359, 21);
		empTable.addMouseListener(new JTableMouseListener());
		empTable.getColumn("id").setWidth(0);
		empTable.getColumn("id").setMaxWidth(0);
		empTable.getColumn("id").setMinWidth(0);
		empTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		chatTable = new JTable(chatModel);
		chatTable.setBounds(64, 339, 359, 21);
		empTable.addMouseListener(new JTableMouseListener());		
		chatTable.getColumn("id").setWidth(0);
		chatTable.getColumn("id").setMaxWidth(0);
		chatTable.getColumn("id").setMinWidth(0);
		chatTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		JScrollPane scrollPane = new JScrollPane(empTable);
		scrollPane.setBounds(54, 120, 362, 172);
		contentPane.add(scrollPane);
		

		
		JScrollPane scrollPane_1 = new JScrollPane(chatTable);
		scrollPane_1.setBounds(64, 381, 359, 146);
		contentPane.add(scrollPane_1);

	
		
		setVisible(true);

		
		
		Thread t1 = new Thread(this);
		t1.start();


		
		
	}
	
	private class JTableMouseListener implements MouseListener {



		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		
			JTable jtable = (JTable)e.getSource();
			int row = jtable.getSelectedRow();
		
			if(e.getSource()==empTable) {
				tEmpId = (int) empModel.getValueAt(row, 5);
			
			}else if(e.getSource()==chatTable) {
				
				tChatId = (int)chatModel.getValueAt(row, 3);
				
			}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {

			while (true) {

				if (call) {
					String resCom = (String) resMap.get("command");

					switch (resCom) {

					case "afterMain":

						List<EmpDTO> empList = (List<EmpDTO>) resMap.get("empList");
						List<ChatInfo> roomList = (List<ChatInfo>) resMap.get("roomList");
				
						if (empList != null) {
			
							for(EmpDTO emp : empList) {
								System.out.println("emp 테이블 생성");
								System.out.println(emp);
								empModel.addRow(new Object[] {emp.getDepartmentName(),emp.getJobTitle(),emp.getName(),emp.getTel()});
								
							}
							
						}
						
						if(roomList != null) {
							
					
							for(ChatInfo myChat : roomList) {
								System.out.println("chat 테이블 생성");
								System.out.println(myChat);
								chatModel.addRow(new Object[] {myChat.getChatListDTO().getChatName(),""+myChat.getChatUserDTO().size()});
								
							}
							
							
							
						}
						

						
						
						//System.out.println(printChatList);
						if (empList != null) {
							for (EmpDTO emp : empList) {

							}
						}

						call = false;

						break;
						
					case "afterSelectByName":

						
						String printEmpList = "";
						List<EmpDTO> empList1 = (List<EmpDTO>) resMap.get("empList");
						if (empList1.size() != 0) {

							for (EmpDTO result : empList1) {

								printEmpList += result + "\n";

							}
							textArea.setText(printEmpList);
						} else {
							textArea.setText("寃��깋寃곌낵媛� �뾾�뒿�땲�떎.");

						}
						
						call=false;

						break;
						
					


					}

				}System.out.printf("");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}
}
