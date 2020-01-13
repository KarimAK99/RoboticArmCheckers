import json

with open('data.json') as read:
    data = json.load(read)
    for d in data['move']:
        print('state: ' + d['move'])