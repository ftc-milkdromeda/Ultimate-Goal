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

        this.shooter.enterThread(this);
        this.feeder.enterThread(this);
    }


    @Override
    public void run() {
        this.alive = true;

        int gauge = super.clock.getCurrentState();

        while(!super.isInterrupted()) {
            if(this.isRunning)
                this.shooter.runShooterPower(this, .79);
            else
                this.shooter.stopShooter(this);

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

        this.storage.setPosition(this, 1);
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

    private static final double power = .796;
}
