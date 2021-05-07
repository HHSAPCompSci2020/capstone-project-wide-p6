[Slash Dash]
Authors: Ethan Wang & Justin Zhu
Revision: 4/26/2021


Introduction: 
[In a few paragraphs totaling about ½ page, introduce the high-level concept of your program. What this looks like depends a lot on what type of thing you are making. An introduction for an application will look different than one for a game. In general, your introduction should address questions like these:
What does your program do?What problem does it solve? Why did you write it?


What is the story?


What are the rules? What is the goal?


Who would want to use your program?


What are the primary features of your program?


        Our project’s name is Slash Dash. Slash Dash is a game, based heavily upon metroidvanias. We made it because we like video games, and would like to make something which we ourselves would use and enjoy. In our game you wander around the map, jumping and dashing across platforms, fighting enemies, avoiding dangerous obstacles, and discovering new checkpoints. This will be a 2d game where you are given a side view of your character, and you will be able to move side to side and jump up, and down. You will also be able to dash midair and attack any enemies who may approach you. Our project’s target audience is video game players who like platforming and fighting games, but we think it’s a game anyone will be able to enjoy. 








Instructions:
[Explain how to use the program. This needs to be specific: 
Which keyboard keys will do what? 
Where will you need to click? 
Will you have menus that need to be navigated? What will they look like? 
Do actions need to be taken in a certain order?]


W - jump
A - Move left
S - dive attack
D - Move right


Q - Normal Attack
E - Heavy Attack / Heavy Dive (Press E during dive attack)


Space - dash attack


Features List (THE ONLY SECTION THAT CANNOT CHANGE LATER):
Must-have Features:
[These are features that we agree you will definitely have by the project due date. A good final project would have all of these completed. At least 5 are required. Each feature should be fully described (at least a few full sentences for each)]
* We will have a full physics engine. It will not have any sort of rotation but it will allow the player to move and jump around while being dragged down by gravity, and it will have collisions between players and obstacles. There will be some actions the player can do to slow their descent or even move around in midair to help them travel around.
* We will have enemies which will attempt to attack the player in various ways (through contact or by shooting projectiles). These enemies could be affected by the physics engine and walk on the ground, or they could just fly around and go through walls depending on the type. If you take enough damage due to these enemies you will die and go back to your most recently passed checkpoint.
* Along with our enemies we want ways to attack them back. If you attack enemies enough times you’ll cause them to temporarily die until they eventually respawn again.
* Our game will include a large map along with some checkpoints along the way. The map will scroll sideways or up and down depending on where you’re supposed to be moving, and will include various obstacles to challenge players who are trying to move throughout it. Falling off the map, or falling onto certain obstacles will damage or kill the player, sending them back to their most recently passed checkpoint.
* We will have a pause menu with which you can have a breather or adjust some settings, for example which keys bind to which things or volume adjustment.


Want-to-have Features:
[These are features that you would like to have by the project due date, but you’re unsure whether you’ll hit all of them. A good final project would have perhaps half of these completed. At least 5 are required. Again, fully describe each.]
* We want to have a powerful boss which you can fight once you pass every checkpoint on the map. This boss will hopefully be fun to fight, but hard to beat. After you beat this boss, the game should more or less be completed.
* We intend to have many sound effects to add more impact into the game. For example, when you attack enemies, when you run along, when enemies attack you, when you jump, or when you land on a platform.
* We want to have a map that you can access in the pause menu to both see where you are at the moment and to teleport to a previously passed checkpoint. 
* More interaction with the map. Like checkpoints, there can be more things the map would do. For example, certain areas only open when the nearby enemies are defeated, falling platforms, or there could be levers and buttons to get things done.
* A more advanced combat system mostly focused on midair combat, where the more times you hit enemies before touching the ground, the more damage you begin to do. In addition to doing more damage, you will charge your ultimate skill. Although it will be possible to play it safe by mostly fighting on the ground, we’ll try to make it much more rewarding to stay in the air, despite being more difficult. 


Stretch Features:
[These are features that we agree a fully complete version of this program would have, but that you probably will not have time to implement. A good final project does not necessarily need to have any of these completed at all. At least 3 are required. Again, fully describe each.]
*  In a fully completed version of this program, we would probably have a storyline as well as more bosses as a reason to actually complete the game.
*  In a fully completed version of this game, There would definitely be upgrades you could get along the way to become more powerful, adding a satisfying sense of progression for players. For example new weapons or new types of attacks for more damage and visual flair.
*  A fully completed version of the game would also have npcs and other environmental but not hostile creatures. This would just be to complete the feel of it being more of a world than just running through killing things. It would also probably make the story significantly easier to advance and make the game more visually pleasing with more movement.


Class List:
[This section lists the Java classes that make up the program and very briefly describes what each represents. It’s totally fine to put this section in list format and not to use full sentences.]
* Player
* Map
* Entities
* MainMenu
* MovingImage
* DrawingSurface
* Main
Credits:
[Gives credit for project components. This includes both internal credit (your group members) and external credit (other people, websites, libraries). To do this:
* List the group members and describe how each member contributed to the completion of the final program. This could be classes written, art assets created, leadership/organizational skills exercises, or other tasks. Initially, this is how you plan on splitting the work.
   * Ethan: Coding 
   * Justin: Coding, Level design, graphic design, sound design, paperwork.
* Give credit to all outside resources used. This includes downloaded images or sounds, external java libraries, parent/tutor/student coding help, etc.]
   * Used the Processing - Game and Physics demo to start off.