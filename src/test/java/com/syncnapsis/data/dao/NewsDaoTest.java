package com.syncnapsis.data.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.ObjectNotFoundException;

import com.syncnapsis.data.dao.hibernate.NewsDaoHibernate;
import com.syncnapsis.data.model.News;
import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.enums.EnumNewsAge;
import com.syncnapsis.enums.EnumNewsType;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ NewsDao.class, NewsDaoHibernate.class })
public class NewsDaoTest extends BaseDaoTestCase
{
	private NewsDao			newsDao;
	private ParameterDao	parameterDao;
	private UserDao			userDao;

	public void testGetNewsInvalid() throws Exception
	{
		logger.debug("testing getNews invalid...");
		try
		{
			newsDao.get(-1L);
			fail("'badNewsID' found in database, failing test...");
		}
		catch(ObjectNotFoundException e)
		{
			assertNotNull(e);
		}
	}

	public void testGetNews() throws Exception
	{
		logger.debug("testing getNews valid...");
		News news = newsDao.get(-10L);

		assertNotNull(news);
		assertEquals("T1", news.getNewsId());
	}

	public void testGetByNewsIdAndLocale() throws Exception
	{
		logger.debug("testing getByNewsIdAndLocale valid...");
		News news;

		for(EnumLocale locale : EnumLocale.values())
		{
			news = newsDao.getByNewsIdAndLocale("T1", locale);

			assertNotNull(news);
			assertEquals("T1", news.getNewsId());
			assertEquals(locale, news.getLocale());
		}
	}

	public void testGetIdsByMaxAge() throws Exception
	{
		logger.debug("testing testGetIdsByMaxAge...");

		News news1 = new News();
		news1.setNewsId("T2");
		news1.setLocale(EnumLocale.EN);
		news1.setDate(new Date(timeProvider.get()));
		news1.setMaxAge(EnumNewsAge.length1);
		news1.setType(EnumNewsType.others);
		news1.setHighPriority(false);
		news1.setText("text");
		news1.setTitle("title");
		news1.setAuthor(userDao.get(0L));

		news1 = newsDao.save(news1);

		News news1a = new News();
		news1a.setNewsId("T2");
		news1a.setLocale(EnumLocale.DE);
		news1a.setDate(new Date(timeProvider.get()));
		news1a.setMaxAge(EnumNewsAge.length1);
		news1a.setType(EnumNewsType.others);
		news1a.setHighPriority(false);
		news1a.setText("text");
		news1a.setTitle("title");
		news1a.setAuthor(userDao.get(0L));

		news1a = newsDao.save(news1a);

		News news2 = new News();
		news2.setNewsId("T3");
		news2.setLocale(EnumLocale.EN);
		news2.setDate(new Date(timeProvider.get()));
		news2.setMaxAge(EnumNewsAge.length1);
		news2.setType(EnumNewsType.others);
		news2.setHighPriority(false);
		news2.setText("text");
		news2.setTitle("title");
		news2.setAuthor(userDao.get(0L));

		news2 = newsDao.save(news2);

		News news3 = new News();
		news3.setNewsId("T4");
		news3.setLocale(EnumLocale.EN);
		news3.setDate(new Date(timeProvider.get()));
		news3.setMaxAge(EnumNewsAge.length2);
		news3.setType(EnumNewsType.others);
		news3.setHighPriority(false);
		news3.setText("text");
		news3.setTitle("title");
		news3.setAuthor(userDao.get(0L));

		news3 = newsDao.save(news3);

		News news4 = new News();
		news4.setNewsId("T5");
		news4.setLocale(EnumLocale.EN);
		news4.setDate(new Date(timeProvider.get() - 1000000));
		news4.setMaxAge(EnumNewsAge.length1);
		news4.setType(EnumNewsType.others);
		news4.setHighPriority(false);
		news4.setText("text");
		news4.setTitle("title");
		news4.setAuthor(userDao.get(0L));

		news3 = newsDao.save(news3);

		EnumNewsAge maxAge = EnumNewsAge.length1;
		long maxAgeValue = parameterDao.getLong(maxAge.getParameterKey()) * 1000;

		List<String> newsIds = newsDao.getIdsByMaxAge(maxAge, maxAgeValue, new Date(timeProvider.get()));
		assertNotNull(newsIds);
		assertEquals(2, newsIds.size());
		assertEquals("T2", newsIds.get(0));
		assertEquals("T3", newsIds.get(1));
	}

	public void testUpdateNews() throws Exception
	{
		logger.debug("testing update news...");
		News news = newsDao.get(-10L);

		news.setNewsId("T1X");
		news = newsDao.save(news);

		news = newsDao.get(-10L);
		assertEquals("T1X", news.getNewsId());
	}

	public void testAddAndRemoveNews() throws Exception
	{
		logger.debug("testing add and remove on news...");
		News news = new News();
		news.setNewsId("T2");
		news.setLocale(EnumLocale.EN);
		news.setDate(new Date(timeProvider.get()));
		news.setMaxAge(EnumNewsAge.length1);
		news.setType(EnumNewsType.others);
		news.setHighPriority(false);
		news.setText("text");
		news.setTitle("title");
		news.setAuthor(userDao.get(0L));

		news = newsDao.save(news);

		assertNotNull(news.getId());
		news = newsDao.get(news.getId());
		assertEquals("T2", news.getNewsId());
		assertEquals(EnumLocale.EN, news.getLocale());

		assertEquals("deleted", newsDao.remove(news));
	}

	public void testNewsExists() throws Exception
	{
		logger.debug("testing news exists...");
		boolean b = newsDao.exists(-10L);
		super.assertTrue(b);
	}

	public void testNewsNotExists() throws Exception
	{
		logger.debug("testing news not exists...");
		boolean b = newsDao.exists(-1L);
		super.assertFalse(b);
	}

	public void testGetAll() throws Exception
	{
		List<News> news;

		logger.debug("testing getAll()...");
		news = newsDao.getAll();
		assertNotNull(news);
		logger.debug(news.size() + " news found");
		assertTrue(news.size() >= 2);
	}
}
