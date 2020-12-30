package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotFeeder;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

import java.nio.file.attribute.UserDefinedFileAttributeView;

public class Feeder extends RobotFeeder {
    public Feeder(RobotStorage storage, Servo servo) {
        super(storage);
        this.servo = servo;
        this.servo.setPosition(Feeder.initialPosition);
    }

    @Override
    protected void feed() {
        servo.setPosition(Feeder.extendedPosition);
        long startTime = System.currentTimeMillis();

        while(System.currentTimeMillis() - startTime <= Feeder.timeExtended);

        servo.setPosition(Feeder.initialPosition);
    }

    private Servo servo;

    //constants
    private static final double initialPosition = 0.975;
    private static final double extendedPosition = 0.8;
    private static final int timeExtended = 150;
}