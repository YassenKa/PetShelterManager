package code;

public class Pet {
    private String type;
    private String name;
    private int age;
    private String healthStatus;

    public Pet(String type, String name, int age, String healthStatus) {
        this.type = type;
        this.name = name;
        this.age = age;
        this.healthStatus = healthStatus;
    }

    public String getType() { return type; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getHealthStatus() { return healthStatus; }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    @Override
    public String toString() {
        return type + " - " + name + ", възраст: " + age + ", здраве: " + healthStatus;
    }
}
