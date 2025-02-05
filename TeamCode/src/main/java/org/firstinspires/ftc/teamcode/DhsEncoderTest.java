package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;

import com.acmerobotics.roadrunner.ftc.Encoder;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class DhsEncoderTest extends LinearOpMode {
    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() throws InterruptedException {
       Encoder perp = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "left_climber")));
       Encoder par = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "right_climber")));
       par.setDirection(DcMotorSimple.Direction.REVERSE);




        Encoder frontRight = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "FrontRight")));
        Encoder frontLeft = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "FrontLeft")));
        Encoder backRight = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "BackRight")));
        Encoder backLeft = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "BackLeft")));

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);


        while (!isStarted())
        {
            telemetry.addLine("Waiting for start...");
            telemetry.update();
        }

        while (!isStopRequested())
        {
            telemetry.addLine(String.format("par=%6d perp=%6d",
                    par.getPositionAndVelocity().position,
                    perp.getPositionAndVelocity().position));
            telemetry.addLine(String.format("FrontLeft=%6d BackLeft=%6d",
                    frontLeft.getPositionAndVelocity().position,
                    backLeft.getPositionAndVelocity().position));
            telemetry.addLine(String.format("FrontRight=%6d BackRight=%6d",
                    frontRight.getPositionAndVelocity().position,
                    backRight.getPositionAndVelocity().position));
            telemetry.update();
        }

    }
}
