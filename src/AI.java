import java.util.ArrayList;

public class AI {

	public Move random(BoardState b, ArrayList<Piece> pieces, Game g) {
		
		ArrayList<Move> allMoves = new ArrayList<Move>();
		ArrayList<Move> capturingMoves = new ArrayList<Move>();
		
		for(int i = 0; i < pieces.size(); i++){
			
			ArrayList<Move> currentPiece = g.findLegalMoves(pieces.get(i), b);
			
			if(currentPiece.size() > 0) {
				
				currentPiece = g.findMultipleJumps(currentPiece, b);
			}
			
			for(int j = 0; j < currentPiece.size(); j++) {
				
				if(currentPiece.get(j).getCapture()) {
					
					capturingMoves.add(currentPiece.get(j));
				} else {
					
					allMoves.add(currentPiece.get(j));
				}
			}
			
		}
			
		if(capturingMoves.size() > 0) {
			
			double rand = Math.random();
			int index = (int) rand * (capturingMoves.size() - 1);
			
			double randJump = Math.random();
			int indexJump = (int) randJump * (capturingMoves.get(index).getMoves().size() - 1);
			
			return capturingMoves.get(index).getMoves().get(indexJump);
		}
		
		if(allMoves.size() > 0) {
			
			double randAll = Math.random();
			int indexAll = (int) randAll * (allMoves.size() - 1);
			
			return allMoves.get(indexAll);
		}
		
		return null;
	}
}
