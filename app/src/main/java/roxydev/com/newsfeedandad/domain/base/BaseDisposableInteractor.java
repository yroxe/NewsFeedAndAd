package roxydev.com.newsfeedandad.domain.base;

import android.arch.lifecycle.MutableLiveData;

import io.reactivex.disposables.CompositeDisposable;
import roxydev.com.newsfeedandad.domain.model.DataResult;

public class BaseDisposableInteractor<Object> {

    public CompositeDisposable subscriptions = new CompositeDisposable();

    public MutableLiveData<DataResult> state = new MutableLiveData<>();

    public void cancel(){
        subscriptions.clear();
    }


}
