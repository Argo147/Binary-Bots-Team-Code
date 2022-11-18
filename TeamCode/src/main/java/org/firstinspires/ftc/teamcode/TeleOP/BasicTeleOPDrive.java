package org.firstinspires.ftc.teamcode.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="BasicTeleOPDrive", group="Linear Opmode")
public class BasicTeleOPDrive extends LinearOpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontRight = null;
    private DcMotor frontLeft = null;
    private DcMotor backRight = null;
    private DcMotor backLeft = null;
    private DigitalChannel touchSensor = null;


    @Override
    public void runOpMode() {

        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        touchSensor = hardwareMap.get(DigitalChannel.class, "touchSensor");

        touchSensor.setMode(DigitalChannel.Mode.INPUT);

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            double drivePower = gamepad1.left_stick_y;
            double turnPower = gamepad1.right_stick_x;
            double strafePower = -gamepad1.left_stick_x;


            if (touchSensor.getState() != true) {
                telemetry.addData("Touch Sensor", "Is pressed");
                frontRight.setPower(1);
                frontLeft.setPower(1);
                backRight.setPower(1);
                backLeft.setPower(1);
                sleep(3000);
            }

            frontLeft.setPower(drivePower - turnPower - strafePower);
            frontRight.setPower(drivePower + turnPower + strafePower);
            backLeft.setPower(drivePower - turnPower + strafePower);
            backRight.setPower(drivePower + turnPower - strafePower);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }

}
