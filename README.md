# soundmatch

Hack the North 2017 Submission by @dhron, @shaybanerjee, @iameskay, @paulhuijiali

## Inspiration

We wanted to build something that gives you feedback when you're playing the violin. Most of the time, beginners don't have an ear for correctness in terms of the music, so feedback is very important early on to becoming a good musician. 

## What it does
Our app is on the Android platform, which uses the microphone to listen to the music being played. Our backend API parses .mid files that you can build with MuseScore, and our app will compare the song being played to the sheet music that was parsed. The phone app will then give you feedback in sequence whether the notes are correct and then give you a percentage summary on how you did.

## How We built it
We used most of the app using a baseline for reading sound input and then incorporating logic for the analysis of the microphone and calculating feedback. The parsed song itself is put onto a stack of notes, and then each note is popped off the stack when it has a successful read. We used Heroku to host our Node.js API which parses the song's sheet music, and then used Android's functionality to read in the microphone input and generate feedback.

## Challenges We ran into
Since our application involves dealing with reading frequencies in real time. There were many instances where external sounds had an impact on our readings. Additionally, since our program is analyzing real-time music while performing a comparative analysis to frequencies of the same song played perfectly, there were many instances where frequencies were misinterpreted by our engine. We were able to handle this by creating a stack-popping algorithm with a button to indicate when the song has finished playing. This was a simple, but optimal solution to our problem. 

Another issue that we faced was having to ramp up on Android development. Luckily, one of our group members (Simran) had prior experience with android and was able to provide support to our other members. 

## Accomplishments that we're proud of
We are proud of the fact that our application works. We all ended up learning something new; android development, deploying an API with Heroku, managing a project among four people, working with sound and microphone input.  We believe that building this application was extremely challenging, especially under a time constraint and the mathematics behind manipulating and reading sound input; however, our strong teamwork helped us persevere through this difficult task, and make it become something functional.

## What We learned
We learned android mobile development and interesting ways to apply OOP to an android application. We also learned the importance of strong planning. We also learned that working with sound input is very complex because the input comes in as waves and there's a lot of variability in the input due to the microphone on the machine.

## What's next for SoundMatch
SoundMatch is an application that we would love to make IOS compatible. As well, there are a few tweaks we would like to perform on our application to increase accuracy and performance. There's also room for machine learning algorithms and statistical analysis of one's music performance, and how to make the app so it's resilient to other sound waves interfering with the music being played; this would be cool to incorporate in the future. 

https://devpost.com/software/soundmatch
