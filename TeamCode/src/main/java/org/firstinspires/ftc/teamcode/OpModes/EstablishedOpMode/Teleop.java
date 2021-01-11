package org.firstinspires.ftc.teamcode.OpModes.EstablishedOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Drivers.Feeder;
import org.firstinspires.ftc.teamcode.Drivers.GamePad;
import org.firstinspires.ftc.teamcode.Drivers.Intake;
import org.firstinspires.ftc.teamcode.Drivers.MecanumDrive;
import org.firstinspires.ftc.teamcode.Drivers.Shooter;
import org.firstinspires.ftc.teamcode.Drivers.Storage;
import org.firstinspires.ftc.teamcode.OpModes.Templates.TeleOpTemplate;
import org.firstinspires.ftc.teamcode.Tasks.Gamepad.Movement;
import org.firstinspires.ftc.teamcode.Tasks.Gamepad.RingTaskCoordinator;

import TaskManager.Clock;
import Drivers.Controller;
import TaskManager.ThreadManager;

@TeleOp(name="TeleOp")
public class Teleop extends TeleOpTemplate {
    @Override
    protected void startSequence() {
        this.clock.start();
        this.movement.start();
        this.ringManagement.start();
    }

    @Override
    protected void finalizer() {
        ThreadManager.stopAllProcess();

        this.feeder.terminate();
        this.shooter.terminate();
        this.intake.terminate();
        this.drive.terminate();
        this.storage.terminate();
    }

    @Override
    protected void initHardware() {
        this.storage = new Storage(super.hardwareMap);
        this.feeder = new Feeder(storage, super.hardwareMap);
        this.drive = new MecanumDrive(super.hardwareMap);
        this.intake = new Intake(storage, super.hardwareMap, super.telemetry);
        this.shooter = new Shooter(super.hardwareMap);
        this.clock = new Clock(60);

        this.controller = new Controller[2];
        this.controller[0] = new GamePad(super.gamepad1);
        this.controller[1] = new GamePad(super.gamepad2);


        this.movement = new Movement(this.clock, this.controller[0], this.drive);
        this.ringManagement = new RingTaskCoordinator(this.clock, this.controller[0], this.intake, this.shooter, this.feeder, this.storage);
    }

    @Override
    protected void main() {
       super.telemetry.addData("Refresh: ", "%.2f", this.clock.getCurrentRate());
       super.telemetry.addData("Shooter Speed: ", "%.2f : %.2f ", this.shooter.getAverageVelocity()[0], this.shooter.getAverageVelocity()[1]);
       super.telemetry.addData("Rings: ", "%d", this.storage.getRings());
       super.telemetry.update();
    }

    Clock clock;

    //Tasks
    Movement movement;
    RingTaskCoordinator ringManagement;

    //Drivers
    Feeder feeder;
    Shooter shooter;
    Intake intake;
    MecanumDrive drive;
    Storage storage;
    Controller controller[];
}
