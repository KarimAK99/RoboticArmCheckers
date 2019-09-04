import java.util.ArrayList;

public class Game {

	
	public ArrayList<Move> findLegalMoves(Piece p, BoardState b){
		
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		
		if(p.getColour() == "Black") {
			
			if(p.getX()> 0 && p.getY() < 7) {
				
				if(b.getBoard().get(p.getX() - 1).get(p.getY() + 1).getColour() == "empty") {
					
					legalMoves.add(new Move(p, p.getX()-1, p.getY()-1));
				}
				
				if(b.getBoard().get(p.getX() - 1).get(p.getY() + 1).getColour() == "White") {
					
				
				}
			}
			
			if(p.getX() < 7 && p.getY() < 7) {
					
				if(b.getBoard().get(p.getX() + 1).get(p.getY() + 1).getColour() == "empty") {
						
					legalMoves.add(new Move(p, p.getX()+1, p.getY()+1));
				}
					
				if(b.getBoard().get(p.getX() + 1).get(p.getY() + 1).getColour() == "White") {
						
						
				}
			}
			
		}
		
		if(p.getColour() == "White") {
			
			if(p.getX() < 7 && p.getY() > 0) {
				
				if(b.getBoard().get(p.getX() + 1).get(p.getY() - 1).getColour() == "empty") {

					legalMoves.add(new Move(p, p.getX()+1, p.getY()-1));
				}
				
				if(b.getBoard().get(p.getX() + 1).get(p.getY() - 1).getColour() == "Black") {

				}
			}
			
			if(p.getX() > 0 && p.getY() > 0) {
				
				if(b.getBoard().get(p.getX() - 1).get(p.getY() - 1).getColour() == "empty") {
					
					legalMoves.add(new Move(p, p.getX()-1, p.getY()-1));
				}
				
				if(b.getBoard().get(p.getX() - 1).get(p.getY() - 1).getColour() == "Black") {
					
				}
			}
			
			
		}
		
		return legalMoves;
	}
	
	public Boolean checkLeftDiagonal(Piece p, BoardState b){
		
		if(p.getColour() == "Black") {
			
			if(p.getX()> 0 && p.getY() < 7) {
				
				if(b.getBoard().get(p.getX() - 1).get(p.getY() + 1).getColour() == "empty") {
					
					return true;
				}
				
				if(b.getBoard().get(p.getX() - 1).get(p.getY() + 1).getColour() == "White") {
					
					return true;
				}
			}
			
			return false;
		}
		
		if(p.getColour() == "White") {
			
			if(p.getX() < 7 && p.getY() > 0) {
				
				if(b.getBoard().get(p.getX() + 1).get(p.getY() - 1).getColour() == "empty") {
					
					return true;
				}
				
				if(b.getBoard().get(p.getX() + 1).get(p.getY() - 1).getColour() == "Black") {
					
					return true;
				}
			}
			
			return false;
		}
		
		return false;
		
	}
	
	public Boolean checkRightDiagonal(Piece p, BoardState b){
		
		if(p.getColour() == "Black") {
			
			if(p.getX() < 7 && p.getY() < 7) {
				
				if(b.getBoard().get(p.getX() + 1).get(p.getY() + 1).getColour() == "empty") {
					
					return true;
				}
				
				if(b.getBoard().get(p.getX() + 1).get(p.getY() + 1).getColour() == "White") {
					
					return true;
				}
			}
			
			return false;
		}
		
		if(p.getColour() == "White") {
			
			if(p.getX() > 0 && p.getY() > 0) {
				
				if(b.getBoard().get(p.getX() - 1).get(p.getY() - 1).getColour() == "empty") {
					
					return true;
				}
				
				if(b.getBoard().get(p.getX() - 1).get(p.getY() - 1).getColour() == "Black") {
					
					return true;
				}
			}
			
			return false;
		}
		
		return false;
		
	}
	
}
