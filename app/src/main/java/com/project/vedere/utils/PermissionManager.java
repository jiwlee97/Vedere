package com.project.vedere.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.project.vedere.interfaces.PermissionCallback;

import java.util.ArrayList;

/**
 * permission을 처리하기 위한 클래스
 * Reference: TMap API sample code
 * http://tmapapi.sktelecom.com/main.html#android/sample/androidSample.sdk_download
 */
public class PermissionManager {
    private Activity mAcitivity;
    private ArrayList<PermissionCallback> permissionCallbacks;

    public PermissionManager (Activity activity) {
        mAcitivity = activity;
    }

    /**
     * permission허가 여부 체크
     * @param permission 확인하고자 하는 퍼미션
     * @return true시 퍼미션이 허가돼었음을 의미
     */
    public boolean check(String permission) {
        int permissionState = ContextCompat.checkSelfPermission(mAcitivity, permission);
        return (permissionState == PackageManager.PERMISSION_GRANTED || Build.VERSION.SDK_INT < 23);
    }

    /**
     * 퍼미션을 요청
     * @param permissions 요청하고자 하는 퍼미션
     * @param callback 퍼미션 요청에 대한 응답을 처리하는 콜백
     */
    public void request(String[] permissions, PermissionCallback callback) {
        permissionCallbacks.add(callback);
        // Todo 안내문구
        ActivityCompat.requestPermissions(mAcitivity, permissions, permissionCallbacks.size()-1);
    }

    /**
     * 퍼미션 요청의 응답 처리
     * @param requestCode 퍼미션 요청시 index
     * @param grantResults 퍼미션 응답 결과
     */
    public void setResponse(int requestCode, int[] grantResults) {
        int cntGrant = 0;
        if (grantResults.length > 0) {
            for (int grantRusult : grantResults) {
                if (grantRusult == PackageManager.PERMISSION_GRANTED) {
                    cntGrant++;
                }
            }

            if (grantResults.length == cntGrant) {
                permissionCallbacks.get(requestCode).granted();
            }
            else {
                permissionCallbacks.get(requestCode).denied();
            }
        }
        else {
            permissionCallbacks.get(requestCode).denied();
        }
    }
}
