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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    @Override
    public String toString() {
        return "Животно: " + type + ", Име: " + name + ", Възраст: " + age + ", Здравословно състояние: " + healthStatus;
    }
}
