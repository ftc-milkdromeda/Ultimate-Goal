package org.firstinspires.ftc.teamcode.Tasks.Auto;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotFeeder;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotIntake;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotShooter;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

import Drivers.Controller;
import TaskManager.Clock;
import TaskManager.Task;
import TaskManager.ThreadManager;

public class RingTaskCoordinator extends Task {
    public RingTaskCoordinator(Clock clock, RobotIntake intake, RobotShooter shooter, RobotFeeder feeder, RobotStorage storage) {
        super(clock);

        this.intake = new IntakeTask(clock, intake);
        this.shooter = new ShooterTask(clock, shooter, feeder);
        this.storage = storage;
    }

    @Override
    public void run() {
        if(RingTaskCoordinator.status)
            return;
        RingTaskCoordinator.status = true;

        this.mode = false;

        while(!super.isInterrupted()) {
            if(this.mode) {
                this.shooter.pause();

                this.storage.exitThread(this.shooter);
                this.storage.enterThread(this.intake);

                status = false;

                this.intake.runIntake();
            }
            else {
                this.intake.stopIntake();

                this.storage.exitThread(this.intake);
                this.storage.enterThread(this.shooter);

                this.storage.setRings(3);

                status = true;

                this.shooter.proceed();
            }

            if(this.storage.getRings() == 0)
                this.mode = false;

            int startClock = super.clock.getCurrentState();
            while(startClock == super.clock.getCurrentState() && !super.isInterrupted());
        }

        RingTaskCoordinator.status = false;
    }

    @Override
    protected void deconstructor() {
        this.storage.exitThread(this.intake);
        this.storage.exitThread(this.shooter);

        ThreadManager.stopProcess(intake.getProcessId());
        ThreadManager.stopProcess(shooter.getProcessId());
    }

    public void runShooter() {
        this.mode = true;
    }
    public void stopShooter() {
        this.mode = false;
    }

    private static boolean status = false;

    private IntakeTask intake;
    private ShooterTask shooter;
    private RobotStorage storage;
    private static boolean mode;
}
