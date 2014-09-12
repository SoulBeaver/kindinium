# Kindinium

## Vindinium for Kotlin

Welcome to [Vindinium](vindinium.org), the fun AI challeng that lets you battle heroes and goblins!
This client is written in Kotlin and is fully functional, allowing you to jump right in, write a bot,
and smash those heroes in epic battle!

## What do I need?

If you haven't registered yourself yet, please head to the [Vindinium Registration Page](http://vindinium.org/register)
and create a bot. Once you have registered you should see an API Key such as 'hie44sww'- this API Key
is bound to *dummy123*.

Open the file *VindiniumClient.kt* and modify the line

    val API_KEY = ""
   
That's technically it! A random bot will now participate in a training game whenever you launch the app.

## How do I change the game mode?

The program supports both the *arena* and *training* modes. By default, a training mode with 300
turns, and a random map is chosen.

### I want to play in Arena mode!

Run the game with *-m arena*

### I want to play in a predefined map!

(Only in training mode) Run the game with *-map m#* where m# is in range of m1..m6

### I want to change the number of turns a game is played!

(Only in training mode) Run the game with *-t #* where # is the number of turn you'd like to play.

## How do I change my bot?

The random bot is simple, stupid, and not likely to win you any prizes. You *could* simply extend
the rando bot, but then it wouldn't be a RandomBot anymore :) Instead, head to *Main.kt* and
modify the line

    val client = VindiniumClient(RandomBot())
   
With the bot of your choosing. Please implement the *Bot* trait.

## What's in store for future iterations?

I'd like to expand the map. Right now you get the raw, uninterpreted map and a bunch of unimplemented functions.
That should change very soon and give you detailed, important information about the map and the object
contained therein.

## I have further questions!

Don't hesitate to ask me anything. My knowledge so far isn't unlimited, but I enjoy helping out
as much as possible.