package org.firstinspires.ftc.teamcode.DataCollection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSet<type> {
    public DataSet() {}

    public boolean writeData(String fileName) {
        return true;
    }
    public boolean readData(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner reader = new Scanner(file);

        for(int a = 0; a < Integer.parseInt(reader.nextLine()); a++) {
            data.add(null);
        }
        return true;
    }

    public void add(DataPoint dataPoint) {
        data.add(dataPoint);
    }
    public void add(DataPoint dataPoint, int position) {
        try {
            data.set(position, dataPoint);
        }
        catch(ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    public void removeDataPoint(int position) {
        try {
            data.remove(position);
        }
        catch(ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    public void clear() {
        data =  new ArrayList<DataPoint>();
    }
    public int size() {
        return data.size();
    }

    private ArrayList<DataPoint> data;
}

/*
FILE FORMAT:
File length\n
DataPoint1\n
DataPoint2\n
DataPointN\n
 */
