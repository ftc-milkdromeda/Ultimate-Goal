package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Drivers.Feeder;
import org.firstinspires.ftc.teamcode.Drivers.Storage;

@Disabled
@TeleOp(name = "ShooterTest", group = "Robot Test")
public class ShooterTest extends OpMode {
    @Override
    public void init() {
        this.shooter0 = hardwareMap.get(DcMotor.class, "shooter0");
        this.shooter0.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.shooter0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.shooter0.setDirection(DcMotorSimple.Direction.FORWARD);

        this.liftServo = hardwareMap.get(Servo.class, "lift");
        this.liftServo = hardwareMap.get(Servo.class, "storage");
        this.storage = new Storage(liftServo, storageServo);

        this.feederServo = hardwareMap.get(Servo.class, "feeder");
        this.feeder = new Feeder(this.storage, this.feederServo);

        this.liftServo.setPosition(0);
        this.feederServo.setPosition(0);
        this.storageServo.setPosition(0);

        this.storage.setRings(3);
    }

    @Override
    public void loop() {
        super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
        super.telemetry.addData("Power set: ", "%.2d", this.power);
        super.telemetry.addData("# of Rings: ", "%n", this.storage.getRings());
        super.telemetry.update();

        while(!this.status) {
            if(super.gamepad1.b && this.power <= 0.95) {
                this.power += 0.05;
                while(super.gamepad1.b) {
                    super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
                    super.telemetry.addData("Power set: ", "%.2d", this.power);
                    super.telemetry.addData("# of Rings: ", "%n", this.storage.getRings());
                    super.telemetry.update(); }
            }
            else if(super.gamepad1.a && this.power >= 0.05) {
                this.power -= 0.05;
                while(super.gamepad1.a) {
                    super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
                    super.telemetry.addData("Power set: ", "%.2d", this.power);
                    super.telemetry.addData("# of Rings: ", "%n", this.storage.getRings());
                    super.telemetry.update();
                }
            }
            else if(super.gamepad1.x && this.power >= 0.0005) {
                this.power -= 0.0005;
                while(super.gamepad1.x) {
                    super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
                    super.telemetry.addData("Power set: ", "%.2d", this.power);
                    super.telemetry.addData("# of Rings: ", "%n", this.storage.getRings());
                    super.telemetry.update();
                }
            }
            else if(super.gamepad1.y && this.power <= 0.9995) {
                this.power += 0.0005;
                while(super.gamepad1.y) {
                    super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
                    super.telemetry.addData("Power set: ", "%.2d", this.power);
                    super.telemetry.addData("# of Rings: ", "%n", this.storage.getRings());
                    super.telemetry.update();
                }
            }
            else if(super.gamepad1.left_bumper) {
                this.status = true;
                while(super.gamepad1.left_bumper) {
                    super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
                    super.telemetry.addData("Power set: ", "%.2d", this.power);
                    super.telemetry.addData("# of Rings: ", "%n", this.storage.getRings());
                    super.telemetry.update();
                }
            }
            else if(super.gamepad2.a) {
                this.storage.setRings(3);
                while(super.gamepad2.a) {
                    super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
                    super.telemetry.addData("Power set: ", "%.2d", this.power);
                    super.telemetry.addData("# of Rings: ", "%n", this.storage.getRings());
                    super.telemetry.update();
                }
            }

        }

        while(!super.gamepad1.left_bumper) {
            if(super.gamepad1.right_bumper) {
                this.feeder.shoot();

                while(super.gamepad1.left_bumper) {
                    super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
                    super.telemetry.addData("Power set: ", "%.2d", this.power);
                    super.telemetry.addData("# of Rings: ", "%n", this.storage.getRings());
                    super.telemetry.update();
                }
            }
            else if(super.gamepad2.a)
                this.storage.setRings(3);

            super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
            super.telemetry.addData("Power set: ", "%.2d", this.power);
            super.telemetry.addData("# of Rings: ", "%n", this.storage.getRings());
            super.telemetry.update();
        }
    }

    private Storage storage;
    private Feeder feeder;
    private double power = 1.0;
    private boolean status = false;
    private DcMotor shooter0;
    private Servo feederServo;
    private Servo liftServo;
    private Servo storageServo;
}
