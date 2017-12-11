package Bean;

/**
 * Created by hasee on 2017/11/6.
 */
public class UserBean {
    private int u_ID;
    private String u_LoginID;
    private String u_NickName;
    private String U_PassWord;
    private String U_SignaTure;
    private int u_Sex;
    private String u_Email;
    private String u_Telephone;
    private String u_HeadPortrait;
    private String u_City;
    private int u_UserState;

    public UserBean(){}

    public int getU_ID() {
        return u_ID;
    }

    public void setU_ID(int u_ID) {
        this.u_ID = u_ID;
    }

    public String getU_LoginID() {
        return u_LoginID;
    }

    public void setU_LoginID(String u_LoginID) {
        this.u_LoginID = u_LoginID;
    }

    public String getU_NickName() {
        return u_NickName;
    }

    public void setU_NickName(String u_NickName) {
        this.u_NickName = u_NickName;
    }

    public String getU_PassWord() {
        return U_PassWord;
    }

    public void setU_PassWord(String u_PassWord) {
        U_PassWord = u_PassWord;
    }

    public String getU_SignaTure() {
        return U_SignaTure;
    }

    public void setU_SignaTure(String u_SignaTure) {
        U_SignaTure = u_SignaTure;
    }

    public int getU_Sex() {
        return u_Sex;
    }

    public void setU_Sex(int u_Sex) {
        this.u_Sex = u_Sex;
    }

    public String getU_Email() {
        return u_Email;
    }

    public void setU_Email(String u_Email) {
        this.u_Email = u_Email;
    }

    public String getU_Telephone() {
        return u_Telephone;
    }

    public void setU_Telephone(String u_Telephone) {
        this.u_Telephone = u_Telephone;
    }

    public String getU_HeadPortrait() {
        return u_HeadPortrait;
    }

    public void setU_HeadPortrait(String u_HeadPortrait) {
        this.u_HeadPortrait = u_HeadPortrait;
    }

    public String getU_City() {
        return u_City;
    }

    public void setU_City(String u_City) {
        this.u_City = u_City;
    }

    public int getU_UserState() {
        return u_UserState;
    }

    public void setU_UserState(int u_UserState) {
        this.u_UserState = u_UserState;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "u_ID=" + u_ID +
                ", u_LoginID='" + u_LoginID + '\'' +
                ", u_NickName='" + u_NickName + '\'' +
                ", U_PassWord='" + U_PassWord + '\'' +
                ", U_SignaTure='" + U_SignaTure + '\'' +
                ", u_Sex=" + u_Sex +
                ", u_Email='" + u_Email + '\'' +
                ", u_Telephone='" + u_Telephone + '\'' +
                ", u_HeadPortrait='" + u_HeadPortrait + '\'' +
                ", u_City='" + u_City + '\'' +
                ", u_UserState=" + u_UserState +
                '}';
    }
}
