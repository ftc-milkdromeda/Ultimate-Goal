package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import android.view.FocusFinder;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import Milkdromeda.RobotFunctions.MecanumWheels.Motor;

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

        this.motors[0].setDirection(DcMotorSimple.Direction.FORWARD);
        this.motors[1].setDirection(DcMotorSimple.Direction.FORWARD);
        this.motors[2].setDirection(DcMotorSimple.Direction.REVERSE);
        this.motors[3].setDirection(DcMotorSimple.Direction.REVERSE );

        super.waitForStart();

        this.motors[0].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motors[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motors[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motors[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        while(!super.gamepad1.x)
            super.telemetry.update();

        this.motors[0].setTargetPosition(3936);
        this.motors[1].setTargetPosition(3936);
        this.motors[2].setTargetPosition(3936);
        this.motors[3].setTargetPosition(3936);

        this.motors[0].setPower(0.60);
        this.motors[1].setPower(0.45);
        this.motors[2].setPower(0.54);
        this.motors[3].setPower(0.50);

        this.motors[0].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.motors[1].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.motors[2].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.motors[3].setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(super.gamepad1.x && super.opModeIsActive())
            super.telemetry.update();
    }

    DcMotor motors[];
}
