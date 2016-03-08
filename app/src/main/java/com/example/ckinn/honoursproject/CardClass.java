package com.example.ckinn.honoursproject;

/**
 * Created by ckinn on 22/02/2016.
 */
public class CardClass {
    private String Name;
   private String Type;
    private String CardLevel;
    private String CardAttribute;
    private String CardType;
    private String CardText;
    private String Attack;
    private String Defence;
    private String Set;

    public CardClass()
    {

    }

    public CardClass(String name, String type, String cardLevel, String cardAttribute, String cardType, String cardText, String attack, String defence, String set)
    {
        this.Name = name;
        this.Type = type;
        this.CardLevel = cardLevel;
        this.CardAttribute = cardAttribute;
        this.CardType = cardType;
        this.CardText = cardText;
        this.Attack = attack;
        this.Defence = defence;
        this.Set = set;
    }

    public String getName()
    {
        return this.Name;
    }

    public void setName(String name)
    {
        this.Name = name;
    }

    public String getType()
    {
        return this.Type;
    }

    public void setType(String type)
    {
        this.Type = type;
    }

    public String getCardLevel()
    {
        return this.CardLevel;
    }

    public void setCardLevel(String cardLevel)
    {
        this.CardLevel = cardLevel;
    }

    public String getCardAttribute()
    {
        return this.CardAttribute;
    }

    public void setCardAttribute(String cardAttribute)
    {
        this.CardAttribute = cardAttribute;
    }

    public String getCardType()
    {
        return this.CardType;
    }

    public void setCardType(String cardType)
    {
        this.CardType = cardType;
    }

    public String getCardText()
    {
        return this.CardText;
    }

    public void setCardText(String cardText)
    {
        this.CardText = cardText;
    }

    public String getAttack()
    {
        return  this.Attack;
    }

    public void setAttack(String attack)
    {
        this.Attack = attack;
    }

    public  String getDefence()
    {
        return  this.Defence;
    }

    public void setDefence(String defence)
    {
        this.Defence = defence;
    }

    public String getSet()
    {
        return this.Set;
    }

    public void setSet(String set)
    {
        this.Set = set;
    }

    @Override
    public  String toString() {
        String cardData;
        cardData = "Name: " + getName() + " Type of Card: " + getType()
                + " Level: " + getCardLevel() + " Attribute: " + getCardAttribute()
                + "Type (if monster): " + getCardType() + " Card Text: " + getCardText() +
                " Attack: " + getAttack() + " Defence: " + getDefence() + " Set: " + getSet();
        return  cardData;
    }
    }















