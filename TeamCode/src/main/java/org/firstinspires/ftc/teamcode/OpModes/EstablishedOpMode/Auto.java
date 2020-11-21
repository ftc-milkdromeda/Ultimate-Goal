package org.firstinspires.ftc.teamcode.OpModes.EstablishedOpMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class Auto extends LinearOpMode {
    protected abstract void initHardware();
    protected abstract void startSequence();
    protected abstract void main();
    protected abstract void finalizer();

    @Override
    public void runOpMode() throws InterruptedException {
        this.initHardware();

        super.waitForStart();
        this.startSequence();

        this.main();
    }
}
