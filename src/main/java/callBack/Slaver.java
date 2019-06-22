package callBack;

import java.util.Arrays;
import java.util.List;

public class Slaver {
    private CallBackInterface callBack;

    public Slaver(CallBackInterface callBack) {
        this.callBack = callBack;
    }

    public void dailyWork() {
        List<Integer> workingHours = Arrays.asList (1,2,3,4,5,6,7,8);
        workingHours.stream ().forEach (workingHour -> System.out.println ("    --- I finish my work on " + workingHour));

        callBack.execute ();
    }
}
