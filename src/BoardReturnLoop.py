import cv2
import numpy as np
import imutils
import json
import time
import math as math


def main():
    start = int(round(time.time() * 1000))

    cam = cv2.VideoCapture(1, cv2.CAP_DSHOW)
    cam.set(cv2.CAP_PROP_FRAME_WIDTH, 1280)
    cam.set(cv2.CAP_PROP_FRAME_HEIGHT, 720)
    retval, frame = cam.read()
    if retval != True:
        #    #replace this by popup window
        raise ValueError("Can't read frame. Check if webcam is connected !!")
    cv2.imwrite('img2.jpg', frame)
    # cv2.imshow("img1", frame)
    img = cv2.imread('img2.jpg')
    img_hsv = cv2.imread('img2.jpg')

    # img = cv2.imread('queentest.jpg')
    # img_hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

    # detect top left corner
    hsvY = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
    lower_rangeY = np.array([30, 60, 195])
    upper_rangeY = np.array([40, 255, 255])
    maskY = cv2.inRange(hsvY, lower_rangeY, upper_rangeY)
    pointsY = cv2.findNonZero(maskY)
    avgY = np.mean(pointsY, axis=0)
    # print(avgY)
    # print(avgY[0])
    yellowX = int(avgY[0][0])
    yellowY = int(avgY[0][1])
    # print(yellowX)
    # print(yellowY),

    # detect bottom right corner
    hsvP = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
    lower_rangeP = np.array([150, 100, 175])
    upper_rangeP = np.array([165, 255, 255])
    maskP = cv2.inRange(hsvP, lower_rangeP, upper_rangeP)
    pointsP = cv2.findNonZero(maskP)
    avgP = np.mean(pointsP, axis=0)
    # print(avgP)
    # print(avgP[0])
    pinkX = int(avgP[0][0])
    pinkY = int(avgP[0][1])
    # print(pinkX)
    # print(pinkY)
    ##replace r by detected colour points
    ####r = cv2.selectROI(img)
    ####print(r[0])
    ####print(r[1])
    ####print(r[2])
    ####print(r[3])
    ####print(int(r[1] + r[3]))
    ####print(int(r[0] + r[2]))
    ##r0=x1 r1=y1 r2=xadd r3=yadd r0+r2=x2 r1+r3=y2
    # crop to area of interest, in this case board using detected cornerpoint
    img = img[int(yellowY):int(pinkY), int(yellowX):int(pinkX)]
    img_hsv = img_hsv[int(yellowY):int(pinkY), int(yellowX):int(pinkX)]

    # ranges for b&w
    hsv = cv2.cvtColor(img_hsv, cv2.COLOR_BGR2HSV)
    white_lower_range = np.array([0, 0, 200])
    white_upper_range = np.array([179, 75, 255])
    black_lower_range = np.array([0, 0, 0])
    black_upper_range = np.array([179, 255, 50])
    # circle detection to know where to look for black and white
    img = cv2.medianBlur(img, 5)
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    circles = cv2.HoughCircles(gray, cv2.HOUGH_GRADIENT, 1, 20, param1=50, param2=35, minRadius=25, maxRadius=50)

    # detect red for queen finding
    lower_range1R = np.array([0, 75, 150])
    upper_range1R = np.array([10, 200, 255])
    lower_range2R = np.array([140, 75, 150])
    upper_range2R = np.array([179, 200, 255])
    mask1R = cv2.inRange(img_hsv, lower_range1R, upper_range1R)
    mask2R = cv2.inRange(img_hsv, lower_range2R, upper_range2R)
    maskR = mask1R | mask2R

    circles = np.uint16(np.around(circles))
    circle_color = list()
    # for circles in list draw and check if b or w
    for i in circles[0, :]:
        # print("next piece")
        # draw the outer circle
        cv2.circle(img, (i[0], i[1]), i[2], (0, 255, 0), 2)
        # draw the center of the circle
        cv2.circle(img, (i[0], i[1]), 2, (0, 0, 255), 3)
        h, w = gray.shape
        mask = np.zeros((h, w), np.uint8)
        cv2.circle(mask, (i[0], i[1]), i[2], (255, 255, 255), thickness=-1)
        masked_data = cv2.bitwise_and(img, img, mask=mask)
        _, thresh = cv2.threshold(mask, 1, 255, cv2.THRESH_BINARY)
        contours, _ = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        x, y, w, h = cv2.boundingRect(contours[0])
        # print(x)
        # print(y)
        # print(w)
        # print(h)
        crop = masked_data[y:y + h, x:x + w]
        # cv2.imshow('crop', crop)
        # print(i[0])
        # print(i[1])
        # print(i[2])
        # mean, _ = cv2.meanStdDev(crop)
        queenCntr = 0
        blackCntr = 0
        whiteCntr = 0
        # print(img[i[1],i[0]])
        for row in range(y, y + h):
            for col in range(x, x + w):
                if (math.sqrt((i[0] - col) ** 2 + (i[1] - row) ** 2) <= i[2]):
                    # print("i got here")
                    blue = img[row, col, 0]
                    green = img[row, col, 1]
                    red = img[row, col, 2]
                    # print(blue)
                    # print(green)
                    # print(red)
                    mean = int((int(blue) + int(green) + int(red)) / 3)
                    # print(mean)
                    if (maskR[row, col] > 0):
                        queenCntr = queenCntr + 1
                    # print("queencntr++")
                    if (mean < 127.5):
                        # print("blackCntr++")
                        blackCntr = blackCntr + 1
                    # print(blackCntr)
                    else:
                        # print("whitecntr++")
                        whiteCntr = whiteCntr + 1
                    # print(whiteCntr)
        # print(blackCntr)
        # print(whiteCntr)
        if (queenCntr > 20):
            if (blackCntr > whiteCntr):
                # print('black')
                circle_color.append((i[0], i[1], 1, 1))
            else:
                # print('white')
                circle_color.append((i[0], i[1], 2, 1))
        else:
            if (blackCntr > whiteCntr):
                # print('black')
                circle_color.append((i[0], i[1], 1, 0))
            else:
                # print('white')
                circle_color.append((i[0], i[1], 2, 0))
    # print output
    # print(circle_color)
    # convert pixel coords to board coords
    h, w = gray.shape
    square_w = w / 8
    square_h = h / 8
    # print(w)
    # print(h)
    circle_position = list()
    for c in circle_color:
        x_pos = 0
        y_pos = 0
        for x in range(1, 9):
            if ((c[0] < x * square_w) & (c[0] > (x - 1) * square_w)):
                x_pos = x
        for y in range(1, 9):
            if ((c[1] < y * square_h) & (c[1] > (y - 1) * square_h)):
                y_pos = y
        circle_position.append((c[2], x_pos - 1, y_pos - 1, c[3]))
    # output final list with colours and board coordinates
    # output format = (colour, X, Y)
    # where colour=1 -> black, colour=2 -> white
    # X from left to right 0 -> 7
    # Y from top to bottom 0 -> 7
    print(circle_position)

    boardData = {'board': []}
    boardData['board'].append({
        'board': circle_position
    })
    with open('boardData.json', 'w') as boardState:
        json.dump(boardData, boardState)

    end = milli_sec = int(round(time.time() * 1000))
    duration = end - start
    print(duration)

    cv2.imshow('detected circles', img)
    cv2.imshow('detected red', maskR)
    cv2.waitKey(0)
    cv2.destroyAllWindows()
