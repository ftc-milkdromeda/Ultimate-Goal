package org.firstinspires.ftc.teamcode.Tasks.Auto;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotIntake;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

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
    }
    public void stopIntake() {
        if(!this.isRunning || !this.isActive)
            return;

        this.isRunning = false;
    }

    @Override
    public void run() {
        if(IntakeTask.status)
            return;

        this.isActive = true;
        IntakeTask.status = true;

        while(!super.isInterrupted()) {
            if(this.isRunning)
                this.intake.runIntake(this);
            else if(!this.isRunning)
                this.intake.stopIntake(this);

            int startClock = super.clock.getCurrentState();
            while(super.clock.getCurrentState() == startClock && !super.isInterrupted());
        }

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
