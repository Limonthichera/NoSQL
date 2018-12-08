import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class StorageNode implements Comparable<StorageNode>{
	private int maxCapacity;
	private int id;
	private Deque<DbEntity> mem;
	
	public StorageNode(int maxCapacity, int id) {
		this.id = id;
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
	
	protected Iterator<DbEntity> getMemIterator() {
		return mem.iterator();
	}

	@Override
	public int compareTo(StorageNode obj) {
		if (mem.size() > obj.mem.size()) return -1;
		if (mem.size() < obj.mem.size()) return 1;
		if (id > obj.id) return -1;
		if (id < obj.id) return 1;
		return 0;
	}
	
	
}
