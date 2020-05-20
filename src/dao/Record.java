package dao;

public class Record {
    private int recordId = 0;
    private String recordDate = "";
    private String recordType = "";
    private Double recordAmount = 0.00;
    private String recordExplain = "";
    private String recordOwner = "";

    //getter&setter
    public int getRecordId() { return recordId; }

    public void setRecordId(int recordId) { this.recordId = recordId; }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getRecordType() { return recordType; }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public Double getRecordAmount() {
        return recordAmount;
    }

    public void setRecordAmount(Double recordAmount) {
        this.recordAmount = recordAmount;
    }

    public String getRecordExplain() {
        return recordExplain;
    }

    public void setRecordExplain(String recordExplain) {
        this.recordExplain = recordExplain;
    }

    public String getRecordOwner() { return recordOwner; }

    public void setRecordOwner(String recordOwner) { this.recordOwner = recordOwner; }
}
