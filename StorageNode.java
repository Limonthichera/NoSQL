import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Entity Storage Nodes
 * @author Teodor
 *
 */
public class StorageNode implements Comparable<StorageNode>{
	private int maxCapacity;
	private int id;
	private Deque<DbEntity> mem;
	
	/**
	 * Creates a new Storage Node with given capacity and id
	 * @param maxCapacity Entity capacity
	 * @param id Storage node id in database
	 */
	public StorageNode(int maxCapacity, int id) {
		this.id = id;
		this.maxCapacity = maxCapacity;
		mem = new ArrayDeque<DbEntity>();
	}
	
	/**
	 * Stores entity
	 * @param entity Entity to be stored
	 * @return true if successfully stored, false if failed (Capacity capped)
	 */
	public boolean addEntity(DbEntity entity) {
		if (mem.size() == maxCapacity) return false;
		mem.push(new DbEntity(entity));
		return true;
	}
	
	/**
	 * Gets number of entities stored in node
	 * @return Number of entities stored
	 */
	public int getSize() {
		return mem.size();
	}
	
	/**
	 * Removes entity of given Type and Primary Key
	 * @param entityType Entity Type
	 * @param primaryKey ENtity Primary Key
	 * @return True if successfully removed, false otherwise (entity did not exist)
	 */
	public boolean removeEntity(String entityType, String primaryKey) {
		Iterator<DbEntity> entityIterator = mem.iterator();
		DbEntity entity = null;
		while (entityIterator.hasNext()) {
			entity = entityIterator.next();
			if (entity.equals(entityType, primaryKey)) {
				entityIterator.remove();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets entity of given Type and Primary Key
	 * @param entityType Entity Type
	 * @param primaryKey ENtity Primary Key
	 * @return Entity instance if found, null otherwise (entity does not exist)
	 */
	public DbEntity getEntity(String entityType, String primaryKey) {
		Iterator<DbEntity> entityIterator = mem.iterator();
		DbEntity entity = null;
		while (entityIterator.hasNext()) {
			entity = entityIterator.next();
			if (entity.equals(entityType, primaryKey)) {
				return entity;
			}
		}
		return null;
	}
	
	/**
	 * @return All Node information in JSON format
	 */
	public String toString() {
		String returnString = "{maxCapacity:" + maxCapacity + ", entities:[";
		Iterator<DbEntity> entityIterator = mem.iterator();
		while (entityIterator.hasNext()) {
			returnString += entityIterator.next().toString();
			if (entityIterator.hasNext())
				returnString += ", ";
		}
		return returnString + "]}";
	}
	
	/**
	 * Get an iterator through all the Entities stored in node
	 * @return Iterator through all the Entities stored in node
	 */
	public Iterator<DbEntity> getMemIterator() {
		return mem.iterator();
	}

	/**
	 * Overrides the compareTo method to order Nodes in reverse order based on number of Entities stored
	 * and Node ID
	 */
	@Override
	public int compareTo(StorageNode obj) {
		if (mem.size() > obj.mem.size()) return -1;
		if (mem.size() < obj.mem.size()) return 1;
		if (id > obj.id) return -1;
		if (id < obj.id) return 1;
		return 0;
	}
	
	
}
