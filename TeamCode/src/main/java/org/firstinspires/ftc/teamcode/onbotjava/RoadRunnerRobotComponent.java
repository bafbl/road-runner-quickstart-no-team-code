package org.firstinspires.ftc.teamcode.onbotjava;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
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
        Pose2d pose = drive.localizer.getPose();
        telemetry.addData("RR",
                String.format("P=[%5.1f, %5.1f] %.1f deg",
                        pose.position.x,
                        pose.position.y,
                        Math.toDegrees(pose.heading.toDouble())));
    }

    public void runAction(Pose2d startPose, Action action, String nameFmt, Object... nameArgs) {
        String actionName = String.format(nameFmt, nameArgs);
        FtcDashboard dash = FtcDashboard.getInstance();
        Canvas previewCanvas = new Canvas();
        action.preview(previewCanvas);
        robot.setStatus("Running RR Action: " + actionName);

        if (startPose != null ) {
            Pose2d oldPose = drive.localizer.getPose();

//            robot.log("Updating rr position: Was [%.2f, %.2f] at %.2fdeg ---> [%.2f, %.2f] at %.2fdeg",
//                oldPose.position.x, oldPose.position.y, oldPose.heading,
//                startPose.position.x, startPose.position.y, startPose.heading);
//            robot.log("Updating rr position: Correction is %.2f distance and %.2fdeg",
//                Math.sqrt((oldPose.position.x - startPose.position.x)**2 + oldPose.position.y - startPose.position.y)**2),
//                startPose.heading.minus(oldPose.heading));

            drive.localizer.setPose(startPose);
        }

        boolean keepRunning = true;
        while (keepRunning && !Thread.currentThread().isInterrupted()) {
            robot.loop();
            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().getOperations().addAll(previewCanvas.getOperations());

            keepRunning = action.run(packet);

            dash.sendTelemetryPacket(packet);
        }
        robot.setStatus("RR Action Done: " + actionName);
    }
}
