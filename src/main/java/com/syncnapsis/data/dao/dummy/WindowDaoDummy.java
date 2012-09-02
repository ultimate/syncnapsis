package com.syncnapsis.data.dao.dummy;

import java.util.LinkedList;
import java.util.List;

import com.syncnapsis.data.dao.WindowDao;
import com.syncnapsis.data.model.Window;

/**
 * A WindowDao-Dummy
 * 
 * @author ultimate
 */
public class WindowDaoDummy implements WindowDao
{
	private List<Window>	windows;

	private Long			idCount	= 0L;

	public WindowDaoDummy()
	{
		this.windows = new LinkedList<Window>();
		this.windows.add(newWindow());
		this.windows.add(newWindow());
		this.windows.add(newWindow());
		this.windows.add(newWindow());
		this.windows.add(newWindow());
	}

	private Window newWindow()
	{
		return newWindow(idCount++, 1, 100, 100, 400, 300, 0.0);
	}

	private Window newWindow(Long id, Integer version, int positionX, int positionY, int width, int height, double rotation)
	{
		Window w = new Window();
		w.setId(id);
		w.setVersion(version);
		w.setPositionX(positionX);
		w.setPositionY(positionY);
		w.setWidth(width);
		w.setHeight(height);
		w.setRotation(rotation);
		return w;
	}

	@Override
	public boolean exists(Long arg0)
	{
		return get(arg0) != null;
	}

	@Override
	public Window get(Long arg0)
	{
		for(Window p : windows)
			if(p.getId().equals(arg0))
				return p;
		return null;
	}

	@Override
	public List<Window> getAll()
	{
		return getAll(false);
	}

	@Override
	public List<Window> getAll(boolean arg0)
	{
		return new LinkedList<Window>(windows);
	}

	@Override
	public List<Window> getByIdList(List<Long> arg0)
	{
		List<Window> results = new LinkedList<Window>();
		for(Window p : windows)
			if(arg0.contains(p.getId()))
				results.add(p);
		return results;
	}

	@Override
	public String remove(Window arg0)
	{
		this.windows.remove(arg0);
		return "deleted";
	}

	@Override
	public Window save(Window arg0)
	{
		if(arg0.getId() == null)
		{
			this.windows.add(arg0);
			arg0.setId(idCount++);
			arg0.setVersion(1);
			return arg0;
		}
		else
		{
			Window w = get(arg0.getId());
			w.fromMap(arg0.toMap());
			w.setVersion(w.getVersion() + 1);
			return w;
		}
	}
 }
