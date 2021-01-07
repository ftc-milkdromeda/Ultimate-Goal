package org.firstinspires.ftc.teamcode.RobotFunctions;

import Drivers.Template.Driver;

public abstract class RobotIntake extends Driver {
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
