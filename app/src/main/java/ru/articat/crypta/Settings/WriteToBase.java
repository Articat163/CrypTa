package ru.articat.crypta.Settings;

import android.content.Context;
import android.util.Log;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import ru.articat.crypta.DataBases.DBHelper;
import ru.articat.crypta.R;
import ru.articat.crypta.Util.Converter;
import ru.articat.crypta.Util.SaveBitmap;

import static ru.articat.crypta.Settings.Constants.TAG;


public class WriteToBase{
	
	private final DBHelper dbHelper;
	private final Context context;
//	int widgetID;
private Map <String, String> smsId_Sms= new HashMap<String, String>();
	
	public WriteToBase(Context c) {
        this.context=c;
//		this.widgetID=wid;

		dbHelper=new DBHelper(context);
	}
	
	public WriteToBase(Context c, Map<String, String> abs) {
        this.context=c;
	//	this.widgetID=wid;
		this.smsId_Sms=abs;

		dbHelper=new DBHelper(context);
	}
	
	
	public void toBase(Elements el, int newMsg, String getOrSend){

		for(Element elm:el){
			String userId=elm.select("font.bbs1").select("a").attr("href").toString().substring(11);
			String photo=elm.select("div.em_photo").select("img").attr("src");
			String photoLink;
			if (photo.equals("/img/no_photo.jpg")){
				photoLink= "";
			}
			else{
				photoLink= Constants.IMAGENAME+userId+".jpg";
				new SaveBitmap(context, "https://"+photo, Constants.IMAGENAME, Integer.parseInt(userId), false).execute();
			}


//			String message=elm.select("div.em_mes").text();
			String name=elm.select("font.bbs1").select("a").text();

			String messageId=elm.select("div.em_sys").attr("id").toString().substring(1);
			String message="";
			if(smsId_Sms.containsKey(messageId)){
				message=smsId_Sms.get(messageId);
			}
			else message=elm.select("div.em_mes").text();

			// если изображение, то сохраняем его на sd
			if(message.contains(context.getResources().getString(R.string.visit_link))){
				String link=message.replace(context.getResources().getString(R.string.visit_link), "");
				// имя файла- это имя из облака
				String nm=link.substring(link.lastIndexOf("/"));
				new SaveBitmap(context, link.trim() , nm, false).execute();

			}
			Log.d(TAG, "userId: "+userId+" messageId= "+messageId);

			Element eName=elm.select("font.bbs1").first();

			String[] dates= new String[3];
			int i=0;
			for (Node child : eName.childNodes()) {
				if (child instanceof TextNode) {
					// 0-years
					// 1-onSite
					// 2-send

					dates[i]=((TextNode) child).text();
					i++;
					Log.i(TAG, "i= "+i);
				}

			}
			if(i!=3){
				dates[2]=dates[1];
				dates[1]=context.getResources().getString(R.string.no_info);
			}
			//	Log.i(TAG, "replace: "+dates[2].replaceAll("\\D+",""));
			String newDate=new Converter().dateToSqlFormat( dates[2].replaceAll("\\D+",""));
			/*
			 get_or_send
			 user_id
			 message_id
			 name
			 year
			 sphoto_link
			 send
			 onsite
			 messages)
			 */

			dbHelper.insertMessage(newMsg, getOrSend, userId, Integer.parseInt(messageId),
								   name, dates[0], photoLink, dates[1], newDate, message);

			if(newMsg==1) {

			}

		} // end for Elements

	} // end toBase
	
	
//	public void allToBase(String myId, String abonent, String photo, Elements els){
//		
//		
//		String photoLink;
//		if (photo.equals("/img/no_photo.jpg")){
//			photoLink= "";
//		}
//		else{
//			photoLink= Constants.IMAGENAME+abonent+".jpg";
//			new SaveBitmap(context, "https://"+photo, Integer.parseInt(abonent), false).execute();
//		}
//		
//		for(Element elm:els){
//			String sms= elm.select("td").get(1).text();
//			String nm=elm.select("td.tb_right white").select("a").text();
//			String messageId=elm.attr("id").toString().substring(1);
//		//	Log.w(Constants.TAG, "sms: "+ sms);
////		String gos=(myId.equals(abonent))? "send":"get";
////			dbHelper.insertMessage("0", gos, abonent, Integer.parseInt(messageId), 
////								   nm, dates[0], photoLink, dates[1], newDate, sms);
//		}
//	}
}
