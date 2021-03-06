package gamesimulation;
import java.util.ArrayList;

public class randomAI {

	public randomAI() {
		
		
	}
	
	public SimMove returnMove(ArrayList<SimMove> moves) {
		
		ArrayList<SimMove> captures = new ArrayList<SimMove>();
		
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
