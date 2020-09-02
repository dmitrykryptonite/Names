package com.example.names.presentation.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.names.R;
import com.example.names.presentation.presenters.MainPresenter;
import com.example.names.presentation.ui.adapters.MainFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Names");
        TabLayout tabs = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.viewPager);
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_all)
            deleteAllNames();
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNames() {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                presenter.onBtnDeleteClicked();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure_delete_all_names)
                .setPositiveButton(getString(android.R.string.yes), dialogClickListener)
                .setNegativeButton(getString(android.R.string.no), dialogClickListener)
                .show();
    }

    @Override
    public void showSuccessMassage(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMassage(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        presenter.releasePresenter();
        presenter = null;
        super.onDestroy();
    }
}
