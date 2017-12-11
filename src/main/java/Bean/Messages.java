package Bean;

/**
 * Created by hasee on 2017/11/6.
 */
public class Messages {
    private int m_ID;
    private String m_PostMessages;
    private int m_status;
    private String m_Time;
    private int m_MessagesType;
    private int m_FromUserID;
    private int m_ToUserID;

    public Messages(){}

    public int getM_ID() {
        return m_ID;
    }

    public void setM_ID(int m_ID) {
        this.m_ID = m_ID;
    }

    public String getM_PostMessages() {
        return m_PostMessages;
    }

    public void setM_PostMessages(String m_PostMessages) {
        this.m_PostMessages = m_PostMessages;
    }

    public int getM_status() {
        return m_status;
    }

    public void setM_status(int m_status) {
        this.m_status = m_status;
    }

    public String getM_Time() {
        return m_Time;
    }

    public void setM_Time(String m_Time) {
        this.m_Time = m_Time;
    }

    public int getM_MessagesType() {
        return m_MessagesType;
    }

    public void setM_MessagesType(int m_MessagesType) {
        this.m_MessagesType = m_MessagesType;
    }

    public int getM_FromUserID() {
        return m_FromUserID;
    }

    public void setM_FromUserID(int m_FromUserID) {
        this.m_FromUserID = m_FromUserID;
    }

    public int getM_ToUserID() {
        return m_ToUserID;
    }

    public void setM_ToUserID(int m_ToUserID) {
        this.m_ToUserID = m_ToUserID;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "m_ID=" + m_ID +
                ", m_PostMessages='" + m_PostMessages + '\'' +
                ", m_status=" + m_status +
                ", m_Time='" + m_Time + '\'' +
                ", m_MessagesType=" + m_MessagesType +
                ", m_FromUserID=" + m_FromUserID +
                ", m_ToUserID=" + m_ToUserID +
                '}';
    }
}
