package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "ShooterTest")
public class ShooterTest extends OpMode {
    @Override
    public void init() {
        this.shooter0 = hardwareMap.get(DcMotor.class, "shooter0");
    }

    @Override
    public void loop() {
        this.shooter0.setPower(super.gamepad1.right_trigger);
    }

    private DcMotor shooter0;
}
