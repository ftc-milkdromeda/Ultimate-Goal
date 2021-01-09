package org.firstinspires.ftc.teamcode.RobotFunctions;

import Drivers.DriverManager;
import Drivers.Template.Driver;
import TaskManager.Task;

public abstract class RobotStorage extends Driver {
    protected RobotStorage() {
        super();

        if(RobotStorage.processId != -1)
            super.alive = false;
        else
            RobotStorage.processId = DriverManager.attachProcess(this);
    }

    /**
     * @brief Sets the storage to specified location.
     * @param postion An integer 0 - 3, where 0 is the intake position; where 1 - 3 is each of its shooting positions.
     * @return returns true on success; false if function failed to execute.
     */
    public abstract boolean setPosition(Task task, int postion);
    public abstract boolean shake(Task task);
    public abstract boolean shakeEnd(Task task);

    public int getRings() {
        return RobotStorage.numOfRings;
    }
    public void setRings(int rings) {
        RobotStorage.numOfRings = rings;
    }
    public void addRing() {
        RobotStorage.numOfRings++;
    }
    public void removeRing() {
        RobotStorage.numOfRings--;
    }

    public boolean nextRing(Task task) {
        if(numOfRings == 0 || super.testTask(task))
            return false;

        this.setPosition(task, 4 - numOfRings);

        return true;
    }

    @Override
    protected void destructor() {
        RobotStorage.processId = -1;
    }

    private static int position;
    private static int numOfRings;
    private static int processId = -1;
}
