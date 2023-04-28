import java.io.IOException;
import java.util.Objects;

/**
 * Run this file to begin processing files within a directory
 */
public class Driver {

    //change to accept an array of arrays and create a for loop to enter each to make it.
    public static void main(String[] args) throws IOException {
        //defining the input directory and the Output directory
        String directory = "./src/FileInput/";
        String output = "./src/FileOutput/";

        System.out.println("Running second program\n");
        System.out.println(System.getProperty("user.dir")+"./src/FileInput");

        //Create FilTypeDetection Object and set the file name then print them
        FileTypeDetection FilesForClassOne = new FileTypeDetection(directory);
        FilesForClassOne.setFileNames();
        FilesForClassOne.printFileNames();

        //Creates objects for each class
        FileObjectCreation createObj = new FileObjectCreation();
        createObj.createDocxObjects(FilesForClassOne.getDOCXNames(), directory);
        createObj.createPdfObjects(FilesForClassOne.getPDFNames(), directory);
        createObj.createPptxObjects(FilesForClassOne.getPPTXNames(), directory);
        createObj.createExcelObjects(FilesForClassOne.getEXCELNames(), directory);

        //creates JSON equivalent of each file
        DataJSON JSON = new DataJSON(output);


        //Everything past this line simply prints file information onto console
        System.out.println("\n\n-------------\n\nword documents\n\n-------------");
        for(DocxFile docs: createObj.getListOfDocxObjects()){

            System.out.println("name of file: "+docs.getFileName()+"\n");
            System.out.println("name of author: "+docs.getAuthor()+"\n");
            System.out.println("Word count: "+docs.getWordCount()+"\n");
            System.out.println("file size: " + docs.getFileSize()+"\n");
            System.out.println("Page count: "+docs.getPageCount()+"\n");
            System.out.println("Date Created: "+docs.getDateOfCreation()+"\n");
            //if this string gets added, it will check the URLs
            //To test, you can go to Run -> edit configurations -> then add "-validateURL" to argument parameters
            if(args.length >= 1 && Objects.equals(args[0], "-validateURL"))
            {
                LinkDetection.main(docs.getLocatedURLs());
            }
            JSON.toJSON(docs);
            System.out.println("\n\n-------------------");
        }
        System.out.println("\n\nPrinting pdf file data \n\n-----------------");
        for(PdfFile pdf: createObj.getListOfPdfObjects()){


            System.out.println("name of file: " + pdf.getFileName()+"\n");
            System.out.println("name of author: " + pdf.getAuthor()+"\n");
            System.out.println("page count: " + pdf.getPageCount()+"\n");
            System.out.println("file size: " + pdf.getFileSize()+"\n");
            System.out.println("word count: " + pdf.getWordCount()+"\n");
            System.out.println("date created: " + pdf.getFileMonth()+"/"+pdf.getFileDay()+
                    "/"+pdf.getFileYear()+" " +pdf.getFileHour()+":"+pdf.getFileMinute()+":"
                    +pdf.getFileSecond()+"\n");
            //if this string gets added, it will check the URLs
            //To test, you can go to Run -> edit configurations -> then add "-validateURL" to argument parameters
            if(args.length >= 1 && Objects.equals(args[0], "-validateURL"))
            {
                LinkDetection.main(pdf.getLocatedURLs());
            }
            JSON.toJSON(pdf);
            System.out.println("\n\n-------------------");
        }
        System.out.println("\n\nPowerpoint files \n\n---------------");
        for(PptxFile pptx: createObj.getListOfPptxObjects()){

            System.out.println("name of file: "+pptx.getFileName()+"\n");
            System.out.println("name of author: "+ pptx.getAuthor()+"\n");
            System.out.println("Word count: "+ pptx.getWordCount()+"\n");
            System.out.println("file size: " + pptx.getFileSize()+"\n");
            System.out.println("Page count: "+ pptx.getNumberOfSlides()+"\n");
            System.out.println("date created: " + pptx.getCreationDate()+"\n");
            //if this string gets added, it will check the URLs
            //To test, you can go to Run -> edit configurations -> then add "-validateURL" to argument parameters
            if(args.length >= 1 && Objects.equals(args[0], "-validateURL"))
            {
                LinkDetection.main(pptx.getLocatedURLs());
            }
            JSON.toJSON(pptx);
            System.out.println("\n\n-------------------");
        }
        System.out.println("\n\nExcel files \n\n---------------");
        for(ExcelFile excel: createObj.getListOfExcelObjects()){

            System.out.println("name of file: "+excel.getFileName()+"\n");
            System.out.println("name of author: "+ excel.getAuthor()+"\n");
            System.out.println("Word count: "+ excel.getWordCount()+"\n");
            System.out.println("file size: " + excel.getFileSize()+"\n");
            System.out.println("Row count: " + excel.getRowCount()+"\n");
            System.out.println("date created: " + excel.getCreationTime() + "\n");
            //if this string gets added, it will check the URLs
            //To test, you can go to Run -> edit configurations -> then add "-validateURL" to argument parameters
            if(args.length >= 1 && Objects.equals(args[0], "-validateURL"))
            {
                LinkDetection.main(excel.getLocatedURLs());
            }
            JSON.toJSON(excel);
            System.out.println("\n\n-------------------");

        }

    }
}
