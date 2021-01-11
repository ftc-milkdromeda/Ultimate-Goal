package org.firstinspires.ftc.teamcode.Tasks.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Drivers.Storage;
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
        this.modeToggle = new ToggleButton(clock, controller);
        this.storage = storage;
    }

    @Override
    public void run() {
        if(RingTaskCoordinator.status)
            return;
        RingTaskCoordinator.status = true;

        this.intake.start();
        System.out.println(this.intake.getProcessId());
        this.shooter.start();
        this.modeToggle.start();

        this.storage.enterThread(this.intake);
        this.modeToggle.setState(false);
        boolean status = true;

        while(!super.isInterrupted()) {
            if(this.keyMapping()[0] == 0.0 && status) {
                this.shooter.pause();

                this.storage.exitThread(this.shooter);
                this.storage.enterThread(this.intake);

                System.out.println(this.storage.getProcessId());

                this.storage.getProcessId();

                status = false;

                this.intake.runIntake();
            }
            else if (this.keyMapping()[0] == 1.0 && !status){
                this.intake.stopIntake();

                this.storage.exitThread(this.intake);
                this.storage.enterThread(this.shooter);

                System.out.println(this.storage.getProcessId());

                this.storage.setRings(3);

                status = true;

                this.shooter.proceed();
            }
            else if(this.shooter.getRings() == 0 && status)
                this.modeToggle.setState(false);

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
        this.storage.exitThread(this.intake);
        this.storage.exitThread(this.shooter);

        ThreadManager.stopProcess(intake.getProcessId());
        ThreadManager.stopProcess(shooter.getProcessId());

        this.modeToggle.terminate();
    }

    private static class ToggleButton extends Toggle {
        public ToggleButton(Clock clock, Controller controller) {
            super(clock, controller);
        }
        @Override
        protected boolean testKey() {
            return super.controller.get_X() == 1.0;
        }
    }
    private static boolean status = false;

    private IntakeTask intake;
    private ShooterTask shooter;
    private ToggleButton modeToggle; //if true, then shoot mode; if false, then intake mode.
    private RobotStorage storage;
}
