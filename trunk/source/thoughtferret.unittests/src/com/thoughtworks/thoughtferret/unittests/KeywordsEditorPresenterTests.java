package com.thoughtworks.thoughtferret.unittests;

import java.util.Arrays;

import org.junit.Test;

import com.thoughtworks.thoughtferret.presenter.KeywordsEditorPresenter;

public class KeywordsEditorPresenterTests {
	
	@Test
	public void testCanMergeForwardFromTheStart() {
		KeywordsEditorPresenter presenter = new KeywordsEditorPresenter();
		presenter.addKeywords(Arrays.asList("one", "two", "three", "four"));
		presenter.merge("one", "two");
		presenter.getKeywords().equals(Arrays.asList("one two", "three", "four"));
	}

	@Test
	public void testCanMergeForwardInTheMiddle() {
		KeywordsEditorPresenter presenter = new KeywordsEditorPresenter();
		presenter.addKeywords(Arrays.asList("one", "two", "three", "four"));
		presenter.merge("two", "three");
		presenter.getKeywords().equals(Arrays.asList("one", "two three", "four"));
	}

	@Test
	public void testCanMergeForwardAtTheEnd() {
		KeywordsEditorPresenter presenter = new KeywordsEditorPresenter();
		presenter.addKeywords(Arrays.asList("one", "two", "three", "four"));
		presenter.merge("three", "four");
		presenter.getKeywords().equals(Arrays.asList("one", "two", "three four"));
	}
	
	@Test
	public void testCanMergeBackwardsToTheStart() {
		KeywordsEditorPresenter presenter = new KeywordsEditorPresenter();
		presenter.addKeywords(Arrays.asList("one", "two", "three", "four"));
		presenter.merge("two", "one");
		presenter.getKeywords().equals(Arrays.asList("two one", "three", "four"));
	}

	@Test
	public void testCanMergeBackwardsInTheMiddle() {
		KeywordsEditorPresenter presenter = new KeywordsEditorPresenter();
		presenter.addKeywords(Arrays.asList("one", "two", "three", "four"));
		presenter.merge("three", "two");
		presenter.getKeywords().equals(Arrays.asList("one", "three two", "four"));
	}

	@Test
	public void testCanMergeBackwardsFromTheEnd() {
		KeywordsEditorPresenter presenter = new KeywordsEditorPresenter();
		presenter.addKeywords(Arrays.asList("one", "two", "three", "four"));
		presenter.merge("four", "three");
		presenter.getKeywords().equals(Arrays.asList("one", "two", "four three"));
	}
	
}
