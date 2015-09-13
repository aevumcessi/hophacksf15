import com.thalmic.myo.*;
import com.thalmic.myo.enums.*;
import java.util.*;

public class HelloMyo
{		
	static ArrayList<String> a = new ArrayList();
	
	static boolean b = true;
	
	static String[] characters;
	
	public static void hello()
	{
		Hub hub = new Hub("com.example.HelloMyo");
		
		Myo myo = hub.waitForMyo(10000);
		
		hub.addListener(new AbstractDeviceListener()
			{
			
				@Override
				public void onPose(Myo myo, long timestamp, Pose pose) {
				
					if (!pose.toString().contains("REST"))
						System.out.println(pose.toString());
					
					if (pose.toString().contains("_IN") || pose.toString().contains("_OUT") || pose.toString().contains("FIST"))
					{
						a.add(pose.toString());
						myo.notifyUserAction();
					}
						
					else if (pose.toString().contains("FIN"))
					{
						myo.notifyUserAction();
						if (a.get(a.size()-1).contains("FIN"))
						{
							b = false;
							return;
						}

						a.add(pose.toString());
					}
				}
			}
		);
		
		hub.setLockingPolicy(com.thalmic.myo.enums.LockingPolicy.LOCKING_POLICY_NONE);
		
		while (b)
			hub.run(1000 / 20);
	}
	
	public static void translate()
	{
		characters = new String[getArraySize()];
		
		for (int i = 0; i < characters.length; i++)
			characters[i] = "";
		
		int j = 0;
		
		for (int i = 0; i < a.size(); i++)
		{
			if (a.get(i).contains("FIST"))
				j++;
			else if (a.get(i).contains("FIN"))
				j+=2;
			else
				characters[j] += poseToMorse(a.get(i));
		}
		
		MorseToEnglish mTE = new MorseToEnglish();
		
		System.out.println(mTE.convert(characters));
	}
	
	public static String poseToMorse(String pose)
	{
		switch (pose)
		{
			case "Pose [type=WAVE_IN]": return ".";
			
			case "Pose [type=WAVE_OUT]": return "-";
			
			default: return "-1";
		}
	}
	
	public static int getArraySize()
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
	
	
	public static void main(String args[])
	{ /*
		a.add("Pose [type=WAVE_IN]");
		a.add("Pose [type=WAVE_IN]");
		a.add("Pose [type=WAVE_IN]");
		a.add("Pose [type=WAVE_IN]");
		a.add("Pose [type=FIST]");
		a.add("Pose [type=WAVE_IN]");
		a.add("Pose [type=FIST]");
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
		hello();
		translate();
	}
}
