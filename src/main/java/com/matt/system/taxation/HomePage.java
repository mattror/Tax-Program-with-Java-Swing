package com.matt.system.taxation;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

import com.matt.utilities.Utility;

@SuppressWarnings("serial")
public class HomePage extends JPanel {

	/**
	 * Create the panel.
	 */
	public HomePage() {
		Utility uti = new Utility();
		uti.bgColor(this);
		setLayout(new BorderLayout(0, 0));
		
		JLabel lbImage = new JLabel("");
		lbImage.setHorizontalAlignment(SwingConstants.CENTER);
		lbImage.setIcon(new ImageIcon("src/main/resources/tax.png"));
		add(lbImage);

	}

}
