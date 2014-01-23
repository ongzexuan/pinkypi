
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
