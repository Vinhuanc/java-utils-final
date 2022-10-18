package beans;

import lombok.Builder;

@Builder(toBuilder = true)
public class Student {
    private String Name;
    private String major;

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
