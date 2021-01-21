package org.firstinspires.ftc.teamcode.RobotFunctions;

import Milkdromeda.Drivers.DriverManager;
import Milkdromeda.Drivers.Template.Driver;
import Milkdromeda.TaskManager.Task;

public abstract class RobotFeeder extends Driver {
    protected RobotFeeder(RobotStorage storage) {
        super();

        this.storage = storage;
        if(this.processId != -1)
            super.alive = false;
        else
            RobotFeeder.processId = DriverManager.attachProcess(this);
    }

    /**
     * @brief Activates the servo to feed the next ring.
     */
    protected abstract void feed();

    public boolean feedRing(Task task) {
         if(!super.testTask(task) || super.busy)
            return false;

         super.busy = true;

         if(!this.storage.nextRing(task))
             return false;

        this.feed();

        this.storage.removeRing();

        super.busy = false;

        return true;
    }

    @Override
    protected void destructor() {
        RobotFeeder.processId = -1;
    }

    private RobotStorage storage;

    private static int processId = -1;

}
