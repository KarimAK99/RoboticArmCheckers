# Python main class
# =========================================
# NOTE(s)
#	- to import a python file, write "import [name]" without the .py extension
#	- running a script can be done only once
#	- you can run a method multiple times via "[filename].[methodcall]()"
#	- files must be in the same directory as this main class (package handling hasn't been implemented)


# Initial set-up code.
import time

# Import the files to be called.
import BoardReturnLoop
import motion_vision
import sercom

# Initiate loop.
print("Entering python main loop-- ")
motion_vision.motion_color()
while 1:
	# Detect the board positions
	BoardReturnLoop.main()

	# Detect if there is obstruction
	motion_vision.motion_color()

	# Begin moving
	#sercom.main()

	# Repeat loop...
	print("Python loop complete, will repeat after a second delay!-- ")
	time.sleep(1)

# Initiate termination.
# this is empty because we should never reach this part...
