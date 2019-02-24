package com.matt.system.taxation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.matt.employee.DB;
import com.matt.employee.Employee;
import com.matt.utilities.FileExplorer;
import com.matt.utilities.Utility;
import com.toedter.calendar.JDateChooser;

import java.awt.Toolkit;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.border.LineBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
@SuppressWarnings("serial")
public class InfoDialog extends JDialog {
	Utility uti = new Utility();
	String imagePath;

	private JPanel contentPanel;
	private JScrollPane contentScroll;
	private JPanel pnCol1;
	private JPanel pnCol2;
	private JPanel pnCol1Detail;
	private JPanel pnCol2Detail;
	private JLabel lbFirst = new JLabel("First Name");
	private JLabel lbLast = new JLabel("Last Name");
	private JLabel lbId = new JLabel("ID");
	private JLabel lbGender = new JLabel("Gender");
	private JLabel lbDob = new JLabel("Date of Birth");
	private JLabel lbAddress = new JLabel("Current Address");
	private JLabel lbPhone = new JLabel("Phone");
	private JLabel lbEmail = new JLabel("Email");
	private JLabel lbDepartment = new JLabel("Department");
	private JLabel lbPosition = new JLabel("Position");
	private JLabel lbSpouse = new JLabel("Spouse");
	private JLabel lbMinor = new JLabel("Minor Children");
	private JLabel lbSalary = new JLabel("Salary($)");
	private JLabel lbBenefit = new JLabel("Benefit($)");
	private JLabel lbPob = new JLabel("POB");
	public JLabel lbExchange = new JLabel("Exchange Rate");
	private JLabel lbTitle;
	public JLabel lbTaxSalaryU,lbTaxSalaryR, lbNetSalaryU,lbNetSalaryR;
	
	public JTextField txtId;
	public JTextField txtFirst;
	public JTextField txtLast;
	public JDateChooser txtDob;
	public JTextField txtAddress;
	public JTextField txtPhone;
	public JTextField txtEmail;
	public JTextField txtDepartment;
	public JTextField txtPosition;
	public JTextField txtMinor;
	public JTextField txtSalary;
	public JTextField txtBenefit;
	public JTextField txtPob;
	public JTextField txtExchange;
	public JLabel lbImage;
	
	String[] g= {"F","M"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JComboBox cboGender = new JComboBox(g);
	String[] s = {"Yes","No"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JComboBox cboSpouse = new JComboBox(s);
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnDone;
	
	private JPanel pnCenter = new JPanel();
	private JPanel pnImage;
	public JButton btnBrowse;
	private JPanel pnMainImage;
	public JPanel buttonPane;
	private JPanel pnButton;
	private JPanel pnSpace;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			InfoDialog dialog = new InfoDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	
	@SuppressWarnings("unchecked")
	public InfoDialog() {
		
		setTitle("Employee Detail");
		setResizable(false);
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/favicon.png"));
		setBounds(350, 5,700,710); 
		BorderLayout borderLayout = new BorderLayout();
		getContentPane().setLayout(borderLayout);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				txtFirst.requestFocus();
			}
		});
		
		pnMainImage = new JPanel();
		getContentPane().add(pnMainImage, BorderLayout.NORTH);
		pnMainImage.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		bgColor(pnMainImage);
		
		// image
		pnImage = new JPanel();
		pnMainImage.add(pnImage);
		bgColor(pnImage);
		pnImage.setLayout(new BorderLayout(0, 5));
		lbImage = new JLabel("");
		lbImage.setBorder(new LineBorder(Color.LIGHT_GRAY));
		lbImage.setIcon(uti.imageSize("src/main/resources/user.png", 100, 100));
		
		pnImage.add(lbImage, BorderLayout.CENTER);
		
		// browse
		btnBrowse = new JButton("Change");
		btnBrowse.setBorder(new LineBorder(Color.LIGHT_GRAY));
		btnBrowse.setForeground(Color.WHITE);
		uti.btnColor(btnBrowse);
		btnBrowse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//uti.imagePicker(pnImage, lbImage);
				FileExplorer fe = new FileExplorer();
				@SuppressWarnings("static-access")
				File file = fe.chooseFileWithJavaFXDialog();
				uti.paintLabel(file, lbImage);
				uti.setPath(file+"");
				imagePath = uti.getPath();
				System.out.println("CHOSEN FILE: " + imagePath);
			}
		});
		pnImage.add(btnBrowse, BorderLayout.SOUTH);
		
		getContentPane().add(pnCenter, BorderLayout.CENTER);
		pnCenter.setLayout(new BorderLayout(0, 0));
		
		// content panel 
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		bgColor(contentPanel);
		
		contentScroll = new JScrollPane(contentPanel);
		pnCenter.add(contentScroll, BorderLayout.CENTER);
		
		// panel col 1
		pnCol1 = new JPanel();
		contentPanel.add(pnCol1);
		pnCol1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		bgColor(pnCol1); 
		
		// panel col detail 1
		pnCol1Detail = new JPanel();
		pnCol1.add(pnCol1Detail);
		pnCol1Detail.setLayout(new GridLayout(0, 2, 0, 20));
		bgColor(pnCol1Detail);
		
		// panel col 2
		pnCol2 = new JPanel();
		contentPanel.add(pnCol2);
		bgColor(pnCol2);
		pnCol2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		// panel col detail 2
		pnCol2Detail = new JPanel();
		pnCol2.add(pnCol2Detail);
		pnCol2Detail.setLayout(new GridLayout(0, 2, 0, 20));
		bgColor(pnCol2Detail);
		
		// id
		pnCol1Detail.add(lbId);
		txtId = new JTextField();
		pnCol1Detail.add(txtId);
		txtId.setEditable(false);
		uti.labelTextFieldStyle(lbId,txtId);
		bgColor(txtId);
		
		// first name
		pnCol1Detail.add(lbFirst);
		txtFirst = new JTextField();
		pnCol1Detail.add(txtFirst);
		uti.labelTextFieldStyle(lbFirst,txtFirst);
		bgColor(txtFirst);
		
		// last 
		pnCol1Detail.add(lbLast);
		txtLast = new JTextField();
		pnCol1Detail.add(txtLast);
		uti.labelTextFieldStyle(lbLast,txtLast);
		bgColor(txtLast);
		
		// gender
		pnCol1Detail.add(lbGender);
		pnCol1Detail.add(cboGender);
		uti.labelAppearance(lbGender);
		uti.comboBox(cboGender);
		bgColor(cboGender);
		
		// dob
		pnCol1Detail.add(lbDob);
		txtDob = uti.datePicker();
		bgColor(txtDob);
		pnCol1Detail.add(txtDob);
		uti.labelAppearance(lbDob);
		
		// pob
		pnCol1Detail.add(lbPob);
		txtPob = new JTextField();
		pnCol1Detail.add(txtPob);
		uti.labelTextFieldStyle(lbPob,txtPob);
		bgColor(txtPob);
		
		// address
		pnCol1Detail.add(lbAddress);
		txtAddress = new JTextField();
		pnCol1Detail.add(txtAddress);
		uti.labelTextFieldStyle(lbAddress,txtAddress);
		bgColor(txtAddress);
		
		// phone
		pnCol1Detail.add(lbPhone);
		txtPhone = new JTextField();
		pnCol1Detail.add(txtPhone);
		uti.labelTextFieldStyle(lbPhone,txtPhone);
		bgColor(txtPhone);
		
		// email
		pnCol1Detail.add(lbEmail);
		txtEmail = new JTextField();
		pnCol1Detail.add(txtEmail);
		uti.labelTextFieldStyle(lbEmail,txtEmail);
		bgColor(txtEmail);
		
		// department
		pnCol1Detail.add(lbDepartment);
		txtDepartment = new JTextField();
		pnCol1Detail.add(txtDepartment);
		uti.labelTextFieldStyle(lbDepartment,txtDepartment);
		bgColor(txtDepartment);
		
		// position
		pnCol2Detail.add(lbPosition);
		txtPosition = new JTextField();
		pnCol2Detail.add(txtPosition);
		uti.labelTextFieldStyle(lbPosition,txtPosition);
		bgColor(txtPosition);
		
		// spouse
		pnCol2Detail.add(lbSpouse); 
		uti.labelAppearance(lbSpouse);
		//cboSpouse.setSelectedIndex(1);
		pnCol2Detail.add(cboSpouse);
		uti.comboBox(cboSpouse);
		bgColor(cboSpouse);
		
		// minor
		pnCol2Detail.add(lbMinor);
		txtMinor = new JTextField();
		pnCol2Detail.add(txtMinor);
		uti.labelTextFieldStyle(lbMinor,txtMinor);
		bgColor(txtMinor);
		
		// salary
		pnCol2Detail.add(lbSalary);
		txtSalary = new JTextField();
		pnCol2Detail.add(txtSalary);
		uti.labelTextFieldStyle(lbSalary,txtSalary);
		bgColor(txtSalary);
		
		// benefit
		pnCol2Detail.add(lbBenefit);
		txtBenefit = new JTextField();
		pnCol2Detail.add(txtBenefit);
		uti.labelTextFieldStyle(lbBenefit,txtBenefit);
		bgColor(txtBenefit);
		
		pnCol2Detail.add(lbExchange);
		txtExchange = new JTextField();
		txtExchange.setEditable(false);
		pnCol2Detail.add(txtExchange);
		uti.labelTextFieldStyle(lbExchange,txtExchange);
		bgColor(txtExchange);
			
		// tax on salary
		lbTitle = new JLabel("Tax On Salary"); 
		uti.labelAppearance(lbTitle);
		pnCol2Detail.add(lbTitle);
		lbTaxSalaryU = new JLabel(uti.currencySign(0.0, "us")); 
		uti.labelAppearance(lbTaxSalaryU);
		pnCol2Detail.add(lbTaxSalaryU);
				
		lbTitle = new JLabel(""); 
		pnCol2Detail.add(lbTitle);
		lbTaxSalaryR = new JLabel(uti.currencySign(0.0, "Kh")); 
		uti.labelAppearance(lbTaxSalaryR);
		pnCol2Detail.add(lbTaxSalaryR);
				
		// net salary
		lbTitle = new JLabel("Net Salary"); 
		uti.labelAppearance(lbTitle);
		pnCol2Detail.add(lbTitle);
		lbNetSalaryU = new JLabel(uti.currencySign(0.0, "us")); 
		uti.labelAppearance(lbNetSalaryU);
		pnCol2Detail.add(lbNetSalaryU);
				
		lbTitle = new JLabel("");
		pnCol2Detail.add(lbTitle);
		lbNetSalaryR= new JLabel(uti.currencySign(0.0, "Kh")); 
		uti.labelAppearance(lbNetSalaryR);
		pnCol2Detail.add(lbNetSalaryR);
				
		bgColor(contentScroll);

		{
					
			buttonPane = new JPanel();
			buttonPane.setVisible(false);
			bgColor(buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BorderLayout(0, 0));
			
			pnButton = new JPanel();
			bgColor(pnButton);
			buttonPane.add(pnButton);
			pnButton.setLayout(new GridLayout(0, 3, 20, 20));
			
			// panel space
			pnSpace = new JPanel();
			bgColor(pnSpace);
			buttonPane.add(pnSpace, BorderLayout.WEST);
			
			pnSpace = new JPanel();
			bgColor(pnSpace);
			buttonPane.add(pnSpace, BorderLayout.EAST);
			
			pnSpace = new JPanel();
			bgColor(pnSpace);
			buttonPane.add(pnSpace, BorderLayout.NORTH);
			
			pnSpace = new JPanel();
			bgColor(pnSpace);
			buttonPane.add(pnSpace, BorderLayout.SOUTH);
			
			// update button
			btnUpdate = new JButton("Update");
			uti.labelAppearance(btnUpdate); 
			uti.btnColor(btnUpdate);
			btnUpdate.setBackground(Color.decode("#F7A541"));
			btnUpdate.addMouseListener(new MouseAdapter() {
			 	@Override
			 	public void mouseClicked(MouseEvent e) {
			 		try {
						DB db = new DB();
						//System.out.println("Image path: "+imagePath);
						db.update(getText(imagePath));
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			 		refresh();
			 		dispose();
			 	}
			});
			pnButton.add(btnUpdate);
			
			// delete button
			btnDelete = new JButton("Delete");
			uti.btnColor(btnDelete);
			uti.labelAppearance(btnDelete); 
			btnDelete.setBackground(Color.decode("#e74c3c"));
			btnDelete.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Object[] opts = {"Yes","Cancel"};
					if(!txtId.getText().equalsIgnoreCase("")) {
						int confirm = JOptionPane.showOptionDialog(getContentPane(),"Are you to delete?","Delete",
								JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,
								null,opts,opts[1]);
						if(confirm == JOptionPane.YES_OPTION) {
							try {
									DB db = new DB();
									db.delete(txtId.getText());
									refresh();
									dispose();
								} catch (ClassNotFoundException e1) {
									e1.printStackTrace();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}
						}
					}	
			});
			pnButton.add(btnDelete);
			
			// done button
			btnDone = new JButton("Done");
			btnDone.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					dispose();
				}
			});
			uti.btnColor(btnDone);
			uti.labelAppearance(btnDone); 
			btnDone.setBackground(Color.decode("#17C680"));
			pnButton.add(btnDone);
		}
	}
	
	// refresh
	public void refresh() {
		ListEmployee.cboRefresh.setSelectedIndex(1);
	}
	
	// get text
	public Employee getText(String image) {
		Employee em = new Employee();
		em.setId(txtId.getText());
		em.setFirst_name(txtFirst.getText());
		em.setLast_name(txtLast.getText());
		em.setGender(cboGender.getSelectedItem().toString().charAt(0));
		em.setAddress(txtAddress.getText());
		
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String dob = df.format(txtDob.getDate());
		em.setDob(dob);
		em.setPob(txtPob.getText());
		
		em.setPhone(txtPhone.getText());
		em.setEmail(txtEmail.getText());
		em.setDepartment(txtDepartment.getText());
		em.setPosition(txtPosition.getText());
		
		boolean hs = cboSpouse.getSelectedIndex()==0?true:false;
		em.setHasSpouse(hs);
		
		em.setMinorChild(Integer.parseInt(txtMinor.getText()));
		double salary = Double.parseDouble(txtSalary.getText());
		em.setSalary(salary);
		double benefit = Double.parseDouble(txtBenefit.getText());
		em.setBenefit(benefit);
		
		if(image==null) {
			try {
				DB db = new DB();
				image = db.getImage(em.getId());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		em.setImage(image);
		
		return em;
	}
	
	// background color
	public void bgColor(JComponent jc) {
		jc.setBackground(Color.decode("#505050"));
	}
	
}
