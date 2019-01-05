# SpikesLib
Welcome to SpikesLib!

SpikesLib is an open source, generic library, which extends <a href='https://github.com/wpilibsuite/allwpilib'> FRC WPILib </a> by adding useful features to it.
This library was written by the FRC team The Spikes #2212. For more information about us, visit <a href='http://www.spikes2212.com/'> our site</a>.

* ####  <a href='#Purpose'>What Is the purpose of SpikesLib?</a>
* ####  <a href='#Download&Setup'>How can I install SpikesLib?</a>
* ####  <a href='#Exmps'>How do I use SpikesLib?</a>
* ####  <a href='#Contacts'>Have questions? Here are people to ask</a>

## <a name = 'Purpose'> The Purpose Of The Library 
There are many concepts in First Robotics Competition that repeat themeselves every year, with a small change in implemantation. The purpose of SpikesLib is to reduce time in writing code for your robot and reduce the chances of incountering bugs, by creating generic classes for common types of subsystems and commands.
  
## <a name = 'Download&Setup'> Download and Setup SpikesLib </a>

### Download
To download the library (.zip file) go to <a href='https://github.com/Spikes-2212-Programming-Guild/SpikesLib/releases'> the latest release </a> and you can download the jar there.

### Installation (for Eclipse)
1. Extract the SpikesLib2.zip file into C:\Users\%USERNAME%\wpilib\user\java\lib
2. Open Eclipse
3. Right click on the wanted project
4. Hover on Build Path
5. Click on Configure Build Path
6. Click on Add External JARs
7. Find and add the SpikesLib jar file
8. return 0

### Using SpikesLib in Gradle Projects
Currently, there are no artifacts maven for SpikesLib.
Regardless of that, you can include the SpikesLib jar file in your gradle projects.
1. add a `libs` directory to your project root.
2. add the .jar file to that folder
3. in your `gradle.build` go to the `dependencies` section.
once there, add the following line `compile files('libs/YOUR_JAR.jar')` under all the other dependencies.
## <a name = 'Exmps'> Examples </a>
We created a repository with a pleathra of <a href='https://github.com/Spikes-2212-Programming-Guild/Spikes-Lib-Example'> examples of how to use SpikesLib</a>. <br/>
You can learn how to use this library by reading the examples and playing with them.

## <a name = 'Contacts'> Contacts </a>
Omri "Riki" Cohen - +972 54-626-4324 <br/>
Uriah "Jhonny" Rokach - +972 58-430-9901
