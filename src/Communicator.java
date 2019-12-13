import java.util.ArrayList;
// import java.util.concurrent.TimeUnit;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileOutputStream;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonWriter;
public class Communicator {

	public static void main(String args[]) {
		
		Game g = new Game();
		
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
			
		}
		
	}
	
	// Method that should read move that player plays from CV
	private static Move readMove() {
		
		return null;
	}
	
	// Method that should read all the current pieces on the board
	private static ArrayList<Pieces> readAllPieces(){
		
		return null;
	}
	
	// Method that should send the robot move to control
	private static void sendMove(Move m) {
		
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
	public void WriteToJSON(){
		/* create an array obj :
		 JsonArray value = Json.createArrayBuilder()
         .add(Json.createObjectBuilder() use this for each different thing in array
         .add("", ""))
         .build();
		 */
			JsonWriter writer = Json.createWriter(new FileOutputStream("move Data"));
			writer.writeArray() //name of the array obj
			writer.close();
		}
	public void readFromJSON(){
		Object obj = parser.parse(new FileReader("E:\\json.txt"));

		JSONObject jsonObject = (JSONObject) obj;

		System.out.println(jsonObject.get("move Data"));

		JSONArray solutions = (JSONArray) jsonObject.get("move Data");

		Iterator iterator = solutions.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

	}

	public class readFromJSON{

	}
}
