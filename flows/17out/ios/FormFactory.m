/* create my 17 */
#import "FormFactory.h"

@implementation FormFactory

+(NSString*) getForm:(Event) event{
      switch(event){
          case FORM_MAIN: return @"FormMain";
          case FORM_FLASH: return @"FormFlash";
          case FORM_EDIT: return @"FormEdit";
          case FORM_SETTING: return @"FormSetting";
          case FORM_SOUND_SET: return @"FormSoundSet";
     }
     return nil;
}

@end
