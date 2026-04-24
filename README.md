# Quiz Leaderboard System

## Overview

This project was developed as part of an internship assignment. The goal is to process quiz data received from an external API, handle duplicate entries correctly, and generate an accurate leaderboard based on participant scores.

The application fetches data multiple times, filters repeated responses, calculates scores, and submits the final result back to the API.



## Problem Statement

The API returns quiz results across multiple polls. However, due to system behavior, the same data may appear more than once.

The task is to:

* Collect all responses
* Remove duplicate entries
* Calculate total scores per participant
* Generate a leaderboard
* Submit the final result



## Approach

1. Polling API
   The system makes 10 API calls using different poll values (0–9) with a delay between each request.

2. Collecting Data
   All events from each response are stored in a list.

3. Removing Duplicates
   Duplicate entries are filtered using a combination of roundId and participant.

4. **Score Calculation**
   Scores are aggregated for each participant using a map.

5. Leaderboard Generation
   Participants are sorted in descending order based on their total score.

6. Submission
   The final leaderboard is sent back to the API.



## Tech Stack

* Java
* Spring Boot
* REST APIs
* Maven


## Project Structure

* controller → Handles API requests
* service → Contains core logic (polling, processing, submission)
* model → Data classes for API responses



## How to Run

1. Clone the repository
2. Open the project in your IDE
3. Run the Spring Boot application
4. Call the endpoint:


GET /quiz


This will:

* wait for 50 sec
* Fetch all data
* Process it
* Submit the result
* Return the leaderboard



## Notes

* A delay is maintained between API calls to match the requirements
* Duplicate handling is important to ensure correct results
* The submission API is called only once after processing



## Output

The application returns:

* Final leaderboard
* Total score
* Submission response from the API



## Author

Sathish Kumar
