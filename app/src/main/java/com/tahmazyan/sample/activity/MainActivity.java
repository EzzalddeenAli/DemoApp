
package com.tahmazyan.sample.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.tahmazyan.R;
import com.tahmazyan.core.activity.BaseActivityWithViewModel;
import com.tahmazyan.core.viewModel.ViewModelBase;
import com.tahmazyan.databinding.ActivityMainBinding;
import com.tahmazyan.sample.viewModel.UsersListViewModel;

public class MainActivity extends BaseActivityWithViewModel<ActivityMainBinding, UsersListViewModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<UsersListViewModel> getViewModelClass() {
        return UsersListViewModel.class;
    }

    @Override
    protected void onCreated(@Nullable Bundle savedInstanceState) {
        super.onCreated(savedInstanceState);
        initNavController();
    }

    private void initNavController() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

            }
        });
    }
}
