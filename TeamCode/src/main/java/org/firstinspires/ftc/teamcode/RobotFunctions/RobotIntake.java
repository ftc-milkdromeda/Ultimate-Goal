package org.firstinspires.ftc.teamcode.RobotFunctions;

public abstract class RobotIntake {
    protected  RobotIntake() {
        this.busy = false;
    }

    public abstract void runIntake();
    public abstract void stopIntake();

    public boolean isBusy() {
        return this.busy;
    }

    protected boolean busy;
}
