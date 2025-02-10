package org.firstinspires.ftc.teamcode.onbotjava;

import com.acmerobotics.dashboard.config.Config;
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

        double H_NORTH=Math.toRadians(90);
        double H_SOUTH=Math.toRadians(270);
        double H_EAST=Math.toRadians(0);
        double H_WEST=Math.toRadians(180);

        double T_FORWARD=Math.toRadians(90);
        double T_BACK=Math.toRadians(270);
        double T_RIGHT=Math.toRadians(0);
        double T_LEFT=Math.toRadians(180);

        Pose2d beginPose = new Pose2d(0, 0, H_NORTH);
        Pose2d specimenDrop = new Pose2d(25, 12, H_NORTH);
        Pose2d afterSpecimenDrop = new Pose2d(20, -12, H_NORTH);

        robot.arm.setLiftHeight(-1640,false);
        robot.arm.outtakeToFlat();
        Actions.runBlocking(
                robot.rr.drive.actionBuilder(beginPose)
                        .splineToSplineHeading(specimenDrop, 0)
                        .waitSeconds(5)
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(-800);})
                        .waitSeconds(0.5)
                        .splineToSplineHeading(afterSpecimenDrop, T_BACK)
                        .waitSeconds(5)
                        .build());

        robot.sleep(99*1000);

    }
}
