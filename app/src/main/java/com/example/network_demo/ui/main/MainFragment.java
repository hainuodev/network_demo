package com.example.network_demo.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.network_demo.R;
import com.example.network_demo.api.ResultBean;
import com.example.network_demo.api.WanApiInterface;
import com.google.gson.Gson;
import com.ynby.network.BYNetworkApi;
import com.ynby.network.observer.BaseObserver;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
        BYNetworkApi.getService(WanApiInterface.class).getBanner()
                .compose(BYNetworkApi.getInstance().applySchedulers(new BaseObserver<ResultBean>() {
                    @Override
                    public void onSuccess(ResultBean resultBean) {
                        Log.e("TAG", "onSuccess: " + new Gson().toJson(resultBean));
                    }

                    @Override
                    public void onFailure(Throwable e) {

                    }
                }));
    }

}