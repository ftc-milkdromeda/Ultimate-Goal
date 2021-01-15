package org.firstinspires.ftc.teamcode.OpModes.EstablishedOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Drivers.Arm;
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
import org.firstinspires.ftc.teamcode.Tasks.Auto.IntakeTask;
import org.firstinspires.ftc.teamcode.Tasks.Auto.RingTaskCoordinator;
import org.firstinspires.ftc.teamcode.Tasks.Auto.ShooterTask;

import Drivers.DriverManager;
import RobotFunctions.Units_length;
import TaskManager.Clock;
import TaskManager.ThreadManager;

@Autonomous(name="Auto")
public class RemoteAuto extends AutoTemplate {
    private int tickCalculator(double distance, Units_length units) {
        final double tickConstant = 3787.87878788;

        return (int)Math.round(distance * units.getValue() * tickConstant);
    }
    private void runToDistance(double power, double distance, Units_length units) {
        for(int a = 0; a < this.motors.length; a++) {
            this.motors[a].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            this.motors[a].setTargetPosition(tickCalculator(distance, units));
            this.motors[a].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        this.motors[0].setPower(power);
        this.motors[1].setPower(power);
        this.motors[2].setPower(power);
        this.motors[3].setPower(power);

        while((this.motors[0].getCurrentPosition() || this.motors[1].isBusy() || this.motors[2].isBusy() || this.motors[3].isBusy()) && super.opModeIsActive());
    }

    private void strafeToDistance(double power, double distance, Units_length units) {
        for(int a = 0; a < this.motors.length; a+=3) {
            this.motors[a].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            this.motors[a].setTargetPosition(tickCalculator(distance, units));
            this.motors[a].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        for(int a = 1; a < this.motors.length - 1; a++) {
            this.motors[a].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            this.motors[a].setTargetPosition(-tickCalculator(distance, units));
            this.motors[a].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        this.motors[0].setPower(power);
        this.motors[1].setPower(power);
        this.motors[2].setPower(power);
        this.motors[3].setPower(power);

        while((this.motors[0].isBusy() || this.motors[1].isBusy() || this.motors[2].isBusy() || this.motors[3].isBusy()) && super.opModeIsActive());

        this.motors[0].setPower(power);
        this.motors[1].setPower(power);
        this.motors[2].setPower(power);
        this.motors[3].setPower(power);

        while((this.motors[0].isBusy() || this.motors[1].isBusy() || this.motors[2].isBusy() || this.motors[3].isBusy()) && super.opModeIsActive());
    }

    @Override
    protected void startSequence() {
        this.clock.start();
        this.coordinator.start();
        this.armTask.start();
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

        //Tasks
        this.clock = new Clock(20);
        this.armTask = new ArmTask(this.clock, this.arm);
        this.coordinator = new RingTaskCoordinator(this.clock, this.intake, this.shooter, this.feeder, this.storage);

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
        this.runToDistance(0.5, 29, Units_length.IN);

        this.storage.setRings(3);
        this.coordinator.runShooter();
        while(this.storage.getRings() != 0 || super.opModeIsActive());
        this.coordinator.stopShooter();

        this.strafeToDistance(0.2, 5, Units_length.IN);
    }

    private RobotShooter shooter;
    private RobotIntake intake;
    private RobotFeeder feeder;
    private RobotStorage storage;
    private RobotArm arm;

    private DcMotor motors[];

    private ArmTask armTask;
    private RingTaskCoordinator coordinator;
    private Clock clock;
}
