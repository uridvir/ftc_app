package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

//Written by Uri Dvir for Team 8148 Alephbots 2018-2019
/*
This is a really basic teleop to let us drive the robot at the
competition on January 6th.
 */
@TeleOp(name = "BasicTeleop", group = "Teleop")
public class BasicTeleop extends LinearOpMode {

    ElapsedTime timer = new ElapsedTime();

    //Declare drivetrain motors
    DcMotor frontLeft;
    DcMotor backLeft;
    DcMotor frontRight;
    DcMotor backRight;

    //Declare arm motors
    DcMotor armLeft;
    DcMotor armRight;
    DcMotor beltMotor;

    public void runOpMode(){

        //Adjusts how sensitive the arm and belt controls are
        final double armDriveFactor = 0.30;
        final double beltDriveFactor = 0.75;

        //Add initial telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Finds motors
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        armLeft = hardwareMap.get(DcMotor.class, "armLeft");
        armRight = hardwareMap.get(DcMotor.class, "armRight");
        beltMotor = hardwareMap.get(DcMotor.class, "beltMotor");

        //Makes sure all motors turn the same way
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        armLeft.setDirection(DcMotor.Direction.FORWARD);
        armRight.setDirection(DcMotor.Direction.FORWARD);
        beltMotor.setDirection(DcMotor.Direction.REVERSE);

        //Waits until game starts then resets timer
        waitForStart();
        timer.reset();

        //In case one arm drive motor fails, this gets set to 2 to double the remaining motor's power
        double armDriveFactor2 = 1;

        //During the game
        while(opModeIsActive()){

            //Sets armDriveFactor2, pressing Y will set it to 2, and A will set to 1
            if((gamepad1.y || gamepad2.y) && armDriveFactor2 == 1) {
                armDriveFactor2 = 2;
            }
            if((gamepad1.a || gamepad2.a) && armDriveFactor2 == 2) {
                armDriveFactor2 = 1;
            }

            //Sets power to wheels
            double leftPower = gamepad1.left_stick_y;
            double rightPower = gamepad1.right_stick_y;
            double armDrive = Range.clip(armDriveFactor * armDriveFactor2 * gamepad2.left_stick_y, -1, 1);
            double beltDrive = beltDriveFactor * gamepad2.right_stick_y;
            
            //Sets motor power
            frontLeft.setPower(leftPower);
            backLeft.setPower(leftPower);
            frontRight.setPower(rightPower);
            backRight.setPower(rightPower);
            armLeft.setPower(armDrive);
            armRight.setPower(armDrive);
            beltMotor.setPower(beltDrive);

            //Updates telemetry with elapsed time and motor power settings
            telemetry.addData("Status", "Run Time: " + timer.toString());
            telemetry.addData("Motors", "leftPower = %.2f, rightPower = %.2f", leftPower, rightPower);
            telemetry.addData("Motors", "armDrive = %.2f, beltDrive = %.2f", armDrive, beltDrive);
            telemetry.update();
        }
    }
}
