/* create my 17 */
package com.dym.alarm.forms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dym.alarm.Form;
import com.dym.alarm.R;
import com.dym.alarm.common.Event;

import github.chenupt.springindicator.SpringIndicator;

public class FormAbout extends Form {


    ViewPager viewPager;
    SpringIndicator indicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (mView == null) {
            mView = inflater.inflate(R.layout.form_about, null);
            setView(mView);


            final int[] images = new int[]{R.mipmap.form_edit,R.mipmap.form_edit_time_select,R.mipmap.form_sound_set,R.mipmap.form_sound_sel,R.mipmap.form_open_source};

            final String[] titles = getContext().getResources().getStringArray(R.array.about_titles);
            final String[] descripts = getContext().getResources().getStringArray(R.array.about_descripts);



            viewPager = (ViewPager) ViewInject(R.id.viewpager);
            indicator = (SpringIndicator) ViewInject(R.id.indicator);

            viewPager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return images.length;
                }

                @Override
                public int getItemPosition(Object object) {

                    return super.getItemPosition(object);
                }


                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {

                    View view = View.inflate(container.getContext(),R.layout.view_about_item,null);

                    ImageView image = (ImageView) view.findViewById(R.id.image);
                    image.setImageResource(images[position]);

                    TextView text_title = (TextView) view.findViewById(R.id.text_title);
                    TextView text_descript = (TextView) view.findViewById(R.id.text_descript);


                    text_title.setText(titles[position]);
                    text_descript.setText(descripts[position]);


                    container.addView(view);
                    return view;
                }


                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {

                    container.removeView((View)object);

                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return String.valueOf( position+1 );
                }
            });


            indicator.setViewPager(viewPager);


        }

        return mView;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_back:
                sendMessage(Event.REQ_FORM_BACK);
                break;
        }

    }

    @Override
    public boolean isScreen() {
        return true;
    }
}
