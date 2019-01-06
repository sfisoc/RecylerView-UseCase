package Model;

public class Country {
    private String name;
    private String flag;
    private String capital;
    private String region;

    public Country(String name, String flag, String cap, String reg) {
        this.name = name;
        this.flag = flag;
        this.capital = cap;
        this.region = reg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
