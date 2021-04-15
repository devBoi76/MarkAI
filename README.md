# MarkAI
Markov chain based AI discord bot written in Java

The .jar is in `target/markov-1.0-SNAPSHOT-jar-with-dependencies.jar`

run with `java -jar markov-1.0-SNAPSHOT-jar-with-dependencies.jar OAUTH TOKEN FOR DISCORD`

To change the bot's status message, change `JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new Bot())
                .setActivity(Activity.playing("CHANGE THIS"))
                .build();` this
