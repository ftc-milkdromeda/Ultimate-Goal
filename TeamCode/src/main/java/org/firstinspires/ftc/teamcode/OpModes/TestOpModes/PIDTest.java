package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Drivers.Feeder;
import org.firstinspires.ftc.teamcode.Drivers.GamePad;
import org.firstinspires.ftc.teamcode.Drivers.Shooter;
import org.firstinspires.ftc.teamcode.Drivers.Storage;
import org.firstinspires.ftc.teamcode.Tasks.Gamepad.ShooterTask;

import Milkdromeda.Drivers.DriverManager;
import Milkdromeda.TaskManager.Clock;
import Milkdromeda.TaskManager.ThreadManager;

@TeleOp(name="PID test")

public class PIDTest extends OpMode {
    @Override
    public void init() {
        ThreadManager.stopAllProcess();
        DriverManager.stopAllProcess();

        this.clock = new Clock(60);
        this.storage = new Storage(super.hardwareMap);
        this.feeder = new Feeder(this.storage, super.hardwareMap);
        this.driver = new Shooter(super.hardwareMap);

        this.shooter = new ShooterTask(this.clock, new GamePad(super.gamepad1), this.driver, this.feeder, this.storage);

        this.storage.enterThread(this.shooter);
        this.feeder.enterThread(this.shooter);
        this.storage.enterThread(this.shooter);
        this.driver.enterThread(this.shooter);

        FtcDashboard dashboard = FtcDashboard.getInstance();
        this.telemetry = dashboard.getTelemetry();
    }

    @Override
    public void start() {
        this.clock.start();
        this.shooter.start();
        this.shooter.pause();
        this.shooter.proceed();
    }

    @Override
    public void loop() {
        this.telemetry.addData("Speed", "%.3f", this.driver.getVelocity()[0]);
        this.telemetry.addData("Refresh", "%.3f", this.clock.getCurrentRate());
        this.telemetry.update();
    }

    private ShooterTask shooter;
    private Feeder feeder;
    private Storage storage;
    private Shooter driver;
    private Clock clock;
    private Telemetry telemetry;
}
