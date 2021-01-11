package org.firstinspires.ftc.teamcode.Tasks.Gamepad;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotIntake;

import TaskManager.Clock;
import TaskManager.Task;

public class IntakeTask extends Task {
    protected IntakeTask(Clock clock, RobotIntake intake) {
        super(clock);

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

        while(!super.isInterrupted());

        IntakeTask.status = false;
        this.isActive = false;
    }

    @Override
    protected void deconstructor() {
        this.intake.hardStop(this);
        this.intake.exitThread(this);
    }

    private static boolean status = false;

    private RobotIntake intake;
    private boolean isRunning;
    private boolean isActive;
}
