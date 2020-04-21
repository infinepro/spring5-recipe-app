package guru.springframework.converters;

public interface Converter<D, C> {

    D convert (C convertObject);
}
