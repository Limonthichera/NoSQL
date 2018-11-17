import java.util.ArrayList;
import java.util.Iterator;

public class DbEntity {
	private String entityType;
	private ArrayList<String> attributeTypes;
	private ArrayList<String> attributeNames;
	private ArrayList<Object> attributeValues;
	
	public DbEntity(DbEntityTemplate entityTemplate, ArrayList<Object> attributeValues) {
		this.entityType = entityTemplate.getType();
		this.attributeTypes = new ArrayList<String>();
		this.attributeNames = new ArrayList<String>();
		this.attributeValues = new ArrayList<Object>();

		Iterator<Object> valueIterator = attributeValues.iterator();
		while (valueIterator.hasNext()) {
			this.attributeValues.add(valueIterator.next());
		}
		
		Iterator<String> typeIterator = entityTemplate.getTypeList().iterator();
		while (typeIterator.hasNext()) {
			attributeTypes.add(typeIterator.next());
		}
		
		Iterator<String> nameIterator = entityTemplate.getNameList().iterator();
		while (nameIterator.hasNext()) {
			attributeNames.add(nameIterator.next());
		}
	}
	
	public DbEntity(DbEntity entity) {
		this.entityType = entity.entityType;
		attributeTypes = new ArrayList<String>();
		attributeNames = new ArrayList<String>();
		attributeValues = new ArrayList<Object>();
		
		Iterator<String> typeIterator = entity.attributeTypes.iterator();
		while (typeIterator.hasNext()) {
			attributeTypes.add(typeIterator.next());
		}
		
		Iterator<String> nameIterator = entity.attributeNames.iterator();
		while (nameIterator.hasNext()) {
			attributeNames.add(nameIterator.next());
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
	
	public Object getPrimaryKey() {
		return attributeValues.get(0);
	}
	
	public ArrayList<Object> getAttributes() {
		ArrayList<Object> attrVals = new ArrayList<Object>();
		Iterator<Object> valueIterator = attributeValues.iterator();
		while (valueIterator.hasNext()) {
			attrVals.add(valueIterator.next());
		}
		return attrVals;
	}
	
	public void updateAttributes(ArrayList<String> attrNames, ArrayList<Object> attrValues) {
		Iterator<String> nameIterator = attributeNames.iterator();
		String currentAttrName;
		int currentIndex = 0;
		while (nameIterator.hasNext()) {
			// Go through all names in current entity, compare to names received as parameter
			currentAttrName = nameIterator.next();
			if (attrNames.contains(currentAttrName)) {
				// Find exactly the index where current name is found, update and remove from parameter array
				for (int i = 0; i <= attrNames.size(); i++) {
					if (attrNames.get(i).equals(currentAttrName)) {
						attributeValues.set(currentIndex, attrValues.get(i));
						attrNames.remove(i);
						attrValues.remove(i);
						break;
					}
				}
			}
			currentIndex++;
		}
	}
}
