package org.firstinspires.ftc.teamcode.RobotFunctions;

import Milkdromeda.Drivers.DriverManager;
import Milkdromeda.Drivers.Template.Driver;
import Milkdromeda.TaskManager.Task;

public abstract class RobotIntake extends Driver {
    protected  RobotIntake() {
        super();

        if(RobotIntake.processId != -1)
            super.alive = false;
        else
            RobotIntake.processId = DriverManager.attachProcess(this);
    }

    public abstract boolean runIntake(Task task);
    public abstract boolean stopIntake(Task task);
    public abstract boolean hardStop(Task task);

    @Override
    protected void destructor() {
        RobotIntake.processId = -1;
    }

    private static int processId = -1;
}
