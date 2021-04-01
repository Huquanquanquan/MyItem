package cn.mokier.soulitem.spigotutils.contain;

public enum ContainNodeTypes {
    /**
     * VIEW  装饰
     * CMD  执行
     * START 开始
     * END 结束
     * CLOSE 关闭
     */
    VIEW("装饰", 1), CMD("执行", 2), START("开始", 3), END("结束", 4), CLOSE("关闭", 5);

    private String name;
    private int index;

    ContainNodeTypes(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for(ContainNodeTypes type : ContainNodeTypes.values()) {
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
