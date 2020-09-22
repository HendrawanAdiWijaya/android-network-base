package one.hendra.androidnetworkbase;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NetworkBoundResource<T, N> {

    private MediatorLiveData<Event<Resource<T>>> result;

    public NetworkBoundResource() {
        result = new MediatorLiveData<>();
        final LiveData<T> local = loadFromLocal();
        setValue(Resource.<T>loading(null));
        if (local == null) {
            fetchNetwork();
        } else {
            result.addSource(local, new Observer<T>() {
                @Override
                public void onChanged(T t) {
                    setValue(Resource.loading(t));
                    result.removeSource(local);
                    fetchNetwork();
                }
            });
        }
    }



    void fetchNetwork() {
        Call<ApiResponse<N>> call = loadFromNetwork();
        if (call == null) {
            setValue(Resource.<T>serverError(null));
        } else {
            call.enqueue(new Callback<ApiResponse<N>>() {
                @Override
                public void onResponse(Call<ApiResponse<N>> call, Response<ApiResponse<N>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().data!=null){
                            saveCallResult(networkMapper(response.body().data));
                        }
                        setValue(Resource.success(networkMapper(response.body().data), response.body().code));
                    } else {
                        setValue(Resource.<T>serverError(null));
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<N>> call, Throwable t) {
                    if (t instanceof IOException) {
                        setValue(Resource.<T>networkError(null));
                    } else {
                        setValue(Resource.<T>parseError(null));
                    }
                }
            });
        }
    }

    private void setValue(Resource<T> resource) {
        result.setValue(new Event<>(resource));
    }

    public void saveCallResult(T t){

    }

    public abstract T networkMapper(N n);

    public abstract LiveData<T> loadFromLocal();

    public abstract Call<ApiResponse<N>> loadFromNetwork();

    public LiveData<Event<Resource<T>>> asLiveData() {
        return result;
    }

}
