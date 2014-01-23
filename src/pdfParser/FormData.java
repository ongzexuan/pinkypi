package pdfParser;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * A macro data structure containing a LinkedList of DataSet, thus being able to effectively capture every data field within the SSEF form.
 * A SSEF code is required for unique identification.
 */
public class FormData {
	String SSEFCode;
	LinkedList<DataSet> dataList;
	
	public FormData(String SSEFCode, String content) {
		this.SSEFCode = SSEFCode;
        dataList = new LinkedList<DataSet>();
		readFromFile(content);
	}
	
	/**
	 * Reads from text parsed from the pdfParserDevice, and populates the LinkedList based on its datatype.
	 * @param input Output of pdfParserDevice that contains information on the various field names, field types and field data
	 */
	private void readFromFile(String input) {
		try {
            Scanner sc = new Scanner(input);
			StringTokenizer st;
			String inputLine, fieldName, fieldType, fieldData;
			while (sc.hasNextLine()) {
                inputLine = sc.nextLine();
				st = new StringTokenizer(inputLine, ":+-#");
				fieldName = st.nextToken().replaceAll("\\s", "");
				fieldType = st.nextToken();
				//fieldData = st.nextToken();
                if (!st.hasMoreTokens())
                    fieldData = "";
                else
                    fieldData = st.nextToken();

                //System.out.println(fieldName + fieldType + fieldData + "POOTUS");
                if (fieldType.equals("Text"))
                    dataList.add(new DataSet<String>(fieldName, fieldData, true));
				else if (fieldType.equals("Checkbox"))
					dataList.add(new DataSet<Boolean>(fieldName, (fieldData.equals("No")?false:true), false));

			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Getter method for SSEF code, required as a unique identifier when uploading onto the database
	 * @return SSEF Code
	 */
	public String getSSEFCode() {
		return SSEFCode;
	}

}
