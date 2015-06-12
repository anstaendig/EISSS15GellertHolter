package util;

import android.app.Activity;

import java.util.Date;
import java.util.List;

public class Child {
    public String name;
    public Integer age;
    public String gender;
    public List<String> sports;
    public List<String> diseases;
    public List<LogEntry> log;
    public String parent;
    public String doc;
    public Therapy therapy;

    public class Therapy {
        public Factor factor;
        public String type;
        public Integer target;
        public Integer correction;
    }

    public class Factor {
        public Double morning;
        public Double day;
        public Double evening;
        public String type;
    }

    public static class LogEntry {
        public String date;
        public Integer bloodsugar;
        public Double be;
        public Double beFactor;
        public Integer correctionValue;
        public Double insulin;
        //public String mood;
        public String notes;
    }
}