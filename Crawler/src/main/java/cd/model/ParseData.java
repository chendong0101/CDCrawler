package cd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chendong on 16/4/29.
 */
public class ParseData implements Serializable {

    public static enum EntryType {
        DATA, SUBENTRY
    }

    public static class Entry implements Serializable{
        public String name;
        public Object value;
        public EntryType type;

        public Entry(String name, Object value, EntryType type) {
            this.name = name;
            this.value = value;
            this.type = type;
        }
    }

    public ParseData() {
        entries = new ArrayList<>();
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    private List<Entry> entries;
}
