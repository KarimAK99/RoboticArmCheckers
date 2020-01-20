package gamesimulation;

public class Tiles {

	
	public Tiles(int location, boolean occupied, int leftup, int rightup, int leftdown, int rightdown, int jumpleftup, int jumprightup, int jumpleftdown, int jumprightdown) {
		
		this.location = location;
		this.occupied = occupied;
		this.leftup = leftup;
		this.rightup = rightup;
		this.leftdown = leftdown; 
		this.rightdown = rightdown;
		this.jumpleftup = jumpleftup;
		this.jumprightup = jumprightup;
		this.jumpleftdown = jumpleftdown; 
		this.jumprightdown = jumprightdown;
	}
	
	public Tiles(Tiles tile) {
		
		this.location = tile.getLocation();
		this.occupied = tile.isOccupied();
		this.leftup = tile.getLeftup();
		this.rightup = tile.getRightup();
		this.leftdown = tile.getLeftdown(); 
		this.rightdown = tile.getRightdown();
		this.jumpleftup = tile.getJumpleftup();
		this.jumprightup = tile.getJumprightup();
		this.jumpleftdown = tile.getJumpleftdown(); 
		this.jumprightdown = tile.getJumprightdown();
	}
	
	private int location;
	private boolean occupied;
	private int leftup;
	private int rightup;
	private int leftdown;
	private int rightdown;
	private int jumpleftup;
	private int jumprightup;
	private int jumpleftdown;
	private int jumprightdown;
	private String status;
	
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public boolean isOccupied() {
		return occupied;
	}
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	public int getLeftup() {
		return leftup;
	}
	public void setLeftup(int leftup) {
		this.leftup = leftup;
	}
	public int getRightup() {
		return rightup;
	}
	public void setRightup(int rightup) {
		this.rightup = rightup;
	}
	public int getLeftdown() {
		return leftdown;
	}
	public void setLeftdown(int leftdown) {
		this.leftdown = leftdown;
	}
	public int getRightdown() {
		return rightdown;
	}
	public void setRightdown(int rightdown) {
		this.rightdown = rightdown;
	}
	public int getJumpleftup() {
		return jumpleftup;
	}
	public void setJumpleftup(int jumpleftup) {
		this.jumpleftup = jumpleftup;
	}
	public int getJumprightup() {
		return jumprightup;
	}
	public void setJumprightup(int jumprightup) {
		this.jumprightup = jumprightup;
	}
	public int getJumpleftdown() {
		return jumpleftdown;
	}
	public void setJumpleftdown(int jumpleftdown) {
		this.jumpleftdown = jumpleftdown;
	}
	public int getJumprightdown() {
		return jumprightdown;
	}
	public void setJumprightdown(int jumprightdown) {
		this.jumprightdown = jumprightdown;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
