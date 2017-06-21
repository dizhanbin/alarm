#import "FlowBoxs.h"
@implementation FlowBoxs
+(NSString*) getBox:(Event)event{
        switch(event){
         case REQ_ALARM_NEXT: return @"alarm_set.xml";
         } 
         return nil; 
} 
@end 
