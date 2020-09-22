package one.hendra.androidnetworkbase;


import androidx.annotation.Nullable;

public class Resource<T> {

    @Nullable
    public T data;
    @Nullable
    public Integer code;
    public Status status;

    public Resource(@Nullable T data, @Nullable Integer code, Status status) {
        this.data = data;
        this.code = code;
        this.status = status;
    }

    public static <T> Resource<T> success(@Nullable T data, @Nullable Integer code){
        return new Resource<>(data, code, Status.SUCCESS);
    }

    public static <T> Resource<T> networkError(@Nullable T data)
    {
        return new Resource<>(data, null, Status.NETWORK_ERROR);
    }

    public static <T> Resource<T> serverError(@Nullable T data)
    {
        return new Resource<>(data, null, Status.SERVER_ERROR);
    }

    public static <T> Resource<T> parseError(@Nullable T data)
    {
        return new Resource<>(data, null, Status.PARSE_ERROR);
    }

    public static <T> Resource<T> loading(@Nullable T data)
    {
        return new Resource<>(data, null, Status.LOADING);
    }

    public enum  Status {

        SUCCESS, LOADING, NETWORK_ERROR, SERVER_ERROR, PARSE_ERROR

    }


}
