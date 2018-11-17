import java.util.ArrayList;
import java.util.Iterator;

public class StorageNode {
	private int maxCapacity;
	private ArrayList<DbEntity> mem;
	
	public StorageNode(int maxCapacity) {
		this.maxCapacity = maxCapacity;
		mem = new ArrayList<DbEntity>();
	}
	
	public boolean addEntity(DbEntity entity) {
		if (mem.size() == maxCapacity) return false;
		mem.add(new DbEntity(entity));
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
	
	
}