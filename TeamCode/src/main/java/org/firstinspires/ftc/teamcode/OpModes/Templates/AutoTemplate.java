package org.firstinspires.ftc.teamcode.OpModes.Templates;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import RobotFunctions.Units_time;

public abstract class AutoTemplate extends LinearOpMode {
    protected void startSequence() {}
    protected void finalizer() {}
    protected abstract void initHardware();
    protected abstract void main();

    protected final boolean wait(double time, Units_time units) {
        long startTime = System.currentTimeMillis();
        while(super.opModeIsActive() && System.currentTimeMillis() - startTime <= time * units.getValue());

        return super.opModeIsActive();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initHardware();

        super.waitForStart();
        this.startSequence();

        this.main();
    }
}
