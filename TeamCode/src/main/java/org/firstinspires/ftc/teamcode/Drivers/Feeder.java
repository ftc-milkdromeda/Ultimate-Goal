package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotFeeder;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

import java.nio.file.attribute.UserDefinedFileAttributeView;

public class Feeder extends RobotFeeder {
    public Feeder(RobotStorage storage, Servo servo) {
        super(storage);
        this.servo = servo;
        this.servo.setPosition(0.975f);
    }

    @Override
    protected void feed() {
        servo.setPosition(0.98);
        servo.setPosition(0.8);
        servo.setPosition(0.98);
    }
    private Servo servo;
}