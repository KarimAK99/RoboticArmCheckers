# Python main class
# =========================================
# NOTE(s)
#	- to import a python file, write "import [name]" without the .py extension
#	- running a script can be done only once
#	- you can run a method multiple times via "[filename].[methodcall]()"
#	- files must be in the same directory as this main class (package handling hasn't been implemented)


# Initial set-up code.
import time
import serial

# Import the files to be called.
import BoardReturnLoop
import motion_vision
import sercom


# Initiate loop.
print("Entering python main loop-- ")
baudRate = 9600
serPort = '/dev/ttyCOM11'
ser = serial.Serial(serPort, baudRate, timeout=5) # YYY microcontroller dependency.
print("Serial port " + serPort + " opened/ Baudrate " + str(baudRate))
startMarker = 60
endMarker = 62
while 1:
	# Detect the board positions
	BoardReturnLoop.main()

	# Detect if there is obstruction
	motion_vision.motion_color()

	# Begin moving
	sercom.automaticMode(ser)

	# Repeat loop...
	print("Python loop complete, will repeat after a second delay!-- ")
	time.sleep(1)

# Initiate termination.
# this is empty because we should never reach this part...
