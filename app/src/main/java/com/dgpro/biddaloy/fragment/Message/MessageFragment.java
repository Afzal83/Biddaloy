package com.dgpro.biddaloy.fragment.Message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.fragment.Message.InboxFragment;
import com.dgpro.biddaloy.fragment.Message.OutboxFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Babu on 12/27/2017.
 */

public class MessageFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_message,container,false);

        ViewPager viewPager = (ViewPager) mView.findViewById(R.id.message_viewpager);
        setupViewPager(viewPager);
        TabLayout tabs = (TabLayout) mView.findViewById(R.id.message_tabs);
        tabs.setupWithViewPager(viewPager);

        return mView;
    }
    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {

        MessagePagerAdapter adapter = new MessagePagerAdapter(getChildFragmentManager());
        adapter.addFragment(new InboxFragment(), "Inbox");
        adapter.addFragment(new OutboxFragment(), "Outbox");
        viewPager.setAdapter(adapter);

    }

    static class MessagePagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MessagePagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
