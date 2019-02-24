package com.matt.system.taxation;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;

import java.awt.GridLayout;

@SuppressWarnings("serial")
public class TaxRule extends JPanel {

	private JLabel img;

	public ImageIcon icon(String path) {
		return new ImageIcon(path);
	}
	
	/**
	 * Create the panel.
	 */
	public TaxRule() {
		setBackground(Color.decode("#292929"));
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblTaxRule = new JLabel("Tax Rule");
		add(lblTaxRule, BorderLayout.NORTH);
		lblTaxRule.setHorizontalAlignment(SwingConstants.CENTER);
		lblTaxRule.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTaxRule.setForeground(Color.WHITE);
		
		JPanel pnCenter = new JPanel();
		pnCenter.setBackground(Color.decode("#323232"));
		pnCenter.setLayout(new GridLayout(0, 1, 0, 0));
		
		img = new JLabel();
		img.setHorizontalAlignment(SwingConstants.CENTER);
		img.setIcon(icon("src/main/resources/rule1.jpg"));
		pnCenter.add(img);
		
		img = new JLabel();
		img.setHorizontalAlignment(SwingConstants.CENTER);
		img.setIcon(icon("src/main/resources/rule2.jpg"));
		pnCenter.add(img);
		
		img = new JLabel();
		img.setHorizontalAlignment(SwingConstants.CENTER);
		img.setIcon(icon("src/main/resources/rule3.jpg"));
		pnCenter.add(img);
		
		JScrollPane jsp = new JScrollPane(pnCenter);
		jsp.setBorder(null);
		add(jsp, BorderLayout.CENTER);
		
		
	}

}
