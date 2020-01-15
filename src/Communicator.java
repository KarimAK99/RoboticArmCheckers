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
	static int x1;
	static int x2;
	static int y1;
	static int y2;

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
		x1 = 1;
		x2 =7 ;
		y1 = 2;
		y2 = 3;
		writeToJSON();
		readFromJSON();
		System.out.println(Arrays.toString(color));
		System.out.println(Arrays.toString(xPos));
		System.out.println(Arrays.toString(yPos));


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
				System.out.println("data" + data);
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
		color = new int[size/3];
		xPos = new int[size/3];
		yPos = new int[size/3];
		int j =0; int x =1; int y = 2;
		for(int i = 0; i< color.length;i++){
			color[i]= arr[j];
			xPos[i]=arr[x];
			yPos[i] = arr[y];
			j+=3; x+=3; y+=3;
		}
	}
}


