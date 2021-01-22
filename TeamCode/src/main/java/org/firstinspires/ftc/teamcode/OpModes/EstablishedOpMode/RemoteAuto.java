package org.firstinspires.ftc.teamcode.OpModes.EstablishedOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Drivers.Arm;
import org.firstinspires.ftc.teamcode.Drivers.Camera;
import org.firstinspires.ftc.teamcode.Drivers.Feeder;
import org.firstinspires.ftc.teamcode.Drivers.Intake;
import org.firstinspires.ftc.teamcode.Drivers.Shooter;
import org.firstinspires.ftc.teamcode.Drivers.Storage;
import org.firstinspires.ftc.teamcode.OpModes.Templates.AutoTemplate;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotArm;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotFeeder;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotIntake;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotShooter;
import org.firstinspires.ftc.teamcode.RobotFunctions.RobotStorage;
import org.firstinspires.ftc.teamcode.Tasks.Auto.ArmTask;
import org.firstinspires.ftc.teamcode.Tasks.Auto.RingTaskCoordinator;
import org.firstinspires.ftc.teamcode.Tasks.Auto.StackHeightTask;

import java.sql.Driver;

import Milkdromeda.Drivers.DriverManager;
import Milkdromeda.Drivers.RobotCamera;
import Milkdromeda.RobotFunctions.Units_length;
import Milkdromeda.TaskManager.Clock;
import Milkdromeda.TaskManager.ThreadManager;

@Autonomous(name="Auto")
public class RemoteAuto extends AutoTemplate {
    private boolean motorIsDone(DcMotor motor, int target) {
        final int acceptedError = 5;
        return motor.getCurrentPosition() <= target + acceptedError && motor.getCurrentPosition() >= target - acceptedError;
    }
    private int tickCalculator(double distance, Units_length units) {
        final double tickConstant = 1631.3213703;
        return (int)Math.round(distance * units.getValue() * tickConstant);
    }
    private void runToDistance(double power, double distance, Units_length units) {
        for(int a = 0; a < this.motors.length; a++) {
            this.motors[a].setTargetPosition(tickCalculator(distance, units));
            this.motors[a].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        this.motors[0].setPower(power);
        this.motors[1].setPower(power);
        this.motors[2].setPower(power);
        this.motors[3].setPower(power);

        while(
                !(this.motorIsDone(this.motors[0], this.tickCalculator(distance, units)) &&
                this.motorIsDone(this.motors[1], this.tickCalculator(distance, units)) &&
                this.motorIsDone(this.motors[2], this.tickCalculator(distance, units)) &&
                this.motorIsDone(this.motors[3], this.tickCalculator(distance, units)))
                && super.opModeIsActive()
        );

        for(int a = 0; a < 4; a++) {
            this.motors[a].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            this.motors[a].setPower(0.0);
        }
    }
    private void strafeToDistance(double power, double distance, Units_length units) {
        for(int a = 0; a < this.motors.length; a+=3) {
            this.motors[a].setTargetPosition(tickCalculator(distance, units));
            this.motors[a].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        for(int a = 1; a < this.motors.length - 1; a++) {
            this.motors[a].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            this.motors[a].setTargetPosition(-tickCalculator(distance, units));
            this.motors[a].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        this.motors[0].setPower(power);
        this.motors[1].setPower(-power);
        this.motors[2].setPower(-power);
        this.motors[3].setPower(power);

        while(
                !(this.motorIsDone(this.motors[0], this.tickCalculator(distance, units)) &&
            this.motorIsDone(this.motors[1], -this.tickCalculator(distance, units)) &&
            this.motorIsDone(this.motors[2], -this.tickCalculator(distance, units)) &&
            this.motorIsDone(this.motors[3], this.tickCalculator(distance, units)))
            && super.opModeIsActive()
        );

        for(int a = 0; a < 4; a++) {
            this.motors[a].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            this.motors[a].setPower(0.0);
        }
}

    private void fourRing() {
        this.strafeToDistance(0.4, 3.0625, Units_length.IN);
        this.runToDistance(0.2, 7, Units_length.IN);
        this.strafeToDistance(-0.4, -3.0625, Units_length.IN);

        this.storage.setRings(3);
        this.coordinator.runShooter();
        while(this.storage.getRings() > 0)
            this.coordinator.shoot();
        this.coordinator.stopShooter();

        this.strafeToDistance(0.4, 3.0625, Units_length.IN);
        this.runToDistance(0.4, 12, Units_length.IN);
        this.strafeToDistance(-0.4, -3.0625, Units_length.IN);

        this.storage.setRings(2);
        this.coordinator.runShooter();
        while(this.storage.getRings() > 0)
            this.coordinator.shoot();

        this.coordinator.stopShooter();

        this.runToDistance(0.8, 53.375, Units_length.IN);
        this.strafeToDistance(0.7, 29.5, Units_length.IN);

        this.armTask.setPosition(2);
        this.armTask.setGrabber(true);

        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < RemoteAuto.ArmTimeDown && super.opModeIsActive());

        this.armTask.setPosition(0);

        startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < RemoteAuto.ArmTimeDown && super.opModeIsActive());

        ThreadManager.stopAllProcess();
        DriverManager.stopAllProcess();

        this.runToDistance(-1.0, -32.5, Units_length.IN);
    }
    private void oneRing() {
        this.strafeToDistance(0.4, 3.0625, Units_length.IN);
        this.runToDistance(0.2, 7, Units_length.IN);
        this.strafeToDistance(-0.4, -3.0625, Units_length.IN);

        this.storage.setRings(2);
        this.coordinator.runShooter();
        while(this.storage.getRings() > 0)
            this.coordinator.shoot();
        this.coordinator.stopShooter();

        this.runToDistance(0.8, 53.375, Units_length.IN);
        this.strafeToDistance(0.7, 29.5, Units_length.IN);

        this.armTask.setPosition(2);
        this.armTask.setGrabber(true);

        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < RemoteAuto.ArmTimeDown && super.opModeIsActive());

        this.armTask.setPosition(0);

        startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < RemoteAuto.ArmTimeDown && super.opModeIsActive());

        ThreadManager.stopAllProcess();
        DriverManager.stopAllProcess();

        this.runToDistance(-1.0, -32.5, Units_length.IN);
    }
    private void zeroRing() {
        this.runToDistance(0.8, 53.375, Units_length.IN);
        this.strafeToDistance(0.7, 29.5, Units_length.IN);

        this.armTask.setPosition(2);
        this.armTask.setGrabber(true);

        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < RemoteAuto.ArmTimeDown && super.opModeIsActive());

        this.armTask.setPosition(0);

        startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < RemoteAuto.ArmTimeDown && super.opModeIsActive());

        ThreadManager.stopAllProcess();
        DriverManager.stopAllProcess();

        this.runToDistance(-1.0, -32.5, Units_length.IN);
    }


    @Override
    protected void startSequence() {
        this.clock.start();
        this.coordinator.start();
        this.armTask.start();
        this.stack.start();
    }

    @Override
    protected void finalizer() {
        DriverManager.stopAllProcess();
        ThreadManager.stopAllProcess();
    }

    @Override
    protected void initHardware() {
        //Driver init
        this.shooter = new Shooter(hardwareMap);
        this.storage = new Storage(hardwareMap);
        this.intake = new Intake(this.storage, hardwareMap);
        this.feeder = new Feeder(this.storage, hardwareMap);
        this.arm = new Arm(this.hardwareMap);
        this.camera = new Camera();

        //Tasks
        this.clock = new Clock(100);
        this.armTask = new ArmTask(this.clock, this.arm, super.telemetry);
        this.coordinator = new RingTaskCoordinator(this.clock, this.intake, this.shooter, this.feeder, this.storage);
        this.stack = new StackHeightTask(this.clock, this.camera);

        this.motors = new DcMotor[4];

        this.motors[0] = hardwareMap.dcMotor.get("motor0");
        this.motors[0].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motors[1] = hardwareMap.dcMotor.get("motor1");
        this.motors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motors[2] = hardwareMap.dcMotor.get("motor2");
        this.motors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motors[3] = hardwareMap.dcMotor.get("motor3");
        this.motors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.motors[2].setDirection(DcMotorSimple.Direction.REVERSE);
        this.motors[3].setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    protected void main() {
        for(DcMotor motor : this.motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setPower(0.3);
        }

        this.storage.setRings(3);
        this.coordinator.runShooter();
        this.armTask.setPosition(1);

        this.runToDistance(0.4, 29.5, Units_length.IN);

        while(this.storage.getRings() > 0)
            this.coordinator.shoot();
        this.coordinator.stopShooter();

        super.telemetry.addData("Rings", "%d", this.stack.getRingHeight());

        switch (this.stack.getRingHeight()) {
            case 0:
                this.zeroRing();
                break;
            case 1:
                this.oneRing();
                break;
            case 4:
                this.fourRing();
                break;
        }
    }

    private RobotShooter shooter;
    private RobotIntake intake;
    private RobotFeeder feeder;
    private RobotStorage storage;
    private RobotArm arm;
    private RobotCamera camera;

    private DcMotor motors[];

    private ArmTask armTask;
    private RingTaskCoordinator coordinator;
    private Clock clock;
    private StackHeightTask stack;

    private static final double ArmTimeDown = 500;
}
