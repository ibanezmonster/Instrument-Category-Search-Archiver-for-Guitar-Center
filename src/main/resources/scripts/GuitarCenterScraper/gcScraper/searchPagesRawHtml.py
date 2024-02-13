from bs4 import BeautifulSoup
from urllib import request
from time import sleep
import os, re
import requests
#from run.gc_run import instrumentCategory


class searchPagesRawHtml:
    
    def __init__(self):
        pass




    def calculateHomeURL(self, currentPageNum, instrumentCategory):
        #Categories:
        # 'Acoustic Guitar'
        # '8-string Electric Guitar'
        # '7-string Electric Guitar'

        addValue = 24 #24 results per page
        newVal = addValue * (currentPageNum - 1)
        
        if instrumentCategory == 'acoustic_guitar':            
            URL = f'https://www.guitarcenter.com/Acoustic-Guitars.gc?N=18153&Ns=bM&Nao={newVal}&pageName=category-page&recsPerPage=24&profileCountryCode=US&profileCurrencyCode=USD&SPA=true'
        
        elif instrumentCategory == '7_string_electric_guitar':
            URL = f'https://www.guitarcenter.com/7-String--Electric-Guitars.gc?N=18145+2214&Ns=bM&Nao={newVal}&pageName=category-page&recsPerPage=24&profileCountryCode=US&profileCurrencyCode=USD&SPA=true'
            
        elif instrumentCategory == '8_string_electric_guitar':
            URL = f'https://www.guitarcenter.com/8-String--Electric-Guitars.gc?N=18145+2213&Ns=bM&Nao={newVal}&pageName=category-page&recsPerPage=24&profileCountryCode=US&profileCurrencyCode=USD&SPA=true'  
            
        return URL
    
    
    
    
    def savePage(self, currentPageNum, instrumentCategory):   
             
        # example of URLS:
        # searchUrl2 = 'https://www.guitarcenter.com/8-String--Electric-Guitars.gc?N=18145+2213&Ns=bM&Nao=24&pageName=category-page&recsPerPage=24&profileCountryCode=US&profileCurrencyCode=USD&SPA=true'
        # searchUrl3 = 'https://www.guitarcenter.com/8-String--Electric-Guitars.gc?N=18145+2213&Ns=bM&Nao=48&pageName=category-page&recsPerPage=24&profileCountryCode=US&profileCurrencyCode=USD&SPA=true'
        homeUrl = ''
        data = ''
        page_save_success = False
        
        if instrumentCategory == 'acoustic_guitar':
            homeUrl = 'https://www.guitarcenter.com/Acoustic-Guitars.gc'
        
        elif instrumentCategory == '7_string_electric_guitar':
            homeUrl = 'https://www.guitarcenter.com/7-String--Electric-Guitars.gc'
            
        elif instrumentCategory == '8_string_electric_guitar':
            homeUrl = 'https://www.guitarcenter.com/8-String--Electric-Guitars.gc'  
        
        
        #if it's not the first page, recalculate the page URL
        if currentPageNum > 1:
            homeUrl = self.calculateHomeURL(self, currentPageNum, instrumentCategory)
            print(f'\nCurrent URL: {homeUrl}')
            
            
        #acquire list of proxies
        with open('valid_proxies.txt', 'r') as f:
            proxies = f.read().split('\n')
        
            
        #rotate proxies when reading the page   
        for i, proxy in enumerate(proxies):

            try:
                print(f'Using the proxy #{i}: {proxy}')
                print(f'URL: {homeUrl}') 
                
                #request headers
                headers = {'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36'}
                                
                #wait 30 seconds to avoid detection
                if currentPageNum > 1:
                    print('Waiting 30 seconds...')
                    sleep(30)
                
                res = requests.get(homeUrl, headers=headers, proxies={'http': proxies[i]})
    
                #if success, then move on to the next URL
                if res.status_code == 200:                    
                    data = res.text     #save text string
                    #data.encode()
                    page_save_success = True
                    print(f'{res.status_code} - Successful page read')
                    break
    
            except:
                print(f'Proxy failed with status code: {res.status_code}')                
    
            finally:
                #give up on URL if all proxies are exhausted
                if i == len(proxies):
                    i = 0
                    break
                #otherwise, move on to trying the next proxy
                else:
                    i += 1   
            
            
        if page_save_success == False:
            print(f'Unable to save page: {homeUrl}')
            

        #write to file    
        folder = f'./searchpageshtml/{instrumentCategory}'    
        currentFile = f'{folder}/{currentPageNum}.html'
        
        try:
            if(currentPageNum == 1):
                if not os.path.exists(folder):
                    os.mkdir(folder)
          
            with open(currentFile, 'w', encoding='utf8') as f:
                f.write(data)
                
            print(f'Page {currentPageNum} saved.')
        
        except:
            raise Exception('Error writing to html file.')
    
    
    
    
    #check if next page is available
    def checkPageCount(self, instrumentCategory):
        
        numOfPages = 0
        
        with open(f'./searchpageshtml/{instrumentCategory}/1.html', 'r', encoding='utf-8') as txt:
            soup = BeautifulSoup(txt, 'html.parser')
                        
            rel_soup = soup.find_all('a', attrs={'rel': 'nofollow'})        
            num_rel = len(rel_soup)
            num_arr = []                
            
            #for i in rel_soup:
            for x in range(num_rel):
                num_content = rel_soup[x].text.strip()
                
                if num_content.isnumeric():
                    num_arr.append(num_content)                                
                            
                
            numOfPages = len(num_arr)
            
        return numOfPages    
    
