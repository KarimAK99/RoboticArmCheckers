package gamesimulation;
import java.util.ArrayList;

public class Tree {

	public Tree(Node root, int player) {
		
		this.root = root;
		depth = 0;
		allNodes = new ArrayList<Node>();
		allNodes.add(root);
		this.player = player;
		
	}
	
	private Node root;
	private int depth;
	private ArrayList<Node> allNodes;
	private int player;
	
	public void increaseDepth() {
		
		for(int i = 0; i < allNodes.size(); i++) {
			
			if(allNodes.get(i).getDepth() == depth && !allNodes.get(i).isEnd()) {
				
				expandChildren(allNodes.get(i));
			}
		}
		
		depth++;
	}
	
	public void expandChildren(Node parent) {
		
		ArrayList<Node> kiddies = parent.giveChildren(parent, player);
		
		for(int i = 0; i < kiddies.size(); i++) {
			
			parent.getChildren().add(kiddies.get(i));
			allNodes.add(kiddies.get(i));
			
		}
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public ArrayList<Node> getAllNodes() {
		return allNodes;
	}

	public void setAllNodes(ArrayList<Node> allNodes) {
		this.allNodes = allNodes;
	}
	
	
}
