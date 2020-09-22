package one.hendra.androidnetworkbase;

import com.google.gson.annotations.JsonAdapter;

public class ApiResponse<T> {

    public int code;
    public T data;
    public String message;

}
