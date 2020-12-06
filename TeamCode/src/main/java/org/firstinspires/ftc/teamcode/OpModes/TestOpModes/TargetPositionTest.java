package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import RobotFunctions.MecanumWheels.Motor;

@Disabled
@TeleOp(name = "TargetPositionTest", group = "Robot Test")
public class TargetPositionTest extends LinearOpMode {

    public TargetPositionTest() {
        motors = new DcMotor[4];
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.motors[0] = hardwareMap.dcMotor.get("0");
        this.motors[1] = hardwareMap.dcMotor.get("1");
        this.motors[2] = hardwareMap.dcMotor.get("2");
        this.motors[3] = hardwareMap.dcMotor.get("3");

        this.motors[0].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.motors[0].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.motors[1].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.motors[2].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.motors[3].setMode(DcMotor.RunMode.RUN_TO_POSITION);

        super.waitForStart();

        while(super.gamepad1.x)
            super.telemetry.update();

        this.motors[0].setPower(1.0);
        this.motors[1].setPower(1.0);
        this.motors[2].setPower(1.0);
        this.motors[3].setPower(1.0);

        this.motors[0].setTargetPosition(3936);
        this.motors[1].setTargetPosition(3936);
        this.motors[2].setTargetPosition(3936);
        this.motors[3].setTargetPosition(3936);

        while(super.gamepad1.x || super.opModeIsActive())
            super.telemetry.update();
    }

    DcMotor motors[];
}
