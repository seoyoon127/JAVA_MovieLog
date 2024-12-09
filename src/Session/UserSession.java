package Session;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserSession {
    private String userId;
    private final List<UserSessionListener> listeners = new CopyOnWriteArrayList<>();

    // userId를 변경할 때 모든 구독자에게 알림 -> userId 바로 갱신
    public void setUserId(String userId) {
        this.userId = userId;
        for (UserSessionListener listener : listeners) {
            listener.onUserIdChanged(userId);
        }
    }

    public String getUserId() {
        return userId;
    }

    public void addListener(UserSessionListener listener) {
        listeners.add(listener);
    }

    public void removeListener(UserSessionListener listener) {
        listeners.remove(listener);
    }

    public interface UserSessionListener {
        void onUserIdChanged(String newUserId);
    }
}
