package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;

public class ResultsPage<T> {

	/**
	 * The data values returned in this page of results
	 */
	private List<T> values;

	/**
	 * The last value returned in this page of results
	 * This value is typically included in the query for the next page of results
	 */
	private T lastItem;

	public ResultsPage() {
		values = new ArrayList<>();
		lastItem = null;
	}

	// Values property

	public void addValue(T v) {
		values.add(v);
	}

	public boolean hasValues() {
		return (values != null && values.size() > 0);
	}

	public List<T> getValues() {
		return values;
	}

	// LastItem property

	public void setLastItem(T value) {
		lastItem = value;
	}

	public T getLastItem() {
		return lastItem;
	}

	public boolean hasLastItem() {
		return (lastItem != null);
	}
}
