import java.util.ArrayList;

public class Main {
	public static void main(String argvp[]) {
		StorageNode node = new StorageNode(5);
		ArrayList<String> types = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Object> vals = new ArrayList<Object>();
		types.add("String");
		types.add("Integer");
		types.add("Float");
		vals.add("Abracadabrisme");
		vals.add(5);
		vals.add(5.7);
		names.add("Primary Key");
		names.add("An integer");
		names.add("A floating point");
		
		DbEntityTemplate LOLE = new DbEntityTemplate("LOLE", types, names);
		DbEntity entity = new DbEntity(LOLE, vals);
		DbEntity newEntity = new DbEntity(entity);
		
		ArrayList<String> attrNames = new ArrayList<String>();
		ArrayList<Object> attrValues = new ArrayList<Object>();
		attrNames.add("An integer");
		attrValues.add(18);
		entity.updateAttributes(attrNames, attrValues);

		System.out.println(entity);
		System.out.println(newEntity);
	}
}
