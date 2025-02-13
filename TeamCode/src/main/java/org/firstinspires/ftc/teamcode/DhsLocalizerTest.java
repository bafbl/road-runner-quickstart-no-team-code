package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class DhsLocalizerTest extends LinearOpMode {
    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() throws InterruptedException {
        // This opmode only cares about ticks, so we're going to just call it 1 in/tick
       PinpointLocalizer localizer=new PinpointLocalizer(hardwareMap, new Pose2d(0,0,0));

        while (!isStarted())
        {
            telemetry.addLine("Waiting for start...");
            telemetry.update();
        }

        while (!isStopRequested())
        {
            localizer.update();
            Pose2d pose = localizer.getPose();
            telemetry.addLine(String.format("x=%8.2f y=%8.2f hdg=%.3f",
                    pose.position.x,
                    pose.position.y,
                    pose.heading));
            telemetry.update();
        }

    }
}
