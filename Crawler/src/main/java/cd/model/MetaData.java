package cd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chendong on 16/4/29.
 */
public class MetaData implements Serializable {

    public static enum DataType {
        DATA, URL, SUBDATAS
    }

    public static class Entry implements Serializable{
        public String name;
        public String data;
        public List<MetaData> subDatas;
        public DataType type;

        public Entry(String name, String data, List<MetaData> subDatas, DataType type) {
            this.name = name;
            this.data = data;
            this.subDatas= subDatas;
            this.type = type;
        }
    }

    public MetaData() {
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
