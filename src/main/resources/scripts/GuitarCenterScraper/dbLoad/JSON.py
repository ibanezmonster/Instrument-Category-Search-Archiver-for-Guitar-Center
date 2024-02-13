from datetime import date
import os
import json

class JSON:
       
    def __init__(self):
        self.guitarLinks = self
    
    
    
    def createJSON(self, guitarLinks, instrumentCategory):
        dt = date.today()        
        folder = './list'      
        filePath = f'{folder}/{instrumentCategory}/list-{dt}.json'
                
        if not os.path.exists(folder):
            os.mkdir(folder)    
               
        with open(filePath, 'w') as f:
            json.dump(guitarLinks, f)
        
        
        
        