package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Drivers.Feeder;
import org.firstinspires.ftc.teamcode.Drivers.Storage;

//@Disabled
@TeleOp(name = "ShooterTest", group = "Robot Test")
public class ShooterTest extends LinearOpMode {
    private void updateTelemetry() {
        super.telemetry.addData("Status: ", this.status ? "Running" : "Stopped");
        super.telemetry.addData("Power set: ", "%.5f", this.power);
        super.telemetry.addData("# of Rings: ", "%d", this.storage.getRings());
        super.telemetry.update();
    }

    public void runOpMode() {
        this.shooter = hardwareMap.get(DcMotor.class, "shooter0");
        this.shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.shooter.setDirection(DcMotorSimple.Direction.REVERSE);

        this.liftServo = hardwareMap.servo.get("lift");
        this.storageServo = hardwareMap.servo.get("storage");
        this.storage = new Storage(liftServo, storageServo);

        this.feederServo = hardwareMap.servo.get("feeder");
        this.feeder = new Feeder(this.storage, this.feederServo);

        this.storage.setRings(3);

        this.updateTelemetry();

        super.waitForStart();

        while(super.opModeIsActive()) {
            while (!this.status) {
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
                else if (super.gamepad1.left_bumper) {
                    this.status = true;
                    while (super.gamepad1.left_bumper)
                        this.updateTelemetry();
                }
                else if (super.gamepad2.a) {
                    this.storage.setRings(3);
                    while (super.gamepad2.a) {
                        this.updateTelemetry();
                    }
                }
            }

            this.shooter.setPower(this.power);

            while (this.status) {
                if (super.gamepad1.right_bumper) {
                    this.feeder.shoot();
                    while (super.gamepad1.right_bumper)
                        updateTelemetry();
                }
                else if (super.gamepad2.a) {
                    this.storage.setRings(3);
                    this.updateTelemetry();
                }
                else if(super.gamepad1.left_trigger == 1.0)
                    this.status = false;
            }

            this.updateTelemetry();
            this.shooter.setPower(0.0);
        }
    }

    private Storage storage;
    private Feeder feeder;
    private double power = 1.0;
    private boolean status = false;
    private DcMotor shooter;
    private Servo feederServo;
    private Servo liftServo;
    private Servo storageServo;

    //increment amounts
    private static final double bigIncrement = 0.05;
    private static final double smallIncrement = 0.0005;
}
