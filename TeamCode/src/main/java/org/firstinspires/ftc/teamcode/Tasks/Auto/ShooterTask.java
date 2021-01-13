package org.firstinspires.ftc.teamcode.Tasks.Auto;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotFeeder;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotShooter;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;

import TaskManager.Clock;
import TaskManager.Task;

public class ShooterTask extends Task {
    public ShooterTask(Clock clock, RobotShooter shooter, RobotFeeder feeder) {
        super(clock);
        this.shooter = shooter;

        this.isRunning = false;
        this.alive = false;

        this.shooter.enterThread(this);
        this.feeder.enterThread(this);
    }


    @Override
    public void run() {
        this.alive = true;

        int gauge = super.clock.getCurrentState();

        while(!super.isInterrupted()) {
            if(this.shoot)
                this.feeder.feedRing(this);
            if(this.isRunning)
                this.shooter.runShooterPower(this, .78);
            else
                this.shooter.stopShooter(this);

            if(super.clock.getCurrentState() - gauge >= ShooterTask.gaugeRefresh)
                this.shooter.resetGauge();

            int startClock = super.clock.getCurrentState();
            while(super.clock.getCurrentState() == startClock && !super.isInterrupted());
        }

        this.alive = false;
    }

    public void pause() {
        if(!this.isRunning)
            return;

        this.isRunning = false;
        ShooterTask.status = false;
    }
    public void proceed() {
        if(ShooterTask.status || !this.alive || this.isRunning)
            return;

        ShooterTask.status = true;
        this.isRunning = true;
    }
    public void shoot() {
        if(!this.isRunning || this.shoot)
            return;

        this.shoot = true;
    }

    private static boolean status = false;

    private RobotShooter shooter;
    private RobotFeeder feeder;
    private boolean isRunning;
    private boolean alive;
    private boolean shoot;

    private static final double power = .77;
    private static final int gaugeRefresh = 100;
}
