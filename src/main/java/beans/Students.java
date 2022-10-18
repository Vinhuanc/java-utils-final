package beans;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Students {
    public String name;
    public String major;

    @Override
    public String toString() {
        return getStudent();
    }

    public String getStudent(){
        return "students{\"name\": "+ this.name +", \"major\": "+this.major+"}";
    }

}
