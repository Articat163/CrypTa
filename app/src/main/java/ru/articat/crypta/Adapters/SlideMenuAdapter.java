//package ru.articat.nicy.Adapters;
//
//import android.content.Context;
//import android.graphics.PorterDuff;
//import android.support.annotation.NonNull;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.readystatesoftware.viewbadger.BadgeView;
//
//import ru.articat.nicy.R;
//import ru.articat.nicy.Settings.Constants;
//
//class SlideMenuAdapter extends ArrayAdapter<String>
//{
//	private final Context context;
//	private final String[] values;
//	private final int[] id_icons;
//	private final int color;
//
//	public SlideMenuAdapter(Context context, String[] values, int[] icons, int color)
//	{
//		super(context, R.layout.drawer_list_item, values);
//		this.context = context;
//		this.values = values;
//		this.id_icons = icons;
//		this.color=color;
//	}
//
//	@Override
//	public View getView(int position, View convertView, @NonNull ViewGroup parent)
//	{
//
//		LayoutInflater inflater = (LayoutInflater) context
//			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View rowView = inflater.inflate(R.layout.drawer_list_item, parent, false);
//		TextView textView = (TextView) rowView.findViewById(R.id.text1);
//		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
//		textView.setText(values[position]);
//		imageView.setImageResource(id_icons[position]);
//	//	int color=context.getResources().getColor(R.color.secondary_color_1);
//		imageView.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
//
//
//		// viewbager
//		if (position == 1 && Constants.UNREADMESSAGES!=0)
//		{
//			View target = rowView.findViewById(R.id.text1);
//			BadgeView badge = new BadgeView(context, target);
//			badge.setText("" + Constants.UNREADMESSAGES);
//			badge.show();
//			notifyDataSetChanged();
//		}
//		return rowView;
//	}
//}
