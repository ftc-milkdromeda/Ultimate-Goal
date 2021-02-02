package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp(name = "Servo Tester")
public class ServoPositionTester extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        double servoValue = 0;
        double servoValue1 = 0;

        Servo testServo = hardwareMap.servo.get("servo0");
        Servo testServo1 = hardwareMap.servo.get("servo1");

        //testServo.setDirection(Servo.Direction.REVERSE);

        final double bigIncrement = 0.05;
        final double smallIncrement = 0.0015;

        testServo.setPosition(servoValue);

        super.waitForStart();

        while(super.opModeIsActive()) {
            if (super.gamepad1.b && servoValue <= 1 - bigIncrement) {
                servoValue += bigIncrement;
                while (super.gamepad1.b);
            }
            else if (super.gamepad1.a && servoValue >= bigIncrement) {
                servoValue -= bigIncrement;
                while (super.gamepad1.a);
            }
            else if (super.gamepad1.x && servoValue >= smallIncrement) {
                servoValue -= smallIncrement;
                while (super.gamepad1.x);
            }
            else if (super.gamepad1.y && servoValue <= 1 - smallIncrement) {
                servoValue += smallIncrement;
                while (super.gamepad1.y);
            }
            else if (super.gamepad2.b && servoValue1 <= 1 - bigIncrement) {
                servoValue1 += bigIncrement;
                while (super.gamepad2.b);
            }
            else if (super.gamepad2.a && servoValue1 >= bigIncrement) {
                servoValue1 -= bigIncrement;
                while (super.gamepad2.a);
            }
            else if (super.gamepad2.x && servoValue1 >= smallIncrement) {
                servoValue1 -= smallIncrement;
                while (super.gamepad2.x);
            }
            else if (super.gamepad2.y && servoValue1 <= 1 - smallIncrement) {
                servoValue1 += smallIncrement;
                while (super.gamepad2.y);
            }

            testServo.setPosition(servoValue);
            testServo1.setPosition(servoValue1);

            super.telemetry.addData("Value: ", "%.5f", servoValue);
            super.telemetry.addData("Value1: ", "%.5f", servoValue1);
            super.telemetry.update();
        }
    }

}
