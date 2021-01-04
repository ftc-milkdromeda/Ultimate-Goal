package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Drivers.Feeder;
import org.firstinspires.ftc.teamcode.Drivers.Storage;

import java.util.ArrayList;

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
        this.shooter = new DcMotor[2];
        this.shooter[0] = hardwareMap.get(DcMotor.class, "shooter0");
        this.shooter[1] = hardwareMap.get(DcMotor.class, "shooter1");

        this.shooter[0].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.shooter[0].setDirection(DcMotorSimple.Direction.REVERSE);

        this.shooter[1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.shooter[1].setDirection(DcMotorSimple.Direction.REVERSE);

        this.motorVelocity = new ShooterTest.Velocity(shooter);
        this.motorVelocity.start();

        this.storage = new Storage(super.hardwareMap);
        this.feeder = new Feeder(this.storage, super.hardwareMap);
        this.storage.setRings(3);

        this.updateTelemetry();

        super.waitForStart();

        double velocity[] = this.motorVelocity.getMotorVelocity();
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
                    while (super.gamepad2.a) {
                        this.updateTelemetry(velocity);
                    }
                }
            }

            this.shooter[0].setPower(this.power);
            this.shooter[1].setPower(this.power);

            boolean isReset = false;
            while (this.status && super.opModeIsActive()) {
                if ((super.gamepad1.right_bumper || super.gamepad2.right_bumper) && isReset) {
                    velocity = this.motorVelocity.getMotorVelocity();
                    this.feeder.shoot();
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
                    this.motorVelocity.reset();
                    isReset = true;
                }

                this.updateTelemetry(velocity);
            }

            this.updateTelemetry(velocity);
            this.shooter[0].setPower(0.0);
            this.shooter[1].setPower(0.0);
        }

        this.motorVelocity.interrupt();
    }

    private static class Velocity extends Thread {
        public Velocity(DcMotor motor[]) {
            this.motor = motor;
            this.interrupt = Interrupt.CONTINUE;

            this.motorVelocity = new double[2];
            this.motorVelocity[0] = 0;
            this.motorVelocity[1] = 0;
        }


        @Override
        public void run() {
            long startTime;
            long endTime;

            int startPositions[] = {motor[0].getCurrentPosition(), motor[1].getCurrentPosition()};

            while(this.interrupt == Interrupt.CONTINUE) {
                endTime = System.currentTimeMillis();
                startTime = System.currentTimeMillis();

                startPositions[0] = motor[0].getCurrentPosition();
                startPositions[1] = motor[1].getCurrentPosition();

                while(endTime - startTime <= this.refreshSpeed) {
                    System.out.println("In timer");
                    if (this.interrupt == Interrupt.STOP)
                        return;
                    endTime = System.currentTimeMillis();
                }

                double velocity[] = { this.motor[0].getCurrentPosition() - startPositions[0], this.motor[1].getCurrentPosition() - startPositions[1] };

                velocity[0] = 60000 * velocity[0] / this.tpr / (endTime - startTime);
                velocity[1] = 60000 * velocity[1] / this.tpr / (endTime - startTime);

                this.motorVelocity[0] = (this.motorVelocity[0] * this.dataPoints + velocity[0]) / (this.dataPoints + 1);
                this.motorVelocity[1] = (this.motorVelocity[1] * this.dataPoints + velocity[1]) / (this.dataPoints + 1);
                this.dataPoints++;
            }
        }

        public synchronized void reset() {
            this.dataPoints = 0;

            this.motorVelocity[0] = 0;
            this.motorVelocity[1] = 0;
        }

        public synchronized void stopThread() {
            this.interrupt = Interrupt.STOP;
        }

        public double[] getMotorVelocity() {
            double returnArray[] = { this.motorVelocity[0], this.motorVelocity[1] };

            return returnArray;
        }

        private enum Interrupt {
            STOP, CONTINUE;
        }

        private double motorVelocity[];
        private Interrupt interrupt;
        private DcMotor motor[];
        private int dataPoints;

        //constants
        private final int refreshSpeed = 500;
        private final double tpr = 103.6 / 3;
    }

    private Storage storage;
    private Feeder feeder;
    private double power = .71;
    private boolean status = false;
    private DcMotor shooter[];
    private ShooterTest.Velocity motorVelocity;

    //increment amounts
    private static final double bigIncrement = 0.05;
    private static final double smallIncrement = 0.00025;
}
