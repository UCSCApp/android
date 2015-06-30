PROGRESS:
- Event page pretty much done
- Top toolbar implemented

TO DO:
- RSS page
- Dining Hall page
- Map page
- Bottom toolbar
- Converting each page into fragments
- Settings page
- Also, images load kind of slow, maybe some way to make them load faster?
edit 6/29/2015 11:53pm:
- Event page also needs search function actually

Currently the Events page is using the fake json. To make actual http call:
- Uncomment lines 83 and 163
- Comment line 150
- (if somehow android studio auto formats and changes the line numbers, just uncomment the lines
   that have displayResult and comment the line with parseFakeData)

Only tested on Galaxy S5 so far