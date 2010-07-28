package com.thoughtworks.thoughtferret.unittests;

import java.util.Arrays;

import org.junit.Test;

import com.thoughtworks.thoughtferret.presenter.MoodTagsBuilder;

public class KeywordsEditorPresenterTests {
	
	@Test
	public void testCanMergeForwardFromTheStart() {
		MoodTagsBuilder presenter = new MoodTagsBuilder();
		presenter.addKeywords(Arrays.asList("one", "two", "three", "four"));
		presenter.merge("one", "two");
		presenter.getKeywords().equals(Arrays.asList("one two", "three", "four"));
	}

	@Test
	public void testCanMergeForwardInTheMiddle() {
		MoodTagsBuilder presenter = new MoodTagsBuilder();
		presenter.addKeywords(Arrays.asList("one", "two", "three", "four"));
		presenter.merge("two", "three");
		presenter.getKeywords().equals(Arrays.asList("one", "two three", "four"));
	}

	@Test
	public void testCanMergeForwardAtTheEnd() {
		MoodTagsBuilder presenter = new MoodTagsBuilder();
		presenter.addKeywords(Arrays.asList("one", "two", "three", "four"));
		presenter.merge("three", "four");
		presenter.getKeywords().equals(Arrays.asList("one", "two", "three four"));
	}
	
	@Test
	public void testCanMergeBackwardsToTheStart() {
		MoodTagsBuilder presenter = new MoodTagsBuilder();
		presenter.addKeywords(Arrays.asList("one", "two", "three", "four"));
		presenter.merge("two", "one");
		presenter.getKeywords().equals(Arrays.asList("two one", "three", "four"));
	}

	@Test
	public void testCanMergeBackwardsInTheMiddle() {
		MoodTagsBuilder presenter = new MoodTagsBuilder();
		presenter.addKeywords(Arrays.asList("one", "two", "three", "four"));
		presenter.merge("three", "two");
		presenter.getKeywords().equals(Arrays.asList("one", "three two", "four"));
	}

	@Test
	public void testCanMergeBackwardsFromTheEnd() {
		MoodTagsBuilder presenter = new MoodTagsBuilder();
		presenter.addKeywords(Arrays.asList("one", "two", "three", "four"));
		presenter.merge("four", "three");
		presenter.getKeywords().equals(Arrays.asList("one", "two", "four three"));
	}
	
}
