# kickballscorer

[![Build Status](https://travis-ci.org/ceasarj/kickballscorer.svg?branch=master)](https://travis-ci.org/ceasarj/kickballscorer)

######Currently in Development

###What is this about?
Kickballscorer is an Android application for umpires to keep score which sends a live feed to players, spectators and field umpires. 

###What you need
1. You need to register the app to firebase.
2. You will need to download google-services.json file and add it to the [App Directory](https://github.com/ceasarj/kickballscorer/tree/master/kickballscorer/app)
3. Then go ahead and click on console>Database>Rules and change the json to 
```
{
  "rules": {
    ".read": true,
    ".write": true
  }
}
```
###Task List
- [X] Add umpire scoring
- [X] Add game Spectating
- [X] Save Games
- [ ] Players can add their name to rostor
