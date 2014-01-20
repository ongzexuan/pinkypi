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

import javax.xml.parsers.*;
import javax.xml.transform.*;

import java.net.URL;
import java.util.*;
import java.io.*;

public class pdfParser {

    private static URL url;

    public static void main(String[] args) {

        weatherDevice wd = new weatherDevice();
        wd.pullDataFromWeb();

//        System.out.println("Program started");
//
//        String in = "ssef2013forms.pdf";
//        String out = "output.txt";
//        getFields(in,out);
//
//        getRSS("http://weather.yahooapis.com/forecastrss?w=1062617");
//
//
//        System.out.println("Program ended");

    }

    //EVERYTHING ELSE IS IRRELEVANT DO NOT USE

    public static void getFields(String in, String out) {
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

//    public static void getRSS(String link) {
//        try {
//            this.url =  new URL(link);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//
//    }



    //DO NOT USE ANYTHING BELOW THIS, NOT RELEVANT.
    //Parse PDF in gibberish 4 value terms I can't make sense of at the moment
    public static void parsePdf(String in, String out) {
        try{
            PdfReader reader = new PdfReader(in);
            byte[] streambytes = reader.getPageContent(1);
            PRTokeniser tk = new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(streambytes)));
            PrintWriter pw = new PrintWriter(new FileOutputStream(out));
            while (tk.nextToken()) {
                if (tk.getTokenType() == PRTokeniser.TokenType.STRING) {
                    pw.println(tk.getStringValue());
                }
            }
            pw.flush();
            pw.close();
            reader.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //Output PDF text into a file
    public static void extractText(String in, String out) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(out));
            PdfReader reader = new PdfReader(in);
            RenderListener listener = new MyTextRenderListener(pw);
            PdfContentStreamProcessor processor = new PdfContentStreamProcessor(listener);
            PdfDictionary pageDic = reader.getPageN(1);
            PdfDictionary resourceDic = pageDic.getAsDict(PdfName.RESOURCES);
            processor.processContent(ContentByteUtils.getContentBytesForPage(reader, 1), resourceDic);
            pw.flush();
            pw.close();
            reader.close();

        } catch(Exception e) {
            e.printStackTrace();
        }



    }

    //inner helper class MyTextRenderListener
    static class MyTextRenderListener implements RenderListener {
        protected PrintWriter pw;

        public MyTextRenderListener(PrintWriter pw) {
            this.pw = pw;
        }

        public void beginTextBlock() {
            pw.print("<");
        }

        public void endTextBlock() {
            pw.println(">");
        }

        public void renderImage(ImageRenderInfo renderInfo) {

        }

        public void renderText(TextRenderInfo renderInfo) {
            pw.print("<");
            pw.print(renderInfo.getText());
            pw.println(">");
        }

    }

}
