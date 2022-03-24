package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

public class ResultsPage<T> {

	/**
	 * The data values returned in this page of results
	 */
	private List<T> values;

	/**
	 * The last primary key returned in this page of results
	 * This value is typically included in the query for the next page of results
	 */
	private PrimaryKey lastItem;

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

	public void setLastItem(PrimaryKey value) {
		lastItem = value;
	}

	public PrimaryKey getLastItem() {
		return lastItem;
	}

	public boolean hasLastItem() {
		return (lastItem != null);
	}
}
