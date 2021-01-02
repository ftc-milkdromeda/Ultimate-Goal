package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

public class Storage extends RobotStorage {
    public Storage(Servo liftServo, Servo storageServo) {
        super();
        this.liftServo = liftServo; //Lifts the storage up and down.
        this.storageServo = storageServo; //Rotates the storage box to line it up with the shooter box.

        this.liftServo.setPosition(Storage.liftInitialPosition);
        this.storageServo.setPosition(Storage.storageInitialPosition);
    }

    @Override
    protected void setPosition(int position) {
        if (position == 0) {
           liftServo.setPosition(liftInitialPosition);
           storageServo.setPosition(storageInitialPosition);
        }
        else if (position == 1) {
            liftServo.setPosition(liftPos1);
            storageServo.setPosition(storageExtendedPosition);
        }
        else if (position == 2) {
            liftServo.setPosition(liftPos2);
            storageServo.setPosition(storageExtendedPosition);
        }
        else if (position == 3) {
            liftServo.setPosition(liftPos3);
            storageServo.setPosition(storageExtendedPosition);
        }
    }

    private Servo liftServo;
    private Servo storageServo;

    //constants
    private static final double liftInitialPosition = 0.0;
    private static final double liftPos1 = 0.1;
    private static final double liftPos2 = 0.7;
    private static final double liftPos3 = 1.95;
    private static final double storageInitialPosition = 1.0;
    private static final double storageExtendedPosition = 0.905;
}