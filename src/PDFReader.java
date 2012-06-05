import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class PDFReader {

	/* Receive a filename with the complete path. Return the contents of the
	 * file
	 */
	
	public static String getPDFContent (String filename){
		InputStream input = null;
		try {
			input = new FileInputStream(new File(filename));
		} catch (FileNotFoundException e) {
			System.err.println("Problem finding the file.");
			e.printStackTrace();
			return null;
		}
		ContentHandler textHandler = new BodyContentHandler(10*1024*1024);
		Metadata metadata = new Metadata();
		PDFParser parser = new PDFParser();
		ParseContext context = new ParseContext();
		try {
			parser.parse(input, textHandler, metadata,context);
			input.close();
		} catch (IOException e1) {
			System.err.println("Problem reading the file");
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		} catch (TikaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
	
		String text =  textHandler.toString();
		
		text = filterWhitespaces(text);
		
		return text;
	}
	/*
	 * Filter cases in which the input of the PDF has spaces between every letter
	 */
	public static String filterWhitespaces (String text){
		String copy = new String(text); 
		double charCount = copy.replaceAll(" ", "").length();
		
		copy = copy.replace("  ", "$%*()$%*");
		copy = copy.replace(" ", "");
		copy = copy.replace("$%*()$%*", " ");
		
		if ((double) ((double) charCount/ (double) text.length())<=0.50){
			return copy;
		} else{
			return text;
		}
	}
}
