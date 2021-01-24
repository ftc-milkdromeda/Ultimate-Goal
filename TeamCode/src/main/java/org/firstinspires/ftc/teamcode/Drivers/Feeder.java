package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotFeeder;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

public class Feeder extends RobotFeeder{
    public Feeder(RobotStorage storage, HardwareMap hardware) {
        super(storage);

        this.servo = hardware.servo.get("feeder");
        this.servo.setPosition(Feeder.initialPosition);
    }

    @Override
    protected void feed() {
        servo.setPosition(Feeder.extendedPosition);

        long startTimer = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTimer <= Feeder.time);

        servo.setPosition(Feeder.initialPosition);
    }

    private Servo servo;

    //constants
    private static final double initialPosition = 0.975;
    private static final double extendedPosition = 0.75;
    private static final double time = 500;
}