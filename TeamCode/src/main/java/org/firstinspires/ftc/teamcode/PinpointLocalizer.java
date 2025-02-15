package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.onbotjava.Robot2024_AS;

import java.util.Objects;

@Config
public final class PinpointLocalizer implements Localizer {
    public static class Params {
        public double parYTicks = 0.0; // y position of the parallel encoder (in tick units)
        public double perpXTicks = 0.0; // x position of the perpendicular encoder (in tick units)
    }

    private static PinpointLocalizer sharedInstance=null;

    public static Params PARAMS = new Params();

    public final GoBildaPinpointDriver driver;
    private Pose2d txWorldPinpoint;
    private Pose2d txPinpointRobot = new Pose2d(0, 0, 0);

    // This is used if pinpoint doesn't respond
    PoseVelocity2d previousUpdateResult;

    public static PinpointLocalizer getSharedInstance(HardwareMap hardwareMap, Pose2d initialPose) {
        if (sharedInstance == null )
            sharedInstance = new PinpointLocalizer(hardwareMap, initialPose);
        return sharedInstance;
    }

    private PinpointLocalizer(HardwareMap hardwareMap, Pose2d initialPose) {
        // TODO: make sure your config has a Pinpoint device with this name
        //   see https://ftc-docs.firstinspires.org/en/latest/hardware_and_software_configuration/configuring/index.html
        driver = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        // DHS 2024-25 Robot geometry & components
        driver.setOffsets(92.075, -50.8); //these are tuned for 3110-0002-0001 Product Insight #1
        driver.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        driver.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        // TODO: reverse encoder directions if needed
        //    driver.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.REVERSED);

        driver.resetPosAndIMU();

        txWorldPinpoint = initialPose;
    }

    @Override
    public void setPose(Pose2d pose) {
        txWorldPinpoint = pose.times(txPinpointRobot.inverse());
    }

    @Override
    public Pose2d getPose() {
        return txWorldPinpoint.times(txPinpointRobot);
    }

    @Override
    public PoseVelocity2d update() {
        PoseVelocity2d result;
        driver.update();
        if (Objects.requireNonNull(driver.getDeviceStatus()) == GoBildaPinpointDriver.DeviceStatus.READY) {
            txPinpointRobot = new Pose2d(driver.getPosX() / 25.4, driver.getPosY() / 25.4, driver.getHeading());
            Vector2d worldVelocity = new Vector2d(driver.getVelX() / 25.4, driver.getVelY() / 25.4);
            Vector2d robotVelocity = Rotation2d.fromDouble(-driver.getHeading()).times(worldVelocity);
            result = new PoseVelocity2d(robotVelocity, driver.getHeadingVelocity());
            previousUpdateResult = result;
            return result;
        } else {
            if (previousUpdateResult != null) {
                System.err.printf("4232 Problem: Gobilda pinpoint status is not ready (%s), returning old data", driver.getDeviceStatus());
                return previousUpdateResult;
            }
            else {
                System.err.printf("4232 Problem: Gobilda pinpoint status is not ready (%s), returning zero data", driver.getDeviceStatus());
                return new PoseVelocity2d(new Vector2d(0, 0), 0);
            }
        }
    }
}
