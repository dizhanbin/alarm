package com.dym.alarm.flows;


import com.dym.alarm.common.Event;
import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;
import com.dym.alarm.common.MessageCenter;

import java.util.Timer;
import java.util.TimerTask;

public class EventSend  extends FlowPoint {

	final String key_event = "event";//数据存放变量
	final String key_out = "params";//数据存放变量
	final String key_delay = "delay";//数据存放变量

	@Override
	public void run(final FlowBox flowBox) throws Exception {


		final Event e = Event.valueOf(params.get( key_event ));

		final String value = params.get(key_out);

		final Object objparam = flowBox.getValue(value);


		String delay = flowBox.getVarString(params.get(key_delay));
		if( !isNull( delay ) )
		{
			long time = Long.parseLong(delay);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {

					if(!isNull( value ) )
					{

						MessageCenter.sendMessage(e,objparam);

					}
					else
						MessageCenter.sendMessage(e);

				}
			},time);

		}else
		{

			if(!isNull( value ) )
			{

				MessageCenter.sendMessage(e,flowBox.getValue(value));

			}
			else
				MessageCenter.sendMessage(e);

		}



		flowBox.notifyFlowContinue();

	}




}
