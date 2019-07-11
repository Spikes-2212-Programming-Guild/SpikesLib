# SpikesLib
Welcome to SpikesLib!

SpikesLib is an open source, generic library, which extends <a href='https://github.com/wpilibsuite/allwpilib'> FRC WPILib </a> by adding useful features to it.
This library was written by the FRC team The Spikes #2212. For more information about us, visit <a href='http://www.spikes2212.com/'> our site</a>.

* ####  <a href='#Purpose'>What Is the purpose of SpikesLib?</a>
* ####  <a href='#Download&Setup'>How can I install SpikesLib?</a>
* ####  <a href='#Exmps'>How do I use SpikesLib?</a>
* ####  <a href='#Contacts'>Have questions? Here are people to ask</a>

## <a name = 'Purpose'> The Purpose Of The Library 
There are many concepts in First Robotics Competition that repeat themeselves every year, with a small change in implemantation. The purpose of SpikesLib is to reduce time in writing code for your robot and reduce the chances of encountering bugs, by creating generic classes for common types of subsystems and commands.
  
## <a name = 'Download&Setup'> Download and Setup SpikesLib </a>

### Using SpikesLib in Gradle Projects
#### Using Maven Artifacts
SpikesLib includes Gradle Support Since 2019. <br>
In order to add SpikesLib to your Gradle FRC Project
you should add the following lines  to the repositories section of your build.gradle
```
maven {
    url = "https://mymavenrepo.com/repo/fDwN5Fqu32WHQ4GM9lTY/"
}
```
then, add the following line to your dependencies section <br>
 `compile "com.spikes2212:sl:3.1.2"`
 
Note that you should run a build task once while connected to the internet before deploying your code to the RIO.

#### Using A Local Jar

download the library (.zip file) from <a href='https://github.com/Spikes-2212-Programming-Guild/SpikesLib/releases'> the latest release </a> and you can download the jar there.
1. add a `libs` directory to your project root.
2. Extract the SpikesLib2.zip to that folder
3. in your `gradle.build` go to the `dependencies` section.
once there, add the following line `compile files('libs/YOUR_JAR.jar')` under all the other dependencies.

### Using SpikesLib in Eclipse Projects
1. download the .jar file using the method mentioned above into C:\Users\%USERNAME%\wpilib\user\java\lib
2. Open Eclipse
3. Right click on the wanted project
4. Hover on Build Path
5. Click on Configure Build Path
6. Click on Add External JARs
7. Find and add the SpikesLib jar file
8. return 0

## <a name = 'Exmps'> Examples </a>
We created a repository with a pleathra of <a href='https://github.com/Spikes-2212-Programming-Guild/Spikes-Lib-Example'> examples of how to use SpikesLib</a>. <br/>
You can learn how to use this library by reading the examples and playing with them.

## <a name = 'Contacts'> Contacts </a>
Omri "Riki" Cohen - +972 54-626-4324 <br/>
Matan Grynbaum Nachmias - +972 54-335-3148
