import java.util.ArrayList;

public class randomAI {

	public randomAI() {
		
		
	}
	
	public Move returnMove(ArrayList<Move> moves) {
		
		ArrayList<Move> captures = new ArrayList<Move>();
		
		for(int i = 0; i < moves.size(); i++) {
			
			if(moves.get(i).getCapture() > -1) {
				
				captures.add(moves.get(i));
			}
		}
		
		if(captures.size()>0) {
			
			double crandom = Math.random();
			int cmove = (int)(crandom*captures.size());
			
			return captures.get(cmove);
		}
		
		double random = Math.random();
		int move = (int)(random*moves.size());
		
		return moves.get(move);
	}
}
