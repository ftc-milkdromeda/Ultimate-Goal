package org.firstinspires.ftc.teamcode.RobotFunctions;

import RobotFunctions.Units_length;

public abstract class RobotShooter {
    protected RobotShooter() {
        this.busy = false;
    }

    public abstract void runShooter(double distance, Units_length units);

    public abstract void runShooter(double distance, Units_length units, double offset);

    public boolean isBusy() {
        return this.busy;
    }

    protected boolean busy;
}
