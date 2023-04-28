import com.google.gson.Gson;
import org.apache.poi.EmptyFileException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class DocxFile {
    File file;
    XWPFDocument document;

    /**
     * Constructor
     * @param name name of the file
     * @param Directory directory of the file
     * @throws IOException throws exception if file is not found
     * @throws InvalidFormatException throws invalidformatexception if the file is not of the correct type
     */
    public DocxFile(String name, String Directory) throws IOException {
        this.fileName = name;
        this.Directory = Directory;
        this.file = new File(Directory+name);
        try {
            this.document = new XWPFDocument(new FileInputStream(this.file));
        }catch(EmptyFileException e){
            System.out.println("emptyfile " + name);
            return;
        }

        this.setWordCount();
        this.setPageCount();
        this.setAuthor();
        this.setDateOfCreation();
        this.setFileSize();
        findUrl();
        this.setData();
    }

    private String Directory;
    private String allData;
    private int wordCount;
    private int pageCount;
    private String author;
    private long fileSize;
    private Date dateOfCreation;
    private String fileName;
    private final ArrayList<String> locatedURLs = new ArrayList<>();

    /**
     * Sets word count for the Word document by parsing text
     * @throws IOException throws IOexception if file is not found
     */
    public void setWordCount() throws IOException, EmptyFileException {

        XWPFWordExtractor extractor = new XWPFWordExtractor(this.document);
        String allText = extractor.getText();
        String[] words = allText.split("\\s+");
        this.wordCount = words.length;

    }

    public Integer getWordCount(){
        return this.wordCount;
    }
    public void setPageCount() {
        this.pageCount = this.document.getProperties().getExtendedProperties().getPages();

    }
    public int getPageCount(){
        if(pageCount < 0)
        {
            pageCount = pageCount * -1;
        }
        return this.pageCount;
    }
    public void setAuthor() throws IOException {
        this.author = this.document.getProperties().getCoreProperties().getCreator();
    }
    public String getAuthor(){
        return this.author;
    }
    public void setFileSize(){
        File file = new File(Directory+fileName);

        // Get the size of the file in bytes
        this.fileSize = file.length();
    }
    public long getFileSize(){
        return this.fileSize;
    }
    public void setDateOfCreation(){
        // Get the creation date of the document from its properties
        this.dateOfCreation = this.document.getProperties().getCoreProperties().getCreated();
    }
    public Date getDateOfCreation(){
        return this.dateOfCreation;
    }
    public String getFileName() {
        return this.fileName;
    }

    public ArrayList<String> getLocatedURLs()
    {
        return this.locatedURLs;
    }
    //Iterates through the file and locates an instance of a hyperlink
    public void findUrl(){
        try {
            Iterator<XWPFParagraph> i = document.getParagraphsIterator();
            while(i.hasNext()) {
                XWPFParagraph paragraph = i.next();
                //Going through paragraph text
                for (XWPFRun run : paragraph.getRuns()) {
                    if (run instanceof XWPFHyperlinkRun) {
                        //Finding the urls
                        XWPFHyperlink link = ((XWPFHyperlinkRun) run).getHyperlink(document);
                        String linkURL = link.getURL();
                        locatedURLs.add(linkURL);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setData(){
        this.allData =  "{'name': '" + getFileName() + "',\n 'author': '" + getAuthor() + "',\n 'page count': " + getPageCount() +
                ",\n 'file size': " + getFileSize() + ",\n 'word count': " + getWordCount() + ",\n 'created': '" + getDateOfCreation() +
                "',\n'URLs':'" + getLocatedURLs() + "'}";
    }
    public String getData(){
        return this.allData;
    }
}