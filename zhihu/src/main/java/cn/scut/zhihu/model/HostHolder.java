package cn.scut.zhihu.model;

import org.springframework.stereotype.Component;

/**
 * User: yinkai
 * Date: 2019-11-10
 * Time: 9:44
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
