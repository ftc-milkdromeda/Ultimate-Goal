package org.firstinspires.ftc.teamcode.Drivers;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

public class Storage extends RobotStorage {
    public Storage(HardwareMap hardware) {
        super();
        this.liftServo = hardware.servo.get("lift"); //Lifts the storage up and down.
        this.storageServo = hardware.servo.get("storage"); //Rotates the storage box to line it up with the shooter box.

        this.liftServo.setPosition(Storage.liftInitialPosition);
        this.storageServo.setPosition(Storage.storageInitialPosition);
    }

    @Override
    public boolean setPosition(int position) {
        if(super.busy)
            return false;

        super.busy = true;

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

        super.busy = false;

        return true;
    }

    @Override
    protected boolean shake() {
        if(this.busy || this.shake != null)
            return false;

        this.busy = true;

        this.shake = new Shake(storageServo);
        this.shake.start();

        return true;
    }
    @Override
    protected boolean shakeEnd() {
        if(this.shake == null)
            return false;

        this.shake.interrupt();
        this.storageServo.setPosition(Storage.storageInitialPosition);

        this.shake = null;
        this.busy = false;

        return true;
    }

    @Override
    public void destructor() {
        this.shakeEnd();
    }

    private static class Shake extends Thread {
        public Shake(Servo servo) {
            this.servo = servo;
        }

        @Override
        public void run() {
            while(!this.isInterrupted()) {
                this.servo.setPosition(Shake.extendedPos);

                try {
                    this.sleep((long)(1000 / Shake.frequency / 2));
                }
                catch(InterruptedException e) {
                    this.servo.setPosition(Shake.initialPos);
                    return;
                }

                this.servo.setPosition(Shake.initialPos);

                try {
                    this.sleep((long)(1000 / Shake.frequency / 2));
                }
                catch(InterruptedException e) {
                    this.servo.setPosition(Shake.initialPos);
                    return;
                }
            }
        }

        private Servo servo;

        //constants
        private static final double frequency = 8;
        private static final double initialPos = 1.0;
        private static final double extendedPos = 0.99;
    }

    private Servo liftServo;
    private Servo storageServo;
    private Storage.Shake shake;
    private boolean busy;

    //constants
    private static final double liftInitialPosition = 0.0;
    private static final double liftPos1 = 0.405;
    private static final double liftPos2 = 0.7;
    private static final double liftPos3 = 1.95;

    private static final double storageInitialPosition = 1.0;
    private static final double storageExtendedPosition = 0.905;
    private static final double shakeExtendedPosition = 0.9;
}