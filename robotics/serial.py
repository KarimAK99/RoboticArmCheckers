#
#   TODO
#==================================================
#   Can read inputs from non-terminal source

#
#   IMPORTS
#==================================================
import serial
import time
import math

#
#   SET-UP CODE
#==================================================
baudRate = 9600
serPort = "/dev/ttyACM1"
ser = serial.Serial(serPort, baudRate, timeout = 5)
print("Serial port " + serPort + " opened/ Baudrate " + str(baudRate))
startMarker = 60
endMarker = 62

mode = input("Please enter 0 for terminal mode and 1 for automatic mode.")
if mode is 0:
    terminalMode()
if mode is 1:
    automaticMode()

#
#   MODE 1: Teminal Mode
#==================================================
def terminalMode():
    val=input("Please input 1', 2', 3' and Magnet state:  ")

    while(val != "terminate"):
        # Convert to bytes in "newline mode".
        val = val+"\n"
        ser.write(val.encode())
        
        # Receive completion notice.
        #while(1 == 2):
        #    print("pain is love")

        # Checking while condition...
        val=input("Please input 1', 2', 3' and Magnet state:  ")
    ser.close #closes serial port

#
#   MODE 2: Automatic Mode
#==================================================
def automaticMode():
    # Read input somehow

    while(val != "terminate"):
        # Convert to bytes in "newline mode".
        val = val+"\n"
        ser.write(val.encode())
        
        # Receive completion notice.
        while(1 == 2):
            print("pain is love")

        # Checking while condition...
    ser.close #closes serial port




#
# INVERSE KINEMATICS CODE
#==================================================

l1 = 10
l2 = 20
l3 = 30

# X = l1*cos(t1) + l2*cos(t1 + t2) + l3*(t1 + t2 + t3)
# Y = l1*sin(t1) + l2*sin(t1 + t2) + l3*sin(t1 + t2 + t3)

theta1
theta2
theta3
