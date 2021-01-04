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
    protected void setPosition(int position) {
        if(this.busy)
            return;

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

    @Override
    protected void shake() {
        if(this.busy)
            return;

        this.busy = true;

        this.shake = new Shake(storageServo);
        this.shake.start();
    }
    @Override
    protected void shakeEnd() {
        if(this.shake == null)
            return;

        this.shake.interrupt();
        this.storageServo.setPosition(Storage.storageInitialPosition);

        this.shake = null;
        this.busy = false;
    }

    private static class Shake extends Thread {
        public Shake(Servo servo) {
            this.servo = servo;
        }

        @Override
        public void run() {
            int iteration = 0;
            while(!this.isInterrupted()) {

                System.out.println(iteration++);

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
        private static final double frequency = 3;
        private static final double initialPos = 1.0;
        private static final double extendedPos = 0.95;
    }

    private Servo liftServo;
    private Servo storageServo;
    private Storage.Shake shake;
    private boolean busy;

    //constants
    private static final double liftInitialPosition = 0.0;
    private static final double liftPos1 = 0.1;
    private static final double liftPos2 = 0.7;
    private static final double liftPos3 = .89;
    private static final double storageInitialPosition = 1.0;
    private static final double storageExtendedPosition = 0.905;
}