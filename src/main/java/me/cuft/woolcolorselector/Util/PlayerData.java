package me.cuft.woolcolorselector.Util;

public class PlayerData
{
    private String uuid;
    private String woolColor;

    public PlayerData(String uuid, String woolColor) {
        this.uuid = uuid;
        this.woolColor = woolColor;
    }

    public String getWoolColor() {
        return woolColor;
    }

    public void setWoolColor(String woolColor) {
        this.woolColor = woolColor;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
