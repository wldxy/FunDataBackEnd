package fundata.model;

/**
 * Created by huang on 17-3-9.
 */
public class Token {
    //用户id
    private long userId;

    //随机生成的uuid
    private String token;

    public Token(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
