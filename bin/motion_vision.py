from imutils.video import VideoStream
from time import sleep
import imutils
import time
import cv2
import numpy as np
import json

movingState = " "
blueState = ''
robot_state = ''


def motion_color():
    # select the camera
    # vs = VideoStream(src=0).start()  # laptop camera
    vs = VideoStream(src=1).start()  # robot camera
    sleep(2.0)
    staart = int(round(time.time() * 1000))
    minArea = 2500
    firstFrame = None
    global movingState
    global blueState
    global robot_state

    # loop over the frames of the video
    while True:
        # grab the current frame and initialize the occupied/unoccupied
        frame = vs.read()

        frame = frame
        movingState = "not moving"
        blueState = "no blue"
        # if the frame could not be grabbed, then we have reached the end
        # of the video
        if frame is None:
            break

        # resize the frame, convert it to grayscale, and blur it
        frame = imutils.resize(frame, width=500)
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        gray = cv2.GaussianBlur(gray, (21, 21), 0)

        # if the first frame is None, initialize it
        if firstFrame is None:
            firstFrame = gray
            continue

        # compute the absolute difference between the current frame and first frame
        frameDelta = cv2.absdiff(firstFrame, gray)
        thresh = cv2.threshold(frameDelta, 25, 255, cv2.THRESH_BINARY)[1]

        # dilate the thresholded image to fill in holes, then find contours
        thresh = cv2.dilate(thresh, None, iterations=2)
        contours = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        contours = imutils.grab_contours(contours)

        for c in contours:
            if cv2.contourArea(c) < minArea:
                continue

            (x, y, w, h) = cv2.boundingRect(c)
            cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 2)
            movingState = "moving"

        # color detection
        # define the list of boundaries
        hsv_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
        dilated_color = cv2.dilate(hsv_frame, None, iterations=2)
        boundaries = [([97, 200, 200], [110, 255, 255])]

        # loop over the boundaries
        for (lower, upper) in boundaries:
            # create NumPy arrays from the boundaries
            lower = np.array(lower, dtype="uint8")
            upper = np.array(upper, dtype="uint8")

            # find the colors within the specified boundaries and apply the mask
            mask = cv2.inRange(dilated_color, lower, upper)
            colors = cv2.bitwise_and(frame, frame, mask=mask)
            if cv2.countNonZero(mask) > 1000:
                blueState = "blue"
        # imageOut = np.hstack([frame, output])

        # show the frame and record if the user presses a key
        cv2.imshow("move Feed", frame)
        # cv2.imshow("Thresh", thresh)
        # cv2.imshow("Frame Delta", frameDelta)
        cv2.imshow("colors", colors)

        # get the states of who is playing
        if get_move() == 'moving' and get_blue() != 'blue':
            robot_state = 'human playing'
        elif get_move() == 'moving' and get_blue() == 'blue':
            robot_state = 'robot playing'
        elif get_move() != 'moving' and get_blue() == 'blue':
            robot_state = 'unsure'
        else:
            robot_state = 'allowed to move'

        if cv2.waitKey(1) & 0xFF == ord("q"):
            break
        eend = int(round(time.time() * 1000))
        if eend - staart > 1000:
            break
        # print(get_blue())
        # print(get_move())
        # print(get_robot_state())
        write_data()
        read_data()

    # cleanup the camera and close any open windows
    vs.stop()
    cv2.destroyAllWindows()


def get_blue():
    return blueState


def get_move():
    return movingState


def get_robot_state():
    return robot_state


def write_data():
    data = {'move': []}
    data['move'].append({
        'move': get_robot_state()
    })
    with open('data.json', 'w') as moveData:
        json.dump(data, moveData)


def read_data():
    with open('data.json') as read:
        data = json.load(read)
        for d in data['move']:
            print('state: ' + d['move'])


if __name__ == '__main__':
    motion_color()
