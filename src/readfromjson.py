import json


def read():
    with open('moveToControl.json') as read:
        data = json.load(read)
    for d in data['positions']:
        xOld = d['xOld']
        xNew = d['xNew']
        yOld = d['yOld']
        yNew = d['yNew']
        pos = [xOld, xNew, yOld, yNew]
        print('positions', pos)
    return pos


if __name__ == '__main__':
    positions = read()
    x1 = positions[0]
    x2 = positions[1]
    y1 = positions[2]
    y2 = positions[3]
    print('x1: ', x1, ' x2: ', x2, ' y1: ', y1, ' y2: ', y2)