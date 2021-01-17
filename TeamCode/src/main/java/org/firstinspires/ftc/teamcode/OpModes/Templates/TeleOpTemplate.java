package org.firstinspires.ftc.teamcode.OpModes.Templates;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Drivers.GamePad;
import org.firstinspires.ftc.teamcode.Tasks.Gamepad.ExitTask;

import Milkdromeda.Drivers.DriverManager;
import Milkdromeda.RobotFunctions.Units_time;
import Milkdromeda.TaskManager.ThreadManager;

public abstract class TeleOpTemplate extends LinearOpMode {
    protected void startSequence() {}
    protected void finalizer() {}
    protected abstract void initHardware();
    protected abstract void main();

    protected boolean programIsActive() {
        return !exit.getExit();
    }

    @Override
    public final void runOpMode() throws InterruptedException {
        ThreadManager.stopAllProcess();
        DriverManager.stopAllProcess();

        this.exit = new ExitTask(new GamePad(super.gamepad1));
        this.exit.start();

        this.initHardware();

        super.waitForStart();
        this.startSequence();

        while(super.opModeIsActive() && this.programIsActive())
            this.main();

        this.finalizer();
    }

    private ExitTask exit;
}
