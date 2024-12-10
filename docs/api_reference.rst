======================
InfinityFit API Reference
======================

Welcome to the API Reference for **InfinityFit**, your all-in-one health management platform.

This document provides detailed information about the APIs available in InfinityFit for developers and integrators.

----------------------

API Overview
============

1. **Diet Plan Calculator API**
   - **Description**: Generate a personalized diet plan based on user input.
   - **Endpoint**: `/api/diet-plan`
   - **Method**: POST
   - **Parameters**:
     - `current_weight` (float, required): The user's current weight in kilograms.
     - `goal_weight` (float, required): The user's target weight in kilograms.
     - `age` (integer, required): The user's age in years.
     - `gender` (string, required): Gender of the user (`male` or `female`).
     - `duration` (integer, required): Duration of the diet plan in days.
   - **Response**: 
     - JSON object containing recommended daily calorie intake and meal suggestions.

2. **Food Calorie Dictionary API**
   - **Description**: Fetch calorie information for specific foods.
   - **Endpoint**: `/api/food-calories`
   - **Method**: GET
   - **Parameters**:
     - `query` (string, required): Name of the food item.
   - **Response**: 
     - JSON object with food details and calorie count.

3. **Health Management Diary API**
   - **Description**: Save and retrieve daily health records.
   - **Endpoint**: `/api/health-diary`
   - **Method**: POST/GET
   - **Parameters**:
     - For POST: `entry_date`, `diet_details`, `workout_details`, `notes`.
     - For GET: `entry_date`.
   - **Response**: 
     - JSON object with saved or retrieved health data.

4. **Body Weight Trends API**
   - **Description**: Fetch and visualize weight trends.
   - **Endpoint**: `/api/weight-trends`
   - **Method**: GET
   - **Parameters**:
     - `start_date` and `end_date` (optional): Date range for the trend data.
   - **Response**:
     - JSON object containing weight data points and trend analysis.

----------------------

