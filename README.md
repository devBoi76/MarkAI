# MarkAI
Markov chain based AI discord bot written in Java

He learns from messages written on the server that are not sent by a bot and don't have "markai" in them

The .jar is in `target/markov-VERSION-jar-with-dependencies.jar`

run with `java -jar markov-VERSION-jar-with-dependencies.jar BOT TOKEN`

To change the bot's status message, change `JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new Bot())
                .setActivity(Activity.playing("CHANGE THIS"))
                .build();` this

Words are stored in the `words` folder (make sure to make one, was too lazy to code it in), each word has it's own file with weighs. To change what chance a word has to appear after the previous one, change the weigh (higher = more often)
