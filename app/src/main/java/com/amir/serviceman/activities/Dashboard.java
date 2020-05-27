package com.amir.serviceman.activities;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amir.serviceman.R;
import com.amir.serviceman.core.BaseActivity;
import com.amir.serviceman.databinding.ActivityDashboardBinding;
import com.amir.serviceman.fragments.Profile;
import com.amir.serviceman.fragments.customer.MyJob;
import com.amir.serviceman.fragments.customer.SerachProviders;
import com.amir.serviceman.fragments.provider.MyBid;
import com.amir.serviceman.fragments.provider.SearchJob;
import com.google.android.material.tabs.TabLayout;

public class Dashboard extends BaseActivity {

    private ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_dashboard);
        if (sp.getString(USER_TYPE).equals(PROVIDER)){
            setUpContractorTab();
            switchToFragment(new SearchJob());

        }else {
            setUpCustomerTab();
            switchToFragment(new MyJob());
        }
        setTabSelectedListner();
    }


    private void setUpCustomerTab() {
        setCustomTabView("My Jobs", R.drawable.my_job_selector);
        setCustomTabView("Search", R.drawable.search_selector);
        setCustomTabView("Profile", R.drawable.profile_selector);
    }

    private void setUpContractorTab(){
        setCustomTabView("Search Jobs", R.drawable.search_selector);
        setCustomTabView("My bids",R.drawable.my_job_selector);
        setCustomTabView("Profile", R.drawable.profile_selector);

    }

    private void setCustomTabView(String text, int icon) {
        LinearLayout customView = (LinearLayout) LayoutInflater.from(Dashboard.this).inflate(R.layout.item_custom_tab, null);
        ((ImageView) customView.findViewById(R.id.ivIcon)).setImageResource(icon);
        ((TextView) customView.findViewById(R.id.tvText)).setText(text);

        binding.tabs.addTab(binding.tabs.newTab().setCustomView(customView));
    }

    private void switchToFragment(Fragment fragment){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.flContainer.getId(), fragment)
                .commit();

    }

    private void setTabSelectedListner(){
        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (sp.getString(USER_TYPE).equals(PROVIDER)){
                    switch (tab.getPosition()){
                        case 0 :
                            switchToFragment(new SearchJob());
                            break;
                        case 1:
                            switchToFragment(new MyBid());
                            break;
                        case 2:
                            switchToFragment(new Profile());
                            break;
                    }
                }
                else {
                    switch (tab.getPosition()){
                        case 0 :
                            switchToFragment(new MyJob());
                            break;
                        case 1:
                            switchToFragment(new SerachProviders());
                            break;
                        case 2:
                            switchToFragment(new Profile());
                            break;
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
