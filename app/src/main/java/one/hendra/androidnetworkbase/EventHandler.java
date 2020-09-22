package one.hendra.androidnetworkbase;

import android.app.Activity;

import com.google.android.material.snackbar.Snackbar;

public abstract class EventHandler<T> {

    Activity mActivity;
    Event<Resource<T>> mEvent;

    public EventHandler(Event<Resource<T>> event) {
        this(null, event);
    }

    public EventHandler(Activity activity, Event<Resource<T>> event) {
        mActivity = activity;
        mEvent = event;
        Resource<T> resource = event.getContent();
        switch (resource.status) {
            case SUCCESS:
                if (resource.code == null) {
                    onParseError();
                    globalAction();
                    return;
                }
                onSuccess(resource.code, resource.data);
                globalAction();
                break;
            case NETWORK_ERROR:
                onNetworkError();
                globalAction();
                break;
            case SERVER_ERROR:
                onServerError();
                globalAction();
                break;
            case PARSE_ERROR:
                onParseError();
                globalAction();
                break;
        }
    }

    public void onParseError(){
        if (mActivity!=null)
        showSnackbar(mActivity.getString(R.string.server_error));
    }

    public void onServerError(){
        if (mActivity!=null)
        showSnackbar(mActivity.getString(R.string.server_error));
    }

    public void onNetworkError(){
        if (mActivity!=null)
        showSnackbar(mActivity.getString(R.string.network_error));
    }

    public abstract void onSuccess(Integer code, T t);

    public void globalAction(){

    }

    public void showSnackbar(String message, int length) {
        if (!mEvent.isHasBeenHandled()&&mActivity!=null)
            Snackbar.make(mActivity.findViewById(android.R.id.content), message, length).show();
        mEvent.setHasBeenHandled(true);
    }

    public void showSnackbar(String message){
        showSnackbar(message, Snackbar.LENGTH_SHORT);
    }

}
