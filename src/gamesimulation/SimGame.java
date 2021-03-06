package gamesimulation;
import java.util.ArrayList;

public class SimGame {

	public static void main(String args[]) {
			
		int greedyWins = 0;
		int randomWins = 0;
		int ties = 0;
		int counter = 0;
		long current = System.currentTimeMillis();
		long start = System.currentTimeMillis();
		long stop = current + 5000;
		
		int bigdiff = 0;
		int smalldiff = 0;
		int closediff = 0;
		
		while(counter < 1000) {
			
			SimGame g = new SimGame();
			g.createTiles();
			g.createPieces();
			// g.printBoard();
			stop = current + 1000;
			SimMove previousMove = new SimMove(0,0,false,0);
			double average = 100;

			ArrayList<Double> playerone = new ArrayList<Double>();
			ArrayList<Double> playertwo = new ArrayList<Double>();
			
			double player1score = 0;
			double player2score = 0;
			
			while(!g.gameEnded  && System.currentTimeMillis() < stop) {
			
				current = System.currentTimeMillis();
				//greedyAI r = new greedyAI(g.AllTiles, g.RobotPieces, g.PlayerPieces);
				//System.out.println(g.RobotPieces.size() + " " + g.findMoves(g.RobotPieces).size());
				//g.playMove(r.returnMove(g.findMoves(g.RobotPieces), 1));
				//g.printBoard();
		
				MediumAI r = new MediumAI(g.AllTiles, g.RobotPieces, g.PlayerPieces);
				previousMove = r.returnMove(g.findMoves(g.RobotPieces), 1);
				g.playMove(previousMove);
				//randomAI p = new randomAI();
				//g.playMove(p.returnMove(g.findMoves(g.RobotPieces)));
				
				playerone.add(previousMove.getScore());
				double sumscores = 0;
				
				for(int i = 0; i < playerone.size(); i++) {
					
					sumscores += playerone.get(i);
					
				}
				
				player1score = sumscores / playerone.size();
				
				g.hasGameEnded();
			
				if(!g.gameEnded) {
					
				//	minimaxAI m = new minimaxAI(g.AllTiles, g.RobotPieces, g.PlayerPieces, -1, 2);
				//	Move move = m.returnMove();
				//	m = null;
					//System.out.println(move.getStart() + " " + move.getEnd());
				//	g.playMove(move);
				//	g.printBoard();
				//	g.hasGameEnded();
					
					EasyAI m = new EasyAI(g.AllTiles, g.RobotPieces, g.PlayerPieces);
					SimMove move = m.returnMove(g.findMoves(g.PlayerPieces), 2);
					g.playMove(move);
				//	randomAI p = new randomAI();
				//	g.playMove(p.returnMove(g.findMoves(g.PlayerPieces)));
					//g.printBoard();
					
				//	average = ((average + previousMove.getScore())/3) + 10;
				//	AdaptiveAI m = new AdaptiveAI(g.AllTiles, g.RobotPieces, g.PlayerPieces, average);
				//	Move move = m.returnMove(g.findMoves(g.PlayerPieces), 2, average);
				 //	g.playMove(move);
					
				//	MediumAI k = new MediumAI(g.AllTiles, g.RobotPieces, g.PlayerPieces);
				//	previousMove = k.returnMove(g.findMoves(g.PlayerPieces), 2);
				//	g.playMove(previousMove);
					
					playertwo.add(move.getScore());
					double sum2scores = 0;
					
					for(int i = 0; i < playertwo.size(); i++) {
						
						sum2scores += playertwo.get(i);
						
					}
					
					player2score = sum2scores / playertwo.size();
					
					g.hasGameEnded();
				}
			}
			
			if(g.RobotPieces.size() == 0 || g.findMoves(g.RobotPieces).size() == 0) {
				
				System.out.println("Player 2 Wins game " + counter + "  player 1 average score: " + player1score + "  player 2 average score: " + player2score);
				randomWins++;
			} else {
			
				
				if(g.PlayerPieces.size() == 0 || g.findMoves(g.PlayerPieces).size() == 0) {
					
					System.out.println("Player 1 Wins game " + counter  + "  player 1 average score: " + player1score + "  player 2 average score: " + player2score);
					greedyWins++;
				} else {
					
					System.out.println("Tie in game " + counter);
					ties++;
				}
			}
			
			if(Math.abs(player1score - player2score) > 17) {
				
				bigdiff++;
			} else {
				
				if(Math.abs(player1score) - player2score > 8.5) {
					
					smalldiff++;
				} else {
					closediff++;
				}
			}
			
			counter++;
		}
		
		System.out.println("Games played = " + counter + " Easy wins = " + greedyWins + " Adaptive wins: " + randomWins + " ties: " + ties);
		System.out.println("Bigdiff " + bigdiff + " Smalldiff " + smalldiff + " closediff " + closediff);
		System.out.println("Time taken: " + (System.currentTimeMillis() - start) + " milliseconds, or " + ((System.currentTimeMillis() - start)/1000) + " seconds, or " + (((System.currentTimeMillis() - start)/1000)/60) + " minutes");
	}
	
	private boolean gameEnded = false;
	private ArrayList<Tiles> AllTiles = new ArrayList<Tiles>();
	private ArrayList<SimPieces> AllPieces = new ArrayList<SimPieces>();
	private ArrayList<SimPieces> RobotPieces = new ArrayList<SimPieces>();
	private ArrayList<SimPieces> PlayerPieces = new ArrayList<SimPieces>();
	
	private void createTiles(){
		
		Tiles tile1 = new Tiles(1, false, -1, 5, -1, -1, -1, 10, -1, -1);
		Tiles tile2 = new Tiles(2, false, 5, 6, -1, -1, 9, 11, -1, -1);
		Tiles tile3 = new Tiles(3, false, 6, 7, -1, -1, 10, 12, -1, -1);
		Tiles tile4 = new Tiles(4, false, 7, 8, -1, -1, 11, -1, -1, -1);
		Tiles tile5 = new Tiles(5, false, 9, 10, 1, 2, -1, 14, -1, -1);
		Tiles tile6 = new Tiles(6, false, 10, 11, 2, 3, 13, 15, -1, -1);
		Tiles tile7 = new Tiles(7, false, 11, 12, 3, 4, 14, 16, -1, -1);
		Tiles tile8 = new Tiles(8, false, 12, -1, 4, -1, 15, -1, -1, -1);
		Tiles tile9 = new Tiles(9, false, -1, 13, -1, 5, -1, 18, -1, 2);
		Tiles tile10 = new Tiles(10, false, 13, 14, 5, 6, 17, 19, 1, 3);
		Tiles tile11 = new Tiles(11, false, 14, 15, 6, 7, 18, 20, 2, 4);
		Tiles tile12 = new Tiles(12, false, 15, 16, 7, 8, 19, -1, 3, -1);
		Tiles tile13 = new Tiles(13, false, 17, 18, 9, 10, -1, 22, -1, 6);
		Tiles tile14 = new Tiles(14, false, 18, 19, 10, 11, 21, 23, 5, 7);
		Tiles tile15 = new Tiles(15, false, 19, 20, 11, 12, 22, 24, 6, 8);
		Tiles tile16 = new Tiles(16, false, 20, -1, 12, -1, 23, -1, 7, -1);
		Tiles tile17 = new Tiles(17, false, -1, 21, -1, 13, -1, 26, -1, 10);
		Tiles tile18 = new Tiles(18, false, 21, 22, 13, 14, 25, 27, 8, 11);
		Tiles tile19 = new Tiles(19, false, 22, 23, 14, 15, 26, 28, 10, 12);
		Tiles tile20 = new Tiles(20, false, 23, 24, 15, 16, 27, -1, 11, -1);
		Tiles tile21 = new Tiles(21, false, 25, 25, 17, 18, -1, 30, -1, 14);
		Tiles tile22 = new Tiles(22, false, 26, 27, 18, 19, 29, 31, 13, 15);
		Tiles tile23 = new Tiles(23, false, 27, 28, 19, 20, 30, 32, 14, 16);
		Tiles tile24 = new Tiles(24, false, 28, -1, 20, -1, 31, -1, 15, -1);
		Tiles tile25 = new Tiles(25, false, -1, 29, -1, 21, -1, -1, -1, 18);
		Tiles tile26 = new Tiles(26, false, 29, 30, 21, 22, -1, -1, 17, 19);
		Tiles tile27 = new Tiles(27, false, 30, 31, 22, 23, -1, -1, 18, 20);
		Tiles tile28 = new Tiles(28, false, 31, 32, 23, 24, -1, -1, 19, -1);
		Tiles tile29 = new Tiles(29, false, -1, -1, 25, 26, -1, -1, -1, 22);
		Tiles tile30 = new Tiles(30, false, -1, -1, 26, 27, -1, -1, 21, 23);
		Tiles tile31 = new Tiles(31, false, -1, -1, 27, 28, -1, -1, 22, 24);
		Tiles tile32 = new Tiles(32, false, -1, -1, 28, -1, -1, -1, 23, -1);
		
		AllTiles.clear();
		
		AllTiles.add(tile1);
		AllTiles.add(tile2);
		AllTiles.add(tile3);
		AllTiles.add(tile4);
		AllTiles.add(tile5);
		AllTiles.add(tile6);
		AllTiles.add(tile7);
		AllTiles.add(tile8);
		AllTiles.add(tile9);
		AllTiles.add(tile10);
		AllTiles.add(tile11);
		AllTiles.add(tile12);
		AllTiles.add(tile13);
		AllTiles.add(tile14);
		AllTiles.add(tile15);
		AllTiles.add(tile16);
		AllTiles.add(tile17);
		AllTiles.add(tile18);
		AllTiles.add(tile19);
		AllTiles.add(tile20);
		AllTiles.add(tile21);
		AllTiles.add(tile22);
		AllTiles.add(tile23);
		AllTiles.add(tile24);
		AllTiles.add(tile25);
		AllTiles.add(tile26);
		AllTiles.add(tile27);
		AllTiles.add(tile28);
		AllTiles.add(tile29);
		AllTiles.add(tile30);
		AllTiles.add(tile31);
		AllTiles.add(tile32);		
		
	}
	
	private void createPieces() {
		
		SimPieces piece1 = new SimPieces(true, false, 1);
		SimPieces piece2 = new SimPieces(true, false, 2);
		SimPieces piece3 = new SimPieces(true, false, 3);
		SimPieces piece4 = new SimPieces(true, false, 4);
		SimPieces piece5 = new SimPieces(true, false, 5);
		SimPieces piece6 = new SimPieces(true, false, 6);
		SimPieces piece7 = new SimPieces(true, false, 7);
		SimPieces piece8 = new SimPieces(true, false, 8);
		SimPieces piece9 = new SimPieces(true, false, 9);
		SimPieces piece10 = new SimPieces(true, false, 10);
		SimPieces piece11 = new SimPieces(true, false, 11);
		SimPieces piece12 = new SimPieces(true, false, 12);
		
		SimPieces piece13 = new SimPieces(false, false, 21);
		SimPieces piece14 = new SimPieces(false, false, 22);
		SimPieces piece15 = new SimPieces(false, false, 23);
		SimPieces piece16 = new SimPieces(false, false, 24);
		SimPieces piece17 = new SimPieces(false, false, 25);
		SimPieces piece18 = new SimPieces(false, false, 26);
		SimPieces piece19 = new SimPieces(false, false, 27);
		SimPieces piece20 = new SimPieces(false, false, 28);
		SimPieces piece21 = new SimPieces(false, false, 29);
		SimPieces piece22 = new SimPieces(false, false, 30);
		SimPieces piece23 = new SimPieces(false, false, 31);
		SimPieces piece24 = new SimPieces(false, false, 32);
		
		AllTiles.get(0).setOccupied(true);
		AllTiles.get(1).setOccupied(true);
		AllTiles.get(2).setOccupied(true);
		AllTiles.get(3).setOccupied(true);
		AllTiles.get(4).setOccupied(true);
		AllTiles.get(5).setOccupied(true);
		AllTiles.get(6).setOccupied(true);
		AllTiles.get(7).setOccupied(true);
		AllTiles.get(8).setOccupied(true);
		AllTiles.get(9).setOccupied(true);
		AllTiles.get(10).setOccupied(true);
		AllTiles.get(11).setOccupied(true);
		AllTiles.get(0).setStatus("R");
		AllTiles.get(1).setStatus("R");
		AllTiles.get(2).setStatus("R");
		AllTiles.get(3).setStatus("R");
		AllTiles.get(4).setStatus("R");
		AllTiles.get(5).setStatus("R");
		AllTiles.get(6).setStatus("R");
		AllTiles.get(7).setStatus("R");
		AllTiles.get(8).setStatus("R");
		AllTiles.get(9).setStatus("R");
		AllTiles.get(10).setStatus("R");
		AllTiles.get(11).setStatus("R");
		
		AllTiles.get(20).setOccupied(true);
		AllTiles.get(21).setOccupied(true);
		AllTiles.get(22).setOccupied(true);
		AllTiles.get(23).setOccupied(true);
		AllTiles.get(24).setOccupied(true);
		AllTiles.get(25).setOccupied(true);
		AllTiles.get(26).setOccupied(true);
		AllTiles.get(27).setOccupied(true);
		AllTiles.get(28).setOccupied(true);
		AllTiles.get(29).setOccupied(true);
		AllTiles.get(30).setOccupied(true);
		AllTiles.get(31).setOccupied(true);
		AllTiles.get(20).setStatus("P");
		AllTiles.get(21).setStatus("P");
		AllTiles.get(22).setStatus("P");
		AllTiles.get(23).setStatus("P");
		AllTiles.get(24).setStatus("P");
		AllTiles.get(25).setStatus("P");
		AllTiles.get(26).setStatus("P");
		AllTiles.get(27).setStatus("P");
		AllTiles.get(28).setStatus("P");
		AllTiles.get(29).setStatus("P");
		AllTiles.get(30).setStatus("P");
		AllTiles.get(31).setStatus("P");
		
		AllTiles.get(12).setStatus(" ");
		AllTiles.get(13).setStatus(" ");
		AllTiles.get(14).setStatus(" ");
		AllTiles.get(15).setStatus(" ");
		AllTiles.get(16).setStatus(" ");
		AllTiles.get(17).setStatus(" ");
		AllTiles.get(18).setStatus(" ");
		AllTiles.get(19).setStatus(" ");
		
		AllPieces.add(piece1);
		AllPieces.add(piece2);
		AllPieces.add(piece3);
		AllPieces.add(piece4);
		AllPieces.add(piece5);
		AllPieces.add(piece6);
		AllPieces.add(piece7);
		AllPieces.add(piece8);
		AllPieces.add(piece9);
		AllPieces.add(piece10);
		AllPieces.add(piece11);
		AllPieces.add(piece12);
		AllPieces.add(piece13);
		AllPieces.add(piece14);
		AllPieces.add(piece15);
		AllPieces.add(piece16);
		AllPieces.add(piece17);
		AllPieces.add(piece18);
		AllPieces.add(piece19);
		AllPieces.add(piece20);
		AllPieces.add(piece21);
		AllPieces.add(piece22);
		AllPieces.add(piece23);
		AllPieces.add(piece24);
		
		RobotPieces.add(piece1);
		RobotPieces.add(piece2);
		RobotPieces.add(piece3);
		RobotPieces.add(piece4);
		RobotPieces.add(piece5);
		RobotPieces.add(piece6);
		RobotPieces.add(piece7);
		RobotPieces.add(piece8);
		RobotPieces.add(piece9);
		RobotPieces.add(piece10);
		RobotPieces.add(piece11);
		RobotPieces.add(piece12);
		
		PlayerPieces.add(piece13);
		PlayerPieces.add(piece14);
		PlayerPieces.add(piece15);
		PlayerPieces.add(piece16);
		PlayerPieces.add(piece17);
		PlayerPieces.add(piece18);
		PlayerPieces.add(piece19);
		PlayerPieces.add(piece20);
		PlayerPieces.add(piece21);
		PlayerPieces.add(piece22);
		PlayerPieces.add(piece23);
		PlayerPieces.add(piece24);
		
		
	}

	private ArrayList<SimMove> findMoves(ArrayList<SimPieces> pieces){
		
		ArrayList<SimMove> allMoves = new ArrayList<SimMove>();
		
		for(int i = 0; i < pieces.size(); i++) {
			
			int location = pieces.get(i).getLocation() - 1;
			int leftup = AllTiles.get(pieces.get(i).getLocation() - 1).getLeftup() - 1;
			int rightup = AllTiles.get(pieces.get(i).getLocation() - 1).getRightup() - 1;
			int leftdown = AllTiles.get(pieces.get(i).getLocation() - 1).getLeftdown() - 1;
			int rightdown = AllTiles.get(pieces.get(i).getLocation() - 1).getRightdown() - 1;
			int jumpleftup = AllTiles.get(pieces.get(i).getLocation() - 1).getJumpleftup() - 1;
			int jumprightup = AllTiles.get(pieces.get(i).getLocation() - 1).getJumprightup() - 1;
			int jumpleftdown = AllTiles.get(pieces.get(i).getLocation() - 1).getJumpleftdown() - 1;
			int jumprightdown = AllTiles.get(pieces.get(i).getLocation() - 1).getJumprightdown() - 1;
			
			if(leftup != -2) {
				
				if(pieces.get(i).isRobot() || pieces.get(i).isKing()) {
					
					if(AllTiles.get(leftup).isOccupied()) {
					
						if(!(AllTiles.get(leftup).getStatus().equals(AllTiles.get(location).getStatus()))) {
						
							if(jumpleftup != -2) {
							
								if(!(AllTiles.get(jumpleftup).isOccupied())){
								
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
					
					if(AllTiles.get(rightup).isOccupied()) {
					
						if(!(AllTiles.get(rightup).getStatus().equals(AllTiles.get(location).getStatus()))) {
						
							if(jumprightup != -2) {
							
								if(!(AllTiles.get(jumprightup).isOccupied())){
								
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
		
					if(AllTiles.get(leftdown).isOccupied()) {
		
						if(!(AllTiles.get(leftdown).getStatus().equals(AllTiles.get(location).getStatus()))) {
			
							if(jumpleftdown != -2) {
					
								if(!(AllTiles.get(jumpleftdown).isOccupied())){
					
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
		
					if(AllTiles.get(rightdown).isOccupied()) {
		
						if(!(AllTiles.get(rightdown).getStatus().equals(AllTiles.get(location).getStatus()))) {
			
							if(jumprightdown != -2) {
				
								if(!(AllTiles.get(jumprightdown).isOccupied())){
					
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

/*	private double calculateHeuristics(int player, double h1, double h2, double h3, double h4, double h5, double h6, double h7, double h8) {	
		
		int robotPieces = RobotPieces.size();
		int playerPieces = PlayerPieces.size();
		int robotProtected = 0;
		int playerProtected = 0;
		int robotKings = 0;
		int playerKings = 0;
		int robotMoves = 0;
		int playerMoves = 0;
		
		for(int i = 0; i < RobotPieces.size(); i++) {
			
			Tiles rt = AllTiles.get(RobotPieces.get(i).getLocation() - 1);
			
			
			
			if(rt.getLeftdown()!=-1 && rt.getRightdown()!= -1) {
				if(!AllTiles.get(rt.getLeftdown() - 1).getStatus().equals(" ") && !AllTiles.get(rt.getRightdown() - 1).getStatus().equals(" ")) {
				
					robotProtected++;
				}
			} else {
				
				robotProtected++;
			}
			
			if(RobotPieces.get(i).isKing()) {
				
				robotKings++;
			}
			
			ArrayList<Move> rMoves = findMoves(RobotPieces);
			robotMoves = rMoves.size();
			
		}
		
		for(int i = 0; i < PlayerPieces.size(); i++) {
			
			Tiles pt = AllTiles.get(PlayerPieces.get(i).getLocation() - 1);
			
			if(pt.getLeftup()!=-1 && pt.getRightup()!= -1) {
				if(!AllTiles.get(pt.getLeftup() - 1).getStatus().equals(" ") && !AllTiles.get(pt.getRightup() - 1).getStatus().equals(" ")) {
				
					playerProtected++;
				}
			} else {
				
				playerProtected++;
			}
			
			if(PlayerPieces.get(i).isKing()) {
				
				playerKings++;
			}
			
			ArrayList<Move> pMoves = findMoves(PlayerPieces);
			playerMoves = pMoves.size();
			
		}
		
		System.out.println("Robot Pieces: " + robotPieces + " Player Pieces: " + playerPieces + " Robot Protected: " + robotProtected + " Player Protected: " + playerProtected + " Robot Kings: " + robotKings
				+ " Player Kings: " + playerKings + " Robot Moves: " + robotMoves + " Player Moves: " + playerMoves);
		
		if(player == 1) {
			
			return (double)(h1*robotPieces - h2*playerPieces + h3*robotProtected - h4*playerProtected + h5*robotKings - h6*playerKings + h7*robotMoves - h8*playerMoves);
			
		} else {
			
			return (double)(-h1*robotPieces + h2*playerPieces - h3*robotProtected + h4*playerProtected - h5*robotKings + h6*playerKings - h7*robotMoves + h8*playerMoves);
		}
		
	} */

	
	private void playMove(SimMove move) {
		
		int start = move.getStart() - 1;
		int end = move.getEnd() - 1;
		
		if(AllTiles.get(start).getStatus().equals("R")) {
			
			int index = 0;
			
			for(int i = 0; i < RobotPieces.size(); i++) {
				
				if(RobotPieces.get(i).getLocation() == move.getStart()) {
					
					index = i;
				}
			}
			
			RobotPieces.get(index).setLocation(move.getEnd());
			
			if(move.getEnd() > 28) {
				
				RobotPieces.get(index).setKing(true);
			}
			
			if(move.getCapture() > -1) {
				
				int remove = 0;
				
				for(int i = 0; i < PlayerPieces.size(); i++) {
					
					if(PlayerPieces.get(i).getLocation() == move.getCapture()) {
						
						remove = i;
					}
				}
				
				PlayerPieces.remove(remove);
			}
			
		} else {
			
			int index = 0;
			
			for(int i = 0; i < PlayerPieces.size(); i++) {
				
				if(PlayerPieces.get(i).getLocation() == move.getStart()) {
					
					index = i;
				}
			}
			
			PlayerPieces.get(index).setLocation(move.getEnd());
			
			if(move.getEnd() < 5) {
				
				PlayerPieces.get(index).setKing(true);
			}
			
			if(move.getCapture() > -1) {
				
				int remove = 0;
				
				for(int i = 0; i < RobotPieces.size(); i++) {
					
					if(RobotPieces.get(i).getLocation() == move.getCapture()) {
						
						remove = i;
					}
				}
				
				RobotPieces.remove(remove);
			}
		}
		
		if(move.getCapture() > -1) {
			
			AllTiles.get(move.getCapture()-1).setOccupied(false);
			AllTiles.get(move.getCapture()-1).setStatus(" ");
			
		}
		
		AllTiles.get(end).setStatus(AllTiles.get(start).getStatus());
		AllTiles.get(start).setStatus(" ");
		
		AllTiles.get(start).setOccupied(false);
		AllTiles.get(end).setOccupied(true);
		
	} 
	
 	private boolean hasGameEnded() {
	
		boolean ended = false;
		
		if(findMoves(RobotPieces).size() == 0 || findMoves(PlayerPieces).size() == 0) {
			
			ended = true;
		}
		
		if(RobotPieces.size() == 0 || PlayerPieces.size() == 0) {
			
			ended = true;
		}

		gameEnded = ended;
		
		return ended;
	} 
	
	private void printBoard() {
		
		System.out.println("");
		
		System.out.println("| |" + "|" + AllTiles.get(28).getStatus() + "|| ||" + AllTiles.get(29).getStatus() + "|| ||" + AllTiles.get(30).getStatus() + "|| ||" + AllTiles.get(31).getStatus() + "|");
		System.out.println("|" + AllTiles.get(24).getStatus() + "|| ||" + AllTiles.get(25).getStatus() + "|| ||" + AllTiles.get(26).getStatus() + "|| ||" + AllTiles.get(27).getStatus() + "|| |");
		System.out.println("| |" + "|" + AllTiles.get(20).getStatus() + "|| ||" + AllTiles.get(21).getStatus() + "|| ||" + AllTiles.get(22).getStatus() + "|| ||" + AllTiles.get(23).getStatus() + "|");
		System.out.println("|" + AllTiles.get(16).getStatus() + "|| ||" + AllTiles.get(17).getStatus() + "|| ||" + AllTiles.get(18).getStatus() + "|| ||" + AllTiles.get(19).getStatus() + "|| |");
		System.out.println("| |" + "|" + AllTiles.get(12).getStatus() + "|| ||" + AllTiles.get(13).getStatus() + "|| ||" + AllTiles.get(14).getStatus() + "|| ||" + AllTiles.get(15).getStatus() + "|");
		System.out.println("|" + AllTiles.get(8).getStatus() + "|| ||" + AllTiles.get(9).getStatus() + "|| ||" + AllTiles.get(10).getStatus() + "|| ||" + AllTiles.get(11).getStatus() + "|| |");
		System.out.println("| |" + "|" + AllTiles.get(4).getStatus() + "|| ||" + AllTiles.get(5).getStatus() + "|| ||" + AllTiles.get(6).getStatus() + "|| ||" + AllTiles.get(7).getStatus() + "|");
		System.out.println("|" + AllTiles.get(0).getStatus() + "|| ||" + AllTiles.get(1).getStatus() + "|| ||" + AllTiles.get(2).getStatus() + "|| ||" + AllTiles.get(3).getStatus() + "|| |");
		
		System.out.println("");
		
	}
	
	private void printMoves(ArrayList<SimMove> moves){
		
		System.out.println("");
		
		for(int i = 0; i < moves.size(); i++) {
			
			System.out.println("Move " + (i+1) + ": from " + moves.get(i).getStart() + "  to " + moves.get(i).getEnd());
		}
		
		System.out.println("");
	}
}

