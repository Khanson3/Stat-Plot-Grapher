# Stat-Plotter by Kyle Hanson
Graphing app for Android that plots user inputted data

Stat Plotter takes in user inputted data points and plots a scatter plot with an optional regression line or box-and-whisker plot. The data input screen handles all possible exceptions I could think of with snackbars that popup to tell the user the exception. The most difficult part of this project was understanding how to use the SurfaceView class with so little examples online that I could find. I had trouble trying to format the graph as well; the SurfaceView is oriented with an x-axis that grew positively in the right direction as I expected it to, while the y-axis grew positively downwards which complicated things a bit further. This forced me to reflect all the data points over the x-axis after scaling and translating so the data wouldn't be upside down.

**Coding Log**

**9/9**
- Started and finished the design to the title/data point input page

**9/11**
- Coded 'Enter" logic for EditTexts

**9/14-9/15**
- Fully functional data input screen
- Passed data point list from data input page to scatter plot page and box-and-whisker plot page

**9/17**
- Designed scatter plot screen and box-and-whisker plot screen
- Added MyGraph.java that extends SurfaceView

**9/21-10/2 (Here and there additions throughout)**
- Updates to design (Adding background images, color modifications, etc)
- Finally figured out SurfaceView class kinda
- Graph scaling took forever to figure out (still kinda broken even though it was working fine earlier)
- Added onTouch event that descales the screen coordinates to the data point coordinates and displays the coordinates of the closest data point at the top of the screen
- Lots of broken features that I will continue to update upon