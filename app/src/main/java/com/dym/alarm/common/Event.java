/* create my 17 */
package com.dym.alarm.common;
public enum Event{

  REQ_NONE,//空事件
  REQ_FORM_BACK,//页面返回
  REQ_TOAST,//提示信息
  REQ_WAITTING_SHOW,//等待框
  REQ_WAITTING_HIDE,//隐藏等待框
  REQ_DIALOG_INFO,//消息提示框
  REQ_DIALOG_SURE,//弹出确认对话框
  REQ_FROM_CLEAR,//清空堆栈中所有页面
  FORM_MAIN,//首页
  FORM_FLASH,//启动页
  REQ_PUSH_TO_AND_CLEAR_ALL,//进入并清除所有界面
  FORM_EDIT,//闹钟编辑页
  REQ_ALARM_LIST,//闹钟列表
  REP_ALARM_LIST,//闹钟列表
  REQ_ALARM_SAVE,//保存闹钟
  REP_ALARM_SAVE_SUCCESS,//闹钟保存成功
  REP_ALARM_SAVE_FAIL,//闹钟保存失败
  REQ_ALARM_NEXT,//生成下一个闹钟
  FORM_SETTING;//设置页面

}
