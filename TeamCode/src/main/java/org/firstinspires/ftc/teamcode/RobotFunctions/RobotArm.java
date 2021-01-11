package org.firstinspires.ftc.teamcode.RobotFunctions;

import Drivers.DriverManager;
import Drivers.Template.Driver;

import TaskManager.Task;

public abstract class RobotArm extends Driver {
    protected RobotArm() {
        super();

        if(RobotArm.processId != -1)
            super.alive = false;
        else
            RobotArm.processId = DriverManager.attachProcess(this);
    }

    public abstract boolean setArmPosition(Task task, int position);
    public abstract boolean setGrabberPosition(Task task, boolean open);

    private static int processId = -1;
}
