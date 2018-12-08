import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Storage and interaction with entity instance
 * @author Teodor
 *
 */
public class DbEntity {
	private String entityType;
	private int replicatingFactor;
	private Timestamp timestamp;
	private ArrayList<String> attributeTypes;
	private ArrayList<String> attributeNames;
	private ArrayList<Object> attributeValues;
	
	/**
	 * Creates entity instance. Will remember all template information, so it will continue to function
	 * even if template has been destroyed
	 * @param entityTemplate Template that will be used to instantiate entity
	 * @param attributeValues ArrayList of attribute values; Must match attributes in <b>enityTemplate</b>
	 */
	public DbEntity(DbEntityTemplate entityTemplate, ArrayList<Object> attributeValues) {
		timestamp = new Timestamp(System.currentTimeMillis());
		replicatingFactor = entityTemplate.getRF();
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
	
	/**
	 * Creates a perfect copy of an entity instance
	 * @param entity Source entity instance
	 */
	public DbEntity(DbEntity entity) {
		this.entityType = entity.entityType;
		timestamp = entity.getTimestamp();
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
	
	/**
	 * Checks if entity instance matches given type and primary key
	 * @param entityType Type to compare to
	 * @param primaryKey Key to compare to
	 * @return true (Entity is of given type and has given primary key) or false (otherwise)
	 */
	public boolean equals(String entityType, String primaryKey) {
		try {
			if (!entityType.equals(this.entityType)) return false;
			if (attributeTypes.get(0).equals("Integer"))
				if ((int)(attributeValues.get(0)) == Integer.parseInt(primaryKey)) return true;
			if (attributeTypes.get(0).equals("Float"))
				if ((float)(attributeValues.get(0)) == Float.parseFloat(primaryKey)) return true;
			if (attributeTypes.get(0).equals("String"))
				if (((String)(attributeValues.get(0))).equals((String)primaryKey)) return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Fetches entity instance type<br/>Notice: It can't be altered
	 * @return Entity type
	 */
	public String getType() {
		return entityType;
	}
	
	/**
	 * Fetches entity instance Primary Key<br/>Notice: It can't be altered
	 * @return Entity Primary Key
	 */
	public Object getPrimaryKey() {
		return attributeValues.get(0);
	}
	
	/**
	 * Fetches entity timestamp (in ms)
	 * @return Entity timestamp (ms)
	 */
	public long getTime() {
		return timestamp.getTime();
	}
	
	private Timestamp getTimestamp() {
		return timestamp;
	}
	
	protected Iterator<Object> getAttributeIterator() {
		return attributeValues.iterator();
	}
	
	protected Iterator<String> getAttributeNameIterator() {
		return attributeNames.iterator();
	}
	
	protected Iterator<String> getAttributeTypeIterator() {
		return attributeTypes.iterator();
	}
	
	public Timestamp updateTimestamp() {
		timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	}
	
	/**
	 * Updates attributes with names in attrNames to new values in attrValues
	 * @param attrNames ArrayList of names for desired updates
	 * @param attrValues ArrayList of new values
	 */
	public void updateAttributes(ArrayList<String> attrNames, ArrayList<Object> attrValues) {
		updateTimestamp();
		Iterator<String> nameIterator = attributeNames.iterator();
		String currentAttrName;
		int currentIndex = 0;
		while (nameIterator.hasNext()) {
			// Go through all names in current entity, compare to names received as parameter
			currentAttrName = nameIterator.next();
			if (attrNames.contains(currentAttrName)) {
				// Find exactly the index where current name is found, update and remove from parameter array
				for (int i = 0; i < attrNames.size(); i++) {
					if (attrNames.get(i).equals(currentAttrName)) {
						attributeValues.set(currentIndex, attrValues.get(i));
						//attrNames.remove(i);
						//attrValues.remove(i);
						break;
					}
				}
			}
			currentIndex++;
		}
	}
	
	/**
	 * @return Entity instance information in JSON format
	 */
	@Override
	public String toString() {
		String returnString = "{type:" + entityType + ", timestamp:" + timestamp + ", RF:" + replicatingFactor + ", attributes:{";
		
		Iterator<Object> valueIterator = attributeValues.iterator();
		Iterator<String> nameIterator = attributeNames.iterator();
		while (valueIterator.hasNext()) {
			returnString += nameIterator.next() + ":" + valueIterator.next();
			if (valueIterator.hasNext())
				returnString += ", ";
		}
		return returnString + "}}";
	}
	
	public int getRF() {
		return replicatingFactor;
	}
}
