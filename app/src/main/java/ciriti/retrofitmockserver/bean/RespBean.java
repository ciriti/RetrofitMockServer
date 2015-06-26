package ciriti.retrofitmockserver.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by carmelo.iriti on 26/06/2015.
 */
@Data
public class RespBean {

    @Expose
    @SerializedName("items")
    private List<User> users = new ArrayList<User>();
    @SerializedName("has_more")
    @Expose
    private Boolean hasMore;
    @SerializedName("quota_max")
    @Expose
    private Integer quotaMax;
    @SerializedName("quota_remaining")
    @Expose
    private Integer quotaRemaining;


    @Data
    public static class User {
        @SerializedName("badge_counts")
        @Expose
        private BadgeCounts badgeCounts;
        @SerializedName("account_id")
        @Expose
        private Integer accountId;
        @SerializedName("is_employee")
        @Expose
        private Boolean isEmployee;
        @SerializedName("last_modified_date")
        @Expose
        private Integer lastModifiedDate;
        @SerializedName("last_access_date")
        @Expose
        private Integer lastAccessDate;
        @Expose
        private Integer age;
        @SerializedName("reputation_change_year")
        @Expose
        private Integer reputationChangeYear;
        @SerializedName("reputation_change_quarter")
        @Expose
        private Integer reputationChangeQuarter;
        @SerializedName("reputation_change_month")
        @Expose
        private Integer reputationChangeMonth;
        @SerializedName("reputation_change_week")
        @Expose
        private Integer reputationChangeWeek;
        @SerializedName("reputation_change_day")
        @Expose
        private Integer reputationChangeDay;
        @Expose
        private Integer reputation;
        @SerializedName("creation_date")
        @Expose
        private Integer creationDate;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("accept_rate")
        @Expose
        private Integer acceptRate;
        @Expose
        private String location;
        @SerializedName("website_url")
        @Expose
        private String websiteUrl;
        @Expose
        private String link;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;
        @SerializedName("display_name")
        @Expose
        private String displayName;

        @Data
        public static class BadgeCounts {
            @Expose
            private Integer bronze;
            @Expose
            private Integer silver;
            @Expose
            private Integer gold;
        }
    }

}
