import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.tika.Tika;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.microsoft.xml.WordMLParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.GregorianCalendar;
import java.util.logging.Logger;



public class PoiTests {

    static final Logger logger = Logger.getLogger(String.valueOf(PoiTests.class));
    static final String LOG_PROPERTIES_FILE = "log4j.properties";

    public void wordToString() {
        try {

            XWPFDocument docx = new XWPFDocument((new FileInputStream("./src/FileInput/Jarold_Sabillon_Resume.pdf")));
            XWPFWordExtractor we = new XWPFWordExtractor(docx);
            System.out.print(we.getText());

            String smallfile = we.getText();
            String[] swearwords = smallfile.split(" ");
            for (String s : swearwords) {
                System.out.println(s);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static String parseToStringExample(String document_location) throws IOException, SAXException, TikaException {
        InputStream input = new FileInputStream(document_location);

        Detector detector = new DefaultDetector();
        Metadata metadata = new Metadata();

        MediaType mediaType = detector.detect(input, metadata);
        return mediaType.toString();


    }

    public static void main(String[] args) throws Exception {
        // Create a Tika instance with the default configuration

        File document = new File("./src/FileInput/Group22_DocSecOps_Proposal.docx");
        String content = new Tika().parseToString(document);


        InputStream stream = new FileInputStream(document);
        Tika tika = new Tika();
        String mediaType = tika.detect(stream);
        System.out.println(mediaType);

        File file = new File("./src/FileInput/Jarold_Sabillon_Resume.pdf");
        PDDocument doc = PDDocument.load(file);

        PDDocumentInformation pdd = doc.getDocumentInformation();
        System.out.println("Author of the PDF document is :"+ pdd.getAuthor());
        System.out.println("Title of the PDF document is :"+ pdd.getTitle());
        System.out.println("Subject of the document is :"+ pdd.getSubject());

        System.out.println("Creator of the PDF document is :"+ pdd.getCreator());
        System.out.println("Keywords of the PDF document are :"+ pdd.getKeywords());

        System.out.println("Creation date of the PDF document is :"+ pdd.getCreationDate());
        GregorianCalendar date = (GregorianCalendar) pdd.getCreationDate();
        System.out.println("to greg cal: "+ date );

        System.out.println("Modification date of the PDF document is :"+ pdd.getModificationDate());



    }
}

