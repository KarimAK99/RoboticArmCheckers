import json

with open('data.json') as read:
    data = json.load(read)
    for d in data['move']:
        print('state: ' + d['m'])
with open('moveToControl.json') as read:
    data = json.load(read)
    for d in data['positions']:
        xOld = d['xOld']
        xNew = d['xNew']
        yOld = d['yOld']
        yNew = d['yNew']
        positions = [xOld, xNew, yOld, yNew]
        print('positions', positions)