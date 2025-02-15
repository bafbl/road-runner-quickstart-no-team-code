package org.firstinspires.ftc.teamcode.onbotjava;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.PinpointLocalizer;

import java.util.Locale;


public class OdometryComponent_AS extends RobotComponent_AS {
    //write code here
    PinpointLocalizer localizer;
    Pose2d currentPosition;

    public OdometryComponent_AS(Robot2024_AS _robot){
        super(_robot);
         // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        IllegalArgumentException e=null;
        
        for (int i=0; i<10; i++) {
            try {
                localizer = PinpointLocalizer.getSharedInstance(hardwareMap,null);
                break;
            } catch (IllegalArgumentException e1) {
                e=e1;
                localizer = PinpointLocalizer.getSharedInstance(hardwareMap,null);
                robot.alert("Problem with odo, retrying");
                try {Thread.sleep(500);} catch (InterruptedException ee){return;}
            }
        }
        if ( localizer==null )
            throw e;
        localizer = PinpointLocalizer.getSharedInstance(hardwareMap,new Pose2d(0,0, 0));

        loop();
    }

    public void loop(){
        GoBildaPinpointDriver.DeviceStatus status = localizer.driver.getDeviceStatus();
        if (status != GoBildaPinpointDriver.DeviceStatus.READY)
            robot.alert(String.format("Gobilda Pinpoint status is not ready: %s", status.name()));

        // In autonomous, roadrunner calls update
        if (!robot.isAuto) {
            localizer.update();
        }

        currentPosition = localizer.getPose();
    }
    public void doTelemetry(Telemetry telemetry){
        String data = String.format(Locale.US, "{X: %.1f in, Y: %.1f in, H: %.1f}", 
            currentPosition.position.x, currentPosition.position.y,
            currentPosition.heading.toDouble());
        telemetry.addData("Odo position", data);
    }
    
    public void setPosition(Pose2d newPosition) {
        currentPosition = newPosition;
    }
}    


    
    
    
    
