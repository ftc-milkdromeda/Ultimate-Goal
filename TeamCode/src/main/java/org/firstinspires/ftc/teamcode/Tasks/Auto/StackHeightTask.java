package org.firstinspires.ftc.teamcode.Tasks.Auto;


import org.firstinspires.ftc.teamcode.Drivers.Camera;

import Milkdromeda.TaskManager.Clock;
import Milkdromeda.TaskManager.Task;

import Milkdromeda.Image.Bitmap;

public class StackHeightTask extends Task {
    public StackHeightTask(Clock clock, Camera camera) {
        super(clock);

        this.ringHeight = -1;
        this.camera = camera;

        this.camera.enterThread(this);
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
        Bitmap image = this.camera.takeImage(this);

        if(image == null)
            return;

        image.makeGrayscale();

        image.writeImage("/storage/self/primary/FIRST/beforeImage");

        double whiteLevel = 0;
        double blackLevel = 0;

        for(int a = 0; a < StackHeightTask.LuminanceSampleSize; a++) {
            blackLevel += image.getPixel(StackHeightTask.BlackLevelCoord[0], StackHeightTask.BlackLevelCoord[1] + a)[0];
            whiteLevel += image.getPixel(StackHeightTask.WhiteLevelCoord[0] + a, StackHeightTask.WhiteLevelCoord[1])[0];

            System.out.println("A Value: " + a);
        }

        whiteLevel /= StackHeightTask.LuminanceSampleSize;
        blackLevel /= StackHeightTask.LuminanceSampleSize;

        image.crop(StackHeightTask.crop_x1, StackHeightTask.crop_y1, StackHeightTask.crop_x2, StackHeightTask.crop_y2);

        image.adjustLuminance(blackLevel, whiteLevel);

        Bitmap.Curve curve = new Bitmap.Curve() {
            @Override
            public double curve(double input) {
                if(input > 0.6)
                    return 1.0;
                else
                    return 0.0;
            }
        };

        image.adjustCurve(curve);

        int pixelCount = this.countPixel(image, -0.1, 0.1);

        if(pixelCount <= oneRingMin)
            this.ringHeight = 0;
        else if(pixelCount <= fourRingMin)
            this.ringHeight = 1;
        else
            this.ringHeight = 4;


        image.writeImage("/storage/self/primary/FIRST/finalImage");
    }

    public int getRingHeight() {
        return this.ringHeight;
    }

    private int ringHeight;
    private Camera camera;

    private static final int oneRingMin = 800;
    private static final int fourRingMin = 15000;

    private static final int crop_x1 = 84;
    private static final int crop_x2 = 234;

    private static final int crop_y1 = 436;
    private static final int crop_y2 = 696;

    private static final int LuminanceSampleSize = 50;
    private static final int BlackLevelCoord[] = {540, 590};
    private static final int WhiteLevelCoord[] = {100, 730};
}

