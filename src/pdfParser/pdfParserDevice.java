package pdfParser; /**
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


/**
 * A manager object for handling PDF file requests and processes.
 */
public class pdfParserDevice {

    private String in;
    private String out;

    public pdfParserDevice() {
        in = "";
        out = "";
    }

    /**
     * Creates a pdfParserDevice with the PDF filepath in.
     *
     * @param in Filepath of input PDF
     */
    public pdfParserDevice(String in) {
        this.in = in;
    }

    /**
     * Creates a pdfParserDevice with the PDF filepath in and the output filepath out.
     *
     * @param in Filepath of input PDF
     * @param out Filepath of output txt
     */
    public pdfParserDevice(String in, String out) {
        this.in = in;
        this.out = out;
    }

    /**
     * Prints the Acrofields data of the input PDF to the output file as defined by in and out.
     */
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

    /**
     * Returns the Acrofield data of the input PDF in a String format denoted by FieldName:FieldType:FieldValue for each entry.
     * Similar to output(), except that the data is returned as a String instead.
     */
    public String getFields() {
        String rtnString = "";
        try {


            PdfReader reader = new PdfReader(in);//input

            AcroFields form = reader.getAcroFields();//get AcroFields (i.e. fill-in-spaces)

            Set<String> fields = form.getFields().keySet();
            for (String key : fields) {

                boolean isCheckBox = false;

                rtnString = rtnString.concat(key + ":");
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
