package interfaces;

public interface Validatable<T> {
    void validate(T obj);

    default void validated(T obj) {
        validate(obj);
    }
    static boolean isNullOrBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
