import java.util.ArrayList;
import java.util.Iterator;

/**
 * Template for entity type
 * @author Teodor
 *
 */
public class DbEntityTemplate {
	private String type;
	private int replicatingFactor;
	private ArrayList<String> attributeTypes;
	private ArrayList<String> attributeNames;
	
	/**
	 * Creates template
	 * @param type Builds template for entities of this type
	 * @param attributeTypes ArrayList of attribute types for the entity
	 * @param attributeNames ArrayList of attribute names for the entity
	 */
	public DbEntityTemplate(String type, int RF, ArrayList<String> attributeTypes, ArrayList<String> attributeNames) {
		this.type = type;
		replicatingFactor = RF;
		
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
	
	/**
	 * Fetches entity attribute names
	 * @return ArrayList of all attribute names for entities of this type
	 */
	public ArrayList<String> getNameList() {
		ArrayList<String> attrNames = new ArrayList<String>();
		Iterator<String> nameIterator = attributeNames.iterator();
		while (nameIterator.hasNext()) {
			attrNames.add(nameIterator.next());
		}
		return attrNames;
	}
	
	public Iterator<String> getNameIterator() {
		return attributeNames.iterator();
	}
	
	/**
	 * Fetches entity attribute types
	 * @return ArrayList of all attribute types for entities of this type
	 */
	public ArrayList<String> getTypeList() {
		ArrayList<String> attrTypes = new ArrayList<String>();
		Iterator<String> typeIterator = attributeTypes.iterator();
		while (typeIterator.hasNext()) {
			attrTypes.add(typeIterator.next());
		}
		return attrTypes;
	}
	
	public Iterator<String> getTypeIterator() {
		return attributeTypes.iterator();
	}
	
	public String getType() {
		return type;
	}
	
	public int getRF() {
		return replicatingFactor;
	}
	
	public String toString() {
		return "{type:" + type + ", RF:" + replicatingFactor + ", attrNames:" + attributeNames + ", attrTypes:" + attributeTypes + "}";
	}
}
