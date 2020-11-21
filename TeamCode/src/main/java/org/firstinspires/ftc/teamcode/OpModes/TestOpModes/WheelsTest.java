package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.concurrent.ExecutionException;

@Disabled
@TeleOp(name = "WheelTest", group = "HardwareTest")
public class WheelsTest extends OpMode {
    @Override
    public void init() {
        motors = new DcMotor[4];

        this.motors[0] = hardwareMap.get(DcMotor.class, "wheel0");
        this.motors[1] = hardwareMap.get(DcMotor.class, "wheel1");
        this.motors[2] = hardwareMap.get(DcMotor.class, "wheel2");
        this.motors[3] = hardwareMap.get(DcMotor.class, "wheel3");

        this.motors[0].setDirection(DcMotorSimple.Direction.FORWARD);
        this.motors[1].setDirection(DcMotorSimple.Direction.FORWARD);
        this.motors[2].setDirection(DcMotorSimple.Direction.REVERSE);
        this.motors[3].setDirection(DcMotorSimple.Direction.REVERSE);

        this.motors[0].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.motors[0].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.motors[1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.motors[2].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.motors[3].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        double power = super.gamepad1.left_stick_y;
        this.motors[0].setPower(-power);
        this.motors[1].setPower(-power);
        this.motors[2].setPower(-power);
        this.motors[3].setPower(-power);
    }

    private DcMotor motors[];
}
