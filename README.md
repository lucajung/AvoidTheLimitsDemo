# AvoidTheLimitsDemo
A simple Demo to show how fast neural networks can learn how to play a game.

What's the goal?
---
The goal is to not touch the border.<br><br>
<img src="/images/readme-2.gif" width="300">

What are the restrictions?
---
Each character is accelerated by a value X automatically.
In order not to touch the edge, the character must change his direction. But they are unable to change it to the opposite direction.
Also they have to move a bit after they changed their direction.

How does it work?
---
Every character gets an own fully connected neural network at the beginning. Each neural network is consists out of 4 input neurons, 8 hidden neurons and 4 output neurons.
After they die (touched the border), the neural network gets mutated at 70 random weights.<br><br>
<img src="/images/readme-1.gif" width="300">

Video
---
<a href="https://youtu.be/wiBAb6uY2Tc">https://youtu.be/wiBAb6uY2Tc</a>
