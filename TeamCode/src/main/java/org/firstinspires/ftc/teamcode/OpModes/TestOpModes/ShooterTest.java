package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Drivers.RobotFeeder;

@Disabled
@TeleOp(name = "ShooterTest", group = "Robot Test")
public class ShooterTest extends OpMode {
    @Override
    public void init() {
        this.shooter0 = hardwareMap.get(DcMotor.class, "shooter0");
        this.shooter0.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.shooter0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.shooter0.setDirection(DcMotorSimple.Direction.FORWARD);

        this.servo = hardwareMap.get(Servo.class, "servo1");

        this.feeder = new RobotFeeder(this.servo, 270);
    }

    @Override
    public void loop() {
        super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
        super.telemetry.addData("Power set: ", "%.2d", this.power);
        super.telemetry.update();

        while(!this.status) {
            if(super.gamepad1.b && this.power <= 0.95)
                this.power += 0.5;
            else if(super.gamepad1.a && this.power > 0.05)
                this.power -= 0.5;
            else if(super.gamepad1.x &&this.power > 0.005)
                this.power -= 0.005;
            else if(super.gamepad1.y && this.power <= 0.995)
                this.power += 0.005;
            else if(super.gamepad1.left_bumper)
                this.status = true;

            super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
            super.telemetry.addData("Power set: ", "%.2d", this.power);
            super.telemetry.update();
        }

        while(!super.gamepad1.left_bumper) {
            if(super.gamepad1.right_bumper)
                this.feeder.feed();

            super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
            super.telemetry.addData("Power set: ", "%.2d", this.power);
            super.telemetry.update();
        }

        super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
        super.telemetry.addData("Power set: ", "%.2d", this.power);
        super.telemetry.update();
    }

    private RobotFeeder feeder;
    private double power = 1.0;
    private boolean status = false;
    private DcMotor shooter0;
    private Servo servo;
}
