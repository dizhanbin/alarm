/* create my 17 */
#import <Foundation/Foundation.h>
typedef enum{

  FORM_ABOUT,//关于
  FORM_EDIT,//闹钟编辑页
  FORM_FLASH,//启动页
  FORM_HELP,//帮助页
  FORM_MAIN,//首页
  FORM_OPENSOURCE,//开源库
  FORM_SETTING,//设置页面
  FORM_SOUND_SET,//设置铃声
  REP_ALARM_LIST,//闹钟列表
  REP_ALARM_REMOVE_SUCCESS,//删除成功
  REP_ALARM_SAVE_FAIL,//闹钟保存失败
  REP_ALARM_SAVE_SUCCESS,//闹钟保存成功
  REP_OPENSOURCE_LIST,//返回开源库列表
  REQ_ALARM_LIST,//闹钟列表
  REQ_ALARM_NEXT,//生成下一个闹钟
  REQ_ALARM_REMOVE,//删除指定闹钟
  REQ_ALARM_SAVE,//保存闹钟
  REQ_DIALOG_INFO,//消息提示框
  REQ_DIALOG_SURE,//弹出确认对话框
  REQ_FORM_BACK,//页面返回
  REQ_FROM_CLEAR,//清空堆栈中所有页面
  REQ_NONE,//空事件
  REQ_OPENSOURCE_LIST,//请求开源库列表
  REQ_PUSH_TO_AND_CLEAR_ALL,//进入并清除所有界面
  REQ_SOUND_CHANGED,//铃声调整
  REQ_SOUND_TEST_STOP,//测试音频停止
  REQ_TOAST,//提示信息
  REQ_WAITTING_HIDE,//隐藏等待框
  REQ_WAITTING_SHOW,//等待框
  REP_BUY_SUCCESS,//购买成功
  REP_BUY_FAIL//购买失败
} Event;

@interface EventUtil : NSObject

+(Event) event_by_str:(NSString *)eventstr;

+(NSString*)str_by_event:(Event)event;

@end
