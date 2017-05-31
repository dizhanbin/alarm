package com.dym.alarm.common;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * Created by dzb on 16/5/17.
 */
public class FlowFactory {






    public static FlowBox parse(Context context, String xmlpath){


        if( xmlpath == null )
            return null;

        try {


            long current = System.currentTimeMillis();

            NLog.i("parse xml:%s ",xmlpath);

            FlowBox box = new FlowBox(context);

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();

            parser.setInput(context.getResources().getAssets().open(xmlpath),"utf-8");


            /*
            InputStream stream =
            context.getResources().getAssets().open(xmlpath);
            byte[] datas = new byte[stream.available()];
            stream.read(datas);
            NLog.i(new String(datas,"utf-8"));
            */

            int eventType ;
            String tagname;
            FlowPoint flowpoint=null;
            Line line=null;

            while( (eventType = parser.getEventType()) != XmlPullParser.END_DOCUMENT )
            {

                tagname = parser.getName();

                switch (eventType)
                {
                    case XmlPullParser.START_TAG:
                    {
                        switch (tagname)
                        {
                            case "business":
                            {

                                if( parser.getAttributeCount() > 0 ) {
                                    box.setTitle(parser.getAttributeValue(null, "descript"));
                                   // box.setEvent(parser.getAttributeValue(null, "event"));
                                }
                               break;
                            }
                            case "item":
                            {
                                String classname = parser.getAttributeValue(null,"c_android");
                                String id = parser.getAttributeValue(null,"id");


                                //NLog.i("parse imp class:%s id:%s",classname+"",id);


                                flowpoint = (FlowPoint) Class.forName(classname).newInstance();
                                flowpoint.setId( id );


                            }
                            break;
                            case "data":
                            {


                            }
                            break;

                            case "Line":
                            {
                                line = new Line();

                            }
                            break;
                            case "from":
                            {

                                line.fromid = parser.nextText();

                            }
                            break;
                            case "to":
                            {
                                line.toid = parser.nextText();

                            }
                            break;
                            case "property":
                            {
                                String key = parser.getAttributeValue(null,"name");
                                String value = parser.getAttributeValue(null,"value");
                                flowpoint.addParams(key,value);

                            }
                            break;
                            case "condition":
                            {
                                line.conditions = parser.nextText();

                            }
                            break;
                            case "isint":
                            {
                                line.isNumber =  "true".equals(parser.nextText());
                            }
                            break;
                            case "id":
                            {
                                flowpoint.setId( parser.nextText() );

                            }
                            break;

                        }


                    }
                    break;
                    case XmlPullParser.END_TAG:
                    {

                        switch (tagname) {
                            case "item":
                            {

                                    box.addPoint(flowpoint);
                                    if( flowpoint.isStart() )
                                    {
                                        box.setRoot(flowpoint);
                                        box.setEvent( flowpoint.params.get("event") );
                                        box.setStatic( "true".equals(flowpoint.params.get("static")) );

                                    }

                            }
                            break;
                            case "Line":
                            {

                                FlowPoint parent = box.getPoint(line.fromid);
                                FlowPoint child = box.getPoint(line.toid);

                                line.parent = parent;
                                line.child = child;

                                if( parent != null && child != null )
                                    parent.addChildLine( line  );


                            }
                            break;


                        }


                    }
                    break;


                }



                parser.next();




            }


            NLog.i("parse over...time:%d ",(System.currentTimeMillis()-current));

            return box;


        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
