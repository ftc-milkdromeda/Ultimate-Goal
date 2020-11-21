package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "WheelTest", group = "HardwareTest")
public class WheelsTest extends OpMode {
    @Override
    public void init() {
        moters = new DcMotor[4];

        this.moters[0] = hardwareMap.get(DcMotor.class, "wheel0");
        this.moters[1] = hardwareMap.get(DcMotor.class, "wheel1");
        this.moters[2] = hardwareMap.get(DcMotor.class, "wheel2");
        this.moters[3] = hardwareMap.get(DcMotor.class, "wheel3");

        this.moters[0].setDirection(DcMotorSimple.Direction.FORWARD);
        this.moters[1].setDirection(DcMotorSimple.Direction.FORWARD);
        this.moters[2].setDirection(DcMotorSimple.Direction.REVERSE);
        this.moters[3].setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        double power = super.gamepad1.left_stick_y;
        this.moters[0].setPower(power);
        this.moters[1].setPower(power);
        this.moters[2].setPower(power);
        this.moters[3].setPower(power);
    }

    private DcMotor moters[];
}
