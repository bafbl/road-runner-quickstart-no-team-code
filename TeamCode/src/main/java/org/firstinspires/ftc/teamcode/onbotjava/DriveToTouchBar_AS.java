package org.firstinspires.ftc.teamcode.onbotjava;
//import org.firstinspires.ftc.teamcode.vision.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous

public class DriveToTouchBar_AS extends LinearOpMode
{
    private Robot2024_AS robot;
    //USE : returns moter power scale
    //USE : init
    public void runOpMode()
    {
        robot = new Robot2024_AS(this,true);
        //Wait for play
        while (!isStarted())
        {
           robot.loop();
        }
        
        long start_t=System.currentTimeMillis();
        robot.setStatus("Moving forward");
        robot.teamDriveTrain.moveInDirectForDistance(.5,0,1,48);//forward
        robot.setStatus("turning");
        robot.teamDriveTrain.turn(-90);
        robot.setStatus("Moving right");
        robot.teamDriveTrain.moveInDirectForDistance(.5,0,1,10);
        robot.sleep(30*1000);
    }
}
