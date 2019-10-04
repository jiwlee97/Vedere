package com.project.vedere;

import android.Manifest;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.project.vedere.controller.SensorTestActivity;
import com.project.vedere.managers.AngleManager;
import com.project.vedere.model.DirectionInfo;
import com.skt.Tmap.TMapPoint;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.PriorityQueue;
import java.util.Queue;

@RunWith(AndroidJUnit4.class)
public class AngleManagerTest {
    private AngleManager angleManager;

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.VIBRATE);

    @Rule
    public ActivityTestRule<SensorTestActivity> intentTestRule = new ActivityTestRule<>(SensorTestActivity.class);
    @Before
    public void setup() {
        SensorTestActivity activity = intentTestRule.getActivity();
        angleManager = new AngleManager(activity);
    }

    @Test
    public void test() {
        Queue<DirectionInfo> queue = new PriorityQueue<DirectionInfo>();
//
//        float azimuth = 176.77115f;
        TMapPoint startPoint = new TMapPoint(37.569758,12.6977022);
        TMapPoint arrivePoint = new TMapPoint(37.570594,12.6997589);
        TMapPoint priorStartPoint = new TMapPoint(37.568000,12.6977300);
        int turnInfo = 0;
        DirectionInfo dirInfo = new DirectionInfo(startPoint,arrivePoint,priorStartPoint,turnInfo);
        queue.add(dirInfo);
        angleManager.setAllTurnInfo(queue);
    }
}
