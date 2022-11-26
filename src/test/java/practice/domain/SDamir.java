package practice.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SDamir {
    public String name;
    public String surname;
    public Address address;
    @SerializedName("favorite_music")
    public List<String> favoriteMusic;
}
