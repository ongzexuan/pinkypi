import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;


public class FormData {
	String SSEFCode;
	LinkedList<DataSet> dataList;
	
	public FormData(String SSEFCode, File inputData) {
		this.SSEFCode = SSEFCode;
		readFromFile(inputData);
	}
	
	private void readFromFile(File input) {
		try {
			Scanner sc = new Scanner(input);
			StringTokenizer st;
			String inputLine, fieldName, fieldType, fieldData;
			while (sc.hasNextLine()) {
				inputLine = sc.nextLine();
				st = new StringTokenizer(inputLine, ":");
				fieldName = st.nextToken().replaceAll("\\s", "");
				fieldType = st.nextToken();
				fieldData = st.nextToken();
				
				if (fieldType.equals("Text"))
					dataList.add(new DataSet<String>(fieldName, fieldData, true));
				else if (fieldType.equals("Checkbox"))
					dataList.add(new DataSet<Boolean>(fieldName, (fieldData.equals("No")?false:true), false));
			}
			
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String getSSEFCode() {
		return SSEFCode;
	}
}
