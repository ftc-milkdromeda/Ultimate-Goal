package org.firstinspires.ftc.teamcode.Tasks.Auto;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotFeeder;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotIntake;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotShooter;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

import Milkdromeda.TaskManager.Clock;
import Milkdromeda.TaskManager.Task;
import Milkdromeda.TaskManager.ThreadManager;

public class RingTaskCoordinator extends Task {
    public RingTaskCoordinator(Clock clock, RobotIntake intake, RobotShooter shooter, RobotFeeder feeder, RobotStorage storage) {
        super(clock);

        this.intake = new IntakeTask(clock, intake);
        this.shooter = new ShooterTask(clock, shooter, feeder, storage);
        this.storage = storage;
        this.mode = false;
        this.power = .79;
    }

    @Override
    public void run() {
        if(RingTaskCoordinator.status)
            return;
        RingTaskCoordinator.status = true;

        this.intake.start();
        this.shooter.start();

        while(!super.isInterrupted()) {
            if(!this.mode) {
                this.shooter.pause();

                this.storage.exitThread(this.shooter);
                this.storage.enterThread(this.intake);

                this.intake.runIntake();
            }
            else {
                this.intake.stopIntake();

                this.storage.exitThread(this.intake);
                this.storage.enterThread(this.shooter);

                this.shooter.setPower(this.power);
                this.shooter.proceed();
            }

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
    public void shoot() {
        this.shooter.shoot();
    }

    public void setPower(double power) {
        this.power = power;
    }

    private static boolean status = false;

    private IntakeTask intake;
    private ShooterTask shooter;
    private RobotStorage storage;
    private boolean mode;
    private double power;
}
