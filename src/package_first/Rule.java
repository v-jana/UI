package package_first;

import java.util.ArrayList;


public class Rule {

	private String name;
	private String stringCond;
	private ArrayList<ElementaryCondition> conditions;
	private ArrayList<Action> actions;
	private ArrayList<ArrayList<Connection>> finalConnections;

	public Rule(String name) {
		this.name = name;
		conditions = new ArrayList<>();
		actions = new ArrayList<>();
		finalConnections = new ArrayList<ArrayList<Connection>>();
	}

	public void addCondition(ElementaryCondition elemCond) {
		conditions.add(elemCond);
	}

	public void addAction(Action action) {
		actions.add(action);
	}

	public ArrayList<ElementaryCondition> getConditions() {
		return conditions;
	}

	public ArrayList<ArrayList<Connection>> getFinalConn() {
		return finalConnections;
	}

	public ArrayList<Action> getActions() {
		return actions;
	}

	public void combine() {
		int i, j = 0;
		int k = 0;
		finalConnections.clear();

		while (k < conditions.size() - 1) {

			ArrayList<ArrayList<Connection>> list1 = new ArrayList<>();
			ArrayList<ArrayList<Connection>> list2 = new ArrayList<>();

			if (!finalConnections.isEmpty()) {
				list1.addAll(finalConnections);
				finalConnections.clear();
			} else {
				list1.addAll(conditions.get(k).getConnections());
			}

			list2.addAll(conditions.get(k + 1).getConnections());
			
			if (!list1.isEmpty()) {
				for (j = 0; j < list1.size(); j++) {
					
					if(!list2.isEmpty()){
						for (i = 0; i < list2.size(); i++) {
							ArrayList<Connection> connection = new ArrayList<Connection>();
							connection.addAll(list1.get(j));
							connection.addAll(list2.get(i));
							finalConnections.add(connection);
						}
					}else{
						ArrayList<Connection> connection = new ArrayList<Connection>();
						connection.addAll(list1.get(j));
						finalConnections.add(connection);
					}
										
				}
			}
			k++;
		}
	}
	
	public void filterRecords3(){
		int i,j,k;
		int remove = 0;
		
		for(i = 0; i < finalConnections.size(); i++){
			remove = 0;
			for(j = 0; j < finalConnections.get(i).size(); j++){
				Connection conn1 = finalConnections.get(i).get(j);
				
				for(k = j+1; k < finalConnections.get(i).size(); k++){
					Connection conn2 = finalConnections.get(i).get(k);
					if(conn1.getVariable() != conn2.getVariable()){
						continue;
					}else{
						if(conn1.getValue().equals(conn2.getValue())){
							finalConnections.get(i).remove(k);
							j--;
							continue;
						}else{
							remove = 1;
							break;
						}
					}
				}	
				if(remove == 1){
					finalConnections.remove(i);
					i--;
					break;
				}
			}
			
		}
	}
	
	public void printPosibleConn() {
		int i, j;

		for (i = 0; i < finalConnections.size(); i++) {
			for (j = 0; j < finalConnections.get(i).size(); j++) {
				System.out.println(finalConnections.get(i).get(j).getVariable() + " == "
						+ finalConnections.get(i).get(j).getValue());
			}
			System.out.println("===========");
		}
	}

	public void filterSpecial(String special) {

		String[] splitString = special.split(" ");
		char a = splitString[1].charAt(1);
		char b = splitString[2].charAt(1);

		for (int k = 0; k < finalConnections.size(); k++) {

			String valueA = null;
			String valueB = null;

			for (Connection conn : finalConnections.get(k)) {

				if (conn.getVariable() == a) {
					valueA = conn.getValue();
				}
				if (conn.getVariable() == b) {
					valueB = conn.getValue();
				}
			}

			if (valueA.equals(valueB)) {
				finalConnections.remove(finalConnections.get(k));
				k--;
			}
		}
	}

}
