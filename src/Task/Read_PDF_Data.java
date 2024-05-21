package Task;



import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.state.PDGraphicsState;
import org.apache.pdfbox.text.PDFTextStripper;

public class Read_PDF_Data {
	
	private static Logger loggers = Logger.getLogger(Read_PDF_Data.class);
	
//	static {
//		try {
//			PropertyConfigurator.configure(
//					"C:\\Users\\SKTS_Admin_02\\eclipse-workspace\\Log4j\\Log4j Properties File\\Loggers.properties");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		}
//	}
	
	public static void main(String[] args) throws Exception {

		// PDF Path
		File file = new File("C:\\Users\\SKTS_Admin_02\\eclipse-workspace\\Log4j_Task\\Documents\\Read PDF File.pdf");

		// Loading the File by Creating PDDocument Object.

		PDDocument document = PDDocument.load(file);

		PDFTextStripper pdftextformat = new PDFTextStripper();  
		

		// Reading Certain Amount of Pages from the PDF

		pdftextformat.setStartPage(1);
		pdftextformat.setEndPage(15);

		// Reading the Data
		String text = pdftextformat.getText(document);
	
		 System.out.println(text);   
		  
		 // loggers.info(text);

		// Closing the PDDocuments.
		 document.close();

	}
}
