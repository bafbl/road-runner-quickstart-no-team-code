package org.firstinspires.ftc.teamcode.onbotjava;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Arclength;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.PosePath;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Twist2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
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

        double H_NORTH=Math.toRadians(0);
        double H_SOUTH=Math.toRadians(180);
        double H_EAST=Math.toRadians(270);
        double H_WEST=Math.toRadians(90);

        double T_FORWARD=Math.toRadians(90);
        double T_BACK=Math.toRadians(270);
        double T_RIGHT=Math.toRadians(0);
        double T_LEFT=Math.toRadians(180);

        Pose2d beginPose = new Pose2d(0, 0, H_NORTH);
        Pose2d specimenDrop = new Pose2d(27, 12, H_NORTH);
        Pose2d afterSpecimenDrop = new Pose2d(20, -30, H_NORTH);
        Pose2d readyToPush1= new Pose2d(afterSpecimenDrop.position.x+24,-32,H_EAST);
        Pose2d pushP2= new Pose2d(readyToPush1.position.x,readyToPush1.position.y-6,H_SOUTH);
        Pose2d finishPushing=new Pose2d(-5,-40,H_SOUTH);
        Pose2d grabSpecimen= finishPushing.plus(new Twist2d(new Vector2d(9,0),0));
        Pose2d readyIntoBar= new Pose2d(14,9,H_NORTH);
        Pose2d driveIntoBar= new Pose2d(38,9,H_NORTH);
        Pose2d almostnewSpecimenDrop= new Pose2d(26,9,H_NORTH);
        Pose2d newSpecimenDrop= new Pose2d(29,9,H_NORTH);
        robot.arm.setLiftHeight(-1690,false);
        robot.arm.outtakeToFlat();
        //robot.rr.runAction(beginPose, );
        robot.rr.runAction(beginPose, "starting_drop",
                robot.rr.drive.actionBuilder(beginPose)
                        //set a
                        .lineToXConstantHeading(specimenDrop.position.x, new VelConstraint() {
                            @Override
                            public double maxRobotVel(@NonNull Pose2dDual<Arclength> pose2dDual, @NonNull PosePath posePath, double v) {
                                return 25;
                            }
                        })
                        .waitSeconds(1)
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(-800);})
                        .waitSeconds(0.5)
                        .lineToXConstantHeading(afterSpecimenDrop.position.x)
                        .setTangent(H_EAST)
                        .lineToYConstantHeading(afterSpecimenDrop.position.y)
                        //.waitSeconds(1)
                        //set b
                        .splineToSplineHeading(readyToPush1,H_NORTH)
                        //.waitSeconds(1)
                        //set c
                        .splineToSplineHeading(pushP2,H_EAST)
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(0,false);})
                        //.waitSeconds(1)
                        //set d
                        .splineToSplineHeading(finishPushing,H_SOUTH)
                        .waitSeconds(.5)
                        //set e
                        .splineToSplineHeading(grabSpecimen,H_SOUTH,new VelConstraint() {
                            @Override
                            public double maxRobotVel(@NonNull Pose2dDual<Arclength> pose2dDual, @NonNull PosePath posePath, double v) {
                                return 5;
                            }
                        })
                        .waitSeconds(0.5)
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(-1740,false);})
                        .build());
        robot.rr.runAction(null,"ready_up",
                robot.rr.drive.actionBuilder(beginPose)
                        //set f
                        .lineToXConstantHeading(4)
                        //set g
                        .splineToSplineHeading(readyIntoBar,H_NORTH)
                        .waitSeconds(0.5)
                        //.stopAndAdd(() -> {robot.arm.outtakeToStart();})
                        .build());

        //driveForwardForTime(0.5, 5000);


        robot.rr.runAction(null,"second_drop",
                robot.rr.drive.actionBuilder(beginPose)
                        //.lineToXConstantHeading(-5)
                        //.stopAndAdd(() -> {robot.arm.setLiftHeight(-1690, false);})
                        .stopAndAdd(() -> {robot.arm.outtakeToFlat();})
                        .waitSeconds(0.5)
                        //set h
                        .splineToSplineHeading(almostnewSpecimenDrop,H_NORTH)
                        .waitSeconds(0.5)
                        //.splineToSplineHeading(newSpecimenDrop,H_NORTH)
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(-800, false);})
                        .waitSeconds(0.5)
                        .build());
        robot.rr.runAction(null,"third_drop",
                robot.rr.drive.actionBuilder(almostnewSpecimenDrop)
                        .setTangent(H_SOUTH)
                        //step j (same place as d)
                        .splineToSplineHeading(finishPushing,H_SOUTH)
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(0,false);})
                        .waitSeconds(0.5)
                        //step k (same place as e)
                        .splineToSplineHeading(grabSpecimen,H_SOUTH,new VelConstraint() {
                            @Override
                            public double maxRobotVel(@NonNull Pose2dDual<Arclength> pose2dDual, @NonNull PosePath posePath, double v) {
                                return 5;
                            }
                        })
                        .waitSeconds(0.5)
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(-1740,false);})
                        .build());
    }

    public void driveForwardForTime(double power, long dur_ms) {
        long start_ms = System.currentTimeMillis();
        while ( !isStopRequested() && System.currentTimeMillis() < start_ms+dur_ms) {
            robot.rr.drive.leftBack.setPower(power);
            robot.rr.drive.rightBack.setPower(power);
            robot.rr.drive.leftFront.setPower(power);
            robot.rr.drive.rightFront.setPower(power);
        }
        robot.rr.drive.leftBack.setPower(0);
        robot.rr.drive.rightBack.setPower(0);
        robot.rr.drive.leftFront.setPower(0);
        robot.rr.drive.rightFront.setPower(0);
    }
}
