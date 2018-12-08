import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
	public static void main(String argv[]) {
		String inFileName = argv[0];
		String command;
		ArrayList<DbEntityTemplate> templates = new ArrayList<DbEntityTemplate>();
		ArrayList<String> attrTypes;
		ArrayList<String> attrNames;
		ArrayList<Object> attrVals;
		int noAttributes;
		
		try {
			FileReader inFile = new  FileReader(inFileName);
	        BufferedReader inStream = new BufferedReader(inFile);
	        
	        FileWriter outFile = new FileWriter(inFileName + "_out");
	        PrintWriter output = new PrintWriter(outFile);
	        
	        String line = inStream.readLine();
	        System.out.println("Reading: " + line);
	        String[] words = line.split("[\\s]+");
	        Database database = new Database(words[1], Integer.parseInt(words[2]), Integer.parseInt(words[3]));
	        
	        line = inStream.readLine();
	        while (line != null) {
	        	System.out.println("Reading: " + line);
	        	if (line.isEmpty()) {
	        		continue;
	        	}
	        	words = line.split("[\\s]+");
	        	
	        	command = words[0];
	        	switch (command) {
	        	case "CREATEDB":
	        		database = new Database(words[1], Integer.parseInt(words[2]), Integer.parseInt(words[3]));
	        		break;
	        	case "CREATE":
	        		attrTypes = new ArrayList<String>();
	        		attrNames = new ArrayList<String>();
	        		noAttributes = Integer.parseInt(words[3]);
	        		for (int i = 0; i < noAttributes; i++) {
	        			attrNames.add(words[4 + i * 2]);
	        			attrTypes.add(words[5 + i * 2]);
	        		}
	        		templates.add(new DbEntityTemplate(words[1], Integer.parseInt(words[2]), attrTypes, attrNames));
	        		break;
	        	case "INSERT":
	        		attrVals = new ArrayList<Object>();
	        		Iterator<DbEntityTemplate> templatesIterator = templates.iterator();
	        		DbEntityTemplate currentTemplate = null;
	        		while (templatesIterator.hasNext()) {
	        			currentTemplate = templatesIterator.next();
	        			if (currentTemplate.getType().equals(words[1])) {
	        				break;
	        			} else {
	        				currentTemplate = null;
	        			}
	        		}
	        		if (currentTemplate == null) {
	        			System.out.println("!--No such Entity Template");
	        			break;
	        		}
	        		ArrayList<String> templateTypes = currentTemplate.getTypeList();
	        		for (int i = 0; i < templateTypes.size(); i++) {
	        			if (templateTypes.get(i).equals("Integer")) attrVals.add(Integer.parseInt(words[i+2]));
	        			if (templateTypes.get(i).equals("Float")) attrVals.add(Float.parseFloat(words[i+2]));
	        			if (templateTypes.get(i).equals("String")) attrVals.add(words[i+2]);
	        		}
	        		database.add(new DbEntity(currentTemplate, attrVals));
	        		break;
	        	case "SNAPSHOTDB":
	        		System.out.println(database);
	        		database.snapshot(output);
	        		break;
	        	default:
	        		break;
	        	}
	        	
	        	line = inStream.readLine();
	        }
	        
	        inStream.close();
	        output.close();
		} catch(IOException e) {
			return;
		}
		
		
	}
}
