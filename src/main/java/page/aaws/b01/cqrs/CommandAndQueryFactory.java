package page.aaws.b01.cqrs;

public interface CommandAndQueryFactory {
    <T> T create(Object input, Class<T> requiredType);
}
