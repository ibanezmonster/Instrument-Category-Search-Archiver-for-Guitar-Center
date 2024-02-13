import requests
from time import sleep

#acquire list of proxies
with open("valid_proxies.txt", "r") as f:
    proxies = f.read().split("\n")


sites_to_check = ["https://books.toscrape.com",
                  "https://books.toscrape.com/catalogue/category/books/fantasy_19/index.html",
                  "https://books.toscrape.com/catalogue/category/books/history_32/index.html"]


# res = requests.get('https://httpbin.org/ip', proxies={"http": proxies[0]})
# print(res.status_code)


#sites_to_check2 = ["https://guitarcenter.com"]


for h, site in enumerate(sites_to_check):

    for i, proxy in enumerate(proxies):

        try:
            print(f"Using the proxy #{i}: {proxies[i]}, URL {h}: {sites_to_check[h]}")                        

            #res = requests.get(site, proxies={"http": proxies[h], "https": proxies[h]})

            #request headers
            headers = {'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36'}


            res = requests.get(site, headers=headers, proxies={"http": proxies[i]})
            #print(res.content)


            sleep(10)
            print(res.status_code)

            #if success, then move on to the next URL
            if res.status_code == 200:
                break

        except:
            print(res.status_code)
            print("Failed")

        finally:
            #give up on URL if all proxies are exhausted
            if i == len(proxies):
                i = 0
                break

            else:
                i += 1

    h += 1
    print(h)    