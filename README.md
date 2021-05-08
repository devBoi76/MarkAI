# MarkAI
Markov chain based AI discord bot written in Java 11

He learns from messages written on the server that are not sent by a bot and don't have "markai" in them

The newest .jar is in `target/markov-VERSION-jar-with-dependencies.jar`
An older .jar is in the release
If something with the .jar from `target` breaks, don't blame it on me - it's basically an alpha version

I don't know if this bot works on Windows

run with `java -jar markov-VERSION-jar-with-dependencies.jar`

Configure with config/CONFIG (provide the bot token there, also other settings)

Words are stored in the `words` folder, each word has it's own file with weighs. To change what chance a word has to appear after the previous one, change the weigh (higher = more often)
