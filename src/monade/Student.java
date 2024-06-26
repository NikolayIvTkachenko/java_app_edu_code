package monade;

public class Student {
    private String nameStudent;
    private Integer age;
    private Boolean isActive;

    public Student(String nameStudent, Integer age, Boolean isActive) {
        this.nameStudent = nameStudent;
        this.age = age;
        this.isActive = isActive;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
