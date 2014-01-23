/**
 * Created with IntelliJ IDEA.
 * User: ongzexuan
 * Date: 9/1/14
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */

import com.itextpdf.text.*;
import com.itextpdf.text.io.*;
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

    public pdfParserDevice(String in) {
        this.in = in;
    }

    public pdfParserDevice(String in, String out) {
        this.in = in;
        this.out = out;
    }

    public void output() {
        if (!in.equals("") && !out.equals("")) {
            String s = getFields();
            try {
                PrintWriter pw = new PrintWriter(new FileOutputStream(out));//output
                pw.print(s);
                pw.flush();
                pw.close();
                System.out.println("successful data output to "+out);
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("input/output file is missing!");
        }

    }

    public String getFields() {
        String rtnString = "";
        try {


            PdfReader reader = new PdfReader(in);//input

            AcroFields form = reader.getAcroFields();//get AcroFields (i.e. fill-in-spaces)

            Set<String> fields = form.getFields().keySet();
            for (String key : fields) {

                boolean isCheckBox = false;

                rtnString = rtnString = rtnString.concat(key + ":");
                switch(form.getFieldType(key)) {   //may be useful for sorting later
                    case AcroFields.FIELD_TYPE_CHECKBOX:
                        rtnString = rtnString.concat("Checkbox");
                        isCheckBox = true;
                        break;
                    case AcroFields.FIELD_TYPE_COMBO:
                        rtnString = rtnString.concat("Combobox");
                        break;
                    case AcroFields.FIELD_TYPE_LIST:
                        rtnString = rtnString.concat("List");
                        break;
                    case AcroFields.FIELD_TYPE_NONE:
                        rtnString = rtnString.concat("None");
                        break;
                    case AcroFields.FIELD_TYPE_PUSHBUTTON:
                        rtnString = rtnString.concat("Pushbutton");
                        break;
                    case AcroFields.FIELD_TYPE_RADIOBUTTON:
                        rtnString = rtnString.concat("Radiobutton");
                        break;
                    case AcroFields.FIELD_TYPE_SIGNATURE:
                        rtnString = rtnString.concat("Signature");
                        break;
                    case AcroFields.FIELD_TYPE_TEXT:
                        rtnString = rtnString.concat("Text");
                        break;
                    default:
                        rtnString = rtnString.concat("?");
                }

                //post-processing for checkboxes to print no when null value is read
                String data = form.getField(key);
                if (data.equals("")) {
                    if(isCheckBox) {
                        data = "No";
                    }
                }

                //print data
                rtnString = rtnString.concat(":" + data + "\n");

            }

            reader.close();

            // Get possible values for field "CP_1"
//                pw.println("Possible values for SCActEnd:");
//                String[] states = form.getAppearanceStates("SCActEnd");
//                for (int i = 0; i < states.length; i++) {
//                    pw.print(" - ");
//                    pw.println(states[i]);
//                }


        } catch(Exception e) {
            e.printStackTrace();
        }
        return rtnString;
    }

}
