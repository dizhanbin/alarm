/* create my 17 */
#import "event.h"
@implementation EventUtil

+(Event) event_by_str:(NSString *)eventstr{
  if( [eventstr isEqual:@"FORM_ABOUT"] ) return FORM_ABOUT;
  if( [eventstr isEqual:@"FORM_EDIT"] ) return FORM_EDIT;
  if( [eventstr isEqual:@"FORM_FLASH"] ) return FORM_FLASH;
  if( [eventstr isEqual:@"FORM_HELP"] ) return FORM_HELP;
  if( [eventstr isEqual:@"FORM_MAIN"] ) return FORM_MAIN;
  if( [eventstr isEqual:@"FORM_OPENSOURCE"] ) return FORM_OPENSOURCE;
  if( [eventstr isEqual:@"FORM_SETTING"] ) return FORM_SETTING;
  if( [eventstr isEqual:@"FORM_SOUND_SET"] ) return FORM_SOUND_SET;
  if( [eventstr isEqual:@"REP_ALARM_LIST"] ) return REP_ALARM_LIST;
  if( [eventstr isEqual:@"REP_ALARM_SAVE_FAIL"] ) return REP_ALARM_SAVE_FAIL;
  if( [eventstr isEqual:@"REP_ALARM_SAVE_SUCCESS"] ) return REP_ALARM_SAVE_SUCCESS;
  if( [eventstr isEqual:@"REP_OPENSOURCE_LIST"] ) return REP_OPENSOURCE_LIST;
  if( [eventstr isEqual:@"REQ_ALARM_LIST"] ) return REQ_ALARM_LIST;
  if( [eventstr isEqual:@"REQ_ALARM_NEXT"] ) return REQ_ALARM_NEXT;
  if( [eventstr isEqual:@"REQ_ALARM_SAVE"] ) return REQ_ALARM_SAVE;
  if( [eventstr isEqual:@"REQ_DIALOG_INFO"] ) return REQ_DIALOG_INFO;
  if( [eventstr isEqual:@"REQ_DIALOG_SURE"] ) return REQ_DIALOG_SURE;
  if( [eventstr isEqual:@"REQ_FORM_BACK"] ) return REQ_FORM_BACK;
  if( [eventstr isEqual:@"REQ_FROM_CLEAR"] ) return REQ_FROM_CLEAR;
  if( [eventstr isEqual:@"REQ_NONE"] ) return REQ_NONE;
  if( [eventstr isEqual:@"REQ_OPENSOURCE_LIST"] ) return REQ_OPENSOURCE_LIST;
  if( [eventstr isEqual:@"REQ_PUSH_TO_AND_CLEAR_ALL"] ) return REQ_PUSH_TO_AND_CLEAR_ALL;
  if( [eventstr isEqual:@"REQ_TOAST"] ) return REQ_TOAST;
  if( [eventstr isEqual:@"REQ_WAITTING_HIDE"] ) return REQ_WAITTING_HIDE;
  if( [eventstr isEqual:@"REQ_WAITTING_SHOW"] ) return REQ_WAITTING_SHOW;
  if( [eventstr isEqual:@"REQ_SOUND_TEST_STOP"] ) return REQ_SOUND_TEST_STOP;
  return -1;
}

+(NSString*)str_by_event:(Event)event{
  switch(event){

  case FORM_ABOUT : return @"FORM_ABOUT";
  case FORM_EDIT : return @"FORM_EDIT";
  case FORM_FLASH : return @"FORM_FLASH";
  case FORM_HELP : return @"FORM_HELP";
  case FORM_MAIN : return @"FORM_MAIN";
  case FORM_OPENSOURCE : return @"FORM_OPENSOURCE";
  case FORM_SETTING : return @"FORM_SETTING";
  case FORM_SOUND_SET : return @"FORM_SOUND_SET";
  case REP_ALARM_LIST : return @"REP_ALARM_LIST";
  case REP_ALARM_SAVE_FAIL : return @"REP_ALARM_SAVE_FAIL";
  case REP_ALARM_SAVE_SUCCESS : return @"REP_ALARM_SAVE_SUCCESS";
  case REP_OPENSOURCE_LIST : return @"REP_OPENSOURCE_LIST";
  case REQ_ALARM_LIST : return @"REQ_ALARM_LIST";
  case REQ_ALARM_NEXT : return @"REQ_ALARM_NEXT";
  case REQ_ALARM_SAVE : return @"REQ_ALARM_SAVE";
  case REQ_DIALOG_INFO : return @"REQ_DIALOG_INFO";
  case REQ_DIALOG_SURE : return @"REQ_DIALOG_SURE";
  case REQ_FORM_BACK : return @"REQ_FORM_BACK";
  case REQ_FROM_CLEAR : return @"REQ_FROM_CLEAR";
  case REQ_NONE : return @"REQ_NONE";
  case REQ_OPENSOURCE_LIST : return @"REQ_OPENSOURCE_LIST";
  case REQ_PUSH_TO_AND_CLEAR_ALL : return @"REQ_PUSH_TO_AND_CLEAR_ALL";
  case REQ_TOAST : return @"REQ_TOAST";
  case REQ_WAITTING_HIDE : return @"REQ_WAITTING_HIDE";
  case REQ_WAITTING_SHOW : return @"REQ_WAITTING_SHOW";
  case REQ_SOUND_TEST_STOP : return @"REQ_SOUND_TEST_STOP";
}

  return @"not define";
}

@end
