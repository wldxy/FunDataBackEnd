package fundata.repository;

import fundata.configure.Constants;
import fundata.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by huang on 17-3-9.
 */
@Repository
public class TokenRepository {
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("redisTemplate")
    public void setRedis(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        //泛型设置成Long后必须更改对应的序列化方案
        redisTemplate.setKeySerializer(new JdkSerializationRedisSerializer());
    }

    public Token createToken(long userId) {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        Token model = new Token(userId, token);
        //存储到redis并设置过期时间
        redisTemplate.boundValueOps(userId).set(token, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return model;
    }

    public Token getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        long userId = Long.parseLong(param[0]);
        String token = param[1];
        return new Token(userId, token);
    }

    public boolean checkToken(Token model) {
        if (model == null) {
            return false;
        }
        String token = redisTemplate.boundValueOps(model.getUserId()).get().toString();
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redisTemplate.boundValueOps(model.getUserId()).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return true;
    }

    public void deleteToken(long userId) {
        redisTemplate.delete(userId);
    }
}
