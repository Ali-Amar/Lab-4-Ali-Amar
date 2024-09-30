/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 */
package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Extract {

    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.isEmpty()) {
            return new Timespan(null, null); 
        }
        
        Instant start = tweets.get(0).getTimestamp();
        Instant end = tweets.get(0).getTimestamp();

        for (Tweet tweet : tweets) {
            Instant timestamp = tweet.getTimestamp();
            if (timestamp.isBefore(start)) {
                start = timestamp;
            }
            if (timestamp.isAfter(end)) {
                end = timestamp;
            }
        }
        return new Timespan(start, end);
    }

    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();

        for (Tweet tweet : tweets) {
            String text = tweet.getText();
            String[] words = text.split("\\s+");
            
            for (String word : words) {
                if (word.startsWith("@")) {
                    String username = word.substring(1);
                    if (username.matches("[A-Za-z0-9_]{1,15}")) {
                        mentionedUsers.add(username.toLowerCase());
                    }
                }
            }
        }
        return mentionedUsers;
    }
}