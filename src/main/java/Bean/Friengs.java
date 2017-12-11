package Bean;

/**
 * Created by hasee on 2017/11/6.
 */
public class Friengs {
    private int f_ID;
    private int f_FirendID;
    private int f_UserID;
    private String f_Name;
    private int f_Verification;

    public int getF_Verification() {
        return f_Verification;
    }

    public void setF_Verification(int f_Verification) {
        this.f_Verification = f_Verification;
    }

    public Friengs(){}

    public int getF_ID() {
        return f_ID;
    }

    public void setF_ID(int f_ID) {
        this.f_ID = f_ID;
    }

    public int getF_FirendID() {
        return f_FirendID;
    }

    public void setF_FirendID(int f_FirendID) {
        this.f_FirendID = f_FirendID;
    }

    public int getF_UserID() {
        return f_UserID;
    }

    public void setF_UserID(int f_UserID) {
        this.f_UserID = f_UserID;
    }

    public String getF_Name() {
        return f_Name;
    }

    public void setF_Name(String f_Name) {
        this.f_Name = f_Name;
    }

    @Override
    public String toString() {
        return "Friengs{" +
                "f_ID=" + f_ID +
                ", f_FirendID=" + f_FirendID +
                ", f_UserID=" + f_UserID +
                ", f_Name='" + f_Name + '\'' +
                '}';
    }
}
