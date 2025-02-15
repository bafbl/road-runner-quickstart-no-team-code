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
        Pose2d grabSpecimen= finishPushing.plus(new Twist2d(new Vector2d(10,0),0));
        Pose2d readyIntoBar= new Pose2d(14,9,H_NORTH);
        Pose2d driveIntoBar= new Pose2d(38,9,H_NORTH);
        Pose2d almostnewSpecimenDrop= new Pose2d(26,9,H_NORTH);
        Pose2d almostnewSpecimenDrop2= new Pose2d(25,9,H_NORTH);
        Pose2d newSpecimenDrop= new Pose2d(29,9,H_NORTH);
        //robot.rr.runAction(beginPose, );
        robot.rr.runAction(beginPose, "specimen auto",
                robot.rr.drive.actionBuilder(beginPose)
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(-1690,false);})
                        .stopAndAdd(() -> {robot.arm.outtakeToFlat();})

                        //set A A A A A - Approach submersible
                        .stopAndAdd(() -> {robot.setStatus("Auto step A (approach submersible)");})
                        .lineToXConstantHeading(specimenDrop.position.x, new VelConstraint() {
                            @Override
                            public double maxRobotVel(@NonNull Pose2dDual<Arclength> pose2dDual, @NonNull PosePath posePath, double v) {
                                return 25;
                            }
                        })
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(-800);})
                        .stopAndAdd(() -> {robot.arm.outtakeToStart();})
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(0, false);})
                        .waitSeconds(0.25)

                        // Step B B B B B B -- Back away from submersible and move to the right
                        // Achieve afterSpecimenDrop location in X and then Y, to avoid submersible
                        .stopAndAdd(() -> {robot.setStatus("Auto step B (back away from submersible)");})
                        .lineToXConstantHeading(afterSpecimenDrop.position.x)
                        .setTangent(H_EAST)
                        .lineToYConstantHeading(afterSpecimenDrop.position.y)

                        //Step C C C C C C C - Move into pushing position
                        .stopAndAdd(() -> {robot.setStatus("Auto step C (move into pushing position)");})
                        .splineToSplineHeading(readyToPush1,H_NORTH)
                        .splineToSplineHeading(pushP2,H_EAST)

                        //set D D D D D D D - Lower arm (if not already)  and drive just short of the wall
                        // already done above: .stopAndAdd(() -> {robot.arm.setLiftHeight(0,false);})
                        .stopAndAdd(() -> {robot.setStatus("Auto step D (lower arm and push)");})
                        .stopAndAdd(() -> {robot.arm.outtakeToFlat();})
                        .splineToSplineHeading(finishPushing,H_SOUTH)
                        .waitSeconds(.25)


                        //set E E E E E E - Drive forward slowly to pickup specimen
                        .stopAndAdd(() -> {robot.setStatus("Auto step E (pick up specimen)");})
                        .splineToSplineHeading(grabSpecimen,H_SOUTH,new VelConstraint() {
                            @Override
                            public double maxRobotVel(@NonNull Pose2dDual<Arclength> pose2dDual, @NonNull PosePath posePath, double v) {
                                return 10;
                            }
                        })

                        //set F F F F F F - Lift specimen off rail and Back off the rail
                        .stopAndAdd(() -> {robot.setStatus("Auto step F (lift specimen and back away from wall)");})
                        .stopAndAdd(() -> {robot.arm.outtakeToStart();})
                        .waitSeconds(0.1)
                        .setTangent(H_NORTH)
                        .lineToXConstantHeading(4)

                        //set G G G G G G G - Race over to the submersible bar
                        .stopAndAdd(() -> {robot.setStatus("Auto step G (go to submersible)");})
                        .splineToSplineHeading(readyIntoBar,H_WEST)
                        .waitSeconds(0.25)
                        .stopAndAdd(() -> {robot.arm.outtakeToFlat();})
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(-1740,true);})

                        //set H H H H H H - Specimen-Drop position
                        .stopAndAdd(() -> {robot.setStatus("Auto step H (get ready to clip specimen)");})
                        .splineToSplineHeading(almostnewSpecimenDrop,H_NORTH)
                        .waitSeconds(0.1)

                        // set I I I I I  -  Lower and then fold up and park robot arm
                        .stopAndAdd(() -> {robot.setStatus("Auto step I (clip specimen)");})
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(-800);})
                        .stopAndAdd(() -> {robot.arm.outtakeToStart();})
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(0, false);})
                        .waitSeconds(0.25)

                        //step J J J J J J  (same place as d) - Race towards specimen pickup
                        .stopAndAdd(() -> {robot.setStatus("Auto step J (go to specimen pickup)");})
                        .setTangent(H_SOUTH)
                        .splineToSplineHeading(finishPushing,H_SOUTH)

                        //step K K K K K K K (same place as e)  - Drive forward to pick up specimen from rail
                        .stopAndAdd(() -> {robot.setStatus("Auto step K (grab specimen)");})
                        .stopAndAdd(() -> {robot.arm.outtakeToFlat();})
                        .waitSeconds(0.1)
                        .splineToSplineHeading(grabSpecimen,H_SOUTH,new VelConstraint() {
                            @Override
                            public double maxRobotVel(@NonNull Pose2dDual<Arclength> pose2dDual, @NonNull PosePath posePath, double v) {
                                return 5;
                            }
                        })

                        //set L L L L L L (same place as f), Lift specimen off rail and back away from wall
                        .stopAndAdd(() -> {robot.setStatus("Auto step L (lift specimen and back away from wall)");})
                        .stopAndAdd(() -> {robot.arm.outtakeToStart();})
                        .waitSeconds(0.1)
                        .setTangent(H_NORTH)
                        .lineToXConstantHeading(4)

                        //set M M M M M M (similar to g) - Raise arm and drive to submersible bar
                        .stopAndAdd(() -> {robot.setStatus("Auto step M (go to submersible)");})
                        .stopAndAdd(() -> {robot.arm.outtakeToFlat();})
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(-1740, false);})
                        .waitSeconds(0.25)
                        .splineToSplineHeading(readyIntoBar,H_WEST)
                        .waitSeconds(0.25)

                        // set N N N N N N - CLIP Lower arm, fold up box, and then park arm
                        .stopAndAdd(() -> {robot.setStatus("Auto step N (CLIP specimen)");})
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(-800);})
                        .stopAndAdd(() -> {robot.arm.outtakeToStart();})
                        .stopAndAdd(() -> {robot.arm.setLiftHeight(0, false);})
                        .waitSeconds(0.1)

                        // set O O O O O O - ???
                        .stopAndAdd(() -> {robot.setStatus("Auto step O (????)");})
                        .lineToXConstantHeading(-5)
                        .setTangent(H_WEST)
                        .lineToYConstantHeading(almostnewSpecimenDrop2.position.y)

                        //set P P P P P P - ???
                        .stopAndAdd(() -> {robot.setStatus("Auto step P (???)");})
                        .splineToSplineHeading(almostnewSpecimenDrop2,H_NORTH)
                        .waitSeconds(0.25)
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
