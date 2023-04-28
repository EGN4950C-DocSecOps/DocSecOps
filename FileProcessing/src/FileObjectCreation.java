import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * Creates objects from the files being passed
 * This file must be edited for every new file class type being added
 * ArrayList<___>listOf___Objects - array that will contain the document objects
 * get List of___Objects()    - a getter function to retrieve list of objects
 * add___Objects() -    A function that adds an object to the list
 * Create___Objects()   - A function that creates an object from a file
 */
public class FileObjectCreation {

    private static String DIRECTORY ;

    //Add a new ArrayList of file type
    private final ArrayList<DocxFile> listOfDocxObjects = new ArrayList<>();
    private final ArrayList<PptxFile> listOfPptxObjects = new ArrayList<>();
    private final ArrayList<PdfFile> listOfPdfObjects = new ArrayList<>();
    private final ArrayList<ExcelFile> listOfExcelObjects = new ArrayList<>();

    //Must create a new getter method for each file type

    public ArrayList<DocxFile> getListOfDocxObjects() {
        return this.listOfDocxObjects;
    }

    public ArrayList<PdfFile> getListOfPdfObjects(){ return this.listOfPdfObjects;}

    public ArrayList<PptxFile> getListOfPptxObjects(){ return this.listOfPptxObjects;}

    public ArrayList<ExcelFile> getListOfExcelObjects(){ return this.listOfExcelObjects;}

    //Adds the object to the list
    public void addDocxObject(DocxFile file){
        this.listOfDocxObjects.add(file);
    }
    public void addPdfObject(PdfFile file){ this.listOfPdfObjects.add(file);}
    public void addPptxObject(PptxFile file){ this.listOfPptxObjects.add(file);}
    public void addExcelObject(ExcelFile file){ this.listOfExcelObjects.add(file);}

    //Creates objects from a list of file names
    public void createDocxObjects(ArrayList<String> docx, String dir) throws IOException {

        DIRECTORY = dir;
        for(String file: docx){
            addDocxObject(new DocxFile(file, DIRECTORY));
        }
    }
    public void createPptxObjects(ArrayList<String> pptx, String dir) throws IOException {
        DIRECTORY = dir;
        for(String file: pptx){
            addPptxObject(new PptxFile(file, DIRECTORY));
        }
    }
    public void createPdfObjects(ArrayList<String> pdf, String dir) throws IOException {
        DIRECTORY = dir;
        for(String file:pdf) {
            addPdfObject(new PdfFile(file, DIRECTORY));
        }
    }
    public void createExcelObjects(ArrayList<String> excel, String dir) throws IOException {
        DIRECTORY = dir;
        for(String file:excel) {
            addExcelObject(new ExcelFile(file, DIRECTORY));
        }
    }

}
