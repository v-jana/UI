package package_first;

public class Connection {
	
	private char variable;
	private String value;
	
	public Connection(char variable, String value){
		this.variable = variable;
		this.value = value;
	}
	
	public char getVariable(){
		return variable; 
	}
	
	public String getValue(){
		return value; 
	}

}
