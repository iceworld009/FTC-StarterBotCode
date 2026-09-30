package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "TankDrive TeleOp")
public class TankDriveTeleOp extends LinearOpMode {

    private DcMotor leftDrive, rightDrive, intakeMotor;
    private CRServo intakeServoL, intakeServoR;

    @Override
    public void runOpMode() {
        leftDrive = hardwareMap.get(DcMotor.class, "LD");
        rightDrive = hardwareMap.get(DcMotor.class, "RD");
        intakeMotor = hardwareMap.get(DcMotor.class, "IM");
        intakeServoL = hardwareMap.get(CRServo.class, "IL");
        intakeServoR = hardwareMap.get(CRServo.class, "IR");

        // adjust
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        intakeServoR.setDirection(CRServo.Direction.REVERSE);

        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double drive = gamepad1.left_stick_y;
            double turn = -gamepad1.right_stick_x;

            double leftPower = drive + turn;
            double rightPower = drive - turn;


            double max = Math.max(1.0, Math.max(Math.abs(leftPower), Math.abs(rightPower)));
            leftDrive.setPower(leftPower / max);
            rightDrive.setPower(rightPower / max);


            if (gamepad1.right_trigger > 0.1) {
                intakeMotor.setPower(1.0);
                intakeServoL.setPower(1.0);
                intakeServoR.setPower(1.0);
            } else if (gamepad1.left_trigger > 0.1) {
                intakeMotor.setPower(-1.0);
                intakeServoL.setPower(-1.0);
                intakeServoR.setPower(-1.0);
            } else {
                intakeMotor.setPower(0);
                intakeServoL.setPower(0);
                intakeServoR.setPower(0);
            }
            telemetry.clear();
            telemetry.addData("left", leftDrive.getPower());
            telemetry.addData("right", rightDrive.getPower());
            telemetry.update();
        }
    }
}