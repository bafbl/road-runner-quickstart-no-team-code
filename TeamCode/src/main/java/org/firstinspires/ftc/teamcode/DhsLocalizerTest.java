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
            localizer.driver.update();
            telemetry.addLine(String.format("par0=%6d par1=%6d hdg=%.3f",
                    localizer.driver.getEncoderX(),
                    localizer.driver.getEncoderY(),
                    localizer.driver.getHeading()));
            telemetry.update();
        }

    }
}
