package com.syncnapsis.data.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.dao.NewsDao;
import com.syncnapsis.data.model.News;
import com.syncnapsis.data.service.NewsManager;
import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.enums.EnumNewsAge;
import com.syncnapsis.utils.SortUtil;

/**
 * Manager-Implementierung für den Zugriff auf News.
 * 
 * @author ultimate
 */
public class NewsManagerImpl extends GenericManagerImpl<News, Long> implements NewsManager
{
	/**
	 * NewsDao für den Datenbankzugriff
	 */
	private NewsDao			newsDao;
	/**
	 * ParameterManager für den Datenbankzugriff
	 */
	private ParameterManager	parameterManager;

	/**
	 * Standard Constructor, der die DAOs speichert.
	 * 
	 * @param dao - NewsDao für den Datenbankzugriff
	 * @param parameterDao - ParameterDao für den Datenbankzugriff
	 */
	public NewsManagerImpl(NewsDao newsDao, ParameterManager parameterManager)
	{
		super(newsDao);
		this.newsDao = newsDao;
		this.parameterManager = parameterManager;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * 
	 * com.syncnapsis.service.NewsManager#getIdsByMaxAge(com.syncnapsis.enums.EnumNewsAge
	 * , java.util.Date)
	 */
	@Override
	public List<String> getIdsByMaxAge(EnumNewsAge maxAge, Date referenceDate)
	{
		return newsDao.getIdsByMaxAge(maxAge, referenceDate);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.NewsManager#getActualIds(java.util.Date)
	 */
	@Override
	public List<String> getActualIds(Date referenceDate)
	{
		List<String> newsIds = new ArrayList<String>();
		for(EnumNewsAge age : EnumNewsAge.values())
			newsIds.addAll(getIdsByMaxAge(age, referenceDate));
		return newsIds;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.service.NewsManager#getActual(com.syncnapsis.enums.EnumLocale,
	 * java.util.Date)
	 */
	@Override
	public List<News> getActual(EnumLocale locale, Date referenceDate)
	{
		List<News> newsList = new ArrayList<News>();
		List<News> newsListHP = new ArrayList<News>();
		List<String> newsIds = getActualIds(referenceDate);
		News news;
		for(String newsId : newsIds)
		{
			news = getByNewsIdAndLocale(newsId, locale);
			if(news.isHighPriority())
				newsListHP.add(news);
			else
				newsList.add(news);
		}
		int maxItems = parameterManager.getInteger(ApplicationBaseConstants.PARAM_NEWS_MAXITEMS);
		maxItems = maxItems - newsListHP.size();
		SortUtil.sortListDescending(newsList, "getDate");
		if(newsList.size() > maxItems)
			newsList = newsList.subList(0, maxItems);
		SortUtil.sortListDescending(newsListHP, "getDate");
		newsList.addAll(0, newsListHP);
		return newsList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.service.NewsManager#getByNewsIdAndLocale(java.lang.String,
	 * com.syncnapsis.enums.EnumLocale)
	 */
	@Override
	public News getByNewsIdAndLocale(String newsId, EnumLocale locale)
	{
		News news = newsDao.getByNewsIdAndLocale(newsId, locale);
		if(news == null && !locale.equals(EnumLocale.getDefault()))
			return newsDao.getByNewsIdAndLocale(newsId, EnumLocale.getDefault());
		return news;
	}
}
