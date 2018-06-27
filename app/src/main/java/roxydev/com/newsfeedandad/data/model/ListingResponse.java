package roxydev.com.newsfeedandad.data.model;

import java.util.List;

public class ListingResponse {
    public ListingData data;

    public ListingResponse(ListingData data) {
        this.data = data;
    }

    public class ListingData{
        public List<RedditChildrenResponse> children;
        public String after;
        public String before;

        public ListingData(List<RedditChildrenResponse> children, String after, String before) {
            this.children = children;
            this.after = after;
            this.before = before;
        }

        public class RedditChildrenResponse{

            public RedditModel data;

            public RedditChildrenResponse(RedditModel model) {
                this.data = model;
            }
        }
    }
}
