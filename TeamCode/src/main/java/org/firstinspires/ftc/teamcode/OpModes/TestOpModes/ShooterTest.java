package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Drivers.Feeder;
import org.firstinspires.ftc.teamcode.Drivers.Shooter;
import org.firstinspires.ftc.teamcode.Drivers.Storage;

import java.util.ArrayList;
/*
//@Disabled
@TeleOp(name = "ShooterTest", group = "Robot Test")
public class ShooterTest extends LinearOpMode {
    private void updateTelemetry() {
        super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
        super.telemetry.addData("Power set: ", "%.7f", this.power);
        super.telemetry.addData("# of Rings: ", "%d", this.storage.getRings());
        super.telemetry.update();
    }

    private void updateTelemetry(double motorVelocity[]) {
        super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
        super.telemetry.addData("Power set: ", "%.7f", this.power);
        super.telemetry.addData("# of Rings: ", "%d", this.storage.getRings());

        super.telemetry.addData("Motor 1: ", "%.2f", motorVelocity[0]);
        super.telemetry.addData("Motor 2: ", "%.2f", motorVelocity[1]);
        super.telemetry.addData("Motor Average: ", "%.2f", (motorVelocity[0] + motorVelocity[1]) / 2);
        super.telemetry.update();
    }

    public void runOpMode() {
        this.shooter = new Shooter(super.hardwareMap);
        this.storage = new Storage(super.hardwareMap);
        this.feeder = new Feeder(this.storage, super.hardwareMap);
        this.storage.setRings(3);

        this.updateTelemetry();

        super.waitForStart();

        double velocity[] = this.shooter.getAverageVelocity();
        while(super.opModeIsActive()) {
            while (!this.status && super.opModeIsActive()) {
                if (super.gamepad1.b && this.power <= 1 - bigIncrement) {
                    this.power += bigIncrement;
                    while (super.gamepad1.b)
                        this.updateTelemetry();
                }
                else if (super.gamepad1.a && this.power >= bigIncrement) {
                    this.power -= bigIncrement;
                    while (super.gamepad1.a)
                        this.updateTelemetry();
                }
                else if (super.gamepad1.x && this.power >= smallIncrement) {
                    this.power -= smallIncrement;
                    while (super.gamepad1.x)
                        this.updateTelemetry();
                }
                else if (super.gamepad1.y && this.power <= 1 - smallIncrement) {
                    this.power += smallIncrement;
                    while (super.gamepad1.y)
                        this.updateTelemetry();
                }
                else if (super.gamepad1.left_trigger > .75 && super.gamepad2.left_trigger > .75) {
                    this.status = true;
                }
                else if (super.gamepad2.a) {
                    this.storage.setRings(3);
                    this.storage.setPosition(0);
                    while (super.gamepad2.a) {
                        this.updateTelemetry();
                    }
                }
            }

            this.shooter.runShooterPower(this.power);

            boolean isReset = false;
            while (this.status && super.opModeIsActive()) {
                if ((super.gamepad1.right_bumper || super.gamepad2.right_bumper) && isReset) {
                    velocity = this.shooter.getAverageVelocity();
                    this.feeder.feedRing();
                    isReset = false;

                    while (super.gamepad1.right_bumper || super.gamepad2.right_bumper)
                        this.updateTelemetry(velocity);
                }
                else if (super.gamepad2.a) {
                    this.storage.setRings(3);

                    this.updateTelemetry(velocity);
                }
                else if(super.gamepad1.left_bumper || super.gamepad2.left_bumper)
                    this.status = false;
                else if(super.gamepad1.right_trigger > .75 || super.gamepad2.right_trigger > .75) {
                    shooter.resetGauge();
                    isReset = true;
                    this.updateTelemetry(velocity);
                }

                this.updateTelemetry(velocity);
            }

            this.updateTelemetry(velocity);
            this.shooter.stopShooter();
        }
    }

    private Shooter shooter;
    private Storage storage;
    private Feeder feeder;

    private double power = .74;
    private boolean status = false;

    //increment amounts
    private static final double bigIncrement = 0.05;
    private static final double smallIncrement = 0.00025;
}

 */
//todo update to fit new drivers.
