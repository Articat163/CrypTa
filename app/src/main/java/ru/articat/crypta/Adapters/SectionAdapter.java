package ru.articat.crypta.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ru.articat.crypta.DataBases.DBHelperMarkedList;
import ru.articat.crypta.Models.SecionModel;
import ru.articat.crypta.R;
import ru.articat.crypta.Settings.Constants;

public class SectionAdapter extends BaseAdapter{

    private final ArrayList<SecionModel> forum;
    private final Context context;
    private final DBHelperMarkedList dbHelper;
    private final Resources res;
    private int color;

    @Override
    public int getCount() {
        // TODO: Implement this method
        return forum.size();
    }

    @Override
    public Object getItem(int p1) {
        // TODO: Implement this method
        return forum.get(p1);
    }

    @Override
    public long getItemId(int p1) {
        // TODO: Implement this method
        return 0;
    }


    public SectionAdapter(Context context, ArrayList<SecionModel> forum) {
        // super(context, 0, forum);
        this.context = context;
        this.forum = forum;
        this.color = color;
        res = context.getResources();
        dbHelper = new DBHelperMarkedList(context);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        // forum = getItem(position);    
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.items_section, parent, false);
        }
        //	color=context.getResources().getColor(R.color.secondary_color_1);
        // Lookup view for data population
        final ImageView mark = (ImageView) convertView.findViewById(R.id.itemsImageViewMark);
//		RelativeLayout markLayout = (RelativeLayout) convertView.findViewById((R.id.itemsLinearLayout1Title));

        mark.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // тема есть в закладках
                if (dbHelper.isMarked(dbHelper.getReadableDatabase(), forum.get(position).urlTema)) {
                    mark.clearColorFilter();
                    mark.setBackgroundDrawable(res.getDrawable(R.drawable.mark_grey));
                    dbHelper.deleteMark(dbHelper.getWritableDatabase(), forum.get(position).urlTema);
                }
                // темы нет в закладках
                else {
                    // SECTION LINK TITLE TEXT NICK DATE USER UDATE UTIME CITY ANSWERS VIEWS VIDEO


                    mark.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                    mark.setBackgroundDrawable(res.getDrawable(R.drawable.mark));

                    dbHelper.insertMark(dbHelper.getWritableDatabase(),
                            Constants.FORUMNAME,
                            forum.get(position).urlTema,
                            forum.get(position).title,
                            forum.get(position).pages,
                            forum.get(position).nick,
                            forum.get(position).date,
                            forum.get(position).user,
                            forum.get(position).userDate,
                            forum.get(position).userTime,
                            forum.get(position).city,
                            forum.get(position).answers,
                            forum.get(position).views,
                            forum.get(position).video ? "true" : "false");

                }
            }
        });
//		TextView txtLastAnswer = (TextView) convertView.findViewById(R.id.itemsTextViewLastAnswer);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
//        TextView txtText = (TextView) convertView.findViewById(R.id.txtText);
        TextView txtNicks = (TextView) convertView.findViewById(R.id.txtNick);
        TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
        TextView txtUser = (TextView) convertView.findViewById(R.id.txtLastUser);
        TextView txtLastDate = (TextView) convertView.findViewById(R.id.txtLast);
//		TextView txtCity = (TextView) convertView.findViewById(R.id.txtCity);
        TextView txtAnswers = (TextView) convertView.findViewById(R.id.txtAnswers);
        TextView txtViews = (TextView) convertView.findViewById(R.id.txtViews);
//        if (Integer.parseInt(forum.get(position).pages) > 0) {
            int[] textViews = {R.id.txtPage1, R.id.txtPage2, R.id.txtPage3, R.id.txtPage4, R.id.txtPage5};
            int pages = Integer.parseInt(forum.get(position).pages);
//			int p = pages>5? 5:pages;
            Log.i(Constants.TAG, "pages= " + pages);
            for (int i = 1; i <= 5; i++) {
                TextView tv = (TextView) convertView.findViewById(textViews[i-1]);
                if (pages >= i) {
                    tv.setVisibility(View.VISIBLE);
                    if (i == 5) tv.setText(String.valueOf(pages));
                }
                else {
                    tv.setVisibility(View.GONE);
                }

            }
//        }
//		ImageView ivVideo = (ImageView) convertView.findViewById(R.id.ivVideo);
            //save original textcolor
//		int oldColors = context.getResources().getColor(android.R.color.darker_gray);
            // Populate the data into the template view using the data object
            if (dbHelper.isMarked(dbHelper.getReadableDatabase(), forum.get(position).urlTema)) {
                //	int color=context.getResources().getColor(R.color.bottom_send_color);
                mark.setBackgroundDrawable(res.getDrawable(R.drawable.mark));
                mark.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }

            // темы нет в закладказ
            else {
                mark.clearColorFilter();
                mark.setBackgroundDrawable(res.getDrawable(R.drawable.mark_grey));
            }
            txtTitle.setText(forum.get(position).title);
//        txtText.setText(forum.get(position).text);
            txtNicks.setText(forum.get(position).nick);
            txtDate.setText(forum.get(position).date);
            //	 String day = (String) android.text.format.DateFormat.format("dd", forum.date);
            txtUser.setText(forum.get(position).user);

            SimpleDateFormat sdf = new SimpleDateFormat("d-MM-yyyy");
            String currentDate = sdf.format(new Date());
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String yesterday = sdf.format(cal.getTime());
// Log.i(TAG, "DATE: "+currentDate);
// Log.i(TAG, "userDate: "+forum.userDate);
            if (forum.get(position).userDate.equals(currentDate)) {
                //	Log.w(Constants.TAG, "СОВПАДАЕТ");
                txtLastDate.setBackgroundColor(context.getResources().getColor(R.color.today_color));
//			txtLastDate.setTextColor(context.getResources().getColor(R.color.text_color_light_1));
                txtLastDate.setText(context.getString(R.string.today) + " " + forum.get(position).userTime);
            } else if (forum.get(position).userDate.equals(yesterday)) {
                //Log.w(Constants.TAG, "СОВПАДАЕТ");
                txtLastDate.setBackgroundColor(context.getResources().getColor(R.color.yesterday_color));
//			txtLastDate.setTextColor(context.getResources().getColor(R.color.text_color_light_1));
                txtLastDate.setText(context.getString(R.string.yesterday) + " " + forum.get(position).userTime);
            } else {
                txtLastDate.setBackgroundColor(context.getResources().getColor(R.color.transparent_color));
//			txtLastDate.setTextColor(oldColors);
                txtLastDate.setText(forum.get(position).userDate + " " + forum.get(position).userTime);
            }
            //	txtLastDate.setText(forum.userDate);
//		txtCity.setText(forum.get(position).city);
            txtAnswers.setText(forum.get(position).answers);
            txtViews.setText(forum.get(position).views);

//		if (forum.get(position).video){ // есть видео
//			ivVideo.setVisibility(View.VISIBLE);
//		}
//		else{
//			ivVideo.setVisibility(View.GONE);
//		}
            // Return the completed view to render on screen
            return convertView;
        }

        public String getAnswer (int position){

            return forum.get(position).answers;
        }
    }

