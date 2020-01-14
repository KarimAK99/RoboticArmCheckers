import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.parser.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class Communicator {

	public static void main(String args[]) throws Exception {
		//readFromJSON();
		/*Game g = new Game();
		
		boolean RobotMove = true;
		
		while(g.hasGameEnded(g.RobotPieces, g.PlayerPieces, g.AllTiles)) {
			
			if(RobotMove) {
				
				Move m = g.mediumAI(g.AllTiles, g.RobotPieces, g.PlayerPieces, 1);
				sendMove(m);
				RobotMove = false;
			}
			
			Move r = readMove();
			ArrayList<Pieces> allpieces = readAllPieces();
			
			if(legalMove(allpieces, r)) {
				
				g.playMove(r, g.AllTiles, g.RobotPieces, g.PlayerPieces);
				sendLegality(true);
				RobotMove = true;
			} else {
				
				sendLegality(false);
			}
			
		}*/


	}

	// Method that should read move that player plays from CV
	private static Move readMove() {

		return null;
	}

	// Method that should read all the current pieces on the board
	private static ArrayList<Pieces> readAllPieces() {

		return null;
	}

	// Method that should send the robot move to control
	private static void sendMove(Move m) {

		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		
		if(m.getStart() >= 5) {
			if(m.getStart() >= 9) {
				if(m.getStart() >= 13) {
					if(m.getStart() >= 17) {
						if(m.getStart() >= 21) {
							if(m.getStart() >= 25) {
								if(m.getStart() >= 29) {
									y1 = 8;
								} else {
									y1 = 7;
								}
							} else {
								y1 = 6;
							}
						} else {
							y1 = 5;
						}
					} else {
						y1 = 4;
					}
				} else {
					y1 = 3;
				}
			} else {
				y1 = 2;
			}
		} else {
			y1 = 1;
		}
		
		if(m.getStart()%8 == 1) {
			x1 = 1;
		}
		if(m.getStart()%8 == 2) {
			x1 = 3;
		}
		if(m.getStart()%8 == 3) {
			x1 = 5;
		}
		if(m.getStart()%8 == 4) {
			x1 = 7;
		}
		if(m.getStart()%8 == 0) {
			x1 = 8;
		}
		if(m.getStart()%8 == 5) {
			x1 = 2;
		}
		if(m.getStart()%8 == 6) {
			x1 = 4;
		}
		if(m.getStart()%8 == 7) {
			x1 = 6;
		}
		
		if(m.getEnd() >= 5) {
			if(m.getEnd() >= 9) {
				if(m.getEnd() >= 13) {
					if(m.getEnd() >= 17) {
						if(m.getEnd() >= 21) {
							if(m.getEnd() >= 25) {
								if(m.getEnd() >= 29) {
									y2 = 8;
								} else {
									y2 = 7;
								}
							} else {
								y2 = 6;
							}
						} else {
							y2 = 5;
						}
					} else {
						y2 = 4;
					}
				} else {
					y2 = 3;
				}
			} else {
				y2 = 2;
			}
		} else {
			y2 = 1;
		}
		
		if(m.getEnd()%8 == 1) {
			x2 = 1;
		}
		if(m.getEnd()%8 == 2) {
			x2 = 3;
		}
		if(m.getEnd()%8 == 3) {
			x2 = 5;
		}
		if(m.getEnd()%8 == 4) {
			x2 = 7;
		}
		if(m.getEnd()%8 == 0) {
			x2 = 8;
		}
		if(m.getEnd()%8 == 5) {
			x2 = 2;
		}
		if(m.getEnd()%8 == 6) {
			x2 = 4;
		}
		if(m.getEnd()%8 == 7) {
			x2 = 6;
		}
		
		
	}

	// Method that should send the legality of the move played by the player back to CV
	private static void sendLegality(boolean legal) {

		// pause if move isn't legal?
		
/*		if(!legal) {
			
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
*/
	}

	// method that checks if the move a player has done is legal
	
	private static Boolean legalMove(ArrayList<Pieces> allpieces, Move m) {

		Game n = new Game();

		n.updateGameState(allpieces);

		ArrayList<Move> possibleMoves = n.findMoves(n.PlayerPieces, n.AllTiles);

		boolean legal = false;

		for (int i = 0; i < possibleMoves.size(); i++) {

			if (possibleMoves.get(i).getStart() == m.getStart() && possibleMoves.get(i).getEnd() == m.getEnd()) {

				legal = true;
			}
		}

		n = null;

		return legal;
	}

	public void WriteToJSON() throws FileNotFoundException {
		/* create an array obj :
		 JsonArray value = Json.createArrayBuilder()
         .add(Json.createObjectBuilder() use this for each different thing in array
         .add("", ""))
         .build();
		 */
		/*	JSONWriter writer = JSON.createWriter(new FileOutputStream("move Data"));
			writer.writeArray(); //name of the array obj
			writer.close();*/
	}

	//public static void readFromJSON() throws Exception {


	//}
}


