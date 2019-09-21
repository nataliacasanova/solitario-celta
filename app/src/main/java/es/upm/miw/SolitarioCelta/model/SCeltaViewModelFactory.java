package es.upm.miw.SolitarioCelta.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SCeltaViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @NonNull
    private final Application application;

    private final long id;

    public SCeltaViewModelFactory(@NonNull Application application, long id) {
        this.application = application;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == SCeltaViewModel.class) {
            return (T) new SCeltaViewModel(application);
        }
        return null;
    }
}
