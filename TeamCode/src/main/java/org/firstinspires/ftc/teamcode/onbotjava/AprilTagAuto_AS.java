package org.firstinspires.ftc.teamcode.onbotjava;
//import org.firstinspires.ftc.teamcode.vision.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous

public class AprilTagAuto_AS extends LinearOpMode
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
            //robot.arm.outtakeToCarry();
            //robot.driveTrain.moveInDirectForDistance(0.5,0,1,10);
            robot.teamDriveTrain.driveToAprilTagGoal(0.5,0,12);
            // robot.driveTrain.moveInDirectForDistance(0.5,-1,0,18);
            // robot.driveTrain.driveToAprilTagGoal(0.5,18,0);
            // robot.driveTrain.moveInDirectForDistance(0.5,0,-1,5);
            // robot.arm.setLiftHeight(-4300);
            // robot.driveTrain.moveInDirectForDistance(0.3,0,-1,2);
            // robot.arm.outtakeToDrop();
            
            robot.sleep(60*1000);
    }   
}
