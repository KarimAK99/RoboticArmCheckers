import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Communicator {
	static String data;
	static int[] color;
	static int[]xPos;
	static int[]yPos;
	static int[]queen;
	static int x1;
	static int x2;
	static int y1;
	static int y2;

	static Move m = new Move(0,0,false,0);
	
	public static void main(String args[]) throws Exception {
		
		Move prevmove = new Move(0,0,false,0);
		prevmove.setScore(0);
		double average = 100;
		
		readFromJSON();
		Game g = new Game();
		g.updateGameState(readAllPieces());

		boolean RobotMove = true;
		
		System.out.println(g.hasGameEnded(g.RobotPieces, g.PlayerPieces, g.AllTiles));

		while(!g.hasGameEnded(g.RobotPieces, g.PlayerPieces, g.AllTiles)) {
			
			System.in.read();
			
			if(RobotMove) {
				average = ((average + prevmove.getScore())/3) + 10;
				//Move t = g.easyAI(g.AllTiles, g.RobotPieces, g.PlayerPieces, 1);
				//Move t = g.mediumAI(g.AllTiles, g.RobotPieces, g.PlayerPieces, 1);
				//Move t = g.hardAI(g.AllTiles, g.RobotPieces, g.PlayerPieces, 1);
				Move t = g.adaptiveAI(g.findMoves(g.RobotPieces, g.AllTiles), 1, average);
				System.out.println("Move: start " + t.getStart() + " end: " + t.getEnd());
				sendMove(t);
				g.playMove(t, g.AllTiles, g.RobotPieces, g.PlayerPieces);
				g.printBoard(g.AllTiles);
				RobotMove = true;
			}

			System.in.read();
			
			ArrayList<Pieces> tempPrevious = g.AllPieces;
			ArrayList<Pieces> allpieces = readAllPieces();
			
			if(findMove(tempPrevious, allpieces)) {
			
				prevmove = m;
				
				if(legalMove(allpieces, prevmove)) {

					g.playMove(prevmove, g.AllTiles, g.RobotPieces, g.PlayerPieces);
					sendLegality(true);
					RobotMove = true;
				} else {

					//System.out.println("ERROR: Illegal move played");
					sendLegality(false);
				}
			}
			

		} 
		
		x1 = 1;
		x2 =7 ;
		y1 = 2;
		y2 = 3;
		writeToJSON();
		readFromJSON();
	//	System.out.println(Arrays.toString(color));
	//	System.out.println(Arrays.toString(xPos));
	//	System.out.println(Arrays.toString(yPos));
	//	System.out.println(Arrays.toString(queen));

	}

	// Method that should read move that player plays from CV
	private static Boolean findMove(ArrayList<Pieces> prev, ArrayList<Pieces> current) {
		
		boolean movefound = false;
		
		ArrayList<Pieces> changed = new ArrayList<Pieces>();
		ArrayList<Pieces> moved = new ArrayList<Pieces>();
		
		for(int i = 0; i < prev.size(); i++) {
			
			boolean exists = false;
			
			for(int j = 0; j < current.size(); j++) {
				
				if(prev.get(i).isRobot() == current.get(j).isRobot() && prev.get(i).isKing() == current.get(j).isKing() && prev.get(i).getLocation() == current.get(j).getLocation()) {
					
					exists = true;
				}
			}
			
			if(!exists) {
				
				changed.add(prev.get(i));
			}
		}
		
		for(int i = 0; i < current.size(); i++) {
			
			boolean exists = false;
			
			for(int j = 0; j < prev.size(); j++) {
				
				if(current.get(i).isRobot() == prev.get(j).isRobot() && current.get(i).isKing() == prev.get(j).isKing() && current.get(i).getLocation() == prev.get(j).getLocation()) {
					
					exists = true;
				}
			}
			
			if(!exists) {
				
				moved.add(current.get(i));
			}
		}
		
		if(changed.size() == 1 && moved.size() == 1) {
			
			boolean jump;
			
			if(Math.abs(changed.get(0).getLocation() - moved.get(0).getLocation()) > 5) { 
				
				jump = true;
				
			} else {
				
				jump = false;
			}
			
			m = new Move(changed.get(0).getLocation(), moved.get(0).getLocation(), jump, 0); 
			movefound = true;
		}
		
		if(changed.size() == 2) {
			
			if(changed.get(0).isRobot() == moved.get(0).isRobot()) {
				
				boolean jump;
				
				if(Math.abs(changed.get(0).getLocation() - moved.get(0).getLocation()) > 5) { 
					
					jump = true;
					
				} else {
					
					jump = false;
				}
				
				m = new Move(changed.get(0).getLocation(), moved.get(0).getLocation(), jump, 0);
				movefound = true;
			}
			
			if(changed.get(1).isRobot() == moved.get(0).isRobot()) {
				
				boolean jump;
				
				if(Math.abs(changed.get(1).getLocation() - moved.get(0).getLocation()) > 5) { 
					
					jump = true;
					
				} else {
					
					jump = false;
				}
				
				m = new Move(changed.get(1).getLocation(), moved.get(0).getLocation(), jump, 0);
				movefound = true;
			}
		}
		
		Game temp = new Game();
		temp.updateGameState(current);
		double score = temp.calculateHeuristics(2, 1, 1, 1, 2, 1, 1.5, 1, 1, temp.AllTiles, temp.RobotPieces, temp.PlayerPieces);
		m.setScore(score);
		
		
		return movefound;
	}

	// Method that should read all the current pieces on the board
	private static ArrayList<Pieces> readAllPieces() {

		ArrayList<Pieces> readPieces = new ArrayList<Pieces>();
		
		for(int i = 0; i < color.length; i++) {
			
			int location = 1;
			
			if (yPos[i] == 0 && xPos[i] == 1) {location = 29;}
			if (yPos[i] == 0 && xPos[i] == 3) {location = 30;}
			if (yPos[i] == 0 && xPos[i] == 5) {location = 31;}
			if (yPos[i] == 0 && xPos[i] == 7) {location = 32;}
			if (yPos[i] == 1 && xPos[i] == 0) {location = 25;}
			if (yPos[i] == 1 && xPos[i] == 2) {location = 26;}
			if (yPos[i] == 1 && xPos[i] == 4) {location = 27;}
			if (yPos[i] == 1 && xPos[i] == 6) {location = 28;}
			if (yPos[i] == 2 && xPos[i] == 1) {location = 21;}
			if (yPos[i] == 2 && xPos[i] == 3) {location = 22;}
			if (yPos[i] == 2 && xPos[i] == 5) {location = 23;}
			if (yPos[i] == 2 && xPos[i] == 7) {location = 24;}
			if (yPos[i] == 3 && xPos[i] == 0) {location = 17;}
			if (yPos[i] == 3 && xPos[i] == 2) {location = 18;}
			if (yPos[i] == 3 && xPos[i] == 4) {location = 19;}
			if (yPos[i] == 3 && xPos[i] == 6) {location = 20;}
			if (yPos[i] == 4 && xPos[i] == 1) {location = 13;}
			if (yPos[i] == 4 && xPos[i] == 3) {location = 14;}
			if (yPos[i] == 4 && xPos[i] == 5) {location = 15;}
			if (yPos[i] == 4 && xPos[i] == 7) {location = 16;}
			if (yPos[i] == 5 && xPos[i] == 0) {location = 9;}
			if (yPos[i] == 5 && xPos[i] == 2) {location = 10;}
			if (yPos[i] == 5 && xPos[i] == 4) {location = 11;}
			if (yPos[i] == 5 && xPos[i] == 6) {location = 12;}
			if (yPos[i] == 6 && xPos[i] == 1) {location = 5;}
			if (yPos[i] == 6 && xPos[i] == 3) {location = 6;}
			if (yPos[i] == 6 && xPos[i] == 5) {location = 7;}
			if (yPos[i] == 6 && xPos[i] == 7) {location = 8;}
			if (yPos[i] == 7 && xPos[i] == 0) {location = 1;}
			if (yPos[i] == 7 && xPos[i] == 2) {location = 2;}
			if (yPos[i] == 7 && xPos[i] == 4) {location = 3;}
			if (yPos[i] == 7 && xPos[i] == 6) {location = 4;}
			
			boolean robotPiece;
			if(color[i] == 1) {robotPiece = true;} else {robotPiece = false;}
			
			boolean kingPiece;
			if(queen[i] == 1) {kingPiece = true;} else {kingPiece = false;}
			
			Pieces p = new Pieces(robotPiece, kingPiece, location);
			readPieces.add(p);
			
			
		}
		
		return readPieces;
	}
	// Method that should send the robot move to control
	private static void sendMove(Move m) {
		
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

	public static void writeToJSON() throws FileNotFoundException {
		JSONObject move = new JSONObject();
		//put data to JSONObject
		move.put(("yOld"),y1);
		move.put(("xOld"),x1);
		move.put(("xNew"),x2);

		move.put(("yNew"),y2);
		//make data into an array, use that!! don't make it loose data
		JSONArray ja = new JSONArray();
		ja.put(move);

		JSONObject mainObj = new JSONObject();
		mainObj.put("positions", ja);

		// writing JSON to file, specify file name with src/ to write it in the src folder
		PrintWriter pw = new PrintWriter("src/moveToControl.json");
		pw.write(mainObj.toString());

		pw.flush();
		pw.close();
	}
	public static void readFromJSON() throws Exception {
		/**
		 * specify src for the file,
		 * get file and read it then makes it into 3 different arrays representing board state, i.e. player, xpos,ypos
		 * for each tiles on the board
		 */
		Object obj = new JSONParser().parse(new FileReader("src/boardData.json"));
		org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;

		org.json.simple.JSONArray solutions = (org.json.simple.JSONArray) jsonObject.get("board");
		Iterator iterator = solutions.iterator();
			while (iterator.hasNext()) {
			Object n = iterator.next();
			String test = n.toString();
			//read through JSONarray
			if (test.contains("board")){
				String ans = test.substring(test.indexOf("["),test.indexOf("]}"));
				data = ans.substring(1);
			//	System.out.println("data" + data);
			}
		}
		//remove useless char
		String[] items = data.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
		int size = items.length;
		int[] arr = new int[size];
		// creates array from data string from the file
		for(int i = 0; i < items.length;i++){
			arr[i] = Integer.parseInt(items[i]);
		}
		//3 arrays with all the data, color = player, xpos= x- position, ypos = y- position
		color = new int[size/4];
		xPos = new int[size/4];
		yPos = new int[size/4];
		queen = new int[size/4];
		int j =0; int x =1; int y = 2; int w=3;
		for(int i = 0; i< color.length;i++){
			color[i]= arr[j];
			xPos[i]=arr[x];
			yPos[i] = arr[y];
			queen[i] = arr[w];
			j+=4; x+=4; y+=4; w+=4;
		}

		
	}
}


