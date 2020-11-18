package ru.articat.crypta.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import ru.articat.crypta.Models.ThemeModel;
import ru.articat.crypta.R;
import ru.articat.crypta.Util.Content;
import ru.articat.crypta.Util.FullSizeImagePopup;

public class ThemeAdapter extends ArrayAdapter<ThemeModel> {

	private final Context ctx;
	private final int allPages; //, secondaryColor, lightTextColor;//, darkTextColor;int primaryColor
	private final int primaryColor;
	private Content content;

	private ViewHolder holder;
//	LayoutInflater layoutInflater;

//	public ArrayList<String> profileList = new ArrayList<String>();

    public ThemeAdapter(Context context, ArrayList<ThemeModel> tema, int allPag) {

        super(context, 0, tema);
		this.ctx = context;
		this.allPages = allPag;

		// Get the primary text color of the theme
		TypedValue typedValue = new TypedValue();
		Resources.Theme theme = ctx.getTheme();
		theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
		TypedArray arr =
				ctx.obtainStyledAttributes(typedValue.data, new int[]{
						android.R.attr.textColorPrimary});
		primaryColor = arr.getColor(0, -1);

//		this.secondaryColor = secondaryColor;
//		this.lightTextColor = lightTextColor;
		//	this.darkTextColor=darkTextColor;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
		ThemeModel tema = getItem(position);
		content= new Content(ctx, parent);
        // Check if an existing view is being reused, otherwise inflate the view
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			rowView = inflater.inflate(R.layout.tema_list_items, null, true);
			holder = new ViewHolder();
			holder.txtNick = (TextView) rowView.findViewById(R.id.txtNick);
//			holder.txtCity = (TextView) rowView.findViewById(R.id.txtCity);
			holder.txtAdd = (TextView) rowView.findViewById(R.id.txtAdd);
			holder.imgAnswerTema=(ImageView)rowView.findViewById(R.id.imgAnswerTema);
			holder.layout = (LinearLayout) rowView.findViewById(R.id.llForAdd);
			holder.myImage = (ImageView) rowView.findViewById(R.id.my_image);
			rowView.setTag(holder);
		}
		else {
			holder = (ViewHolder) rowView.getTag();
		}



			// иконка меню для ответов и т.п. в ответах на тему
//			ImageView imgAnswerTema=(ImageView)convertView.findViewById(R.id.imgAnswerTema);
			holder.imgAnswerTema.setOnClickListener(new View.OnClickListener()
				{ 
					@Override
					public void onClick(View v) 
					{
						// Your code that you want to execute on this button click
						showPopupMenu(v, position); // выводим меню под кнопкой меню в теме
					}

				});

//			ImageLoader imageLoader = ImageLoader.getInstance(); // Получили экземпляр
//			imageLoader.init(ImageLoaderConfiguration.createDefault(getContext())); // Проинициализировали конфигом по умолчанию
//			DisplayImageOptions picOptions;
//			picOptions = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.loading)
//				.showImageForEmptyUri(R.drawable.no_img)
//				.showImageOnFail(R.drawable.no_img)
//				.cacheInMemory(true)
//				.cacheOnDisk(true)
//				.considerExifParams(true)
//				.bitmapConfig(Bitmap.Config.RGB_565)
//				.build();

//			LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.llForAdd);
			holder.layout.setOrientation(LinearLayout.VERTICAL);

			//	ImageGetter imgGetter = new ImageGetter(getContext());
			//	TextView txt = new TextView(getContext());

			/**************************************************
			 *
			 * ВОТ ЭТОТ БЛОК ВАЖЕН! БЕЗ НЕГО ПОВТОРЕНИЯ TEXTVIEW
			 *
			 *************************************************/

			if (holder.layout.getChildCount() > 0)
				holder.layout.removeAllViews();
			//**************************************

//			if (tema.warning != "") {
//				// есть предупреждения!
//				TextView warn = new TextView(getContext());
//				warn.setText(Html.fromHtml(tema.warning));
////				warn.setTextColor(getContext().getResources().getColor(R.color.text_color_light_1));
//				warn.setBackgroundColor(getContext().getResources().getColor(R.color.warning_color));
//				warn.setPadding(5, 5, 5, 5);
//				holder.layout.setPadding(10, 10, 10, 10);
//				holder.layout.addView(warn); // выводим текст
//			}


		holder.txtAdd.setText(tema.addDate.replace("Добавлено: ", ""));
//			if (tema.city.contains("на сайте")) {
//				holder.txtNick.setTextColor(ctx.getResources().getColor(R.color.online_color));
////				txtNick.setAlpha(1f);
//				holder.txtCity.setText(tema.city.replace("на сайте", "").trim());
//				//	txtCity.setTextColor(darkTextColor);
////				txtCity.setAlpha(.54f);
//			}
//			else {
//				holder.txtCity.setText(tema.city.trim());
//				//	txtCity.setTextColor(darkTextColor);
////				txtCity.setAlpha(.54f);
//				holder.txtNick.setTextColor(primaryColor);
////				txtNick.setAlpha(.54f);
//			}
		holder.txtNick.setText(tema.nick);
			final String imageUrl = tema.avatar;
//			} //if (tema.avatar == "") {
////				imageUrl = "";
////			}
////			else {
////				imageUrl = "https://" + tema.avatar.trim();
////			 URL картинки (н-р: "http://site.com/image.png", "file:///mnt/sdcard/img/image.jpg")
			//	Log.w("mplog", "AVATAR= "+imageUrl);
			//	Log.w("mplog", "imgUrl= "+imageUrl);
			//imageLoader.displayImage(imageUrl, imageView, avaOptions); // Запустили асинхронный показ картинки
//--
//			SmartImageView myImage = (SmartImageView) convertView.findViewById(R.id.my_image);
			if (imageUrl != "" && imageUrl.contains("https://")) {
//				holder.myImage.setImageUrl(imageUrl);
				Glide
						.with(ctx)
						.load(imageUrl)
						.thumbnail(.01f)
                        .override(50, 50) // resizes the image to these dimensions (in pixel)
						.error(R.drawable.no_img)
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.into(holder.myImage);
			}

			else {
//				holder.myImage.setImageResource(R.drawable.no_img);
				/**
				 * This library from: https://github.com/amulyakhare/TextDrawable
				 * This light-weight library provides images with letter/text like the Gmail app.
				 */
				ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
				// generate random color
//				int backColor = generator.getRandomColor();
				// generate color based on a key (same key returns the same color)
				int backColor = generator.getColor(tema.nick);
				TextDrawable drawable = TextDrawable.builder()
						.buildRect(tema.nick.substring(0, 1), backColor);

//				ImageView image = (ImageView) findViewById(R.id.image_view);
				holder.myImage.setImageDrawable(drawable);
			}
			holder.myImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new FullSizeImagePopup(ctx).showImage(R.layout.fragment_tema, imageUrl, false);
			}
		});
//			Content content= new Content(ctx, parent);
		holder.layout.addView(content.getLayout(tema.text));

        // Return the completed view to render on screen
        return rowView;
    }



	//***************************************
	//*										*
	//*              popupmenu              *
	//*										*
	//***************************************

	private void showPopupMenu(View v, final int position) {
		PopupMenu popupMenu = new PopupMenu(getContext(), v);
		popupMenu.inflate(R.menu.popup); // Для Android 4.0

		popupMenu
			.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem item)
				{

					ThemeModel t2;
					t2 = getItem(position);

					switch (item.getItemId())
					{

						case R.id.menu1: // профиль

							Toast.makeText(getContext(),
									"Доступно в следующих версиях",
									Toast.LENGTH_SHORT).show();

							return true;

						case R.id.menu2: // закладка

							Toast.makeText(getContext(),
										   "Доступно в следующих версиях",
										   Toast.LENGTH_SHORT).show();
							return true;

						case R.id.menu3: // сообщение

							Toast.makeText(getContext(),
									"Доступно в следующих версиях",
									Toast.LENGTH_SHORT).show();
							return true;

//						case R.id.menu4: // цитата
//							Toast.makeText(getContext(),
//										   "Доступно в следующих версиях",
//										   Toast.LENGTH_SHORT).show();
//							return true;
//
						case R.id.menu4: // ответ в тему

							Toast.makeText(getContext(),
									"Доступно в следующих версиях",
									Toast.LENGTH_SHORT).show();

							return true;
						default:
							return false;
					}
				}
			});

		popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {

				@Override
				public void onDismiss(PopupMenu menu)
				{
					// TODO Auto-generated method stub
					//	Toast.makeText(getContext(), "onDismiss", Toast.LENGTH_SHORT).show();
				}
			});
		popupMenu.show();
	}

	
	static class ViewHolder {
		TextView txtNick;
//		TextView txtCity;
		TextView txtAdd;
		ImageView imgAnswerTema;
		TextView warn;
		ImageView myImage;
		LinearLayout layout;
	}
//
}

/* LayoutInflater layoutInflater = (LayoutInflater) 
        this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);    
rl.addView(1, layoutInflater.inflate(R.layout.content_layout, this, false) ); 
*/
