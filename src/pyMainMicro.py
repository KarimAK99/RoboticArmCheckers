# Python main class
# =========================================
# NOTE(s)
#	- to import a python file, write "import [name]" without the .py extension
#	- running a script can be done only once
#	- you can run a method multiple times via "[filename].[methodcall]()"
#	- files must be in the same directory as this main class (package handling hasn't been implemented)


# Import the files to be called.
import BoardReturnLoop
import motion_vision
import sercom
import subprocess

# Initial set-up code.
import time
import serial


def serStart():
    baudRate = 9600
    serPort = 'COM3'
    ser = serial.Serial(serPort, baudRate, timeout=5)  # YYY microcontroller dependency.
    print("Serial port " + serPort + " opened/ Baudrate " + str(baudRate))
    startMarker = 60
    endMarker = 62

    return ser


# Initiate loop.
# ser = serStart() # ZZZ
print("Entering python main loop-- ")
counter = 0
while counter < 1000:
    # Detect the board positions.
    BoardReturnLoop.main()

    # Detect if there is obstruction.
    motion_vision.motion_color()

    subprocess.call(['java', '-jar', 'RoboticArmCheckers1.jar'])

    # Begin moving.
    # sercom.main() # ZZZ
    # ser = serStart()
    # sercom.automaticMode(ser)
    input('Press Enter to continue ...')
    counter = counter + 1
    print(counter)
    # Repeat loop...
    #print("Python loop complete, will repeat after a second delay!-- ")
    #time.sleep(1)

# Initiate termination.
# ser.close() # ZZZ
