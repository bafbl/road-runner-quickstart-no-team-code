package org.firstinspires.ftc.teamcode.onbotjava;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous

public class specimenRRAuto extends LinearOpMode{
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
        Pose2d beginPose = new Pose2d(0, 0, 0);
        robot.arm.setLiftHeight(-1640,false);
        robot.arm.outtakeToFlat();
        Actions.runBlocking(
                robot.rr.drive.actionBuilder(beginPose)
                        .strafeTo(new Vector2d(-25,-12))
                        .build());
        robot.sleep(5000);
        robot.arm.setLiftHeight(-800);
        Actions.runBlocking(
                robot.rr.drive.actionBuilder(beginPose)
                        .strafeTo(new Vector2d(-36, 48))
                        .waitSeconds(5)
                        .build());
        Actions.runBlocking(
                robot.rr.drive.actionBuilder(beginPose)
                        .strafeToConstantHeading(new Vector2d(-36, 48))
                        .waitSeconds(99)
//                        .splineTo(new Vector2d(-48,48),Math.toRadians(179))
                        .build());


        robot.sleep(99*1000);

    }
}
