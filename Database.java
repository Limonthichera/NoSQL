import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Database {
	private String name;
	private int noNodes;
	private int nodeCapacity;
	private PriorityQueue<StorageNode> nodes;
	
	public Database(String name, int noNodes, int nodeCapacity) {
		this.name = name;
		this.noNodes = noNodes;
		this.nodeCapacity = nodeCapacity;
		nodes = new PriorityQueue<StorageNode>();
		for (int i = 0; i < noNodes; i++) {
			nodes.add(new StorageNode(nodeCapacity));
		}
	}
	
	public void add(DbEntity entity) {
		int i = 0;
		ArrayList<StorageNode> backupNodes = new ArrayList<StorageNode>();
		StorageNode cNode = null;
		while (i < entity.getRF()) {
			cNode = nodes.poll();
			if (cNode == null) {
				noNodes++;
				cNode = new StorageNode(nodeCapacity);
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
	
	public String toString() {
		ArrayList<StorageNode> backupNodes = new ArrayList<StorageNode>();
		String returnString = "{name:" + name + ", noNodes:" + noNodes + ", nodeCapacity:" + nodeCapacity + ", nodes:[";
		
		StorageNode cNode = null;
		for (int i = 0; i < noNodes; i++) {
			cNode = nodes.poll();
			backupNodes.add(cNode);
			returnString += cNode.toString();
			if (i < noNodes - 1)
				returnString += ", ";
		}
		
		Iterator<StorageNode> nodeIterator = backupNodes.iterator();
		while (nodeIterator.hasNext()) {
			nodes.add(nodeIterator.next());
		}
		
		return returnString + "]}";
	}
	
	public void snapshot(PrintWriter output) {
		StorageNode cNode = null;
		DbEntity cEntity = null;
		ArrayList<StorageNode> backupNodes = new ArrayList<StorageNode>();
		for (int i = 1; i <= noNodes; i++) {
			cNode = nodes.poll();
			backupNodes.add(cNode);
			
			if (cNode.getSize() == 0) continue;
			output.printf("Nod" + i + "\n");
			
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
		Iterator<StorageNode> nodeIterator = backupNodes.iterator();
		while (nodeIterator.hasNext()) {
			nodes.add(nodeIterator.next());
		}
	}
	
}
