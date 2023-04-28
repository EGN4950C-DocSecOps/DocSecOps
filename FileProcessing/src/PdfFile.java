import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PdfFile {


    private final PDDocument doc;

    public PdfFile(String fileName, String directory) throws IOException {


        File file = new File(directory + fileName);
        this.doc = PDDocument.load(file);
        PDDocumentInformation pdd = this.doc.getDocumentInformation();

        this.fileName = fileName;
        this.author = pdd.getAuthor();

        this.pageCount = doc.getNumberOfPages();
        this.fileSize = file.length();
        setWordCount();

        this.fileMonth = (pdd.getCreationDate()).get(Calendar.MONTH);
        this.fileDay = (pdd.getCreationDate()).get(Calendar.DATE);
        this.fileYear = (pdd.getCreationDate()).get(Calendar.YEAR);
        this.fileHour = (pdd.getCreationDate()).get(Calendar.HOUR);
        this.fileMinute = (pdd.getCreationDate()).get(Calendar.MINUTE);
        this.fileSecond = (pdd.getCreationDate()).get(Calendar.SECOND);

        this.creationDate = getFileMonth()+"/"+getFileDay()+
                "/"+getFileYear()+" " +getFileHour()+":"+getFileMinute()+":"
                +getFileSecond();
        findUrl();
        doc.close();
        this.setData();
    }

    private final int fileMonth;
    private final int fileDay;
    private final int fileYear;
    private final int fileHour;
    private final int fileMinute;
    private final int fileSecond;
    private int wordCount;
    private String allData;
    private final int pageCount;
    private String author;
    private final long fileSize;
    private final String creationDate;
    private final String fileName;
    private final ArrayList<String> locatedURLs = new ArrayList<>();

    //Get and set methods
    public void setWordCount() throws IOException {
        int count = 0;
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setEndPage(20);
        String text = stripper.getText(this.doc);
        String[] texts = text.split(" ");
        for (String txt : texts) {
            if(txt.trim().length() != 0)
            {
                count = count + 1;
            }
        }


        this.wordCount = count;
    }
    public Integer getWordCount(){
        return this.wordCount;
    }
    public int getPageCount(){
        return this.pageCount;
    }
    public void setAuthor(String name){
        this.author = name;
    }
    public String getAuthor(){
        return this.author;
    }
    public long getFileSize(){
        return this.fileSize;
    }
    public String getDateOfCreation(){
        return this.creationDate;
    }
    public String getFileName() {return this.fileName;}

    public void setData(){
        this.allData = "{'name': '" + getFileName() + "',\n 'author': '" + getAuthor() + "',\n 'page count': " + getPageCount() +
                ",\n 'file size': " + getFileSize() + ",\n 'word count': " + getWordCount() + ",\n 'created': '" + getDateOfCreation() +
                "',\n'URLs':'" + getLocatedURLs() + "'}";
    }
    public String getData(){
        return this.allData;
    }

    public int getFileMonth(){
        return this.fileMonth;
    }
    public int getFileDay(){
        return this.fileDay;
    }
    public int getFileYear(){
        return this.fileYear;
    }
    public int getFileHour(){
        return this.fileHour;
    }
    public int getFileMinute(){
        return this.fileMinute;
    }
    public int getFileSecond(){
        return this.fileSecond;
    }
    public ArrayList<String> getLocatedURLs()
    {
        return this.locatedURLs;
    }

    public void findUrl() throws IOException {
        //Apache Poi contained a method for extracted URLs for PDFs, below is the link
        //https://svn.apache.org/repos/asf/pdfbox/trunk/examples/src/main/java/org/apache/pdfbox/examples/pdmodel/PrintURLs.java
        for( PDPage page : doc.getPages() )
        {
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            List<PDAnnotation> annotations = page.getAnnotations();
            //first setup text extraction regions
            for( int j=0; j<annotations.size(); j++ )
            {
                PDAnnotation annot = annotations.get(j);
                if( annot instanceof PDAnnotationLink)
                {
                    PDAnnotationLink link = (PDAnnotationLink)annot;
                    PDRectangle rect = link.getRectangle();
                    float x = rect.getLowerLeftX();
                    float y = rect.getUpperRightY();
                    float width = rect.getWidth();
                    float height = rect.getHeight();
                    int rotation = page.getRotation();
                    if( rotation == 0 )
                    {
                        PDRectangle pageSize = page.getMediaBox();
                        y = pageSize.getHeight() - y;
                    }
                    else if( rotation == 90 )
                    {
                        //do nothing
                        System.out.println("Nothing occurred");
                    }

                    Rectangle2D.Float awtRect = new Rectangle2D.Float( x,y,width,height );
                    stripper.addRegion( "" + j, awtRect );
                }
            }

            stripper.extractRegions( page );

            for (PDAnnotation annot : annotations) {
                if (annot instanceof PDAnnotationLink) {
                    PDAnnotationLink link = (PDAnnotationLink) annot;
                    PDAction action = link.getAction();
                    if (action instanceof PDActionURI) {
                        PDActionURI uri = (PDActionURI) action;
                        locatedURLs.add(uri.getURI());
                    }
                }
            }
        }
    }
}
