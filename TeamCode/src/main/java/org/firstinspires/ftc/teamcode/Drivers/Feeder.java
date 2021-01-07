package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotFeeder;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

import java.nio.file.attribute.UserDefinedFileAttributeView;

public class Feeder extends RobotFeeder{
    public Feeder(RobotStorage storage, HardwareMap hardware) {
        super(storage);

        this.servo = hardware.servo.get("feeder");
        this.servo.setPosition(Feeder.initialPosition);
    }

    @Override
    protected boolean feed() {
        if(super.busy)
            return false;

        servo.setPosition(Feeder.extendedPosition);

        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime <= Feeder.timeExtended);

        servo.setPosition(Feeder.initialPosition);

        super.busy = false;

        return true;
    }

    public void terminate() {}

    private Servo servo;

    //constants
    private static final double initialPosition = 0.975;
    private static final double extendedPosition = 0.75;
    private static final int timeExtended = 200;
}