package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotFunctions.RobotShooter;
import Milkdromeda.TaskManager.Task;

import Milkdromeda.RobotFunctions.Units_length;

public class Shooter extends RobotShooter {
    public Shooter(HardwareMap hardware) {
        shooter = new DcMotor[2];

        this.shooter[0] = hardware.dcMotor.get("shooter0");
        this.shooter[0].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.shooter[0].setDirection(DcMotorSimple.Direction.REVERSE);

        this.shooter[1] = hardware.dcMotor.get("shooter1");
        this.shooter[1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.shooter[1].setDirection(DcMotorSimple.Direction.REVERSE);

        super.setMeter(new Velocity(this.shooter));
    }
    @Override
    public boolean runShooter(Task task, double distance, Units_length units) {
        return false;
    }
    @Override
    public boolean runShooter(Task task, double distance, Units_length units, double offset) {
        return false;
    }
    @Override
    public boolean runShooter(Task task, double rpm) {
        if(super.busy || super.testTask(task))
            return false;

        super.busy = true;

        this.shooter[0].setPower(rpm / Shooter.maxMotorSpeed);
        this.shooter[1].setPower(rpm / Shooter.maxMotorSpeed);

        return true;
    }
    @Override
    public boolean runShooterPower(Task task, double power) {
        if(super.busy || !super.testTask(task))
            return false;

        super.busy = true;
        this.shooter[0].setPower(power);
        this.shooter[1].setPower(power);

        return true;
    }
    @Override
    public boolean stopShooter(Task task) {
        if(!super.busy || !this.testTask(task))
            return false;

        shooter[0].setPower(0.0);
        shooter[1].setPower(0.0);

        super.busy = false;

        return true;
    }

    private static class Velocity extends VelocityGauge {
        public Velocity(DcMotor motor[]) {
            this.motor = motor;
            this.interrupt = Velocity.Interrupt.CONTINUE;

            this.motorVelocity = new double[2];
            this.motorVelocity[0] = 0;
            this.motorVelocity[1] = 0;

            this.averageMotorVelocity = new double[2];
            this.averageMotorVelocity[0] = 0;
            this.averageMotorVelocity[1] = 0;
        }

        @Override
        public void run() {
            long startTime;
            long endTime;

            int startPositions[] = {motor[0].getCurrentPosition(), motor[1].getCurrentPosition()};

            while (this.interrupt == Interrupt.CONTINUE) {
                endTime = System.currentTimeMillis();
                startTime = System.currentTimeMillis();

                startPositions[0] = motor[0].getCurrentPosition();
                startPositions[1] = motor[1].getCurrentPosition();

                while (endTime - startTime <= this.refreshSpeed) {
                    if (this.interrupt == Interrupt.STOP)
                        return;
                    endTime = System.currentTimeMillis();
                }

                double velocity[] = {this.motor[0].getCurrentPosition() - startPositions[0], this.motor[1].getCurrentPosition() - startPositions[1]};

                velocity[0] = 60000 * velocity[0] / this.tpr / (endTime - startTime);
                velocity[1] = 60000 * velocity[1] / this.tpr / (endTime - startTime);

                this.averageMotorVelocity[0] = (this.averageMotorVelocity[0] * this.dataPoints + velocity[0]) / (this.dataPoints + 1);
                this.averageMotorVelocity[1] = (this.averageMotorVelocity[1] * this.dataPoints + velocity[1]) / (this.dataPoints + 1);

                this.motorVelocity[0] = velocity[0];
                this.motorVelocity[1] = velocity[1];

                this.dataPoints++;
            }
        }

        @Override
        public synchronized void stopThread() {
            this.interrupt = Interrupt.STOP;
        }

        private DcMotor motor[];
        private int dataPoints;

        //constants
        private final int refreshSpeed = 1000;
        private final double tpr = 103.6 / 3;
    }

    private DcMotor shooter[];

    //constants
    private static final double maxMotorSpeed = 4860; //max motor speed given in RPM.
    private Velocity meter;
}

