import cv2
import numpy as np
import imutils
import time

start = int(round(time.time() * 1000))

cam = cv2.VideoCapture(1,cv2.CAP_DSHOW)
cam.set(cv2.CAP_PROP_FRAME_WIDTH, 1280)
cam.set(cv2.CAP_PROP_FRAME_HEIGHT, 720)
retval, frame = cam.read()
if retval != True:
    #replace this by popup window
    raise ValueError("Can't read frame. Check if webcam is connected !!")
cv2.imwrite('img2.jpg', frame)
#cv2.imshow("img1", frame)
img = cv2.imread('img2.jpg')
img_hsv = cv2.imread('img2.jpg')

#img = cv2.imread('arm3.jpg')
#r = cv2.selectROI(img)
#img = img[int(r[1]):int(r[1] + r[3]), int(r[0]):int(r[0] + r[2])]

hsvL = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

lower_rangeL = np.array([40,55,175])
upper_rangeL = np.array([50,200,255])

maskL = cv2.inRange(hsvL, lower_rangeL, upper_rangeL)

pointsL = cv2.findNonZero(maskL)
avgL = np.mean(pointsL, axis=0)
#print(avgL)
#print(avgL[0])
limeX = int(avgL[0][0])
limeY = int(avgL[0][1])
#print(limeX)
#print(limeY)

cv2.circle(img,(int(limeX),int(limeY)),2,(0,0,255),3)

end = milli_sec = int(round(time.time() * 1000))
duration = end - start
print(duration)

cv2.imshow('image', img)
cv2.imshow('mask', maskL)

cv2.waitKey(0)
cv2.destroyAllWindows()