package com.example.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.ui.Model;

public interface EmployeeUtils {

	public static void createEmployeeHobbiesList(Model model) {
		List<String> hobbies = Arrays.asList("Painting", "Cooking", "Sports", "Fishing", "Reading");
		model.addAttribute("hobbies", hobbies);
	}
}
