package com.sgkhmjaes.jdias.service.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class SearchTags {

    public Set<String> searchingTags(String post) {
        Set<String> tags = new HashSet<>();
        if (post == null || post.isEmpty()) return tags;
        int tagsCount = 0;
        StringTokenizer st = new StringTokenizer(" " + post, "#");
        while (st.hasMoreTokens()) {
            tagsCount++;
            if (tagsCount > 1) {
                StringTokenizer getOneWord = new StringTokenizer(st.nextToken());
                if (getOneWord.hasMoreTokens()) {
                    char[] tagInCharArray = getOneWord.nextToken().toLowerCase().toCharArray();
                    for (int i = tagInCharArray.length - 1; i >= 0; i--) {
                        if (Character.isLetterOrDigit(tagInCharArray[i])) {
                            if (tagInCharArray.length == i + 1) tags.add(new String(tagInCharArray));
                            else tags.add(new String(Arrays.copyOfRange(tagInCharArray, 0, i + 1)));
                            break;
            }}}}
            else st.nextToken();
        }
        //tags.remove("");
        return tags;
    }

}
