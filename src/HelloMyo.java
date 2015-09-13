import com.thalmic.myo.*;
import java.util.*;

public class HelloMyo
{		
	static ArrayList<String> a = new ArrayList(); //this is an ArrayList to hold all of the Myo's inputs
	
	static boolean b = true; //a boolean variable that keeps the Myo checking for inputs until set to false
	
	static String[] characters; //translated characters from Myo to morse
	
	public static void hello() //this method allows the Myo to check inputs based on gestures, and puts them into a
	{
		Hub hub = new Hub("com.example.HelloMyo");
		
		Myo myo = hub.waitForMyo(10000); //tells the program to check for a Myo
		
		hub.addListener(new AbstractDeviceListener()
			{
			
				@Override
				public void onPose(Myo myo, long timestamp, Pose pose) { //do all of the following things each time there is a pose
				
					//if (!pose.toString().contains("REST"))
					//	System.out.println(pose.toString());
					
					if (pose.toString().contains("_IN") || pose.toString().contains("_OUT") || pose.toString().contains("FIST")) 
					{
						a.add(pose.toString()); //if the current pose is one of the key ones above ("WAVE_IN", "WAVE_OUT", "FIST"), add it to a
						myo.notifyUserAction(); //lets the user know this has happened by vibrating the Myo
					}
						
					else if (pose.toString().contains("FIN")) //one FIN ("FINGER_SPREAD") signifies the end of a word
					{
						myo.notifyUserAction();
						if (a.get(a.size()-1).contains("FIN")) //two FINs signify the end of the input
						{
							b = false; //ends the loop below
							return;
						}

						a.add(pose.toString());
					}
				}
			}
		);
		
		hub.setLockingPolicy(com.thalmic.myo.enums.LockingPolicy.LOCKING_POLICY_NONE); //makes sure the Myo doesn't lock itself and stop inputting
		
		while (b) //the loop that keeps the Myo checking for gestures
			hub.run(1000 / 20);
	}
	
	public static void translate() //takes each input and puts it into the characters array, translating from Myo to morse code in the process
	{
		characters = new String[getArraySize()]; //calls getArraySize() to figure out how many letters and spaces there are in the input
		
		for (int i = 0; i < characters.length; i++) //sets all elements of characters to "" to avoid unwanted "null" strings
			characters[i] = "";
		
		int j = 0;
		
		for (int i = 0; i < a.size(); i++) //this loop puts each character, which may be made up of multiple inputs, into its own space in characters
		{
			if (a.get(i).contains("FIST")) //if it's the end of a letter ("FIST"), go to the next index of characters
				j++;
			else if (a.get(i).contains("FIN")) //if it's the end of a word ("FINGERS_SPREAD"), leave another spot in characters for a space
				j+=2;
			else
				characters[j] += poseToMorse(a.get(i)); //adds each input of the same character to the same index, translating to morse simultaneously
		}
		
		MorseToEnglish mTE = new MorseToEnglish(); //creates an object of the MorseToEnglish class
		
		System.out.println(mTE.convert(characters)); //converts all of the morse characters in the array into legible English
	}
	
	public static String poseToMorse(String pose) //this method converts Myo code to morse code
	{
		switch (pose)
		{
			case "Pose [type=WAVE_IN]": return ".";
			
			case "Pose [type=WAVE_OUT]": return "-";
			
			default: return "-1";
		}
	}
	
	public static int getArraySize() //figures out how big the characters array should be; how many characters and spaces are there?
	{
		int output = 1;
		
		for (int i = 0; i < a.size()-1; i++)
		{
			if (a.get(i).contains("FIST"))
				output++;
			if (a.get(i).contains("FIN"))
				output += 2;
		}
		
		return output;
	}
	
	
	public static void main(String args[]) //main method
	{ /*
		a.add("Pose [type=WAVE_IN]");
		a.add("Pose [type=WAVE_IN]");
		a.add("Pose [type=WAVE_IN]");
		a.add("Pose [type=WAVE_IN]");
		a.add("Pose [type=FIST]");
		a.add("Pose [type=WAVE_IN]");			this block of text is to allow us to test more quickly
		a.add("Pose [type=FIST]");				these inputs result in the word "hello"
		a.add("Pose [type=WAVE_IN]");
		a.add("Pose [type=WAVE_OUT]");
		a.add("Pose [type=WAVE_IN]");
		a.add("Pose [type=WAVE_IN]");
		a.add("Pose [type=FIST");
		a.add("Pose [type=WAVE_IN]");
		a.add("Pose [type=WAVE_OUT]");
		a.add("Pose [type=WAVE_IN]");
		a.add("Pose [type=WAVE_IN]");
		a.add("Pose [type=FIST]");
		a.add("Pose [type=WAVE_OUT]");
		a.add("Pose [type=WAVE_OUT]");
		a.add("Pose [type=WAVE_OUT]");
		a.add("Pose [type=FIN]");
		a.add("Pose [type=FIN]");
		*/
		hello(); //runs the hello() method
		translate(); //runs the translate() method
	}
}
