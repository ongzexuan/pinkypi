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

public class pdfParser {


    public static void main(String[] args) {

        System.out.println("Program started");

        String in = "ssef2013forms.pdf";
        String out = "output.txt";
        //extractText(in, out);
        getFields(in,out);


        System.out.println("Program ended");

    }

    public static void getFields(String in, String out) {
        try {


            PdfReader reader = new PdfReader(in);
            PrintWriter pw = new PrintWriter(new FileOutputStream(out));
            AcroFields form = reader.getAcroFields();

            Set<String> fields = form.getFields().keySet();
            for (String key : fields) {
                pw.print(key + ": ");
                switch(form.getFieldType(key)) {
                    case AcroFields.FIELD_TYPE_CHECKBOX:
                        pw.println("Checkbox");
                        break;
                    case AcroFields.FIELD_TYPE_COMBO:
                        pw.println("Combobox");
                        break;
                    case AcroFields.FIELD_TYPE_LIST:
                        pw.println("List");
                        break;
                    case AcroFields.FIELD_TYPE_NONE:
                        pw.println("None");
                        break;
                    case AcroFields.FIELD_TYPE_PUSHBUTTON:
                        pw.println("Pushbutton");
                        break;
                    case AcroFields.FIELD_TYPE_RADIOBUTTON:
                        pw.println("Radiobutton");
                        break;
                    case AcroFields.FIELD_TYPE_SIGNATURE:
                        pw.println("Signature");
                        break;
                    case AcroFields.FIELD_TYPE_TEXT:
                        pw.println("Text");
                        break;
                    default:
                        pw.println("?");
                }




            }

            pw.flush();
            pw.close();
            reader.close();



        } catch(Exception e) {
            e.printStackTrace();
        }
    }

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
