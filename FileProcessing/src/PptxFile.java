
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EmptyFileException;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.*;

public class PptxFile {
    File file;

    public PptxFile(String nameOfFile, String directory) throws IOException {
        this.file = new File(directory + nameOfFile);
        this.Directory = directory;
        this.fileName = nameOfFile;
        try {
            this.pptx = new XMLSlideShow(new FileInputStream(this.file));
        }catch(EmptyFileException e){
            System.out.println("Empty file: " + nameOfFile);
            return;
        }
        //sets the value of fileName to the parameter nameOfFile


        //calls the setFileSize method and returns the files length in bytes
        setFileSize();

        //calls the setAuthorName method and extracts the name of the file creator
        setAuthorName();

        //calls the setCreationDate method to extract timestamp
        setCreationDate();

        //calls the setNumberOfSlides methods and returns the number of slides counted
        setNumberOfSlides();

        //calls the setWordCount method which returns the word count
        //Issue, this method also counts words in images...
        setWordCount();

        //locates instances of a URL
        findUrl();

        this.setData();
        //createJSON();

    }

    //variable definitions

    private String fileName;
    private String Directory;

    private XMLSlideShow pptx;
    private int numberOfSlides;
    private String author;
    private String creationDate;
    private long fileSize;
    private int wordCount;
    private String allData;
    private final ArrayList<String> locatedURLs = new ArrayList<>();

    //get and set methods

    public String getFileName() {
        return this.fileName;
    }

    public void setFileSize() {
        this.fileSize = this.file.length();
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public void setAuthorName() {
        POIXMLProperties.CoreProperties props = this.pptx.getProperties().getCoreProperties();
        this.author = String.valueOf(props.getCreator());
    }

    public String getAuthor() {
        return this.author;
    }

    public void setNumberOfSlides() throws IOException {
        int slideCount = 0;
        //changed slide to ignored...not exactly sure how this works...
        for (XSLFSlide ignored : this.pptx.getSlides()) {
            slideCount++;
        }
        this.numberOfSlides = slideCount;
    }

    public int getNumberOfSlides() {
        return this.numberOfSlides;
    }

    public void setCreationDate() {
        POIXMLProperties.CoreProperties props = this.pptx.getProperties().getCoreProperties();
        this.creationDate = String.valueOf(props.getCreated());
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setWordCount() {
        int count = 0;
        for (XSLFSlide slide : this.pptx.getSlides()) {
            XSLFShape[] shapes = slide.getShapes().toArray(new XSLFShape[0]);
            for (XSLFShape shape : shapes) {
                if (shape instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape) shape;
                    String text = textShape.getText().trim();
                    if (text.length() != 0) {
                        count++;
                        //System.out.println("Text: " + text);
                    }
                }
            }
        }
        this.wordCount = count;
    }

    public int getWordCount() {
        return this.wordCount;
    }

    public void setData(){
        this.allData = "{'name': '" + getFileName() + "',\n 'author': '" + getAuthor() + "',\n 'slide count': " + getNumberOfSlides() +
                ",\n 'file size': " + getFileSize() + ",\n 'word count': " + getWordCount() + ",\n 'created': '" + getCreationDate() +
                "',\n'URLs':'" + getLocatedURLs() + "'}";
    }
    public String getData(){
        return this.allData;
    }
    public ArrayList<String> getLocatedURLs()
    {
        return this.locatedURLs;
    }
    public void findUrl(){
        for (XSLFSlide slide : this.pptx.getSlides()) {
            XSLFShape[] shapes = slide.getShapes().toArray(new XSLFShape[0]);
            for (XSLFShape shape : shapes) {
                if (shape instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape) shape;
                    String text = textShape.getText().trim();
                    if (text.length() != 0 ) {
                        if(isValidUrl(text))
                        {
                            locatedURLs.add(text);
                        }
                    }
                }

            }
        }
    }

    //runs a regex against the text in the file and see if it matches
    boolean isValidUrl(String urlValue)
    {
        String regex = "((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)";
        Pattern regexPattern = Pattern.compile(regex);
        if (urlValue == null)
        {
            return false;
        }
        Matcher acceptedUrl = regexPattern.matcher(urlValue);
        return acceptedUrl.matches();
    }
}
