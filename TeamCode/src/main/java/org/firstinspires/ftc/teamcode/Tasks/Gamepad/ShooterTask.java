package org.firstinspires.ftc.teamcode.Tasks.Gamepad;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotFeeder;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotShooter;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

import Milkdromeda.TaskManager.Clock;
import Milkdromeda.Drivers.Controller;
import Milkdromeda.TaskManager.KeyTask;

public class ShooterTask extends KeyTask {
    public ShooterTask(Clock clock, Controller controller, RobotShooter shooter, RobotFeeder feeder, RobotStorage storage) {
        super(clock, controller);
        this.shooter = shooter;
        this.feeder = feeder;
        this.isRunning = false;
        this.alive = false;

        ShooterTask.status = false;

        this.shooter.enterThread(this);
        this.feeder.enterThread(this);
    }

    @Override
    protected double[] keyMapping() {
        double returnArray[] = { super.controller.get_RightTrigger() };

        return returnArray;
    }

    @Override
    public void run() {
        this.alive = true;

        while(!super.isInterrupted()) {
            while(!this.isRunning && !super.isInterrupted());

            if(this.keyMapping()[0] >= .8) {
                this.feeder.feedRing(this);
                while(!super.isInterrupted() && this.keyMapping()[0] >= .8);
            }

            int startClock = super.clock.getCurrentState();
            while(super.clock.getCurrentState() == startClock && !super.isInterrupted());
        }

        this.pause();
        this.alive = false;
    }

    public void pause() {
        if(!this.isRunning)
            return;

        this.shooter.stopShooter(this);
        this.shooter.resetGauge();
        this.isRunning = false;
        ShooterTask.status = false;
    }
    public void proceed() {
        if(ShooterTask.status || !this.alive || this.isRunning)
            return;

        ShooterTask.status = true;
        this.isRunning = true;

        this.shooter.runShooterPower(this, ShooterTask.power);
        this.shooter.resetGauge();
    }

    private static boolean status = false;

    private RobotShooter shooter;
    private RobotFeeder feeder;
    private boolean isRunning;
    private boolean alive;

    private static final double power = .78;
}
