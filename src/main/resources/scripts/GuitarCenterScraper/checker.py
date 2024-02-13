#run from the same directory as gc_run.py
#keep open with admin privileges
#will check for any request to run gc_run.py

#it will read the to_run.txt file, anything with a 1 by the name will trigger command
#to open a new cmd and run gc.py from there, and then close the window

#gc_run.py will then set the value back to 0

from time import sleep
from pathlib2 import Path
import os

while True:
    
    os.system('cls')
    print('Waiting for request...')
    
    #check file every 20 seconds, wait 10 more seconds success
    sleep(20)
    
    flag = ''
    success = False        
    text_to_replace = ''
    
    #look for instrument with flag set to 1 (on/active request), only update the first one found 
    file = open("to_run.txt", 'r')
    lines = file.readlines()
    
    instrument_to_turn_off = ''
        
    for line in lines:
        if line[-2] == '1':         # -2 because last character is a \n
            instrument_to_turn_off = line
            break
    
    print(instrument_to_turn_off)
    
    file.close()
        
    #replace text    
    file = Path(r"to_run.txt")    
    data = file.read_text()
    
    off_setting = instrument_to_turn_off.replace('=1', '=0')
            
    data = data.replace(instrument_to_turn_off, off_setting)
    file.write_text(data)
    success = True

    #launch new cmd to start scraper
    if instrument_to_turn_off == '8-string Electric Guitar=1\n':
        print(instrument_to_turn_off)
        os.system('start cmd /k ' +
              '\"' +
              'python -m gc_run ' + 
              '8_string_electric_guitar ' +
              '&& exit' +
              '\"')
    
    elif instrument_to_turn_off == '7-string Electric Guitar=1\n':
        os.system('start cmd /k ' +
               '\"' +
               'python -m gc_run ' + 
               '7_string_electric_guitar ' +
               '&& exit' +
               '\"')
    
    elif instrument_to_turn_off == 'Acoustic Guitar=1\n':
        os.system('start cmd /k ' +
               '\"' +
               'python -m gc_run ' + 
               'acoustic_guitar ' +
               '&& exit' +
               '\"')
    
    #for 10 seconds, leave open operation complete message
    if success == True:
        print("Operation complete.")
        
    else:
        print("Error performing update.")
        
    sleep(10) 