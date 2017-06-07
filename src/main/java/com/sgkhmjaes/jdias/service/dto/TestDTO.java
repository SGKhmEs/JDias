
package com.sgkhmjaes.jdias.service.dto;

import com.sgkhmjaes.jdias.domain.enumeration.PostType;
import java.util.List;

public class TestDTO {
    
    private PostType postType;
    private List <String> mentioned_people;

    public TestDTO() {}

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public List<String> getMentioned_people() {
        return mentioned_people;
    }

    public void setMentioned_people(List<String> mentioned_people) {
        this.mentioned_people = mentioned_people;
    }
    
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TestDTO: { postType=").
                append(postType).
                append(", mentioned_people=").append(mentioned_people).append("}");
        return sb.toString();
    }
    
    
    
}
