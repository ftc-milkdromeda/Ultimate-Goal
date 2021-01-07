package org.firstinspires.ftc.teamcode.RobotFunctions;

import com.qualcomm.robotcore.hardware.DcMotor;

import Drivers.Template.Driver;
import RobotFunctions.Units_length;

public abstract class RobotShooter extends Driver {
    protected RobotShooter() {
        this.busy = true;
    }

    public abstract boolean runShooter(double distance, Units_length units);
    public abstract boolean runShooter(double distance, Units_length units, double offset);
    public abstract boolean runShooter(double rpm);
    public abstract boolean runShooterPower(double power);
    public abstract boolean stopShooter();

    public double[] getVelocity() {
        return meter.getMotorVelocity();
    }
    public double[] getAverageVelocity() {
        return  meter.getAverageMotorVelocity();
    }
    public void resetGauge() {
        this.meter.reset();
    }

    public boolean isBusy() {
        return this.busy;
    }

    protected void setMeter(VelocityGauge meter) {
        this.meter = meter;
        this.meter.start();

        this.busy = false;
    }


    protected static abstract class VelocityGauge extends Thread {
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

    protected boolean busy;

    private VelocityGauge meter;

}
//todo make all drivers singletons
