package com.priyankamehta.hw8;

/**
 * Created by PriyankaMehta on 4/16/16.
 */
public class Conversations {

    private String urlContactImage;
    private String Name;
    private String urlRedBubble;
    private String urlPhone;
    private String phoneNumber;
    private String email;
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Conversations(String urlContactImage, String name) {
        this.urlContactImage = urlContactImage;
        Name = name;
    }

    public Conversations() {
    }

    public String getUrlContactImage() {
        return urlContactImage;
    }

    public void setUrlContactImage(String urlContactImage) {
        this.urlContactImage = urlContactImage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrlRedBubble() {
        return urlRedBubble;
    }

    public void setUrlRedBubble(String urlRedBubble) {
        this.urlRedBubble = urlRedBubble;
    }

    public String getUrlPhone() {
        return urlPhone;
    }

    public void setUrlPhone(String urlPhone) {
        this.urlPhone = urlPhone;
    }

    @Override
    public String toString() {
        return "Conversations{" +
                "urlContactImage='" + urlContactImage + '\'' +
                ", Name='" + Name + '\'' +
                ", urlRedBubble='" + urlRedBubble + '\'' +
                ", urlPhone='" + urlPhone + '\'' +
                '}';
    }
}