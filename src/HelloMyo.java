import com.thalmic.myo.*;
import com.thalmic.myo.enums.*;

public class HelloMyo
{
	public static void main(String args[])
	{
		Hub hub = new Hub("com.example.HelloMyo");
		
		Myo myo = hub.waitForMyo(10000);
		
		hub.addListener(new AbstractDeviceListener() {
			@Override
			public void onPose(Myo myo, long timestamp, Pose pose) {
				System.out.println(String.format("Myo switched to pose %s", pose.toString()));
			}
		});
		
		hub.setLockingPolicy(com.thalmic.myo.enums.LockingPolicy.LOCKING_POLICY_NONE);
		
		while (true)
		{
			hub.run(1000 / 20);
		}
	}
}
