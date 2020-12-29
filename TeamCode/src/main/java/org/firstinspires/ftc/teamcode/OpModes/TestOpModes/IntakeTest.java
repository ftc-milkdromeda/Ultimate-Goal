package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class IntakeTest extends OpMode {
    @Override
    public void init() {
        this.intakeMotor = hardwareMap.dcMotor.get("intake");
        this.power = 1;
    }

    @Override
    public void loop() {
        final double bigIncrement = 0.05;
        final double smallIncrement = 0.0005;

        while(!this.status) {
            if (super.gamepad1.b && this.power <= 1 - bigIncrement) {
                this.power += bigIncrement;
                while (super.gamepad1.b) {
                    super.telemetry.addData("Value: ", "%.5f", this.power);
                    super.telemetry.update();
                }
            }
            else if (super.gamepad1.a && this.power >= bigIncrement) {
                this.power -= bigIncrement;
                while (super.gamepad1.a) {
                    super.telemetry.addData("Value: ", "%.5f", this.power);
                    super.telemetry.update();
                }
            }
            else if (super.gamepad1.x && this.power >= smallIncrement) {
                this.power -= smallIncrement;
                while (super.gamepad1.x) {
                    super.telemetry.addData("Value: ", "%.5f", this.power);
                    super.telemetry.update();
                }
            }
            else if (super.gamepad1.y && this.power <= 1 - smallIncrement) {
                this.power += smallIncrement;
                while (super.gamepad1.y) {
                    super.telemetry.addData("Value: ", "%.5f", this.power);
                    super.telemetry.update();
                }
            }
            else if(super.gamepad1.left_bumper) {
                this.status = true;
                while (super.gamepad1.left_bumper) {
                    super.telemetry.addData("Value: ", "%.5f", this.power);
                    super.telemetry.update();
                }
            }
        }

        this.intakeMotor.setPower(this.power);

        while(this.status) {
            if(super.gamepad1.left_bumper) {
                this.status = false;
                while (super.gamepad1.left_bumper) {
                    super.telemetry.addData("Value: ", "%.5f", this.power);
                    super.telemetry.update();
                }
            }
        }
    }

    private double power;
    private boolean status = false;
    private DcMotor intakeMotor;
}
