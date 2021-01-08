package org.firstinspires.ftc.teamcode.Tasks.Gamepad;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotFeeder;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotIntake;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotShooter;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

import TaskManager.Clock;
import Drivers.Controller;
import TaskManager.KeyTask;
import TaskManager.ThreadManager;

public class RingTaskCoordinator extends KeyTask {
    public RingTaskCoordinator(Clock clock, Controller controller, RobotIntake intake, RobotShooter shooter, RobotFeeder feeder, RobotStorage storage) {
        super(clock, controller);

        this.intake = new IntakeTask(clock, intake);
        this.shooter = new ShooterTask(clock, controller, shooter, feeder, storage);
        this.modeToggle = new Toggle(clock, controller) {
            @Override
            protected boolean testKey() {
                return 1.0 == super.controller.get_X();
            }
        };
    }

    @Override
    public void run() {
        if(RingTaskCoordinator.status)
            return;
        RingTaskCoordinator.status = true;

        this.intake.start();
        this.shooter.start();
        this.modeToggle.start();

        while(!super.isInterrupted()) {
            if(this.keyMapping()[0] == 0.0) {
                this.intake.runIntake();
                this.shooter.pause();
            }
            else {
                if(this.shooter.getRings() == 0)
                    this.modeToggle.setState(false);
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
        double returnArray[] = {modeToggle.getToggleState()};

        return returnArray;
    }

    @Override
    protected void deconstructor() {
        ThreadManager.stopProcess(intake.getProcessId());
        ThreadManager.stopProcess(shooter.getProcessId());
    }

    private static boolean status = false;

    private IntakeTask intake;
    private ShooterTask shooter;
    private Toggle modeToggle; //if true, then shoot mode; if false, then intake mode.
}
