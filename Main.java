import java.util.ArrayList;

public class Main {
	public static void main(String argvp[]) {
		StorageNode node = new StorageNode(5);
		ArrayList<String> types = new ArrayList<String>();
		ArrayList<Object> vals = new ArrayList<Object>();
		types.add("String");
		types.add("Integer");
		types.add("Float");
		vals.add("Abracadabrisme");
		vals.add(5);
		vals.add(5.7);
		
		DbEntity entity = new DbEntity("LOLE", types, vals);
		DbEntity newEntity = new DbEntity(entity);
		
		System.out.println(entity.getType());
		System.out.println(newEntity.getAttributes());
		System.out.println(entity.getAttributes());
		System.out.println(newEntity.getAttributes());
		
		
		
	}
}
