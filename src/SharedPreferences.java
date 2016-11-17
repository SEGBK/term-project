public interface SharedPreferences {
    abstract String getString(String key);

    public static interface Editor {
        abstract Editor putString(String key, String value);
        abstract boolean commit();
    }
}