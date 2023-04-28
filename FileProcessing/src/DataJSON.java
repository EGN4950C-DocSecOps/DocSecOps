import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

public class DataJSON {

    private String output;
    public DataJSON(String output){
        this.output = output;
    }

    public void toJSON(DocxFile file){
        Gson gson = new Gson();

        // Convert the input string to a JSON object
        Object jsonObject = gson.fromJson(file.getData(), Object.class);

        //change this directory to
        String outputFilePath = this.output;

        // Write the JSON object to a file
        try (FileWriter fileWriter = new FileWriter(outputFilePath+file.getFileName()+".json")) {
            gson.toJson(jsonObject, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void toJSON(PptxFile file){
        Gson gson = new Gson();

        // Convert the input string to a JSON object
        Object jsonObject = gson.fromJson(file.getData(), Object.class);
        String outputFilePath = this.output;

        // Write the JSON object to a file
        try (FileWriter fileWriter = new FileWriter(outputFilePath + file.getFileName() + ".json")) {
            gson.toJson(jsonObject, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void toJSON(PdfFile file){
        Gson gson = new Gson();

        // Convert the input string to a JSON object
        Object jsonObject = gson.fromJson(file.getData(), Object.class);
        String outputFilePath = this.output;

        // Write the JSON object to a file
        try (FileWriter fileWriter = new FileWriter(outputFilePath + file.getFileName() + ".json")) {
            gson.toJson(jsonObject, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void toJSON(ExcelFile file){
        Gson gson = new Gson();

        // Convert the input string to a JSON object
        Object jsonObject = gson.fromJson(file.getData(), Object.class);

        //change this directory to
        String outputFilePath = this.output;

        // Write the JSON object to a file
        try (FileWriter fileWriter = new FileWriter(outputFilePath + file.getFileName() + ".json")) {
            gson.toJson(jsonObject, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
