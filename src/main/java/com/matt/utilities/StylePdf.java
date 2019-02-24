package com.matt.utilities;

import java.io.IOException;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
public class StylePdf {
	public void tableHeader(Cell c) throws IOException {
		c.setBackgroundColor(Color.CYAN);
		c.setTextAlignment(TextAlignment.CENTER);
		tableFont(c);
	}
	
	public void tableFont(Cell c) throws IOException {
		PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
		c.setFont(font);
		c.setFontSize(9);
		
	}
	
	public void paragraphHeader(Paragraph p) throws IOException {
		PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
		p.setFont(font);
		p.setFontSize(12);
		p.setTextAlignment(TextAlignment.CENTER);
	}
}
