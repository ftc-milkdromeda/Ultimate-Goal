package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

import TaskManager.Task;
import TaskManager.ThreadManager;

public class Storage extends RobotStorage {
    public Storage(HardwareMap hardware) {
        super();
        this.liftServo = hardware.servo.get("lift"); //Lifts the storage up and down.
        this.storageServo = hardware.servo.get("storage"); //Rotates the storage box to line it up with the shooter box.

        this.liftServo.setPosition(Storage.liftInitialPosition);
        this.storageServo.setPosition(Storage.storageInitialPosition);
    }

    @Override
    public boolean setPosition(Task task, int position) {
        if(!super.testTask(task))
            return false;

        super.busy = true;

        if (position == 0) {
           liftServo.setPosition(liftInitialPosition);
           storageServo.setPosition(storageInitialPosition);
        }
        else if (position == 1) {
            this.shakeEnd(task);
            liftServo.setPosition(liftPos1);
            storageServo.setPosition(storageExtendedPosition);
        }
        else if (position == 2) {
            this.shakeEnd(task);
            liftServo.setPosition(liftPos2);
            storageServo.setPosition(storageExtendedPosition);
        }
        else if (position == 3) {
            this.shakeEnd(task);
            liftServo.setPosition(liftPos3);
            storageServo.setPosition(storageExtendedPosition);
        }

        super.busy = false;

        return true;
    }

    @Override
    public boolean shake(Task task) {
        if(!super.testTask(task) || this.shake != null)
            return false;

        super.busy = true;

        this.shake = new Shake(storageServo);
        this.shake.start();

        return true;
    }
    @Override
    public boolean shakeEnd(Task task) {
        if(!super.testTask(task) || this.shake == null)
            return false;

        ThreadManager.stopProcess(this.shake.getProcessId());
        this.storageServo.setPosition(Storage.storageInitialPosition);

        this.shake = null;
        super.busy = false;

        return true;
    }

    @Override
    public boolean dump(Task task) {
        if(!this.setPosition(task, 0))
            return false;

        super.busy = true;
        this.storageServo.setPosition(Storage.dumpPosition);

        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime <= Storage.dumpWait);


        super.busy = false;
        this.setPosition(task, 0);

        return true;
    }

    @Override
    protected void destructor() {
        super.destructor();

        if(this.shake != null)
            ThreadManager.stopProcess(this.shake.getProcessId());

        this.storageServo.setPosition(Storage.storageInitialPosition);
    }

    private static class Shake extends Task {
        public Shake(Servo servo) {
            super(null);
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

            Shake.status = false;
        }

        private static boolean status = false;

        private Servo servo;

        //constants
        private static final double frequency = 5;
        private static final double initialPos = 1.0;
        private static final double extendedPos = 0.97;
    }

    private Servo liftServo;
    private Servo storageServo;
    private Storage.Shake shake;
    private boolean busy;

    //constants
    private static final double liftInitialPosition = 0.0;
    private static final double liftPos1 = 0.35;
    private static final double liftPos2 = 0.7;
    private static final double liftPos3 = .9985;

    private static final double storageInitialPosition = 1.0;
    private static final double storageExtendedPosition = 0.882;

    private static final double dumpPosition = 1.0;
    private static final long dumpWait = 1000;
}