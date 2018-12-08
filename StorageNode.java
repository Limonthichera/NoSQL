import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class StorageNode {
	private int maxCapacity;
	private Deque<DbEntity> mem;
	
	public StorageNode(int maxCapacity) {
		this.maxCapacity = maxCapacity;
		mem = new ArrayDeque<DbEntity>();
	}
	
	public boolean addEntity(DbEntity entity) {
		if (mem.size() == maxCapacity) return false;
		mem.push(new DbEntity(entity));
		return true;
	}
	
	public int getSize() {
		return mem.size();
	}
	
	public boolean removeEntity(String entityType, Object primaryKey) {
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
	
	public DbEntity getEntity(String entityType, Object primaryKey) {
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
	
}
