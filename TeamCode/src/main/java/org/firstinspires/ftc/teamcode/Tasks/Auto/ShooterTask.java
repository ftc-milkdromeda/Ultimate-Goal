package org.firstinspires.ftc.teamcode.Tasks.Auto;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotFeeder;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotShooter;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

import Milkdromeda.TaskManager.Clock;
import Milkdromeda.TaskManager.Task;

public class ShooterTask extends Task {
    public ShooterTask(Clock clock, RobotShooter shooter, RobotFeeder feeder, RobotStorage storage) {
        super(clock);
        this.shooter = shooter;
        this.feeder = feeder;
        this.storage = storage;

        this.isRunning = false;
        this.alive = false;
        this.power = 0.796;

        this.shooter.enterThread(this);
        this.feeder.enterThread(this);
    }


    @Override
    public void run() {
        this.alive = true;

        int gauge = super.clock.getCurrentState();

        while(!super.isInterrupted()) {
            if(this.isRunning) {
                this.shooter.runShooterPower(this, this.power);
                this.storage.nextRing(this);
                ShooterTask.status = true;
            }
            else {
                this.shooter.stopShooter(this);
                ShooterTask.status = false;
            }

            int startClock = super.clock.getCurrentState();
            while(super.clock.getCurrentState() == startClock && !super.isInterrupted());
        }

        this.alive = false;
    }

    public synchronized void pause() {
        if(!this.isRunning)
            return;

        this.isRunning = false;
    }
    public synchronized void proceed() {
        if(ShooterTask.status || !this.alive || this.isRunning)
            return;

        this.isRunning = true;
    }
    public void setPower(double power) {
        this.power = power;
    }
    public void shoot() {
        if(!this.isRunning || !this.alive)
            return;

        this.feeder.feedRing(this);
    }

    private static boolean status = false;
    private RobotStorage storage;
    private RobotShooter shooter;
    private RobotFeeder feeder;
    private boolean isRunning;
    private boolean alive;
    private boolean shoot;
    private double power;
}
