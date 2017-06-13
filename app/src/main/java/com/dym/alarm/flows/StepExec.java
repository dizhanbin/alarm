package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;
import com.dym.alarm.common.Line;

/**
 * Created by dzb on 16/8/11.
 */
public class StepExec extends FlowPoint {




    FlowPoint curretn;

    @Override
    public void run(FlowBox flowBox) throws Exception {


        if( curretn != null ) {
            flowBox.log("StepExec ->flow :%s",curretn);
            curretn.run(flowBox);
        }
        else
            flowBox.notifyFlowContinue();

    }

    int index=0;
    @Override
    public FlowPoint getChild(FlowBox flowBox) {

        if( curretn != null ){
            curretn =  curretn.getChild(flowBox);
        }
        if( curretn == null ) {

            Line line;
            for (;index < lines_child.size();) {
                line = lines_child.get(index++);
                if (line.isRight(flowBox)) {
                    curretn = line.child;

                    flowBox.log("StepExec[%d] ---------------------",index);
                    break;
                }

            }

        }
        if( curretn == null ){

            return null;

        }

        return this;

    }
}
