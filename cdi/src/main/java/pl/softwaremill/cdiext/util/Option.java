package pl.softwaremill.cdiext.util;

public class Option<T> {
    
    private final boolean error;
    private final boolean object;
    private final T result;
    private final String errorMessage;
    
    private Option(T object){
        this.result = object;
        this.object = true;
        this.error = false;
        this.errorMessage = null;
    }
    
    private Option(String errorMessage){
        this.errorMessage = errorMessage;
        this.object = false;
        this.error = true;
        this.result = null;
    }
    
    public static <T> Option<T> object(T object){
        return new Option<T>(object);
    }
    
    public static Option error(String errorMessage){
        return new Option(errorMessage);
    }
    
    public boolean isError() {
        return error;
    }

    public boolean isObject() {
        return object;
    }

    public T getResult() {
        return result;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}
