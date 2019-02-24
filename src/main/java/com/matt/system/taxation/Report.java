package com.matt.system.taxation;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.matt.employee.DB;
import com.matt.employee.Employee;
import com.matt.utilities.StylePdf;
import com.matt.utilities.Utility;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Report extends JPanel {
	
	Utility cal = new Utility();
	ListEmployee listEmp = new ListEmployee();
	
	private JLabel lbPdf;
	private JPanel pnCenter;
	private JPanel pnBottom;
	private JLabel lbExport;
	private JPanel pnTop;
	private JPanel pnContent;
	private JLabel lblReport;
	private JPanel pnSpace;
	private JPanel pnTable;
	private JTable table;
	private JScrollPane tbScroll;
	private JPanel pnBCol1;
	private JPanel pnBCol2;
	private JLabel lbCurrent;
	private JTextField txtCurrent = new JTextField();
	private JPanel pnBCol1Detail;
	private JPanel pnBCol2Detail;
	private JLabel lbTitle;
	private JLabel lbTotalSalary;
	private DefaultTableModel tbModel;
	
	/**
	 * Create the panel.
	 */
	public Report() {
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.decode("#292929"));
		
		lblReport = new JLabel("Report");
		lblReport.setForeground(Color.WHITE);
		lblReport.setHorizontalAlignment(SwingConstants.CENTER);
		lblReport.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(lblReport, BorderLayout.NORTH);
		
		pnContent = new JPanel();
		pnContent.setBackground(Color.decode("#323232"));
		add(pnContent, BorderLayout.CENTER);
		pnContent.setLayout(new BorderLayout(0, 0));
		
		pnTop = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnTop.getLayout();
		pnTop.setVisible(false);
		flowLayout.setVgap(10);
		flowLayout.setHgap(20);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		cal.bgColor(pnTop);
		pnContent.add(pnTop, BorderLayout.NORTH);
		
		lbExport = new JLabel("Export As : ");
		cal.labelAppearance(lbExport); cal.bgColor(lbExport);
		pnTop.add(lbExport);
		
		lbPdf = new JLabel("");
	
		lbPdf.setIcon(cal.icon("src/main/resources/pdf.png"));
		hover(lbPdf,"pdf.png","pdf_tq.png");
		lbPdf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(txtCurrent.getText()!="") {
					try {
						DateFormat df = new SimpleDateFormat("dd-MM-h-mm-ss");
						Date date = new Date();
						String dest = "report-"+df.format(date)+".pdf";
						
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("Specify a file to save");
						
						int userSelect = fileChooser.showSaveDialog(pnTop);
						if(userSelect==JFileChooser.APPROVE_OPTION) {
							File fileToSave = fileChooser.getSelectedFile();
							dest = fileToSave.getAbsolutePath();
						}
						
						System.out.println(dest);
						PdfWriter writer = new PdfWriter(dest);
						PdfDocument pdf = new PdfDocument(writer);
						Document doc = new Document(pdf);
						
						float[] columnWidths = {50F,250F,10F,150F,100F,100F,150F,150F};
						Table tbPdf = new Table(columnWidths);
						
						StylePdf stPdf = new StylePdf();
						
						String header = "Report";
						Paragraph paraHeader = new Paragraph(header);
						
						Cell cId = new Cell();
						cId.add("ID");
						
						Cell cName = new Cell();
						cName.add("NAME");
						
						Cell cGender = new Cell();
						cGender.add("SEX");
						
						Cell cDOB = new Cell();
						cDOB.add("DOB");
						
						Cell cEx = new Cell();
						cEx.add("EXCHANGE RATE($)");
						
						Cell cSal = new Cell();
						cSal.add("SALARY($)");
						
						Cell cTax = new Cell();
						cTax.add("TAX ON SALARY($)");
						
						Cell cNet = new Cell();
						cNet.add("NET SALARY($)");
						
						try {
							stPdf.paragraphHeader(paraHeader);
							stPdf.tableHeader(cId);
							stPdf.tableHeader(cName);
							stPdf.tableHeader(cGender);
							stPdf.tableHeader(cDOB);
							stPdf.tableHeader(cEx);
							stPdf.tableHeader(cSal);
							stPdf.tableHeader(cTax);
							stPdf.tableHeader(cNet);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
						tbPdf.addCell(cId);
						tbPdf.addCell(cName);
						tbPdf.addCell(cGender);
						tbPdf.addCell(cDOB);
						tbPdf.addCell(cEx);
						tbPdf.addCell(cSal);
						tbPdf.addCell(cTax);
						tbPdf.addCell(cNet);
						
						double exchange = Double.parseDouble(txtCurrent.getText()==""?"4000":txtCurrent.getText());
						double salary;
						double benefit;
						boolean spouse;
						int minor;
						double tax;
						double net;
						Utility uti = new Utility();
						
						Cell ceId,ceName,ceGender,ceDOB,ceEx,ceSal,ceTax,ceNet;
						
						DB db = new DB();
						for(Employee em : db.read()) {
							ceId = new Cell();
							ceName = new Cell();
							ceGender = new Cell();
							ceDOB = new Cell();
							ceEx = new Cell();
							ceSal = new Cell();
							ceTax = new Cell();
							ceNet = new Cell();
							
							tbPdf.addCell(ceId.add(em.getId()));
							tbPdf.addCell(ceName.add(em.getFirst_name()+" "+em.getLast_name()));
							tbPdf.addCell(ceGender.add(em.getGender()+""));
							tbPdf.addCell(ceDOB.add(em.getDob()));
							tbPdf.addCell(ceEx.add(uti.currencySign(exchange, "KH")));
							
							salary = em.getSalary();
							benefit = em.getBenefit();
							spouse = em.isHasSpouse();
							minor = em.getMinorChild();
							
							Employee emp = new Employee(salary,benefit,spouse,minor);
							tax = emp.getTax(exchange)/exchange;
							net = emp.getNetSalary(exchange)/exchange;
							
							tbPdf.addCell(ceSal.add(uti.currencySign(salary, "US")+""));
							tbPdf.addCell(ceTax.add(uti.currencySign(tax, "us")));
							tbPdf.addCell(ceNet.add(uti.currencySign(net, "us")));
							
							try {
								stPdf.tableFont(ceId);
								stPdf.tableFont(ceName);
								stPdf.tableFont(ceGender);
								stPdf.tableFont(ceDOB);
								stPdf.tableFont(ceEx);
								stPdf.tableFont(ceSal);
								stPdf.tableFont(ceTax);
								stPdf.tableFont(ceNet);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						doc.add(paraHeader);
						doc.add(tbPdf);
						doc.close();
						
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}finally {
						JOptionPane.showMessageDialog(null, "Success");
					}
				}
			}
		});
		pnTop.add(lbPdf);
		
		pnCenter = new JPanel();
		pnContent.add(pnCenter, BorderLayout.CENTER);
		pnCenter.setLayout(new BorderLayout(0, 0));
		
		pnSpace = new JPanel(); cal.bgColor(pnSpace);
		pnCenter.add(pnSpace, BorderLayout.WEST);
		
		pnTable = new JPanel();
		pnCenter.add(pnTable, BorderLayout.CENTER);
		pnTable.setLayout(new GridLayout(1, 0, 0, 0));
		
		tbModel = new DefaultTableModel(){
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		// table
		table = new JTable(tbHeader(tbModel));
		cal.labelAppearance(table); cal.bgColor(table);
		cal.tableSetWidth(table);
		
		tbScroll = new JScrollPane(table);
		tbScroll.getViewport().setBackground(Color.decode("#323232"));
		pnTable.add(tbScroll);
		
		pnSpace = new JPanel(); cal.bgColor(pnSpace);
		pnCenter.add(pnSpace, BorderLayout.EAST);
		
		pnBottom = new JPanel(); 
		cal.bgColor(pnBottom);
		pnContent.add(pnBottom, BorderLayout.SOUTH);
		pnBottom.setLayout(new GridLayout(0, 2, 0, 0));
		
		pnBCol1 = new JPanel();
		cal.bgColor(pnBCol1);
		pnBottom.add(pnBCol1);
		pnBCol1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		pnBCol1Detail = new JPanel();
		cal.bgColor(pnBCol1Detail);
		pnBCol1Detail.setLayout(new GridLayout(0, 2, 10, 10));
		pnBCol1.add(pnBCol1Detail);
		
		lbTitle = new JLabel("");
		pnBCol1Detail.add(lbTitle);
		lbTitle = new JLabel("");
		pnBCol1Detail.add(lbTitle);
		
		// current exchange rate
		lbCurrent = new JLabel("Current Exchange Rate");
		cal.labelAppearance(lbCurrent); cal.bgColor(lbCurrent);
		pnBCol1Detail.add(lbCurrent);
		
		txtCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtCurrent.getText() =="") {
					tbModel.setRowCount(0); 
					pnTop.setVisible(false);
				}else{
					pnTop.setVisible(true);
					try {
						tbModel.setRowCount(0);
						DB db = new DB();
						String[] data;
						Double exchange = Double.parseDouble(txtCurrent.getText()==""?"4000":txtCurrent.getText());
						for(Employee em : db.read()) {
							data = em.stringReport(exchange);
							tbModel.addRow(data);
						}
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		cal.textField(txtCurrent);
		pnBCol1Detail.add(txtCurrent);

		pnBCol2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnBCol2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		cal.bgColor(pnBCol2);
		pnBottom.add(pnBCol2);
		
		pnBCol2Detail = new JPanel();
		cal.bgColor(pnBCol2Detail);
		pnBCol2Detail.setLayout(new GridLayout(0, 2, 20, 10));
		pnBCol2.add(pnBCol2Detail);
		
		lbTitle = new JLabel("Total Employee");
		cal.labelAppearance(lbTitle); cal.bgColor(lbTitle);
		pnBCol2Detail.add(lbTitle);
		lbTitle = new JLabel("0");
		cal.labelAppearance(lbTitle); cal.bgColor(lbTitle);
		pnBCol2Detail.add(lbTitle);
		
		// total salary
		lbTitle = new JLabel("Total Salary");
		cal.labelAppearance(lbTitle); cal.bgColor(lbTitle);
		pnBCol2Detail.add(lbTitle);
		
		lbTotalSalary = new JLabel("0.0 $");
		cal.labelAppearance(lbTotalSalary); cal.bgColor(lbTotalSalary);
		pnBCol2Detail.add(lbTotalSalary);
		
		lbTitle = new JLabel("");
		pnBCol2Detail.add(lbTitle);
		
		lbTotalSalary = new JLabel("0.0 $");
		cal.labelAppearance(lbTotalSalary); cal.bgColor(lbTotalSalary);
		pnBCol2Detail.add(lbTotalSalary);
		
		// total tax on salary
		lbTitle = new JLabel("Total Tax On Salary");
		cal.labelAppearance(lbTitle); cal.bgColor(lbTitle);
		pnBCol2Detail.add(lbTitle);
		
		lbTotalSalary = new JLabel("0.0 $");
		cal.labelAppearance(lbTotalSalary); cal.bgColor(lbTotalSalary);
		pnBCol2Detail.add(lbTotalSalary);

		lbTitle = new JLabel("");
		pnBCol2Detail.add(lbTitle);
		
		lbTotalSalary = new JLabel("0.0 $");
		cal.labelAppearance(lbTotalSalary); cal.bgColor(lbTotalSalary);
		pnBCol2Detail.add(lbTotalSalary);
		
		// total net salary
		lbTitle = new JLabel("Total Net Salary");
		cal.labelAppearance(lbTitle); cal.bgColor(lbTitle);
		pnBCol2Detail.add(lbTitle);
		
		lbTotalSalary = new JLabel("0.0 $");
		cal.labelAppearance(lbTotalSalary); cal.bgColor(lbTotalSalary);
		pnBCol2Detail.add(lbTotalSalary);

		lbTitle = new JLabel("");
		pnBCol2Detail.add(lbTitle);
		
		lbTotalSalary = new JLabel("0.0 $");
		cal.labelAppearance(lbTotalSalary); cal.bgColor(lbTotalSalary);
		pnBCol2Detail.add(lbTotalSalary);
	}
	
	// table header
	public DefaultTableModel tbHeader(DefaultTableModel tm) {
		
		tm.addColumn("ID");
		tm.addColumn("FIRST NAME");
		tm.addColumn("LAST NAME");
		tm.addColumn("GENDER");
		tm.addColumn("DOB");
		tm.addColumn("SALARY($)");
		tm.addColumn("SALARY(RIELS)");
		tm.addColumn("TAX ON SALARY($)");
		tm.addColumn("TAX ON SALARY(RIELS)");
		tm.addColumn("NET SALARY($)");
		tm.addColumn("NET SALARY(RIELS)");
		
		return tm;
	}
	
	// hover
	public void hover(final JLabel lb,final String pOld, final String pNew) {
		lb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lb.setIcon(cal.icon("src/main/resources/"+pNew));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lb.setIcon(cal.icon("src/main/resources/"+pOld));
			}
		});
	}
}
