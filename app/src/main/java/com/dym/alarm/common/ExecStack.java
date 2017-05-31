package com.dym.alarm.common;

import android.support.annotation.NonNull;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by dizhanbin on 16/12/2.
 */

public class ExecStack  {

    Vector<Execable> stacks = new Vector<>();


    public void addExec(Execable run){

        stacks.add(run);
    }
    public void exec(){

//        for(int i=execs.size()-1;i>-1;i--)
//        {
//            Execable runable = execs.remove(i);
//            runable.run();
//        }

        if( stacks.size() > 0 )
        {
            stacks.get(stacks.size()-1).run();
        }

    }

    public static abstract class Execable{
        String key;
        public Execable(@NonNull String key){
            this.key = key;
        }
        public abstract  void run();

        public boolean equals(Execable able){
            return key.equals(able.key);
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }
    }

    public void finish(String k){

        for(int i=stacks.size()-1;i>-1;i--) {
            if( stacks.get(i).key.equals(k))
                stacks.remove(i);
        }
        exec();

    }

}

