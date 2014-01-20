/**
 * Created with IntelliJ IDEA.
 * User: ongzexuan
 * Date: 9/1/14
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */

import com.itextpdf.text.*;
import com.itextpdf.text.io.RandomAccessSource;
import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.*;

import java.util.*;
import java.io.*;

public class pdfParserDevice {

    private String in;
    private String out;

    public pdfParserDevice() {
        in = "";
        out = "";
    }

    public pdfParserDevice(String in, String out) {
        this.in = in;
        this.out = out;
    }

    public void getFields() {
        try {

            PdfReader reader = new PdfReader(in);//input
            PrintWriter pw = new PrintWriter(new FileOutputStream(out));//output
            AcroFields form = reader.getAcroFields();//get AcroFields (i.e. fill-in-spaces)

            Set<String> fields = form.getFields().keySet();
            for (String key : fields) {

                boolean isCheckBox = false;

                pw.print(key + ":");
                switch(form.getFieldType(key)) {   //may be useful for sorting later
                    case AcroFields.FIELD_TYPE_CHECKBOX:
                        pw.print("Checkbox");
                        isCheckBox = true;
                        break;
                    case AcroFields.FIELD_TYPE_COMBO:
                        pw.print("Combobox");
                        break;
                    case AcroFields.FIELD_TYPE_LIST:
                        pw.print("List");
                        break;
                    case AcroFields.FIELD_TYPE_NONE:
                        pw.print("None");
                        break;
                    case AcroFields.FIELD_TYPE_PUSHBUTTON:
                        pw.print("Pushbutton");
                        break;
                    case AcroFields.FIELD_TYPE_RADIOBUTTON:
                        pw.print("Radiobutton");
                        break;
                    case AcroFields.FIELD_TYPE_SIGNATURE:
                        pw.print("Signature");
                        break;
                    case AcroFields.FIELD_TYPE_TEXT:
                        pw.print("Text");
                        break;
                    default:
                        pw.print("?");
                }

                //post-processing for checkboxes to print no when null value is read
                String data = form.getField(key);
                if (data.equals("")) {
                    if(isCheckBox) {
                        data = "No";
                    }
                }

                //print data
                pw.println(":" + data);


            }

            // Get possible values for field "CP_1"
//                pw.println("Possible values for SCActEnd:");
//                String[] states = form.getAppearanceStates("SCActEnd");
//                for (int i = 0; i < states.length; i++) {
//                    pw.print(" - ");
//                    pw.println(states[i]);
//                }


            //close writer and reader
            pw.flush();
            pw.close();
            reader.close();



        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
