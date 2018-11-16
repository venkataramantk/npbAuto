package ui.utils;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by skonda on 6/8/2016.
 */
public class FileUtils {
    private static final Logger logger = Logger.getLogger(FileUtils.class);

    public static String readFileContent(String fileAbsolutePath) throws IOException {

        File file = new File(fileAbsolutePath);
        FileReader fileInputStream = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(fileInputStream);

        String line = "";
        String outputString = "";
        while ((line = bufferReader.readLine()) != null) {
            outputString = outputString + line;
        }
        logger.info("Return String after reading from File:" +outputString);
        return outputString;
    }


    public static void writeDataToFile(String fileAbsolutePath,String textToWrite)
    {

        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileAbsolutePath, true)))) {
            out.println(textToWrite);
            logger.info("Text written to file is: " +textToWrite);
        }catch (IOException e) {
            logger.info("Got Exception in WriteToFile: "+ e.getMessage());
        }
    }
}
