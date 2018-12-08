import java.util.ArrayList;

public class Main {
	public static void main(String argvp[]) {
		StorageNode node = new StorageNode(5);
		ArrayList<String> types = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Object> vals = null;
		types.add("String");
		types.add("Integer");
		types.add("Float");
		names.add("Primary Key");
		names.add("An integer");
		names.add("A floating point");
		
		DbEntityTemplate LOLE = new DbEntityTemplate("LOLE", types, names);
		
		vals = new ArrayList<Object>();
		vals.add("Abracadabrisme");
		vals.add(5);
		vals.add(5.7);
		DbEntity entity = new DbEntity(LOLE, vals);

		vals = new ArrayList<Object>();
		vals.add("Alta cheie");
		vals.add(12);
		vals.add(69.69);
		DbEntity newEntity = new DbEntity(LOLE, vals);
		
		ArrayList<String> attrNames = new ArrayList<String>();
		ArrayList<Object> attrValues = new ArrayList<Object>();
		attrNames.add("An integer");
		attrValues.add(18);
		entity.updateAttributes(attrNames, attrValues);

		System.out.println(entity);
		System.out.println(newEntity);
		
		System.out.println("Testing nodes...");
		node.addEntity(entity);
		node.addEntity(newEntity);
		System.out.println(node);
	}
}
