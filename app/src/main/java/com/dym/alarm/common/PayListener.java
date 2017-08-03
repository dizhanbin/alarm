package com.dym.alarm.common;

/**
 * Created by dizhanbin on 17/5/16.
 */
public interface PayListener {

    public static enum PR{


        VIP_USER,    //普通vip用户
        FREE_USER,   //普通用户
        QUERY_FAIL,  //查询失败
        INIT_SUCCESS,//初始化成功
        PRODUT_HAD,//含有订单
        PRODUT_NONE,//无订单
        PURCHASE_AUTORENEW,//订单续订中
        PURCHASE_CANCEL,//订单已取消
        PURCHASE_BACK,//订单退款
        PURCHASE_TIMEOUT,//订单超期
        PURCHASE_INTIME,//订单在有效期内
        BUY_SUCCESS,
        BUY_FAIL,

    }

    public void pay_do(PR pr, Object value);

}
