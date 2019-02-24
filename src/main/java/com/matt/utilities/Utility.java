package com.matt.utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.TableColumn;

import com.matt.system.taxation.InfoDialog;
import com.toedter.calendar.JDateChooser;

public class Utility extends BasicComboBoxUI{

	private JFileChooser chooser;
	private File file;
	private ImageIcon icon;
	private String path;
	
	public String getPath() {
    	return this.path;
    }
	public void setPath(String p) {
		this.path=p;
	}

	public static Utility createUI(JComponent c) {
        return new Utility();
    }
	
    @Override 
    protected JButton createArrowButton() {
        return new BasicArrowButton(
            BasicArrowButton.SOUTH,
            Color.decode("#323232"), Color.WHITE,
            Color.WHITE, Color.WHITE);
    }
    
    // auto generate id
    public String generatedId() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	       StringBuilder strBuilder = new StringBuilder(5);
	       Random rand = new Random();

	       while (strBuilder.length() < 5)
	       {
	           int i = (int)(rand.nextInt(chars.length()));
	           strBuilder.append((int)chars.charAt(i));
	       }
	    return strBuilder.toString();
	}
    
    // image picker
    public void imagePicker(JPanel jp, JLabel lb) {
    	chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "png");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(jp);
		
		if(returnVal == JFileChooser.APPROVE_OPTION) {
		       System.out.println("You chose to open this file: " +
		            chooser.getSelectedFile().getName());
		       this.path = chooser.getSelectedFile().getAbsolutePath();
		}
		
        file = chooser.getSelectedFile();
		paintLabel(file,lb);
    }
    // paint label
    public void paintLabel(File file, final JLabel lb) {
    	BufferedImage imgPath;
    	try {
            imgPath=ImageIO.read(file);
            Image dimg = imgPath.getScaledInstance(100, 120,Image.SCALE_SMOOTH);
            icon = new ImageIcon(dimg); 
            lb.setIcon(icon); 
            lb.revalidate(); 
            lb.repaint(); 
        }
        catch(Exception e1) {
        	//e1.printStackTrace();
        }
    }
    
    // image string size
    public ImageIcon imageSize(String path,int w, int h) {
    	BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(path));
		} catch (IOException e) {
		    //e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(w, h,Image.SCALE_SMOOTH);
		return new ImageIcon(dimg);
    }
    
    // combobox
    public void comboBox(JComboBox<String> combo) {
		combo.setUI(Utility.createUI(combo)); 
		combo.setForeground(Color.WHITE);
		combo.setBackground(Color.decode("#323232"));
		combo.setBorder(new LineBorder(Color.WHITE));
		labelAppearance(combo);
    }
    
    // table width
    public void tableSetWidth(JTable table) {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(30); 
		table.setSelectionBackground(Color.decode("#1abc9c"));
		table.setSelectionForeground(Color.WHITE);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.setForeground(Color.WHITE);
		for (int column = 0; column < table.getColumnCount(); column++)
		{
		    TableColumn tableColumn = table.getColumnModel().getColumn(column);
		    tableColumn.setPreferredWidth( 150 );
		}
	}
    
    // set date
    public Date date(String d) throws ParseException {
    	Date da;
		try {
			da = new SimpleDateFormat("dd-MM-yyyy").parse(d);
		} catch (ParseException e) {
			return null;
		}
    	return da;
    }
    
    // date chooser 
    public JDateChooser datePicker() {
		JDateChooser dc = new JDateChooser();
		dc.setDateFormatString("dd-MM-yyyy");
		bgColor(dc); labelAppearance(dc);
		return dc;
	}
    
    // icon
 	public ImageIcon icon(String path) {
 		return new ImageIcon(path);
 	}
 	
 	// background color
 	public void bgColor(JComponent jc) {
 		jc.setBackground(Color.decode("#323232"));
 	}
 	
 	// button color
 	public void btnColor(JButton button) {
 		button.setContentAreaFilled(false);
 		button.setOpaque(true);
 		button.setBorder(new LineBorder(Color.LIGHT_GRAY));
 		bgColor(button);
 	}
 	
 	// title
 	public JLabel labelTitle(String t) {
 		JLabel lbTitle = new JLabel(t);
 		labelAppearance(lbTitle); bgColor(lbTitle);
 		return lbTitle;
 	}

 	// label
 	public void labelAppearance(JComponent lb) {
 		lb.setForeground(Color.WHITE);
 		lb.setFont(new Font("Tahoma", Font.PLAIN, 18));
 	}
 	
 	// label value
 	public String currencySign(Double val, String cr) {
 		DecimalFormat fm = new DecimalFormat("#,##0.00");
 		if(cr.equalsIgnoreCase("Kh")) {
 			String r = "៛";
 			return (fm.format(val)+" "+r);
 		}
 		return (fm.format(val)+" $");
 	}
 	
 	// text field
 	public void textField(JTextField tf) {
 		tf.setCaretColor(Color.decode("#1abc9c"));
 		tf.putClientProperty("caretWidth", 3);
 		tf.setForeground(Color.WHITE);
 		bgColor(tf);
 		tf.setFont(new Font("Tahoma", Font.PLAIN, 18));
 		tf.setColumns(10);
 	}
 	
 	// disable info Dialog field
 	public void disableInfoDialogField(InfoDialog in) {
 		in.txtFirst.setEditable(false);
 		in.txtLast.setEditable(false);
 		in.txtAddress.setEditable(false);
 		in.txtBenefit.setEditable(false);
 		in.txtDepartment.setEditable(false);
 		in.txtPosition.setEditable(false);
 		in.txtDob.setEnabled(false);
 		in.txtPob.setEditable(false);
 		in.txtPhone.setEditable(false);
 		in.txtEmail.setEditable(false);
 		in.txtExchange.setEditable(false);
 		in.txtMinor.setEditable(false);
 		in.txtSalary.setEditable(false);
 		in.cboGender.setEnabled(false);
 		in.cboSpouse.setEnabled(false);
 		in.btnBrowse.setVisible(false);
 	}
 	
 	// text and label style
 	public void labelTextFieldStyle(JLabel lb, JTextField tf) {
		textField(tf); 
		labelAppearance(lb); 
		bgColor(lb);
	}

 	// allow only number
 	public void allowNumber(final JTextField tf) {
 		tf.addKeyListener(new KeyAdapter() {
			@Override
 			public void keyPressed(KeyEvent e) {
 				char c = e.getKeyChar();
 				if(Character.isLetter(c)) {
 					tf.setEditable(false);
 				}else {
 					tf.setEditable(true);
 				}
 			}
 			@Override
 			public void keyReleased(KeyEvent e) {
 				tf.setEditable(true);
 			}
 		});
 	}
 	
 	// allow text
 	public void allowText(final JTextField tf) {
 		tf.addKeyListener(new KeyAdapter() {
 			@Override
 			public void keyPressed(KeyEvent e) {
 				char c = e.getKeyChar();
 				if(!Character.isLetter(c)) {
 					tf.setEditable(false);
 				}else {
 					tf.setEditable(true);
 				}
 			}
 			@Override
 			public void keyReleased(KeyEvent e) {
 				tf.setEditable(true);
 			}
 		});
 	}
}
