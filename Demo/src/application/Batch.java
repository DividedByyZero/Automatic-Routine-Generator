package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Batch extends Routine{
    public Batch(String BatchName){
        ArrayList<String>a1 = new ArrayList<String>();
        ArrayList<String>a2 = new ArrayList<String>();
        ArrayList<String>a3 = new ArrayList<String>();
        ArrayList<String>a4 = new ArrayList<String>();
        ArrayList<String>a5 = new ArrayList<String>();
        this.Track.put(0,a1);
        this.Track.put(1,a2);
        this.Track.put(2,a3);
        this.Track.put(3,a4);
        this.Track.put(4,a5);
        this.BatchName = BatchName;
    }
    String BatchName;
    ArrayList<String> Courses = new ArrayList<String>();
    Map<Integer,ArrayList<String>> Track = new HashMap<Integer,ArrayList<String>>();
}
