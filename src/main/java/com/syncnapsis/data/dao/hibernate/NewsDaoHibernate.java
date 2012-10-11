package com.syncnapsis.data.dao.hibernate;

import java.util.Date;
import java.util.List;

import com.syncnapsis.data.dao.NewsDao;
import com.syncnapsis.data.model.News;
import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.enums.EnumNewsAge;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf AllianceAllianceContactGroup
 * 
 * @author ultimate
 */
public class NewsDaoHibernate extends GenericDaoHibernate<News, Long> implements NewsDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse News
	 */
	public NewsDaoHibernate()
	{
		super(News.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.NewsDao#getIdsByMaxAge(long, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getIdsByMaxAge(EnumNewsAge maxAge, long maxAgeValue, Date referenceDate)
	{
		Date minDate = new Date(referenceDate.getTime() - maxAgeValue);
		return (List<String>) createQuery("select distinct n.newsId from News n where n.maxAge=? and n.date>=? and n.date<=? order by n.newsId", maxAge,
				minDate, referenceDate).list();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.NewsDao#getByNewsIdAndLocale(java.lang.String, com.syncnapsis.enums.EnumLocale)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public News getByNewsIdAndLocale(String newsId, EnumLocale locale)
	{
		List<News> news = createQuery("from News n where n.newsId=? and n.locale=?", newsId, locale).list();
		return singleResult(news);
	}
}
