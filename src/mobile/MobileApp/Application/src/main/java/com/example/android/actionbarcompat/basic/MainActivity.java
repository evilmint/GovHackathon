/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.actionbarcompat.basic;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * This sample shows you how to use ActionBarCompat to create a basic Activity which displays
 * action items. It covers inflating items from a menu resource, as well as adding an item in code.
 *
 * This Activity extends from {@link AppCompatActivity}, which provides all of the function
 * necessary to display a compatible Action Bar on devices running Android v2.1+.
 */

@EActivity(R.layout.sample_main)
public class MainActivity extends AppCompatActivity {
    @ViewById(R.id.mainTextView)
    TextView mainTextView;

    @Click
    void myButton() {
        mainTextView.setText("Hello!!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (mainTextView != null) {
            mainTextView.setText("waow");
        }

        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        // "Emit" any data to the subscriber
                        sub.onNext("a");
                        sub.onNext("b");
                        sub.onNext("c");
                        // Trigger the completion of the event
                        sub.onCompleted();
                    }
                }
        );

        Observer<String> mySubscriber = new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onNext: ");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        myObservable.subscribe(mySubscriber);

    }

    // BEGIN_INCLUDE(create_menu)
    /**
     * Use this method to instantiate your menu, and add your items to it. You
     * should return true if you have added items to it and want the menu to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate our menu from the resources by using the menu inflater.
        getMenuInflater().inflate(R.menu.main, menu);

        // It is also possible add items here. Use a generated id from
        // resources (ids.xml) to ensure that all menu ids are distinct.
        MenuItem locationItem = menu.add(0, R.id.menu_location, 0, R.string.menu_location);
        locationItem.setIcon(R.drawable.ic_action_location);

        // Need to use MenuItemCompat methods to call any action item related methods
        MenuItemCompat.setShowAsAction(locationItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }
    // END_INCLUDE(create_menu)

    // BEGIN_INCLUDE(menu_item_selected)
    /**
     * This method is called when one of the menu items to selected. These items
     * can be on the Action Bar, the overflow menu, or the standard options menu. You
     * should return true if you handle the selection.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                // Here we might start a background refresh task
                return true;

            case R.id.menu_location:
                // Here we might call LocationManager.requestLocationUpdates()
                return true;

            case R.id.menu_settings:
                // Here we would open up our settings activity
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // END_INCLUDE(menu_item_selected)
}
