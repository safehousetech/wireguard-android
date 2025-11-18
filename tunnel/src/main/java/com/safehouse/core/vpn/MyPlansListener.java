package com.safehouse.core.vpn;


import com.safehouse.core.model.MyPlanResponse;

public interface MyPlansListener {
    void onMyPlanSuccess(MyPlanResponse myPlanResponse);
    void onMyPlanFailure(String errorMessage);
}
