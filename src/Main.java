/**
 * This is the main method of the entire project.
 * It will call all the subcomponents to execute them in sequence.
 * The subcomponenets will communicate to each other via the JSON files (ask Thibaut)
 * 
 * @author Olive
 */


import java.io.*;

public class Main
{
	public static void main(String args[]) throws IOException
	{
		// Set-up code.
		System.out.println("The Main class has been triggered...  Starting!!-- ");


		// Repeating code.
		while(true)
		{
			// Call Stefan. Computer Vision to read the curent board state.
			//Process cvStateProcess = Runtime.getRuntime().exec("python test.py");

			// Call Karim. Will calculate the AI's next move.
			//

			// Call Thibaut. Computer Vision to determine if there is obstruction of the board.
			Process cvObstrustionProcess = Runtime.getRuntime().exec("python src/motion_vision.py");

			// Call Olive. Will move the robot arm accordingly.
			//Process controlProcess = Runtime.getRuntime().exec("python test.py");
		}

		
		// Terminating code.
		// this line gives an error for some reason..!?
		//System.out.println("The while loop has been exited...  Stopping!!-- ");
	}
}

/**
 * Surplus code...

 
// Read the printed statements from python.
BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

String ret = in.readLine();
System.out.println("value is : "+ret);

 
 */