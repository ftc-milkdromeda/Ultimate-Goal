package org.firstinspires.ftc.teamcode.Tasks.Gamepad;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotIntake;

import RobotFunctions.TaskManager.Clock;
import RobotFunctions.TaskManager.Task;

public class IntakeTask extends Task {
    protected IntakeTask(Clock clock, RobotIntake intake) {
        super(clock);

        this.intake = intake;
        this.isRunning = false;
    }

    public void runIntake() {
        if(IntakeTask.status && !this.isRunning)
            return;

        intake.runIntake();
    }
    public void stopIntake() {
        if(IntakeTask.status && !this.isRunning)
            return;

        intake.stopIntake();
    }

    @Override
    public void run() {
        if(IntakeTask.status)
            return;

        IntakeTask.status = true;
        this.isRunning = true;

        while(!super.isInterrupted());

        IntakeTask.status = false;
        this.isRunning = false;
    }

    private static boolean status = false;

    private boolean isRunning;
    private RobotIntake intake;
}
