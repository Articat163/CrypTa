package ru.articat.crypta.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonIcon;

import java.util.ArrayList;

import ru.articat.crypta.DataBases.DBHelperForumList;
import ru.articat.crypta.Fragments.ForumsListFragment;
import ru.articat.crypta.Models.ForumsListModel;
import ru.articat.crypta.R;
import ru.articat.crypta.Settings.Constants;

public class ForumsListAdapter extends BaseAdapter
{
	private ButtonIcon btnStar;
	//	ForumsListModel forumsListModel;
private final ForumsListFragment fragment;
	private final Resources res;
	private final Context c;
    private final int color;
//	private int fieldColor;
//	private int windowColor;
	private Drawable iconFullStar;
	private final ArrayList<ForumsListModel> forumsList;
	private String TAG = Constants.TAG;
	
	@Override
	public int getCount()
	{
		return forumsList.size();
	}

	@Override
	public Object getItem(int p1)
	{
		return forumsList.get(p1);
	}

	@Override
	public long getItemId(int p1)
	{
		return p1;
	}
	// ArrayAdapter<ForumsListModel> {
	
	
	
	
    public ForumsListAdapter(Context context, ArrayList<ForumsListModel> forumsList, ForumsListFragment fr) {
       // super(context, 0, forumsList);
		this.c=context;
		this.fragment=fr;
		this.forumsList=forumsList;
		this.color=R.color.colorAccent;
//		this.windowColor=windowColor;
//		this.fieldColor=fieldColor;
		res=c.getResources();
	}
	
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		
		// set star icons color
	//	color=c.getResources().getColor(R.color.primary_color_1);
		iconFullStar=res.getDrawable(R.drawable.star);
//		iconFullStar.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        // Get the data item for this position
        //forumsList = getItem(position);    
        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
			view = LayoutInflater.from(c).inflate(R.layout.items_forums_list, parent, false);
        }

		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.forumslistitemRelativeLayout1);
//		int columnId = forumsList.get(position).id;
		TextView txtTitle = (TextView) view.findViewById(R.id.forumlistitemTextViewTitle);
	//	TextView txtSection=(TextView)view.findViewById(R.id.forumlistitemTextViewSection);
		TextView txtTotal = (TextView) view.findViewById(R.id.forumlistitemTextViewTotal);
	//	TextView txtNew=(TextView)view.findViewById(R.id.forumlistitemTextViewNew);
		btnStar=(ButtonIcon)view.findViewById(R.id.buttonIconStar);
	//	btnStar.setBackgroundColor(res.getColor(R.color.primary_color1));
		if(forumsList.get(position).star.equals("true")) {
			btnStar.setDrawableIcon(iconFullStar);
			btnStar.setAlpha(.87f);
//			layout.setBackgroundColor(fieldColor);
		}
		else {
			btnStar.setDrawableIcon(res.getDrawable(R.drawable.star_transparent));
			btnStar.setAlpha(1f);
//			layout.setBackgroundColor(windowColor);
		}
		btnStar.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v){
				Log.e(TAG, "adapter position= "+position);
				getItemStar(position);
				//notifyDataSetChanged();
				fragment.refreshList();
			}
		});
		txtTitle.setText(forumsList.get(position).title);
	//	txtSection.setText(forumsList.section);
		txtTotal.setText(""+forumsList.get(position).total);
		
		
		return view;
		}
		
		// ставим или снимаем звёздочку на тему
		private void getItemStar(int pos){
		//	ForumsListModel f2=new ForumsListModel();
		//	f2=getItem(pos);
			DBHelperForumList dbHelper = new DBHelperForumList(c);
			
			// если уже в избранном, то убираем оттуда
            String star = "false";
            if(forumsList.get(pos).star.equals("true")){
				star ="false";
				dbHelper.updateLinks(dbHelper.getWritableDatabase(), forumsList.get(pos).link, forumsList.get(pos).title, forumsList.get(pos).total, star);
				btnStar.setDrawableIcon(iconFullStar);
				btnStar.setAlpha(.87f);
				
			}
			// и наоборот
			else {
				star ="true";
				dbHelper.updateLinks(dbHelper.getWritableDatabase(), forumsList.get(pos).link, forumsList.get(pos).title, forumsList.get(pos).total, star);
				btnStar.setDrawableIcon(res.getDrawable(R.drawable.star_transparent));
				btnStar.setAlpha(1f);
			}
		//	notifyDataSetChanged();
		
		}
		
//	@Override
//	public int getId(int pos) {
//		ForumsListModel f2=new ForumsListModel();
//	//	f2=getItem(pos);
//		return f2.id;
//	}
}

