package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

public class Storage extends RobotStorage {
    public Storage(Servo liftServo, Servo storageServo, double maxAngle) {
        super();
        this.liftServo = liftServo;
        this.storageServo = storageServo;
        this.maxAngle = maxAngle;
    }

    @Override
    protected void setPosition(int position) {
        if (position == 0) {
           liftServo.setPosition(0.0);
           storageServo.setPosition(0.0);
        }


        else if (position == 1) {
            liftServo.setPosition(0.0);
            storageServo.setPosition(0.0);
        }

        else if (position == 2) {
            liftServo.setPosition(0.0);
            storageServo.setPosition(0.0);
        }

        else if (position ==3) {
            liftServo.setPosition(0.0);
            storageServo.setPosition(0.0);
        }
    }

    private Servo liftServo;
    private Servo storageServo;
    private double maxAngle;
}