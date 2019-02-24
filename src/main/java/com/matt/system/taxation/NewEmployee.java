package com.matt.system.taxation;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.matt.employee.DB;
import com.matt.employee.Employee;
import com.matt.utilities.FileExplorer;
import com.matt.utilities.Utility;
import com.toedter.calendar.JDateChooser;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class NewEmployee extends JPanel {
	private JPanel pnCol2;
	private JPanel pnCol1;
	private JPanel pnAdd;
	private JPanel pnAddNew;
	private JPanel pnSpouse;
	private JPanel pnTable;
	private JPanel pnSearch;
	private JPanel pnEmpty;
	private JPanel pnNew;
	private JPanel pnImage;
	private JPanel pnBrowse;
	
	@SuppressWarnings("rawtypes")
	private JComboBox cboGender;
	@SuppressWarnings("rawtypes")
	private JComboBox cboSort;
	
	private JTextField txtFirst;
	private JTextField txtLast;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JTextField txtDepartment;
	private JTextField txtPosition;
	private JTextField txtBenefit;
	private JTextField txtPob;
	private JTextField txtAddress;
	private JTextField txtMinor;
	private JTextField txtSalary;
	private JTextField txtSearch;
	
	private JLabel lbImage;
	private JLabel lbMinor;
	private JLabel lbYes;
	private JLabel lbNo;
	
	private JButton btnSave;
	private JButton btnClear;
	private JButton btnBrowse;
	
	private JDateChooser txtDate;
	
	private JTable table;
	private JScrollPane tableScroll;
	private JScrollPane newScroll;
	
	Utility uti = new Utility();
	private JRadioButton rdYes;
	private JRadioButton rdNo;
	private ButtonGroup btnGroup;
	private File file;
	private DefaultTableModel tm;
	private DefaultTableModel tbModel;
	
	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NewEmployee() {
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.decode("#292929"));
		
		JLabel lblNewEmployee = new JLabel("New Employee");
		lblNewEmployee.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewEmployee.setForeground(Color.WHITE);
		lblNewEmployee.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewEmployee, BorderLayout.NORTH);
		
		JPanel pnCenter = new JPanel();
		pnCenter.setBackground(Color.decode("#323232"));
		add(pnCenter, BorderLayout.CENTER);
		pnCenter.setLayout(new GridLayout(0, 2, 0, 0));
		
		pnCol1 = new JPanel();
		uti.bgColor(pnCol1);
		pnCenter.add(pnCol1);
		pnCol1.setLayout(new BorderLayout(0, 0));
		
		pnSearch = new JPanel();
		uti.bgColor(pnSearch);
		pnCol1.add(pnSearch, BorderLayout.NORTH);
		
		String[] sort= {"ID","Name","Gender"};
		cboSort = new JComboBox(sort);
		uti.comboBox(cboSort); uti.labelAppearance(cboSort);
		pnSearch.add(cboSort);
		
		// search
		txtSearch = new JTextField();
		uti.textField(txtSearch);
		pnSearch.add(txtSearch);
		
		DocumentListener dl = new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				searchEmp();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				searchEmp();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				searchEmp();
			}
			
			private void searchEmp() {
				if(!txtSearch.getText().equals("")) {
					try {
						DB db = new DB();
						List<Employee> list = db.search(cboSort.getSelectedItem().toString(), txtSearch.getText());
						tbModel.setRowCount(0);
						for(Employee em : list) {
							tbModel.addRow(addColData(em));
						}
						if(!list.isEmpty()){
							table.addRowSelectionInterval(0, 0);
		        			table.setSelectionBackground(SystemColor.textHighlight);
		        			table.setSelectionForeground(SystemColor.text);
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else {
					tableModel().setRowCount(0);
					try {
						DB db = new DB();
						for(Employee em : db.read()) {
							tbModel.addRow(addColData(em));
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		txtSearch.getDocument().addDocumentListener(dl);
		
		pnTable = new JPanel();
		uti.bgColor(pnTable);
		pnCol1.add(pnTable, BorderLayout.CENTER);
		
		tbModel = tableModel();
		// table
		table = new JTable(tbModel); 
		table.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				TableModel tbModel = table.getModel();
				String id = tbModel.getValueAt(index, 0).toString();
				if(e.getClickCount()==2) {
					InfoDialog inf = new InfoDialog();
					try {
						DB db = new DB();
						String gender;
						for(Employee em : db.read(id)) {
							inf.txtId.setText(id);
							inf.txtFirst.setText(em.getFirst_name());
							inf.txtLast.setText(em.getLast_name());
							gender = em.getGender()+"";
							inf.cboGender.setSelectedIndex(gender.equalsIgnoreCase("F")?0:1);
							inf.txtEmail.setText(em.getEmail());
							inf.txtPhone.setText(em.getPhone());
							inf.lbImage.setIcon(uti.imageSize(em.getImage(), 100, 120));
							inf.txtDob.setDate(uti.date(em.getDob()));
							inf.txtPob.setText(em.getPob());
							inf.txtAddress.setText(em.getAddress());
							inf.txtDepartment.setText(em.getDepartment());
							inf.txtPosition.setText(em.getPosition());
							inf.cboSpouse.setSelectedIndex(em.isHasSpouse()?0:1);
							inf.txtMinor.setText(em.getMinorChild()+"");
							inf.txtSalary.setText(em.getSalary()+"");
							inf.txtBenefit.setText(em.getBenefit()+"");
							uti.disableInfoDialogField(inf);
						}
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}catch (ParseException e1) {
						e1.printStackTrace();
					}
					
					inf.show();
				}
			}
		});
		uti.bgColor(table); uti.labelAppearance(table);
		uti.tableSetWidth(table);
		
		try {
			DB db = new DB();
			tm.setRowCount(0);
			for(Employee em : db.read()) {
				tm.addRow(addColData(em));
			}
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		// scroll pane
		tableScroll = new JScrollPane(table);
		tableScroll.getViewport().setBackground(Color.decode("#323232"));
		pnTable.setLayout(new BorderLayout(0, 0));
		
		pnEmpty = new JPanel();
		pnTable.add(pnEmpty, BorderLayout.WEST);
		uti.bgColor(pnEmpty);
		pnTable.add(tableScroll);
		
		pnCol2 = new JPanel();
		uti.bgColor(pnCol2);
		pnCol2.setLayout(new BorderLayout(0, 0));
		pnCenter.add(pnCol2);
		
		// panel new
		pnNew = new JPanel();
		pnNew.setLayout(new BorderLayout(0, 0));
		uti.bgColor(pnNew);
		
		pnAdd = new JPanel();
		pnAdd.setLayout(new FlowLayout());
		uti.bgColor(pnAdd);
		pnNew.add(pnAdd);
		
		//scroll pane
		newScroll = new JScrollPane(pnNew);
		newScroll.setBorder(null);
		pnCol2.add(newScroll, BorderLayout.CENTER);
		
		// panel image
		pnImage = new JPanel();
		uti.bgColor(pnImage);
		pnNew.add(pnImage, BorderLayout.NORTH);
		pnImage.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		// panel browse
		pnBrowse = new JPanel();
		pnImage.add(pnBrowse);
		pnBrowse.setLayout(new BorderLayout(0, 0));
		
		// label image
		lbImage = new JLabel("");
		pnBrowse.add(lbImage, BorderLayout.NORTH);
		lbImage.setIcon(uti.imageSize("src/main/resources/user.png", 100, 100));
		
		// browse
		btnBrowse = new JButton("Change");
		uti.btnColor(btnBrowse);
		uti.labelAppearance(btnBrowse);
		btnBrowse.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent e) {
				//cal.imagePicker(pnBrowse, lbImage);
				FileExplorer fe = new FileExplorer();
				file = fe.chooseFileWithJavaFXDialog();
				uti.paintLabel(file, lbImage);
				uti.setPath(file+"");
			}
		});
		pnBrowse.add(btnBrowse);
		
		pnAddNew = new JPanel();
		pnAddNew.setLayout(new GridLayout(0, 2, 0, 10));
		uti.bgColor(pnAddNew);
		pnAdd.add(pnAddNew);
		
		// first name
		pnAddNew.add(uti.labelTitle("First Name"));
		
		txtFirst = new JTextField();
		uti.textField(txtFirst); 
		pnAddNew.add(txtFirst);
		
		// last name
		pnAddNew.add(uti.labelTitle("Last Name"));
		
		txtLast = new JTextField();
		uti.textField(txtLast); 
		pnAddNew.add(txtLast);
		
		pnAddNew.add(uti.labelTitle("Gender"));
		
		String g[]= {"F","M"};
		cboGender = new JComboBox(g);
		uti.comboBox(cboGender);
		pnAddNew.add(cboGender);
		
		// date
		pnAddNew.add(uti.labelTitle("Date of Birth"));
		txtDate = uti.datePicker();  
		//txtDate.getJFormattedTextField().setEditable(true);
		uti.bgColor(txtDate); uti.labelAppearance(txtDate);
		pnAddNew.add(txtDate);
		
		// place of birth
		pnAddNew.add(uti.labelTitle("Place of Birth"));
		txtPob = new JTextField();
		uti.bgColor(txtPob); uti.labelAppearance(txtPob);
		pnAddNew.add(txtPob);
		
		pnAddNew.add(uti.labelTitle("Current Address"));
		txtAddress = new JTextField();
		uti.bgColor(txtAddress); uti.labelAppearance(txtAddress);
		pnAddNew.add(txtAddress);
		
		// phone
		pnAddNew.add(uti.labelTitle("Phone"));
		txtPhone = new JTextField();
		uti.textField(txtPhone);
		uti.allowNumber(txtPhone);
		pnAddNew.add(txtPhone);
		
		// email
		pnAddNew.add(uti.labelTitle("Email"));
		txtEmail = new JTextField();
		uti.textField(txtEmail);
		pnAddNew.add(txtEmail);
		
		pnAddNew.add(uti.labelTitle("Department"));
		txtDepartment = new JTextField();
		uti.textField(txtDepartment);
		pnAddNew.add(txtDepartment);
		
		pnAddNew.add(uti.labelTitle("Position"));
		txtPosition = new JTextField();
		uti.textField(txtPosition);
		pnAddNew.add(txtPosition);
		
		pnAddNew.add(uti.labelTitle("Salary"));
		txtSalary = new JTextField();
		uti.textField(txtSalary); uti.allowNumber(txtSalary);
		pnAddNew.add(txtSalary);
		
		pnAddNew.add(uti.labelTitle("Benefit"));
		txtBenefit = new JTextField();
		uti.textField(txtBenefit); uti.allowNumber(txtBenefit);
		pnAddNew.add(txtBenefit);
		
		pnAddNew.add(uti.labelTitle("Spouse"));
		
		pnSpouse = new JPanel();
		uti.bgColor(pnSpouse);
		pnAddNew.add(pnSpouse);
		pnSpouse.setLayout(new GridLayout(0, 2, 0, 0));
		
		// yes no radio button
		lbYes = new JLabel(); 
		lbYes.setHorizontalAlignment(SwingConstants.CENTER);
		lbYes.setIcon(uti.icon("src/main/resources/diselected.png"));
		lbYes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				radioButton(lbYes,lbNo,true);
				rdYes.setSelected(true);
			}
		});
		lbYes.setText("Yes");
		uti.labelAppearance(lbYes); uti.bgColor(lbYes);
		pnSpouse.add(lbYes);
		
		lbNo = new JLabel();
		lbNo.setHorizontalAlignment(SwingConstants.CENTER);
		lbNo.setIcon(uti.icon("src/main/resources/selected.png"));
		lbNo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				radioButton(lbNo,lbYes,false);
				rdNo.setSelected(true);
			}
		});
		lbNo.setText("No");
		uti.labelAppearance(lbNo); uti.bgColor(lbNo);
		pnSpouse.add(lbNo);
		
		
		lbMinor = new JLabel("Minor Children");
		lbMinor.setVisible(false);
		uti.labelAppearance(lbMinor); uti.bgColor(lbMinor);
		pnAddNew.add(lbMinor);
		
		txtMinor = new JTextField();
		txtMinor.setVisible(false);
		uti.textField(txtMinor); uti.allowNumber(txtMinor);
		pnAddNew.add(txtMinor);
		
		// radio yes no
		rdYes = new JRadioButton("Yes");
		rdYes.setVisible(false);
		pnAddNew.add(rdYes);
		rdNo = new JRadioButton("No");
		rdNo.setSelected(true);
		rdNo.setVisible(false);
		pnAddNew.add(rdNo);
		
		btnGroup = new ButtonGroup();
		btnGroup.add(rdYes); btnGroup.add(rdNo);
		
		// save
		btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(checkText() == true) {
					try {
						DB db = new DB();
						db.save(getText());
						tm.addRow(addColData(getText()));
						clearText();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Please input all field !", "Warnning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		uti.labelAppearance(btnSave); 
		uti.btnColor(btnSave);
		btnSave.setBackground(Color.decode("#007ED9"));
		pnAddNew.add(btnSave);
		
		// clear
		btnClear = new JButton("Clear");
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clearText();
			}
		});
		uti.labelAppearance(btnClear); 
		uti.btnColor(btnClear);
		btnClear.setBackground(Color.decode("#E14B0F"));
		pnAddNew.add(btnClear);
		
	}
	
	
	
	// table model
	public DefaultTableModel tableModel() {
		tm = new DefaultTableModel() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		tm.addColumn("ID");
		tm.addColumn("FIRST NAME");
		tm.addColumn("LAST NAME");
		tm.addColumn("GENDER");
		tm.addColumn("EMAIL");
		tm.addColumn("PHONE");
		tm.addColumn("DOB");
		
		return tm;
	}
	
	// add column data
	public String[] addColData(Employee em) {
		String data[]={
				em.getId(),
				em.getFirst_name(),
				em.getLast_name(),
				em.getGender()+"",
				em.getEmail(),
				em.getPhone(),
				em.getDob()
		};
		return data;
	}
	
	// check text field
	public boolean checkText() {
		if(
			txtFirst.getText() !="" && 
			txtLast.getText() !="" && 
			txtPhone.getText() !="" &&
			txtEmail.getText() !="" &&
			txtDepartment.getText() !="" &&
			txtPosition.getText() !="" &&
			txtSalary.getText() !="" &&
			txtDate.getDate() !=null && 
			txtAddress.getText()!="" &&
			txtPob.getText()!="" &&
			file != null
	      ) {
			return true;
		}
		return false;
	}
	
	// clear text field
	public void clearText() {
		txtFirst.setText(""); 
		txtLast.setText("");
		cboGender.setSelectedIndex(0); 
		txtPhone.setText("");
		txtEmail.setText("");
		txtDepartment.setText("");
		txtPosition.setText("");
		txtSalary.setText(""); 
		txtBenefit.setText("");
		txtDate.setCalendar(null);
		radioButton(lbNo,lbYes,false);
		txtAddress.setText("");
		txtPob.setText("");
		table.clearSelection();
		lbImage.setIcon(uti.imageSize("src/main/resources/user.png", 100, 100)); 
	}
	
	// get text
	public Employee getText() {
		Employee em = new Employee();
		Utility uti = new Utility();
		
		em.setId(uti.generatedId());
		em.setFirst_name(txtFirst.getText());
		em.setLast_name(txtLast.getText());
		em.setGender(cboGender.getSelectedItem().toString().charAt(0));
		em.setAddress(txtAddress.getText());
		
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String dob = df.format(txtDate.getDate());
		em.setDob(dob);
		em.setPob(txtPob.getText());
		
		em.setPhone(txtPhone.getText());
		em.setEmail(txtEmail.getText());
		em.setDepartment(txtDepartment.getText());
		em.setPosition(txtPosition.getText());
		
		boolean hs = rdYes.isSelected()?true:false;
		em.setHasSpouse(hs);
		
		int minor = Integer.parseInt(txtMinor.getText().equals("")?"0":txtMinor.getText());
		em.setMinorChild(minor);
		double salary = Double.parseDouble(txtSalary.getText());
		em.setSalary(salary);
		double benefit = Double.parseDouble(txtBenefit.getText().equals("")?"0":txtBenefit.getText());
		em.setBenefit(benefit);
		
		em.setImage(file.getAbsolutePath());
		
		return em;
	}
	
	// radio button
	public void radioButton(JLabel yes, JLabel no, boolean visible) {
		yes.setIcon(uti.icon("src/main/resources/selected.png"));
		no.setIcon(uti.icon("src/main/resources/diselected.png"));
		lbMinor.setVisible(visible);
		txtMinor.setVisible(visible); txtMinor.setText("");
	}
}
