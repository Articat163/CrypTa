package ru.articat.crypta.Models;

import org.jsoup.nodes.Element;

public class ThemeHeader
{
	private String userId;
	private String title;
	private String ava;
	private String nick;
	private Element text;
//	int hType;
//private String city;
	private String addDate;
//	private String video;
	
	
	public ThemeHeader(){
        super();
    }


	public ThemeHeader(String id, String title, String ava, String nick, Element text, String addDate){
		this.userId=id;
		this.title=title;
		this.text=text;
		this.ava=ava;
		this.nick=nick;
	//	this.hType=hType;
//		this.city=city;
		this.addDate=addDate;
//		this.video=video;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTitle()
	{
		return title;
	}

	public void setAva(String ava)
	{
		this.ava = ava;
	}

	public String getAva()
	{
		return ava;
	}

	public void setNick(String nick)
	{
		this.nick = nick;
	}

	public String getNick()
	{
		return nick;
	}

//	public void setText(String text)
//	{
//		this.text = text;
//	}

	public Element getText()
	{
		return text;
	}

//	public void setCity(String city)
//	{
//		this.city = city;
//	}
//
//	public String getCity()
//	{
//		return city;
//	}

	public void setAddDate(String addDate)
	{
		this.addDate = addDate;
	}

	public String getAddDate()
	{
		return addDate;
	}

//	public void setVideo(String video)
//	{
//		this.video = video;
//	}
//
//	public String getVideo()
//	{
//		return video;
//	}

	
}
