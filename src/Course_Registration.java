import java.awt.EventQueue;
import java.sql.PreparedStatement;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.ImageIcon;

public class Course_Registration extends JFrame {

	private Connection connection = null;
	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtContact;
	private JTextField txtCourse;
	private JTable table;
	private JTextField txtSearchName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Course_Registration frame = new Course_Registration();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	private void loadTable() {
		
		try {

			String query = "select ID, Name, Contact, Course from StudentInfo";
			PreparedStatement pst = connection.prepareStatement(query);
			
			ResultSet rs = pst.executeQuery();
			
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			txtID.setText(null);
			txtName.setText(null);
			txtContact.setText(null);
			txtCourse.setText(null);
			
			pst.close();
			rs.close();
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
	}
	
	

	/**
	 * Create the frame.
	 */
	public Course_Registration() {
		
		connection = SqlConnection.dbConnector(); 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 676, 548);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(282, 112, 356, 386);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblNewLabel.setBounds(23, 209, 29, 24);
		contentPane.add(lblNewLabel);
		
		txtID = new JTextField();
		txtID.setBounds(90, 212, 86, 20);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Name: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblNewLabel_1.setBounds(20, 245, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblContact = new JLabel("Contact: ");
		lblContact.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblContact.setBounds(20, 285, 60, 14);
		contentPane.add(lblContact);
		
		JLabel lblCourse = new JLabel("Course:");
		lblCourse.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblCourse.setBounds(20, 324, 60, 14);
		contentPane.add(lblCourse);
		
		txtName = new JTextField();
		txtName.setBounds(90, 243, 86, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtContact = new JTextField();
		txtContact.setBounds(90, 282, 86, 20);
		contentPane.add(txtContact);
		txtContact.setColumns(10);
		
		txtCourse = new JTextField();
		txtCourse.setBounds(90, 321, 86, 20);
		contentPane.add(txtCourse);
		txtCourse.setColumns(10);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "insert into StudentInfo (ID, Name, Contact, Course) values (?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					
					
					pst.setString(1, txtID.getText());
					pst.setString(2, txtName.getText());
					pst.setString(3, txtContact.getText());
					pst.setString(4, txtCourse.getText());

					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Saved Succesfully!");

					pst.close();
				
			

					
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e2);

				}
				
				loadTable();
				
			}
			
		});
		btnAdd.setBounds(34, 385, 89, 23);
		contentPane.add(btnAdd);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "update StudentInfo set ID='" + txtID.getText() +
							"',Name='" + txtName.getText() + 
							"',Contact='" + txtContact.getText() + 
					        "',Course='" + txtCourse.getText() + 
					        "'where ID='"+ txtID.getText() +"'";
					
					
					PreparedStatement pst = connection.prepareStatement(query);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Updated Succesfully!");
					
					
				} catch (Exception e2) {
					// TODO: handle exception
					
					JOptionPane.showMessageDialog(null, e2.getMessage());
					
				}
				
				loadTable();
				
			}
			});
		btnUpdate.setBounds(145, 385, 89, 23);
		contentPane.add(btnUpdate);
		
		JButton btnDELETE = new JButton("DELETE");
		btnDELETE.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		btnDELETE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int action = JOptionPane.showConfirmDialog(null, "Are You sure to delete?", "Delete", JOptionPane.YES_NO_OPTION);
				//Yes = 0  No = 1

				if(action == 0) {
					
					try {
						
						String query = "delete from StudentInfo where ID='" +
						txtID.getText() +"'";
						
						PreparedStatement pst = connection.prepareStatement(query);
						pst.execute();
						JOptionPane.showMessageDialog(null, "Removed Succesfully!");
						pst.close();
						
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}
					
				}
				
				loadTable();
			}
		});
		btnDELETE.setBounds(34, 437, 89, 23);
		contentPane.add(btnDELETE);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtID.setText("");
				txtName.setText("");
				txtContact.setText("");
				txtCourse.setText("");
				
				JOptionPane.showMessageDialog(null,"The text fields have been cleared!");
				
			}
		});
		btnClear.setBounds(145, 437, 89, 23);
		contentPane.add(btnClear);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				

				try {
					
					int row = table.getSelectedRow();
					String id = (table.getModel().getValueAt(row, 0)).toString();
					
					String query =  "select * from StudentInfo where ID='" + id + "'";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()) {
						
						txtID.setText(rs.getString("ID"));
						txtName.setText(rs.getString("Name"));
						txtContact.setText(rs.getString("Contact"));
						txtCourse.setText(rs.getString("Course"));
						
						
						
					}
					
					rs.close();
					pst.close();
					
					
				} catch (Exception e4) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e4);
				}
				
			}
		});
		scrollPane.setViewportView(table);
		//table.setBounds(252, 97, 382, 261);
		//contentPane.add(table);
		
		txtSearchName = new JTextField();
		txtSearchName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					
					String query = "select ID, Name, Contact, Course from StudentInfo where Name=?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, txtSearchName.getText());
					ResultSet rs = pst.executeQuery();
					
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					pst.close();
					rs.close();
					
					
					
				} catch (Exception e6) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e6.getMessage());
				}
				
			}
		});
		txtSearchName.setBounds(479, 77, 159, 24);
		contentPane.add(txtSearchName);
		txtSearchName.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Search By Name");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 22));
		lblNewLabel_2.setBounds(282, 72, 187, 24);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\Emiliano\\Desktop\\School\\Advanced_OOP_Java\\Student_Course_Registration\\img\\laSalle.png"));
		lblNewLabel_3.setBounds(10, 0, 218, 198);
		contentPane.add(lblNewLabel_3);
		
		
		
		loadTable();
	}
}
