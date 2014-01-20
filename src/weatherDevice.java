/**
 * Created by ongzexuan on 20/1/14.
 */

import com.itextpdf.text.*;
import com.itextpdf.text.io.RandomAccessSource;
import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.*;

import com.sun.org.apache.xerces.internal.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import javax.xml.parsers.*;
import javax.xml.parsers.SAXParser;
import javax.xml.transform.*;
import javax.xml.stream.*;
import javax.xml.stream.events.*;

import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.io.*;

public class weatherDevice {

    private String in;
    private String out;
    private URL url = null;

    public weatherDevice() {
        this.in = "";
        this.out = "";
    }

    public weatherDevice(String in, String out) {
        this.in = in;
        this.out = out;
    }

    public weatherDevice(String in, String out, String link) {
        this.in = in;
        this.out = out;
        try {
            this.url = new URL(link);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void pullDataFromWeb() {
        String filename = "weather.xml";

        if (url == null) url = getURL("http://weather.yahooapis.com/forecastrss?w=1062617");
        String contents = readData(url);
        writeData(contents, new File(filename));
        System.out.println("pullData() executed successfully");

        pullDataFromSource(new File(filename));
    }

    public void pullDataFromSource(File file) {
        //SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        try {

//            SAXParser saxParser = saxFactory.newSAXParser();
//            SAXHandler saxHandler = new SAXHandler();
//            saxParser.parse(ClassLoader.getSystemResourceAsStream(file.getName()), saxHandler);
            XMLReader xr = XMLReaderFactory.createXMLReader();


        } catch(Exception e) {
            e.printStackTrace();
        }


    }



    //method to get url
    public URL getURL(String link) {
        try {
            return new URL(link);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readData(URL url) {
        BufferedReader inReader = null;
        try {
            URLConnection conn = url.openConnection();
            inReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = inReader.readLine();
            StringBuilder builder = new StringBuilder();
            do {
                builder.append(line+"\n");
            } while((line = inReader.readLine()) != null);
            return builder.toString();


        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (inReader != null) try {
                inReader.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public String writeData(String content, File file) {
        BufferedWriter outWriter = null;
        try {
            outWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            outWriter.write(content);
            outWriter.flush();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) try {
                outWriter.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    private class SAXHandler extends DefaultHandler {

        public void startElement(String uri, String name, String qName, Attributes atts) {
            if (uri.equals(" ")) {
                System.out.println("Start element: "+qName);
            } else {
                System.out.println("Start element: {"+uri+"}"+name);
            }
        }

        public void endElement(String uri, String name, String qName) {
            if (uri.equals(" ")) {
                System.out.println("End element: "+qName);
            } else {
                System.out.println("End element: {"+uri+"}"+name);
            }
        }

        public void characters (char ch[], int start, int length)
        {
            System.out.print("Characters:    \"");
            for (int i = start; i < start + length; i++) {
                switch (ch[i]) {
                    case '\\':
                        System.out.print("\\\\");
                        break;
                    case '"':
                        System.out.print("\\\"");
                        break;
                    case '\n':
                        System.out.print("\\n");
                        break;
                    case '\r':
                        System.out.print("\\r");
                        break;
                    case '\t':
                        System.out.print("\\t");
                        break;
                    default:
                        System.out.print(ch[i]);
                        break;
                }
            }
            System.out.print("\"\n");
        }
    }






}
