package org.firstinspires.ftc.teamcode.onbotjava;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous

public class TestRRAuto extends LinearOpMode{
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
        Actions.runBlocking(
                robot.rr.drive.actionBuilder(beginPose)
                        .splineTo(new Vector2d(48, 48), Math.PI / 2)
                        .waitSeconds(2)
                        .splineTo(new Vector2d(0, 96), Math.PI)
                        .waitSeconds(2)
                        .splineTo(new Vector2d(-48,48),3*Math.PI/2)
                        .waitSeconds(2)
                        .splineTo(new Vector2d(0,0),0)
                        .build());
    }
}
