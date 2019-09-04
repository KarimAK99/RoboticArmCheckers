import java.util.ArrayList;



public class BoardState {
	
	public BoardState() {
		
		ArrayList<ArrayList<Piece>> newBoard = new ArrayList<ArrayList<Piece>>();
		board = newBoard;
		
	}

	private ArrayList<ArrayList<Piece>> board;
	
	public void resetBoard() {
		
		for(int i = 0; i < 8; i++) {
			
			board.add(new ArrayList<Piece>());
			
			for(int j = 0; j < 8; j++) {
				
				board.get(i).add(new Piece(i, j, "empty", false));
			}
		}
		
	}
	
	public void movePiece(Piece p, int x, int y) {
		
	    // legal move checks
		
		// king check 
		
		board.get(x).get(y).setColour(p.getColour());
		board.get(x).get(y).setKing(p.getKing());
	}

	public ArrayList<ArrayList<Piece>> getBoard() {
		return board;
	}

	public void setBoard(ArrayList<ArrayList<Piece>> board) {
		this.board = board;
	}
	
}
