package org.firstinspires.ftc.teamcode.onbotjava;

import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;

public class RoadRunnerRobotComponent extends RobotComponent_AS {
    MecanumDrive drive;

    RoadRunnerRobotComponent(Robot2024_AS _robot) {
        super(_robot);
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
    }

    @Override
    public void loop() {
        drive.updatePoseEstimate();
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("RR", drive.pose);
    }
}
