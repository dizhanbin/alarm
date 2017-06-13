package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

/**
 * 开始节点
 * @author dzb
 *
 */
public class EventStart extends FlowPoint {

	static final String key_params = "params";
	static final String key_static = "static";

	@Override
	public boolean isStart() {
		return true;
	}


	boolean isStatic;
	@Override
	public void run(FlowBox flowBox) throws Exception {

		flowBox.log("EventStart params:%s",flowBox.getFlowParam());
		String paramstr = params.get(key_params);
		if( !isNull(paramstr) ) {
			flowBox.setValue(paramstr, flowBox.getFlowParam());
		}
		String staticstr = params.get(key_static);

		if( !isNull(staticstr) && "true".equals(staticstr) )
		{
			isStatic = true;
			flowBox.setStatic(true);

		}

		flowBox.notifyFlowContinue();

	}


//	public FlowPoint getChild(FlowBox flowBox) {
//
//
//		if( !isStatic )
//			return super.getChild(flowBox);
//		else {
//
//			Line line;
//			for (int i = 0; i < lines_child.size(); i++) {
//
//				line = lines_child.get(i);
//				if( "static".equals(line.conditions) )
//				{
//					return line.child;
//				}
//
//			}
//
//		}
//
//		return null;
//
//	}
//
	

	

}
