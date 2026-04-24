package com.Quiz.QuizLeader.service;

import com.Quiz.QuizLeader.model.LeaderBoardEntry;
import com.Quiz.QuizLeader.model.PollResponse;
import com.Quiz.QuizLeader.model.QuizEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class QuizService {
    private static final String REG_NO     = "RA2311042020024";
    private static final String BASE_URL   = "https://devapigw.vidalhealthtpa.com/srm-quiz-task";
    private static final int    TOTAL_POLLS = 10;
    private static final long   DELAY_MS    = 5000L;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> run() throws InterruptedException {


        List<QuizEvent> allEvents = new ArrayList<>();

        for (int poll = 0; poll < TOTAL_POLLS; poll++) {
            System.out.printf("[Poll %d/%d] Fetching...%n", poll + 1, TOTAL_POLLS);

            String url = BASE_URL + "/quiz/messages?regNo=" + REG_NO + "&poll=" + poll;

            try {
                PollResponse response = restTemplate.getForObject(url, PollResponse.class);
                if (response != null && response.getEvents() != null) {
                    allEvents.addAll(response.getEvents());
                    System.out.printf("           Got %d events%n", response.getEvents().size());
                }
            } catch (Exception e) {
                System.err.println("[Poll " + poll + "] Error: " + e.getMessage());
            }

            if (poll < TOTAL_POLLS - 1) {
                Thread.sleep(DELAY_MS);
            }
        }

        System.out.println("\nTotal raw events: " + allEvents.size());


        Set<String> seen= new HashSet<>();
        List<QuizEvent> uniqueEvents = new ArrayList<>();
        int dropped=0;

        for (QuizEvent event : allEvents) {
            if (seen.add(event.getDedupKey())) {
                uniqueEvents.add(event);
            } else {
                dropped++;
            }
        }

        System.out.println("Unique events   : " + uniqueEvents.size());
        System.out.println("Duplicates dropped: " + dropped);


        Map<String, Integer> scores = new LinkedHashMap<>();
        for (QuizEvent e : uniqueEvents) {
            scores.merge(e.getParticipant(), e.getScore(), Integer::sum);
        }


        List<LeaderBoardEntry> leaderboard = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            leaderboard.add(new LeaderBoardEntry(entry.getKey(), entry.getValue()));
        }
        Collections.sort(leaderboard);

        int grandTotal = leaderboard.stream().mapToInt(LeaderBoardEntry::getTotalScore).sum();
        System.out.println("Grand total     : " + grandTotal);


        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("regNo", REG_NO);
        payload.put("leaderboard", leaderboard);

        String submitUrl = BASE_URL + "/quiz/submit";
        Map<?, ?> submitResponse = restTemplate.postForObject(submitUrl, payload, Map.class);

        System.out.println("Submit response : " + submitResponse);


        Map<String, Object> result = new LinkedHashMap<>();
        result.put("leaderboard", leaderboard);
        result.put("grandTotal", grandTotal);
        result.put("submitResponse", submitResponse);
        return result;
    }
}
