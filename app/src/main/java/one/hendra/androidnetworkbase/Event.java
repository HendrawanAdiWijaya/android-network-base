package one.hendra.androidnetworkbase;

import android.util.Log;

public class Event<T> {

    private boolean mHasBeenHandled;

    private T mContent;

    public Event(T content) {
        mContent = content;
    }

    public boolean isHasBeenHandled() {
        return mHasBeenHandled;
    }

    public void setHasBeenHandled(boolean hasBeenHandled) {
        mHasBeenHandled = hasBeenHandled;
    }

    public T getContent() {
        return mContent;
    }

    public T getContentAndHandle() {
        return mContent;
    }
}
