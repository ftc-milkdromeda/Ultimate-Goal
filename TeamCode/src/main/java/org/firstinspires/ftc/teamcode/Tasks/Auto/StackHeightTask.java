package org.firstinspires.ftc.teamcode.Tasks.Auto;

import org.firstinspires.ftc.teamcode.Drivers.Camera;
import org.firstinspires.ftc.teamcode.OpModes.EstablishedOpMode.RemoteAuto;
import org.firstinspires.ftc.teamcode.OpModes.Templates.AutoTemplate;

import Milkdromeda.Drivers.RobotCamera;
import Milkdromeda.TaskManager.Clock;
import Milkdromeda.TaskManager.Task;

import Milkdromeda.Image.Bitmap;
import Milkdromeda.TaskManager.ThreadManager;

public class StackHeightTask extends Task {
    public StackHeightTask(Clock clock, RobotCamera camera, RemoteAuto opmode) {
        super(clock);

        this.ringHeight = -1;
        this.camera = camera;
        this.image = null;

        this.camera.enterThread(this);

        this.opmode = opmode;
    }

    private int countPixel(Bitmap image, double lowerBound, double upperBound) {
        double pixels[] = image.getPixels();
        int numOfPixel = 0;

        if(pixels == null)
            return 0;

        for(int a = 0; a < pixels.length; a++) {
            numOfPixel += (pixels[a] > lowerBound && pixels[a] < upperBound) ? 1 : 0;
        }

        return numOfPixel;
    }

    @Override
    protected void deconstructor() {
        this.camera.exitThread(this);
    }

    @Override
    public void run() {

        while(this.image == null && !this.isInterrupted())
            return;

        if(this.isInterrupted())
            return;

        this.image.makeGrayscale();

        this.image.writeImage("/storage/self/primary/FIRST/beforeImage");

        double whiteLevel = 0;
        double blackLevel = 0;

        for(int a = 0; a < StackHeightTask.LuminanceSampleSize; a++) {
            blackLevel += this.image.getPixel(StackHeightTask.BlackLevelCoord[0], StackHeightTask.BlackLevelCoord[1] + a)[0];
            whiteLevel += this.image.getPixel(StackHeightTask.WhiteLevelCoord[0] + a, StackHeightTask.WhiteLevelCoord[1])[0];

            System.out.println("A Value: " + a);
        }

        whiteLevel /= StackHeightTask.LuminanceSampleSize;
        blackLevel /= StackHeightTask.LuminanceSampleSize;

        this.image.crop(StackHeightTask.crop_x1, StackHeightTask.crop_y1, StackHeightTask.crop_x2, StackHeightTask.crop_y2);

        this.image.adjustLuminance(blackLevel, whiteLevel);

        Bitmap.Curve curve = new Bitmap.Curve() {
            @Override
            public double curve(double input) {
                if(input > 0.6)
                    return 1.0;
                else
                    return 0.0;
            }
        };

        this.image.adjustCurve(curve);

        int pixelCount = this.countPixel(this.image, -0.1, 0.1);

        if(pixelCount <= oneRingMin)
            this.ringHeight = 0;
        else if(pixelCount <= fourRingMin)
            this.ringHeight = 1;
        else
            this.ringHeight = 4;


        this.image.writeImage("/storage/self/primary/FIRST/finalImage");

        this.opmode.ringCallBack(this.ringHeight);

        ThreadManager.stopProcess(super.getProcessId());
    }

    public int getRingHeight() {
        return this.ringHeight;
    }

    public boolean setImage(Bitmap image) {
        if(this.image != null)
            return false;

        this.image = new Bitmap(image);

        return true;
    }

    private int ringHeight;
    private RobotCamera camera;
    private Bitmap image;
    private RemoteAuto opmode;

    private static final int oneRingMin = 800;
    private static final int fourRingMin = 13000;

    private static final int crop_x1 = 81;
    private static final int crop_x2 = 251;

    private static final int crop_y1 = 614;
    private static final int crop_y2 = 978;

    private static final int LuminanceSampleSize = 50;
    private static final int BlackLevelCoord[] = {525, 655};
    private static final int WhiteLevelCoord[] = {123, 927};
}

