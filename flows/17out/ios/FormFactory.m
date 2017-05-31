/* create my 17 */
#import "FormFactory.h"

@implementation FormFactory

+(NSString*) getForm:(Event) event{
      switch(event){
          case FORM_MAIN: return @"FormMain";
          case FORM_FLASH: return @"FormFlash";
          case FORM_EDIT: return @"FormEdit";
     }
     return nil;
}

@end
