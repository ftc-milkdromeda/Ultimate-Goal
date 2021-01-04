package org.firstinspires.ftc.teamcode.RobotFunctions;

import RobotFunctions.Units_length;

public abstract class RobotShooter {
    protected RobotShooter() {
        this.busy = false;
    }

    public abstract boolean runShooter(double distance, Units_length units);
    public abstract boolean runShooter(double distance, Units_length units, double offset);

    @Deprecated
    public abstract boolean runShooter(double rpm);

    public abstract  boolean stopShooter();

    public boolean isBusy() {
        return this.busy;
    }

    protected boolean busy;
}
