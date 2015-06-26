package de.fh_koeln.gellert_holter.client.util;

import java.util.List;

/**
 * Klasse zur Implementierung des Typs "Child". Muss äquivalent zur Datenstruktur der Objekte auf dem
 * Server sein, um mittels GSON-Library die JSON-Objekte vom Server in Java-Objekte zu parsen.
 */
public class Child {
    public String _id;
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