/**
 * Description:
 * User: zhouq
 * Date: 2016/11/15
 */
public enum ModuleKeyEnum {
    BANNERS("MK_BANNERS"),
    COMPONENTS("MK_COMPONENTS"),
    BEST_APPS("MK_BEST_APPS"),
    SERVICE_NUMBER("MK_SERVICE_NUMBER");

    private final String moduleKey;

    private ModuleKeyEnum(String moduleKey) {
        this.moduleKey = moduleKey;
    }

    public String getModuleKey() {
        return this.moduleKey;
    }

    public String toString() {
        return "ModuleKeyEnum{moduleKey=\'" + this.moduleKey + '\'' + '}';
    }

    public static ModuleKeyEnum getByModuleKey(String moduleKey) {
        ModuleKeyEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ModuleKeyEnum moduleKeyEnum = var1[var3];
            if(moduleKeyEnum.getModuleKey().equals(moduleKey)) {
                return moduleKeyEnum;
            }
        }

        return null;
    }
}
