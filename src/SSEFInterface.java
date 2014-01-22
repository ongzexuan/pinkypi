import java.io.File;

/**
 * Created by hohankitivan on 1/22/14.
 */
public class SSEFInterface {
    private String weather;

    // poll() will update fields in the future when SSEFInterface communicates with the Models
    public void poll(){

    }

    public void uploadFile(String code, String filename){
        pdfParserDevice parser = new pdfParserDevice(filename, "temp.txt");
        parser.getFields();
        FormData fd = new FormData(code, "temp.txt");
        DatabaseExec de = new DatabaseExec("SSEF");
        de.updateToDatabase(fd);
        File temp = new File("temp.txt");
        if (temp.delete()) System.out.println("File uploaded successfully!");;
    }
}
