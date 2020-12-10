package cn.bupt.joinme.model;

import cn.bupt.joinme.share.CardType;
import cn.bupt.joinme.share.Count;
import cn.bupt.joinme.share.LevelType;
import cn.bupt.joinme.share.UserType;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {

    public Integer getUserId() {
        return userId;
    }

    public void setUserId() {
        this.userId = Count.userCount.getAndIncrement();
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LevelType getLevelType() {
        return levelType;
    }

    public void setLevelType(LevelType levelType) {
        this.levelType = levelType;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public List<String> getPermission() {
        return permission;
    }

    public void setPermission(List<String> permission) {
        this.permission = permission;
    }

    @Id
    private String id;
    private Integer userId;
    private String userName;
    private String password;
    private UserType userType;
    private String realName;
    private CardType cardType;
    private String cardNumber;
    private String phoneNumber;
    private LevelType levelType;
    private String introduction;
    private String city;
    private Date registerDate;
    private Date modifyDate;
    private List<String> permission;
}
