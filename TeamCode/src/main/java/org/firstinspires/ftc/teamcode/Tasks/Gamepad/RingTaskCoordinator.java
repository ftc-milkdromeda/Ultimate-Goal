package org.firstinspires.ftc.teamcode.Tasks.Gamepad;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotFeeder;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotIntake;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotShooter;

import RobotFunctions.TaskManager.Clock;
import RobotFunctions.TaskManager.Controller;
import RobotFunctions.TaskManager.KeyTask;

public class RingTaskCoordinator extends KeyTask {
    public RingTaskCoordinator(Clock clock, Controller controller, RobotIntake intake, RobotShooter shooter, RobotFeeder feeder) {
        super(clock, controller);

        this.intake = new IntakeTask(clock, intake);
        this.shooter = new ShooterTask(clock, controller, shooter, feeder);
        this.button = new ToggleButton(controller, clock);
    }

    @Override
    public void run() {
        if(RingTaskCoordinator.status)
            return;
        RingTaskCoordinator.status = true;

        this.intake.start();
        this.shooter.start();

        while(!super.isInterrupted()) {
            if(this.keyMapping()[0] == 0.0) {
                this.intake.runIntake();
                this.shooter.pause();
            }
            else {
                this.intake.stopIntake();
                this.shooter.proceed();
            }

            int startClock = super.clock.getCurrentState();
            while(startClock == super.clock.getCurrentState() && !super.isInterrupted());
        }

        RingTaskCoordinator.status = false;
    }

    @Override
    protected double[] keyMapping() {
        double returnArray[] = {button.getToggleState()};

        return returnArray;
    }

    private static class ToggleButton extends Toggle {
        public ToggleButton(Controller controller, Clock clock) {
            super(clock, controller);
        }

        @Override
        protected boolean testKey() {
            return 1.0 == super.controller.get_RightBumper();
        }
    }

    private static boolean status = false;

    private IntakeTask intake;
    private ShooterTask shooter;
    private ToggleButton button; //if true, then shoot mode; if false, then intake mode.
}
