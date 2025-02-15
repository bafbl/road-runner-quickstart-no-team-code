package org.firstinspires.ftc.teamcode.onbotjava;

import android.annotation.SuppressLint;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.ArrayList;
import java.util.List;

public class Robot2024_AS {
    HardwareMap hardwareMap;
    VoltageSensor voltageSensor;
    LinearOpMode opmode;
    DriveTrainMecanum_AS teamDriveTrain;
    RoadRunnerRobotComponent rr;

    public final boolean isAuto;
    TeamIMU_AS imu;
    ArmMovement_AS arm;
    String status = "";
    AprilTagComponent_AS_AS aprilTagComponent;
    OdometryComponent_AS odo;
    ArrayList<String> alerts = new ArrayList<>();
    
    List<RobotComponent_AS> robotComponents = new ArrayList<>();
    
    
    
    public Robot2024_AS(LinearOpMode opmode, boolean isAuto){
        this.isAuto = isAuto;
        this.hardwareMap = opmode.hardwareMap;
        this.opmode = opmode;
        voltageSensor = hardwareMap.voltageSensor.iterator().next();
        imu = new TeamIMU_AS(this);
        odo = new OdometryComponent_AS(this);
        if(isAuto){
             rr = new RoadRunnerRobotComponent(this);
        } else {
            teamDriveTrain = new DriveTrainMecanum_AS(this);
        }
        arm = new ArmMovement_AS(this);
        aprilTagComponent = new AprilTagComponent_AS_AS(this);
    }
    
    @SuppressLint("DefaultLocale")
    public void loop(){
        alerts.clear();
        for (RobotComponent_AS component: robotComponents) {
            component.loop();
        }

        double voltage = voltageSensor.getVoltage();
        opmode.telemetry.addData("Robot", String.format("#components: %d Voltage: %.1f", robotComponents.size(), voltage));
        if (voltage<11)
            alert(String.format("Voltage is lowish: %.1f", voltage));

        opmode.telemetry.addData("Status", status);
        opmode.telemetry.addData("ALERTS", alerts.toString());
        for (RobotComponent_AS component: robotComponents) {
            component.doTelemetry(opmode.telemetry);
        }
        opmode.telemetry.update();
    }
    
    public void setStatus(String status){
        if ( status.equals(this.status))
            return;

        System.err.printf("4232 Status Update: %s%n", status);
        this.status = status;
    }
    
    public void alert(String message) {
        System.err.printf("4232 Alert: %s%n", message);
        alerts.add(message);
    }
    
    public void teleopLoop(){
        for (RobotComponent_AS component: robotComponents) {
            component.teleopLoop(opmode.gamepad1, opmode.gamepad2);
        }
        for (RobotComponent_AS component: robotComponents) {
            component.loop();
        }
    }

    public void registerComponent(RobotComponent_AS component) {
        robotComponents.add(component);
    }
    
    public void sleep(int ms){
        long startT =  System.currentTimeMillis();
        long stopT = startT + ms;
        while (!opmode.isStopRequested() && System.currentTimeMillis()<stopT)
        {
            setStatus(String.format("Sleeping: %.0fsec remaining", 
                1.0*(stopT-System.currentTimeMillis())/1000));
            loop();
        }
    }
    public Pose2D getPosition(){
        return new Pose2D(DistanceUnit.INCH,0,0, AngleUnit.DEGREES,0);
        //return odo.currentPosition;
    }
    public Pose2D getVelocity(){
        return new Pose2D(DistanceUnit.INCH,0,0, AngleUnit.DEGREES,0);
        //return odo.currentVelocity;
    }
    public void waitForGamePad2X(String message){
        setStatus(String.format("Waiting for GP2.X: %s", message));
        while(!opmode.gamepad2.x){
            loop();
        }
         while(opmode.gamepad2.x){
            loop();
        }
    }
}

