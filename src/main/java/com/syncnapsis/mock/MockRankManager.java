package com.syncnapsis.mock;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.RankManager;

public class MockRankManager<T extends BaseObject<PK>, PK extends Serializable> implements RankManager<MockRank<T>, T, PK>
{
	private T	entity;

	public MockRankManager(T entity)
	{
		super();
		this.entity = entity;
	}

	@SuppressWarnings("unchecked")
	public List<MockRank<T>> getDatas()
	{
		List<MockRank<T>> datas = new LinkedList<MockRank<T>>();
		int size = (int) (Math.random() * 100.0 + 10.0);
		for(int i = 0; i < size; i++)
		{
			try
			{
				datas.add(new MockRank<T>((T) entity.getClass().newInstance()));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return datas;
	}

	@Override
	public List<MockRank<T>> getByCriterion(String criterion)
	{
		return getDatas();
	}

	@Override
	public List<MockRank<T>> getByDefaultPrimaryCriterion()
	{
		return getDatas();
	}

	@Override
	public MockRank<T> getByEntity(PK entityId)
	{
		return new MockRank<T>(entity);
	}

	@Override
	public List<MockRank<T>> getHistoryByEntity(PK entityId)
	{
		return getDatas();
	}

	@Override
	public boolean exists(Long id)
	{
		return id > 0;
	}

	@Override
	public MockRank<T> get(Long id)
	{
		return new MockRank<T>(entity);
	}

	@Override
	public List<MockRank<T>> getAll()
	{
		return getDatas();
	}

	@Override
	public List<MockRank<T>> getAll(boolean returnOnlyActivated)
	{
		return getDatas();
	}

	@Override
	public List<MockRank<T>> getByIdList(List<Long> idList)
	{
		return getDatas();
	}

	@Override
	public String remove(MockRank<T> object)
	{
		return "removed";
	}

	@Override
	public MockRank<T> save(MockRank<T> object)
	{
		return new MockRank<T>(entity);
	}
}
