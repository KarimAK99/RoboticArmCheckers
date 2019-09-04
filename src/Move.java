import java.util.ArrayList;

public class Move {

	public Move(Piece piece, int xValue, int yValue){
		
		p = piece;
		x = xValue;
		y = yValue;
		ArrayList<Move> m = new ArrayList<Move>();
		moves = m;
	}
	
	private Piece p;
	private int x, y;
	private ArrayList<Move> moves; 
	
	public Piece getP() {
		return p;
	}
	public void setP(Piece p) {
		this.p = p;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public ArrayList<Move> getMoves() {
		return moves;
	}
	public void setMoves(ArrayList<Move> moves) {
		this.moves = moves;
	}
	
}
