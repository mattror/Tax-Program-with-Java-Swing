package com.matt.system.taxation;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.matt.employee.DB;
import com.matt.employee.Employee;
import com.matt.utilities.Utility;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ListEmployee extends JPanel {
	Utility uti = new Utility();
	NewEmployee newEmp = new NewEmployee();
	
	private JScrollPane tableScroll;
	private JPanel pnSpace;
	private JButton btnDelete;
	private JTextField txtSearch;
	private String[] category = {"ID","Name","Gender"};
	@SuppressWarnings("rawtypes")
	private JComboBox cboCate;
	private JPanel pnSearch;
	private JPanel pnFind;
	private JPanel pnExchange;
	private JLabel lblSearch;
	public DefaultTableModel dfTable;
	
	private JTable table;
	private JTextField txtExchange;
	private JPanel pnBottom;
	@SuppressWarnings("rawtypes")
	static JComboBox cboRefresh;
	private JPanel pnButton;
	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ListEmployee() {
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.decode("#292929"));
		
		JLabel lblListEmployee = new JLabel("List Employee");
		lblListEmployee.setForeground(Color.WHITE);
		lblListEmployee.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblListEmployee.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblListEmployee, BorderLayout.NORTH);
		
		JPanel pnContent = new JPanel();
		pnContent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table.clearSelection();
				deleteBtn(false);
			}
		});
		uti.bgColor(pnContent);
		add(pnContent, BorderLayout.CENTER);
		pnContent.setLayout(new BorderLayout(0, 0));
		
		pnSearch = new JPanel();
		uti.bgColor(pnSearch);
		pnContent.add(pnSearch, BorderLayout.NORTH);
		pnSearch.setLayout(new BorderLayout(0, 0));
		
		pnFind = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnFind.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(20);
		uti.bgColor(pnFind);
		pnSearch.add(pnFind, BorderLayout.CENTER);
		
		pnExchange = new JPanel();
		uti.bgColor(pnExchange);
		pnSearch.add(pnExchange, BorderLayout.WEST);
		pnExchange.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		
		JLabel lblCurrentExchangeRate = new JLabel(" Current Exchange Rate");
		pnExchange.add(lblCurrentExchangeRate);
		
		txtExchange = new JTextField();
		txtExchange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DB db;
				try {
					db = new DB();
					readTable(Double.parseDouble(txtExchange.getText()),db.read());
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		txtExchange.setText("4000");
		pnExchange.add(txtExchange);
		uti.labelTextFieldStyle(lblCurrentExchangeRate, txtExchange);
		
		lblSearch = new JLabel("Search");
		uti.bgColor(lblSearch); uti.labelAppearance(lblSearch);
		pnFind.add(lblSearch);
		
		cboCate = new JComboBox(category);
		pnFind.add(cboCate);
		uti.comboBox(cboCate);
		
		// search
		txtSearch = new JTextField();
		pnFind.add(txtSearch);
		uti.textField(txtSearch);
		
		DocumentListener dl = new DocumentListener() {

			public void insertUpdate(DocumentEvent e) {
				updateFieldState();
			}

			public void removeUpdate(DocumentEvent e) {
				updateFieldState();
			}

			public void changedUpdate(DocumentEvent e) {
				updateFieldState();
			}
			protected void updateFieldState() {
				if(txtSearch.getText().equalsIgnoreCase("")) {
					defaultTable().setRowCount(0);
					DB db;
					try {
						db = new DB();
						readTable(Double.parseDouble(txtExchange.getText()),db.read());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else {
					DB db;
					try {
						db = new DB();
						List<Employee> list = db.search(cboCate.getSelectedItem().toString(), txtSearch.getText());
						readTable(Double.parseDouble(txtExchange.getText()),list);
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
					
				}
			}
			
		};
		txtSearch.getDocument().addDocumentListener(dl);
		
		pnSpace = new JPanel(); uti.bgColor(pnSpace);
		pnContent.add(pnSpace, BorderLayout.WEST);
		
		JPanel pnCenter = new JPanel();
		uti.bgColor(pnCenter);
		
		pnContent.add(pnCenter, BorderLayout.CENTER);
		pnCenter.setLayout(new GridLayout(1, 0, 0, 0));
		
		// table
		dfTable = defaultTable();
		table = new JTable(dfTable);
		uti.bgColor(table); 
		uti.tableSetWidth(table);
		DB db;
		try {
			db = new DB();
			readTable(Double.parseDouble(txtExchange.getText()),db.read());
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		table.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(e.getClickCount()==2) {
					double exchange = Double.parseDouble(txtExchange.getText());
					int index = table.getSelectedRow();
					TableModel tbModel = table.getModel();
					
					String id = tbModel.getValueAt(index, 0).toString();
					String first = tbModel.getValueAt(index, 1).toString();
					String last = tbModel.getValueAt(index, 2).toString();
					String gender = tbModel.getValueAt(index, 3).toString();
					String dob = tbModel.getValueAt(index, 4).toString();
					String pob = tbModel.getValueAt(index, 5).toString();
					String address = tbModel.getValueAt(index, 6).toString();
					String email = tbModel.getValueAt(index, 7).toString();
					String phone = tbModel.getValueAt(index, 8).toString();
					String depart = tbModel.getValueAt(index, 9).toString();
					String pos = tbModel.getValueAt(index, 10).toString();
					String spouse = tbModel.getValueAt(index, 11).toString();
					String minor = tbModel.getValueAt(index, 12).toString();
					String salary = tbModel.getValueAt(index, 13).toString();
					String benefit = tbModel.getValueAt(index, 14).toString();
					
					InfoDialog ed = new InfoDialog();
					ed.buttonPane.setVisible(true);
					// put image icon to employee detail jdialog
					try {
						DB db = new DB();
						ed.lbImage.setIcon(uti.imageSize(db.getImage(id), 120, 120));
					} catch (Exception ex) {
						ed.lbImage.setIcon(uti.imageSize("src/main/resources/user.png", 100, 100));
					} 
					
					ed.txtId.setText(id); 
					ed.txtFirst.setText(first);
					ed.txtLast.setText(last);
					ed.cboGender.setSelectedIndex(gender.equalsIgnoreCase("F")?0:1);
					ed.txtPob.setText(pob);
					ed.txtAddress.setText(address);
					ed.txtEmail.setText(email);
					ed.txtPhone.setText(phone);
					ed.txtDepartment.setText(depart);
					ed.txtPosition.setText(pos);
					ed.cboSpouse.setSelectedIndex(spouse.equalsIgnoreCase("Yes")?0:1);
					ed.txtMinor.setText(minor);
					ed.txtSalary.setText(salary);
					ed.txtBenefit.setText(benefit);
					ed.txtExchange.setText(exchange+"");
					
					double s = Double.parseDouble(salary);
					double b = Double.parseDouble(benefit);
					boolean sp = spouse.equalsIgnoreCase("Yes")?true:false;
					int m = Integer.parseInt(minor);
					Employee em = new Employee(s,b,sp,m);
					
					double taxR = em.getTax(exchange);
					double taxU = taxR/exchange;
					double netR = em.getNetSalary(exchange);
					double netU = netR/exchange;
					ed.lbTaxSalaryR.setText(uti.currencySign(taxR, "Kh"));
					ed.lbTaxSalaryU.setText(uti.currencySign(taxU, "us"));
					ed.lbNetSalaryR.setText(uti.currencySign(netR, "kh"));
					ed.lbNetSalaryU.setText(uti.currencySign(netU, "us"));
					
					try {
						ed.txtDob.setDate(uti.date(dob));
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					
					ed.show(); 
				}
				
				// if row is selected
				if(table.getSelectedRow() !=-1) {
					deleteBtn(true);
				}
			}
		});
		
		tableScroll = new JScrollPane(table);
		tableScroll.getViewport().setBackground(Color.decode("#323232"));
		pnCenter.add(tableScroll);
		
		pnSpace = new JPanel(); 
		uti.bgColor(pnSpace);
		pnContent.add(pnSpace, BorderLayout.EAST);
		
		pnBottom = new JPanel();
		pnBottom.setBackground(Color.decode("#292929"));
		pnContent.add(pnBottom, BorderLayout.SOUTH);
		pnBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		pnButton = new JPanel();
		pnBottom.add(pnButton);
		pnButton.setLayout(new GridLayout(0, 1, 0, 0));
		
		// delete
		btnDelete = new JButton("Delete");
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 24));
		uti.btnColor(btnDelete);
		btnDelete.setBackground(Color.decode("#e74c3c"));
		deleteBtn(false);
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object[] opts = {"Yes","Cancel"};
				int conf = JOptionPane.showOptionDialog(null,"Are you to delete?","Delete",
						JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,
						null,opts,opts[1]);
				if(conf == JOptionPane.YES_OPTION) {
					int[] index = table.getSelectedRows();
					TableModel tbModel = table.getModel();
					
					try {
						String id;
						for(int i=0; i<index.length; i++) {
							id = tbModel.getValueAt(index[i], 0).toString();
							DB db = new DB();
							db.delete(id);
						}
						
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					cboRefresh.setSelectedIndex(1);
				}
			}
		});
		pnButton.add(btnDelete, BorderLayout.CENTER);
		
		// refresh
		String[] f = {"0","1"};
		cboRefresh = new JComboBox(f);
		cboRefresh.setVisible(false);
		cboRefresh.setSelectedIndex(0);
		cboRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cboRefresh.getSelectedIndex()==1) {
					DB db;
					try {
						db = new DB();
						readTable(Double.parseDouble(txtExchange.getText()),db.read());
					cboRefresh.setSelectedIndex(0);
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		pnBottom.add(cboRefresh);
		
	}
	
	// delete button
	public void deleteBtn(boolean visible) {
		btnDelete.setEnabled(visible);
		btnDelete.setVisible(visible);
		pnButton.setVisible(visible);
	}
	// default table
	public DefaultTableModel defaultTable() {
		DefaultTableModel dtm = new DefaultTableModel(){
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		dtm.addColumn("ID");
		dtm.addColumn("FIRST NAME");
		dtm.addColumn("LAST NAME");
		dtm.addColumn("GENDER");
		dtm.addColumn("DOB");
		dtm.addColumn("POB");
		dtm.addColumn("ADDRESS");
		dtm.addColumn("EMAIL");
		dtm.addColumn("PHONE");
		dtm.addColumn("DEPARTMENT");
		dtm.addColumn("POSITION");
		dtm.addColumn("SPOUSE");
		dtm.addColumn("MINOR CHILDREN");
		dtm.addColumn("SALARY");
		dtm.addColumn("BENEFIT");
		dtm.addColumn("TAX ON SALARY");
		dtm.addColumn("TAX ON SALARY Riel");
		dtm.addColumn("NET SALARY");
		dtm.addColumn("NET SALARY Riel");
		
		return dtm;
	}
	
	// read
	public void readTable(double exchange, List<Employee> list) {
		String[] data;
		dfTable.setRowCount(0);
		for(Employee emp : list) {
			data = emp.listEmpRowData(exchange);
			dfTable.addRow(data);
		}
	}

}
