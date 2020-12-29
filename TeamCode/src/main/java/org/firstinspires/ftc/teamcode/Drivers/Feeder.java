package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotFeeder;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

import java.nio.file.attribute.UserDefinedFileAttributeView;

public class Feeder extends RobotFeeder {
    public Feeder(RobotStorage storage, Servo servo, double maxAngle) {
        super(storage);
        this.servo = servo;
    }

    @Override
    protected void feed() {
        servo.setPosition(0.0);
        servo.setPosition(0.0);
    }
    private Servo servo;
}