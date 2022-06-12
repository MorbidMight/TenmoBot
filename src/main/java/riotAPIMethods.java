import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.match.Match;
import com.merakianalytics.orianna.types.core.match.MatchHistories;
import com.merakianalytics.orianna.types.core.match.MatchHistory;
import com.merakianalytics.orianna.types.core.match.Matches;
import com.merakianalytics.orianna.types.core.staticdata.Item;
import com.merakianalytics.orianna.types.core.staticdata.ReforgedRunes;
import com.merakianalytics.orianna.types.core.staticdata.Rune;
import com.merakianalytics.orianna.types.core.staticdata.Runes;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMasteries;
import com.merakianalytics.orianna.types.core.league.League;
import com.merakianalytics.orianna.types.core.league.LeaguePositions;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import com.merakianalytics.orianna.types.core.status.ShardStatus;
import com.merakianalytics.orianna.types.core.status.ShardStatuses;
import com.merakianalytics.orianna.types.common.Queue;
import org.json.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.text.NumberFormat;

public class riotAPIMethods {
    public static void main(String[] args) {
    	System.out.print(printMasteries("MorbidMight"));
    }
    public static String printMasteries(String summoner)
    {
    	
    	try {
    		String contents = new String(Files.readAllBytes(Paths.get("ENTER DDRAGON.11.24 LOCAL PATH")));
	    	JSONObject champsJson = new JSONObject(contents);
	    	champsJson = champsJson.getJSONObject("data");
	    	JSONObject temp;
	    	ArrayList<String> champs = new ArrayList<String>();
	    	ArrayList<String> ids = new ArrayList<String>();
	    	int y = 0;
	    	for(String x: champsJson.keySet())
	    	{
	    		temp = champsJson.getJSONObject(x);
	    		champs.add(temp.getString("name"));
	    		ids.add(temp.getString("key"));
	    	}
	    	Summoner me = Summoner.named(summoner).withRegion(Region.NORTH_AMERICA).get();
	    	ChampionMasteries masteries = ChampionMasteries.forSummoner(me).get();
	    	JSONObject obj = new JSONObject(masteries.toJSON());
	    	JSONArray arr = obj.getJSONArray("data");
	    	
	    	NumberFormat myFormat = NumberFormat.getInstance();
	    	myFormat.setGroupingUsed(true);
	    	
	    	int index;
	    	String tempStr;
	    	String finalS = "Top Ten Masteries for " + summoner + " \n" ;
	    	for(int x = 0; x < 10; x++)
	    	{
	    		temp = arr.getJSONObject(x);
	    		tempStr = Integer.toString(temp.getInt("championId"));
	    		if(ids.contains(tempStr))
	    		{
	    			index = ids.indexOf(tempStr);
	    			finalS += ((x + 1) + ". " + champs.get(index) + ", " + "Mastery: " + myFormat.format(temp.getDouble("points")) + " Level: " + temp.get("level") + "\n");
	    		}
	    	}
	    	return finalS;
    	}catch(IOException e)
    	{
    		e.printStackTrace();
    		return "error";
    	}
    }
    public static String getRank(String summoner)
    {
    	Summoner me = Summoner.named(summoner).withRegion(Region.NORTH_AMERICA).get();
    	LeaguePositions positions = LeaguePositions.forSummoner(me).get();
        JSONObject posObj = new JSONObject(positions.toJSON());
        JSONArray arr = posObj.getJSONArray("data");
        JSONObject soloDuo = arr.getJSONObject(0);
        for(int x = 0; x < arr.length(); x++)
        {
        	if(arr.getJSONObject(x).getString("queue").equals("RANKED_SOLO_5x5"))
        	{
        		soloDuo = arr.getJSONObject(x);
        	}
        }
        int lp = 0;
        try {
        	lp = soloDuo.getInt("leaguePoints");
        
        }catch(Exception e){
        	//LP is supposed to be 0
        }
        String finalS = "";
        String tier = soloDuo.getString("tier");
        tier = tier.toLowerCase();
        tier = tier.substring(0, 1).toUpperCase() + tier.substring(1);
        finalS += tier + " " + soloDuo.getString("division") + " " + lp + " LP |" + "(" + soloDuo.getInt("wins") + " wins" + " / " + soloDuo.getInt("losses") + " losses)";
        return finalS;
    	
    }
    
    public static void getItem(String itemName)
    {
    	 ReforgedRunes reforgedRunes = ReforgedRunes.withRegion(Region.NORTH_AMERICA).get();
    	System.out.println(reforgedRunes);
    }
}
		