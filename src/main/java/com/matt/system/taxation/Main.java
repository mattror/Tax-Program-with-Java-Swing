package com.matt.system.taxation;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Main {

	private JFrame frame;
	private JPanel pnSideBar;
	private JPanel pnSideBarLabel;
	private JTabbedPane pnMain;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}
	
//====================================== HOVER ======================================================
	public void hover(final JLabel lb,final String oldIcon, final String newIcon) {
		lb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lb.setIcon(menuImage("src/main/resources/"+newIcon));
				lb.setForeground(Color.decode("#1abc9c"));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lb.setIcon(menuImage("src/main/resources/"+oldIcon));
				lb.setForeground(Color.WHITE);
			}
		});
	}
//====================================== END HOVER ==================================================
	
//====================================== EXIT =======================================================
	public void exit(final JLabel lb, final int click) {
		lb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==click) {
					Object[] opts = {"Yes","Cancel"};
					int confirmed=JOptionPane.showOptionDialog(frame,"Are you to exit?","Delete",
							JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,
							null,opts,opts[1]);
	
					if (confirmed == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				}
			}
		});
	}
//======================================== END EXIT ==============================================
	
//======================================== REPAINT ==============================================	
	public void repaint(JPanel pn, String title) {
		if(pnMain.getTabCount()==0) {
			pn.setVisible(true);
			pn.setBounds(0, 0, 1320, 705);
			pnMain.add(title,pn);
			pnMain.setSelectedComponent(pn);
		}else {
			 if(!pnMain.getTitleAt(pnMain.getSelectedIndex()).equals(title)) {
				pn.setVisible(true);
				pn.setBounds(0, 0, 1320, 705);
				pnMain.add(title,pn);
				pnMain.setSelectedComponent(pn);
			 }
		}
	}
//======================================== END REPAINT ==============================================
	
	// set visible menu
	public void visibleMenu() {
		pnSideBarLabel.setVisible(false);
		pnSideBar.setVisible(true);
	}
	
//======================================== 	SIDE BAR ==============================================
	public Component sideBar() {
		pnSideBar = new JPanel(); 
		pnSideBar.setBackground(Color.decode("#252525"));
		pnSideBar.setBounds(0, 0, 48, 705);
		pnSideBar.setLayout(new GridLayout(7, 1, 0, 0));
		{
			final JLabel menuToggle, menuNew, menuList, menuCal,menuExit, menuRule, menuReport;
			
			//===================== toggle menu
			menuToggle = new JLabel("");
			menuToggle.setHorizontalAlignment(SwingConstants.CENTER);
			menuToggle.setIcon(menuImage("src/main/resources/toggle.png"));
			menuToggle.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					pnSideBar.setVisible(false);
					pnSideBarLabel.setVisible(true);
				}
			});
			hover(menuToggle,"toggle.png","toggle_tq.png");
			pnSideBar.add(menuToggle);
			
			//====================== new employee
			menuNew = new JLabel("");
			menuNew.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						repaint(new NewEmployee(),"New Employee");
					}
				}
			});
			menuNew.setToolTipText("New Employee");
			menuNew.setHorizontalAlignment(SwingConstants.CENTER);
			menuNew.setIcon(menuImage("src/main/resources/new.png"));
			hover(menuNew,"new.png","new_tq.png");
			pnSideBar.add(menuNew);
			
			//====================== list employee
			menuList = new JLabel("");
			menuList.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						repaint(new ListEmployee(),"List Employee");
					}
				}
			});
			menuList.setToolTipText("List Employee");
			menuList.setHorizontalAlignment(SwingConstants.CENTER);
			menuList.setIcon(menuImage("src/main/resources/list.png"));
			hover(menuList,"list.png","list_tq.png");
			pnSideBar.add(menuList);
			
			//====================== report
			menuReport = new JLabel("");
			menuReport.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						repaint(new Report(),"Report");
					}
				}
			});
			menuReport.setToolTipText("Report");
			menuReport.setHorizontalAlignment(SwingConstants.CENTER);
			menuReport.setIcon(menuImage("src/main/resources/report.png"));
			hover(menuReport,"report.png","report_tq.png");
			pnSideBar.add(menuReport);
			
			// ===================== tax calculator
			menuCal = new JLabel("");
			menuCal.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						repaint(new Calculator(),"Calculator");
					}
				}
			});
			menuCal.setToolTipText("Tax Calculator");
			menuCal.setHorizontalAlignment(SwingConstants.CENTER);
			menuCal.setIcon(menuImage("src/main/resources/calculator.png"));
			hover(menuCal,"calculator.png","calculator_tq.png");
			pnSideBar.add(menuCal);
			
			//====================== tax rule 2018
			menuRule = new JLabel("");
			menuRule.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						repaint(new TaxRule(),"Tax Rule");
					}
				}
			});
			menuRule.setToolTipText("Tax Rule 2018");
			hover(menuRule,"rule.png","rule_tq.png");
			menuRule.setHorizontalAlignment(SwingConstants.CENTER);
			menuRule.setIcon(menuImage("src/main/resources/rule.png"));
			pnSideBar.add(menuRule);
			
			//========================== exit
			menuExit = new JLabel("");
			menuExit.setToolTipText("Exit");
			menuExit.setHorizontalAlignment(SwingConstants.CENTER);
			menuExit.setIcon(menuImage("src/main/resources/exit.png"));
			hover(menuExit,"exit.png","exit_red.png");
			exit(menuExit,2);
			pnSideBar.add(menuExit);
		}
		return pnSideBar;
	}
//============================================== END SIDE BAR =======================================
	
//================================================== SIDE BAR LABEL =================================
	public Component SideBarLabel() {
		pnSideBarLabel = new JPanel();
		pnSideBarLabel.setBounds(0, 0, 250, 705);
		pnSideBarLabel.setBackground(Color.decode("#212121"));
		pnSideBarLabel.setLayout(new GridLayout(7, 1, 0, 0));
		{
			final JLabel lbClose,lbRule,lbReport,lbNew,lbList,lbCal,lbExit;
			
			lbClose = new JLabel("");
			lbClose.setIcon(menuImage("src/main/resources/close.png"));
			lbClose.setHorizontalAlignment(SwingConstants.RIGHT);
			lbClose.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					pnSideBarLabel.setVisible(false);
					pnSideBar.setVisible(true);
				}
			});
			hover(lbClose,"close.png","close_red.png");
			pnSideBarLabel.add(lbClose);
			
			// new employee
			lbNew = new JLabel("");
			lbNew.setIcon(menuImage("src/main/resources/new.png")); 
			lbNew.setText("    New Employee");
			lbNew.setHorizontalAlignment(SwingConstants.LEFT);
			lbNew.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbNew.setForeground(Color.WHITE);
			hover(lbNew,"new.png","new_tq.png");
			lbNew.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					visibleMenu();
					repaint(new NewEmployee(),"New Employee");
				}
			});
			pnSideBarLabel.add(lbNew);
			
			// list employee
			lbList = new JLabel("");
			lbList.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					visibleMenu();
					repaint(new ListEmployee(),"List Employee");
				}
			});
			lbList.setIcon(menuImage("src/main/resources/list.png"));
			lbList.setText("    List Employee");
			lbList.setHorizontalAlignment(SwingConstants.LEFT);
			lbList.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbList.setForeground(Color.WHITE);
			hover(lbList,"list.png","list_tq.png");
			pnSideBarLabel.add(lbList);
		
			// report
			lbReport = new JLabel("");
			lbReport.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					visibleMenu();
					repaint(new Report(),"Report");
				}
			});
			lbReport.setIcon(menuImage("src/main/resources/report.png"));
			lbReport.setText("    Report");
			lbReport.setHorizontalAlignment(SwingConstants.LEFT);
			lbReport.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbReport.setForeground(Color.WHITE);
			hover(lbReport,"report.png","report_tq.png");
			pnSideBarLabel.add(lbReport);
			
			// calculator
			lbCal = new JLabel("");
			lbCal.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					visibleMenu();
					repaint(new Calculator(),"Calculator");
				}
			});
			lbCal.setIcon(menuImage("src/main/resources/calculator.png"));
			lbCal.setText("    Calculator");
			lbCal.setHorizontalAlignment(SwingConstants.LEFT);
			lbCal.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbCal.setForeground(Color.WHITE);
			hover(lbCal,"calculator.png","calculator_tq.png");
			pnSideBarLabel.add(lbCal);
			
			// rule 
			lbRule = new JLabel("");
			lbRule.setIcon(menuImage("src/main/resources/rule.png"));
			lbRule.setText("    Rule");
			lbRule.setHorizontalAlignment(SwingConstants.LEFT);
			lbRule.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbRule.setForeground(Color.WHITE);
			hover(lbRule,"rule.png","rule_tq.png");
			lbRule.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					visibleMenu();
					repaint(new TaxRule(),"Tax Rule");
				}
			});
			
			pnSideBarLabel.add(lbRule);
			
			// exit
			lbExit = new JLabel("");
			lbExit.setIcon(menuImage("src/main/resources/exit.png"));
			lbExit.setText("    Exit");
			lbExit.setHorizontalAlignment(SwingConstants.LEFT);
			lbExit.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbExit.setForeground(Color.WHITE);
			hover(lbExit,"exit.png","exit_red.png");
			exit(lbExit,1);
			pnSideBarLabel.add(lbExit);
		}
		return pnSideBarLabel;
	}
//=================================== END SIDE BAR LABEL ============================================

//=================================== MANU IMAGE ====================================================
	public ImageIcon menuImage(String path) {
		ImageIcon icon = new ImageIcon(path);
		return icon;
	}
//=================================== END MANU IMAGE ================================================
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame("Employee Tax Management System");
		frame.getContentPane().setBackground(Color.decode("#323232"));
		// icon
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/favicon.png"));
		
		// close program
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Object[] opts = {"Yes","Cancel"};
				int confirmed=JOptionPane.showOptionDialog(frame,"Are you to exit?","Delete",
						JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,
						null,opts,opts[1]);

				if (confirmed == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		
		// screen size
		frame.setBounds(100, 100, 450, 300);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		//frame.getContentPane().add(new Auth()); 
		// side bar
		frame.getContentPane().add(sideBar()); 
		
		// side bar label
		frame.getContentPane().add(SideBarLabel()); 
		
		// main panel
		pnMain = new JTabbedPane();
		pnMain.setBorder(null);
		pnMain.setBackground(Color.decode("#323232"));
		pnMain.setBounds(47, 0, 1318, 705);
		pnMain.setLayout(null);
		pnMain.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(pnMain.getTabCount()>=2) {
					pnMain.remove(0);
				}
			}
		});
		
		frame.getContentPane().add(pnMain);
		visibleMenu();
		repaint(new HomePage(),"HomePage");
	}
}
