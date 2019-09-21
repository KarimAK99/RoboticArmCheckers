public class Move {

	public Move(int start, int end, boolean jump, int capture){
		
		this.start = start;
		this.end = end;
		this.jump = jump;
		this.capture = capture;
	}
	
	private int start, end, capture;
	private boolean jump;
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public boolean isJump() {
		return jump;
	}
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	public int getCapture() {
		return capture;
	}
	public void setCapture(int capture) {
		this.capture = capture;
	}
	
}
