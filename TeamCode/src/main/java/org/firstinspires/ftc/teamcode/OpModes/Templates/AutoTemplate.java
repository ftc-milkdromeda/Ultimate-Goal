package org.firstinspires.ftc.teamcode.OpModes.Templates;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Drivers.DriverManager;
import TaskManager.ThreadManager;

public abstract class AutoTemplate extends LinearOpMode {
    protected void startSequence() {}
    protected void finalizer() {}
    protected abstract void initHardware();
    protected abstract void main();

    @Override
    public final void runOpMode() throws InterruptedException {
        ThreadManager.stopAllProcess();
        DriverManager.stopAllProcess();

        this.initHardware();

        super.waitForStart();
        this.startSequence();

        this.main();

        this.finalizer();
    }
}
