package org.firstinspires.ftc.teamcode.onbotjava;
//import org.firstinspires.ftc.teamcode.vision.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous

public class AutoDropBlockAndPark_AS extends LinearOpMode
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
            robot.arm.outtakeToCarry();
            //these move to the target area
            robot.teamDriveTrain.moveInDirectForDistance(0.5,0,1,10);
            robot.teamDriveTrain.moveInDirectForDistance(0.5,-1,0,18);
            //these get into position to drop
            robot.teamDriveTrain.turnCW(45); //clockwise turn
            robot.teamDriveTrain.moveInDirectForDistance(0.5,0,-1,5);
            //move arm and drop
            robot.arm.setLiftHeight(-4300);
            robot.teamDriveTrain.moveInDirectForDistance(0.3,0,-1,3.5);
            robot.arm.outtakeToDrop();
            robot.sleep(2500); //pause for time
            //rest arm and correct angle
            robot.arm.outtakeToStart();
            robot.arm.setLiftHeight(-500);
            robot.teamDriveTrain.turnCCW(45); //counter Clockwise
            //moves to the side and moves forward
            robot.teamDriveTrain.moveInDirectForDistance(0.5,1,0,15);
            robot.teamDriveTrain.moveInDirectForDistance(0.5,0,1,45);
            //turn, backup, and move the bucket to touch
            robot.teamDriveTrain.turnCCW(90);
            robot.arm.setLiftHeight(-2000);
            robot.teamDriveTrain.moveInDirectForDistance(0.5,0,-1,15);
            robot.arm.setLiftHeight(-1500);
            robot.arm.outtakeToDrop();
            robot.sleep(60*1000);
    }
}

