package cn.mokier.soulitem.spigotutils.chat;

public enum ChatTypes {
    /**
     * MESSAGE  聊天框
     * BROADCAST  公告
     * ACTIONBAR 动作条
     * TITLE 屏幕标题
     */
    MESSAGE("[message]", 1), BROADCAST("[broadcast]", 2), ACTIONBAR("[actionBar]", 3), TITLE("[title]", 4);

    private String name;
    private int index;

    ChatTypes(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for(ChatTypes type : ChatTypes.values()) {
            if(type.getIndex() == index) {
                return type.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
