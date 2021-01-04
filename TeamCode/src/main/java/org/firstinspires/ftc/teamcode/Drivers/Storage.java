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
        this.shake = new Storage.Shake(storageServo);

        this.shake.start();
    }
    @Override
    protected void shakeEnd() {
        this.shake.stopThread();
    }

    private static class Shake extends Thread {
        public Shake (Servo servo) {
            this.servo = servo;
        }

        @Override
        public void run() {
            while(this.interrupt == Interrupt.RUN) {
                this.servo.setPosition(Shake.extended);

                long startTime = System.currentTimeMillis();
                while(System.currentTimeMillis() - startTime <= 1 / Shake.frequency / 2) {
                    if (this.interrupt == Interrupt.STOPPED) {
                        this.servo.setPosition(Shake.initial);
                        return;
                    }
                }

                this.servo.setPosition(Shake.initial);

                startTime = System.currentTimeMillis();
                while(System.currentTimeMillis() - startTime <= 1 / Shake.frequency / 2) {
                    if (this.interrupt == Interrupt.STOPPED)
                        return;
                }
            }
        }

        public void stopThread() {
            this.interrupt = Interrupt.STOPPED;
        }

        private enum Interrupt {
            STOPPED, RUN;
        }

        private Shake.Interrupt interrupt;
        private Servo servo;

        //constants
        private static final double frequency = 2;
        private static final double extended = 0.9;
        private static final double initial = 1.0;
    }

    private Servo liftServo;
    private Servo storageServo;
    private Storage.Shake shake;

    //constants
    private static final double liftInitialPosition = 0.0;
    private static final double liftPos1 = 0.1;
    private static final double liftPos2 = 0.7;
    private static final double liftPos3 = .89;
    private static final double storageInitialPosition = 1.0;
    private static final double storageExtendedPosition = 0.905;
}