package hphuc.recordaudio.database;

public interface ConfigSaver {

    String CONFIG_PAGER = "config_pager";

    String CONFIG_SETTING_SAVED_IS_FIRST_LOGIN_APP = "config_setting_saved_is_first_login_app";

    void save(String key, Object data);

    <T> T get(String key);

    void delete(String key);

    void deleteAll();
}
