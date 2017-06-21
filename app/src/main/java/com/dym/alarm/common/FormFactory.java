/* create my 17 */
package com.dym.alarm.common;
import com.dym.alarm.forms.*;
public class FormFactory{
  public static Class getForm(Event event){
      switch(event){
          case FORM_MAIN: return FormMain.class;
          case FORM_FLASH: return FormFlash.class;
          case FORM_EDIT: return FormEdit.class;
          case FORM_SETTING: return FormSetting.class;
          case FORM_SOUND_SET: return FormSoundSet.class;
          }
      return null;
  }
  public static String getFormName(Event event){
      switch(event){
          case FORM_MAIN: return "首页";
          case FORM_FLASH: return "启动页";
          case FORM_EDIT: return "提醒编辑页";
          case FORM_SETTING: return "设置页面";
          case FORM_SOUND_SET: return "选择铃声";
          }
      return "";
  }
}
