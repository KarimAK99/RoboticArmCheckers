package gamesimulation;

import java.util.ArrayList;

public class greedyAI {

	public greedyAI(ArrayList<Tiles> AllTiles, ArrayList<SimPieces> RobotPieces, ArrayList<SimPieces> PlayerPieces) {
		
		for(int i = 0; i < AllTiles.size(); i++) {
			
			this.AllTiles.add(new Tiles(AllTiles.get(i)));
			this.AllTiles.get(i).setStatus(AllTiles.get(i).getStatus());
		}
		
		for(int i = 0; i < RobotPieces.size(); i++) {
			
			this.RobotPieces.add(new SimPieces(RobotPieces.get(i)));
		}
		
		for(int i = 0; i < PlayerPieces.size(); i++) {
			
			this.PlayerPieces.add(new SimPieces(PlayerPieces.get(i)));
		}
		
	}
	
	private ArrayList<Tiles> AllTiles = new ArrayList<Tiles>();
	private ArrayList<SimPieces> RobotPieces = new ArrayList<SimPieces>();
	private ArrayList<SimPieces> PlayerPieces = new ArrayList<SimPieces>();
	
	public SimMove returnMove(ArrayList<SimMove> moves, int player) {
		
		ArrayList<Tiles> AT = AllTiles;
		ArrayList<SimPieces> RP = RobotPieces;
		ArrayList<SimPieces> PP = PlayerPieces;
		
		ArrayList<Double> scores = new ArrayList<Double>();
				
		double best = -1000;
		
		ArrayList<SimMove> captures = new ArrayList<SimMove>();
		ArrayList<SimMove> bestMoves = new ArrayList<SimMove>();
		
		for(int i = 0; i < moves.size(); i++) {
			
			if(moves.get(i).getCapture() > -1) {
				
				captures.add(moves.get(i));
			}
		}
		
		if(captures.size()>0) {
			
			for(int i = 0; i < captures.size(); i++) {
				
				scores.add(playTempMove(captures.get(i), AT, RP, PP, player));
			}

			for(int i = 0; i < scores.size(); i++) {
				
				if(scores.get(i) >= best) {
					
					best = scores.get(i);
				}
			}
			
			for(int i = 0; i < scores.size(); i++) {
				
				if(scores.get(i) == best) {
					
					bestMoves.add(captures.get(i));
				}
			}
			
		} else {
			
			for(int i = 0; i < moves.size(); i++) {
				
				scores.add(playTempMove(moves.get(i), AT, RP, PP, player));
			}

			for(int i = 0; i < scores.size(); i++) {
				
				if(scores.get(i) >= best) {
					
					best = scores.get(i);
				}
			}
			
			for(int i = 0; i < scores.size(); i++) {
				
				if(scores.get(i) == best) {
					
					bestMoves.add(moves.get(i));
				}
			}
					
		}
		
		double random = Math.random();
		int move = (int)(random*bestMoves.size());
		
		return bestMoves.get(move);
	}
	
	public double playTempMove(SimMove move, ArrayList<Tiles> AT, ArrayList<SimPieces> RP, ArrayList<SimPieces> PP, int player) {
		
		int start = move.getStart() - 1;
		int end = move.getEnd() - 1;
		
		if(AT.get(start).getStatus().equals("R") && RP.size() > 0) {
			
			int index = 0;
			
			for(int i = 0; i < RP.size(); i++) {
				
				if(RP.get(i).getLocation() == move.getStart()) {
					
					index = i;
				}
			}
			
			RP.get(index).setLocation(move.getEnd());
			
			if(move.getEnd() > 28) {
				
				RP.get(index).setKing(true);
			}
			
			if(move.getCapture() > -1 && PP.size() > 0) {
				
				int remove = 0;
				
				for(int i = 0; i < PP.size(); i++) {
					
					if(PP.get(i).getLocation() == move.getCapture()) {
						
						remove = i;
					}
				}
				
				PP.remove(remove);
			}
			
		} else {
			
			if(PP.size() > 0) {
			int index = 0;
			
			for(int i = 0; i < PP.size(); i++) {
				
				if(PP.get(i).getLocation() == move.getStart()) {
					
					index = i;
				}
			}
			
			PP.get(index).setLocation(move.getEnd());
			
			if(move.getEnd() < 5) {
				
				PP.get(index).setKing(true);
			}
			}
			if(move.getCapture() > -1 && RP.size() > 0) {
				
				int remove = 0;
				
				for(int i = 0; i < RP.size(); i++) {
					
					if(RP.get(i).getLocation() == move.getCapture()) {
						
						remove = i;
					}
				}
				
				RP.remove(remove);
			}
		}
		
		if(move.getCapture() > -1) {
			
			AT.get(move.getCapture()-1).setOccupied(false);
			AT.get(move.getCapture()-1).setStatus(" ");
			
		}
		
		AT.get(end).setStatus(AT.get(start).getStatus());
		AT.get(start).setStatus(" ");
		
		AT.get(start).setOccupied(false);
		AT.get(end).setOccupied(true);
		
		return calculateHeuristics(player, 1, 1, 2, 1, 1.5, 1, 1, 1, AT, RP, PP);
		
	}
	
	private double calculateHeuristics(int player, double h1, double h2, double h3, double h4, double h5, double h6, double h7, double h8, ArrayList<Tiles> AT, ArrayList<SimPieces> RP, ArrayList<SimPieces> PP) {	
		
		int RPs = RP.size();
		int PPs = PP.size();
		int robotProtected = 0;
		int playerProtected = 0;
		int robotKings = 0;
		int playerKings = 0;
		int robotMoves = 0;
		int playerMoves = 0;
		
		for(int i = 0; i < RP.size(); i++) {
			
			Tiles rt = AT.get(RP.get(i).getLocation() - 1);
			
			
			
			if(rt.getLeftdown()!=-1 && rt.getRightdown()!= -1) {
				if(!AT.get(rt.getLeftdown() - 1).getStatus().equals(" ") && !AT.get(rt.getRightdown() - 1).getStatus().equals(" ")) {
				
					robotProtected++;
				}
			} else {
				
				robotProtected++;
			}
			
			if(RP.get(i).isKing()) {
				
				robotKings++;
			}
			
			ArrayList<SimMove> rMoves = findMoves(RP, AT);
			robotMoves = rMoves.size();
			
		}
		
		for(int i = 0; i < PP.size(); i++) {
			
			Tiles pt = AT.get(PP.get(i).getLocation() - 1);
			
			if(pt.getLeftup()!=-1 && pt.getRightup()!= -1) {
				if(!AT.get(pt.getLeftup() - 1).getStatus().equals(" ") && !AT.get(pt.getRightup() - 1).getStatus().equals(" ")) {
				
					playerProtected++;
				}
			} else {
				
				playerProtected++;
			}
			
			if(PP.get(i).isKing()) {
				
				playerKings++;
			}
			
			ArrayList<SimMove> pMoves = findMoves(PP, AT);
			playerMoves = pMoves.size();
			
		}
		
		if(player == 1) {
			
			return (double)(h1*RPs - h2*PPs + h3*robotProtected - h4*playerProtected + h5*robotKings - h6*playerKings + h7*robotMoves - h8*playerMoves);
			
		} else {
			
			return (double)(-h1*RPs + h2*PPs - h3*robotProtected + h4*playerProtected - h5*robotKings + h6*playerKings - h7*robotMoves + h8*playerMoves);
		}
		
	}

	private ArrayList<SimMove> findMoves(ArrayList<SimPieces> pieces, ArrayList<Tiles> AT){
		
		ArrayList<SimMove> allMoves = new ArrayList<SimMove>();
		
		for(int i = 0; i < pieces.size(); i++) {
			
			int location = pieces.get(i).getLocation() - 1;
			int leftup = AT.get(pieces.get(i).getLocation() - 1).getLeftup() - 1;
			int rightup = AT.get(pieces.get(i).getLocation() - 1).getRightup() - 1;
			int leftdown = AT.get(pieces.get(i).getLocation() - 1).getLeftdown() - 1;
			int rightdown = AT.get(pieces.get(i).getLocation() - 1).getRightdown() - 1;
			int jumpleftup = AT.get(pieces.get(i).getLocation() - 1).getJumpleftup() - 1;
			int jumprightup = AT.get(pieces.get(i).getLocation() - 1).getJumprightup() - 1;
			int jumpleftdown = AT.get(pieces.get(i).getLocation() - 1).getJumpleftdown() - 1;
			int jumprightdown = AT.get(pieces.get(i).getLocation() - 1).getJumprightdown() - 1;
			
			if(leftup != -2) {
				
				if(pieces.get(i).isRobot() || pieces.get(i).isKing()) {
					
					if(AT.get(leftup).isOccupied()) {
					
						if(!(AT.get(leftup).getStatus().equals(AT.get(location).getStatus()))) {
						
							if(jumpleftup != -2) {
							
								if(!(AT.get(jumpleftup).isOccupied())){
								
									allMoves.add(new SimMove(location +1, jumpleftup +1, true, leftup+1));
								}
							}
						}
						
					} else {
						
						allMoves.add(new SimMove(location +1, leftup +1, false, -1));
					}
				}
			}
			
			if(rightup != -2) {
				
				if(pieces.get(i).isRobot() || pieces.get(i).isKing()) {
					
					if(AT.get(rightup).isOccupied()) {
					
						if(!(AT.get(rightup).getStatus().equals(AT.get(location).getStatus()))) {
						
							if(jumprightup != -2) {
							
								if(!(AT.get(jumprightup).isOccupied())){
								
									allMoves.add(new SimMove(location +1, jumprightup +1, true, rightup+1));
								}
							}
						}
						
					} else {
						
						allMoves.add(new SimMove(location +1, rightup +1, false, -1));
					}
				}
			}

			if(leftdown != -2) {
	
				if(!(pieces.get(i).isRobot()) || pieces.get(i).isKing()) {
		
					if(AT.get(leftdown).isOccupied()) {
		
						if(!(AT.get(leftdown).getStatus().equals(AT.get(location).getStatus()))) {
			
							if(jumpleftdown != -2) {
					
								if(!(AT.get(jumpleftdown).isOccupied())){
					
									allMoves.add(new SimMove(location +1, jumpleftdown +1, true, leftdown+1));
								}
							}
						}
			
					} else {
			
						allMoves.add(new SimMove(location +1, leftdown +1, false, -1));
					}
				}	
			}

			if(rightdown != -2) {
	
				if(!(pieces.get(i).isRobot()) || pieces.get(i).isKing()) {
		
					if(AT.get(rightdown).isOccupied()) {
		
						if(!(AT.get(rightdown).getStatus().equals(AT.get(location).getStatus()))) {
			
							if(jumprightdown != -2) {
				
								if(!(AT.get(jumprightdown).isOccupied())){
					
									allMoves.add(new SimMove(location +1, jumprightdown +1, true, rightdown+1));
								}
							}
						}
			
					} else {
			
						allMoves.add(new SimMove(location +1, rightdown +1, false, -1));
					}
				}
			}
			
		} 
		
		return allMoves;
	}
	
}
