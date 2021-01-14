package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Drivers.Feeder;
import org.firstinspires.ftc.teamcode.Drivers.Shooter;
import org.firstinspires.ftc.teamcode.Drivers.Storage;
import org.firstinspires.ftc.teamcode.Tasks.Auto.ShooterTask;

import Drivers.DriverManager;
import TaskManager.Clock;
import TaskManager.ThreadManager;

public class PIDTest extends OpMode {
    @Override
    public void init() {
        ThreadManager.stopAllProcess();
        DriverManager.stopAllProcess();

        this.clock = new Clock(1000);
        this.storage = new Storage(super.hardwareMap);
        this.feeder = new Feeder(this.storage, super.hardwareMap);
        this.driver = new Shooter(super.hardwareMap);

        this.shooter = new ShooterTask(this.clock, this.driver, this.feeder);

        this.storage.enterThread(this.shooter);
        this.feeder.enterThread(this.shooter);
        this.storage.enterThread(this.shooter);

        FtcDashboard dashboard = FtcDashboard.getInstance();
        this.telemetry = dashboard.getTelemetry();
    }

    @Override
    public void start() {
        this.clock.start();
        this.shooter.start();
    }

    @Override
    public void loop() {
        this.telemetry.addData("Speed: ", "%0.3%", this.driver.getVelocity());
        this.telemetry.addData("Refresh", "%0.3", this.clock.getCurrentRate());
        this.telemetry.update();
    }

    private ShooterTask shooter;
    private Feeder feeder;
    private Storage storage;
    private Shooter driver;
    private Clock clock;
    private Telemetry telemetry;
}
