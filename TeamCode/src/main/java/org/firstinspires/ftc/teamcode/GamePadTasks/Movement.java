package org.firstinspires.ftc.teamcode.GamePadTasks;

import RobotFunctions.MecanumWheels.Drive;
import RobotFunctions.MecanumWheels.Procedure;
import RobotFunctions.MecanumWheels.RoughMecanumWheels;
import RobotFunctions.TaskManager.Clock;
import RobotFunctions.TaskManager.Controller;
import RobotFunctions.TaskManager.JoyStick;
import RobotFunctions.TaskManager.JoyStickTask;
import RobotFunctions.Units_length;

public class Movement extends JoyStickTask {
    public Movement(Clock clock, Controller controller, Drive drive) {
        super(clock, controller);

        this.drive = drive;
    }
    @Override
    protected JoyStick[] joyStickMapping() {
        JoyStick returnValue[] = { super.controller.get_LeftStick(), super.controller.get_RightStick() };
        return returnValue;
    }

    @Override
    public void run() {
        if(super.status)
            return;

        super.status = true;

        this.controller = RoughMecanumWheels.instance(this.drive, 18, 18, Units_length.IN);
        if(this.controller == null)
            return;

        Procedure procedure;

        while(!super.isInterrupted()) {
            int startClock = super.clock.getCurrentState();

            JoyStick[] controllerState = this.joyStickMapping();

            if(controllerState[0].getMagnitude() == 0)
                procedure = new Procedure(0, controllerState[1].X, -1);
            else
                procedure = new Procedure(controllerState[0].getAngle(), controllerState[0].getMagnitude(), controllerState[1].X);

            controller.addTrajectory(procedure);
            controller.drive();

            while(super.clock.getCurrentState() != startClock && !super.isInterrupted());
        }
    }

    @Override
    protected void deconstructor() {
        this.controller.stop();
        this.controller.deleteObject();
    }

    private Drive drive;
    private RoughMecanumWheels controller;
}


