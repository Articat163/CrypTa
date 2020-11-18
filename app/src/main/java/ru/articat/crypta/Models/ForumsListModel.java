package ru.articat.crypta.Models;

public class ForumsListModel
{
	public int id;
	public String link;
	public String title;
//	public String section;
	public int total;
//	public int novoe;
	public String star;

	public ForumsListModel()
	{
		super();
	}

	public ForumsListModel(int id, String link, String title, int total, String star)//, int nov, boolean star)
	{
		this.id=id;
		this.link=link;
		this.title = title;
	//	this.section = section;
		this.total = total;
	//	this.novoe = nov;
		this.star = star;
	}

	public String getTitle(){
		return title;
	}
}
