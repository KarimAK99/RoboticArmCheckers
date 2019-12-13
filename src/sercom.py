#
#   TODO
# ==================================================
#   -integration, read board positiong from java
#   -add distance limiter/ add board pos limiter
#   - Error poimts: (4,1); (5.1);
#       -line 66, ValueError: math domain error(s)
#           - fuck it, just round that shit
#   -arduino: change arm range

#   -fix kinematics
#       -precalculate angles
#       -see where it wants to go
#       -remove 90 buffer and reimagine its location 
#       - atan ( abs(y/x)) ???

"""
-angles printed move correctly
-calculations appear to be correct
"""

#
#   IMPORTS
# ==================================================
import serial
import time
import math
import json


def moveCheck():
    val = ""
    # Start loop...
    while 1:
        while 1:
            tmp = str(ser.read())[2:-1]
            if tmp == "\\n":
                # print("breaking \n")
                break
            else:
                val = val + tmp
                # print("[[", val, "]]", sep = "")

        # print("val is currently::", val, "-- pausing program for 3 seconds-- ")
        # time.sleep(3)
        # print("performing check...")
        if val == "movement complete":
            # print("FINISHED")
            # time.sleep(3)
            break
        # Reset val for the next call.
        val = ""


#
# INVERSE KINEMATICS CODE
# ==================================================

# Input: x and y board positions
def inverseKinematics(xB, yB):
    x = xB  # abs(xB)# - ARMPOSITIONX)
    y = yB  # abs(yB)# - ARMPOSITIONY)

    temp = (x * x + y * y - LENGTHONE * LENGTHONE - LENGTHTWO * LENGTHTWO) / (2 * LENGTHONE * LENGTHTWO)
    if temp > 1:
        print("Rounding to 1")
        temp = 1
    elif temp < -1:
        print("Rounding to -1")
        temp = -1

    theta2 = math.acos(temp)
    try:
        theta1A = math.atan(y / x)
    except:
        print("issue... setting theta1A to 0")
        theta1A = 0  # This should work since it means alpha == 0 (see slide 31).
    # ((math.atan(y/x)) + (math.atan( (LENGTHTWO*math.sin(theta2)) / (LENGTHONE + LENGTHTWO*math.cos(theta2)) )))

    # If the negative case is present...
    if 1:  # XXX how to deal with case divisions and when theta2 == 0
        theta2 = theta2 * -1
        theta1B = math.atan((LENGTHTWO * math.sin(theta2)) / (LENGTHONE + LENGTHTWO * math.cos(theta2)))
        theta1 = theta1A + theta1B
    # Else the case is positive
    else:
        print("Positive case identified!")
        theta1B = math.atan((LENGTHTWO * math.sin(theta2)) / (LENGTHONE + LENGTHTWO * math.cos(theta2)))
        theta1 = theta1A - theta1B
    # print(theta1, theta2)

    """print("x is ", x, ", x*x is ", x*x, sep = "")
    print("y is ", y, ", y*y is ", y*y, sep = "")
    print("x*x + y*y is", x*x + y*y)

    print("l1 is ", LENGTHONE , ", l1 squared is ", LENGTHONE*LENGTHONE, sep = "")
    print("l2 is ", LENGTHTWO, ", l2 squared is ", LENGTHTWO*LENGTHTWO, sep = "")
    print("2*l1*l2 is", 2*LENGTHONE*LENGTHTWO)
    print("Their sum is", (x*x + y*y - LENGTHONE*LENGTHONE - LENGTHTWO*LENGTHTWO))
    print("Divided, their value is", (x*x + y*y - LENGTHONE*LENGTHONE - LENGTHTWO*LENGTHTWO) / (2*LENGTHONE*LENGTHTWO))
    print("In cos-1, the final value is", math.acos( (x*x + y*y - LENGTHONE*LENGTHONE - LENGTHTWO*LENGTHTWO) / (2*LENGTHONE*LENGTHTWO) ))
    print()

    print("(y/x) is ", y/x, ", where tan-1(y/x) is ,", math.atan(y/x), sep = "")
    print("sin(theta2) is ", math.sin(theta2), ", where it is then scaled with l2 to ", (LENGTHTWO*math.sin(theta2)), sep = "")
    print("cos(theta2) is ", math.cos(theta2), ", where it is then scaled with l2 and l1 added to ", (LENGTHONE + LENGTHTWO*math.cos(theta2)), sep = "")
    print("tan-1 of this is then", math.atan( (LENGTHTWO*math.sin(theta2)) / (LENGTHONE + LENGTHTWO*math.cos(theta2)) ))
    """
    # print("Together, they make", ((math.atan(y/x)) + (math.atan( (LENGTHTWO*math.sin(theta2)) / (LENGTHONE + LENGTHTWO*math.cos(theta2)) ))) )

    # print("\nJust to be clear, our values are", theta1, "and", theta2)
    # Covert to roboarm-friendly jargen.
    theta1 = round(math.degrees(theta1))  # - 90
    theta2 = (round(math.degrees(theta2)) + 90)

    if theta2 < 0:
        theta2 = theta2 * 0.8
    else:
        theta2 = theta2 * 1.1
    # print("Which then converts to", theta1, "and", theta2, "\n")

    # print("Theta1 is,", theta1, "---  and Theta2 is,", theta2, "---")
    return theta1, theta2


#
#   MODE 1: Teminal Mode
# ==================================================
def terminalMode():
    val = input("Please input 1', 2', 3' and Magnet state:  ")

    while (val != "terminate"):
        # Convert to bytes in "newline mode".
        val = val + "\n"
        ser.write(val.encode())

        # Receive completion notice.
        # while(1 == 2):
        #    print("pain is love")

        # Checking while condition...
        moveCheck();
        val = input("Please input 1', 2', 3' and Magnet state:  ")
    ser.close  # closes serial port


#
#   MODE 2: Automatic Mode
# ==================================================
def push(movement, t1, t2, t3, m):
    val = str(t1) + "," + str(t2) + "," + str(t3) + "," + str(m)
    val = val + "\n"
    ser.write(val.encode())
    print("Movement ", movement, ":  ", val, sep="")
    moveCheck()


def automaticMode():
    # Ugly-af, copied-over code.

    # Starting board position.
    dbX = int(input("Desired x coordinate for the board please-- "))
    dbY = int(input("Desired y coordinate for the board please-- "))
    desiredY = -2 + (dbX - 4) * 3
    desiredX = 2 + dbY * 3
    print("Your desired coordinates are ", desiredX, desiredY)

    # Comment explaining what in the loop
    [thetaEen, thetaTwee] = inverseKinematics(desiredX, desiredY)
    push(3, thetaEen, 160, thetaTwee, 1)
    push(4, thetaEen, 0, thetaTwee, 0)

    # Set-up movement for the next move.
    thetaEenP = thetaEen
    thetaTweeP = thetaTwee

    while 1:
        # XXX Read input somehow
        # pause = input("press enter continue")
        move = readJson()
        if move == 'allowed to move':
            dbX = int(input("Desired x coordinate for the board please-- "))
            dbY = int(input("Desired y coordinate for the board please-- "))

            # Convert board coordinates to cm
            # and some x-y swapping.
            desiredY = -2 + (dbX - 4) * 3
            desiredX = 2 + dbY * 3
            print("Your desired coordinates are ", desiredX, desiredY)

            # Move the arm down, grab piece from previous desired position.
            push(1, thetaEenP, 180, thetaTweeP, 0)

            # Raise the arm with the piece.
            push(2, thetaEenP, 140, thetaTweeP, 0)

            # Lower arm and drop piece in new desired postition.
            [thetaEen, thetaTwee] = inverseKinematics(desiredX, desiredY)
            push(3, thetaEen, 170, thetaTwee, 1)

            # Raise arm without the piece, restart magnetism.
            push(4, thetaEen, 0, thetaTwee, 0)

            # Set-up movement for the next move.
            thetaEenP = thetaEen
            thetaTweeP = thetaTwee

    ser.close #closes serial port


def readJson():
    with open('data.json') as read:
        data = json.load(read)
        for d in data['move']:
            move = d['move']
        return move


#
#   START CODE
# ==================================================

# Actuator length constants.
LENGTHONE = 17  # Length of the z-axis actuator.
LENGTHTWO = 10.5  # Length of the shortest actuator.

# Actuator yam coordinates for desired endpoint calculation.
ARMPOSITIONX = 12
ARMPOSITIONY = -6

print("Actuator lengths are [1,2]:[", LENGTHONE, ",", LENGTHTWO, "]", sep="")
print("Arm positions are registerd as [x, y]:[", ARMPOSITIONX, ", ", ARMPOSITIONY, "]", sep="")

baudRate = 9600
serPort = "/dev/ttyACM0"
ser = serial.Serial(serPort, baudRate, timeout=5)
print("Serial port " + serPort + " opened/ Baudrate " + str(baudRate))
startMarker = 60
endMarker = 62

"""mode = input("Please enter 0 for terminal mode and 1 for automatic mode-- :   ")
if mode == "0":
    terminalMode()
if mode == "1":
    automaticMode()"""

automaticMode()
