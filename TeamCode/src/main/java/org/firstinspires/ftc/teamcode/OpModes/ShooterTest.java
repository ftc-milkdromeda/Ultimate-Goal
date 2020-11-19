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
        if(!super.gamepad1.x)
            return;

        shooter0.setPower(this.power == 0.0 ? 1.0 : 0.0);

        while(super.gamepad1.x)
            System.out.println(super.gamepad1.x);
    }

    private DcMotor shooter0;
    private float power = 1.0f;
}
