from bs4 import BeautifulSoup
from time import sleep
#from run.gc_run import instrumentCategory

class buildList:
    
    numOfPages = 0
    
    
    
    def __init__(self, numOfPages):
        self.numOfPages = numOfPages
    
    
    
    def getGCFileList(self, instrumentCategory):
        
        baseURL = 'searchpageshtml'            
        fileURLs = []
            
        for i in range(self.numOfPages + 1):
            
            if i < 1:
                continue
            
            currentFile = f'{baseURL}/{instrumentCategory}/{i}.html'            
            fileURLs.append(currentFile)                
        
        return fileURLs
    
      
               
    def getGCLinkList(self, pages, pageCount):
        
        #dictionary for guitar listing and URLs
        pageHTMLList = []        
        instrumentList = []        

        for i in range(pageCount):
                        
            currentPage = pages[i]            
        
            #one page at a time
            with open(currentPage, 'r', encoding='utf-8') as f:
                soup = BeautifulSoup(f, 'html.parser')                        
                pageHTMLList = soup.find_all('a', attrs={'class': 'jsx-2420341498 product-name gc-font-light font-normal text-base leading-[18px] md:leading-6 text-[#2d2d2d] mb-2 md:h-[72px] h-[36px] hover:underline'})
                                    
            #populate links and models list
            i = 0
                 
            #prepend all of the links to direct to guitar center website
            guitarCenterPrefix = "https://www.guitarcenter.com/"

            for text in pageHTMLList:
                model_entry = pageHTMLList[i].text.strip()                 
                link_entry = guitarCenterPrefix + text['href']
                
                instrumentList.append(dict(model = model_entry, 
                                           link = link_entry))
                i = i + 1
                
       
        return instrumentList