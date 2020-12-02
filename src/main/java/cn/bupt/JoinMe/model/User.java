package cn.bupt.JoinMe.model;

import cn.bupt.JoinMe.share.CardType;
import cn.bupt.JoinMe.share.LevelType;
import cn.bupt.JoinMe.share.UserType;

import java.util.Date;

public class User {
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public cn.bupt.JoinMe.share.UserType getUserType() {
        return UserType;
    }

    public void setUserType(cn.bupt.JoinMe.share.UserType userType) {
        UserType = userType;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public cn.bupt.JoinMe.share.CardType getCardType() {
        return CardType;
    }

    public void setCardType(cn.bupt.JoinMe.share.CardType cardType) {
        CardType = cardType;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public cn.bupt.JoinMe.share.LevelType getLevelType() {
        return LevelType;
    }

    public void setLevelType(cn.bupt.JoinMe.share.LevelType levelType) {
        LevelType = levelType;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String introduction) {
        Introduction = introduction;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public Date getRegisterDate() {
        return RegisterDate;
    }

    public void setRegisterDate(Date registerDate) {
        RegisterDate = registerDate;
    }

    public Date getModifyDate() {
        return ModifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        ModifyDate = modifyDate;
    }

    public String Id;
    public String Name;
    public String Password;
    public UserType UserType;
    public String RealName;
    public CardType CardType;
    public String CardNumber;
    public String PhoneNumber;
    public LevelType LevelType;
    public String Introduction;
    public String City;
    public Date RegisterDate;
    public Date ModifyDate;
}
