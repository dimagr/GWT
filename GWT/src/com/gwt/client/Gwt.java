package com.gwt.client;


import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gwt implements EntryPoint {
	private VerticalPanel sortNumbersPanel = new VerticalPanel();
	private FlexTable flexTable = new FlexTable();
	private HorizontalPanel sortTextAndBtnPanel = new HorizontalPanel();
	private TextBox numberTextBox = new TextBox();
	private Button sortButton = new Button("Sort");
	private List<Integer> numbers = new ArrayList();

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		sortTextAndBtnPanel.add(numberTextBox);
		sortTextAndBtnPanel.add(sortButton);

		sortNumbersPanel.add(sortTextAndBtnPanel);
		sortNumbersPanel.add(flexTable);

		RootPanel.get("QuickSortDiv").add(sortNumbersPanel);

		numberTextBox.setFocus(true);

		sortButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				sortNumbers();
			}
		});

		numberTextBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {	
					initNumbers();
				}
			}
		});	
	}
	
	private void sortNumbers() {
        sort(numbers, 0, numbers.size()-1); 
		numberTextBox.setText("");
	}

	
	private void initNumbers() {
		final String symbol = numberTextBox.getText().toUpperCase().trim();
		numberTextBox.setFocus(true);

		if(isNotValidInput(symbol)) {
			return;
		}
		
		initShuffleNumbers(symbol);
		printArray(numbers);
	}

	private boolean isNotValidInput(final String symbol) {
		if (!symbol.matches("^[0-9\\.]{1,3}$")) {
			Window.alert("'" + symbol + "' is not a valid. Enter number from 0 - 999");
			numberTextBox.setText("");
			numberTextBox.selectAll();
			return true;
		}else {
			return false;
		}
	}

	private void initShuffleNumbers(final String symbol) {
		Integer numbersSize = Integer.valueOf(symbol);
		numbers = new ArrayList();
		for (int i = 1; i <= numbersSize ; i++){
	        numbers.add(i);
	    }
		Collections.shuffle(numbers);
	}
	
	private void printArray(List<Integer> list) {
		if(list == null) {
			return;
		}
		
		cleareFlaxTable();
		
		for(int i = 0 ; i < list.size() ; i++ ) {
			flexTable.setText(i, 0, list.get(i).toString());
		}
	}
	
	private void cleareFlaxTable() {
		int count = flexTable.getRowCount();
		for (int i = 0; i < count; i++) {
			flexTable.removeRow(0);
		}
	}
	

	private void sort(List<Integer> sortList, int low, int high) 
	{ 
		if (low < high) 
		{ 
			/* pi is partitioning index, arr[pi] is  
              now at right place */
			Timer timer  = new Timer() {			
				@Override
				public void run() {
					int pi = partition(sortList, low, high);  
					// Recursively sort elements before 
					// partition and after partition 
					sort(sortList, low, pi-1); 
					sort(sortList, pi+1, high); 
				}
			};
			timer.schedule(500);
		} 
	}
	
	private int partition(List<Integer> sortList, int low, int high) 
    { 
        int pivot = sortList.get(high);  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) 
        { 
            // If current element is smaller than or 
            // equal to pivot 
            if (sortList.get(j) <= pivot) 
            { 
                i++; 
                // swap arr[i] and arr[j] 
                int temp = sortList.get(i); 
                sortList.set(i, sortList.get(j));              
                sortList.set(j, temp);
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        int temp = sortList.get(i+1); 
        sortList.set(i+1, sortList.get(high));
        sortList.set(high, temp);
        
        printArray(sortList);
        
        return i+1; 
    } 
}
