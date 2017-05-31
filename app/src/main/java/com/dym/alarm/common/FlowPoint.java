package com.dym.alarm.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/*
 * 
 * 流程点
 */
public abstract class FlowPoint {


	protected  static final String key_descript = "descript";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String id;
	
	public Vector<Line> lines_child = new Vector<Line>();
	public Vector<Line> lines_parent = new Vector<Line>();
	
	public void addChildLine(Line line_0) {

		if(line_0.conditions != null && line_0.conditions.equals("else") )
		{
			lines_child.add(line_0);

		}
		else
			lines_child.insertElementAt(line_0,0);

		line_0.child.lines_parent.add(line_0);

		
	}


	public void setStart(boolean start) {
		this.start = start;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	boolean start,end;

	
	public  boolean isStart(){
		return start;
	}
	public  boolean isEnd(){

		return lines_child.size()==0;

	}
	
	public String getDescript(){

		return params.get(key_descript);

	}
	public FlowPoint getChild(FlowBox flowBox) {
		
		Line line;
		for(int i=0;i<lines_child.size();i++){
			line = lines_child.get(i);
			if( line.isRight(flowBox)){
				return line.child;
			}
			
		}
		
		return null;
		
	}

	public abstract void run(FlowBox flowBox) throws Exception;



	protected Map<String,String> params = new HashMap<String,String>();

	public void addParams(String key, String value) {

		params.put(key,value);


	}

	public String toString(){

		return this.getClass().getName();
	}

	public boolean isNull(String var){

		return (var == null || var.length()==0);

	}

	public FlowPoint getStaticPoint(FlowBox flowBox) {

		Line line;
		for(int i=0;i<lines_child.size();i++){
			line = lines_child.get(i);
			if( "static".equals( line.conditions ) )  {
				return line.child;
			}

		}

		return null;


	}

}
