
public class Piece {

	public Piece(int xValue, int yValue, String colourValue, Boolean kingValue) {
		
		x = xValue;
		y = yValue;
		colour = colourValue;
		king = kingValue;
	}
	
	private int x, y;
	private Boolean king;
	private String colour;
	
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
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public Boolean getKing() {
		return king;
	}
	public void setKing(Boolean king) {
		this.king = king;
	}
	
	
}
