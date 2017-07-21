
package com.sgkhmjaes.jdias.service.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class ParseTag {
    
    private final Set<String> tags = new HashSet<>();
    private String textWithTags;
    private String statusMessageText;
    
    public ParseTag (String statusMessageText) {
        this.statusMessageText = statusMessageText;
        searchingTags();
    }
    
    public String getStatusMessageText () {
        return textWithTags;
    }
    
    public Set <String> getTag () {
        return tags;
    }
    
    private void searchingTags() {
        Set<String> allTags = new HashSet<>();
        if (statusMessageText == null || statusMessageText.isEmpty()) return;
        int tagsCount = 0;
        StringTokenizer st = new StringTokenizer(" " + statusMessageText, "#");
        while (st.hasMoreTokens()) {
            tagsCount++;
            if (tagsCount > 1) {
                StringTokenizer getOneWord = new StringTokenizer(st.nextToken());
                if (getOneWord.hasMoreTokens()) {
                    char[] tagInCharArray = getOneWord.nextToken().toLowerCase().toCharArray();
                    for (int i = tagInCharArray.length - 1; i >= 0; i--) {
                        if (Character.isLetterOrDigit(tagInCharArray[i])) {
                            if (tagInCharArray.length == i + 1) allTags.add(new String(tagInCharArray));
                            else allTags.add(new String(Arrays.copyOfRange(tagInCharArray, 0, i + 1)));
                            break;
            }}}}
            else st.nextToken();
        }
        //tags.remove("");
        //Set<String> tagsToLowerCase = new HashSet<>();
        for (String tag : allTags) {
            String tagToLowerCase = tag.toLowerCase();
            statusMessageText = statusMessageText.replace("#"+tag, "<a href=/api/tags/posts/"+tagToLowerCase+">#"+tag+"</a>");
            tags.add(tagToLowerCase);
        }
        textWithTags = statusMessageText;
        //return tagsToLowerCase;
        //return tags;
    }
    
    
}
