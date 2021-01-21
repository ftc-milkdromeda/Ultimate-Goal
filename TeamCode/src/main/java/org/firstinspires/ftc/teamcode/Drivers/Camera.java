package org.firstinspires.ftc.teamcode.Drivers;

import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.R;

import java.nio.ByteBuffer;

import Milkdromeda.Image.Bitmap;
import Milkdromeda.TaskManager.Task;

import Milkdromeda.Drivers.RobotCamera;

public class Camera extends RobotCamera {
    public Camera() {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.vuforiaLicenseKey = Camera.key;
        params.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;

        ClassFactory factory = ClassFactory.getInstance();
        this.locale = factory.createVuforia(params);
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB888, true);
        locale.setFrameQueueCapacity(1);
    }

    @Override
    public Bitmap takeImage(Task task) {
        if(super.busy || !super.testTask(task))
            return null;

        super.busy = true;

        VuforiaLocalizer.CloseableFrame frame;
        try {
            frame = this.locale.getFrameQueue().take();
        }
        catch (InterruptedException interrupt) {
            return null;
        }

        Image raw = null;
        for(int a = 0; a < frame.getNumImages(); a++) {
            if (frame.getImage(a).getFormat() == PIXEL_FORMAT.RGB888) {
                raw = frame.getImage(a);
                break;
            }
        }

        if(raw == null)
            return null;

        ByteBuffer byteArray = raw.getPixels();
        byteArray.rewind();
        byte array[] = new byte[byteArray.remaining()];
        byteArray.get(array);

        Bitmap finalImage = new Bitmap(raw.getHeight(), raw.getWidth());

        if(!finalImage.setPixels(array, false))
            return null;


        super.busy = false;

        return finalImage;
    }

    private VuforiaLocalizer locale;
    private static final String key = "Ac7O/sT/////AAABmfegDDc3nUE0pRyH393dmoYsL9SH/fuutgc8rqUBYUUvEzt3vPN4UjwVcrmWAy30T8nEa8zEvDsuA03b6QLWoxRURps/uuPOUI9xeKZP17fSjCU8EjpOTEPbMEyKTY0uiR10gAlmD8lSkDBIEbKDLAWVYC9VRzPDllyxA38m8sXXZjDPfIJ4IPe1Ae+hW2x4pxCGY3qrdoM/t/41ZBYXXs/QnGpymvC6Rwqqmqu3K+xisVGxrqeJ9Fj+Ew0etzUJFpUFYXqZDPocJSLvdK1wM5fh6fqOFqdeNAM21X4ccqMGRMraIbEbwWWiabzywi3L5IsyeWU4DvuXk7dV6BKngVkj+wXcBJ2fn+2qdCNzuS7M";
}
