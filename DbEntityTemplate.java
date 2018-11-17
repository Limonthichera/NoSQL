import java.util.ArrayList;
import java.util.Iterator;

public class DbEntityTemplate {
	private String type;
	private ArrayList<String> attributeTypes;
	private ArrayList<String> attributeNames;
	
	public DbEntityTemplate(String type, ArrayList<String> attributeTypes, ArrayList<String> attributeNames) {
		this.type = type;
		
		this.attributeTypes = new ArrayList<String>();
		Iterator<String> typeIterator = attributeTypes.iterator();
		while (typeIterator.hasNext()) {
			this.attributeTypes.add(typeIterator.next());
		}
		
		this.attributeNames = new ArrayList<String>();
		Iterator<String> nameIterator = attributeNames.iterator();
		while (nameIterator.hasNext()) {
			this.attributeNames.add(nameIterator.next());
		}
	}
	
	public ArrayList<String> getNameList() {
		ArrayList<String> attrNames = new ArrayList<String>();
		Iterator<String> nameIterator = attributeNames.iterator();
		while (nameIterator.hasNext()) {
			attrNames.add(nameIterator.next());
		}
		return attrNames;
	}
	
	public ArrayList<String> getTypeList() {
		ArrayList<String> attrTypes = new ArrayList<String>();
		Iterator<String> typeIterator = attributeTypes.iterator();
		while (typeIterator.hasNext()) {
			attrTypes.add(typeIterator.next());
		}
		return attrTypes;
	}
	
	public String getType() {
		return type;
	}
}