package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

@Autonomous(name="ColorTest", group="Linear Opmode")
public class ColorTest extends LinearOpMode {
    // Define a variable for our color sensor
    ColorSensor color;

    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        color = hardwareMap.get(ColorSensor.class, "color");

        // Wait for the Play button to be pressed
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Red:", color.red());
            telemetry.addData("Green:", color.green());
            telemetry.addData("Blue:", color.blue());

            telemetry.update();
        }
    }

}
