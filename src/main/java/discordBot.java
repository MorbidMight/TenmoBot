import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

public class discordBot {
	public static void main(String[] args)
	{
		DiscordClient client = DiscordClient.create("YOUR DISCORD API KEY");
		Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) -> {
			  Mono<Void> printRank = gateway.on(MessageCreateEvent.class, event -> {
				    Message message = event.getMessage();
				      if (message.getContent().toLowerCase().contains("!rank")) 
				      {
				    	  String summoner = message.getContent().split(" ")[1];
				    	  return message.getChannel()
				              .flatMap(channel -> channel.createMessage(riotAPIMethods.getRank(summoner)));
				      }
				    return Mono.empty();
			  }).then();


			  Mono<Void> printMasteries = gateway.on(MessageCreateEvent.class, event -> {
			    Message message = event.getMessage();
			    
			      if (message.getContent().toLowerCase().contains("!masteries")) 
			      {
			    	  String summoner = message.getContent().substring(message.getContent().indexOf(" "));
			    	  return message.getChannel()
			              .flatMap(channel -> channel.createMessage(riotAPIMethods.printMasteries(summoner)));
			      }
			    return Mono.empty();
			  }).then();

			  return printRank.and(printMasteries);
			});		
		login.block();
	}
}
