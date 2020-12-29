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
        Servo testServo = hardwareMap.servo.get("servo");

        super.telemetry.addData("Value: ", "%.2f", servoValue);
        super.telemetry.update();

        //testServo.setDirection(Servo.Direction.REVERSE);

        testServo.setPosition(servoValue);

        super.waitForStart();

        while(super.opModeIsActive()) {
            if(super.gamepad1.b && servoValue <= 0.95)
                servoValue += 0.5;
            else if(super.gamepad1.a && servoValue > 0.05)
                servoValue -= 0.5;
            else if(super.gamepad1.x &&servoValue > 0.005)
                servoValue -= 0.005;
            else if(super.gamepad1.y && servoValue <= 0.995)
                servoValue += 0.005;

            testServo.setPosition(servoValue);

            super.telemetry.addData("Value: ", "%.2f", servoValue);
            super.telemetry.update();
        }
    }
}
