package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Drivers.GamePad;
import org.firstinspires.ftc.teamcode.Drivers.MecanumDrive;
import org.firstinspires.ftc.teamcode.Tasks.Gamepad.Movement;
import org.firstinspires.ftc.teamcode.OpModes.Templates.TeleOpTemplate;

import TaskManager.Clock;
import Drivers.Controller;
import TaskManager.ThreadManager;

@Disabled
@TeleOp(name="Teleop Task Test")
public class TaskMangerTest extends TeleOpTemplate {
    @Override
    protected void initHardware() {
        this.drive = new MecanumDrive(super.hardwareMap);
        this.clock = new Clock(100);
        this.controller = new GamePad(super.gamepad1);

        task = new Movement(this.clock, this.controller, drive);
    }

    @Override
    protected void startSequence() {
        this.task.start();
        this.clock.start();
    }

    @Override
    protected void main() {
        super.telemetry.addData("Refresh: ", "%.2fHz", this.clock.getCurrentRate());
        super.telemetry.addData("Angle: ", "%.2fdeg", 180 * this.controller.get_LeftStick().getAngle() / Math.PI );
        super.telemetry.update();
    }

    @Override
    protected void finalizer() {
        ThreadManager.stopAllProcess();
    }

    private MecanumDrive drive;
    private Clock clock;
    private Controller controller;
    private Movement task;
}
