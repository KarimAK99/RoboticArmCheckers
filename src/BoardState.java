import java.util.ArrayList;



public class BoardState {

	private ArrayList<ArrayList<Piece>> board;
	private ArrayList<Piece> blackPieces;
	private ArrayList<Piece> whitePieces;
	private Boolean gameOver;
	
	public void resetBoard() {
		
		gameOver = false;
		board = new ArrayList<ArrayList<Piece>>();
		
		for(int i = 0; i < 8; i++) {
			
			board.add(new ArrayList<Piece>());
			
			for(int j = 0; j < 8; j++) {
				
				board.get(i).add(new Piece(i, j, "empty", false));
			}
		}
		
		blackPieces = new ArrayList<Piece>();
		whitePieces = new ArrayList<Piece>();
		
		board.get(0).get(0).setColour("Black");
		blackPieces.add(new Piece(0, 0, "Black", false));
		board.get(2).get(0).setColour("Black");
		blackPieces.add(new Piece(2, 0, "Black", false));
		board.get(4).get(0).setColour("Black");
		blackPieces.add(new Piece(4, 0, "Black", false));
		board.get(6).get(0).setColour("Black");
		blackPieces.add(new Piece(6, 0, "Black", false));
		board.get(1).get(1).setColour("Black");
		blackPieces.add(new Piece(1, 1, "Black", false));
		board.get(3).get(1).setColour("Black");
		blackPieces.add(new Piece(3, 1, "Black", false));
		board.get(5).get(1).setColour("Black");
		blackPieces.add(new Piece(5, 1, "Black", false));
		board.get(7).get(1).setColour("Black");
		blackPieces.add(new Piece(7, 1, "Black", false));
		board.get(0).get(2).setColour("Black");
		blackPieces.add(new Piece(0, 2, "Black", false));
		board.get(2).get(2).setColour("Black");
		blackPieces.add(new Piece(2, 2, "Black", false));
		board.get(4).get(2).setColour("Black");
		blackPieces.add(new Piece(4, 2, "Black", false));
		board.get(6).get(2).setColour("Black");
		blackPieces.add(new Piece(6, 2, "Black", false));
		
		board.get(1).get(7).setColour("White");
		whitePieces.add(new Piece(1, 7, "White", false));
		board.get(3).get(7).setColour("White");
		whitePieces.add(new Piece(3, 7, "White", false));
		board.get(5).get(7).setColour("White");
		whitePieces.add(new Piece(5, 7, "White", false));
		board.get(7).get(7).setColour("White");
		whitePieces.add(new Piece(7, 7, "White", false));
		board.get(0).get(6).setColour("White");
		whitePieces.add(new Piece(0, 6, "White", false));
		board.get(2).get(6).setColour("White");
		whitePieces.add(new Piece(2, 6, "White", false));
		board.get(4).get(6).setColour("White");
		whitePieces.add(new Piece(4, 6, "White", false));
		board.get(6).get(6).setColour("White");
		whitePieces.add(new Piece(6, 6, "White", false));
		board.get(1).get(5).setColour("White");
		whitePieces.add(new Piece(1, 5, "White", false));
		board.get(3).get(5).setColour("White");
		whitePieces.add(new Piece(3, 5, "White", false));
		board.get(5).get(5).setColour("White");
		whitePieces.add(new Piece(5, 5, "White", false));
		board.get(7).get(5).setColour("White");
		whitePieces.add(new Piece(7, 5, "White", false));
	}
	
	public void movePiece(Piece p, int x, int y) {
		
		board.get(p.getX()).get(p.getY()).setColour("empty");
		
	    if(p.getColour() == "Black") {
	    	
	    	int index = 0;
	    	
	    	for(int i = 0; i < blackPieces.size(); i++) {
	    		
	    		if(blackPieces.get(i).getX() == p.getX() && blackPieces.get(i).getY() == p.getY()) {
	    			
	    			index = i;
	    		}
	    	}
	    	
	    	blackPieces.get(index).setX(x);
	    	blackPieces.get(index).setY(y);

	    	if(y == 7) {
	    		
	    		p.setKing(true);
	    		blackPieces.get(index).setKing(true);
	    	}
	    }
		
		board.get(x).get(y).setColour(p.getColour());
		board.get(x).get(y).setKing(p.getKing());
	}
	
	public void printBoard(){
		
		for(int i = 0; i < 8; i++) {
			
			for(int j = 0; j < 8; j++) {
				
				System.out.print("|");
				
				if(board.get(i).get(j).getColour() == "Black") {
					
					System.out.print("B");
					
				} else {
					
					if(board.get(i).get(j).getColour() == "White") {
						
						System.out.print("W");
						
					} else {
						
						System.out.print(" ");
					}
				}
				
			}
			
			System.out.print("|");
			System.out.println("");
		}
		
		System.out.println("");
	}

	public ArrayList<ArrayList<Piece>> getBoard() {
		return board;
	}
	public void setBoard(ArrayList<ArrayList<Piece>> board) {
		this.board = board;
	}
	public ArrayList<Piece> getBlackPieces() {
		return blackPieces;
	}
	public void setBlackPieces(ArrayList<Piece> blackPieces) {
		this.blackPieces = blackPieces;
	}
	public ArrayList<Piece> getWhitePieces() {
		return whitePieces;
	}
	public void setWhitePieces(ArrayList<Piece> whitePieces) {
		this.whitePieces = whitePieces;
	}
	public Boolean getGameOver() {
		
		if(whitePieces.size() == 0 || blackPieces.size() == 0) {
			gameOver = true;
		}
		return gameOver;
	}
	public void setGameOver(Boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	
}
