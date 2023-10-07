import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Recommendation {
    public Map<String , List<String>> searchHistory; // people-> searchhistory
    public List<String> categories;
    public Map<String, Integer> categoryScore;
    public List<String> searches;

    /*
        the constructor
     */
        public Recommendation(List<String> categories, Map<String, List<String>> searchHistory, String userId){
            this.searchHistory = searchHistory;
            this.searches = searchHistory.get(userId);
            this.categories = categories;
        }





}
