package org.firstinspires.ftc.teamcode.Tasks.Gamepad;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotFeeder;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotShooter;

import RobotFunctions.TaskManager.Clock;
import RobotFunctions.TaskManager.Controller;
import RobotFunctions.TaskManager.KeyTask;

public class ShooterTask extends KeyTask {
    public ShooterTask(Clock clock, Controller controller, RobotShooter shooter, RobotFeeder feeder) {
        super(clock, controller);
        this.shooter = shooter;
        this.feeder = feeder;
        this.pause = true;
    }

    @Override
    protected double[] keyMapping() {
        double returnArray[] = { super.controller.get_A() };

        return returnArray;
    }

    @Override
    public void run() {
        while(!super.isInterrupted()) {
            if(this.pause) {
                int startClock = super.clock.getCurrentState();
                while(super.clock.getCurrentState() != startClock);
                continue;
            }

            if(this.keyMapping()[0] == 1.0)
                this.feeder.feedRing();

            int startClock = super.clock.getCurrentState();
            while(super.clock.getCurrentState() == startClock);
        }
    }

    public void pause() {
        if(!this.isAlive() || pause)
            return;

        this.shooter.stopShooter();
        this.shooter.resetGauge();
        this.pause = true;
        ShooterTask.status = false;
    }
    public void proceed() {
        if(ShooterTask.status || !this.isAlive() || !pause)
            return;

        ShooterTask.status = true;
        this.pause = false;

        this.shooter.runShooterPower(ShooterTask.power);
        this.shooter.resetGauge();
    }

    private static boolean status = false;

    private RobotShooter shooter;
    private RobotFeeder feeder;
    private boolean pause;

    private static final double power = .74;
}
