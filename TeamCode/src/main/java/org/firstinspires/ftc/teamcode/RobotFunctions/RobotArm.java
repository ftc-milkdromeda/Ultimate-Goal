package org.firstinspires.ftc.teamcode.RobotFunctions;

import Drivers.DriverManager;
import Drivers.Template.Driver;

public abstract class RobotArm extends Driver {
    protected RobotArm() {
        super();

        if(RobotArm.processId != -1)
            super.alive = false;
        else
            RobotArm.processId = DriverManager.attachProcess(this);
    }

    public abstract boolean setArmPosition();
    public abstract boolean setGrabberPosition();

    private static int processId = -1;
}
