
/**
 * Data structure designed to house the data of each field present in the SSEF form.
 * @param <T> Boolean if dealing with checkboxes, String if dealing with textfields
 */
public class DataSet<T> {
	private String fieldName;
	private T data;
	private boolean requiresQuotes;
	
	public DataSet(String fieldName, T data, boolean requiresQuotes) {
		this.fieldName = fieldName;
		this.data = data;
		this.requiresQuotes = requiresQuotes;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}
	
	public T getData() {
		return this.data;
	}
	
	public boolean getRequiresQuotes() {
		return this.requiresQuotes;
	}
}
