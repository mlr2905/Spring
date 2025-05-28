package spring.web.api.repository;

public interface ICacheRepository {

    void createCacheEntity(String key, String value);

    String getCacheEntity(String key);

    void updateCacheEntity(String key, String value);

    Boolean isKeyExist(String key);

    void removeKey(String key);
}
