package org.firstinspires.ftc.teamcode.onbotjava;
//import org.firstinspires.ftc.teamcode.vision.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp

public class TeleOpTemplate_AS extends LinearOpMode
{
    private Robot2024_AS robot;
    //USE : returns moter power scale
    //USE : init
    public void runOpMode()
    {
        robot = new Robot2024_AS(this,false);
        
        while (!isStarted())
        {
           robot.loop();
        }
        
        while (!isStopRequested())
        {
            robot.loop();
        }
    }
}
