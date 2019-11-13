from imutils.video import VideoStream
import argparse
from time import sleep
import imutils
import time
import cv2
import numpy as np

movingState = " "
blueState = ''
robot_state = ''


def motion_color():
    # construct the argument parser and parse the arguments
    ap = argparse.ArgumentParser()
    ap.add_argument("-a", "--min-area", type=int, default=3000, help="minimum area size")
    args = vars(ap.parse_args())
    vs = VideoStream(src=0).start()  # laptop camera
    # vs = VideoStream(src=1).start()  # robot camera
    sleep(2.0)
    # initialize the first frame in the video stream
    firstFrame = None
    global movingState
    global blueState
    global robot_state

    # loop over the frames of the video
    while True:
        # grab the current frame and initialize the occupied/unoccupied
        frame = vs.read()
        frame = frame if args.get("video", None) is None else frame[1]
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

        # compute the absolute difference between the current frame and
        # first frame
        frameDelta = cv2.absdiff(firstFrame, gray)
        thresh = cv2.threshold(frameDelta, 25, 255, cv2.THRESH_BINARY)[1]

        # dilate the thresholded image to fill in holes, then find contours
        # on thresholded image
        thresh = cv2.dilate(thresh, None, iterations=2)
        cnts = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL,
                                cv2.CHAIN_APPROX_SIMPLE)
        cnts = imutils.grab_contours(cnts)

        # color detection, works best w blue
        # define the list of boundaries
        hsv_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
        boundaries = [([97, 100, 100], [110, 255, 255])]

        # loop over the boundaries
        for (lower, upper) in boundaries:
            # create NumPy arrays from the boundaries
            lower = np.array(lower, dtype="uint8")
            upper = np.array(upper, dtype="uint8")

            # find the colors within the specified boundaries and apply
            # the mask
            mask = cv2.inRange(hsv_frame, lower, upper)
            output = cv2.bitwise_and(frame, frame, mask=mask)
            if cv2.countNonZero(mask) > 50:
                blueState = "blue"
        imageOut = np.hstack([frame, output])

        # loop over the contours
        for c in cnts:
            # if the contour is too small, ignore it
            if cv2.contourArea(c) < args["min_area"]:
                continue

            # compute the bounding box for the contour, draw it on the frame,
            # and update the state
            (x, y, w, h) = cv2.boundingRect(c)
            cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 2)
            movingState = "moving"

        # show the frame and record if the user presses a key
        cv2.imshow("move Feed", frame)
        cv2.imshow("Thresh", thresh)
        cv2.imshow("Frame Delta", frameDelta)
        cv2.imshow("colors", output)
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
        print(get_blue())
        print(get_move())
        print(get_robot_state())

    # cleanup the camera and close any open windows
    # print(movingState)
    # print(blueState)

    vs.stop() if args.get("video", None) is None else vs.release()
    cv2.destroyAllWindows()


def get_blue():
    return blueState


def get_move():
    return movingState


def get_robot_state():
    return robot_state


if __name__ == '__main__':
    motion_color()
