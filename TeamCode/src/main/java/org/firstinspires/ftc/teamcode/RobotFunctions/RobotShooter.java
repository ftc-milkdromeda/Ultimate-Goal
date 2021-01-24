package org.firstinspires.ftc.teamcode.RobotFunctions;

import com.qualcomm.robotcore.hardware.DcMotor;

import Milkdromeda.Drivers.DriverManager;
import Milkdromeda.Drivers.Template.Driver;
import Milkdromeda.RobotFunctions.Units_length;
import Milkdromeda.TaskManager.Task;

public abstract class RobotShooter extends Driver {
    protected RobotShooter() {
        super();

        if(RobotShooter.processId != -1)
            super.alive = false;
        else
            RobotShooter.processId = DriverManager.attachProcess(this);
    }

    public abstract boolean runShooter(Task task, double distance, Units_length units);
    public abstract boolean runShooter(Task task, double distance, Units_length units, double offset);
    public abstract boolean runShooter(Task task, double rpm);
    public abstract boolean runShooterPower(Task task, double power);
    public abstract boolean stopShooter(Task task);

    public double[] getVelocity() {
        return meter.getMotorVelocity();
    }
    public double[] getAverageVelocity() {
        return  meter.getAverageMotorVelocity();
    }
    public void resetGauge() {
        this.meter.reset();
    }

    protected void setMeter(VelocityGauge meter) {
        this.meter = meter;
        //this.meter.start();

        this.busy = false;
    }

    protected static abstract class VelocityGauge extends Thread {
        VelocityGauge() {
            this.dataPoints = 0;
            this.motorVelocity = new double[] {0, 0};
            this.averageMotorVelocity = new double[] {0, 0};
        }
        @Override
        public abstract void run();

        public abstract void stopThread();

        public double[] getAverageMotorVelocity() {
            double returnArray[] = {this.motorVelocity[0], this.motorVelocity[1]};

            return returnArray;
        }

        public double[] getMotorVelocity() {
            double returnArray[] = {this.motorVelocity[0], this.motorVelocity[1]};

            return returnArray;
        }

        public synchronized void reset() {
            this.dataPoints = 0;

            this.motorVelocity[0] = 0;
            this.motorVelocity[1] = 0;
        }

        protected enum Interrupt {
            STOP, CONTINUE;
        }

        protected double averageMotorVelocity[];
        protected double motorVelocity[];
        protected Interrupt interrupt;
        protected int dataPoints;
    }

    @Override
    protected void destructor() {
        RobotShooter.processId = -1;
    }

    private VelocityGauge meter;

    private static int processId = -1;
}
//todo fix VelocityGauge
