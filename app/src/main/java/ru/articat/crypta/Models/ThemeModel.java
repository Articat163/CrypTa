package ru.articat.crypta.Models;

import org.jsoup.nodes.Element;

public class ThemeModel {

	public String link;
    public Element text;
	public String userId;
	public String nick;
//	public int type;
	public String avatar;
//	public String city;
	public String warning;
	public String addDate;
//	public String video;
	
	
    public ThemeModel(){
        super();
    }


    public ThemeModel(String link, Element text, String userId, String nick, /*int type,*/ String avatar, String warning, String addDate) {
        super();

		this.link=link;
        this.text = text;
		this.userId=userId;
		this.nick = nick;
	//	this.type = type;
		this.avatar=avatar;
//		this.city=city;
		this.warning=warning;
		this.addDate = addDate;
//		this.video=video;
    }
	
}

