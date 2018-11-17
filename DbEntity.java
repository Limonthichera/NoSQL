import java.util.ArrayList;
import java.util.Iterator;

public class DbEntity {
	private String entityType;
	private ArrayList<String> attributeTypes;
	private ArrayList<Object> attributeValues;
	
	public DbEntity(String entityType, ArrayList<String> attributeTypes, ArrayList<Object> attributeValues) {
		this.entityType = entityType;
		this.attributeTypes = new ArrayList<String>();
		this.attributeValues = new ArrayList<Object>();
		
		Iterator<String> typeIterator = attributeTypes.iterator();
		while (typeIterator.hasNext()) {
			this.attributeTypes.add(typeIterator.next());
		}

		Iterator<Object> valueIterator = attributeValues.iterator();
		while (valueIterator.hasNext()) {
			this.attributeValues.add(valueIterator.next());
		}
	}
	
	public DbEntity(DbEntity entity) {
		this.entityType = entity.entityType;
		attributeTypes = new ArrayList<String>();
		attributeValues = new ArrayList<Object>();
		
		Iterator<String> typeIterator = entity.attributeTypes.iterator();
		while (typeIterator.hasNext()) {
			attributeTypes.add(typeIterator.next());
		}
		
		Iterator<Object> valueIterator = entity.attributeValues.iterator();
		while (valueIterator.hasNext()) {
			attributeValues.add(valueIterator.next());
		}
	}
	
	public boolean equals(String entityType, Object primaryKey) {
		if (!entityType.equals(this.entityType)) return false;
		if (attributeTypes.get(0).equals("Integer") 
				&& (int)(attributeValues.get(0)) == (int)primaryKey) return true;
		if (attributeTypes.get(0).equals("Float") 
				&& (float)(attributeValues.get(0)) == (float)primaryKey) return true;
		if (attributeTypes.get(0).equals("String") 
				&& ((String)(attributeValues.get(0))).equals((String)primaryKey)) return true;
		return false;
	}
	
	public String getType() {
		return entityType;
	}
	
	public ArrayList<Object> getAttributes() {
		ArrayList<Object> attrVals = new ArrayList<Object>();
		Iterator<Object> valueIterator = attributeValues.iterator();
		while (valueIterator.hasNext()) {
			attrVals.add(valueIterator.next());
		}
		return attrVals;
	}
}
