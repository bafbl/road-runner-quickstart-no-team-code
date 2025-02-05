package org.firstinspires.ftc.teamcode.onbotjava;
//import org.firstinspires.ftc.teamcode.vision.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous

public class ParkRight_AS extends LinearOpMode
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
        robot.setStatus("Moving right");
        robot.teamDriveTrain.moveInDirectForDistance(.5,1,0,18);
        robot.sleep(30*1000);
    }
}
