import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Main Database Class
 * @author Teodor
 *
 */
public class Database {
	private String name;
	private int noNodes;
	private int nodeCapacity;
	private PriorityQueue<StorageNode> nodes;
	
	/**
	 * Creates a new database of given parameters
	 * @param name Database name
	 * @param noNodes Number of database nodes
	 * @param nodeCapacity Storage capacity for each node
	 */
	public Database(String name, int noNodes, int nodeCapacity) {
		this.name = name;
		this.noNodes = noNodes;
		this.nodeCapacity = nodeCapacity;
		nodes = new PriorityQueue<StorageNode>();
		for (int i = 0; i < noNodes; i++) {
			nodes.add(new StorageNode(nodeCapacity, i));
		}
	}
	
	/**
	 * Adds an entity to the database
	 * @param entity Entity instance to be added to the database
	 */
	public void add(DbEntity entity) {
		int i = 0;
		ArrayList<StorageNode> backupNodes = new ArrayList<StorageNode>();
		StorageNode cNode = null;
		while (i < entity.getRF()) {
			cNode = nodes.poll();
			if (cNode == null) {
				cNode = new StorageNode(nodeCapacity, noNodes);
				noNodes++;
			}
			if (cNode.addEntity(entity)) {
				i++;
			}
			backupNodes.add(cNode);
		}
		Iterator<StorageNode> nodeIterator = backupNodes.iterator();
		while (nodeIterator.hasNext()) {
			nodes.add(nodeIterator.next());
		}
	}
	
	/**
	 * Returns a JSON
	 * @return Object converted to JSON
	 */
	public String toString() {
		PriorityQueue<StorageNode> backupNodes = new PriorityQueue<StorageNode>();
		String returnString = "{name:" + name + ", noNodes:" + noNodes + ", nodeCapacity:" + nodeCapacity + ", nodes:[";
		
		StorageNode cNode = null;
		for (int i = 0; i < noNodes; i++) {
			cNode = nodes.poll();
			backupNodes.add(cNode);
			returnString += cNode.toString();
			if (i < noNodes - 1)
				returnString += ", ";
		}
		
		nodes = backupNodes;
		
		return returnString + "]}";
	}
	
	/**
	 * Prints the database to file in fixed template
	 * @param output PrintWriter - destination file
	 */
	public void snapshot(PrintWriter output) {
		StorageNode cNode = null;
		DbEntity cEntity = null;
		PriorityQueue<StorageNode> backupNodes = new PriorityQueue<StorageNode>();
		boolean emptydb = false;
		
		for (int i = 1; i <= noNodes; i++) {
			cNode = nodes.poll();
			backupNodes.add(cNode);
			
			if (cNode.getSize() == 0 && i == 1) emptydb = true;
			
			if (cNode.getSize() > 0) output.printf("Nod" + i + "\n");
			
			Iterator<DbEntity> nodeIterator = cNode.getMemIterator();
			while (nodeIterator.hasNext()) {
				cEntity = nodeIterator.next();
				output.printf(cEntity.getType());
				Iterator<String> nameIterator = cEntity.getAttributeNameIterator();
				Iterator<Object> valIterator = cEntity.getAttributeIterator();
				Iterator<String> typeIterator = cEntity.getAttributeTypeIterator();
				while (valIterator.hasNext()) {
					Object curVal = valIterator.next();
					if (typeIterator.next().equals("Float")) {
						float fl = (float) curVal;
						if (fl == (int)fl) curVal = (int)fl;
					}
					output.printf(" " + nameIterator.next() + ":" + curVal);
				}
				output.printf("\n");
			}
		}
		if (emptydb) {
			output.printf("EMPTY DB\n");
		}
		nodes = backupNodes;
	}
	
	/**
	 * Deletes element from database
	 * @param template Entity template
	 * @param primaryKey Entity Primary Key
	 * @return Empty string if successful delete, "NO INSTANCE TO DELETE\n" if no matching elements
	 */
	public String delete(DbEntityTemplate template, String primaryKey) {
		String returnString = "NO INSTANCE TO DELETE\n";
		
		PriorityQueue<StorageNode> backupNodes = new PriorityQueue<StorageNode>();
		
		StorageNode cNode = null;
		for (int i = 0; i < noNodes; i++) {
			cNode = nodes.poll();
			backupNodes.add(cNode);
			if (cNode.removeEntity(template.getType(), primaryKey)) {
				returnString = "";
			}
		}
		
		nodes = backupNodes;
		
		return returnString;
	}
	
	/**
	 * Fetches Entity from database
	 * @param template Entity template
	 * @param primaryKey Entity Primary Key
	 * @return String with Entity information or "NO INSTANCE FOUND\n" otherwise
	 */
	public String get(DbEntityTemplate template, String primaryKey) {
		String returnString = "";
		
		PriorityQueue<StorageNode> backupNodes = new PriorityQueue<StorageNode>();
		DbEntity cEntity = null;
		StorageNode cNode = null;
		for (int i = 0; i < noNodes; i++) {
			cNode = nodes.poll();
			backupNodes.add(cNode);
			if (cNode.getEntity(template.getType(), primaryKey) != null) {
				returnString += "Nod" + (i + 1) + " ";
				cEntity = cNode.getEntity(template.getType(), primaryKey);
			}
		}
		
		nodes = backupNodes;
		
		if (cEntity == null) return "NO INSTANCE FOUND\n";
		
		returnString += cEntity.getType();

		Iterator<String> nameIterator = cEntity.getAttributeNameIterator();
		Iterator<Object> valIterator = cEntity.getAttributeIterator();
		Iterator<String> typeIterator = cEntity.getAttributeTypeIterator();
		while (valIterator.hasNext()) {
			Object curVal = valIterator.next();
			if (typeIterator.next().equals("Float")) {
				float fl = (float) curVal;
				if (fl == (int)fl) curVal = (int)fl;
			}
			returnString += " " + nameIterator.next() + ":" + curVal;
		}
		
		return returnString + "\n";
	}
	
	/**
	 * Updates Entity in database; will update the Timestamp
	 * @param template Entity template
	 * @param primaryKey Entity Primary Key
	 * @param attrNames ArrayList of attribute names to be updated
	 * @param attrValues ArrayList of new attribute values (String)
	 */
	public void update(DbEntityTemplate template, String primaryKey, ArrayList<String> attrNames, ArrayList<String> attrValues) {
		PriorityQueue<StorageNode> backupNodes = new PriorityQueue<StorageNode>();
		StorageNode cNode = null;
		ArrayList<Object> attrVals;
		for (int i = 0; i < noNodes; i++) {
			cNode = nodes.poll();
			backupNodes.add(cNode);
			if (cNode.getEntity(template.getType(), primaryKey) != null) {
				attrVals = getObjectValues(template, attrNames, attrValues);
				DbEntity cEntity = cNode.getEntity(template.getType(), primaryKey);
				cNode.removeEntity(template.getType(), primaryKey);
				cEntity.updateAttributes(attrNames, attrVals);
				cNode.addEntity(cEntity);
			}
		}
		
		nodes = backupNodes;
	}
	
	/**
	 * Converts a String ArrayList of values to an Object ArrayList of values based on the corresponding types in the Entity template
	 * @param template Entity Template used for Value conversion
	 * @param attrNames ArrayList of desired attribute names
	 * @param attrValues ArrayList of given attribute values (String)
	 * @return ArrayList of given attribute values (Object)
	 */
	private ArrayList<Object> getObjectValues(DbEntityTemplate template, ArrayList<String> attrNames, ArrayList<String> attrValues) {
		Iterator<String> nameIterator = attrNames.iterator();
		Iterator<String> valueIterator = attrValues.iterator();
		ArrayList<Object> attrVals = new ArrayList<Object>();
		
		String cName = "";
		String cValue = "";
		String cType = "";
		while (nameIterator.hasNext()) {
			cName = nameIterator.next();
			cValue = valueIterator.next();
			
			Iterator<String> templateNameIterator = template.getNameIterator();
			Iterator<String> templateTypeIterator = template.getTypeIterator();
			while (templateNameIterator.hasNext()) {
				cType = templateTypeIterator.next();
				if (templateNameIterator.next().equals(cName)) {
					if (cType.equals("Integer")) attrVals.add(Integer.parseInt(cValue));
        			if (cType.equals("Float")) attrVals.add(Float.parseFloat(cValue));
        			if (cType.equals("String")) attrVals.add(cValue);
        			break;
				}
			}
		}
		return attrVals;
	}
	
}
