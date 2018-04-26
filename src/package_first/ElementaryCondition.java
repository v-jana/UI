package package_first;

import java.util.ArrayList;

public class ElementaryCondition {
	
	private ArrayList<ArrayList<Connection>> elemConnections;
	private String elemCondition;
	private char type;
	
	public ElementaryCondition(String elemString){
		this.elemCondition = elemString;
		elemConnections = new ArrayList<ArrayList<Connection>>();
	}
	
	//d == priama, s == specialna, v == s pemennymi 
	public void setType(){
		if(elemCondition.contains(">")){
			this.type = 's';
		} else if(elemCondition.contains("?")){
			this.type = 'v';
		} else {
			this.type = 'd';
		}
	}
	
	public char getType(){
		return type;
	}
	
	public String getString(){
		return elemCondition;
	}
	
	public void addCollection(ArrayList<Connection> posibleConnection){
		this.elemConnections.add(posibleConnection);
	}
	
	public ArrayList<ArrayList<Connection>> getConnections(){
		return elemConnections;
	}
	
	public void print(){
		
		int i, j;
		
		for (i = 0; i < elemConnections.size(); i++){
			
			for(j = 0; j < elemConnections.get(i).size(); j++){
				System.out.println(elemConnections.get(i).get(j).getVariable()+ " == " +elemConnections.get(i).get(j).getValue());
			}
			System.out.println("************");
		}
	}

}
