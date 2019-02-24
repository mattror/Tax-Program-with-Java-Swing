package com.matt.system.taxation;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.matt.employee.Employee;
import com.matt.utilities.Utility;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class Calculator extends JPanel {
	private JTextField txtSalary;
	private JTextField txtExchange;
	private JLabel lbExchange;
	private JTextField txtBenefit;
	private JLabel lbSpouse;
	private JPanel pnSpouse;
	private JLabel lbYes;
	private JLabel lbNo;
	private JLabel lbSpace;
	private JLabel lbMinor;
	private JTextField txtMinor;
	private JButton btnCal;
	private JButton btnClear;
	private JPanel pnResultMain;
	private JPanel pnResult;
	private JPanel pnRsCenter;
	private JPanel pnRsDetail;
	private JLabel lbTaxSalaryU;
	private JLabel lbTaxSalaryR;
	private JLabel lbNetSalaryU;
	private JLabel lbNetSalaryR;
	private JLabel lbMinorChildren;
	private JRadioButton rdYes, rdNo;
	private JComponent pnCenter;
	private JPanel pnMainCal;
	private JPanel pnCal;
	private JLabel lblTaxCalculator;
	
	Utility uti = new Utility();

	/**
	 * Create the panel.
	 * @param rdNo 
	 */
	public Calculator() {
		setBackground(Color.decode("#292929"));
		setLayout(new BorderLayout(0, 0));
		
		lblTaxCalculator = new JLabel("Tax Calculator");
		lblTaxCalculator.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTaxCalculator.setHorizontalAlignment(SwingConstants.CENTER);
		lblTaxCalculator.setForeground(Color.WHITE);
		add(lblTaxCalculator, BorderLayout.NORTH);
		
		pnCenter = new JPanel();
		pnCenter.setBackground(Color.decode("#323232"));
		add(pnCenter, BorderLayout.CENTER);
		pnCenter.setLayout(new GridLayout(0, 2, 0, 0));
		
		pnMainCal = new JPanel();
		uti.bgColor(pnMainCal);
		pnMainCal.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnCenter.add(pnMainCal);
		
		pnCal = new JPanel();
		uti.bgColor(pnCal);
		pnCal.setLayout(new GridLayout(0, 2, 10, 10));
		pnMainCal.add(pnCal);
		
		// space
		pnCal.add(uti.labelTitle(""));
		pnCal.add(uti.labelTitle(""));
		
		// salary
		pnCal.add(uti.labelTitle("Salary"));
		
		txtSalary = new JTextField();
		uti.allowNumber(txtSalary);
		txtSalary.grabFocus();
		uti.textField(txtSalary);
		pnCal.add(txtSalary);
		
		// space
		pnCal.add(uti.labelTitle(""));
		pnCal.add(uti.labelTitle(""));
		
		// benefit
		pnCal.add(uti.labelTitle("Benefit"));
		
		txtBenefit = new JTextField();
		uti.allowNumber(txtBenefit);
		uti.textField(txtBenefit);
		pnCal.add(txtBenefit);
		
		// space
		pnCal.add(uti.labelTitle(""));
		pnCal.add(uti.labelTitle(""));
		
		// exchange
		pnCal.add(uti.labelTitle("Exchange Rate($)"));
		
		txtExchange = new JTextField();
		uti.allowNumber(txtExchange);
		uti.textField(txtExchange);
		pnCal.add(txtExchange);
		
		// space
		pnCal.add(uti.labelTitle(""));
		pnCal.add(uti.labelTitle(""));
		
		// spouse
		pnCal.add(uti.labelTitle("Spouse"));
		
		pnSpouse = new JPanel();
		uti.bgColor(pnSpouse);
		pnCal.add(pnSpouse);
		pnSpouse.setLayout(new GridLayout(0, 2, 0, 0));
		
		// Yes label
		lbYes = new JLabel(); 
		lbYes.setIcon(uti.icon("src/main/resources/diselected.png"));
		lbYes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lbYes.setIcon(uti.icon("src/main/resources/selected.png"));
				lbNo.setIcon(uti.icon("src/main/resources/diselected.png"));
				lbMinorChildren.setVisible(true);
				txtMinor.setVisible(true);
				rdYes.setSelected(true);
			}
		});
		lbYes.setText("Yes");
		uti.labelAppearance(lbYes); uti.bgColor(lbYes);
		pnSpouse.add(lbYes);
		
		// No label
		lbNo = new JLabel();
		lbNo.setIcon(uti.icon("src/main/resources/selected.png"));
		lbNo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lbNo.setIcon(uti.icon("src/main/resources/selected.png"));
				lbYes.setIcon(uti.icon("src/main/resources/diselected.png"));
				lbMinorChildren.setVisible(false);
				txtMinor.setVisible(false); txtMinor.setText("");
				rdNo.setSelected(true);
			}
		});
		lbNo.setText("No");
		uti.labelAppearance(lbNo); uti.bgColor(lbNo);
		pnSpouse.add(lbNo);
		
		// radio button
		rdYes = new JRadioButton("Yes");
		rdYes.setVisible(false);
		pnCal.add(rdYes);
		
		rdNo = new JRadioButton("No");
		rdNo.setVisible(false);
		rdNo.setSelected(true);
		pnCal.add(rdNo);
		
		ButtonGroup btnG = new ButtonGroup();
		btnG.add(rdYes); btnG.add(rdNo);
		
		// minor children label
		lbMinorChildren = new JLabel("Minor Children");
		uti.labelAppearance(lbMinorChildren); uti.bgColor(lbMinorChildren);
		lbMinorChildren.setVisible(false);
		pnCal.add(lbMinorChildren);
		
		// minor children text field
		txtMinor = new JTextField();
		txtMinor.setVisible(false);
		uti.allowNumber(txtMinor);
		uti.textField(txtMinor);
		pnCal.add(txtMinor);
		
		// space
		pnCal.add(uti.labelTitle(""));
		pnCal.add(uti.labelTitle(""));
		
		// calculate
		btnCal = new JButton("Calculate");
		btnCal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!txtSalary.getText().equalsIgnoreCase("") || !txtExchange.getText().equalsIgnoreCase("")) {
					double salary = Double.parseDouble(txtSalary.getText());
					double exchange = Double.parseDouble(txtExchange.getText());
					double benefit = Double.parseDouble(txtBenefit.getText().equalsIgnoreCase("")?"0":txtBenefit.getText());
					int minor = Integer.parseInt(txtMinor.getText().equalsIgnoreCase("")?"0":txtMinor.getText());
					boolean hasSpouse = rdYes.isSelected()?true:false;
					
					Employee emp = new Employee(salary,benefit,hasSpouse,minor);
					
					double taxOnSalary = emp.getTax(exchange);
					lbTaxSalaryR.setText(uti.currencySign(taxOnSalary,"kh"));
					lbTaxSalaryU.setText(uti.currencySign(taxOnSalary/exchange,"us"));
					
					lbNetSalaryR.setText(uti.currencySign(emp.getNetSalary(exchange),"kh"));
					lbNetSalaryU.setText(uti.currencySign(emp.getNetSalary(exchange)/exchange,"us"));
					
					lbExchange.setText("1$ = "+uti.currencySign(Double.parseDouble(txtExchange.getText()),"kh"));
					lbSpouse.setText(rdYes.isSelected()?"Yes":"No");
					lbMinor.setText(txtMinor.getText().equalsIgnoreCase("")?"0":txtMinor.getText());
				}
				
			}
		});
		uti.labelAppearance(btnCal); 
		uti.btnColor(btnCal);
		pnCal.add(btnCal);
		
		// clear
		btnClear = new JButton("Clear");
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lbNo.setIcon(uti.icon("src/main/resources/selected.png"));
				lbYes.setIcon(uti.icon("src/main/resources/diselected.png"));
				lbMinorChildren.setVisible(false);
				rdNo.setSelected(true);
				txtMinor.setVisible(false); txtMinor.setText("");
				txtSalary.setText(""); txtBenefit.setText(""); txtExchange.setText("");
				lbSpouse.setText("No");
				lbMinor.setText("0");
				lbTaxSalaryR.setText(uti.currencySign(0.0,"kh")); lbTaxSalaryU.setText(uti.currencySign(0.0,"us"));
				lbNetSalaryR.setText(uti.currencySign(0.0,"kh")); lbNetSalaryU.setText(uti.currencySign(0.0,"us"));
				lbExchange.setText("1$ = "+uti.currencySign(0.0,"kh"));
				txtSalary.grabFocus();
			}
		});
		uti.labelAppearance(btnClear); 
		uti.btnColor(btnClear);
		pnCal.add(btnClear);
		
		// result panel
		pnResultMain = new JPanel();
		uti.bgColor(pnResultMain);
		pnCenter.add(pnResultMain);
		pnResultMain.setLayout(new BorderLayout(0, 0));
		
		lbSpace = new JLabel("Result");
		lbSpace.setHorizontalAlignment(SwingConstants.CENTER);
		uti.labelAppearance(lbSpace); 
		uti.bgColor(lbSpace);
		lbSpace.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pnResultMain.add(lbSpace, BorderLayout.NORTH);
		
		pnResult = new JPanel();
		uti.bgColor(pnResult);
		pnResultMain.add(pnResult, BorderLayout.CENTER);
		pnResult.setLayout(new BorderLayout(0, 0));
		
		pnRsCenter = new JPanel();
		uti.bgColor(pnRsCenter);
		pnResult.add(pnRsCenter);
		pnRsCenter.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		pnRsDetail = new JPanel();
		uti.bgColor(pnRsDetail);
		pnRsCenter.add(pnRsDetail);
		pnRsDetail.setLayout(new GridLayout(0, 2, 50, 10));
		
		pnRsDetail.add(uti.labelTitle(""));
		pnRsDetail.add(uti.labelTitle(""));
		
		pnRsDetail.add(uti.labelTitle("Tax On Salary"));
		lbTaxSalaryU = new JLabel(uti.currencySign(0.0,"us"));
		uti.labelAppearance(lbTaxSalaryU); uti.bgColor(lbTaxSalaryU);
		pnRsDetail.add(lbTaxSalaryU);
		
		pnRsDetail.add(uti.labelTitle(""));
		lbTaxSalaryR = new JLabel(uti.currencySign(0.0,"kh")); 
		uti.labelAppearance(lbTaxSalaryR); uti.bgColor(lbTaxSalaryR);
		pnRsDetail.add(lbTaxSalaryR);
		
		pnRsDetail.add(uti.labelTitle(""));
		pnRsDetail.add(uti.labelTitle(""));
		
		pnRsDetail.add(uti.labelTitle("Net Salary"));
		
		lbNetSalaryU = new JLabel(uti.currencySign(0.0,"us"));
		uti.labelAppearance(lbNetSalaryU); uti.bgColor(lbNetSalaryU);
		pnRsDetail.add(lbNetSalaryU);
		
		pnRsDetail.add(uti.labelTitle(""));
		
		lbNetSalaryR = new JLabel(uti.currencySign(0.0,"kh"));
		uti.labelAppearance(lbNetSalaryR); uti.bgColor(lbNetSalaryR);
		pnRsDetail.add(lbNetSalaryR);
		
		pnRsDetail.add(uti.labelTitle(""));
		pnRsDetail.add(uti.labelTitle(""));
		
		pnRsDetail.add(uti.labelTitle("Exchange Rate"));
		
		lbExchange = new JLabel("1$ = "+uti.currencySign(0.0,"kh"));
		uti.labelAppearance(lbExchange); uti.bgColor(lbExchange);
		pnRsDetail.add(lbExchange);
		
		pnRsDetail.add(uti.labelTitle(""));
		pnRsDetail.add(uti.labelTitle(""));
		
		pnRsDetail.add(uti.labelTitle("Has Spouse?"));
		
		lbSpouse = new JLabel("No");
		uti.labelAppearance(lbSpouse); uti.bgColor(lbSpouse);
		pnRsDetail.add(lbSpouse);
		
		pnRsDetail.add(uti.labelTitle(""));
		pnRsDetail.add(uti.labelTitle(""));
		
		pnRsDetail.add(uti.labelTitle("Minor Children"));
		
		lbMinor = new JLabel("0");
		uti.labelAppearance(lbMinor); uti.bgColor(lbMinor);
		pnRsDetail.add(lbMinor);
	}
	
}
