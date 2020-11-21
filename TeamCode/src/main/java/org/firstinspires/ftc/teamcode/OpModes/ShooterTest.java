package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "ShooterTest", group = "Robot Test")
public class ShooterTest extends OpMode {
    @Override
    public void init() {
        this.shooter0 = hardwareMap.get(DcMotor.class, "shooter0");
        this.shooter0.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.shooter0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.shooter0.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if(!super.gamepad1.x)
            return;

        this.power = !this.power;

        this.shooter0.setPower(this.power ? 0.0 : 1.0);
        while(super.gamepad1.x)
            super.telemetry.update();
    }

    private boolean power = false;
    private DcMotor shooter0;
}
