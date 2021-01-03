package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

//@Disabled
@TeleOp(name = "Servo Tester")
public class ServoPositionTester extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        double servoValue = 0;
        Servo testServo = hardwareMap.servo.get("servo");

        super.telemetry.addData("Value: ", "%.2f", servoValue);
        super.telemetry.update();

        //testServo.setDirection(Servo.Direction.REVERSE);

        final double bigIncrement = 0.05;
        final double smallIncrement = 0.0015;

        testServo.setPosition(servoValue);

        super.waitForStart();

        while(super.opModeIsActive()) {
            if (super.gamepad1.b && servoValue <= 1 - bigIncrement) {
                servoValue += bigIncrement;
                while (super.gamepad1.b) {
                    super.telemetry.addData("Value: ", "%.5f", servoValue);
                    super.telemetry.update();
                }
            }
            else if (super.gamepad1.a && servoValue >= bigIncrement) {
                servoValue -= bigIncrement;
                while (super.gamepad1.a) {
                    super.telemetry.addData("Value: ", "%.5f", servoValue);
                    super.telemetry.update();
                }
            }
            else if (super.gamepad1.x && servoValue >= smallIncrement) {
                servoValue -= smallIncrement;
                while (super.gamepad1.x) {
                    super.telemetry.addData("Value: ", "%.5f", servoValue);
                    super.telemetry.update();
                }
            }
            else if (super.gamepad1.y && servoValue <= 1 - smallIncrement) {
                servoValue += smallIncrement;
                while (super.gamepad1.y) {
                    super.telemetry.addData("Value: ", "%.5f", servoValue);
                    super.telemetry.update();
                }
            }

            testServo.setPosition(servoValue);

            super.telemetry.addData("Value: ", "%.2f", servoValue);
            super.telemetry.update();
        }
    }

}
