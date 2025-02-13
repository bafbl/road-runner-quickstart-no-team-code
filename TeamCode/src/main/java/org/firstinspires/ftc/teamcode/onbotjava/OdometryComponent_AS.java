package org.firstinspires.ftc.teamcode.onbotjava;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;

import java.util.Locale;


public class OdometryComponent_AS extends RobotComponent_AS {
    //write code here
    GoBildaPinpointDriver odo;
    Pose2D currentPosition;
    Pose2D currentVelocity;
    
    public OdometryComponent_AS(Robot2024_AS _robot){
        super(_robot);
         // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        IllegalArgumentException e=null;
        
        for (int i=0; i<10; i++) {
            try {
                odo = hardwareMap.get(GoBildaPinpointDriver.class,"odo");
                break;
            } catch (IllegalArgumentException e1) {
                e=e1;
                odo = (GoBildaPinpointDriver) hardwareMap.get("odo");
                robot.alert("Problem with odo, retrying");
                try {Thread.sleep(500);} catch (InterruptedException ee){return;}
            }
        }
        if ( odo==null )
            throw e;
        odo = hardwareMap.get(GoBildaPinpointDriver.class,"pinpoint");
        odo.setOffsets(92.075, -50.8); //these are tuned for 3110-0002-0001 Product Insight #1
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        odo.resetPosAndIMU();

        odo.getVelocity();
        loop();
    }

    public void loop(){
        odo.update();
        currentPosition = odo.getPosition();
        currentVelocity = odo.getVelocity();
    }
    public void doTelemetry(Telemetry telemetry){
        String data = String.format(Locale.US, "{X: %.1f in, Y: %.1f in, H: %.1f}", 
            currentPosition.getX(DistanceUnit.INCH), currentPosition.getY(DistanceUnit.INCH), 
            currentPosition.getHeading(AngleUnit.DEGREES));
        telemetry.addData("Odo position", data);
    }
    
    public void setPosition(Pose2D newPosition) {
        currentPosition = newPosition;
        currentVelocity = new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0);
        odo.setPosition(newPosition);
    }
}    


    
    
    
    
