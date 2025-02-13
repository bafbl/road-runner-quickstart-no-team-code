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

    public void runAction(Action action, String nameFmt, Object... nameArgs) {
        FtcDashboard dash = FtcDashboard.getInstance();
        Canvas previewCanvas = new Canvas();
        action.preview(previewCanvas);

        boolean keepRunning = true;
        while (keepRunning && !Thread.currentThread().isInterrupted()) {
            robot.loop();
            robot.setStatus("Running action: " + String.format(nameFmt, nameArgs));
            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().getOperations().addAll(previewCanvas.getOperations());

            keepRunning = action.run(packet);

            dash.sendTelemetryPacket(packet);
        }
    }
}
