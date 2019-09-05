import java.util.ArrayList;

public class Game {

	public static void main(String[] Args) {
		
		Game g = new Game();
		AI rand = new AI();
		
		BoardState b = new BoardState();
		b.resetBoard();
		
		b.printBoard();
		
		int player = 0;
		
		while(!b.getGameOver()) {
			
			Move m;
			
			if(player == 0) {
				
				m = rand.random(b, b.getBlackPieces(), g);
				b.movePiece(m.getP(), m.getX(), m.getY());
			}
			
			if(player == 1) {
				
				m = rand.random(b, b.getWhitePieces(), g);
				b.movePiece(m.getP(), m.getX(), m.getY());
			}
			
			b.printBoard();
			
			if(player == 0) {
				player = 1;
			} else {
				player = 0;
			}
			
			try{System.in.read();}
			catch(Exception e){}
		}
	
		if(b.getBlackPieces().size() == 0) {
			
			System.out.println("White player wins!");
		} else {
			if(b.getWhitePieces().size() == 0) {
			
				System.out.println("Black player wins!");
			} else {
				
				System.out.println("Its a tie!");
			}
		}
	}
	
	public ArrayList<Move> findLegalMoves(Piece p, BoardState b){
		
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		
		if(p.getColour() == "Black") {
			
			if(p.getX()> 0 && p.getY() < 7) {
				
				if(b.getBoard().get(p.getX() - 1).get(p.getY() + 1).getColour() == "empty") {
					
					legalMoves.add(new Move(p, p.getX()-1, p.getY()-1, false));
				}
				
				if(b.getBoard().get(p.getX() - 1).get(p.getY() + 1).getColour() == "White" && b.getBoard().get(p.getX() - 2).get(p.getY() + 2).getColour() == "empty") {
					
					if(p.getX()> 1 && p.getY() < 6) {
						
						legalMoves.add(new Move(p, p.getX()-2, p.getY()+2, true));
					}
				}
			}
			
			if(p.getX() < 7 && p.getY() < 7) {
					
				if(b.getBoard().get(p.getX() + 1).get(p.getY() + 1).getColour() == "empty") {
						
					legalMoves.add(new Move(p, p.getX()+1, p.getY()+1, false));
				}
					
				if(b.getBoard().get(p.getX() + 1).get(p.getY() + 1).getColour() == "White" && b.getBoard().get(p.getX() + 2).get(p.getY() + 2).getColour() == "empty") {
						
					if(p.getX() < 6 && p.getY() < 6) {
						
						legalMoves.add(new Move(p, p.getX() + 2, p.getY()+2, true));
					}
				}
			}
			
			if(p.getKing()) {
				
				if(p.getX() < 7 && p.getY() > 0) {
					
					if(b.getBoard().get(p.getX() + 1).get(p.getY() - 1).getColour() == "empty") {

						legalMoves.add(new Move(p, p.getX()+1, p.getY()-1, false));
					}
					
					if(b.getBoard().get(p.getX() + 1).get(p.getY() - 1).getColour() == "White" && b.getBoard().get(p.getX() + 2).get(p.getY() - 2).getColour() == "empty") {

						if(p.getX() < 6 && p.getY() > 1) {
							
							legalMoves.add(new Move(p, p.getX()+2, p.getY()-2, true));
						}
					}
				}
				
				if(p.getX() > 0 && p.getY() > 0) {
					
					if(b.getBoard().get(p.getX() - 1).get(p.getY() - 1).getColour() == "empty") {
						
						legalMoves.add(new Move(p, p.getX()-1, p.getY()-1, false));
					}
					
					if(b.getBoard().get(p.getX() - 1).get(p.getY() - 1).getColour() == "White" && b.getBoard().get(p.getX() - 2).get(p.getY() - 2).getColour() == "empty") {
						
						if(p.getX() > 1 && p.getY() > 1) {
							
							legalMoves.add(new Move(p, p.getX()-2, p.getY()-2, true));
						}
					}
				}
			}		
		}
		
		if(p.getColour() == "White") {
			
			if(p.getX() < 7 && p.getY() > 0) {
				
				if(b.getBoard().get(p.getX() + 1).get(p.getY() - 1).getColour() == "empty") {

					legalMoves.add(new Move(p, p.getX()+1, p.getY()-1, false));
				}
				
				if(b.getBoard().get(p.getX() + 1).get(p.getY() - 1).getColour() == "Black" && b.getBoard().get(p.getX() + 2).get(p.getY() - 2).getColour() == "empty") {

					if(p.getX() < 6 && p.getY() > 1) {
						
						legalMoves.add(new Move(p, p.getX()+2, p.getY()-2, true));
					}
				}
			}
			
			if(p.getX() > 0 && p.getY() > 0) {
				
				if(b.getBoard().get(p.getX() - 1).get(p.getY() - 1).getColour() == "empty") {
					
					legalMoves.add(new Move(p, p.getX()-1, p.getY()-1, false));
				}
				
				if(b.getBoard().get(p.getX() - 1).get(p.getY() - 1).getColour() == "Black" && b.getBoard().get(p.getX() - 2).get(p.getY() - 2).getColour() == "empty") {
					
					if(p.getX() > 1 && p.getY() > 1) {
						
						legalMoves.add(new Move(p, p.getX()-2, p.getY()-2, true));
					}
				}
			}
			
			if(p.getKing()) {
				
				if(p.getX()> 0 && p.getY() < 7) {
					
					if(b.getBoard().get(p.getX() - 1).get(p.getY() + 1).getColour() == "empty") {
						
						legalMoves.add(new Move(p, p.getX()-1, p.getY()-1, false));
					}
					
					if(b.getBoard().get(p.getX() - 1).get(p.getY() + 1).getColour() == "Black" && b.getBoard().get(p.getX() - 2).get(p.getY() + 2).getColour() == "empty") {
						
						if(p.getX()> 1 && p.getY() < 6) {
							
							legalMoves.add(new Move(p, p.getX()-2, p.getY()+2, true));
						}
					}
				}
				
				if(p.getX() < 7 && p.getY() < 7) {
						
					if(b.getBoard().get(p.getX() + 1).get(p.getY() + 1).getColour() == "empty") {
							
						legalMoves.add(new Move(p, p.getX()+1, p.getY()+1, false));
					}
						
					if(b.getBoard().get(p.getX() + 1).get(p.getY() + 1).getColour() == "Black" && b.getBoard().get(p.getX() + 2).get(p.getY() + 2).getColour() == "empty") {
							
						if(p.getX() < 6 && p.getY() < 6) {
							
							legalMoves.add(new Move(p, p.getX() + 2, p.getY()+2, true));
						}
					}
				}
			}
			
		}
		
		if(legalMoves.size() == 0) {
			b.setGameOver(true);
		}
		
		return legalMoves;
	}

	public ArrayList<Move> findMultipleJumps(ArrayList<Move> legalJumps, BoardState b){
		
		for(int i = 0; i < legalJumps.size(); i++) {
			
			if(legalJumps.get(i).getCapture()) {
				
				legalJumps.get(i).getMoves().add(legalJumps.get(i));
				int count = 0;
				
				Boolean moreJumps = true;
				
				while(moreJumps) {
					
					moreJumps = isMoreJumps(legalJumps.get(i), count, b);
					count++;
				}
			}
		}
		
		return legalJumps;
	}
	
	private Boolean isMoreJumps(Move m, int count, BoardState b){
		
		if(count >= m.getMoves().size()) {
			
			return false;
		}
		
		int x = m.getMoves().get(count).getX();
		int y = m.getMoves().get(count).getY();
		
		if(m.getP().getColour() == "Black" && y < 6){
			
			if(b.getBoard().get(x - 1).get(y + 1).getColour() == "White" && x - 2 > 0) {
				
				if(b.getBoard().get(x - 2).get(y + 2).getColour() == "empty") {
					
					m.getMoves().add(new Move(m.getP(), x - 2, y + 2, false));
				}
			}
			
			if(b.getBoard().get(x + 1).get(y + 1).getColour() == "White" && x + 2 < 7) {
				
				if(b.getBoard().get(x + 2).get(y + 2).getColour() == "empty") {
					
					m.getMoves().add(new Move(m.getP(), x + 2, y + 2, false));
				}
			}
			
		}
		
		if(m.getP().getColour() == "White" && y > 0){
			
			if(b.getBoard().get(x - 1).get(y + 1).getColour() == "Black" && x + 2 < 7) {
				
				if(b.getBoard().get(x + 2).get(y - 2).getColour() == "empty") {
					
					m.getMoves().add(new Move(m.getP(), x + 2, y - 2, false));
				}
			}
			
			if(b.getBoard().get(x + 1).get(y + 1).getColour() == "Black" && x - 2 > 0) {
				
				if(b.getBoard().get(x - 2).get(y - 2).getColour() == "empty") {
					
					m.getMoves().add(new Move(m.getP(), x - 2, y - 2, false));
				}
			}
			
		}
		
		if(m.getP().getKing()) {
			
			if(m.getP().getColour() == "Black"  && y > 0) {
				
				if(b.getBoard().get(x - 1).get(y + 1).getColour() == "White" && x + 2 < 7) {
					
					if(b.getBoard().get(x + 2).get(y - 2).getColour() == "empty") {
						
						m.getMoves().add(new Move(m.getP(), x + 2, y - 2, false));
					}
				}
				
				if(b.getBoard().get(x + 1).get(y + 1).getColour() == "White" && x - 2 > 0) {
					
					if(b.getBoard().get(x - 2).get(y - 2).getColour() == "empty") {
						
						m.getMoves().add(new Move(m.getP(), x - 2, y - 2, false));
					}
				}
			}
			
			if(m.getP().getColour() == "White" && y < 6) {
				
				if(b.getBoard().get(x - 1).get(y + 1).getColour() == "Black" && x - 2 > 0) {
					
					if(b.getBoard().get(x - 2).get(y + 2).getColour() == "empty") {
						
						m.getMoves().add(new Move(m.getP(), x - 2, y + 2, false));
					}
				}
				
				if(b.getBoard().get(x + 1).get(y + 1).getColour() == "Black" && x + 2 < 7) {
					
					if(b.getBoard().get(x + 2).get(y + 2).getColour() == "empty") {
						
						m.getMoves().add(new Move(m.getP(), x + 2, y + 2, false));
					}
				}
			}
		}
		
		return true;
	}

}
