
import java.io.File;
import java.util.*;

/**
 * Takes a directory in its constructor
 * checks file types in the directory and sorts them into appropriate arrays
 */
public class FileTypeDetection {
    public FileTypeDetection(String DIRECTORY){
        this.DIRECTORY = DIRECTORY;
    }
    private String DIRECTORY;//absolute path to input

    public String getDirectory(){
        return this.DIRECTORY;
    }
    public void setDIRECTORY(String dir){
        this.DIRECTORY = dir;

    }

    //getting file counts --> convert counts to hashmaps "name / count" easier to access and less commands.

    private HashMap<String, Integer> fileCount = new HashMap<>();

    private int totalCount = 0;

    public int updateTotalCount(){
        return this.totalCount = this.totalCount+1;
    }
    public int getTotalCount(){
        return this.totalCount;
    }
    //data structures to store names of files in dependent on file type
    private ArrayList<String> pptxNames = new ArrayList<>();
    private ArrayList<String> docxNames= new ArrayList<>();
    private ArrayList<String> pdfNames = new ArrayList<>();
    private ArrayList<String> excelNames = new ArrayList<>();

    //if data structure for a specific file type is not created and added to the file organization then it will be added here
    private ArrayList<String> unknownNames = new ArrayList<>();

    public ArrayList<ArrayList<String>>  fileArrayOfArrays= new ArrayList<>();

    public void addArrayToArrayOfArrays(ArrayList<String> fileArray){
        this.fileArrayOfArrays.add(fileArray);
    }
    public ArrayList<ArrayList<String>> getfileArrayOfArrays(){
        return this.fileArrayOfArrays;
    }

    public static void setPDFNames() {

    }
    public static void setDOCXNames(){

    }
    public static void setPPTXNames(){

    }
    public ArrayList<String> getPDFNames(){
        return this.pdfNames;
    }
    public ArrayList<String> getDOCXNames(){
        return this.docxNames;
    }
    public ArrayList<String> getPPTXNames(){
        return this.pptxNames;
    }
    public ArrayList<String> getEXCELNames(){
        return this.excelNames;
    }

    /**
     * Opens a directory and begins adding file names into an appropriate arraylist of the document type
     */
    public void setFileNames() {

        try {
            for (File f : new File(DIRECTORY).listFiles()) {

                updateTotalCount();
                String fileName = f.toString();
                System.out.println("File name: " + f.getName());

                //takes extension of a file
                int index = fileName.lastIndexOf('.');
                if (index > 0) {

                    String extension = fileName.substring(index + 1);

                    if (!fileCount.containsKey(extension)) {
                        fileCount.put(extension, 1);

                    } else {

                        fileCount.put(extension, fileCount.get(extension) + 1);

                    }
                    //storing doc names into appropriate data structures
                    if (extension.equals("docx")) {
                        docxNames.add(f.getName());
                    } else if (extension.equals("pptx")) {
                        pptxNames.add(f.getName());
                    } else if (extension.equals("pdf")) {
                        pdfNames.add(f.getName());
                    }else if (extension.equals("xlsx")) {
                        excelNames.add(f.getName());
                    } else {
                        unknownNames.add(f.getName());
                    }
                }
            }
        }catch(NullPointerException e){
            System.out.println("File not found: " + e);
        }
    }

    /**
     * Prints the data retrieved from a document and placed into an object to the console
     */
    public void printFileNames(){
        System.out.println(
                "---------------------------------------------\n" +
                        "\t\tfiles in docx\n" +
                        "---------------------------------------------");

        for (String s : this.docxNames) {
            System.out.println("|\t"+ s + "\t\t\t|\n");
        }
        System.out.println(
                "---------------------------------------------\n" +
                        "\t\tfiles in pptx\n" +
                        "---------------------------------------------");

        for (String s : this.pptxNames) {
            System.out.println("|\t"+ s + "\t\t\t|\n");
        }
        System.out.println(
                "---------------------------------------------\n" +
                        "\t\tfiles in pdf\n" +
                        "---------------------------------------------");

        for (String s : this.pdfNames) {
            System.out.println("|\t"+ s + "\t\t\t|\n");
        }
        System.out.println(
                "---------------------------------------------\n" +
                        "\t\tfiles in excel\n" +
                        "---------------------------------------------");

        for (String s : this.excelNames) {
            System.out.println("|\t"+ s + "\t\t\t|\n");
        }

        System.out.println(
                "---------------------------------------------\n" +
                        "\t\tOther files\n" +
                        "---------------------------------------------");

        for (String s : this.unknownNames) {
            System.out.println("|\t"+ s + "\t\t\t|\n");
        }

    }


}
