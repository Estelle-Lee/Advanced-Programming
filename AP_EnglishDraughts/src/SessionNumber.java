/**
 * @author - Bokyung Lee (2431088L)
 * 
 * SessionNumber - enum for session number
 * */
public enum SessionNumber{
	
	myID(3);	
	private int value;
	
	SessionNumber(int value){
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
