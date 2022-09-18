# CatSweeper

Android app created using Android Studio and coded in Java and XML. CatSweeper functions similarly to MineSweeper, but instead of mines, you are trying to avoid the sleeping cats and stars are used instead of flags. Like MineSweeper, I added a feature to make sure that the first click will never result in the player hitting a sleeping cat. If the tile the player first clicks on is a cat then the cat is moved to an adjacent tile (see potential bugs). In the future, I am considering adding different levels, a timer, a ranking system, better designs for icons, a tutorial, sound/music, and an introduction screen that explains the concept of the game and how to play it. I am also considering uploading this app to the Google Play Store and creating an iOS version using Xcode with Swift.


Potential Bugs:
- If the player's first click is a sleeping cat and all of the adjacent tiles also have sleeping cats under them then the sleeping cat is not successfully moved. This isn't much of a problem right now given the small number of sleeping cats but will need to be fixed in the future for bigger games
