package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotFeeder;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

public class Feeder extends RobotFeeder {
    public Feeder(RobotStorage storage, Servo servo, int maxAngle) {
        super(storage);
    }

    @Override
    protected void feed() {
        //todo implement a feed function
    }
}
