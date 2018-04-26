package package_first;

import java.util.ArrayList;


public class Action {
	
	private String string;
	
	public Action(String string){
		this.string = string;
	}
	
	
	public ArrayList<String> getFinalActions(ArrayList<ArrayList<Connection>> connections) {
		ArrayList<String> result = new ArrayList<>();
		
		for (int i = 0; i < connections.size(); i++) {
			String finalAction = string;
			for (int j = 0; j < connections.get(i).size(); j++) {
				finalAction = finalAction.replace("?"+connections.get(i).get(j).getVariable(), connections.get(i).get(j).getValue());
			}
			result.add(finalAction);
		}
		
		return result;
	}

}
