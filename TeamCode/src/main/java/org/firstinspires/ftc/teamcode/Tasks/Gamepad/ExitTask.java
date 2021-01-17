package org.firstinspires.ftc.teamcode.Tasks.Gamepad;

import Milkdromeda.Drivers.Controller;
import Milkdromeda.TaskManager.KeyTask;

public class ExitTask extends KeyTask {
    public ExitTask(Controller controller) {
        super(null, controller);

        this.exit = false;
    }

    @Override
    protected double[] keyMapping() {
        double returnArray[] = {super.controller.get_Back()};
        return returnArray;
    }

    @Override
    public void run() {
        while(!super.isInterrupted()) {
            if(this.keyMapping()[0] == 1.0)
                break;
        }
        this.exit = true;
    }

    public boolean getExit() {
        return this.exit;
    }

    private boolean exit;
}
