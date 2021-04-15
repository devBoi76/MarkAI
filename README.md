# MarkAI
Markov chain based AI discord bot written in Java

The .jar is in `target/markov-1.0-SNAPSHOT-jar-with-dependencies.jar`

run with `java -jar markov-1.0-SNAPSHOT-jar-with-dependencies.jar OAUTH TOKEN FOR DISCORD`

To change the bot's status message, change `JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new Bot())
                .setActivity(Activity.playing("CHANGE THIS"))
                .build();` this

Words are stored in the `words` folder (make sure to make one, was to lazy to code it in), each word has it's own file with weighs. To change what chance a word has to appear after the previous one, change the weigh (higher = more often)
