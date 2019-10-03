package com.project.vedere;

import android.Manifest;
import android.graphics.Point;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.project.vedere.controller.MainActivity;
import com.project.vedere.managers.AngleManager;
import com.skt.Tmap.TMapPoint;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AngleManagerTest {
    private AngleManager angleManager;

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.VIBRATE);

    @Rule
    public ActivityTestRule<MainActivity> intentTestRule = new ActivityTestRule<>(MainActivity.class);
    @Before
    public void setup() {
        MainActivity activity = intentTestRule.getActivity();
        angleManager = new AngleManager(activity);
    }

    @Test
    public void test() {
        float azimuth = 176.77115f;
        TMapPoint startPoint = new TMapPoint(37569758,126977022);
        TMapPoint arrivePoint = new TMapPoint(37570594,126997589);
        angleManager.setStartDirection(azimuth,startPoint,arrivePoint);
    }
}
