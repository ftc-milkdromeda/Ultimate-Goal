package org.firstinspires.ftc.teamcode.Tasks.Gamepad;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotIntake;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

import TaskManager.Clock;
import TaskManager.KeyTask;
import Drivers.Controller;

public class IntakeTask extends KeyTask {
    protected IntakeTask(Clock clock, Controller controller, RobotIntake intake, RobotStorage storage) {
        super(clock, controller);

        this.intake = intake;
        this.isRunning = false;
        this.isActive = false;

        this.intake.enterThread(this);

        this.intake.hardStop(this);
    }

    public void runIntake() {
        if(!this.isActive)
            return;

        this.isRunning = true;
        intake.runIntake(this);
    }
    public void stopIntake() {
        if(!this.isRunning || !this.isActive)
            return;

        intake.stopIntake(this);

        this.isRunning = false;
    }

    @Override
    public void run() {
        if(IntakeTask.status)
            return;

        this.isActive = true;
        IntakeTask.status = true;

        while(!super.isInterrupted()) {
            while(!this.isRunning);

            if(this.keyMapping()[0] == 1.0) {
                this.storage.shakeEnd(this);
                this.storage.dump(this);
            }

            int startClock = super.clock.getCurrentState();
            while(super.clock.getCurrentState() == startClock);
        }

        IntakeTask.status = false;
        this.isActive = false;
    }

    @Override
    protected double[] keyMapping() {
        double returnArray[] = {
                super.controller.get_B()
        };

        return returnArray;
    }

    @Override
    protected void deconstructor() {
        this.intake.hardStop(this);
        this.intake.exitThread(this);
    }

    private static boolean status = false;

    private RobotIntake intake;
    private RobotStorage storage;
    private boolean isRunning;
    private boolean isActive;
}
