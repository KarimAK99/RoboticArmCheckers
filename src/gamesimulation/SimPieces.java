package gamesimulation;

public class SimPieces {

	public SimPieces(boolean robot, boolean king, int location) {
		
		this.robot = robot;
		this.king = king;
		this.location = location;
		
	}

	public SimPieces(SimPieces piece) {
		
		this.robot = piece.isRobot();
		this.king = piece.isKing();
		this.location = piece.getLocation();
	}
	
	private boolean robot;
	private boolean king;
	private int location;
	
	public boolean isRobot() {
		return robot;
	}
	public void setRobot(boolean robot) {
		this.robot = robot;
	}
	public boolean isKing() {
		return king;
	}
	public void setKing(boolean king) {
		this.king = king;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	
}
