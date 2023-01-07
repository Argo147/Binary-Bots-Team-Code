package org.firstinspires.ftc.teamcode.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="BasicTeleOPDrive", group="Linear Opmode")
public class BasicTeleOPDrive extends LinearOpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontRight = null;
    private DcMotor frontLeft = null;
    private DcMotor backRight = null;
    private DcMotor backLeft = null;
    private DcMotor slide = null;
    private Servo leftClaw = null;
    private Servo rightClaw = null;

    private static int minimumLength = -10;
    private static int maximumLength = -4475;
    private static int shortStickLength = -1620;
    private static int mediumStickLength = -2815;
    private static int longStickLength = -4026;


    @Override
    public void runOpMode() {
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        slide = hardwareMap.get(DcMotor.class, "slide");
        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        rightClaw = hardwareMap.get(Servo.class, "rightClaw");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            double drivePower = gamepad1.left_stick_y / 2;
            double turnPower = gamepad1.right_stick_x / 2;
            double strafePower = gamepad1.left_stick_x / 2;

            int currentPosition = slide.getCurrentPosition();

            frontLeft.setPower(drivePower - turnPower - strafePower);
            frontRight.setPower(drivePower + turnPower + strafePower);
            backLeft.setPower(drivePower - turnPower + strafePower);
            backRight.setPower(drivePower + turnPower - strafePower);

            if (gamepad1.x) {
                slide.setTargetPosition(minimumLength);
                slide.setPower(1);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if (gamepad1.a) {
                slide.setTargetPosition(shortStickLength);
                slide.setPower(1);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if (gamepad1.b) {
                slide.setTargetPosition(mediumStickLength);
                slide.setPower(1);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if (gamepad1.y) {
                slide.setTargetPosition(longStickLength);
                slide.setPower(1);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if (gamepad1.right_trigger > 0 && (currentPosition - 61) >  maximumLength) {
                slide.setTargetPosition(currentPosition - 60);
                slide.setPower(0.6);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if (gamepad1.left_trigger > 0 && (currentPosition + 61) < minimumLength) {
                slide.setTargetPosition(currentPosition + 60);
                slide.setPower(0.6);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if (gamepad1.right_bumper) {
                leftClaw.setPosition(0.8);
                rightClaw.setPosition(0.2);
            }

            if (gamepad1.left_bumper) {
                leftClaw.setPosition(0.4);
                rightClaw.setPosition(0.5);
            }

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("The slide is running:", slide.isBusy());
            telemetry.update();
        }
    }

}
