package life.iGuaDa.community.enums;

/**
 * Created by codedrinker on 2019/6/14.
 * 消息状态枚举
 * 分为已读或未读
 */
public enum NotificationStatusEnum {
    UNREAD(0), READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
