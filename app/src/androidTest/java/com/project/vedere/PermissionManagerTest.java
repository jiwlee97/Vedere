package com.project.vedere;

import android.Manifest;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.project.vedere.controller.MainActivity;
import com.project.vedere.utils.PermissionManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PermissionManagerTest {
    private PermissionManager permissionManager;

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public ActivityTestRule<MainActivity> intentTestRule = new ActivityTestRule<>(MainActivity.class);
    @Before
    public void setup() {
        MainActivity activity = intentTestRule.getActivity();
        permissionManager = new PermissionManager(activity);
    }

    @Test
    public void test() {
        assertTrue(permissionManager.check(Manifest.permission.ACCESS_FINE_LOCATION));
    }
}
