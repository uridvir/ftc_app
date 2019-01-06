package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Teleop Test", group = "Teleop")
public class TestTeleop extends LinearOpMode {

    ElapsedTime timer = new ElapsedTime();

    //Declare motors
    DcMotor frontLeft;
    DcMotor backLeft;
    DcMotor frontRight;
    DcMotor backRight;

    public void runOpMode(){

        //Add initial telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Finds motors
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        //Makes sure all motors turn the same way
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        //Waits until game starts then resets timer
        waitForStart();
        timer.reset();

        //During the game
        while(opModeIsActive()){

            //Gets drive value from vertical motion of left stick
            double drive = -gamepad1.left_stick_y;
            //Gets turn value from horizontal motion of right stick
            double turn = gamepad1.right_stick_x;

            //Sets power to wheels
            double leftPower = Range.clip(drive + turn, -1.0, 1.0);
            double rightPower = Range.clip(drive - turn, -1.0, 1.0);

            //Sets motor power
            frontLeft.setPower(leftPower);
            backLeft.setPower(leftPower);
            frontRight.setPower(rightPower);
            backRight.setPower(rightPower);

            //Updates telemetry with elapsed time and motor power settings
            telemetry.addData("Status", "Run Time: " + timer.toString());
            telemetry.addData("Motors", "leftPower = %.2f, rightPower = %.2f", leftPower, rightPower);
            telemetry.update();
        }
    }
}
