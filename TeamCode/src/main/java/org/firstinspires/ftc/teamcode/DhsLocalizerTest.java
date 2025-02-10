package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class DhsLocalizerTest extends LinearOpMode {
    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() throws InterruptedException {
        // This opmode only cares about ticks, so we're going to just call it 1 in/tick
       ThreeDeadWheelLocalizer localizer=new ThreeDeadWheelLocalizer(hardwareMap, 1.0);

        while (!isStarted())
        {
            telemetry.addLine("Waiting for start...");
            telemetry.update();
        }

        while (!isStopRequested())
        {
            telemetry.addLine(String.format("par0=%6d par1=%6d perp=%6d",
                    localizer.par0.getPositionAndVelocity().position,
                    localizer.par1.getPositionAndVelocity().position,
                    localizer.perp.getPositionAndVelocity().position));
            telemetry.update();
        }

    }
}
