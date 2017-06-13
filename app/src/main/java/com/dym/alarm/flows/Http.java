package com.dym.alarm.flows;


import com.dym.alarm.RP;
import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;
import com.dym.alarm.common.Https;
import com.dym.alarm.common.ObjectCallBack;

import javax.net.ssl.HttpsURLConnection;

/**
 *
 *
 <property name="url" title="请求地址" type="1" args="$url" value=""/>
 <property name="out" title="输出字符保存至" type="0" args="" value="$http_result"/>
 <property name="status" title="状态码" type="0" args="" value=""/>
 <property name="method" title="请求方式" type="0" args="" value="post" />
 <property name="params" title="参数" type="0" args="" value=""/>
 <property name="descript" title="描述" type="0" args="" value="Http请求"/>
 *
 */

public class Http extends FlowPoint {


	final String key_url = "url";
	final String key_out = "out";
	final String key_status = "status";
	final String key_method = "method";
	final String key_params = "params";

	@Override
	public void run(final FlowBox flowBox) throws Exception {



		String url = flowBox.getVarString(params.get(key_url));

		if( "get".equals( params.get(key_method) ) ){


			Https.get(url, new ObjectCallBack<Integer, String, Boolean>() {
				@Override
				public void onCallBack(Integer integer, String s) {
					if( integer == HttpsURLConnection.HTTP_OK )
					{

						flowBox.setValue( params.get(key_status),"1" );
						flowBox.log(s);
						flowBox.setValue( params.get(key_out),s );

					}
					else
						flowBox.setValue( params.get(key_status),"0" );
					flowBox.notifyFlowContinue();
				}

				@Override
				public Boolean isOK() {
					return true;
				}
			});
		}
		else
		{

			flowBox.log("not implement ");
			flowBox.error();

		}

	}


	/*

	RequestParams rep = new RequestParams(url);

		Object obj = flowBox.getValue( params.get( key_params));

		if( obj instanceof Map)
		{
			Map<String,String> params = (Map) obj;
			for(String k:params.keySet())
			{
				rep.addBodyParameter(k,params.get(k));

			}
		}
		else if( obj instanceof String)
		{
			String params = (String) obj;
			String[] ps = params.split(",");
			for(String s : ps){
				if( s.length() > 0 && s.indexOf(':')>-1) {
					String[] kv = s.split(":");
					rep.addBodyParameter(kv[0], flowBox.getVarString(kv[1]));
				}
			}
		}

		if( "get".equals( params.get(key_method) ) ){

			x.http().get(rep, new Callback.CommonCallback<String>() {
				@Override
				public void onSuccess(String result) {
					flowBox.setValue( params.get(key_status),"1" );
					flowBox.log(result);
					flowBox.setValue( params.get(key_out),result );


				}

				@Override
				public void onError(Throwable ex, boolean isOnCallback) {
					flowBox.setValue( params.get(key_status),"0" );
					ex.printStackTrace();

				}

				@Override
				public void onCancelled(CancelledException cex) {

				}

				@Override
				public void onFinished() {

						flowBox.notifyFlowContinue();

				}
			});
		}
		else
		x.http().post(rep, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {

				flowBox.setValue( params.get(key_status),"1" );
				flowBox.log(result);
				flowBox.setValue( params.get(key_out),result );


			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				flowBox.setValue( params.get(key_status),"0" );
				ex.printStackTrace();

			}

			@Override
			public void onCancelled(CancelledException cex) {

			}

			@Override
			public void onFinished() {

					flowBox.notifyFlowContinue();

			}
		});
		*/



	

}
