package org.firstinspires.ftc.teamcode.RobotFunctions;

import Drivers.DriverManager;
import Drivers.Template.Driver;
import TaskManager.Task;

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
         if(!this.storage.nextRing(task) || !super.testTask(task))
            return false;

         super.busy = true;

        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime <= RobotFeeder.shootingTimeDelay);

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

    //constants
    private static final int shootingTimeDelay = 1000;
}
