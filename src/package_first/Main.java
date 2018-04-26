package package_first;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	FileInputStream facts;
	FileInputStream rules;
	Scanner factsScanner;
	Scanner ruleScanner;
	private static ArrayList<Rule> rulesList = new ArrayList<>();
	private static ArrayList<String> factsList = new ArrayList<>();

	public Main() {

	}

	public void scanFacts() {
		Rule rule = null;

		try {
			facts = new FileInputStream(new File("fakty.txt"));
			rules = new FileInputStream(new File("pravidla.txt"));

			factsScanner = new Scanner(facts);
			ruleScanner = new Scanner(rules);

			while (factsScanner.hasNextLine()) {
				String fact = factsScanner.nextLine();
				factsList.add(fact);
			}

			while (ruleScanner.hasNextLine()) {

				String line = ruleScanner.nextLine();
				if (line.startsWith("Meno:")) {
					rule = new Rule(line.substring(6));
					continue;
				} else if (line.startsWith("AK")) {
					line = line.substring(6);
					if (line.contains(",")) {
						String[] conditions = line.split(",");
						for (int i = 0; i < conditions.length; i++) {
							ElementaryCondition condition = new ElementaryCondition(conditions[i]);
							condition.setType();
							rule.addCondition(condition);
						}
					}
					continue;
				} else if (line.startsWith("POTOM")) {
					line = line.substring(6);
					if (line.contains(",")) {
						String[] actions = line.split(",");
						for (int i = 0; i < actions.length; i++) {
							Action action = new Action(actions[i]);
							rule.addAction(action);

						}
					} else {
						Action action = new Action(line);
						rule.addAction(action);
					}
					rulesList.add(rule);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createPosibleConnections(ElementaryCondition cond) {
		String[] splitFact;
		String[] splitCond = cond.getString().split(" ");
		int i, j;
		ArrayList<Connection> posibleConn;

		for (i = 0; i < factsList.size(); i++) {

			splitFact = factsList.get(i).split(" ");

			if (splitCond.length != splitFact.length) {
				continue;
			} else {
				posibleConn = new ArrayList<Connection>();
				for (j = 0; j < splitCond.length; j++) {

					if (splitCond[j].startsWith("?")) {
						Connection conection = new Connection(splitCond[j].charAt(1), splitFact[j]);
						posibleConn.add(conection);
					} else if (!splitCond[j].equals(splitFact[j])) {
						break;
					}
				}
				if (j == splitCond.length && (!posibleConn.isEmpty())) {
					cond.addCollection(posibleConn);
				}
			}
		}
	}

	public static void main(String[] args) {

		Main main;
		main = new Main();
		main.scanFacts();
		ArrayList<ElementaryCondition> conditions;
		char type;
		int i, j;
		boolean newFacts = true;

		// createPosibleConnections(rulesList.get(0).getConditions().get(0));
		// for each Rule
		//while (newFacts) {

			newFacts = false;

			for (i = 0; i < rulesList.size(); i++) {

				conditions = rulesList.get(i).getConditions();

				// for each condition of Rule (except special conditions)
				for (j = 0; j < conditions.size(); j++) {
					type = conditions.get(j).getType();

					if (type == 'v') {
						createPosibleConnections(conditions.get(j));
					}
				}

				rulesList.get(i).combine();
				rulesList.get(i).filterRecords3();
				
				for (j = 0; j < rulesList.get(i).getConditions().size(); j++) {
					type = rulesList.get(i).getConditions().get(j).getType();

					if (type == 's') {

						rulesList.get(i).filterSpecial(rulesList.get(i).getConditions().get(j).getString());

					}
				}
				// pre konkretne pravidlo ziskame zoznam akcii, finalActions
				// je zoznam "upravenych" pravidiel, s nahradenymi
				// premennymi
				if (!rulesList.get(i).getFinalConn().isEmpty()) {
					ArrayList<Action> actionList = rulesList.get(i).getActions();
					ArrayList<String> finalActions = new ArrayList<>();
					
					for (int k = 0; k < actionList.size(); k++) {

						// zoznam vsetkych akcii s dosadenymi hodnotami
						finalActions.addAll(actionList.get(k).getFinalActions(rulesList.get(i).getFinalConn()));
					}

					for (int k = 0; k < finalActions.size(); k++) {
						if (finalActions.get(k).startsWith("pridaj ")) {
							String newFact = finalActions.get(k).substring(7);
							// ak sa novy fakt nachadza vo faktoch
							boolean exists = false;
							for (int l = 0; l < factsList.size(); l++) {
								if (factsList.get(l).equals(newFact)) {
									exists = true;
									break;
								}
							}
							if (!exists) {
								factsList.add(newFact);
								newFacts = true;

							}
						} else if (finalActions.get(k).startsWith("vymaz ")) {
							String oldFact = finalActions.get(k).substring(6);

							for (int l = 0; l < factsList.size(); l++) {
								if (factsList.get(l).equals(oldFact)) {
									factsList.remove(l);
									break;
								}
							}
						} else if (finalActions.get(k).startsWith("sprava ")) {
							 System.out.println(finalActions.get(k).substring(7));
						}

					}
				}
			}
			
		//}
		System.out.println("Facts:");
		for (int l = 0; l < factsList.size(); l++) {
			System.out.println(factsList.get(l));
		}

	} 

} 
