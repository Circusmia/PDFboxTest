import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFboxTest {

	private static PDDocument doc = PDFboxTest.decryptDoc();

	public static void main(String[] args) {

//		PDFboxTest test = new PDFboxTest();
//		String text = test.getText(doc);
//		test.getParagraphs(text);
//		test.getMetadata(doc);

	}

	/*
	 * entschlüsselt das Input-Document, wenn es entschlüsselt ist return:
	 * entschlüsseltes pdf
	 */
	private static PDDocument decryptDoc() {

		PDDocument pdfDocument = null;
		try {
			pdfDocument = PDDocument.load("Docs/TestDoc.pdf");
			if (pdfDocument.isEncrypted()) {
				pdfDocument.decrypt("");
				pdfDocument.setAllSecurityToBeRemoved(true);
				pdfDocument.save(new File("decrytedPdf.pdf"));
			}
		} catch (Exception e) {
			System.out
					.println("The document is encrypted, and we can't decrypt it.");
			e.printStackTrace();
		}
		return pdfDocument;
	}

	/*
	 * liest Text aus pdf aus return: ausgelesenen Text
	 */
	private static String getText(PDDocument pdfDocument) {
		String text = null;

		try {
			PDFTextStripper pdfTextStripper = new PDFTextStripper();
			text = pdfTextStripper.getText(pdfDocument);
			// System.out.println(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return text;
	}

	/*
	 * liest Metadaten des pdf aus
	 */
	private static void getMetadata(PDDocument pdfDocument) {

		PDDocumentInformation info = pdfDocument.getDocumentInformation();
		
		System.out.println("Title: " + info.getTitle());
		System.out.println("Page Count: " + pdfDocument.getNumberOfPages() );
		System.out.println("Author: " + info.getAuthor());
		System.out.println("Subject: " + info.getSubject());
		System.out.println("Keywords: " + info.getKeywords());
		System.out.println("Creator: " + info.getCreator());
		System.out.println("Producer: " + info.getProducer());
		try {
			System.out.println("Created date "
					+ getProperDate(info.getCreationDate()));
			System.out.println("Modified date "
					+ getProperDate(info.getModificationDate()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Trapped " + info.getTrapped());

	}

	static String getProperDate(Calendar cal) {
		// We use this method to convert the Calendar object to a more readable
		// date.
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String properDate = dateFormat.format(cal.getTime());
		return properDate;
	}

	/*
	 * Absatz verstanden als Text zwischen zwei Überschriften, soll einzeln
	 * ausgelesen werden können return: einzelne Absätze
	 */
	private static String getParagraphs(String text) {

		String paragraph = null;
		String[] para = text.split("\n");

		Integer counter = 0;
		for (int i = 0; i < 10; i++) {

			System.out.println("Absatz " + counter);
			System.out.println(para[i]);
			System.out.println("\n\n");
			counter++;
		}

		return paragraph;
	}

}
