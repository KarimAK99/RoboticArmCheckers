package gamesimulation;
import java.util.ArrayList;

public class minimaxAI {

	public minimaxAI(ArrayList<Tiles> AllTiles, ArrayList<Pieces> RobotPieces, ArrayList<Pieces> PlayerPieces, int player, int depth){
	
		Node root = new Node(null, new ArrayList<Node>(), AllTiles, RobotPieces, PlayerPieces, false, 0, 0, null);
		Tree tree = new Tree(root, player);
		
		for(int i = 0; i < depth; i++) {
			
			tree.increaseDepth();
		}
		
		this.tree = tree;
	}
	
	private Tree tree;
	
	public Move returnMove() {
		
		alphabeta(tree.getRoot(), 0, -100000000, 100000000, false);
		
		ArrayList<Node> captures = new ArrayList<Node>();
		ArrayList<Node> allMoves = new ArrayList<Node>();
		
		double max = -1000000;
		
		for(int i = 0; i < tree.getAllNodes().size(); i++) {
			
			
			if(tree.getAllNodes().get(i).getDepth() == 1) {
				

				//System.out.println(tree.getAllNodes().get(i).getMove().getStart() + " to " + tree.getAllNodes().get(i).getMove().getEnd() + " has a score of: " + tree.getAllNodes().get(i).getHeuristic());
				
				alphabeta(tree.getAllNodes().get(i), 1, -100000000, 100000000, true);
				
				if(tree.getAllNodes().get(i).getMove().isJump()) {
					
					captures.add(tree.getAllNodes().get(i));
				}
				
				allMoves.add(tree.getAllNodes().get(i));
			}
		}
		
		boolean found = false;
		
		for(int i = 0; i < captures.size(); i++) {
			
			if(captures.get(i).getHeuristic() >= max) {
				
				max = captures.get(i).getHeuristic();
				found = true;
			}
		}
		
		if(found) {
			
			ArrayList<Move> bestStuff = new ArrayList<Move>();
			
			for(int i = 0; i < captures.size(); i++) {
			
				if(captures.get(i).getHeuristic() == max) {
					
					bestStuff.add(captures.get(i).getMove());
				}
			}
			
			int index = (int) (Math.random() * bestStuff.size());
			tree = null;
			return bestStuff.get(index);
			
		} else {
			
			for(int i = 0; i < allMoves.size(); i++) {
				
				if(allMoves.get(i).getHeuristic() >= max) {
					
					max = allMoves.get(i).getHeuristic();
				}
			}
			
			ArrayList<Move> bestStuff = new ArrayList<Move>();
			
			for(int i = 0; i < allMoves.size(); i++) {
			
				if(allMoves.get(i).getHeuristic() == max) {
					
					bestStuff.add(allMoves.get(i).getMove());
				}
			}
			
			int index = (int) (Math.random() * bestStuff.size());
			tree = null;
			return bestStuff.get(index);
		}
		
	}
	
	
	private double alphabeta(Node node, int depth, double a, double b, boolean max){
		
		double value = 0;
		
		if(depth == 0 || node.isEnd()) {
			
			value = node.getHeuristic();
			return value;
		}
		
		if(max) {
			
			value = -100000000;
			
			for(int i = 0; i < node.getChildren().size(); i++) {
				
				value = Math.min(value, alphabeta(node.getChildren().get(i), depth + 1, a, b, false));
				a = Math.max(a, value);
				
				if(a>= b) {
					
					i = node.getChildren().size();
				}
				
				return value;
			}
		} else {
			
			value = 100000000;
			
			for(int i = 0; i < node.getChildren().size(); i++) {
				
				value = Math.min(value, alphabeta(node.getChildren().get(i), depth + 1, a, b, true));
				b = Math.min(b, value);
				
				if(a>= b) {
					
					i = node.getChildren().size();
				}
				
				return value;
			}
			
		}
		
		return value;
	}
}
